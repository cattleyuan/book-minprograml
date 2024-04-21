package com.ab.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

@Data
public class LabelDTO implements Serializable {
    @ApiModelProperty("id,更新时需要，新增时不用")
    private Long id;

    /**
     * (标签名称)
     */
    @ApiModelProperty("标签名称")
    private String labelName;

}
