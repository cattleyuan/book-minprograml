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
public class AddPartnerDTO {

    @NotNull(message = "要添加的用户id不能为空")
    @ApiModelProperty("要添加的用户id")
    private long frdId;
    @NotNull(message = "添加留言不能为空")
    @ApiModelProperty("添加时的用户留言")
    private String comment;
}
