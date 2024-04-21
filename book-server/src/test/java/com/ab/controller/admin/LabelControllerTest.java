package com.ab.controller.admin;

import com.ab.dto.LabelDTO;
import com.ab.entity.Label;
import com.ab.puzzle.service.LabelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LabelControllerTest {
    @Autowired
    private LabelService labelService;
    @Test
    void listAll() {
        List<Label> labelList = labelService.list();
        for (Label label : labelList) {
            System.out.println(label);
        }
    }

    @Test
    void updateLabel() {
        LabelDTO label = new LabelDTO();
        label.setLabelName("搞笑");
        label.setId(1L);
        labelService.updateLabelById(label);
    }

    @Test
    void saveBatchLabel() {
        List<LabelDTO> labelDTOList=new ArrayList<>();
        LabelDTO labelDTO1 = new LabelDTO();
        labelDTO1.setLabelName("答辩");
        LabelDTO labelDTO2 = new LabelDTO();
        labelDTO2.setLabelName("牛马");
        labelDTOList.add(labelDTO1);
        labelDTOList.add(labelDTO2);
        labelService.saveBatchLabel(labelDTOList);
    }

    @Test
    void deleteBatchLabel() {
    }
}