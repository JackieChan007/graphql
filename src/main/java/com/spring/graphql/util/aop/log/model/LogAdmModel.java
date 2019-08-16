package com.spring.graphql.util.aop.log.model;

import lombok.Data;

import java.util.Date;

/**
 * @User: JackieChan
 * @Date: 2019/8/16
 * @Time: 10:45
 * @Reserved: ht
 * @Description: TODO
 */
@Data
public class LogAdmModel {
    private Long id;
    private String userId; // 操作用户
    private String userName;
    private String admModel; // 模块
    private String admEvent; // 操作
    private Date createDate; // 操作内容
    private String admOptContent; // 操作内容
    private String desc; // 备注
}
