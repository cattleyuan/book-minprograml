package com.ab.category.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCategoryDTO implements Serializable {
    /**
     * 
     */
    @ApiModelProperty("商品id")
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty("商品名称")
    private String categoryName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}