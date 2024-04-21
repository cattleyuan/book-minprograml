package com.ab.puzzle.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.ab.dto.LabelDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ab.entity.Label;
import com.ab.puzzle.service.LabelService;
import com.ab.puzzle.mapper.LabelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author 86150
* @description 针对表【label(官方标签表)】的数据库操作Service实现
* @createDate 2023-12-07 20:16:36
*/
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label>
    implements LabelService{

    @Override
    public void saveBatchLabel(List<LabelDTO> labelDTOList) {
        Label label = new Label();
        List<Label> labelList = BeanUtil.copyToList(labelDTOList, Label.class);
        for (int i = 0; i < labelList.size(); i++) {
            label=labelList.get(i);
        }

        saveBatch(labelList);
    }

    @Override
    public void updateLabelById(LabelDTO labelDTO) {
        Label label = new Label();
        BeanUtil.copyProperties(labelDTO,label);
        label.setUpdateTime(LocalDateTime.now());
        updateById(label);
    }
}




