package com.spring.graphql.util.context;

import com.spring.graphql.util.aop.graphql.GraphqlComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @User: JackieChan
 * @Date: 2019/8/16
 * @Time: 17:27
 * @Reserved: ht
 * @Description: springboot启动之后获取特定注解的bean
 */
@Component
@Slf4j
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if(event.getApplicationContext().getParent()==null){
            Map<String,Object> beans = event.getApplicationContext().getBeansWithAnnotation(GraphqlComponent.class);
            for(Object bean:beans.values()){
                System.err.println(bean==null?"null":bean.getClass().getName());
                if(bean !=null) {
                    String beanName = bean.getClass().getName();
                    Method[] methods = bean.getClass().getMethods();
                    for(Method method:methods){
                    }
                    Annotation[] annotations = bean.getClass().getAnnotations();

                }
            }
            System.err.println("=====ContextRefreshedEvent====="+event.getSource().getClass().getName());
        }
    }
}