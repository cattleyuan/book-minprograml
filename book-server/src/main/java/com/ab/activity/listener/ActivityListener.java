package com.ab.activity.listener;

import com.ab.activity.domain.Activity;
import com.ab.activity.service.ActivityService;
import com.ab.activity.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author cattleYuan
 * @date 2024/1/27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ActivityListener {
    private final ActivityService activityService;
    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate redisTemplate;
    private final String BASEKEY="RecruitmentKey:Activity:";

    @RabbitListener(queues = "queue.dead")
    public void ActivityDelayComsumer(String message) {
        Integer id=Integer.parseInt(message);
        String key=BASEKEY+id;
        if(!((Optional.ofNullable(redisTemplate.opsForValue().get(key))).isPresent())){
            log.info("活动取消或结束->{}",id);
            return;
        }
        Activity activity=activityService.getById(id);
        if(activity==null){
            log.info("数据库无数据");
            return;
        }

        Long pubTime = TimeUtil.getDuration(activity,0);
        Long overTime = TimeUtil.getDuration(activity,1);

        if (Math.abs(pubTime) < 10) {
            activity.setStatus(1);
            log.info("发布成功->{}", activity);
        }
        if (Math.abs(overTime) < 10) {
            activity.setStatus(0);
            //结束后清除缓存
            redisTemplate.delete(key);
            log.info("活动结束->{}", activity);
        }
        activityService.updateById(activity);

    }
}
