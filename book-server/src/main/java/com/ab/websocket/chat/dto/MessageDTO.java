package com.ab.websocket.chat.dto;


import com.ab.websocket.chat.groupchat.domain.GroupChatMessage;
import com.ab.websocket.chat.singlechat.domain.SingleChatMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
@Data
public class MessageDTO implements Serializable {

    private SingleChatMessageDTO singleChatMessage;
    private GroupChatMessageDTO groupChatMessage;
    private String chatType;

}
