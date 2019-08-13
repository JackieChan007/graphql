package com.spring.graphql.service.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.spring.graphql.service.GraphQLDataFetcherInter;
import com.spring.graphql.service.graphql.datafetcher.GraphQLDataFetchers;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author ：JackieChan
 * @date ：2019/8/9 22:03
 * @version: 1.0
 * @description：graphql
 */
@Component
public class GraphQLProvider {

    @Autowired
    private GraphQLDataFetchers graphQLDataFetchers;

    private GraphQL graphQL;
    @Autowired
    private List<GraphQLDataFetcherInter> graphQLDataFetcherInters;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    //初始化graphql schema
    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("graphql/schema.graphqls");
        String sd1 = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sd1);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        //TypeDefinitionRegistry：解析schema文件的解析脚本
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);


    }

    //注释: 这个需要结合业务逻辑建立通用的注入
    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }

    //注释: 这个需要结合业务逻辑建立通用的注入
    private RuntimeWiring buildWiringLamdba() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> {

                            for (GraphQLDataFetcherInter graphQLDataFetcherInter : graphQLDataFetcherInters
                            ) {
                                builder.dataFetcher(graphQLDataFetcherInter.fieldName(), environment -> graphQLDataFetcherInter.dataFetcher(environment));
                            }
                            return builder;
                        }
//                        builder.dataFetcher("bookById", environment -> {
//                            Long id = environment.getArgument("id");
//                            return new Book();
//                        })
                ).build();
    }


}
