package com.ab.book.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/2/1
 */
@Data
public class UBookQueryDTO implements Serializable {
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
     * 会员专享
     */
    @ApiModelProperty(value = "是否会员专享")
    private Integer permission;
}
