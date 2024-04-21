package com.ab.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

/**
 * 用户表
 * @TableName tb_user
 */
@Data
@TableName("tb_user")
public class User implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * (用户唯一标识)
     */
    @ApiModelProperty("用户唯一标识")
    private String openid;

    /**
     * (用户名称)
     */
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


    /**
     * (用户手机号)
     */
    @ApiModelProperty("用户手机号")
    private String userPhone;

    /**
     * (兴趣爱好)
     */
    @ApiModelProperty("兴趣爱好")
    private String hobby;

    /**
     * (省份)
     */
    @ApiModelProperty("省份")
    private String province;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirthday;


    /**
     * (城市)
     */
    @ApiModelProperty("城市")
    private String city;

    /*用户积分*/
    @ApiModelProperty("用户积分")
    private Integer points;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}