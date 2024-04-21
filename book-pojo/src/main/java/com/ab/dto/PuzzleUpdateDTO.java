package com.ab.dto;

import com.ab.entity.PuzzleContent;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
public class PuzzleUpdateDTO {
    @ApiModelProperty(required = true)
    private Long id;
    @ApiModelProperty(value = "谜题名称",required = true)
    private String puzzleName;//谜题名称
    @ApiModelProperty(value = "出题者id(管理端默认为0，0为官方出题)",required = true)
    private Long userId;// (出题者)0为官方出题
    @ApiModelProperty(value = "谜题答案",required = true)
    private String puzzleAnswer;//谜题答案
    @ApiModelProperty(value = "(管理端)谜题积分",required = true)
    private Long puzzleScore;//谜题积分
    @ApiModelProperty(value = "谜题难度，1~5星",required = true)
    private int star;//谜题难度，1~5星
    @ApiModelProperty(value = "谜题回答类型，如0填空,1选择",required = true)
    private int puzzleType;//谜题类型，如0填空,1选择
    @ApiModelProperty(value = "展示排序字段")
    Long displayOrder;
    @ApiModelProperty(value = "谜题答题类型",required = true)
    Integer puzzleKinds;
    @ApiModelProperty(value = "谜题分类")
    Integer puzzleCategories;
    @ApiModelProperty(value = "(管理端)谜题状态",required = true)
    int puzzleStatus;//0审核,1判断
    @ApiModelProperty(value = "标签列表",required = true)
    private List<PuzzleLabelDTO> labelList;
    @ApiModelProperty(value = "谜题线索")
    String clue;
    @ApiModelProperty(value = "谜题选项内容,谜题类型为填空才要")
    private List<PuzzleOptionsDTO> optionList;
    @ApiModelProperty(value = "谜题内容（图片，文本）",required = true)
    private PuzzleContentDTO puzzleContent;


}
