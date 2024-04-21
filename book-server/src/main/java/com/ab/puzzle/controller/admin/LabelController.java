package com.ab.puzzle.controller.admin;


import com.ab.dto.LabelDTO;
import com.ab.entity.Label;
import com.ab.result.Result;
import com.ab.puzzle.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "官方标签类接口")
@RequestMapping("/admin/label")
public class LabelController {
    private final LabelService labelService;
    @GetMapping("/")
    @ApiOperation("展示所有官方标签")
    @Cacheable(cacheNames = "booklabel")
    public Result<List<Label>> listAll(){
        List<Label> labelList = labelService.list();
        return Result.success(labelList);
    }
    @PutMapping("/")
    @ApiOperation("修改单个官方标签")
    @CacheEvict(cacheNames = "booklabel")
    public Result updateLabel(@RequestBody LabelDTO labelDTO){

        labelService.updateLabelById(labelDTO);
        return Result.success();
    }
    @PutMapping("/save")
    @ApiOperation("批量新增官方标签")
    @CacheEvict(cacheNames = "booklabel")
    public Result saveBatchLabel(@RequestBody List<LabelDTO> labelDTOList){

        labelService.saveBatchLabel(labelDTOList);
        return Result.success();
    }
    @DeleteMapping("/")
    @ApiOperation("批量删除官方标签")
    @CacheEvict(cacheNames = "booklabel")
    public Result deleteBatchLabel(@RequestBody List<Long> ids){

        labelService.removeBatchByIds(ids);
        return Result.success();
    }

}
