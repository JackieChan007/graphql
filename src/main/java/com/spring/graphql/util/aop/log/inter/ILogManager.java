package com.spring.graphql.util.aop.log.inter;

import com.spring.graphql.util.aop.log.model.LogAdmModel;

public interface ILogManager {
    /**
     * 日志处理模块
     * @param paramLogAdmBean
     */
    void dealLog(LogAdmModel paramLogAdmBean);
    /**
     * 日志处理模块
     * @param paramLogAdmBean
     */
    void dealTagLog(String tag, LogAdmModel paramLogAdmBean);
}