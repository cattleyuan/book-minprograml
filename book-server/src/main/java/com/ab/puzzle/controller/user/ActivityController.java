package com.ab.puzzle.controller.user;

import com.ab.activity.domain.dto.ActivityAddDTO;
import com.ab.activity.domain.dto.ActivityDTO;
import com.ab.activity.domain.dto.UpdateActivityDTO;
import com.ab.activity.service.ActivityService;
import com.ab.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cattleYuan
 * @date 2024/1/26
 */
@Slf4j
@Validated
@RestController("userActivityController")
@RequiredArgsConstructor
@Api(tags = "活动发布接口")
@RequestMapping("user/activity")
public class ActivityController {
    private final ActivityService activityService;

    @ApiOperation("查找所有活动(0未发布，1已发布，2所有)")
    @GetMapping("/list")
    @Cacheable(cacheNames ="activityinfo",key = "#status")
    public Result<List<ActivityDTO>> listActivity(@RequestParam @NotNull Integer status) {
        List<ActivityDTO> activityDTOList = activityService.listAllActivity(status);
        return Result.success(activityDTOList);
    }

}
