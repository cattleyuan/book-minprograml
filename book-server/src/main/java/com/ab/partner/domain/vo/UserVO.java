package com.ab.partner.domain.vo;

import com.ab.entity.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/1/22
 */
@Data
public class UserVO implements Serializable {
    @ApiModelProperty("朋友用户信息")
    private User user;
    @ApiModelProperty("朋友备注")
    private String remark;
    @ApiModelProperty("申请留言")
    private String comment;
}
