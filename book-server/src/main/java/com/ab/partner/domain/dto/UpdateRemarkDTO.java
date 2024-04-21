package com.ab.partner.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author cattleYuan
 * @date 2024/1/22
 */
@Data
public class UpdateRemarkDTO {

    @NotNull(message = "备注被修改者者id不能为空")
    @ApiModelProperty(value = "备注被修改者id",required = true)
    private long frdId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty("-1拒绝申请,0待申请，1通过好友")
    private Integer status;
}
