package com.ab.dcomment.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.context.BaseContext;
import com.ab.dcomment.domain.DynamicComment;
import com.ab.dcomment.domain.dto.DynamicCommentDTO;
import com.ab.dcomment.domain.vo.DynamicCommentVO;
import com.ab.dcomment.mapper.DynamicCommentMapper;
import com.ab.dcomment.service.DynamicCommentService;
import com.ab.entity.User;
import com.ab.exception.BaseException;
import com.ab.puzzle.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
* @author 86150
* @description 针对表【dynamic_comment】的数据库操作Service实现
* @createDate 2024-03-16 11:22:06
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class DynamicCommentServiceImpl extends ServiceImpl<DynamicCommentMapper, DynamicComment>
    implements DynamicCommentService {
    private final UserService userService;
    @Override
    public void addDyComment(DynamicCommentDTO commentDTO) {
        Long userId = BaseContext.getCurrentId();
        DynamicComment dynamicComment = new DynamicComment();
        BeanUtil.copyProperties(commentDTO,dynamicComment);
        dynamicComment.setUserId(userId);
        dynamicComment.setCommentTime(LocalDateTime.now());
        boolean isSuccess = save(dynamicComment);
        if(!isSuccess){
            throw new BaseException("保存失败");
        }
    }

    @Override
    public List<DynamicCommentVO> queryAllDyComment(Long id) {
        List<DynamicCommentVO> dynamicCommentVOList=new ArrayList<>();
        //查询动态评论信息
        List<DynamicComment> list = lambdaQuery()
                .eq(id != null, DynamicComment::getDynamicId, id).list();
        //查询评论人基本信息
        buildDynamicCommentVOList(dynamicCommentVOList, list);

        return dynamicCommentVOList;
    }

    public void buildDynamicCommentVOList(List<DynamicCommentVO> dynamicCommentVOList, List<DynamicComment> list) {
        for (DynamicComment dynamicComment : list) {
            DynamicCommentVO commentVO = new DynamicCommentVO();
            BeanUtil.copyProperties(dynamicComment,commentVO);
            User user = userService.getById(dynamicComment.getUserId());
            commentVO.setAvatarurl(user.getAvatarurl());
            commentVO.setNickname(user.getNickname());
            dynamicCommentVOList.add(commentVO);
        }
    }

    @Override
    public Integer getNumberOfDComment(Long id) {

        return Math.toIntExact(lambdaQuery().eq(DynamicComment::getDynamicId, id).count());
    }
}




