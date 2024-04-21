package com.ab.product.dto;

import com.ab.dto.PageQuery;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCompletedQueryDTO extends PageQuery implements Serializable {

    @ApiModelProperty("积分下限")
    private Integer minPoints;

    @ApiModelProperty("积分上限")
    private Integer maxPoints;
    /**
     * 商品分类
     */
    @ApiModelProperty("商品分类id")
    private Long categoryId;

    @ApiModelProperty("商品查询名称")
    private String name;

    @ApiModelProperty("商品状态(0下架,1在售)")
    private Integer status;
    /**
     * 专区展示（会员，积分，普通）
     */
    @ApiModelProperty("商品专区展示（2会员，1积分，0普通）")
    private Integer productZone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}