package com.ab.gamehistory.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ab.context.BaseContext;
import com.ab.gamehistory.domain.GameHistory;
import com.ab.gamehistory.domain.vo.GameHistoryVO;
import com.ab.gamehistory.mapper.GameHistoryMapper;
import com.ab.gamehistory.service.GameHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86150
* @description 针对表【game_history】的数据库操作Service实现
* @createDate 2024-02-28 21:14:15
*/
@Service
public class GameHistoryServiceImpl extends ServiceImpl<GameHistoryMapper, GameHistory>
    implements GameHistoryService {

    @Override
    public List<GameHistoryVO> getGameHistory() {
        Long userId = BaseContext.getCurrentId();
        //查找当前玩家历史记录
        List<GameHistory> gameHistoryList = lambdaQuery()
                .eq(userId != null, GameHistory::getUserId, userId)
                .list();

        List<GameHistoryVO> gameHistoryVOList = BeanUtil.copyToList(gameHistoryList, GameHistoryVO.class);
        return gameHistoryVOList;
    }


}




