package com.ab.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/1/30
 */
@Data
public class UpdateUserDTO implements Serializable {
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
    private LocalDateTime userBirthday;


    /**
     * (城市)
     */
    private String city;
}
