package com.ab.bcategory.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/2/1
 */
@Data
public class BookCategoryVO implements Serializable {

    @ApiModelProperty("书籍分类id")
    private Long id;

    @ApiModelProperty("书籍分类名称")
    private String categoryName;
}
