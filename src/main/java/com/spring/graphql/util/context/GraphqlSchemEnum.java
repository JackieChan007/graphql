package com.spring.graphql.util.context;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ：JackieChan
 * @date ：2019/8/16 21:40
 * @version: 1.0
 * @description：grahqlSchema 枚举类型
 */
public enum GraphqlSchemEnum {
    QUERY("Query"),
    MUTATION("Mutation"),
    SUBSCRIBE("Sunscribe")
    ;
    private String graphqlSchema;

    GraphqlSchemEnum(String graphqlSchema) {
        this.graphqlSchema=graphqlSchema;
    }

    public String getGraphqlSchema(GraphqlSchemEnum graphqlSchema) {
        return graphqlSchema.name();
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param  graphqlSchema 类型名称
     */
    public static String fromGraphqlSchema(String graphqlSchema) {
        for (GraphqlSchemEnum type : GraphqlSchemEnum.values()) {
            if (type.getGraphqlSchema().equals(graphqlSchema)) {
                return type.getGraphqlSchema();
            }
        }
        return null;
    }

    public String getGraphqlSchema() {
        return this.graphqlSchema;
    }


    public void setGraphqlSchema(String graphqlSchema) {
        this.graphqlSchema = graphqlSchema;
    }
}
