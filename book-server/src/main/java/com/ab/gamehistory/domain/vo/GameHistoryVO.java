package com.ab.gamehistory.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/2/28
 */
@Data
public class GameHistoryVO implements Serializable {
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
