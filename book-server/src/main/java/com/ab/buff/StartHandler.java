package com.ab.buff;

import com.ab.config.GameConifg;
import com.ab.entity.Puzzle;
import com.ab.exception.BaseException;
import com.ab.puzzle.service.PuzzleService;
import com.ab.websocket.game.GameWebSocketServer;
import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author cattleYuan
 * @date 2024/2/23
 */
@Slf4j
@Component("startHandler")
@RequiredArgsConstructor
public class StartHandler implements Buff ,Serializable {


    private Buff buffHandler;

    private final PuzzleService puzzleService;

    private final GameConifg gameConifg;
    @Override
    public Buff next(Buff buff) {
        buffHandler=buff;
        return buffHandler;
    }

    @Override
    public Player Handler(GameMessageDTO gameMessageDTO,Player player) {

        Puzzle puzzle = puzzleService.getById(gameMessageDTO.getPuzzleId());
        Optional.ofNullable(puzzle).orElseThrow(()-> new BaseException("不存在的谜题"));
        boolean flag = judgeAnswer(gameMessageDTO, puzzle);
        //执行积分处理逻辑
        if(flag){
            //正确,按题目星级数和连胜数加分
            player.setIntegral(player.getIntegral() + gameConifg.getPuzzleIntegral() * player.getStar()+player.getStillRightNumber());
            player.setStillRightNumber(player.getStillRightNumber()+1);
        }
        else {
            //错误题目星级数扣分
            player.setIntegral( player.getIntegral() - gameConifg.getPuzzleIntegral() * player.getStar());
            player.setStillRightNumber(0);

        }
        if(gameMessageDTO.getRanking()!=null)
        player.setRanking(gameMessageDTO.getRanking());

        player.setResult(flag);

        if(gameMessageDTO.getClueNumber()!=null)
        player.setClueNumber(gameMessageDTO.getClueNumber());

        if(gameMessageDTO.getBuffs()!=null)
        player.setBuffs(gameMessageDTO.getBuffs());

        player.setPuzzleNumber(player.getPuzzleNumber()+1);
        //题目星级数设置
        player.setStar(player.getPuzzleNumber()/5+1);
        log.info("玩家->{}进入buff流水线",player.getUserId());
        return Objects.isNull(buffHandler)? player:buffHandler.Handler(gameMessageDTO,player);
    }

    private boolean judgeAnswer(GameMessageDTO gameMessageDTO, Puzzle puzzle) {
        boolean flag =gameMessageDTO.getResult().equals(puzzle.getPuzzleAnswer());
        log.info("题目:{}->结果:{}",gameMessageDTO.getPuzzleId(),flag?"正确":"错误");
        return flag;
    }

}
