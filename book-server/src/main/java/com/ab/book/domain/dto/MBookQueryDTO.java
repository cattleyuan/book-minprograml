package com.ab.book.domain.dto;

import com.ab.dto.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/2/1
 */
@Data
public class MBookQueryDTO extends PageQuery implements Serializable {
    /**
     * 书名
     */
    @ApiModelProperty(value = "书名")
    private String bookName;

    /**
     * 书籍分类
     */
    @ApiModelProperty(value = "书籍分类")
    private Integer bookCategories;

    /**
     * 是否上架
     */
    @ApiModelProperty(value = "是否上架")
    private Integer status;

    /**
     * 会员专享
     */
    @ApiModelProperty(value = "是否会员专享")
    private Integer permission;
}
