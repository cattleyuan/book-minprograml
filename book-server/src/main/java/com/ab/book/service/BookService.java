package com.ab.book.service;


import com.ab.book.domain.BookEntity;
import com.ab.book.domain.dto.MBookQueryDTO;
import com.ab.book.domain.dto.BookSaveDTO;
import com.ab.book.domain.dto.BookUpdateDTO;
import com.ab.book.domain.dto.UBookQueryDTO;
import com.ab.book.domain.vo.MBookVO;
import com.ab.book.domain.vo.UBookVO;
import com.ab.result.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【book】的数据库操作Service
* @createDate 2024-02-01 13:56:21
*/
public interface BookService extends IService<BookEntity> {

    PageResult<MBookVO> getBookInfoWithPage(MBookQueryDTO MBookQueryDTO);

    void addBookInfo(BookSaveDTO bookSaveDTO);

    void updateBookInfo(BookUpdateDTO bookUpdateDTO);

    List<UBookVO> getBookInfoWithList(UBookQueryDTO queryDTO);
}
