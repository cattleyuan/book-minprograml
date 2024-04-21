package com.ab.websocket.chat.singlechat.service;


import com.ab.websocket.chat.singlechat.domain.SingleChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【single_chat_message】的数据库操作Service
* @createDate 2024-01-29 12:23:59
*/
public interface SingleChatMessageService extends IService<SingleChatMessage> {

    List<SingleChatMessage> getChatMessageList(Long id);

    void deleteMessageById(Long id);
}
