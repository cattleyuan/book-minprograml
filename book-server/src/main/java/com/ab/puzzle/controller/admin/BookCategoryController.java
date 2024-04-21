package com.ab.puzzle.controller.admin;

import com.ab.bcategory.domain.dto.BookCategoryDTO;
import com.ab.bcategory.domain.vo.BookCategoryVO;
import com.ab.bcategory.service.BookCategoryService;
import com.ab.book.domain.dto.BookSaveDTO;
import com.ab.book.domain.dto.BookUpdateDTO;
import com.ab.book.domain.dto.MBookQueryDTO;
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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/30
 */
@RestController("adminBookCateController")
@Slf4j
@Validated
@RequiredArgsConstructor
@Api(tags = "书籍分类相关接口")
@RequestMapping("/admin/bookcategory")
public class BookCategoryController {
    private final BookCategoryService categoryService;
    @GetMapping("/getcategoryInfo")
    @ApiOperation("获取所有书籍分类")
    @Cacheable(cacheNames="bookstrategy")
    public Result<List<BookCategoryVO>> getbookCategory(){
        List<BookCategoryVO> voList=categoryService.getAllBookCategory();
        return Result.success(voList);
    }

    @PostMapping("/add")
    @ApiOperation("新增书籍分类")
    @CacheEvict(cacheNames = "bookstrategy",allEntries = true)
    public Result addBookCategory(@RequestParam @NotNull String categoryName){
        categoryService.addBookCategory(categoryName);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除书籍分类")
    @CacheEvict(cacheNames = "bookstrategy",allEntries = true)
    public Result removeBookCategory(@RequestParam @NotEmpty List<Long> ids){
        categoryService.removeBanchBookCategory(ids);
        return Result.success();
    }

    @PutMapping("/update")
    @ApiOperation("更新书籍分类")
    @CacheEvict(cacheNames = "bookstrategy",allEntries = true)
    public Result updateBookCategory(@RequestBody @NotNull BookCategoryDTO bookCategoryDTO){
        categoryService.updateBookCategory(bookCategoryDTO);
        return Result.success();
    }
}
