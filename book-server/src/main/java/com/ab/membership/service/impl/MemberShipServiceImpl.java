package com.ab.membership.service.impl;


import com.ab.context.BaseContext;
import com.ab.membership.domain.entity.MemberShip;
import com.ab.membership.domain.dto.MembershipDTO;
import com.ab.membership.domain.vo.MembershipVO;
import com.ab.membership.mapper.MemberShipMapper;
import com.ab.membership.service.MemberShipService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

/**
* @author 86150
* @description 针对表【member_ship(会员表)】的数据库操作Service实现
* @createDate 2024-01-30 23:56:19
*/
@Service
@RequiredArgsConstructor
public class MemberShipServiceImpl extends ServiceImpl<MemberShipMapper, MemberShip>
    implements MemberShipService {
    private final RabbitTemplate rabbitTemplate;
    @Override
    public MembershipVO getMembershipInfo() {
        Long userId = BaseContext.getCurrentId();
        MemberShip memberShip = lambdaQuery().eq(userId != null, MemberShip::getUserId, userId).one();
        MembershipVO membershipVO = new MembershipVO();
        BeanUtils.copyProperties(memberShip,membershipVO);

        return membershipVO;
    }

    @Override
    public void renewalMembershipDate(MembershipDTO membershipDTO) {
        Long userId = BaseContext.getCurrentId();
        LocalDateTime plusDays = membershipDTO.getExpirationDate().plusDays(membershipDTO.getActiveDay());
        Long timeout = Duration.between(LocalDateTime.now(),plusDays).getSeconds();
        //更新激活状态
        lambdaUpdate().eq(MemberShip::getUserId,userId).set(MemberShip::getIsActive,1).set(MemberShip::getExpirationDate,plusDays).update();
        //设置消息唯一id
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        Message message = MessageBuilder.withBody(userId.toString().getBytes(StandardCharsets.UTF_8))
                .setHeader("x-delay", timeout * 1000)
                .build();
        //设置自动到期
        rabbitTemplate.convertAndSend("member.exchange","black",message,correlationData);

    }
}




