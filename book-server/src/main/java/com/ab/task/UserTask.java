package com.ab.task;

import com.ab.entity.User;
import com.ab.puzzle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserTask {
    private final UserService userService;
    @Scheduled(cron = "0 0 0 * * ?")
    public void handlerUserBirthday(){
        userService.lambdaUpdate().eq(User::getUserBirthday, LocalDate.now())
                .setSql("points=points+100").update();
        log.info("用户生日礼物送达‘’‘");
    }
}
