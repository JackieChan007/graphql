package com.spring.graphql.util.aop;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: JackieChan
 * \* Date: 2019/8/19
 * \* Time: 11:00
 * \* Copyright (c) HT All Rights Reserved
 * \* Description:
 * \
 */
public class DynamicProxyTargetSourceUtil {

    /**
     * 获取 目标对象
     * @param proxy 代理对象 springboot中如何使接口实现的默认使用JdkDynamic，
     *              如果是类的动态代理类用的cglibDynamic
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy) throws Exception {

        //是否是动态代理类
        if(!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象
        }

        //是否是Jdk生成的动态代类（接口实现）
        if(AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib
            return getCglibProxyTargetObject(proxy);
        }



    }


    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }


    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

}

