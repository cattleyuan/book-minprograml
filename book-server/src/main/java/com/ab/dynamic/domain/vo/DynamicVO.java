package com.ab.dynamic.domain.vo;

import com.ab.dcomment.domain.vo.DynamicCommentVO;
import com.ab.entity.User;
import com.ab.partner.domain.vo.UserVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/3/16
 */
@Data
public class DynamicVO implements Serializable {
    @ApiModelProperty("动态id")
    Long id;
    /**
     *
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     *
     */
    @ApiModelProperty("内容")
    private String text;

    /**
     *
     */
    @ApiModelProperty("图片，用逗号隔开")
    private String image;

    /**
     *
     */
    @ApiModelProperty("心情(0沮丧。1开心...")
    private Integer mood;


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


    @ApiModelProperty("用户年龄")
    private Integer yearsOld;

    @ApiModelProperty("用户是否点赞")
    private boolean isLiked;

    @ApiModelProperty("前三条评论信息")
    private List<DynamicCommentVO> commentList;

    @ApiModelProperty("用户点赞数")
    private Integer thumbs;

    @ApiModelProperty("用户评论数")
    private Long commentNumber;
}
