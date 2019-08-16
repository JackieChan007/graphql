package com.spring.graphql.util.aop.log;

import java.lang.annotation.*;

/**
 * JackieChan
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LogEnable {
    /**
     * 如果为true，则类下面的LogEvent启作用，否则忽略
     * @return
     */
    boolean logEnable() default true;
}

