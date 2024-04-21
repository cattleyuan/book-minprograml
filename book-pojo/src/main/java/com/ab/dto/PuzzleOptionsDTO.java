package com.ab.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PuzzleOptionsDTO implements Serializable {
    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项(ABCDE..)",required = true)
    private String tbSelect;
    @ApiModelProperty(value = "选项内容",required = true)
    private String selectContent;
}
