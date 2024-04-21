package com.ab.websocket.chat.singlechat.domain;

import com.ab.base.ChatBaseIncrlDEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName single_chat_message
 */
@TableName(value ="single_chat_message")
@Data
public class SingleChatMessage extends ChatBaseIncrlDEntity implements Serializable {

    /**
     * 接收方id
     */
    private Long receiverId;

    /**
     * 发送方id
     */
    private Long senderId;

    /**
     * 消息内容
     */
    private String messageText;

    /**
     * 消息发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;

    /**
     * 消息发送方删除标记
     */
    private Integer receiverDeleted;

    /**
     * 消息接收方删除标记
     */
    private Integer senderDeleted;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}