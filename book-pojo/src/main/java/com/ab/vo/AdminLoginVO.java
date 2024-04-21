package com.ab.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginVO implements Serializable {
    Long adminId;
    String adminName;
    @ApiModelProperty("携带token不会被拦截")
    String token;
}
