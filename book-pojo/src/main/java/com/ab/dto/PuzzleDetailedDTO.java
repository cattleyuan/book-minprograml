package com.ab.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;



@Data
public class PuzzleDetailedDTO extends PuzzleDTO {


    @ApiModelProperty(value = "谜题积分",required = true)
    private Long puzzleScore;//谜题积分
    @ApiModelProperty(value = "展示排序字段")
    Long displayOrder;

}
