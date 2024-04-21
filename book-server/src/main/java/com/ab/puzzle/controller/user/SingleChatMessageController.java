package com.ab.puzzle.controller.user;

import com.ab.context.BaseContext;
import com.ab.result.Result;
import com.ab.websocket.chat.singlechat.domain.SingleChatMessage;
import com.ab.websocket.chat.singlechat.service.SingleChatMessageService;
import com.ab.websocket.chat.strategy.SingleChatStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/29
 */
@Slf4j
@Validated
@RestController
@Api(tags = "私聊相关接口")
@RequestMapping("/user/chat")
public class SingleChatMessageController {
    @Autowired
    private SingleChatMessageService chatMessageService;

    @GetMapping("/list")
    @ApiOperation("通过朋友id获取聊天信息")
    public Result<List<SingleChatMessage>> getChatRecord(@RequestParam @NotNull Long id){
        List<SingleChatMessage> list = chatMessageService.getChatMessageList(id);
        return Result.success(list);
    }
    @PostMapping("/delete")
    @ApiOperation("通过消息id撤回消息")
    public Result deleteMessage(@RequestParam @NotNull Long id){
        chatMessageService.deleteMessageById(id);
        return Result.success();
    }
}
