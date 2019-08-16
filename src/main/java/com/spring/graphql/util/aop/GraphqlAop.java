package com.spring.graphql.util.aop;

import com.spring.graphql.util.aop.graphql.GraphqlComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @User: JackieChan
 * @Date: 2019/8/15
 * @Time: 15:09
 * @Reserved: ht
 * @Description: TODO
 */
@Aspect
@Component
@Slf4j
public class GraphqlAop {

    // 切点
    @Pointcut("@annotation(com.spring.graphql.util.aop.graphql.GraphqlComponent)")
    public void executePointCut() {
        log.info("------GraphqlAnnoStart------------");
    }


    //around 切面强化
    @Around("executePointCut()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = attributes.getRequest();
        String methodType =request.getMethod();
        String reomteIp =request.getRemoteAddr();
        String userAgent =request.getHeader("user-agent");
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodName= method.getName();
        GraphqlComponent invokeLog = method.getAnnotation(GraphqlComponent.class);
        String typeName= invokeLog.typeName();
        if(StringUtils.isEmpty(typeName)){
            //添加默认的schema
        }
        String dataFetcher= invokeLog.dataFetchers();
       return  null;
    }
}
