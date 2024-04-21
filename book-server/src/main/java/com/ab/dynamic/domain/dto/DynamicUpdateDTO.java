package com.ab.dynamic.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/3/16
 */
@Data
public class DynamicUpdateDTO implements Serializable {
    @ApiModelProperty("动态id")
    Long id;
    /**
     *
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     *
     */
    @ApiModelProperty("内容")
    private String text;

    /**
     *
     */
    @ApiModelProperty("图片，用逗号隔开")
    private String image;

    /**
     *
     */
    @ApiModelProperty("心情(0沮丧。1开心...")
    private Integer mood;
}
