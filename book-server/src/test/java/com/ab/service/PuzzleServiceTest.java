package com.ab.service;

import com.ab.dto.*;
import com.ab.entity.PuzzleContent;
import com.ab.puzzle.service.PuzzleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PuzzleServiceTest {
    @Autowired
    private PuzzleService puzzleService;
  /*  @Test
    void savePuzzle() {
        PuzzleContentDTO puzzleContentDTO = new PuzzleContentDTO();
        puzzleContentDTO.setContentText("你是猪吗？");
        List<PuzzleLabelDTO> puzzleLabelDTOS = new ArrayList<>();
        PuzzleLabelDTO labelDTO = new PuzzleLabelDTO();
        labelDTO.setLabelName("猪猪侠");
        puzzleLabelDTOS.add(labelDTO);
        List<PuzzleOptionsDTO> puzzleOptionsDTOS = new ArrayList<>();
        PuzzleDetailedDTO puzzleDetailedDTO = PuzzleDetailedDTO.builder()
                .userId(0L)
                .star(5)
                .puzzleType(0)
                .puzzleContentDTO(puzzleContentDTO)
                .orderFlag(10L)
                .puzzleScore(20L)
                .puzzleAnswer("是")
                .puzzleName("是猪猪哎")
                .labelList(puzzleLabelDTOS)
                .clue("不是英雄")
                .build();
        puzzleService.savePuzzle(puzzleDetailedDTO);*/


    //}

    @Test
    void removePuzzle() {
        puzzleService.removePuzzle(List.of(1L,2L,3L));

    }

  /*  @Test
    void saveBlanchPuzzle() {
        PuzzleContentDTO puzzleContentDTO = new PuzzleContentDTO();
        puzzleContentDTO.setContentText("你是猪吗？");
        //添加标签
        List<PuzzleLabelDTO> puzzleLabelDTOS = new ArrayList<>();
        PuzzleLabelDTO labelDTO = new PuzzleLabelDTO();
        labelDTO.setLabelName("猪猪侠");
        puzzleLabelDTOS.add(labelDTO);
        //添加选项内容
        List<PuzzleOptionsDTO> puzzleOptionsDTOS = new ArrayList<>();
        PuzzleOptionsDTO puzzleOptionsDTO1 = new PuzzleOptionsDTO();
        puzzleOptionsDTO1.setSelectContent("牛的，牛的");
        puzzleOptionsDTO1.setTbSelect("B");
        PuzzleOptionsDTO puzzleOptionsDTO2 = new PuzzleOptionsDTO();
        puzzleOptionsDTO2.setSelectContent("nbnb的");
        puzzleOptionsDTO2.setTbSelect("C");
        puzzleOptionsDTOS.add(puzzleOptionsDTO1);
        puzzleOptionsDTOS.add(puzzleOptionsDTO2);


                .userId(0L)
                .star(5)
                .puzzleType(1)
                .puzzleContentDTO(puzzleContentDTO)
                .orderFlag(10L)
                .puzzleScore(20L)
                .puzzleAnswer("是")
                .puzzleName("是猪猪哎")
                .labelList(puzzleLabelDTOS)
                .optionList(puzzleOptionsDTOS)
                .puzzleKinds(1)
                .clue("爱吃苹果")
                .build();
        PuzzleDetailedDTO puzzleDetailedDTO2 = PuzzleDetailedDTO.builder()
                .userId(0L)
                .star(2)
                .puzzleType(0)
                .puzzleContentDTO(puzzleContentDTO)
                .orderFlag(15L)
                .puzzleScore(30L)
                .puzzleAnswer("是")
                .puzzleName("是鸡鸡哎")
                .puzzleKinds(1)
                .clue("爱吃香蕉")
                .labelList(puzzleLabelDTOS)
                .build();
        List<PuzzleDetailedDTO> puzzleDetailedDTOS = new ArrayList<>();
        puzzleDetailedDTOS.add(puzzleDetailedDTO1);
        puzzleDetailedDTOS.add(puzzleDetailedDTO2);
        puzzleService.saveBlanchPuzzle(puzzleDetailedDTOS);*/
  /*  }

    @Test
    void updatePuzzle() {
        PuzzleContent puzzleContent = new PuzzleContent();
        puzzleContent.setPuzzleId(5L);
        puzzleContent.setId(4L);
        puzzleContent.setContentText("GGBOY");

        //添加标签
        List<PuzzleLabelDTO> puzzleLabelDTOS = new ArrayList<>();
        PuzzleLabelDTO labelDTO = new PuzzleLabelDTO();
        labelDTO.setLabelName("猪猪侠");
        puzzleLabelDTOS.add(labelDTO);
        //添加选项内容
        List<PuzzleOptionsDTO> puzzleOptionsDTOS = new ArrayList<>();
        PuzzleOptionsDTO puzzleOptionsDTO1 = new PuzzleOptionsDTO();
        puzzleOptionsDTO1.setSelectContent("牛的，牛的");
        puzzleOptionsDTO1.setTbSelect("A");
        PuzzleOptionsDTO puzzleOptionsDTO2 = new PuzzleOptionsDTO();
        puzzleOptionsDTO2.setSelectContent("nbnb的");
        puzzleOptionsDTO2.setTbSelect("A");
        puzzleOptionsDTOS.add(puzzleOptionsDTO1);
        puzzleOptionsDTOS.add(puzzleOptionsDTO2);

        *//*PuzzleUpdateDTO puzzleUpdateDTO=PuzzleUpdateDTO.builder()
                .id(5L)
                .userId(0L)
                .star(5)
                .puzzleType(1)
                .puzzleContent(puzzleContent)
                .orderFlag(10L)
                .puzzleScore(20L)
                .puzzleAnswer("是")
                .puzzleName("是bug哎")
                .labelList(puzzleLabelDTOS)
                .optionList(puzzleOptionsDTOS)
                .puzzleKinds(1)
                .build();
        puzzleService.updatePuzzle(puzzleUpdateDTO);
*//*
    }*/
}