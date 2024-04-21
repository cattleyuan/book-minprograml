package com.ab.activity.domain;

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
 * @TableName activity
 */
@TableName(value ="activity")
@Data
public class Activity extends BaseIncrIDEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 活动发布人
     */
    private Long adminId;

    /**
     * 活动发布人姓名
     */
    private String adminName;
    /**
     * 活动跳转路径
     */
    private String contentUrl;

    /**
     * 活动图片路径
     */
    private String imgUrl;

    /**
     * 活动发布状态
     */
    private Integer status;
    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 活动发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}