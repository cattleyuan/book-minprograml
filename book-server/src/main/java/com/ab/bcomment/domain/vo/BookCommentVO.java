package com.ab.bcomment.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/2/25
 */
@Data
public class BookCommentVO implements Serializable {
    @ApiModelProperty("评论id")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 书籍评价
     */
    @ApiModelProperty("书评或评论")
    private String comment;

    /**
     * (用户名称)
     */
    @ApiModelProperty("用户名称")
    private String nickname;

    /**
     * (用户性别)
     */
    @ApiModelProperty("用户性别")
    private Integer gender;

    /**
     * (用户头像)
     */
    @ApiModelProperty("用户头像")
    private String avatarurl;

    /**
     * 评分
     */
    @ApiModelProperty("书籍评分")
    private Integer rating;

    @ApiModelProperty("评论时间")
    private LocalDateTime commentTime;
}
