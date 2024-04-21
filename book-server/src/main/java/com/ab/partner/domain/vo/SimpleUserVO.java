package com.ab.partner.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author cattleYuan
 * @date 2024/2/2
 */
@Data
public class SimpleUserVO implements Serializable {
    /**
     *
     */
    private Long id;


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
     * (兴趣爱好)
     */
    @ApiModelProperty("兴趣爱好")
    private String hobby;

    /**
     * (省份)
     */
    @ApiModelProperty("省份")
    private String province;

    /**
     * (城市)
     */
    @ApiModelProperty("城市")
    private String city;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirthday;
}
