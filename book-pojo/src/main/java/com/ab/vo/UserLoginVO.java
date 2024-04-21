package com.ab.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {

    private Long id;
    @ApiModelProperty("用户唯一标识")
    private String openid;
    @ApiModelProperty("用户访问令牌")
    private String wxtoken;

}
