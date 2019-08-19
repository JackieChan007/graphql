package com.spring.graphql.util.context;

import com.spring.graphql.util.aop.DynamicProxyTargetSourceUtil;
import com.spring.graphql.util.aop.graphql.GraphqlComponent;
import com.spring.graphql.util.aop.graphql.GraphqlMethodAnnotation;
import com.spring.graphql.util.context.exception.GraphqlAnnoNullException;
import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        log.info("test");
        // 根容器为Spring容器
        if (event.getApplicationContext().getParent() == null) {
//            Map<String,Map<String, DataFetcher>> dataFtecherMap= new HashMap<>();
//            Map<String,DataFetcher> queryDataFetcher= new HashMap<>();
//            Map<String,DataFetcher> mutationDataFetcher= new HashMap<>();
//            Map<String,DataFetcher> subscribeFetcher= new HashMap<>();
            Map<String, Map<String, Method>> dataFtecherMap = new HashMap<>();
            Map<String, Method> queryDataFetcher = new HashMap<>();
            Map<String, Method> mutationDataFetcher = new HashMap<>();
            Map<String, Method> subscribeFetcher = new HashMap<>();

            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(GraphqlComponent.class);
            //对schema分类
            for (Object bean : beans.values()) {
//                System.err.println(bean==null?"null":bean.getClass().getName());
                if (bean != null) {
                    //判断类是否是动态代理类
//                    if (AopUtils.isAopProxy(bean)) {
//                        log.info("bean:{} is proxy class", bean);
//                        if (AopUtils.isJdkDynamicProxy(bean)) {
//                            log.info("bean:{} is JdkDynamic class", bean);
//                        } else if (AopUtils.isCglibProxy(bean)) {
//                            log.info("bean:{} is CglibProxy class", bean);
//                        }
//                    }
                    Object targetClass = null;
                    try {
                        targetClass = DynamicProxyTargetSourceUtil.getTarget(bean);

                    } catch (Exception e) {
                        log.error("获取动态代理属性出错");
                        e.printStackTrace();
                    }
                    Annotation[] targetAnnos = null;
                    Method[] methods = targetClass.getClass().getMethods();
                    for (Method method : methods) {
                        GraphqlMethodAnnotation graphqlMethodAnnotation = method.getAnnotation(GraphqlMethodAnnotation.class);
                        if (graphqlMethodAnnotation != null) {
                            String typeName = graphqlMethodAnnotation.dataFetcherName();
                            if (StringUtils.isEmpty(typeName)) {
                                log.info("GraphqlMethodAnnotation注解的typeName为空,beanName为:{},方法名为:{}", targetClass);
                                break;
                            }
                            if (Objects.equals(GraphqlSchemEnum.MUTATION.getGraphqlSchema(), graphqlMethodAnnotation.typeName())) {
                                mutationDataFetcher.put(graphqlMethodAnnotation.dataFetcherName(), method);

                            } else if (Objects.equals(GraphqlSchemEnum.QUERY.getGraphqlSchema(), graphqlMethodAnnotation.typeName())) {
                                queryDataFetcher.put(graphqlMethodAnnotation.dataFetcherName(), method);

                            } else if (Objects.equals(GraphqlSchemEnum.SUBSCRIBE.getGraphqlSchema(), graphqlMethodAnnotation.typeName())) {
                                subscribeFetcher.put(graphqlMethodAnnotation.dataFetcherName(), method);
                            }

                        }
                    }
                }
                dataFtecherMap.put(GraphqlSchemEnum.MUTATION.getGraphqlSchema(), mutationDataFetcher);
                dataFtecherMap.put(GraphqlSchemEnum.QUERY.getGraphqlSchema(), queryDataFetcher);
                dataFtecherMap.put(GraphqlSchemEnum.SUBSCRIBE.getGraphqlSchema(), subscribeFetcher);
                log.info("获取到的mutation为：{}", dataFtecherMap);
                System.err.println("=====ContextRefreshedEvent=====" + event.getSource().getClass().getName());
            }
        }
    }
}