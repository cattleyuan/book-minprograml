package com.ab.activity.service;


import com.ab.activity.domain.Activity;
import com.ab.activity.domain.dto.ActivityAddDTO;
import com.ab.activity.domain.dto.ActivityDTO;
import com.ab.activity.domain.dto.UpdateActivityDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86150
* @description 针对表【activity】的数据库操作Service
* @createDate 2024-01-26 21:58:42
*/
public interface ActivityService extends IService<Activity> {

    List<ActivityDTO> listAllActivity(Integer status);

    void addActivity(ActivityAddDTO activityAddDTO);

    void deleteActivityById(List<Long> ids);

    void reUpdateActivity(UpdateActivityDTO updateActivityDTO);
}
