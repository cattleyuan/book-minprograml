package com.ab.membership.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

/**
 * 会员表
 * @TableName member_ship
 */
@TableName(value ="member_ship")
@Data
public class MemberShip implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 会员类型
     */
    @ApiModelProperty("会员类型")
    private Integer memberType;

    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
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

    /**
     * 乐观锁
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}