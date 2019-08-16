package com.spring.graphql.util.aop.log;

/**
 * JackieChan
 */
public enum EventType {
    //default
    DEFAULT("1", "default"),
    ADD("2", "add"),
    UPDATE("3", "update"),
    DELETE_SINGLE("4", "delete-single"),
    LOGIN("10","login"),LOGIN_OUT("11","login_out");

    private EventType(String index, String name){
        this.name = name;
        this.event = index;
    }
    private String event;
    private String name;
    public String getEvent(){
        return this.event;
    }

    public String getName() {
        return name;
    }
}
