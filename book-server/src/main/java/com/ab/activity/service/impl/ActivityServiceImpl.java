package com.ab.activity.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.activity.domain.Activity;
import com.ab.activity.domain.dto.ActivityAddDTO;
import com.ab.activity.domain.dto.ActivityDTO;
import com.ab.activity.domain.dto.UpdateActivityDTO;
import com.ab.activity.mapper.ActivityMapper;
import com.ab.activity.service.ActivityService;
import com.ab.activity.util.TimeUtil;
import com.ab.base.BaseIncrIDEntity;
import com.ab.constant.RabbitEnum;
import com.ab.context.BaseContext;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author 86150
* @description 针对表【activity】的数据库操作Service实现
* @createDate 2024-01-26 21:58:42
*/
@RequiredArgsConstructor
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService {


    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate redisTemplate;
    private final String BASEKEY="RecruitmentKey:Activity:";
    @Override
    public List<ActivityDTO> listAllActivity(Integer status) {
        List<Activity> activityList=null;
        if(status==2){
            activityList= list();
        }
        else{
            activityList=lambdaQuery().eq(Activity::getStatus,status).list();
        }

        List<ActivityDTO> activityDTOList = BeanUtil.copyToList(activityList, ActivityDTO.class);
        return activityDTOList;
    }

    @Override
    public void addActivity(ActivityAddDTO activityAddDTO) {

        Long adminId = BaseContext.getCurrentId();
        Activity activity = new Activity();
        BeanUtil.copyProperties(activityAddDTO,activity);
        activity.setAdminId(adminId);
        //保存活动到数据库
        save(activity);
        //保存活动到redis
        redisTemplate.opsForValue().set(BASEKEY+activity.getId(),String.valueOf(activity.getId()));
        //死信队列+ttl发布活动
        publishActivity(activity);

        log.info("新增活动成功->{}",activityAddDTO);

    }

    @Override
    public void deleteActivityById(List<Long> ids) {
        removeBatchByIds(ids);
        List<String> keyList = Stream.of(ids).map(data -> BASEKEY + data).collect(Collectors.toList());
        redisTemplate.delete(keyList);
        log.info("删除活动成功->{}",ids);
    }

    @Override
    public void reUpdateActivity(UpdateActivityDTO updateActivityDTO) {
        Long adminId = BaseContext.getCurrentId();
        Activity activity = new Activity();
        BeanUtil.copyProperties(updateActivityDTO,activity);
        activity.setAdminId(adminId);
        publishActivity(activity);
        boolean result = updateById(activity);
        if(result)
            log.info("活动预约更新->{}",activity);
        redisTemplate.opsForValue().setIfAbsent(BASEKEY+activity.getId(),String.valueOf(activity.getId()));

    }
    @Async
    public void publishActivity(Activity activity) {
        //现在距离发布活动所需毫秒
        Long publishTime = TimeUtil.getDuration(activity,0)*1000;
        //现在距离活动结束所需毫秒
        Long overTime = TimeUtil.getDuration(activity,1)*1000;
        Message publishMessage = MessageBuilder.withBody(activity.getId().toString().getBytes(StandardCharsets.UTF_8)).setExpiration(publishTime.toString()).build();
        Message overMessage = MessageBuilder.withBody(activity.getId().toString().getBytes(StandardCharsets.UTF_8)).setExpiration(overTime.toString()).build();
        //设置消息的唯一ID
        CorrelationData pubCorrelationData=new CorrelationData(UUID.randomUUID().toString());
        CorrelationData endCorrelationData=new CorrelationData(UUID.randomUUID().toString());
        //预发布活动
        rabbitTemplate.convertAndSend(RabbitEnum.ACTIVITYEXCHANGE.getName(),RabbitEnum.ACTIVITYKEY.getName(),publishMessage,pubCorrelationData);
        //预结束活动
        rabbitTemplate.convertAndSend(RabbitEnum.ACTIVITYEXCHANGE.getName(),RabbitEnum.ACTIVITYKEY.getName(),overMessage,endCorrelationData);
    }


}




