package com.ab.bookshelf.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.book.domain.BookEntity;
import com.ab.book.domain.vo.UBookVO;
import com.ab.book.service.BookService;
import com.ab.bookshelf.domain.Bookshelf;
import com.ab.bookshelf.domain.dto.BookShelfDTO;
import com.ab.bookshelf.domain.vo.BookShelfVO;
import com.ab.bookshelf.mapper.BookshelfMapper;
import com.ab.bookshelf.service.BookshelfService;
import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* @author 86150
* @description 针对表【bookshelf】的数据库操作Service实现
* @createDate 2024-03-06 19:27:52
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class BookshelfServiceImpl extends ServiceImpl<BookshelfMapper, Bookshelf>
    implements BookshelfService {
    private final BookService bookService;

    @Override
    public List<BookShelfVO> getBookInShelf(BookShelfDTO bookShelfDTO) {
        Long userId = BaseContext.getCurrentId();
        //查询用户书架书籍
        List<Bookshelf> bookshelfList = lambdaQuery()
                .eq(bookShelfDTO.getBookCategories() != null, Bookshelf::getBookCategories, bookShelfDTO.getBookCategories())
                .orderBy(bookShelfDTO.getIsOrder()!=null,bookShelfDTO.getIsOrder()==1,Bookshelf::getEnterTime)
                .eq(Bookshelf::getUserId,userId)
                .list();
        List<BookShelfVO> bookShelfVOList = new ArrayList<>();
        //封装书籍详细信息
        for (Bookshelf bookshelf : bookshelfList) {
            //封装返回结果
            BookShelfVO bookShelfVO=buildBookShelfVO(bookshelf);
            //加入到书架结果集
            bookShelfVOList.add(bookShelfVO);
        }

        log.info("用户->{},书架查询成功->{}",userId,bookShelfVOList);
        return bookShelfVOList;
    }

    @Override
    public void addBookToShelf(Long bookId) {
        Long userId = BaseContext.getCurrentId();
        //构建bookshlef实体
        Bookshelf bookshelf=buildBookShelfEntity(bookId, userId);
        //保存到数据库
        save(bookshelf);
        log.info("用户->{},添加书籍->{}到书架成功",userId,bookId);
    }

    private Bookshelf buildBookShelfEntity(Long bookId, Long userId) {
        Bookshelf bookshelf = new Bookshelf();
        BookEntity bookEntity = bookService.getById(bookId);
        Optional.ofNullable(bookEntity).orElseThrow(()->new BaseException("添加书籍不存在!"));
        bookshelf.setBookId(bookId);
        bookshelf.setEnterTime(LocalDateTime.now());
        bookshelf.setUserId(userId);
        bookshelf.setBookCategories(bookEntity.getBookCategories());
        return bookshelf;
    }

    @Override
    public void deleteBanchBookFromShelf(List<Long> bookIds) {

        Long userId = BaseContext.getCurrentId();
        QueryWrapper<Bookshelf> queryWrapper = new QueryWrapper<Bookshelf>()
                .in("book_id", bookIds)
                .eq("user_id", userId);

        remove(queryWrapper);
        log.error("用户->{},书架书籍->{}-删除成功",userId,bookIds);
    }

    private BookShelfVO buildBookShelfVO(Bookshelf bookshelf) {
        BookShelfVO bookShelfVO = new BookShelfVO();
        UBookVO bookVO = new UBookVO();
        //查询书籍
        BookEntity bookEntity = bookService.lambdaQuery()
                .eq(bookshelf.getBookId()!=null,BookEntity::getId, bookshelf.getBookId())
                .one();
        //拷贝到返回值VO
        BeanUtil.copyProperties(bookEntity,bookVO);
        bookShelfVO.setBook(bookVO);
        bookShelfVO.setEnterTime(bookshelf.getEnterTime());
        return bookShelfVO;
    }
}




