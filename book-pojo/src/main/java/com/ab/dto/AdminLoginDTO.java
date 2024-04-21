package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginDTO implements Serializable {
    @ApiModelProperty("管理员账号或用户名")
    String acount;
    @ApiModelProperty("管理员密码")
    String password;
}
