package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PuzzleQueryPageDTO extends PageQuery{
    @ApiModelProperty(value = "谜题名称",required = false)
    private String puzzleName;//谜题名称

    @ApiModelProperty(value = "最低谜题积分",required = false)
    private Long minPuzzleScore;//最低谜题积分
    @ApiModelProperty(value = "最高谜题积分",required = false)
    private Long maxPuzzleScore;//最低谜题积分
    @ApiModelProperty(value = "谜题难度，1~5星",required = false)
    private Integer star;//谜题难度，1~5星
    @ApiModelProperty(value = "谜题状态(0待审核,1通过)",required = false)
    private Integer puzzleStatus;
    @ApiModelProperty(value = "谜题分类",required = false)
    private Integer puzzleKinds;
    @ApiModelProperty(value = "谜题题目类型")
    private Integer puzzleType;

}
