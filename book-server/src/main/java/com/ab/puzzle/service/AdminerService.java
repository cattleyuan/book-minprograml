package com.ab.puzzle.service;

import com.ab.dto.AdminLoginDTO;
import com.ab.entity.Adminer;
import com.ab.vo.AdminInfoDTO;
import com.ab.vo.AdminInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86150
* @description 针对表【adminer(管理员表)】的数据库操作Service
* @createDate 2023-12-08 23:48:02
*/
public interface AdminerService extends IService<Adminer> {

    Adminer adminLogin(AdminLoginDTO adminLoginDTO);

    AdminInfoVO getAdminInfoById(Long adminId);

    void updateAdminInfo(AdminInfoDTO adminInfo);
}
