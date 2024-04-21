package com.ab.product.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductQueryVO implements Serializable {
    /**
     * 
     */

    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("商品图片")
    private String productImg;
    /**
     * 商品所需积分
     */
    @ApiModelProperty("商品所需积分")
    private Integer points;

    /**
     * 商品所需价格
     */
    @ApiModelProperty("商品所需价格")
    private BigDecimal price;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}