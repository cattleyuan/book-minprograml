package com.ab.dynamic.domain.dto;

import com.ab.dto.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/3/24
 */
@Data
public class PageDynamicDTO extends PageQuery implements Serializable {
    @ApiModelProperty("0全部，1朋友，2自己")
    private Integer status;
}
