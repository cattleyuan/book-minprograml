package com.ab.puzzle.controller.user;

import com.ab.bookshelf.domain.dto.BookShelfDTO;
import com.ab.bookshelf.domain.vo.BookShelfVO;
import com.ab.bookshelf.service.BookshelfService;
import com.ab.context.BaseContext;
import com.ab.partner.domain.dto.AddPartnerDTO;
import com.ab.partner.domain.dto.UpdateRemarkDTO;
import com.ab.partner.domain.vo.SimpleUserVO;
import com.ab.partner.domain.vo.UserVO;
import com.ab.partner.service.PartnerService;
import com.ab.puzzle.service.UserService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags="书架相关接口")
@RequestMapping("/user/bookshelf")
@Validated
public class BookshelfController {
    private final BookshelfService bookshelfService;
    @PostMapping("/list")
    @ApiOperation("条件查询用户书架中书籍")
    public Result<List<BookShelfVO>> getBookInShelf(@RequestBody BookShelfDTO bookShelfDTO){

        List<BookShelfVO> bookShelfVOList= bookshelfService.getBookInShelf(bookShelfDTO);
        return Result.success(bookShelfVOList);
    }

    @PostMapping("/add")
    @ApiOperation("添加书籍到书架")
    public Result addBookToShelf(@RequestParam Long bookId){

        bookshelfService.addBookToShelf(bookId);
        return Result.success();
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量将书籍从书架移除")
    public Result addBookToShelf(@RequestParam List<Long> bookIds){

        bookshelfService.deleteBanchBookFromShelf(bookIds);
        return Result.success();
    }


}
