package com.ab.websocket.game;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/2/23
 */
@Data
public class Player implements Serializable {

    //玩家id
    @ApiModelProperty("玩家id")
    private String userId;
    //匹配到的人的id
    @ApiModelProperty("匹配到的人的id")
    private String matcherId;

    @ApiModelProperty("当前回答谜题id")
    //当前回答谜题id
    private Long puzzleId;
    //玩家当前所获积分
    @ApiModelProperty("玩家当前所获积分")
    private Integer integral=40;

    //玩家当前答题连对数
    @ApiModelProperty("玩家当前答题最大连对数")
    private Integer stillRightNumber =0;

    //答题正确个数
    @ApiModelProperty("答题正确个数")
    private Integer RightNumber =0;
    //玩家游戏排名
    @ApiModelProperty("玩家游戏排名")
    private Integer ranking=1;
    //玩家当前所剩提示数
    @ApiModelProperty("玩家当前所剩提示数")
    private Integer clueNumber=3;
    //当前题目是否正确
    @ApiModelProperty("当前题目是否正确")
    private boolean result=false;
    //下一题目限制时间
    @ApiModelProperty("下一题目限制时间")
    private Integer nextAnswerTime=15;
    //下一题目星级数
    @ApiModelProperty("下一题目星级数")
    private Integer star=1;
    //当前回答题目数量
    @ApiModelProperty("当前回答题目总数")
    private Integer puzzleNumber=0;
    //玩家当前buffs
    @ApiModelProperty("玩家当前buffs")
    List<String> buffs;
    //游戏开始时间
    @ApiModelProperty("游戏开始时间")
    private LocalDateTime gameStartTime;


    Player(){
    }
    Player(String matcherId){
        this.matcherId=matcherId;
    }

}
