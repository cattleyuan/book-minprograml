package com.ab.puzzle.controller.user;

import com.ab.book.domain.dto.UBookQueryDTO;
import com.ab.book.domain.vo.UBookVO;
import com.ab.book.service.BookService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/30
 */
@RestController("userBookController")
@Slf4j
@Validated
@RequiredArgsConstructor
@Api(tags = "书籍相关接口")
@RequestMapping("/user/book")
public class BookController {
    private final BookService bookService;
    @PostMapping("/getbookInfo")
    @ApiOperation("条件获取书籍列表")
    public Result<List<UBookVO>> getBookInfo(@RequestBody UBookQueryDTO queryDTO){
        List<UBookVO> voList=bookService.getBookInfoWithList(queryDTO);
        return Result.success(voList);
    }

}
