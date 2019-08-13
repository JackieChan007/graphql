package com.spring.graphql.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：JackieChan
 * @date ：2019/8/9 23:20
 * @version: 1.0
 * @description：hello world restful
 */
@RestController("/hello")
public class HelloWorldController {
    @GetMapping("/world")
    public String getHello() {
        return "hello world!";
    }
}
