package com.ab.websocket.chat.groupchat.domain;

import com.ab.base.ChatBaseIncrlDEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName group_chat_message
 */
@TableName(value ="group_chat_message")
@Data
public class GroupChatMessage extends ChatBaseIncrlDEntity implements Serializable {

    /**
     * 消息内容
     */
    private String messageText;

    /**
     * 发送者id
     */
    private Long senderId;

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 发送者删除标记
     */
    private Integer senderDeleted;

    /**
     * 消息发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date senderTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}