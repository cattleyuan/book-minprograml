package com.ab.dynamic.service;


import com.ab.dynamic.domain.Dynamic;
import com.ab.dynamic.domain.dto.DynamicDTO;
import com.ab.dynamic.domain.dto.DynamicUpdateDTO;
import com.ab.dynamic.domain.dto.PageDynamicDTO;
import com.ab.dynamic.domain.vo.DynamicVO;
import com.ab.result.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 86150
* @description 针对表【dynamic】的数据库操作Service
* @createDate 2024-03-16 11:21:49
*/
public interface DynamicService extends IService<Dynamic> {

    void updateThumbsDynamicById(Long id);

    void addDynamic(DynamicDTO dynamicDTO);

    void udpateDynamicById(DynamicUpdateDTO dynamicUpdateDTO);

    void removeDynamicById(Long id);

    PageResult<DynamicVO> queryDynamicList(PageDynamicDTO pageDynamicDTO);

    Integer getDynamicCTNumber(Long id);
}
