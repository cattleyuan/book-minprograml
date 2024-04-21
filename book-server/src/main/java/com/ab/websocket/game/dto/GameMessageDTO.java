package com.ab.websocket.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/2/23
 */
@Data
public class GameMessageDTO implements Serializable {
    //玩家id
    private Long userId;

    //谜题id
    @ApiModelProperty("谜题id")
    private Long puzzleId;

    //排名
    @ApiModelProperty("玩家排名")
    private Integer ranking;
    //玩家答题结果
    @ApiModelProperty("玩家答题结果")
    private String result;

    //玩家buff集合,重连也需要
    @ApiModelProperty("玩家buff集合")
    private List<String> buffs;

    //玩家当前所剩余提示数
    @ApiModelProperty("玩家当前所剩余提示数")
    private Integer clueNumber;

    //玩家是否获胜(0进行中,1失败,2胜利)
    @ApiModelProperty("0进行中,1失败,2胜利")
    private Integer isSuccess;
}
