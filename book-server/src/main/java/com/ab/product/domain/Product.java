package com.ab.product.domain;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品分类
     */
    private Long categoryId;

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品所需积分
     */
    private Integer points;

    /**
     * 商品状态
     */
    private Integer status;

    /**
     * 商品所需价格
     */
    private BigDecimal price;

    /**
     * 商品展示优先级
     */
    private Integer displayOrder;

    /**
     * 专区展示（会员，积分，普通）
     */
    private Integer productZone;

    /**
     * 创建人id
     */
    private Long createAdminer;
    /**
     * 修改人id
     */
    private Long updateAdminer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}