package com.ab.entity;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 谜题选项表
 * @TableName puzzle_options
 */
@TableName(value ="puzzle_options")
@Data
public class PuzzleOptions extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "puzzle_id")
    private Long puzzleId;

    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项(abcde..)",required = true)
    private String tbSelect;

    @TableField(value = "select_content")
    private String selectContent;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}