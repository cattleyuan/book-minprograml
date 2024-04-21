package com.ab.websocket.chat.singlechat;

import cn.hutool.extra.spring.SpringUtil;
import com.ab.config.WebSorketConfiguration;
import com.ab.context.BaseContext;
import com.ab.websocket.chat.dto.MessageDTO;
import com.ab.websocket.chat.strategy.ChatStrategy;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author cattleYuan
 * @date 2024/1/28
 */
@Component
@Slf4j
@ServerEndpoint("/wx/chat/{id}")
@Getter
public class TalkWebSocketServer {

    private Session session;
    private String userId;
    private static final CopyOnWriteArraySet<TalkWebSocketServer> webSocketSet=new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id){
        this.session=session;
        this.userId= id;
        webSocketSet.add(this);
        log.info("用户->{}-连接成功！",userId);
    }

    @OnMessage
    public void onMessage(String message){
        MessageDTO messageDTO = JSON.parseObject(message, MessageDTO.class);
        if(Objects.isNull(messageDTO)){
            log.error("传输消息为空->{}",messageDTO);
        }
        ChatStrategy instance = SpringUtil.getBean(messageDTO.getChatType());
        instance.storeMessage(messageDTO);
        instance.sendMessage(webSocketSet,messageDTO);
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("用户->{}-退出连接",userId);
    }
   /* @OnError
    public void onError(Exception ex){
        log.error("出问题啦->{}",ex.getMessage());
    }*/
}
