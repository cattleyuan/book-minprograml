package com.ab.membership.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@Data
public class MembershipVO implements Serializable {
    /**
     * 会员类型
     */
    @ApiModelProperty("会员类型")
    private Integer memberType;

    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;

    /**
     * 到期时间
     */
    @ApiModelProperty("到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;

    /**
     * 会员积分
     */
    @ApiModelProperty("会员积分")
    private Integer memberPoints;

    /**
     * 是否激活
     */
    @ApiModelProperty("是否激活")
    private Integer isActive;
}
