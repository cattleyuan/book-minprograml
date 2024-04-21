package com.ab.membership.service;


import com.ab.membership.domain.entity.MemberShip;
import com.ab.membership.domain.dto.MembershipDTO;
import com.ab.membership.domain.vo.MembershipVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86150
* @description 针对表【member_ship(会员表)】的数据库操作Service
* @createDate 2024-01-30 23:56:19
*/
public interface MemberShipService extends IService<MemberShip> {

    MembershipVO getMembershipInfo();

    void renewalMembershipDate(MembershipDTO membershipDTO);
}
