package com.ab.partner.mapper;


import com.ab.partner.domain.Partner;

import com.ab.partner.domain.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
* @author 86150
* @description 针对表【partner】的数据库操作Mapper
* @createDate 2024-01-22 15:31:57
* @Entity partner.domain.Partner
*/
@Mapper
public interface PartnerMapper extends BaseMapper<Partner> {

}




