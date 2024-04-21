package com.ab.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/1/30
 */
@Data
public class AdminInfoDTO implements Serializable {
    private Long id;
    /**
     * (管理员名称)
     */
    @ApiModelProperty("管理员名称")
    private String adminName;

    /**
     * (管理员头像)
     */
    @ApiModelProperty("管理员头像")
    private String adminImg;

    /**
     * (管理员手机号)
     */
    @ApiModelProperty("管理员手机号")
    private String adminPhone;

    /**
     * (管理员账号)
     */
    @ApiModelProperty("管理员账号")
    private String adminAccount;
}
