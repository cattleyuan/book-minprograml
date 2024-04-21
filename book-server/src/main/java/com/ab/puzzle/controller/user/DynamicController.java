package com.ab.puzzle.controller.user;

import com.ab.dcomment.service.DynamicCommentService;
import com.ab.dynamic.domain.Dynamic;
import com.ab.dynamic.domain.dto.DynamicDTO;
import com.ab.dynamic.domain.dto.DynamicUpdateDTO;
import com.ab.dynamic.domain.dto.PageDynamicDTO;
import com.ab.dynamic.domain.vo.DynamicVO;
import com.ab.dynamic.service.DynamicService;
import com.ab.result.PageResult;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author cattleYuan
 * @date 2024/3/16
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "社区动态相关接口")
@RequestMapping("/user/dynamic")
public class DynamicController {
    private final DynamicService dynamicService;

    @PostMapping("/list")
    @ApiOperation("查询动态及发布人基本信息列表(0全部，1朋友,2自己)")
    public Result<PageResult<DynamicVO>> queryDynamicList(@RequestBody PageDynamicDTO pageDynamicDTO){
        PageResult<DynamicVO> pageResult = dynamicService.queryDynamicList(pageDynamicDTO);
        return Result.success(pageResult);
    }
    @PostMapping("/add")
    @ApiOperation("添加动态")
    public Result saveDynamic(@RequestBody DynamicDTO dynamicDTO){
        dynamicService.addDynamic(dynamicDTO);
        return Result.success();
    }

    @DeleteMapping ("/remove")
    @ApiOperation("删除动态")
    public Result removeDynamicById(@RequestParam @NotNull Long id){
        dynamicService.removeDynamicById(id);
        return Result.success();
    }

    @PutMapping ("/update")
    @ApiOperation("修改动态")
    public Result udpateDynamicById(@RequestBody  DynamicUpdateDTO dynamicUpdateDTO){
        dynamicService.udpateDynamicById(dynamicUpdateDTO);
        return Result.success();
    }

    @PutMapping("/addlike")
    @ApiOperation("给动态点赞或取消点赞")
    public Result updateThumbsDynamicById(@RequestParam @NotNull Long id){
        dynamicService.updateThumbsDynamicById(id);
        return Result.success();
    }

    @GetMapping("/get")
    @ApiOperation("获取动态点赞数")
    public Result<Integer> getDynamicCTNumber(@RequestParam @NotNull Long id){
        Integer num=dynamicService.getDynamicCTNumber(id);
        log.info("动态->{},点赞数->{}",id,num);
        return Result.success(num);
    }


}
