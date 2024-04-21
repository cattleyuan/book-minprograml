package com.ab.book.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.bcategory.service.BookCategoryService;
import com.ab.book.domain.BookEntity;
import com.ab.book.domain.dto.MBookQueryDTO;
import com.ab.book.domain.dto.BookSaveDTO;
import com.ab.book.domain.dto.BookUpdateDTO;
import com.ab.book.domain.dto.UBookQueryDTO;
import com.ab.book.domain.vo.MBookVO;
import com.ab.book.domain.vo.UBookVO;
import com.ab.book.mapper.BookMapper;
import com.ab.book.service.BookService;
import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.ab.result.PageResult;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author 86150
 * @description 针对表【book】的数据库操作Service实现
 * @createDate 2024-02-01 13:56:21
 */
@Service
@Slf4j

public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity>
        implements BookService {
    @Autowired
    private BookCategoryService bookCategoryService;
    @Override
    public PageResult<MBookVO> getBookInfoWithPage(MBookQueryDTO MBookQueryDTO) {
        Page<BookEntity> page = new Page<>(MBookQueryDTO.getPageNo(), MBookQueryDTO.getPageSize());
        //添加排序字段
        Map<String, Integer> orderItem = MBookQueryDTO.getOrderItem();
        if (orderItem != null && orderItem.size() > 0) {
            Set<Map.Entry<String, Integer>> entries = orderItem.entrySet();
            for (Map.Entry<String, Integer> entry : entries) {
                page = page.addOrder(new OrderItem(entry.getKey(), entry.getValue() == 1));
            }
        }
        //查询数据
        Page<BookEntity> BookEyResult = lambdaQuery().like(MBookQueryDTO.getBookName() != null, BookEntity::getBookName, MBookQueryDTO.getBookName())
                .eq(MBookQueryDTO.getBookCategories() != null, BookEntity::getBookCategories, MBookQueryDTO.getBookCategories())
                .eq(MBookQueryDTO.getPermission() != null, BookEntity::getPermission, MBookQueryDTO.getPermission())
                .eq(MBookQueryDTO.getStatus() != null, BookEntity::getStatus, MBookQueryDTO.getStatus())
                .page(page);
        List<BookEntity> records = BookEyResult.getRecords();

        Optional.ofNullable(records).orElseThrow(() -> new BaseException("未查到书籍数据"));
        //包装
        List<MBookVO> MBookVOList = BeanUtil.copyToList(records, MBookVO.class);
        PageResult<MBookVO> PageResult = new PageResult<>();
        PageResult.setPages((int) BookEyResult.getPages());
        PageResult.setTotal(BookEyResult.getTotal());
        PageResult.setRecords(MBookVOList);
        return PageResult;
    }

    @Override
    public void addBookInfo(BookSaveDTO bookSaveDTO) {
        Optional.ofNullable(bookCategoryService.getById(bookSaveDTO.getBookCategories())).orElseThrow(()->new BaseException("该书籍分类不存在"));
        Long adminId = BaseContext.getCurrentId();
        BookEntity book = new BookEntity();
        BeanUtil.copyProperties(bookSaveDTO, book);
        book.setCreateAdminer(adminId);
        book.setUpdateAdminer(adminId);
        save(book);
        log.info("新增书籍成功");

    }

    @Override
    public void updateBookInfo(BookUpdateDTO bookUpdateDTO) {
        Long adminId = BaseContext.getCurrentId();
        BookEntity book = new BookEntity();
        BeanUtil.copyProperties(bookUpdateDTO, book);
        book.setUpdateAdminer(adminId);
        updateById(book);
        log.info("更新成功");
    }

    @Override
    public List<UBookVO> getBookInfoWithList(UBookQueryDTO queryDTO) {

        List<BookEntity> bookList = lambdaQuery().eq(queryDTO.getBookCategories() != null, BookEntity::getBookCategories, queryDTO.getBookCategories())
                .eq(queryDTO.getPermission() != null, BookEntity::getPermission, queryDTO.getPermission())
                .like(queryDTO.getBookName() != null, BookEntity::getBookName, queryDTO.getBookName())
                .orderByDesc(BookEntity::getDisplayOrder).list();
        List<UBookVO> voList = BeanUtil.copyToList(bookList, UBookVO.class);

        return voList;
    }
}




