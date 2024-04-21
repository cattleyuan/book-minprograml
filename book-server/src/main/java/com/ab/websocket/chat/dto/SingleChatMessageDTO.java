package com.ab.websocket.chat.dto;

import cn.hutool.crypto.SecureUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
@Data
public class SingleChatMessageDTO implements Serializable {
    /**
     * 发送方id
     */
    private Long userId;
    /**
     * 接收方id
     */
    private Long receiverId;

    /**
     * 消息内容
     */
    private String messageText;

    /**
     * 消息发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;
}
