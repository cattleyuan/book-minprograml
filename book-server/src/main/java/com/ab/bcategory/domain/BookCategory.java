package com.ab.bcategory.domain;

import com.ab.base.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName book_category
 */
@TableName(value ="book_category")
@Data
public class BookCategory extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 创建者id
     */
    private Long createAdminer;

    /**
     * 更新者id
     */
    private Long updateAdminer;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}