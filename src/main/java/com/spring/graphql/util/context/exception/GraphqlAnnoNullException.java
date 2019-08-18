package com.spring.graphql.util.context.exception;

/**
 * @author ：JackieChan
 * @date ：2019/8/16 21:57
 * @version: 1.0
 * @description：graphql自定义框架为null异常
 */
public class GraphqlAnnoNullException extends Exception {
    private String code;
    private String msg;
    public GraphqlAnnoNullException(String code,String msg){
        this.code= code;
        this.msg=msg;
    }

}
