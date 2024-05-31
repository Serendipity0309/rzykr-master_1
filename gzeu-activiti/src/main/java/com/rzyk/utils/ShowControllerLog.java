package com.rzyk.utils;

import com.alibaba.fastjson2.JSONObject;
import com.rzyk.common.constant.HttpStatus;
import com.rzyk.common.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;
@Aspect
@Component
@Slf4j
public class ShowControllerLog {
    @Pointcut("execution(* com.rzyk.controller..*.*(..))")
    public void aopex(){}

    @Around("aopex()")
    public Object ShowControllerLogAll(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();

        Object proceed = joinPoint.proceed();
        if(proceed instanceof AjaxResult){
            AjaxResult result=(AjaxResult)proceed;
            if (ObjectUtils.isEmpty(result.get("data"))) proceed=new AjaxResult(HttpStatus.NO_CONTENT,"无数据");
        }
        log.info("\nShowMethodLogAop---->【{}】调用了【{}】，参数：【{}】\n 返回数据：【{}】",new Date(),joinPoint.getSignature().getName(),args, proceed);
        return proceed;
    }
}
