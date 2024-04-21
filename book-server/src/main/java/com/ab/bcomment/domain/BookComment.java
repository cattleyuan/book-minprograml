package com.ab.bcomment.domain;

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
 * @TableName book_comment
 */
@TableName(value ="book_comment")
@Data
public class BookComment extends BaseIncrIDEntity implements Serializable {
    /**
     * 书籍id
     */
    private Long bookId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 书籍评价
     */
    private String comment;

    /**
     * 父级评论id
     */
    private Long parentId;

    /**
     * 评分
     */
    private Integer rating;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentTime;
    /**
     * 
     */
    @TableField("is_book_rating")
    private Integer isBookRating;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}