package com.ab.entity;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Puzzle extends BaseIncrIDEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    Long id;
    @ApiModelProperty("谜题名称")
    String puzzleName;//谜题名称
    @ApiModelProperty("出题者id")
    Long userId;// (出题者)0为官方出题
    @ApiModelProperty("谜题答案")
    String puzzleAnswer;//谜题答案
    @ApiModelProperty("谜题积分")
    Long puzzleScore;//谜题积分
    @ApiModelProperty("谜题难度，1~5星")
    int star;//谜题难度，1~5星
    @ApiModelProperty("谜题类型，如0填空,1选择")
    int puzzleType;//谜题类型，如0填空,1选择
    @ApiModelProperty("谜题分类（古风，现代，诗文）")
    int puzzleKinds;
    @ApiModelProperty("谜题状态")
    int puzzleStatus;//0待审核,1通过
    @ApiModelProperty(value = "展示排序字段(隐藏分),默认为0",required = false)
    Long displayOrder;
    @ApiModelProperty(value = "谜题线索")
    String clue;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
