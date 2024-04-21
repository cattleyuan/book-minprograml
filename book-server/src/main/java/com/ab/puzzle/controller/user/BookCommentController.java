package com.ab.puzzle.controller.user;

import com.ab.bcomment.domain.dto.AddBookCommentDTO;
import com.ab.bcomment.domain.dto.BookCommentDTO;
import com.ab.bcomment.domain.vo.BookCommentVO;
import com.ab.bcomment.service.BookCommentService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/2/25
 */
@RestController
@RequestMapping("/user/bookcomment")
@Slf4j
@RequiredArgsConstructor
@Api(tags = "书籍评论和书评相关接口")
public class BookCommentController {
    private final BookCommentService bookCommentService;
    @PostMapping("/get")
    @ApiOperation("获取书籍评论或评价")
    public Result<List<BookCommentVO>> getBookCommentOrRating(@RequestBody BookCommentDTO commentDTO){
        List<BookCommentVO> bookCommentVOList=bookCommentService.getBookCommentOrRating(commentDTO);
        return Result.success(bookCommentVOList);
    }

    @PostMapping("/")
    @ApiOperation("新增书评或评论")
    public Result saveBookCommentOrRating(@RequestBody AddBookCommentDTO addBookCommentDTO){
            bookCommentService.saveBookCommentOrRating(addBookCommentDTO);
        return Result.success();
    }

    @DeleteMapping("/")
    @ApiOperation("删除书评或评论")
    public Result removeBookCommentOrRating(@RequestParam Long id){
            bookCommentService.removeBookCommentOrRating(id);
        return Result.success();
    }

}
