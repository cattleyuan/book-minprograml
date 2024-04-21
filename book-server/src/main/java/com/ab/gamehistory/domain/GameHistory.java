package com.ab.gamehistory.domain;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName game_history
 */
@TableName(value ="game_history")
@Data
public class GameHistory extends BaseIncrIDEntity implements Serializable {


    private Long userId;

    /**
     * 
     */
    @ApiModelProperty("匹配者id")
    private Long matcherId;

    /**
     * 最高答题连对数
     */
    @ApiModelProperty("最高答题连对数")
    private Integer stillRightNumber;

    /**
     * 最终游戏积分
     */
    @ApiModelProperty("最终游戏积分")
    private Integer integral;

    /**
     * 游戏结果
     */
    @ApiModelProperty("游戏结果")
    @TableField("is_success")
    private Integer isSuccess;

    /**
     * 答对数
     */
    @ApiModelProperty("答对数")
    private Integer rightNumber;

    /**
     * 题目总数
     */
    @ApiModelProperty("题目总数")
    private Integer puzzleNumber;

    @ApiModelProperty("游戏开始时间")
    private LocalDateTime gameStartTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}