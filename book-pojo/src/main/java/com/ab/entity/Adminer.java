package com.ab.entity;

import java.io.Serializable;
import java.util.Date;

import com.ab.base.BaseIncrIDEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员表
 * @TableName adminer
 */
@Data
public class Adminer extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
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

    /**
     * (管理员密码)
     */
    @ApiModelProperty("管理员密码")
    private String adminPassword;


    private static final long serialVersionUID = 1L;
}