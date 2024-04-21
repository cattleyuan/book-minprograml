package com.ab.bookshelf.service;

import com.ab.bookshelf.domain.Bookshelf;
import com.ab.bookshelf.domain.dto.BookShelfDTO;
import com.ab.bookshelf.domain.vo.BookShelfVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【bookshelf】的数据库操作Service
* @createDate 2024-03-06 19:27:52
*/
public interface BookshelfService extends IService<Bookshelf> {

    List<BookShelfVO> getBookInShelf(BookShelfDTO bookShelfDTO);

    void addBookToShelf(Long bookId);

    void deleteBanchBookFromShelf(List<Long> bookIds);
}
