package com.spring.graphql.util.aop.log;

import com.spring.graphql.util.aop.log.inter.ILogManager;
import com.spring.graphql.util.aop.log.model.LogAdmModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @User: JackieChan
 * @Date: 2019/8/16
 * @Time: 10:57
 * @Reserved: ht
 * @Description: TODO
 */
@Component
@Aspect
public class LogAspect {
    @Autowired
    private LogInfoGeneration logInfoGeneration;

    @Autowired(required = true)
    @Qualifier("bizLogManager")
    private ILogManager logManager;

    @Pointcut("execution(* com.spring.graphql..*.*(..))")
    public void managerLogPoint() {
    }

    @Around("managerLogPoint()")
    public Object aroundManagerLogPoint(ProceedingJoinPoint jp) throws Throwable {

        Class target = jp.getTarget().getClass();
        // 获取LogEnable
        LogEnable logEnable = (LogEnable) target.getAnnotation(LogEnable.class);
        if(logEnable == null || !logEnable.logEnable()){
            return jp.proceed();
        }

        // 获取类上的LogEvent做为默认值
        LogEvent logEventClass = (LogEvent) target.getAnnotation(LogEvent.class);
        Method method = getInvokedMethod(jp);
        if(method == null){
            return jp.proceed();
        }

        // 获取方法上的LogEvent
        LogEvent logEventMethod = method.getAnnotation(LogEvent.class);
        if(logEventMethod == null){
            return jp.proceed();
        }

        String optEvent = logEventMethod.event().getEvent();
        String optModel = logEventMethod.module().getModule();
        String desc = logEventMethod.desc();

        if(logEventClass != null){
            // 如果方法上的值为默认值，则使用全局的值进行替换
            optEvent = optEvent.equals(EventType.DEFAULT) ? logEventClass.event().getEvent() : optEvent;
            optModel = optModel.equals(ModuleType.DEFAULT) ? logEventClass.module().getModule() : optModel;
        }

        LogAdmModel logBean = new LogAdmModel();
        logBean.setAdmModel(optModel);
        logBean.setAdmEvent(optEvent);
        logBean.setDesc(desc);
        logBean.setCreateDate(new Date());
        logInfoGeneration.processingManagerLogMessage(jp,
                logBean, method);
        Object returnObj = jp.proceed();

        if(optEvent.equals(EventType.LOGIN)){
            //TODO 如果是登录，还需要根据返回值进行判断是不是成功了，如果成功了，则执行添加日志。这里判断比较简单
            if(returnObj != null) {
                this.logManager.dealLog(logBean);
            }
        }else {
            this.logManager.dealLog(logBean);
        }
        return returnObj;
    }
    /**
     * 获取请求方法
     *
     * @param jp
     * @return
     */
    public Method getInvokedMethod(JoinPoint jp) {
        // 调用方法的参数
        List classList = new ArrayList();
        for (Object obj : jp.getArgs()) {
            classList.add(obj.getClass());
        }
        Class[] argsCls = (Class[]) classList.toArray(new Class[0]);

        // 被调用方法名称
        String methodName = jp.getSignature().getName();
        Method method = null;
        try {
            method = jp.getTarget().getClass().getMethod(methodName, argsCls);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

}
