package com.ab.bookshelf.domain.dto;

import com.ab.book.domain.vo.UBookVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/3/6
 */
@Data
public class BookShelfDTO implements Serializable {


    @ApiModelProperty("书籍分类")
    private Integer bookCategories;
    /**
     *
     */
    @ApiModelProperty("书籍是否按加入书架时间升序,0降,1升")
    private Integer isOrder;

}
