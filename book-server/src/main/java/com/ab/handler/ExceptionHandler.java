package com.ab.handler;

import com.ab.exception.NoneTokenException;
import com.ab.result.Result;
import com.ab.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception ex, HttpServletRequest request){
        log.error("请求->{},错误信息->{}:",request.getRequestURL(),ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public Result  ValidationHandler(ConstraintViolationException e,HttpServletRequest request){
        String message = e.getConstraintViolations().stream().filter(Objects::nonNull).map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        log.error("请求->{},自定义注解错误信息->{}",request.getRequestURL(),message);
        return Result.error(message);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NoneTokenException.class)
    public Result TokenExceptionHandler(Exception ex, HttpServletRequest request){
        log.error("请求->{},错误信息->{}:",request.getRequestURL(),ex.getMessage());
        return Result.errorWithCode(ex.getMessage(),666);
    }
}
