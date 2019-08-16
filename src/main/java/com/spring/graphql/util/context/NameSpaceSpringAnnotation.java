package com.spring.graphql.util.context;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @User: JackieChan
 * @Date: 2019/8/16
 * @Time: 16:43
 * @Reserved: ht
 * @Description: TODO
 */
public class NameSpaceSpringAnnotation implements BeanPostProcessor {




    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //反射获取这个Bean的对应类的Class元信息
        Class<?> targetClass = AopUtils.getTargetClass(bean);

        //为对应的注解加上对应的功能
        // 注意加final，lambada闭包 不能访问非final的局部变量
        final AtomicBoolean add = new AtomicBoolean(true);
        ReflectionUtils.doWithMethods(targetClass,
                method -> {

                    add.set(false);
                }, method -> add.get());
        return bean;
    }

    //前置bean不加上任何的功能，直接返回
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
