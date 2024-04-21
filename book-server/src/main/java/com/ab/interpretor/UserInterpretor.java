package com.ab.interpretor;

import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import com.ab.exception.NoneThePuzzle;
import com.ab.exception.NoneTokenException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.ab.propertities.JwtProperties;
import com.ab.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@Slf4j
public class UserInterpretor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        try {
            String token = request.getHeader(jwtProperties.getUserTokenName());
            if(token==null){
                log.error("请求头token为空->{}",request.getRequestURL());
                throw new NoneTokenException("请求头token为空");
            }
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Object id = claims.get("user_id");
            Optional.ofNullable(id).orElseThrow(()-> new NoneTokenException("请求头token异常"));
            Long userId = Long.valueOf(id.toString());
            BaseContext.setCurrentId(userId);
        } catch (NumberFormatException e) {
            log.error("user_id格式错误");
            response.setStatus(401);
        }

        return true;
    }
}
