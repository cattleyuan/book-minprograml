package com.ab.puzzle.controller.user;

import cn.hutool.core.lang.func.VoidFunc0;
import com.ab.context.BaseContext;
import com.ab.dto.UserLoginDTO;
import com.ab.entity.User;
import com.ab.partner.domain.vo.UserVO;
import com.ab.propertities.JwtProperties;
import com.ab.result.Result;
import com.ab.puzzle.service.UserService;
import com.ab.utils.JwtUtil;
import com.ab.vo.UpdateUserDTO;
import com.ab.vo.UserLoginVO;
import com.ab.vo.UserSimpleVO;
import com.sun.jdi.LongValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "用户类登录注册类接口")
public class UserController {
    private final UserService userService;
    private final JwtProperties jwtProperties;

    @PutMapping("/wxlogin")
    @ApiOperation("用户微信授权登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        User user=userService.loginByCode(userLoginDTO);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("user_id",user.getId());
        String wxtoken = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        UserLoginVO userLoginVO = BuildUserLoginVO(user, wxtoken);
        return Result.success(userLoginVO);
    }

    @GetMapping("/getuserinfo")
    @ApiOperation("获取用户详细信息")
    /*@Cacheable(cacheNames = "userdetailinfo",key = "#result.data.id")*/
    public Result<User> getUserInfo(){
        Long id= BaseContext.getCurrentId();
        User user = userService.getById(id);
        log.info("用户信息->{}",user);
        return Result.success(user);
    }


    @GetMapping("/getortherInfo")
    @ApiOperation("根据id获取用户简略信息")
    @Cacheable(cacheNames = "usersimpleinfo",key = "#id")
    public Result<UserSimpleVO> getOthersInfo(@RequestParam Long id){
        UserSimpleVO userSimpleVO=userService.getSimpleUserInfo(id);
        return Result.success(userSimpleVO);
    }

    @PostMapping("/update")
    @CacheEvict(cacheNames = {"usersimpleinfo","userdetailinfo"},allEntries = true)
    @ApiOperation("更新用户信息")
    public Result updateUserInfo(@RequestBody @NotNull(message = "参数不能为null") UpdateUserDTO updateUserDTO){
        userService.updateUserInfo(updateUserDTO);
        return Result.success();
    }

    private UserLoginVO BuildUserLoginVO(User user, String wxtoken) {
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setId(user.getId());
        userLoginVO.setOpenid(user.getOpenid());
        userLoginVO.setWxtoken(wxtoken);
        return userLoginVO;
    }
}
