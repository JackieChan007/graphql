package com.spring.graphql.util.aop.log;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogKey {
    String keyName() default ""; // key的名称
    boolean isUserId() default false; // 此字段是否是本次操作的userId，这里略
    boolean isLog() default true; // 是否加入到日志中
}
