package com.spring.graphql.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;

/**
 * @author ：JackieChan
 * @date ：2019/8/15 23:55
 * @version: 1.0
 * @description：GraphqlTag注解扫描
 */
@GraphqlTag
public class GraphqlTagScan {

    public Map scanGraphqlTag(){
        Annotation[] annotations = GraphqlTagScan.class.getAnnotations();
        System.out.println("Annotation[]: " + Arrays.toString(annotations));
        // Annotation[]: [@pub.guoxin.demo.gateway.dr.prvd.RedTag()]

        Component annotation = GraphqlTagScan.class.getAnnotation(Component.class);
        System.out.println("Tags: " + annotation);
        return null;
    }
}
