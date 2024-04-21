package com.ab.puzzle.service;

import com.ab.dto.*;
import com.ab.entity.Puzzle;
import com.ab.result.PageResult;
import com.ab.vo.PuzzleDetailedVO;
import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.vo.GameMessageVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86150
* @description 针对表【puzzle(谜题表)】的数据库操作Service
* @createDate 2023-12-07 19:55:18
*/

public interface PuzzleService extends IService<Puzzle> {

    void savePuzzle(PuzzleDetailedDTO puzzleDetailedDTO);

    void removePuzzle(List<Long> ids);

    void saveBlanchPuzzle(List<PuzzleDetailedDTO> puzzleDetailedDTOList);

    void updatePuzzle(PuzzleUpdateDTO puzzleUpdateDTO);

    void updateStatus(List<Long> ids, Integer status);

    PageResult<Puzzle> listPuzzleWithPage(PuzzleQueryPageDTO puzzleQueryPageDTO);

    PuzzleDetailedVO listOnePuzzle(Long id);

    void savePuzzleByUser(PuzzleDTO puzzleDTO);


    List<Puzzle> listPuzzleByUserId(Long id);

    List<Puzzle> getMoreHotPuzzle();

    List<Puzzle> listPuzzleByCondition(PuzzleQueryDTO puzzleQueryDTO);

    GameMessageVO judgePuzzleOnline(GameMessageDTO gameMessageDTO);

    boolean judgePuzzleSingle(PuzzleGameDTO puzzleGameDTO);
}
