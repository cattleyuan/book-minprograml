package com.ab.membership.listener;

import com.ab.membership.domain.entity.MemberShip;

import com.ab.membership.service.MemberShipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MembershipListener {
    private final MemberShipService memberShipService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "member.queue", durable = "true"),
            exchange = @Exchange(name = "member.exchange", delayed = "true", durable = "true"),
            key = {"black"}
    ))
    public void handlerRenewalMembership(Long userId) {
        log.info("用户过期验证->{}",userId);
        MemberShip memberShip = memberShipService.getById(userId);
        if(memberShip.getExpirationDate().compareTo(LocalDateTime.now())<=0){
            memberShipService.lambdaUpdate()
                    .eq(userId != null, MemberShip::getUserId, userId)
                    .set(MemberShip::getIsActive, 0).update();
            log.info("用户->{}-会员已过期，",userId);
            return;
        }


    }
}
