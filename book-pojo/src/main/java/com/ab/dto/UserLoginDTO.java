package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {

    String code;

    @ApiModelProperty("用户名称")
    private String nickname;

    /**
     * (用户性别)
     */
    @ApiModelProperty("用户性别")
    private Integer gender;

    /**
     * (用户头像)
     */
    @ApiModelProperty("用户头像")
    private String avatarurl;
}
