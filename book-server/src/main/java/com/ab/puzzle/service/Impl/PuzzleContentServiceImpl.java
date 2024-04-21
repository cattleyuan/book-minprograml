package com.ab.puzzle.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ab.entity.PuzzleContent;
import com.ab.puzzle.service.PuzzleContentService;
import com.ab.puzzle.mapper.PuzzleContentMapper;
import org.springframework.stereotype.Service;

/**
* @author 86150
* @description 针对表【puzzle_content(谜题内容表)】的数据库操作Service实现
* @createDate 2023-12-07 20:23:30
*/
@Service
public class PuzzleContentServiceImpl extends ServiceImpl<PuzzleContentMapper, PuzzleContent>
    implements PuzzleContentService{

}




