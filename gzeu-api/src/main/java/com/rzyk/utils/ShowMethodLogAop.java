package com.rzyk.utils;

import com.alibaba.fastjson2.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class ShowMethodLogAop {
    @Around("@annotation(com.rzyk.utils.ShowMethod)")
    public Object ShowMethodLogAop(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();

        final Object proceed = joinPoint.proceed();
        log.info("\nShowMethodLogAop---->【{}】调用了【{}】，参数：【{}】\n 返回数据：【{}】\n JSON:【{}】",
                new Date(),
                joinPoint.getSignature().getName(),
                args, proceed, JSONObject.parseObject(proceed.toString()));
        log.info("JSon:{}",JSONObject.parseObject(proceed.toString()));
        return proceed;
    }

}
