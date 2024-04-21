package com.ab.category.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductCategoryVO implements Serializable {
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