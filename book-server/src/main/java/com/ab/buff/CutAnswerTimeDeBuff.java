package com.ab.buff;

import com.ab.config.GameConifg;
import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author cattleYuan
 * @date 2024/2/23
 */

@Component("cutTime")
@RequiredArgsConstructor
@Slf4j
public class CutAnswerTimeDeBuff implements Buff, Serializable {
    private Buff buffHandler;

    private final GameConifg gameConifg;
    private final RedisTemplate redisTemplate;
    //减少时间
    private final Integer cutTime=2;
    //
    @Override
    public Buff next(Buff buff) {
        buffHandler=buff;
        return buffHandler;
    }

    @Override
    public Player Handler(GameMessageDTO gameMessageDTO,Player player) {
        if(player.getNextAnswerTime()!=null){
            player.setNextAnswerTime(player.getNextAnswerTime()-gameConifg.getAnswerTime());
        }
        else {
            player.setNextAnswerTime(gameConifg.getAnswerTime()-cutTime);
        }

        log.info("减少答题时间buff生效");
        return Objects.isNull(buffHandler)? player:buffHandler.Handler(gameMessageDTO,player);
    }
}
