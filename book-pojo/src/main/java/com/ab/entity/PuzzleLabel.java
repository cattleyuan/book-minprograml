package com.ab.entity;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 谜题标签表
 * @TableName puzzle_label
 */
@TableName(value ="puzzle_label")
@Data
public class PuzzleLabel extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long puzzleId;

    /**
     * (标签名称)
     */
    private String labelName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}