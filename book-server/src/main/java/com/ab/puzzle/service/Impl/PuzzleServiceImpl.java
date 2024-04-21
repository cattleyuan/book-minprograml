package com.ab.puzzle.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ab.buff.Buff;
import com.ab.buff.StartHandler;
import com.ab.config.GameConifg;
import com.ab.constant.PuzzleStatus;
import com.ab.context.BaseContext;
import com.ab.dto.*;
import com.ab.entity.*;
import com.ab.gamehistory.domain.GameHistory;
import com.ab.gamehistory.service.GameHistoryService;
import com.ab.puzzle.service.*;
import com.ab.result.PageResult;


import com.ab.vo.PuzzleDetailedVO;
import com.ab.websocket.game.GameKeyEnum;
import com.ab.websocket.game.GameWebSocketServer;
import com.ab.websocket.game.Player;
import com.ab.websocket.game.dto.GameMessageDTO;
import com.ab.websocket.game.vo.GameMessageVO;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ab.puzzle.mapper.PuzzleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 86150
 * @description 针对表【puzzle(谜题表)】的数据库操作Service实现
 * @createDate 2023-12-07 19:55:18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PuzzleServiceImpl extends ServiceImpl<PuzzleMapper, Puzzle> implements PuzzleService {

    private final PuzzleOptionsService puzzleOptionsService;
    private final PuzzleLabelService puzzleLabelService;
    private final PuzzleContentService puzzleContentService;
    private final RedisTemplate redisTemplate;
    private final UserService userService;
    private final GameHistoryService gameHistoryService;
    private final String SimplePuzzleBaseKey = "puzzle:simplelist:";
    @Autowired
    private GameWebSocketServer gameWebSocketServer;

    @Transactional
    @Override
    public void savePuzzle(PuzzleDetailedDTO puzzleDetailedDTO) {

        Puzzle puzzle = new Puzzle();
        BeanUtil.copyProperties(puzzleDetailedDTO, puzzle);
        puzzle.setUpdateTime(LocalDateTime.now());
        puzzle.setPuzzleStatus(PuzzleStatus.ENABLE);
        save(puzzle);
        Long puzzleId = puzzle.getId();
        buildAndSavePuzzleContent(puzzleDetailedDTO, puzzleId);
        List<PuzzleLabel> puzzleLabels = BeanUtil.copyToList(puzzleDetailedDTO.getLabelList(), PuzzleLabel.class);
        for (int i = 0; i < puzzleLabels.size(); i++) {
            puzzleLabels.get(i).setPuzzleId(puzzleId);
        }
        puzzleLabelService.saveBatch(puzzleLabels);
        List<PuzzleOptionsDTO> optionList = puzzleDetailedDTO.getOptionList();
        if (optionList != null && optionList.size() > 0 && puzzleDetailedDTO.getPuzzleType() == 1) {
            List<PuzzleOptions> puzzleOptions = BeanUtil.copyToList(optionList, PuzzleOptions.class);
            for (int i = 0; i < puzzleOptions.size(); i++) {
                puzzleOptions.get(i).setPuzzleId(puzzleId);
            }
            puzzleOptionsService.saveBatch(puzzleOptions);
        }


    }

    @Override
    @Transactional
    public void removePuzzle(List<Long> ids) {
        removeBatchByIds(ids);
        for (Long id : ids) {
            puzzleContentService.lambdaUpdate().eq(PuzzleContent::getPuzzleId, id).remove();
            puzzleOptionsService.lambdaUpdate().eq(PuzzleOptions::getPuzzleId, id).remove();
            puzzleLabelService.lambdaUpdate().eq(PuzzleLabel::getPuzzleId, id).remove();
        }

    }

    @Override
    @Transactional
    public void saveBlanchPuzzle(List<PuzzleDetailedDTO> puzzleDetailedDTOList) {
        for (PuzzleDetailedDTO puzzleDetailedDTO : puzzleDetailedDTOList) {
            savePuzzle(puzzleDetailedDTO);
        }
    }

    @Override
    @Transactional
    public void updatePuzzle(PuzzleUpdateDTO puzzleUpdateDTO) {
        //更新谜题表主要字段
        Puzzle puzzle = new Puzzle();
        BeanUtil.copyProperties(puzzleUpdateDTO, puzzle);
        puzzle.setUpdateTime(LocalDateTime.now());
        updateById(puzzle);
        Long puzzleId = puzzleUpdateDTO.getId();
        if (Optional.ofNullable(puzzleUpdateDTO.getPuzzleContent()).isPresent()) {
            PuzzleContent puzzleContent = new PuzzleContent();
            BeanUtil.copyProperties(puzzleUpdateDTO.getPuzzleContent(), puzzleContent);
            puzzleContent.setPuzzleId(puzzleId);

            puzzleContentService.lambdaUpdate().eq(PuzzleContent::getPuzzleId, puzzleId).remove();
            puzzleContentService.save(puzzleContent);
        }

        //更新关联标签表字段
        if (puzzleUpdateDTO.getLabelList() != null && puzzleUpdateDTO.getLabelList().size() > 0) {
            puzzleLabelService.lambdaUpdate().eq(PuzzleLabel::getPuzzleId, puzzleId).remove();
            List<PuzzleLabelDTO> labelList = puzzleUpdateDTO.getLabelList();
            List<PuzzleLabel> puzzleLabels = BeanUtil.copyToList(labelList, PuzzleLabel.class);
            for (int i = 0; i < puzzleLabels.size(); i++) {
                puzzleLabels.get(i).setPuzzleId(puzzleId);
            }

            puzzleLabelService.saveBatch(puzzleLabels);
        }

        //更新关联选项选择表字段
        if (puzzleUpdateDTO.getPuzzleType() == 1) {
            puzzleOptionsService.lambdaUpdate().eq(PuzzleOptions::getPuzzleId, puzzleId).remove();
            List<PuzzleOptionsDTO> optionList = puzzleUpdateDTO.getOptionList();
            List<PuzzleOptions> puzzleOptionsList = BeanUtil.copyToList(optionList, PuzzleOptions.class);
            for (int i = 0; i < puzzleOptionsList.size(); i++) {
                puzzleOptionsList.get(i).setPuzzleId(puzzleId);
            }
            puzzleOptionsService.saveBatch(puzzleOptionsList);
        }


    }

    @Override
    public void updateStatus(List<Long> ids, Integer status) {
        for (Long id : ids) {
            Puzzle puzzle = new Puzzle();
            puzzle.setPuzzleStatus(status);
            puzzle.setId(id);
            updateById(puzzle);
        }
    }

    @Transactional
    //分页展示谜题
    public PageResult<Puzzle> listPuzzleWithPage(PuzzleQueryPageDTO puzzleQueryPageDTO) {
        String puzzleName = puzzleQueryPageDTO.getPuzzleName();
        Long minPuzzleScore = puzzleQueryPageDTO.getMinPuzzleScore();
        Long maxPuzzleScore = puzzleQueryPageDTO.getMaxPuzzleScore();
        Integer puzzleStatus = puzzleQueryPageDTO.getPuzzleStatus();
        Integer star = puzzleQueryPageDTO.getStar();
        Integer kind = puzzleQueryPageDTO.getPuzzleKinds();
        Integer puzzleType = puzzleQueryPageDTO.getPuzzleType();

        Page<Puzzle> page = Page.of(puzzleQueryPageDTO.getPageNo(), puzzleQueryPageDTO.getPageSize());
        Map<String, Integer> orderItem = puzzleQueryPageDTO.getOrderItem();
        if (orderItem != null && orderItem.size() > 0) {
            Set<Map.Entry<String, Integer>> entries = orderItem.entrySet();
            for (Map.Entry<String, Integer> entry : entries) {
                page = page.addOrder(new OrderItem(entry.getKey(), entry.getValue() == 1));
            }
        }

        Page<Puzzle> puzzlePage = lambdaQuery().like(puzzleName != null, Puzzle::getPuzzleName, puzzleName)
                .ge(minPuzzleScore != null, Puzzle::getPuzzleScore, minPuzzleScore)
                .le(maxPuzzleScore != null, Puzzle::getPuzzleScore, maxPuzzleScore)
                .eq(star != null, Puzzle::getStar, star)
                .eq(kind != null, Puzzle::getPuzzleKinds, kind)
                .eq(puzzleStatus != null, Puzzle::getPuzzleStatus, puzzleStatus)
                .eq(puzzleType != null, Puzzle::getPuzzleType, puzzleType)
                .page(page);

        PageResult<Puzzle> PageResult = new PageResult<>();
        PageResult.setPages((int) puzzlePage.getPages());
        PageResult.setTotal(puzzlePage.getTotal());
        PageResult.setRecords(puzzlePage.getRecords());
        return PageResult;
    }

    @Override
    @Transactional
    public PuzzleDetailedVO listOnePuzzle(Long id) {
        PuzzleDetailedVO puzzleDetailedVO = new PuzzleDetailedVO();
        Puzzle puzzle = getById(id);
        Long puzzleId = puzzle.getId();
        BeanUtil.copyProperties(puzzle, puzzleDetailedVO);

        User user = userService.getById(puzzle.getUserId());
        String userName = user.getNickname();

        PuzzleContent puzzleContent = puzzleContentService.lambdaQuery().eq(PuzzleContent::getPuzzleId, puzzleId).one();
        PuzzleContentDTO puzzleContentDTO = new PuzzleContentDTO();
        BeanUtil.copyProperties(puzzleContent, puzzleContentDTO);

        if (puzzle.getPuzzleType() == 1) {
            List<PuzzleOptions> puzzleOptionsList = puzzleOptionsService.lambdaQuery().eq(PuzzleOptions::getPuzzleId, puzzleId).list();
            List<PuzzleOptionsDTO> puzzleOptionsDTOList = BeanUtil.copyToList(puzzleOptionsList, PuzzleOptionsDTO.class);
            puzzleDetailedVO.setOptionList(puzzleOptionsDTOList);
        }

        List<PuzzleLabel> puzzleLabelList = puzzleLabelService.lambdaQuery().eq(PuzzleLabel::getPuzzleId, puzzleId).list();
        List<PuzzleLabelDTO> puzzleLabelDTOList = BeanUtil.copyToList(puzzleLabelList, PuzzleLabelDTO.class);


        puzzleDetailedVO.setLabelList(puzzleLabelDTOList);
        puzzleDetailedVO.setPuzzleContentDTO(puzzleContentDTO);
        puzzleDetailedVO.setUserName(userName);
        return puzzleDetailedVO;
    }

    @Override
    public void savePuzzleByUser(PuzzleDTO puzzleDTO) {
        Puzzle puzzle = buildAndSavePuzzle(puzzleDTO);
        Long puzzleId = puzzle.getId();
        buildAndSavePuzzleContent(puzzleDTO, puzzleId);
        List<PuzzleLabel> puzzleLabels = BeanUtil.copyToList(puzzleDTO.getLabelList(), PuzzleLabel.class);
        for (int i = 0; i < puzzleLabels.size(); i++) {
            puzzleLabels.get(i).setPuzzleId(puzzleId);
        }

        puzzleLabelService.saveBatch(puzzleLabels);
        List<PuzzleOptionsDTO> optionList = puzzleDTO.getOptionList();
        if (optionList != null && optionList.size() > 0 && puzzleDTO.getPuzzleType() >= 1) {
            List<PuzzleOptions> puzzleOptions = BeanUtil.copyToList(optionList, PuzzleOptions.class);
            for (int i = 0; i < puzzleOptions.size(); i++) {
                puzzleOptions.get(i).setPuzzleId(puzzleId);
            }
            puzzleOptionsService.saveBatch(puzzleOptions);
        }
    }

    private void buildAndSavePuzzleContent(PuzzleDTO puzzleDTO, Long puzzleId) {
        PuzzleContent puzzleContent = new PuzzleContent();
        BeanUtil.copyProperties(puzzleDTO.getPuzzleContentDTO(), puzzleContent);
        puzzleContent.setPuzzleId(puzzleId);
        puzzleContentService.save(puzzleContent);
    }

    private Puzzle buildAndSavePuzzle(PuzzleDTO puzzleDTO) {
        Puzzle puzzle = new Puzzle();
        BeanUtil.copyProperties(puzzleDTO, puzzle);
        if (puzzleDTO.getUserId() == null) {
            puzzle.setUserId(BaseContext.getCurrentId());
        }
        puzzle.setUpdateTime(LocalDateTime.now());
        puzzle.setPuzzleStatus(PuzzleStatus.UNABLE);
        save(puzzle);
        return puzzle;
    }

    @Override
    public List<Puzzle> listPuzzleByUserId(Long id) {


        List<Puzzle> puzzleList = lambdaQuery().eq(Puzzle::getUserId, id).list();

        return puzzleList;
    }

    @Override
    public List<Puzzle> getMoreHotPuzzle() {

        //根据谜题热门程度进行排序
        List<Puzzle> puzzleList = lambdaQuery().orderByDesc(Puzzle::getDisplayOrder).list();
        return puzzleList;
    }

    @Override
    public List<Puzzle> listPuzzleByCondition(PuzzleQueryDTO puzzleQueryDTO) {
        List<Puzzle> puzzleList = lambdaQuery()
                .eq(puzzleQueryDTO.getPuzzleName() != null, Puzzle::getPuzzleName, puzzleQueryDTO.getPuzzleName())
                .eq(puzzleQueryDTO.getPuzzleType() != null, Puzzle::getPuzzleType, puzzleQueryDTO.getPuzzleType())
                .eq(puzzleQueryDTO.getPuzzleKinds() != null, Puzzle::getPuzzleKinds, puzzleQueryDTO.getPuzzleKinds())
                .eq(puzzleQueryDTO.getStar() != null, Puzzle::getStar, puzzleQueryDTO.getStar())
                .list();

        return puzzleList;

    }

    @Override
    public GameMessageVO judgePuzzleOnline(GameMessageDTO gameMessageDTO) {
        Player player = JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(GameKeyEnum.INITIALIZE.getName() + gameMessageDTO.getUserId())), Player.class);
        if (gameMessageDTO.getIsSuccess() != null && gameMessageDTO.getIsSuccess() != 0) {
            //保存游戏历史记录
            GameHistory gameHistory = new GameHistory();
            BeanUtil.copyProperties(player, gameHistory);
            gameHistory.setIsSuccess(gameMessageDTO.getIsSuccess());
            gameHistoryService.save(gameHistory);
            //玩家积分
            userService.lambdaUpdate()
                    .eq(player.getUserId() != null, User::getId, player.getUserId())
                    .setSql("points=points+5").update();

            //游戏结束，删除游戏数据缓存
            redisTemplate.delete(GameKeyEnum.INITIALIZE.getName() + gameMessageDTO.getUserId());
            return null;
        }


        //处理buffs
        handlerBuffs(gameMessageDTO, player);
        //将更新后游戏数据存入缓存
        redisTemplate.opsForValue().set(GameKeyEnum.INITIALIZE.getName() + gameMessageDTO.getUserId(), JSON.toJSONString(player));

        //返回非重连处理结果
        GameMessageVO gameMessageVO = new GameMessageVO(player, false);
        return gameMessageVO;
    }

    @Override
    public boolean judgePuzzleSingle(PuzzleGameDTO puzzleGameDTO) {
        Puzzle puzzle = getById(puzzleGameDTO.getPuzzleId());
        return judgeAnswer(puzzleGameDTO.getAnswer(), puzzle);
    }

    private boolean judgeAnswer(String answer, Puzzle puzzle) {

        return answer.equals(puzzle.getPuzzleAnswer());
    }

    private void handlerBuffs(GameMessageDTO gameMessageDTO, Player player) {
        StartHandler result = SpringUtil.getBean("startHandler");

        Buff startHandler = result;
        //责任链模式处理buff
        if (gameMessageDTO.getBuffs() != null) {
            for (String buff : gameMessageDTO.getBuffs()) {
                Buff Ubuff = (Buff) SpringUtil.getBean(buff);
                startHandler = startHandler.next(Ubuff);
            }
        }

        result.Handler(gameMessageDTO, player);
    }

}




