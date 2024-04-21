package com.ab.bcategory.service;


import com.ab.bcategory.domain.BookCategory;
import com.ab.bcategory.domain.dto.BookCategoryDTO;
import com.ab.bcategory.domain.vo.BookCategoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【book_category】的数据库操作Service
* @createDate 2024-02-01 18:07:44
*/
public interface BookCategoryService extends IService<BookCategory> {

    List<BookCategoryVO> getAllBookCategory();

    void addBookCategory(String categoryName);

    void removeBanchBookCategory(List<Long> ids);

    void updateBookCategory(BookCategoryDTO bookCategoryDTO);
}
