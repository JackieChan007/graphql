package com.spring.graphql.util.aop.graphql;

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
public @interface GraphqlMethodAnnotation {
    String dataFetcherName() default "";
    String typeName() default "";
}
