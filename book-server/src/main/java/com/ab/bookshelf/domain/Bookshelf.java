package com.ab.bookshelf.domain;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName bookshelf
 */
@TableName(value ="book_shelf")
@Data
public class Bookshelf extends BaseIncrIDEntity implements Serializable {


    /**
     * 书籍id
     */
    private Long bookId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime enterTime;

    private Integer bookCategories;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}