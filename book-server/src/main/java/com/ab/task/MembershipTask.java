package com.ab.task;

import com.ab.membership.service.MemberShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@Component
@RequiredArgsConstructor
public class MembershipTask {
    private final MemberShipService memberShipService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void inproveMsPointsByDay(){
        memberShipService.lambdaUpdate().setSql("member_points=member_points+5*(member_type+1)").update();
    }


}
