package com.ab.dcomment.service;


import com.ab.dcomment.domain.DynamicComment;
import com.ab.dcomment.domain.dto.DynamicCommentDTO;
import com.ab.dcomment.domain.vo.DynamicCommentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【dynamic_comment】的数据库操作Service
* @createDate 2024-03-16 11:22:06
*/
public interface DynamicCommentService extends IService<DynamicComment> {

    void addDyComment(DynamicCommentDTO commentDTO);

    List<DynamicCommentVO> queryAllDyComment(Long id);

    Integer getNumberOfDComment(Long id);
}
