package com.spring.graphql.service.impl;

import com.spring.graphql.service.GraphQLDataFetcherInter;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;

/**
 * @author ：JackieChan
 * @date ：2019/8/10 16:18
 * @version: 1.0
 * @description：获取图书的信息
 */
@Service
public class BookAuthorDataFetcher implements GraphQLDataFetcherInter {

    @Override
    public String fieldName() {
        return "books";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        String id= environment.getArgument("id");

        return null;
    }
}
