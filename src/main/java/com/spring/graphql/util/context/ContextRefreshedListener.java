package com.spring.graphql.util.context;

import com.spring.graphql.util.aop.graphql.GraphqlComponent;
import com.spring.graphql.util.aop.graphql.GraphqlMethodAnnotation;
import com.spring.graphql.util.context.exception.GraphqlAnnoNullException;
import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
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
        // 根容器为Spring容器
        if(event.getApplicationContext().getParent()==null){
//            Map<String,Map<String, DataFetcher>> dataFtecherMap= new HashMap<>();
//            Map<String,DataFetcher> queryDataFetcher= new HashMap<>();
//            Map<String,DataFetcher> mutationDataFetcher= new HashMap<>();
//            Map<String,DataFetcher> subscribeFetcher= new HashMap<>();
            Map<String,Map<String, Method>> dataFtecherMap= new HashMap<>();
            Map<String,Method> queryDataFetcher= new HashMap<>();
            Map<String,Method> mutationDataFetcher= new HashMap<>();
            Map<String,Method> subscribeFetcher= new HashMap<>();

            Map<String,Object> beans = event.getApplicationContext().getBeansWithAnnotation(GraphqlComponent.class);
            //对schema分类
            for(Object bean:beans.values()){
//                System.err.println(bean==null?"null":bean.getClass().getName());
                if(bean !=null) {
                    String beanName = bean.getClass().getName();
                    Method[] methods = bean.getClass().getMethods();
                    for(Method method:methods){
                        Annotation[] annotations = bean.getClass().getAnnotations();
                        GraphqlComponent graphqlComponent=bean.getClass().getAnnotation(GraphqlComponent.class);
                        if(graphqlComponent==null){
                            break;
                        }
                        if(graphqlComponent!=null){
                            GraphqlMethodAnnotation graphqlMethodAnnotation =bean.getClass().getAnnotation(GraphqlMethodAnnotation.class);
                            if(graphqlMethodAnnotation!=null){
                                String typeName= graphqlMethodAnnotation.dataFetcherName();
                                if(StringUtils.isEmpty(typeName)) {
                                    log.info("GraphqlMethodAnnotation注解的typeName为空,beanName为:{},方法名为:{}",beanName);
                                    break;
                                }
                                if(Objects.equals(GraphqlSchemEnum.MUTATION.getGraphqlSchema(),graphqlMethodAnnotation.typeName())){
                                    mutationDataFetcher.put(graphqlMethodAnnotation.dataFetcherName(),method);

                                }else if(Objects.equals(GraphqlSchemEnum.QUERY.getGraphqlSchema(),graphqlMethodAnnotation.typeName())){
                                    queryDataFetcher.put(graphqlMethodAnnotation.dataFetcherName(),method);

                                }else if(Objects.equals(GraphqlSchemEnum.SUBSCRIBE.getGraphqlSchema(),graphqlMethodAnnotation.typeName())){
                                    subscribeFetcher.put(graphqlMethodAnnotation.dataFetcherName(),method);
                                }

                            }
                        }
                    }

                }
            }
            dataFtecherMap.put(GraphqlSchemEnum.MUTATION.getGraphqlSchema(),mutationDataFetcher);
            dataFtecherMap.put(GraphqlSchemEnum.QUERY.getGraphqlSchema(),queryDataFetcher);
            dataFtecherMap.put(GraphqlSchemEnum.SUBSCRIBE.getGraphqlSchema(),subscribeFetcher);
            System.err.println("=====ContextRefreshedEvent====="+event.getSource().getClass().getName());
        }
    }
}