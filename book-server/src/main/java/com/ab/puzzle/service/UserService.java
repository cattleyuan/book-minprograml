package com.ab.puzzle.service;

import com.ab.dto.UserLoginDTO;
import com.ab.entity.User;
import com.ab.partner.domain.vo.SimpleUserVO;
import com.ab.vo.UpdateUserDTO;
import com.ab.vo.UserSimpleVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86150
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2023-12-08 21:40:31
*/
public interface UserService extends IService<User> {

    User loginByCode(UserLoginDTO userLoginDTO);

    void updateUserInfo(UpdateUserDTO updateUserDTO);

    SimpleUserVO findPartnerByNameorPhone(String findInfo);

    UserSimpleVO getSimpleUserInfo(Long id);
}
