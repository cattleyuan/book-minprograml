package com.ab.websocket.chat.strategy;

import com.ab.websocket.chat.dto.MessageDTO;
import com.ab.websocket.chat.singlechat.TalkWebSocketServer;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
public interface ChatStrategy {

    void storeMessage(MessageDTO messageDTO);
    void sendMessage(CopyOnWriteArraySet<TalkWebSocketServer> talkWebSocketSet,MessageDTO messageDTO);

}
