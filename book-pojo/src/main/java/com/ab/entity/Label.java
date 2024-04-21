package com.ab.entity;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 官方标签表
 * @TableName label
 */
@TableName(value ="label")
@Data
public class Label extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * (标签名称)
     */
    @ApiModelProperty("标签名称")
    private String labelName;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}