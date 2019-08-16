package com.spring.graphql.util.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)             //指定注解的使用范围为 方法
@Retention(RetentionPolicy.RUNTIME)   //运行时生效
public @interface NameSpace {
    /**
     * Name Space
     *
     * @return value
     */
    String value();
}
