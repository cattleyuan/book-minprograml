package com.ab.puzzle.service;

import com.ab.dto.LabelDTO;
import com.ab.entity.Label;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【label(官方标签表)】的数据库操作Service
* @createDate 2023-12-07 20:16:36
*/
public interface LabelService extends IService<Label> {

    void saveBatchLabel(List<LabelDTO> labelDTOList);

    void updateLabelById(LabelDTO labelDTO);


}
