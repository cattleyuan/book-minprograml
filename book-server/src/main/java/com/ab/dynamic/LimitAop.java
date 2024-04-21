package com.ab.dynamic;

import com.ab.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author cattleYuan
 * @date 2024/3/24
 */
@Component
@Aspect
@Slf4j
public class LimitAop {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Pointcut("@annotation(com.ab.dynamic.LImit)")
    public void cut(){}

    @Around("cut()")
    public Object test(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        LImit annotation = methodSignature.getMethod().getAnnotation(LImit.class);
        String methodeName = methodSignature.getName();
        int count = annotation.count();
        long period = annotation.period();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //构建key
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String key="Limit_Key"+ip+uri;
        long millis = System.currentTimeMillis();
        stringRedisTemplate.opsForZSet().add(key,String.valueOf(millis),millis);
        stringRedisTemplate.expire(key,period, TimeUnit.SECONDS);
        //滑动窗口限流，清除之前的访问数据
        stringRedisTemplate.opsForZSet().removeRangeByScore(key,0,millis-period*1000);

        Long number = stringRedisTemplate.opsForZSet().zCard(key);
        if(number>count){
            log.info("ip->{},接口->{}限流",ip,methodeName);
            throw new BaseException("请勿频繁访问");
        }

        return proceedingJoinPoint.proceed();

    }
}
