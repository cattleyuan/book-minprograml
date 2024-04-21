package com.ab.puzzle.controller.user;

import com.ab.dcomment.domain.DynamicComment;
import com.ab.dcomment.domain.dto.DynamicCommentDTO;
import com.ab.dcomment.domain.vo.DynamicCommentVO;
import com.ab.dcomment.service.DynamicCommentService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/3/16
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "社区动态评论相关接口")
@RequestMapping("/dycomment")
public class DynamicCommentController {
    private final DynamicCommentService dynamicCommentService;
    @GetMapping("/list")
    @ApiOperation("获取动态所有评论")
    public Result<List<DynamicCommentVO>> queryAllDyComment(@RequestParam Long id){

        List<DynamicCommentVO> list=dynamicCommentService.queryAllDyComment(id);
        return Result.success(list);
    }

    @PutMapping("/add")
    @ApiOperation("新增评论")
    public Result addDyComment(@RequestBody DynamicCommentDTO commentDTO){
        dynamicCommentService.addDyComment(commentDTO);
        return Result.success();
    }

    @PutMapping("/remove")
    @ApiOperation("删除评论")
    public Result removeDyComment(@RequestParam Long id){
        boolean isSuccess = dynamicCommentService.removeById(id);
        if(!isSuccess)
            return Result.error("删除评论失败");
        return Result.success();
    }

    @GetMapping("/get")
    @ApiOperation("获取动态评论数")
    public Result<Integer> getNumberOfDComment(@RequestParam Long id){
        Integer num=dynamicCommentService.getNumberOfDComment(id);
        log.info("动态->{},评论数->{}",id,num);
        return Result.success(num);
    }
}
