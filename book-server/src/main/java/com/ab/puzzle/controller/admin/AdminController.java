package com.ab.puzzle.controller.admin;

import com.ab.context.BaseContext;
import com.ab.dto.AdminLoginDTO;
import com.ab.dynamic.LImit;
import com.ab.entity.Adminer;
import com.ab.propertities.JwtProperties;
import com.ab.result.Result;
import com.ab.puzzle.service.AdminerService;
import com.ab.utils.JwtUtil;
import com.ab.vo.AdminInfoDTO;
import com.ab.vo.AdminInfoVO;
import com.ab.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
@Api(tags = "管理端登录注册类接口")
public class AdminController {
    private final AdminerService adminerService;
    private final JwtProperties jwtProperties;
    @PostMapping("/admin/login")
    @LImit
    @ApiOperation("管理员登录")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO){
        Adminer adminer=adminerService.adminLogin(adminLoginDTO);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("admin_id",adminer.getId());
        log.info("->{}登录",claims);
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);
        AdminLoginVO adminLoginVO = buildAdminLoginVO(adminer, token);
        return Result.success(adminLoginVO);
    }

    @GetMapping("/admin/getInfo")
    @ApiOperation("获取管理员信息")
    public Result<AdminInfoVO> getAdminInfo(){
        Long adminId= BaseContext.getCurrentId();
        AdminInfoVO adminInfoVO=adminerService.getAdminInfoById(adminId);
        return Result.success(adminInfoVO);
    }

    @PutMapping("/admin/update")
    @ApiOperation("更新管理员信息")
    public Result updateAdminInfo(@RequestBody AdminInfoDTO adminInfo){

        adminerService.updateAdminInfo(adminInfo);
        return Result.success();
    }

    private AdminLoginVO buildAdminLoginVO(Adminer adminer, String token) {
        AdminLoginVO adminLoginVO = new AdminLoginVO();
        adminLoginVO.setAdminId(adminer.getId());
        adminLoginVO.setToken(token);
        adminLoginVO.setAdminName(adminer.getAdminName());
        return adminLoginVO;
    }


}
