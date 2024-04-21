package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author cattleYuan
 * @date 2024/2/27
 */
@Data
public class PuzzleQueryDTO implements Serializable {
    @ApiModelProperty(value = "谜题名称",required = false)
    private String puzzleName;//谜题名称

    @ApiModelProperty(value = "谜题难度，1~5星",required = false)
    private Integer star;//谜题难度，1~5星

    @ApiModelProperty(value = "谜题分类",required = false)
    private Integer puzzleKinds;

    @ApiModelProperty(value = "谜题题目类型")
    private Integer puzzleType;
}
