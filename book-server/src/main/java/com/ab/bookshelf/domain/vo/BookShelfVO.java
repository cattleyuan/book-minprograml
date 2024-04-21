package com.ab.bookshelf.domain.vo;

import com.ab.book.domain.vo.UBookVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/3/6
 */
@Data
public class BookShelfVO implements Serializable {
    @ApiModelProperty("书籍信息")
    UBookVO book;
    /**
     *
     */
    @ApiModelProperty("书籍加入书架时间")
    private LocalDateTime enterTime;

}
