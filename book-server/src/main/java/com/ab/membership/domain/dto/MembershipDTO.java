package com.ab.membership.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@Data
public class MembershipDTO {
    @ApiModelProperty("到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;
    @ApiModelProperty("激活天数")
    private Integer activeDay;
    @ApiModelProperty("付款方式(形式，传了没用)")
    private Integer paymentType;
}
