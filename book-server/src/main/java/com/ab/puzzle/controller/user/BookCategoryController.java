package com.ab.puzzle.controller.user;

import com.ab.bcategory.domain.vo.BookCategoryVO;
import com.ab.bcategory.service.BookCategoryService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/30
 */
@RestController("userBookCategoryController")
@Slf4j
@Validated
@RequiredArgsConstructor
@Api(tags = "书籍分类相关接口")
@RequestMapping("/user/bookcategory")
public class BookCategoryController {

    private final BookCategoryService categoryService;

    @GetMapping("/getcategoryInfo")
    @ApiOperation("获取所有书籍分类")
    public Result<List<BookCategoryVO>> getbookCategory(){
        List<BookCategoryVO> voList=categoryService.getAllBookCategory();
        return Result.success(voList);
    }
}
