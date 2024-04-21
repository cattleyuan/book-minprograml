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
public class DeletePartnerDTO {
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private long userId;
    @NotNull(message = "朋友id不能为空")
    @ApiModelProperty("朋友id")
    private long frdId;

}
