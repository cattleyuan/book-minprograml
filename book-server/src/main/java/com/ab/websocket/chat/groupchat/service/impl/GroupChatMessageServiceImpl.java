package com.ab.websocket.chat.groupchat.service.impl;

import com.ab.websocket.chat.groupchat.domain.GroupChatMessage;
import com.ab.websocket.chat.groupchat.mapper.GroupChatMessageMapper;
import com.ab.websocket.chat.groupchat.service.GroupChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author 86150
* @description 针对表【group_chat_message】的数据库操作Service实现
* @createDate 2024-01-29 12:33:16
*/
@Service
public class GroupChatMessageServiceImpl extends ServiceImpl<GroupChatMessageMapper, GroupChatMessage>
    implements GroupChatMessageService {

}




