package com.ab.bcomment.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/2/25
 */
@Data
public class AddBookCommentDTO implements Serializable {


    @ApiModelProperty("书籍id")
    private Long bookId;

    @ApiModelProperty("父级评论，0则为该书籍的一级评论,书籍才要")
    private Long parentId;

    @ApiModelProperty("书评和评论标识，0为评论,1为书评")
    private Integer isBookRating;

    /**
     * 书籍评价
     */
    @ApiModelProperty("书评或评论")
    private String comment;
    /**
     * 评分
     */
    @ApiModelProperty("评分,书评才要")
    private Integer rating;
}
