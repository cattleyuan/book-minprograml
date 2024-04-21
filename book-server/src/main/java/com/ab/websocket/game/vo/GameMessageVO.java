package com.ab.websocket.game.vo;

import com.ab.websocket.game.Player;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/2/23
 */
@Data
public class GameMessageVO implements Serializable {

    //玩家答题信息
    private Player player;

    //0非重连，1请求重连数据
    @ApiModelProperty("0非重连，1请求重连")
    private boolean reConnect;

    public GameMessageVO(Player player,boolean reConnect){
        this.player=player;
        this.reConnect=reConnect;
    }
}
