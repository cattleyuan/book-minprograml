package com.ab.dcomment.domain.dto;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName dynamic_comment
 */

@Data
public class DynamicCommentDTO  implements Serializable {


    /**
     * 
     */
    private Long dynamicId;

    /**
     * 
     */
    private String commentText;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}