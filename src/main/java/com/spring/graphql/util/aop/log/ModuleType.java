package com.spring.graphql.util.aop.log;

public enum  ModuleType {
    // 默认值
    DEFAULT("1"),
    // 学生模块
    STUDENT("2"),
    // 用户模块
    TEACHER("3");

    private ModuleType(String index){
        this.module = index;
    }
    private String module;
    public String getModule(){
        return this.module;
    }
}
