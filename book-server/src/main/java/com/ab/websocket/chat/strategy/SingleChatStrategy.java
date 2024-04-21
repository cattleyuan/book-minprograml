package com.ab.websocket.chat.strategy;

import com.ab.base.ChatKeyEnum;
import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.ab.websocket.chat.dto.MessageDTO;
import com.ab.websocket.chat.dto.SingleChatMessageDTO;
import com.ab.websocket.chat.singlechat.TalkWebSocketServer;
import com.ab.websocket.chat.singlechat.domain.SingleChatMessage;
import com.ab.websocket.chat.singlechat.service.SingleChatMessageService;
import com.ab.websocket.chat.vo.ChatMessageVO;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
@Component("singlechat")
@Slf4j
@RequiredArgsConstructor
public class SingleChatStrategy implements ChatStrategy {
    private final RedisTemplate redisTemplate;
    private final SingleChatMessageService MessageService;


    @Override
    @Async
    public void storeMessage(MessageDTO messageDTO) {
        SingleChatMessage message = new SingleChatMessage();
        SingleChatMessageDTO chatMessage = messageDTO.getSingleChatMessage();
        if(!Optional.ofNullable(chatMessage).isPresent()){
            log.error("存入参数为空");
            throw new BaseException("单人聊天参数为空");
        }
        message.setSenderId(chatMessage.getUserId());
        BeanUtils.copyProperties(chatMessage,message);
        //存入数据库
        MessageService.save(message);
        //存入redis
        storeIntoCache(message);
    }

    private void storeIntoCache(SingleChatMessage message) {
        String key= ChatKeyEnum.SINGLECHATBASEKEY.getName() +generateChatKey(message.getReceiverId(), message.getSenderId());
        log.info("聊天缓存键名->{}",key);
        redisTemplate.opsForList().leftPush(key, JSON.toJSONString(message));
        redisTemplate.opsForList().range(key,0,999);
        log.info("聊天信息已存入缓存");
    }

    @Override
    @Async
    public void sendMessage(CopyOnWriteArraySet<TalkWebSocketServer> talkWebSocketSet,MessageDTO messageDTO)  {
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        buildChatMessageVO(messageDTO, chatMessageVO);
        String messageText = JSON.toJSONString(chatMessageVO);
        for (TalkWebSocketServer WebSocket : talkWebSocketSet) {
            if(WebSocket.getUserId().equals(messageDTO.getSingleChatMessage().getReceiverId().toString())){
                try {
                    WebSocket.getSession().getBasicRemote().sendText(messageText);
                    log.info("发送成功");
                } catch (IOException e) {
                    log.error("发送失败->{}",e.getMessage());
                }
            }
        }
    }

    private void buildChatMessageVO(MessageDTO messageDTO, ChatMessageVO chatMessageVO) {
        chatMessageVO.setSenderId(messageDTO.getSingleChatMessage().getUserId());
        chatMessageVO.setChatType("singlechat");
        chatMessageVO.setMessageText(messageDTO.getSingleChatMessage().getMessageText());
    }

    public static String generateChatKey(Long receiveId, Long senderId) {
        // 将两个用户ID进行排序
        Long[] ids = {receiveId, senderId};
        Arrays.sort(ids);
        // 拼接成唯一的键
        String chatKey = ids[0]+"-"+ids[1];
        return chatKey;
    }
}
