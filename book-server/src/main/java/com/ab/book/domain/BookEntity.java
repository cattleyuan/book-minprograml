package com.ab.book.domain;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @TableName book
 */
@TableName(value ="book")
@Data
public class BookEntity extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 书籍链接
     */
    private String bookUrl;

    /**
     * 封面
     */
    private String bookImg;

    /**
     * 书籍简要描述
     */
    private String bookDescription;

    /**
     * 书籍分类
     */
    private Integer bookCategories;
    /**
     * 作者
     */
    private String author;

    /**
     * 是否上架
     */
    private Integer status;

    /**
     * 会员专享
     */
    private Integer permission;
    /**
     * 创建人id
     */
    private Long createAdminer;

    private Integer displayOrder;
    /**
     * 修改人id
     */
    private Long updateAdminer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}