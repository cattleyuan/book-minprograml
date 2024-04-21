package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
public class PuzzleDTO {
    @ApiModelProperty(value = "谜题名称",required = true)
    private String puzzleName;//谜题名称
    @ApiModelProperty(value = "出题者id(管理端默认为0，0为官方出题)")
    private Long userId;// (出题者)0为官方出题
    @ApiModelProperty(value = "谜题答案",required = true)
    private String puzzleAnswer;//谜题答案
    @ApiModelProperty(value = "谜题难度，1~5星",required = true)
    private int star;//谜题难度，1~5星
    @ApiModelProperty(value = "谜题类型，如0填空,1选择",required = true)
    private int puzzleType;//谜题类型，如0填空,1选择
    @ApiModelProperty(value = "谜题分类",required = true)
    Integer puzzleKinds;
    @ApiModelProperty(value = "标签列表",required = true)
    private List<PuzzleLabelDTO> labelList;
    @ApiModelProperty(value = "谜题选项内容,谜题回答类型为选择才要")
    private List<PuzzleOptionsDTO> optionList;
    @ApiModelProperty(value = "谜题线索")
    String clue;
    @ApiModelProperty(value = "谜题内容（图片，文本）",required = true)
    private PuzzleContentDTO puzzleContentDTO;
}
