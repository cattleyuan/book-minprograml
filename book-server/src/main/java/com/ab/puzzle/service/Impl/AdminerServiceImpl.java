package com.ab.puzzle.service.Impl;

import com.ab.dto.AdminLoginDTO;
import com.ab.exception.LoginErrorException;
import com.ab.vo.AdminInfoDTO;
import com.ab.vo.AdminInfoVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ab.entity.Adminer;
import com.ab.puzzle.service.AdminerService;
import com.ab.puzzle.mapper.AdminerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author cattleyuan
 * @description 针对表【adminer(管理员表)】的数据库操作Service实现
 * @createDate 2023-12-08 23:48:02
 */
@Service
@Slf4j
public class AdminerServiceImpl extends ServiceImpl<AdminerMapper, Adminer>
        implements AdminerService {

    @Override
    public Adminer adminLogin(AdminLoginDTO adminLoginDTO) {
        Adminer adminer = lambdaQuery().eq(Adminer::getAdminAccount, adminLoginDTO.getAcount()).one();
        if (adminer == null) {
            throw new LoginErrorException("用户账号或密码错误");
        }
        //md5加密
        String password = DigestUtils.md5DigestAsHex(adminLoginDTO.getPassword().getBytes());
        if (adminer.getAdminPassword().equals(password)) {
            return adminer;
        } else throw new LoginErrorException("用户账号或密码错误");
    }

    @Override
    public AdminInfoVO getAdminInfoById(Long adminId) {
        Adminer adminer = getById(adminId);
        AdminInfoVO adminInfoVO = new AdminInfoVO();
        BeanUtils.copyProperties(adminer,adminInfoVO);
        log.info("获取信息成功");
        return adminInfoVO;
    }

    @Override
    public void updateAdminInfo(AdminInfoDTO adminInfo) {
        Adminer adminer = new Adminer();
        BeanUtils.copyProperties(adminInfo,adminer);
        updateById(adminer);
        log.info("管理员id->{},信息更新成功",adminer.getId());
    }
}




