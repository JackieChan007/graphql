package com.spring.graphql.util.aop.graphql;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @User: JackieChan
 * @Date: 2019/8/14
 * @Time: 13:22
 * @Reserved: ht
 * @Description: services生成datafetcher
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface GraphqlComponent {
    String dataFetchers() default "";
    String typeName() default "";
    String beanDesc() default "";
}
