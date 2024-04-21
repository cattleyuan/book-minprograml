package com.ab.dynamic.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.log.Log;
import com.ab.context.BaseContext;
import com.ab.dcomment.domain.DynamicComment;
import com.ab.dcomment.domain.vo.DynamicCommentVO;
import com.ab.dcomment.service.DynamicCommentService;
import com.ab.dynamic.domain.Dynamic;
import com.ab.dynamic.domain.dto.DynamicDTO;
import com.ab.dynamic.domain.dto.DynamicUpdateDTO;
import com.ab.dynamic.domain.dto.PageDynamicDTO;
import com.ab.dynamic.domain.vo.DynamicVO;
import com.ab.dynamic.mapper.DynamicMapper;
import com.ab.dynamic.service.DynamicService;
import com.ab.entity.User;
import com.ab.exception.BaseException;
import com.ab.partner.domain.vo.UserVO;
import com.ab.partner.service.PartnerService;
import com.ab.puzzle.service.UserService;
import com.ab.result.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author 86150
 * @description 针对表【dynamic】的数据库操作Service实现
 * @createDate 2024-03-16 11:21:49
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic>
        implements DynamicService {
    private final StringRedisTemplate RedisTemplate;
    private final PartnerService partnerService;
    private final UserService userService;
    private final DynamicCommentService dynamicCommentService;
    private final String DYNAMIC_LIKE_KEY = "dynamic:isliked:";
    private final String DYNAMIC_BOLG_KEY = "dynamic:blog:";
    private final ExecutorService pool;
    @Override
    public void updateThumbsDynamicById(Long id) {
        Long userId = BaseContext.getCurrentId();
        String key = DYNAMIC_LIKE_KEY + id;
        //判断用户是否点赞过
        Boolean isMember = RedisTemplate.opsForSet().isMember(key, userId.toString());
        if (BooleanUtil.isFalse(isMember)) {
            boolean isSuccess = lambdaUpdate().eq(Dynamic::getId,id).setSql("thumbs=thumbs+1").update();
            if (isSuccess) {
                RedisTemplate.opsForSet().add(key, userId.toString());
                log.info("点赞成功");
            }
        } else {
            boolean isSuccess = lambdaUpdate().eq(Dynamic::getId,id).setSql("thumbs=thumbs-1").update();
            if (isSuccess) {
                RedisTemplate.opsForSet().remove(key, userId.toString());
                log.info("取消点赞成功");
            }
        }


    }

    @Override
    public void addDynamic(DynamicDTO dynamicDTO) {
        Long userId = BaseContext.getCurrentId();
        Dynamic dynamic = new Dynamic();
        dynamic.setUserId(userId);
        BeanUtil.copyProperties(dynamicDTO, dynamic);
        boolean isSuccess = save(dynamic);
        if (!isSuccess) {
            throw new BaseException("保存动态失败!");
        }
        //查询已添加朋友
        List<UserVO> userVOList = partnerService.queryPartner(1);

        //遍历集合用推模式将动态推送给朋友
        for (UserVO userVO : userVOList) {
            String key = DYNAMIC_BOLG_KEY + userVO.getUser().getId();
            RedisTemplate.opsForZSet().add(key, dynamic.getId().toString(), System.currentTimeMillis());
        }
        log.info("保存动态成功->{}", dynamic.getId());

    }

    @Override
    public void udpateDynamicById(DynamicUpdateDTO dynamicUpdateDTO) {
        Dynamic dynamic = new Dynamic();
        BeanUtil.copyProperties(dynamicUpdateDTO, dynamic);
        boolean IsSuccess = updateById(dynamic);
        if (!IsSuccess) {
            throw new BaseException("更新动态失败");
        }
        log.info("动态更新成功->{}", dynamicUpdateDTO.getId());
    }

    @Override
    public void removeDynamicById(Long id) {

        removeById(id);

        dynamicCommentService.lambdaUpdate().eq(DynamicComment::getDynamicId,id).remove();
        //查询已添加朋友
        List<UserVO> userVOList = partnerService.queryPartner(1);

        //遍历集合删除缓存的动态
        for (UserVO userVO : userVOList) {
            String key = DYNAMIC_BOLG_KEY + userVO.getUser().getId().toString();
            RedisTemplate.opsForZSet().remove(key, id.toString());
        }
        log.info("删除动态成功->{}", id);
    }

    @Override
    public PageResult<DynamicVO> queryDynamicList(PageDynamicDTO pageDynamicDTO) {
        Long userId = BaseContext.getCurrentId();
        List<DynamicVO> list = new ArrayList<>();
        //可考虑用策略模式优化，这里太懒了
        Integer status=pageDynamicDTO.getStatus();
        PageResult<DynamicVO> pageResult = new PageResult<>();
        switch (status) {
            case 0:
                pageResult=queryAllDynamic(pageDynamicDTO);
                break;
            case 1:
                pageResult=queryDynamicWithFriends(list, DYNAMIC_BOLG_KEY + userId,pageDynamicDTO);
                break;
            case 2:
                pageResult=queryPersonalDynamic(list,userId,pageDynamicDTO);
                break;
            default:
                throw new BaseException("无效的查询条件");
        }
        return pageResult;
    }

    @Override
    public Integer getDynamicCTNumber(Long id) {
        Dynamic dynamic = getById(id);

        return dynamic.getThumbs();
    }

    private PageResult<DynamicVO> queryPersonalDynamic(List<DynamicVO> list,Long userId,PageDynamicDTO pageDynamicDTO) {
        Page<Dynamic> page = new Page<>(pageDynamicDTO.getPageNo(), pageDynamicDTO.getPageSize());

        Page<Dynamic> dynamicPage = lambdaQuery().eq(Dynamic::getUserId, userId).orderByDesc(Dynamic::getPublishTime).page(page);
        List<Dynamic> dynamicList = dynamicPage.getRecords();

        if(dynamicList.isEmpty()){
            return null;
        }

        for (Dynamic dynamic : dynamicList) {
            DynamicVO dynamicVO = new DynamicVO();
            buildDynamicVO(dynamic, dynamicVO, userId);
            list.add(dynamicVO);
        }

        PageResult<DynamicVO> pageResult = getPageResult(list, dynamicPage);
        return pageResult;
    }

    private PageResult<DynamicVO> queryDynamicWithFriends(List<DynamicVO> list, String key,PageDynamicDTO pageDynamicDTO) {
        int start = (pageDynamicDTO.getPageNo() - 1) * pageDynamicDTO.getPageSize();
        int end =  pageDynamicDTO.getPageNo()* pageDynamicDTO.getPageSize() - 1;
        Long size = RedisTemplate.opsForZSet().size(key);

        if (end > size) {
            end = -1;
        }
        Set<String> set = RedisTemplate.opsForZSet().range(key,start,end);
        if (set==null){
            log.info("朋友动态为空");
            return null;
        }

        List<Long> dynamicList = set.stream()
                .map(data -> Long.valueOf(data)).collect(Collectors.toList());

        Long userId = BaseContext.getCurrentId();

        for (Long blogId : dynamicList) {
            //封装返回数据
            DynamicVO dynamicVO = new DynamicVO();
            Dynamic dynamic = getById(blogId);
            buildDynamicVO(dynamic, dynamicVO, userId);
            list.add(dynamicVO);
        }

        PageResult<DynamicVO> pageResult = new PageResult<>();
        Long total = RedisTemplate.opsForZSet().zCard(key);
        pageResult.setRecords(list);
        pageResult.setTotal(total);
        pageResult.setPages((int) (total/pageDynamicDTO.getPageSize()));

        return pageResult;
    }

    private PageResult<DynamicVO> queryAllDynamic(PageDynamicDTO pageDynamicDTO)  {
        Page<Dynamic> page = new Page<>(pageDynamicDTO.getPageNo(), pageDynamicDTO.getPageSize());
        Page<Dynamic> dynamicPage = lambdaQuery().orderByDesc(Dynamic::getPublishTime).page(page);
        List<Dynamic> dynamicList =dynamicPage.getRecords();

        Long userId = BaseContext.getCurrentId();

        CountDownLatch countDownLatch = new CountDownLatch(dynamicList.size());
        //线程安全
        List<DynamicVO> list =new CopyOnWriteArrayList<>();

        if(!dynamicList.isEmpty()){

            for (Dynamic dynamic : dynamicList) {

                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        DynamicVO dynamicVO = new DynamicVO();
                        buildDynamicVO(dynamic, dynamicVO, userId);
                        list.add(dynamicVO);
                        countDownLatch.countDown();
                    }
                });

            }
        }
        else return null;

        try {
            //确认所有线程任务执行完毕
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("线程池异常");
        }

        PageResult<DynamicVO> pageResult = getPageResult(list, dynamicPage);
        return pageResult;
    }

    private PageResult<DynamicVO> getPageResult(List<DynamicVO> list, Page<Dynamic> dynamicPage) {
        //获取分页结果
        PageResult<DynamicVO> pageResult = new PageResult<>();
        pageResult.setRecords(list);
        pageResult.setPages((int) dynamicPage.getPages());
        pageResult.setTotal(dynamicPage.getTotal());
        return pageResult;
    }
    //封装动态信息

    private void buildDynamicVO(Dynamic dynamic, DynamicVO dynamicVO, Long userId) {

        BeanUtil.copyProperties(dynamic, dynamicVO);
        //查询发布者信息
        User user = userService.getById(dynamic.getUserId());
        Long commentNumber = dynamicCommentService.lambdaQuery().eq(DynamicComment::getDynamicId, dynamic.getId()).count();
        String key = DYNAMIC_LIKE_KEY + dynamic.getId();

        List<DynamicCommentVO> dynamicCommentVOList=new ArrayList<>();
        List<DynamicComment> commentList = dynamicCommentService.lambdaQuery().eq(DynamicComment::getDynamicId, dynamic.getId()).last("Limit 4").list();
        if(!commentList.isEmpty()){
            buildDynamicCommentVOList(dynamicCommentVOList,commentList);
            dynamicVO.setCommentList(dynamicCommentVOList);
        }

        //判断用户是否点赞过
        Boolean isMember = RedisTemplate.opsForSet().isMember(key, userId.toString());
        dynamicVO.setCommentNumber(commentNumber);
        dynamicVO.setAvatarurl(user.getAvatarurl());
        dynamicVO.setNickname(user.getNickname());
        dynamicVO.setYearsOld(user.getUserBirthday().until(LocalDate.now()).getYears());
        dynamicVO.setLiked(isMember);
        dynamicVO.setThumbs(dynamic.getThumbs());

    }
    private void buildDynamicCommentVOList(List<DynamicCommentVO> dynamicCommentVOList, List<DynamicComment> list) {
        list.stream().forEach(dynamicComment->{
            DynamicCommentVO commentVO = new DynamicCommentVO();
            BeanUtil.copyProperties(dynamicComment,commentVO);
            User user = userService.getById(dynamicComment.getUserId());
            commentVO.setAvatarurl(user.getAvatarurl());
            commentVO.setNickname(user.getNickname());
            dynamicCommentVOList.add(commentVO);
        });

    }
}




