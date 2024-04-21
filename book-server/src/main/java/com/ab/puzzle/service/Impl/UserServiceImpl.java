package com.ab.puzzle.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.ab.context.BaseContext;
import com.ab.dto.UserLoginDTO;
import com.ab.exception.BaseException;
import com.ab.exception.LoginErrorException;
import com.ab.membership.domain.entity.MemberShip;
import com.ab.membership.service.MemberShipService;
import com.ab.partner.domain.vo.SimpleUserVO;
import com.ab.partner.domain.vo.UserVO;
import com.ab.partner.service.PartnerService;
import com.ab.propertities.WeChatProperties;
import com.ab.utils.HttpClientUtil;
import com.ab.vo.UpdateUserDTO;
import com.ab.vo.UserSimpleVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ab.entity.User;
import com.ab.puzzle.service.UserService;
import com.ab.puzzle.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 86150
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2023-12-08 21:40:31
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    public static final String LoginUrl = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private PartnerService partnerService;
    private final WeChatProperties weChatProperties;
    private final MemberShipService memberShipService;
    private final RabbitTemplate rabbitTemplate;
    private final long MEMBER_EXPIRETION_DAY =7;
    @Override
    public User loginByCode(UserLoginDTO userLoginDTO) {

        Map<String, String> map = BuildLoginMap(userLoginDTO);
        String json = HttpClientUtil.doGet(LoginUrl, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = (String) jsonObject.get("openid");
        if (openid == null) {
            throw new LoginErrorException("登录异常");
        }
        User user = lambdaQuery().eq(User::getOpenid, openid).one();
        if (!Optional.ofNullable(user).isPresent()) {

            User newUser = new User();
            registNewUser(userLoginDTO, openid, newUser);
            registMemberShip(newUser);
            return newUser;
        }
        return user;
    }

    private void registMemberShip(User newUser) {
        MemberShip memberShip = new MemberShip();
        memberShip.setUserId(newUser.getId());
        memberShip.setIsActive(1);
        memberShip.setExpirationDate(LocalDateTime.now().plusDays(MEMBER_EXPIRETION_DAY));

        Long timeout = Duration.ofDays(Long.valueOf(MEMBER_EXPIRETION_DAY)).getSeconds();
        Message message = MessageBuilder.withBody(newUser.getId().toString().getBytes(StandardCharsets.UTF_8))
                .setHeader("x-delay", timeout * 1000)
                .build();
        CorrelationData messageId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("member.exchange", "black",message,messageId);

        memberShipService.save(memberShip);
        log.info("会员自动注册成功!");
    }

    @Override
    public void updateUserInfo(UpdateUserDTO updateUserDTO) {
        if(updateUserDTO.getNickname()!=null){
            judgeConvertUserName(updateUserDTO.getNickname());
        }

        Optional.ofNullable(updateUserDTO).orElseThrow(()-> new BaseException("更新字段不能为空"));
        Long userId = BaseContext.getCurrentId();
        User user = new User();
        BeanUtils.copyProperties(updateUserDTO, user);
        user.setId(userId);
        updateById(user);
        log.info("用户信息更新成功-id:{}",userId);
    }

    @Override
    public SimpleUserVO findPartnerByNameorPhone(String findInfo) {
        User user = lambdaQuery().eq(!Optional.ofNullable(findInfo).isEmpty(), User::getNickname, findInfo)
                .or()
                .eq(!Optional.ofNullable(findInfo).isEmpty(), User::getUserPhone, findInfo)
                .one();
        Optional.ofNullable(user).orElseThrow(()->new BaseException("用户不存在"));
        SimpleUserVO userVO = BeanUtil.copyProperties(user, SimpleUserVO.class);
        return userVO;

    }

    @Override
    public UserSimpleVO getSimpleUserInfo(Long id) {
        Long userId = BaseContext.getCurrentId();
        User user = getById(userId);
        Optional.ofNullable(user).orElseThrow(()->new BaseException("用户不存在"));

        User stranger = getById(id);
        UserSimpleVO userSimpleVO = new UserSimpleVO();
        BeanUtil.copyProperties(stranger,userSimpleVO);

        List<UserVO> voList = partnerService.queryPartner(1);

        UserVO userVO = voList.stream().filter(data -> !data.getUser().getId().equals(id)).findFirst().orElseGet(()->null);

        if(!Objects.nonNull(userVO))
            userSimpleVO.setIsFriend(0);
        else
            userSimpleVO.setIsFriend(1);

        return userSimpleVO;
    }

    private Map<String, String> BuildLoginMap(UserLoginDTO userLoginDTO) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", userLoginDTO.getCode());
        map.put("grant_type", "authorization_code");
        return map;
    }

    private void registNewUser(UserLoginDTO userLoginDTO, String openid, User newUser) {
        //判断是否重名
        judgeConvertUserName(userLoginDTO.getNickname());
        //构建新用户信息
        newUser.setOpenid(openid);
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setAvatarurl(userLoginDTO.getAvatarurl());
        newUser.setNickname(userLoginDTO.getNickname());
        newUser.setGender(userLoginDTO.getGender());
        newUser.setUserBirthday(LocalDate.now());
        save(newUser);
    }

    private void judgeConvertUserName(String nickname) {
        User user = lambdaQuery().eq(nickname != null, User::getNickname, nickname).one();
        if(!Objects.isNull(user)){
            throw new BaseException("用户昵称已存在,请重新输入!");
        }
    }
}




