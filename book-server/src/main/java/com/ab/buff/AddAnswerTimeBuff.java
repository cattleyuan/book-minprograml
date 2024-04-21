package com.ab.buff;

import com.ab.config.GameConifg;
import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author cattleYuan
 * @date 2024/2/23
 */
@Component("addTime")
@Slf4j
public class AddAnswerTimeBuff implements Buff,Serializable {
    private Buff buffHandler;
    @Autowired
    private GameConifg gameConifg;
    private final Integer addTime=2;
    @Override
    public Buff next(Buff buff) {
        buffHandler=buff;
        return buffHandler;
    }

    @Override
    public Player Handler(GameMessageDTO gameMessageDTO, Player player) {

        if(player.getNextAnswerTime()!=null){
            player.setNextAnswerTime(player.getNextAnswerTime()-addTime);
        }
        else {
            player.setNextAnswerTime(gameConifg.getAnswerTime()-addTime);
        }
        log.info("增加答题时间buff生效");
        return Objects.isNull(buffHandler)? player:buffHandler.Handler(gameMessageDTO,player);
    }
}
