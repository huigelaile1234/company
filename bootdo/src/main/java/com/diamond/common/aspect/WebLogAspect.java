package com.diamond.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    //两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution( * com.diamond..controller.*.*(..))")
    public void logPointCut() {

    }


    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("请求地址 : {}", request.getRequestURL().toString());
        log.info("HTTP METHOD : {}，CLASS_METHOD：{}，参数 : {}", request.getMethod(), joinPoint.getSignature().
                getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        // 获取真实的ip地址
        //log.info("IP : " + IPAddressUtil.getClientIpAddress(request));

    }

    // returning的值和doAfterReturning的参数名一致
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
        log.debug("返回值 : {}", ret);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        log.info("耗时 : {}ms", (System.currentTimeMillis() - startTime));
        return ob;
    }
}
