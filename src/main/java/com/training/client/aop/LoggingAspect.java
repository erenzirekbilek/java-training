package com.training.client.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.training.client.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing: {}.{}()", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.training.client.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Completed: {}.{}() = {}", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            result);
    }

    @Around("execution(* com.training.client.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            logger.info("Execution time: {} ms", elapsed);
            return result;
        } catch (Exception e) {
            logger.error("Exception in: {}.{}()", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
            throw e;
        }
    }
}