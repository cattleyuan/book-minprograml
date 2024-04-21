package com.ab.puzzle.controller.admin;

import com.ab.dto.PuzzleDetailedDTO;
import com.ab.dto.PuzzleQueryPageDTO;
import com.ab.dto.PuzzleUpdateDTO;
import com.ab.entity.Puzzle;
import com.ab.result.PageResult;
import com.ab.result.Result;
import com.ab.puzzle.service.PuzzleService;
import com.ab.vo.PuzzleDetailedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminPuzzleController")
@RequestMapping("/admin/puzzle")
@Api(tags = "谜题类接口")
public class PuzzleController {

    @Autowired
    private PuzzleService puzzleService;

    @ApiOperation("新增单个谜题")
    @CacheEvict(cacheNames ={"CacheDetailPuzzle"},allEntries = true )
    @PutMapping ("/")
    public Result savePuzzle(@RequestBody PuzzleDetailedDTO puzzleDetailedDTO){
        puzzleService.savePuzzle(puzzleDetailedDTO);
        return Result.success();
    }

    @ApiOperation("批量新增谜题")
    @CacheEvict(cacheNames ={"CacheDetailPuzzle"},allEntries = true )
    @PutMapping("/save")
    public Result saveBlanchPuzzle(@RequestBody List<PuzzleDetailedDTO> puzzleDetailedDTOList){
        puzzleService.saveBlanchPuzzle(puzzleDetailedDTOList);
        return Result.success();
    }

    @ApiOperation("批量删除谜题")
    @CacheEvict(cacheNames ={"CacheDetailPuzzle"},allEntries = true )
    @DeleteMapping("/")
    public Result removePuzzleByIds(@RequestBody List<Long> ids){
        puzzleService.removePuzzle(ids);
        return Result.success();
    }

    @ApiOperation("修改谜题信息")
    @CacheEvict(cacheNames ={"CacheDetailPuzzle"},allEntries = true )
    @PutMapping("/update")
    public Result updatePuzzle(@RequestBody PuzzleUpdateDTO puzzleUpdateDTO){
        puzzleService.updatePuzzle(puzzleUpdateDTO);
        return Result.success();
    }

    @ApiOperation("分页条件展示简略谜题信息")
    @PostMapping ("/page")
    public Result<PageResult<Puzzle>> listPuzzle(@RequestBody PuzzleQueryPageDTO puzzleQueryPageDTO){
        PageResult<Puzzle> pageResult=puzzleService.listPuzzleWithPage(puzzleQueryPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("根据谜题id展示详细谜题信息")
    @Cacheable(cacheNames = "CacheDetailPuzzle",key = "#id")
    @GetMapping ("/list/{id}")
    public Result<PuzzleDetailedVO> listPuzzle(@PathVariable Long id){
        PuzzleDetailedVO puzzleDetailedVO=puzzleService.listOnePuzzle(id);
        return Result.success(puzzleDetailedVO);
    }

    @ApiOperation("批量审核谜题")
    @GetMapping ("/judge/{status}")
    public Result judgePuzzle(@RequestBody List<Long> ids,@PathVariable Integer status){
        puzzleService.updateStatus(ids,status);
        return Result.success();
    }

}
