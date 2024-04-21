package com.ab.websocket.chat.strategy;

import com.ab.websocket.chat.dto.MessageDTO;
import com.ab.websocket.chat.singlechat.TalkWebSocketServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
@Component("groupchat")
public class GroupChatStrategy implements ChatStrategy {

    @Override
    @Async
    public void storeMessage(MessageDTO messageDTO) {

    }

    @Override
    @Async
    public void sendMessage(CopyOnWriteArraySet<TalkWebSocketServer> talkWebSocketSet,MessageDTO messageDTO) {

    }
}
