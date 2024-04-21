package com.ab.bcategory.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.bcategory.domain.BookCategory;
import com.ab.bcategory.domain.dto.BookCategoryDTO;
import com.ab.bcategory.domain.vo.BookCategoryVO;
import com.ab.bcategory.mapper.BookCategoryMapper;
import com.ab.bcategory.service.BookCategoryService;
import com.ab.book.domain.BookEntity;
import com.ab.book.service.BookService;
import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author 86150
 * @description 针对表【book_category】的数据库操作Service实现
 * @createDate 2024-02-01 18:07:44
 */
@Service
@Slf4j
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory>
        implements BookCategoryService {
    @Autowired
    private BookService bookService;
    @Override
    public List<BookCategoryVO> getAllBookCategory() {

        List<BookCategory> bookCategories = lambdaQuery().list();
        Optional.ofNullable(bookCategories).orElseThrow(()-> new BaseException("无书籍分类数据"));
        List<BookCategoryVO> voList = BeanUtil.copyToList(bookCategories, BookCategoryVO.class);

        return voList;
    }

    @Override
    public void addBookCategory(String categoryName) {

        BookCategory category = lambdaQuery().eq(BookCategory::getCategoryName, categoryName).one();
        if(Optional.ofNullable(category).isPresent()){
            throw new BaseException("分类名不能重复");
        }

        Long adminId = BaseContext.getCurrentId();

        saveNewBCategory(categoryName, adminId);
        log.info("添加书籍分类成功->{}",categoryName);
    }

    @Override
    public void removeBanchBookCategory(List<Long> ids) {

        List<Long> list = ids.stream().filter(id -> bookService.lambdaQuery().eq(BookEntity::getBookCategories,id).list().size()==0)
                .collect(Collectors.toList());

        if(list.size()!=ids.size()){
            throw new BaseException("删除分类中包含书籍信息");
        }

        removeBatchByIds(ids);
        log.info("删除书籍分类成功->{}",ids);
    }

    @Override
    public void updateBookCategory(BookCategoryDTO bookCategoryDTO) {
        Long adminerId = BaseContext.getCurrentId();
        Optional.ofNullable(getById(bookCategoryDTO.getId())).orElseThrow(()->new BaseException("该商品分类已被删除，无法更新"));

        BookCategory bookCategory = new BookCategory();
        BeanUtil.copyProperties(bookCategoryDTO,bookCategory);
        bookCategory.setUpdateAdminer(adminerId);
        updateById(bookCategory);

        log.info("更新书籍分类成功->{}",bookCategoryDTO.getCategoryName());
    }

    private void saveNewBCategory(String categoryName, Long adminId) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setCategoryName(categoryName);
        bookCategory.setCreateAdminer(adminId);
        bookCategory.setUpdateAdminer(adminId);
        save(bookCategory);
    }
}




