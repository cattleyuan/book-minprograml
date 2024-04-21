package com.ab.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@Data
public class ProductVO {
    @ApiModelProperty("商品id")
    private Long id;
    /**
     * 商品分类
     */
    @ApiModelProperty("商品分类")
    private Long categoryId;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;
    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String productImg;
    /**
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    private String description;

    /**
     * 商品所需积分
     */
    @ApiModelProperty("商品所需积分")
    private Integer points;

    /**
     * 商品状态
     */
    @ApiModelProperty("商品状态")
    private Integer status;

    /**
     * 商品所需价格
     */
    @ApiModelProperty("商品所需价格")
    private BigDecimal price;

    /**
     * 商品展示优先级
     */
    @ApiModelProperty("商品展示优先级")
    private Integer displayOrder;

    /**
     * 专区展示（会员，积分，普通）
     */
    @ApiModelProperty("专区展示（2会员，1积分，0普通）")
    private Integer productZone;
}
