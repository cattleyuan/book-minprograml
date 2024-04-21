package com.ab.gamehistory.service;


import com.ab.gamehistory.domain.GameHistory;

import com.ab.gamehistory.domain.vo.GameHistoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【game_history】的数据库操作Service
* @createDate 2024-02-28 21:14:15
*/
public interface GameHistoryService extends IService<GameHistory> {

    List<GameHistoryVO> getGameHistory();


}
