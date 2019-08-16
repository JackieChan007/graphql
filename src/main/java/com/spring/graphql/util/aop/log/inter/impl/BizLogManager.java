package com.spring.graphql.util.aop.log.inter.impl;

import com.alibaba.fastjson.JSON;
import com.spring.graphql.util.aop.log.inter.ILogManager;
import com.spring.graphql.util.aop.log.model.LogAdmModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @User: JackieChan
 * @Date: 2019/8/16
 * @Time: 10:56
 * @Reserved: ht
 * @Description: 业务日志
 */
@Service("bizLogManager")
@Slf4j
public class BizLogManager implements ILogManager {
    @Override
    public void dealLog(LogAdmModel paramLogAdmBean) {
        System.out.println("将日志存入数据库,日志内容如下: " + JSON.toJSONString(paramLogAdmBean));
        if (log.isInfoEnabled()) {
            log.info(JSON.toJSONString(paramLogAdmBean));
        }
    }

    @Override
    public void dealTagLog(String tag, LogAdmModel paramLogAdmBean) {
        if (log.isInfoEnabled()) {
            log.info(tag + JSON.toJSONString(paramLogAdmBean));
        }
    }
}
