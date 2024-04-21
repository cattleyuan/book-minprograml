package com.ab.entity;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 谜题内容表
 * @TableName puzzle_content
 */
@TableName(value ="puzzle_content")
@Data
public class PuzzleContent extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long puzzleId;

    /**
     * (谜题内容媒体文件)
     */
    private String media;

    /**
     * (谜题内容文本)
     */
    private String contentText;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}