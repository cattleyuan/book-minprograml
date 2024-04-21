package com.ab.websocket.chat.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
@Data
public class GroupChatMessageDTO implements Serializable {
    /**
     * 消息内容
     */
    private String messageText;

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 消息发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date senderTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
