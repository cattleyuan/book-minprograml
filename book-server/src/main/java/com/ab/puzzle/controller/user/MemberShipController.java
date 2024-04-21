package com.ab.puzzle.controller.user;

import com.ab.membership.domain.dto.MembershipDTO;
import com.ab.membership.domain.vo.MembershipVO;
import com.ab.membership.service.MemberShipService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/user/membership")
@Api(tags = "会员相关接口")
public class MemberShipController {
    private final MemberShipService memberShipService;
    @GetMapping("/getMsInfo")
    @ApiOperation("获取用户会员信息")
    public Result<MembershipVO> getMembershipInfo(){
        MembershipVO membershipVO=memberShipService.getMembershipInfo();
        log.info("会员信息->{}",membershipVO);
        return Result.success(membershipVO);
    }
    @PutMapping("/Renewal")
    @ApiOperation("用户会员续费")
    public Result renewalMembership(@RequestBody MembershipDTO membershipDTO){
        memberShipService.renewalMembershipDate(membershipDTO);
        return Result.success();
    }

}
