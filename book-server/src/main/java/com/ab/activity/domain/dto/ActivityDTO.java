package com.ab.activity.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/1/26
 */
@Data
public class ActivityDTO implements Serializable {
    private Long id;
    /**
     * 活动发布人id
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

    private String status;

    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("活动结束时间(yyyy-MM-dd HH:mm:ss)")
    @Future
    private LocalDateTime endTime;

    /**
     * 活动发布时间
     */
    @ApiModelProperty("活动发布时间(yyyy-MM-dd HH:mm:ss)")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime publishTime;
}
