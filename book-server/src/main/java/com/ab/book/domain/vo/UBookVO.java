package com.ab.book.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/2/1
 */
@Data
public class UBookVO implements Serializable {
    private Long id;

    /**
     * 书名
     */
    @ApiModelProperty(value = "书名")
    private String bookName;

    /**
     * 书籍链接
     */
    @ApiModelProperty(value = "书籍链接")
    private String bookUrl;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面")
    private String bookImg;

    /**
     * 书籍简要描述
     */
    @ApiModelProperty(value = "书籍简要描述")
    private String bookDescription;

    /**
     * 书籍分类
     */
    @ApiModelProperty(value = "书籍分类")
    private Integer bookCategories;
    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 会员专享
     */
    @ApiModelProperty(value = "是否会员专享")
    private Integer permission;
}
