package com.ab.interpretor;

import com.ab.context.BaseContext;
import com.ab.exception.BaseException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.ab.propertities.JwtProperties;
import com.ab.utils.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class AdminInterpretor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        try {
           String token = request.getHeader(jwtProperties.getAdminTokenName());
           if(token==null){
               throw new BaseException("请求头token为空");
           }
            //解析token
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);

            //获取adminId
            Object id = claims.get("admin_id");
            Optional.ofNullable(id).orElseThrow(()->new BaseException("token异常"));
            Long adminId = Long.valueOf(id.toString());
            //设置当前管理端用户id
            BaseContext.setCurrentId(adminId);
        } catch (NumberFormatException e) {
            log.error("管理员id格式错误->{}",e.getMessage());
            response.setStatus(403);
        }


        return true;
    }
}
