package com.ab.bcategory.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/2/1
 */
@Data
public class BookCategoryDTO implements Serializable {

    @ApiModelProperty("书籍分类id")
    private Long id;

    @ApiModelProperty("书籍分类名称")
    private String categoryName;
}
