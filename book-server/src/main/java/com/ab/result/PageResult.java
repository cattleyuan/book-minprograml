package com.ab.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    @ApiModelProperty("总记录数")
    private long total; //总记录数
    @ApiModelProperty("页数")
    private Integer pages;
    @ApiModelProperty("当前页数据集合")
    private List<T> records; //当前页数据集合

}
