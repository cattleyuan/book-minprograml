package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PuzzleLabelDTO implements Serializable {
    /**
     * (标签名称)
     */
    @ApiModelProperty(value = "标签名称",required = true)
    private String labelName;
}
