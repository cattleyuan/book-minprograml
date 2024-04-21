package com.ab.product.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductQueryDTO implements Serializable {

    /**
     * 商品分类
     */
    @ApiModelProperty("商品分类id")
    private Long categoryId;

    @ApiModelProperty("积分下限")
    private Integer minPoints;

    @ApiModelProperty("积分上限")
    private Integer maxPoints;
    /**
     * 专区展示（会员，积分，普通）
     */
    @ApiModelProperty("商品专区展示（2会员，1积分，0普通）")
    private Integer productZone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}