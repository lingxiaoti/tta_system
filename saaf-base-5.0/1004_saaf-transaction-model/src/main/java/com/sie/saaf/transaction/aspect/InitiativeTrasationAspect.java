package com.sie.saaf.transaction.aspect;

import com.sie.saaf.transaction.event.InitiativeTrasationEvent;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.redis.framework.JedisClusterCore;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * 主动方应用AOP拦截类
 * 主动方应用提交数据库事务前，绑定事务提交监听【用于自动发送消息，调用服务及MQ等】
 * 此AOP ORDER 顺序需低于 SPRING事务 AOP的 ORDER,[在SPRING 开启事务后，提交事务前执行此方法]
 * author: duzh
 * date: 2018-06-12
 */
@Order(1)
@Component
@Aspect
public class InitiativeTrasationAspect extends JedisClusterCore {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InitiativeTrasationAspect.class);

    @Autowired
    private ApplicationContext context;


   /**
     * 切点表达式,拦截带有TransMessageProvider注解的方法
     */
    @Pointcut("@annotation(com.sie.saaf.transaction.annotation.TransMessageProvider)")
    public void startTrasation(){}

    /**
     * 前置通知,注入事务事件监听[事务提交后通知监听发送redis消息]
     */
    @Before("startTrasation()")
    public void doBefore(JoinPoint joinPoint){
        /**
         * 清除线程变量中的messageID
         */
        RemoteServiceInvoke.clearMessageIds();
        /**
         *  触发事务事件，主动方事务成功提交后将发送保存在redis中的消息，当主动方应用事务回滚时清理保存在redis中的消息
         */
        context.publishEvent(new InitiativeTrasationEvent("TransMessageProvider", this));

        logger.info("主动方发起事务事件：{}",Thread.currentThread().getName());
    }

}
