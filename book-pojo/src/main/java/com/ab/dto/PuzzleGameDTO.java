package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/3/9
 */
@Data
public class PuzzleGameDTO implements Serializable {
    @ApiModelProperty("谜题id")
    Long puzzleId;
    @ApiModelProperty("用户回答结果")
    String answer;
}
