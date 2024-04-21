package com.ab.puzzle.controller.user;

import com.ab.gamehistory.domain.vo.GameHistoryVO;
import com.ab.gamehistory.service.GameHistoryService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/2/28
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "游戏历史记录相关接口")
public class GameHistoryController  {
    private final GameHistoryService gameHistoryService;
    @GetMapping("/")
    @ApiOperation("获取玩家游戏历史记录")
    public Result<List<GameHistoryVO>> getGameHistory(){
        List<GameHistoryVO> gameHistoryVOList=gameHistoryService.getGameHistory();
        return Result.success(gameHistoryVOList);
    }

}
