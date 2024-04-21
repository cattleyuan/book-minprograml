package com.ab.puzzle.controller.admin;

import com.ab.book.domain.dto.MBookQueryDTO;
import com.ab.book.domain.dto.BookSaveDTO;
import com.ab.book.domain.dto.BookUpdateDTO;
import com.ab.book.domain.vo.MBookVO;
import com.ab.book.service.BookService;
import com.ab.result.PageResult;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/30
 */
@RestController("adminBookController")
@Slf4j
@Validated
@RequiredArgsConstructor
@Api(tags = "书籍相关接口")
@RequestMapping("/admin/book")
public class BookController {
    private final BookService bookService;
    @PostMapping("/getbookInfo")
    @ApiOperation("分页条件展示书籍列表")
    public PageResult<MBookVO> getBookInfo(@RequestBody MBookQueryDTO MBookQueryDTO){
        PageResult<MBookVO> pageResult=bookService.getBookInfoWithPage(MBookQueryDTO);
        return pageResult;
    }

    @PostMapping("/add")
    @ApiOperation("添加书籍")
    public Result addBook(@NotNull @RequestBody BookSaveDTO bookSaveDTO){
        bookService.addBookInfo(bookSaveDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除书籍")
    public Result deleteBookInfo(@RequestParam List<Long> ids){
        bookService.removeBatchByIds(ids);
        log.info("删除成功");
        return Result.success();
    }

    @PutMapping("/update")
    @ApiOperation("修改书籍信息")
    public Result updateBookInfo(@RequestBody BookUpdateDTO bookUpdateDTO){
        bookService.updateBookInfo(bookUpdateDTO);
        return Result.success();
    }
}
