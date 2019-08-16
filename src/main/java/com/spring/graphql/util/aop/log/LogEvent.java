package com.spring.graphql.util.aop.log;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LogEvent {
    ModuleType module() default ModuleType.DEFAULT; // 日志所属的模块
    EventType event() default EventType.DEFAULT; // 日志事件类型
    String desc() default  ""; // 描述信息
}
