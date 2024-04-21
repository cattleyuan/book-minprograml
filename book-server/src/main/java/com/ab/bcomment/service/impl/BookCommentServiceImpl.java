package com.ab.bcomment.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.bcomment.domain.BookComment;
import com.ab.bcomment.domain.dto.AddBookCommentDTO;
import com.ab.bcomment.domain.dto.BookCommentDTO;
import com.ab.bcomment.domain.vo.BookCommentVO;
import com.ab.bcomment.mapper.BookCommentMapper;
import com.ab.bcomment.service.BookCommentService;
import com.ab.context.BaseContext;
import com.ab.entity.User;
import com.ab.puzzle.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 86150
 * @description 针对表【book_comment】的数据库操作Service实现
 * @createDate 2024-02-25 11:50:59
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookCommentServiceImpl extends ServiceImpl<BookCommentMapper, BookComment>
        implements BookCommentService {
    private final UserService userService;
    @Override
    public List<BookCommentVO> getBookCommentOrRating(BookCommentDTO commentDTO) {

        List<BookComment> bookCommentList=lambdaQuery()
                .eq(commentDTO.getBookId()!=null,BookComment::getBookId, commentDTO.getBookId())
                .eq(commentDTO.getIsBookRating()!=null,BookComment::getIsBookRating,commentDTO.getIsBookRating())
                .eq(commentDTO.getParentId()!=null,BookComment::getParentId, commentDTO.getParentId())
                .orderByDesc(BookComment::getCommentTime)
                .list();

        List<BookCommentVO> bookCommentVOList = BeanUtil.copyToList(bookCommentList, BookCommentVO.class);
        //返回部分用户信息
        for (BookCommentVO bookCommentVO : bookCommentVOList) {

            User user = userService.getById(bookCommentVO.getUserId());
            bookCommentVO.setAvatarurl(user.getAvatarurl());
            bookCommentVO.setNickname(user.getNickname());
            bookCommentVO.setGender(user.getGender());
        }

        return bookCommentVOList;
    }

    @Override
    public void saveBookCommentOrRating(AddBookCommentDTO addBookCommentDTO) {
        Long userId = BaseContext.getCurrentId();
        BookComment bookComment = new BookComment();
        BeanUtil.copyProperties(addBookCommentDTO,bookComment);
        bookComment.setUserId(userId);
        bookComment.setCommentTime(LocalDateTime.now());
        save(bookComment);
    }

    @Override
    public void removeBookCommentOrRating(Long id) {
        Long userId = BaseContext.getCurrentId();
        BookComment bookComment = getById(id);
        if(bookComment.getUserId()!=userId){
            log.error("存在水平越权操作->{}",userId);
            return;
        }
        removeById(id);
    }
}




