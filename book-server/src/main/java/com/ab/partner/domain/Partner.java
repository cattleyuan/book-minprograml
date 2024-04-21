package com.ab.partner.domain;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName partner
 */
@TableName(value ="partner")
@Data
public class Partner extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 被动添加的用户id
     */
    private Long userId;

    /**
     * 主动添加的用户id
     */
    private Long frdId;

    /**
     * 用户添加留言
     */
    private String comment;

    /**
     * user给frd的备注
     */
    private String remark;


    /**
     * 添加状态(0待添加，1已添加)
     */
    private Integer status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}