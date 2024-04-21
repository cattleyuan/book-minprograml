package com.ab.dynamic.domain;

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
 * @TableName dynamic
 */
@TableName(value ="dynamic")
@Data
public class Dynamic extends BaseIncrIDEntity implements Serializable {

    @TableId(type = IdType.AUTO, value = "id")
    private Long id;
    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String text;

    /**
     * 
     */
    private String image;

    /**
     * 
     */
    private LocalDateTime publishTime;

    /**
     * 
     */
    private Integer mood;

    /**
     * 
     */
    private Integer thumbs;



    @TableField(exist = false)
    private boolean isLiked;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}