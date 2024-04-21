package com.ab.partner.service;


import com.ab.partner.domain.Partner;
import com.ab.partner.domain.dto.AddPartnerDTO;
import com.ab.partner.domain.dto.DeletePartnerDTO;
import com.ab.partner.domain.dto.UpdateRemarkDTO;
import com.ab.partner.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【partner】的数据库操作Service
* @createDate 2024-01-22 15:31:57
*/
public interface PartnerService extends IService<Partner> {

    List<UserVO> queryPartner(Integer status);

    void addPartner(AddPartnerDTO addPatrnerDTO);

    void deletePartner(Long freId);

    void updateRemark(UpdateRemarkDTO updateRemarkDTO);
}
