package com.ab.websocket.chat.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
@Data
public class ChatMessageVO implements Serializable {
    //群组id(可选)
    Long groupId;
    //发送者id
    Long senderId;
    //消息内容
    String messageText;
    //聊天方式（群聊，私聊）
    String chatType;
}
