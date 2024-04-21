package com.ab.partner.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ab.context.BaseContext;
import com.ab.entity.User;
import com.ab.partner.domain.Partner;
import com.ab.partner.domain.dto.AddPartnerDTO;

import com.ab.partner.domain.dto.DeletePartnerDTO;
import com.ab.partner.domain.dto.UpdateRemarkDTO;
import com.ab.partner.domain.vo.UserVO;
import com.ab.partner.mapper.PartnerMapper;
import com.ab.partner.service.PartnerService;

import com.ab.puzzle.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 86150
 * @description 针对表【partner】的数据库操作Service实现
 * @createDate 2024-01-22 15:31:57
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerServiceImpl extends ServiceImpl<PartnerMapper, Partner>
        implements PartnerService {

    private final PartnerMapper partnerMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<UserVO> queryPartner(Integer status) {
        Long userId = BaseContext.getCurrentId();
        List<Partner> partnerList = lambdaQuery()
                .select(Partner::getFrdId, Partner::getRemark,Partner::getComment)
                .eq(Partner::getUserId, userId)
                .eq(Partner::getStatus, status)
                .list();

        List<UserVO> userVoList = new ArrayList<>();
        for (Partner partner : partnerList) {
            UserVO userVO = new UserVO();
            userVO.setUser(userService.getById(partner.getFrdId()));
            userVO.setRemark(partner.getRemark());
            userVO.setComment(partner.getComment());
            userVoList.add(userVO);
        }
        log.info("查找成功->{}", userVoList);
        return userVoList;
    }

    @Override
    public void addPartner(AddPartnerDTO addPatrnerDTO) {
        Long userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        //发出请求
        Partner partner = BuildFrdRequest(addPatrnerDTO, userId, user);
        log.info("保存成功->{}", partner);
    }

    private Partner BuildFrdRequest(AddPartnerDTO addPatrnerDTO, Long userId, User user) {
        Partner partner = new Partner();
        partner.setUserId(addPatrnerDTO.getFrdId());
        partner.setFrdId(userId);
        partner.setComment(addPatrnerDTO.getComment());
        partner.setRemark(user.getNickname());
        save(partner);
        return partner;
    }

    @Override
    public void deletePartner(Long freId) {
        Long userId = BaseContext.getCurrentId();

        List<Partner> partnerList = lambdaQuery()
                .and(data-> data.eq(Partner::getFrdId, freId).eq(Partner::getUserId, userId))
                .or()
                .and(data-> data.eq(Partner::getFrdId, userId).eq(Partner::getUserId, freId)).list();

        removeByIds(partnerList);
        log.info("删除成功");
    }

    @Override
    public void updateRemark(UpdateRemarkDTO updateRemarkDTO) {
        Long frdId = updateRemarkDTO.getFrdId();
        Long userId = BaseContext.getCurrentId();
        if (updateRemarkDTO.getRemark() != null) {
            lambdaUpdate()
                    .eq(Partner::getUserId, userId)
                    .eq(Partner::getFrdId, frdId)
                    .set(updateRemarkDTO.getRemark() != null, Partner::getRemark, updateRemarkDTO.getRemark())
                    .update();
            log.info("更新备注");
        }
        if (updateRemarkDTO.getStatus() == -1) {
            lambdaUpdate()
                    .eq(Partner::getUserId, userId)
                    .eq(Partner::getFrdId, frdId)
                    .set(Partner::getStatus, updateRemarkDTO.getStatus()).update();
            log.info("拒绝成功!");
            return;
        }

        if(updateRemarkDTO.getStatus()==1){
            //更新好友申请状态
            lambdaUpdate()
                    .eq(Partner::getUserId, userId)
                    .eq(Partner::getFrdId, frdId)
                    .set(Partner::getStatus, updateRemarkDTO.getStatus()).update();
            //出现在被同意好友列表
            User user = userService.getById(frdId);
            Partner partner = new Partner();
            partner.setRemark(user.getNickname());
            partner.setUserId(frdId);
            partner.setFrdId(userId);
            partner.setStatus(1);
            save(partner);
        }

    }
}




