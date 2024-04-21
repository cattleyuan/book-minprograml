package com.ab.dcomment.domain.vo;

import com.ab.entity.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName dynamic_comment
 */

@Data
public class DynamicCommentVO implements Serializable {

    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * (用户名称)
     */
    @ApiModelProperty("用户名称")
    private String nickname;

    /**
     * (用户头像)
     */
    @ApiModelProperty("用户头像")
    private String avatarurl;
    /**
     *
     */
    @ApiModelProperty("评论内容")
    private String commentText;

    /**
     *
     */
    @ApiModelProperty("评论时间")
    private LocalDateTime commentTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}