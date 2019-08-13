package com.spring.graphql.service;

import graphql.schema.DataFetchingEnvironment;

/**
 * 数据获取
 */
public interface GraphQLDataFetcherInter {

    /**
     * Graphql 查询名称
     * @return
     */
    String fieldName();

    /**
     * 数据的查询
     * @param environment
     * @return
     */
    Object dataFetcher(DataFetchingEnvironment environment);
}
