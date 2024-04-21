package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.swing.*;
import java.util.List;
import java.util.Map;

@Data
public class PageQuery {
    @ApiModelProperty("页码")
    Integer pageNo;
    @ApiModelProperty("单页展示数据数")
    Integer pageSize;
    @ApiModelProperty("排序字段,0降,1升")
    Map<String,Integer> orderItem;
}
