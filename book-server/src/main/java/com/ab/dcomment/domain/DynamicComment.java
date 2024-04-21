package com.ab.dcomment.domain;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName dynamic_comment
 */
@TableName(value ="dynamic_comment")
@Data
public class DynamicComment extends BaseIncrIDEntity implements Serializable {

    private Long userId;
    /**
     * 
     */
    private Long dynamicId;

    /**
     * 
     */
    private String commentText;

    /**
     * 
     */
    private LocalDateTime commentTime;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}