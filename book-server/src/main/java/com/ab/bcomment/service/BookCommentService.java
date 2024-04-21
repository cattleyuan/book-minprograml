package com.ab.bcomment.service;


import com.ab.bcomment.domain.BookComment;
import com.ab.bcomment.domain.dto.AddBookCommentDTO;
import com.ab.bcomment.domain.dto.BookCommentDTO;
import com.ab.bcomment.domain.vo.BookCommentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【book_comment】的数据库操作Service
* @createDate 2024-02-25 11:50:59
*/
public interface BookCommentService extends IService<BookComment> {

    List<BookCommentVO> getBookCommentOrRating(BookCommentDTO bookCommentDTO);

    void saveBookCommentOrRating(AddBookCommentDTO addBookCommentDTO);

    void removeBookCommentOrRating(Long id);
}
