package com.ab.puzzle.controller.user;

import com.ab.context.BaseContext;
import com.ab.dto.*;
import com.ab.entity.Puzzle;
import com.ab.result.PageResult;
import com.ab.result.Result;
import com.ab.puzzle.service.PuzzleService;
import com.ab.vo.PuzzleDetailedVO;
import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.vo.GameMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userPuzzleController")
@RequestMapping("/user/puzzle")
@RequiredArgsConstructor
@Api(tags = "谜题类接口")
public class PuzzleController {


    private final PuzzleService puzzleService;

    @ApiOperation("新增单个谜题")
    @PostMapping("/")
    public Result savePuzzle(@RequestBody PuzzleDTO puzzleDTO) {
        puzzleService.savePuzzleByUser(puzzleDTO);
        return Result.success();
    }

    @ApiOperation("修改谜题信息")
    @CacheEvict(cacheNames = "CacheDetailPuzzle",key = "#puzzleUpdateDTO.id")
    @PutMapping("/update")
    public Result updatePuzzle(@RequestBody PuzzleUpdateDTO puzzleUpdateDTO) {
        puzzleService.updatePuzzle(puzzleUpdateDTO);
        return Result.success();
    }

    @ApiOperation("批量删除谜题")
    @CacheEvict(cacheNames = "CacheDetailPuzzle", allEntries = true)
    @DeleteMapping("/")
    public Result removePuzzleByIds(@RequestBody List<Long> ids) {
        puzzleService.removePuzzle(ids);
        return Result.success();
    }

    @ApiOperation("条件展示简略谜题信息集合")
    @PostMapping ("/list")
    public Result<List<Puzzle>> listPuzzle(@RequestBody PuzzleQueryDTO puzzleQueryDTO){
        List<Puzzle> voList=puzzleService.listPuzzleByCondition(puzzleQueryDTO);
        return Result.success(voList);
    }

    @ApiOperation("联网验证玩家答题信息")
    @PostMapping("/judge")
    public Result<GameMessageVO> judgePuzzleOnline(@RequestBody GameMessageDTO gameMessageDTO){
        GameMessageVO gameMessageVO=puzzleService.judgePuzzleOnline(gameMessageDTO);
        return Result.success(gameMessageVO);
    }

    @ApiOperation("单机验证玩家答题信息")
    @PostMapping("/answer")
    public Result<Boolean> judgePuzzle(@RequestBody PuzzleGameDTO puzzleGameDTO){
        boolean flag=puzzleService.judgePuzzleSingle(puzzleGameDTO);
        return Result.success(flag);
    }

    @ApiOperation("回显玩家所出简略谜题信息")
    @GetMapping("/list")
    public Result<List<Puzzle>> listPuzzleInUser() {
        Long id= BaseContext.getCurrentId();
        List<Puzzle> puzzleList = puzzleService.listPuzzleByUserId(id);
        return Result.success(puzzleList);
    }


    @ApiOperation("展示更多热门谜题")
    @GetMapping("/show")
    public Result<List<Puzzle>> showMoreHotPuzzle(){
        List<Puzzle> puzzleList=puzzleService.getMoreHotPuzzle();
        return Result.success(puzzleList);
    }

    @ApiOperation("根据谜题id展示详细谜题信息")
    @Cacheable(cacheNames = "CacheDetailPuzzle",key = "#id")
    @GetMapping ("/list/{id}")
    public Result<PuzzleDetailedVO> listPuzzle(@PathVariable Long id){

        PuzzleDetailedVO puzzleDetailedVO=puzzleService.listOnePuzzle(id);
        return Result.success(puzzleDetailedVO);
    }

}