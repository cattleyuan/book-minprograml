package com.ab.puzzle.controller.user;

import com.ab.context.BaseContext;
import com.ab.partner.domain.dto.AddPartnerDTO;
import com.ab.partner.domain.dto.DeletePartnerDTO;
import com.ab.partner.domain.dto.UpdateRemarkDTO;
import com.ab.partner.domain.vo.SimpleUserVO;
import com.ab.partner.domain.vo.UserVO;
import com.ab.partner.service.PartnerService;
import com.ab.puzzle.service.UserService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/22
 */
@Slf4j

@RestController
@RequiredArgsConstructor
@Api(tags="朋友模块接口")
@RequestMapping("/user/partner")
@Validated
public class PartnerController {
    private final PartnerService partnerService;
    private final UserService userService;
    @GetMapping("/list")
    @ApiOperation("通过添加状态查询朋友列表")
    public Result<List<UserVO>> queryPartner(@RequestParam @NotNull(message = "status不能为空") @ApiParam("0(待同意列表)，1（已添加）") Integer status){
        Long userId= BaseContext.getCurrentId();
        log.info("朋友查询->id:{},status:{}",userId,status);
        List<UserVO> partnerList= partnerService.queryPartner(status);

        return Result.success(partnerList);
    }

    @PostMapping("/add")
    @ApiOperation("通过要添加朋友id和留言添加朋友")
    public Result addPartner(@Valid @RequestBody AddPartnerDTO addPatrnerDTO) {
        log.info("添加信息->partner{}",addPatrnerDTO);
        partnerService.addPartner(addPatrnerDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("根据朋友id删除彼此")
    public Result deletePartner( @RequestParam @NotNull Long freId){
        partnerService.deletePartner(freId);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改好友备注或申请状态")
    public Result updatePartnerInfo(@RequestBody UpdateRemarkDTO updateRemarkDTO){
        partnerService.updateRemark(updateRemarkDTO);
        return Result.success();
    }

    @GetMapping("/")
    @ApiOperation("根据用户姓名或手机号查找用户")
    public Result<SimpleUserVO> findPartner(@RequestParam @ApiParam("手机号或姓名") String findInfo){
         SimpleUserVO userVO=userService.findPartnerByNameorPhone(findInfo);
        return Result.success(userVO);
    }
}
