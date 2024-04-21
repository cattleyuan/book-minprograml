package com.ab.buff;

import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author cattleYuan
 * @date 2024/2/24
 */
@Component("addClue")
@Slf4j
public class AddClueNumberBuff implements Buff, Serializable {
    private Buff buffHandler;
    @Override
    public Buff next(Buff buff) {
        buffHandler=buff;
        return buffHandler;
    }

    @Override
    public Player Handler(GameMessageDTO gameMessageDTO, Player player) {
        //一次性buff销毁
        player.setClueNumber(player.getClueNumber()+3);
        log.info("增加答题线索buff生效");
        player.getBuffs().removeIf(data->data.equals("addClue"));
        return Objects.isNull(buffHandler)? player:buffHandler.Handler(gameMessageDTO,player);
    }
}
