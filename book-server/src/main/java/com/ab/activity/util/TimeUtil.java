package com.ab.activity.util;

import com.ab.activity.domain.Activity;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/1/27
 */
public class TimeUtil {
    public static Long getDuration(Activity activity,Integer ant) {
        Long time=null;
        LocalDateTime currentTime=LocalDateTime.now();
        if(ant==0){
            LocalDateTime publishTime = activity.getPublishTime();
            time= Duration.between(currentTime, publishTime).getSeconds();
        }
        else {
            LocalDateTime overTime=activity.getEndTime();
            time= Duration.between(currentTime, overTime).getSeconds();
        }


        return time;
    }
}
