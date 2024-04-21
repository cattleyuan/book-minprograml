package com.ab.websocket.game;

import cn.hutool.extra.spring.SpringUtil;
import com.ab.buff.Buff;
import com.ab.buff.StartHandler;
import com.ab.config.GameConifg;
import com.ab.puzzle.service.PuzzleService;
import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.vo.GameMessageVO;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@Slf4j
@ServerEndpoint("/ws/game/{id}")
@NoArgsConstructor
@Getter
public class GameWebSocketServer {

    private Session session;
    private String userId;
    private String matcherId;


    private static GameConifg gameConifg;

    private static RedisTemplate redisTemplate;

    private static PuzzleService puzzleService;
    @Autowired
    public void setPuzzleService(PuzzleService puzzleService) {
        GameWebSocketServer.puzzleService = puzzleService;
    }

    @Autowired
    public void setGameConifg(GameConifg gameConifg) {
        GameWebSocketServer.gameConifg = gameConifg;
    }
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        GameWebSocketServer.redisTemplate = redisTemplate;
    }


    //游戏大厅
    private static CopyOnWriteArraySet<GameWebSocketServer> webSocketSet=new CopyOnWriteArraySet<>();
    //游戏匹配队列
    private static ConcurrentLinkedDeque<GameWebSocketServer> matchQueue= new ConcurrentLinkedDeque<GameWebSocketServer>();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) throws IOException {

        this.session=session;
        this.userId= id;
        webSocketSet.add(this);
        log.info("玩家->{}-连接成功！",userId);
        Optional<Object> objectOptional = Optional.ofNullable(redisTemplate.opsForValue().get(GameKeyEnum.INITIALIZE.getName()+userId));

        if(objectOptional.isPresent()&&objectOptional.get()!=null){
            //玩家数据已存在，判断该对局是否结束
            Player player=JSON.parseObject(String.valueOf(objectOptional.get()),Player.class);
            long durTime = Duration.between(player.getGameStartTime(), LocalDateTime.now()).getSeconds();
            if(durTime>= 20*gameConifg.getAnswerTime()){
                //删除游戏缓存
                redisTemplate.delete(GameKeyEnum.INITIALIZE+userId);
                //证明游戏已结束,重新进入匹配逻辑
                log.info("玩家->{}重新进入匹配",userId);
                initializeMatche();
                return;
            }
            //否则执行重连逻辑
            handlerReConnect(player);
            return;
            
        }
        //匹配逻辑
        initializeMatche();
    }



    private void initializeMatche() throws IOException {
        if(matchQueue.isEmpty()){
            //队列为空,加入该玩家进匹配队列
            log.info("玩家:{}->加入匹配队列",userId);
            matchQueue.add(this);
        }
        else{
            //队列不为空，执行游戏数据初始化逻辑
            handlerMatch();
        }
    }


    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info(message);
        GameMessageDTO gameMessageDTO = JSON.parseObject(message, GameMessageDTO.class);
        if(gameMessageDTO==null){
            log.error("请求参数不存在");
            return;
        }
        log.info("请求参数->{}",gameMessageDTO);
        GameMessageVO gameMessageVO = puzzleService.judgePuzzleOnline(gameMessageDTO);
        if(Objects.isNull(gameMessageVO)){
            log.info("游戏结束");
            return;
        }
        log.info("返回参数->{}",gameMessageVO);
        this.session.getBasicRemote().sendText(JSON.toJSONString(gameMessageVO));

        sendTextToSb(matcherId,"matcherIntegrl: "+ gameMessageVO.getPlayer().getIntegral());

    }

    private void sendTextToSb(String id,String text) throws IOException {
        for (GameWebSocketServer gameWebSocketServer : webSocketSet) {
            if(gameWebSocketServer.getUserId().equals(id)){
                gameWebSocketServer.getSession().getBasicRemote().sendText(text);
            }
        }
    }

    private void handlerReConnect(Player player) throws IOException {
        log.info("玩家->{},重连中",userId);
        matcherId=player.getMatcherId();
        GameMessageVO gameMessageVO = new GameMessageVO(player,true);
        this.session.getBasicRemote().sendText(JSON.toJSONString(gameMessageVO));
    }

    @OnClose
    public void onClose(CloseReason closeReason){
        webSocketSet.remove(this);
        matchQueue.remove(this);
        log.error("连接断开原因->{}",closeReason.getReasonPhrase());
        log.info("玩家->{}-退出连接！",userId);
    }


    private void handlerMatch() throws IOException {
        GameWebSocketServer matcher = matchQueue.poll();
        if(matcher==null){
            log.error("匹配队列为空");
            return;
        }
        if(matcher.userId.equals(this.userId)){
            log.error("玩家-{} 孤零零",this.userId);
            matchQueue.add(this);
            return;
        }

        matcherId=matcher.userId;
        matcher.matcherId=userId;
        matchQueue.remove(matcher);

        log.info("匹配成功->{}vs{}",userId,matcherId);
        //注册玩家信息
        registPlayer(matcher);
    }

    private void registPlayer(GameWebSocketServer matcher) throws IOException {
        //创建玩家用户信息
        Player uPlayer = new Player(matcherId);
        uPlayer.setUserId(userId);
        Player mPlayer =new Player(userId);
        mPlayer.setUserId(matcherId);
        uPlayer.setGameStartTime(LocalDateTime.now());
        mPlayer.setGameStartTime(LocalDateTime.now());
        //设置返回信息
        GameMessageVO userPlayerInfo = new GameMessageVO(uPlayer,false);
        GameMessageVO matcherPlayerInfo = new GameMessageVO(mPlayer,false);
        //初始化玩家游戏数据到redis
        redisTemplate.opsForValue().set(GameKeyEnum.INITIALIZE.getName()+userId,JSON.toJSONString(uPlayer));
        redisTemplate.opsForValue().set(GameKeyEnum.INITIALIZE.getName()+matcherId,JSON.toJSONString(mPlayer));
        //发送匹配成功信息和用户答题初始化数据
        this.session.getBasicRemote().sendText(JSON.toJSONString(userPlayerInfo));
        matcher.session.getBasicRemote().sendText(JSON.toJSONString(matcherPlayerInfo));
    }
}

