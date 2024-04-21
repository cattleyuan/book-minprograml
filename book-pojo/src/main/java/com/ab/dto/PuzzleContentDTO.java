package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PuzzleContentDTO implements Serializable {
    /**
     * (谜题内容媒体文件)
     */
    @ApiModelProperty(value = "谜题内容媒体文件url")
    private String media;

    /**
     * (谜题内容文本)
     */
    @ApiModelProperty(value = "谜题内容文本",required = true)
    private String contentText;
}
