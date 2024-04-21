package com.ab.websocket.chat.singlechat.service.impl;

import com.ab.base.ChatKeyEnum;
import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.ab.websocket.chat.singlechat.domain.SingleChatMessage;
import com.ab.websocket.chat.singlechat.mapper.SingleChatMessageMapper;
import com.ab.websocket.chat.singlechat.service.SingleChatMessageService;
import com.ab.websocket.chat.strategy.SingleChatStrategy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author 86150
 * @description 针对表【single_chat_message】的数据库操作Service实现
 * @createDate 2024-01-29 12:23:59
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SingleChatMessageServiceImpl extends ServiceImpl<SingleChatMessageMapper, SingleChatMessage>
        implements SingleChatMessageService {
    private final RedisTemplate redisTemplate;

    @Override
    public List<SingleChatMessage> getChatMessageList(Long id) {
        Long userId = BaseContext.getCurrentId();
        String key = ChatKeyEnum.SINGLECHATBASEKEY.getName() + SingleChatStrategy.generateChatKey(userId, id);
        List<SingleChatMessage> list=redisTemplate.opsForList().range(key, 0, -1);
        if (Objects.nonNull(list)) {
            log.info("缓存聊天数据读取成功");
            return list;
        }
        //否则从数据库查并把数据放入缓存,并把查出来的数据
        list = lambdaQuery().and(data->data.eq(SingleChatMessage::getSenderId, userId).eq(SingleChatMessage::getReceiverId, id))
                .or()
                .and(data->data.eq(SingleChatMessage::getReceiverId, userId).eq(SingleChatMessage::getSenderId, id))
                .list()
                .stream().sorted(Comparator.comparing(SingleChatMessage::getSendTime).reversed()).collect(Collectors.toList());
        if(!Optional.ofNullable(list).isPresent()){
            log.info("聊天数据为空");
            return Collections.emptyList();
        }
        redisTemplate.opsForList().leftPushAll(key,list);
        return list;
    }

    @Override
    public void deleteMessageById(Long id) {
        removeById(id);
        log.info("删除消息成功");
    }
}




