package com.sie.saaf.transaction.aspect;

import com.sie.saaf.common.model.entities.BaseCommonTransactionConfirmEntity_HI;
import com.sie.saaf.transaction.annotation.TransMsgParam;
import com.sie.saaf.transaction.event.PassiveTrasationEvent;
import com.yhg.hibernate.core.dao.ViewObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 被动方应用AOP拦截类
 * 被动方应用提交数据库事务前，绑定事务提交监听【用于自动幂等保证，redis消息清除】
 * 此AOP ORDER 顺序需低于 SPRING事务 AOP的 ORDER,[在SPRING 开启事务后，提交事务前执行此方法]
 * author: duzh
 * date: 2018-06-12
 */
@Order(1)
@Component
@Aspect
public class PassiveTrasationAspect {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PassiveTrasationAspect.class);

    @Autowired
    private ApplicationContext context;

    private ViewObject<BaseCommonTransactionConfirmEntity_HI> baseCommonTransactionConfirmDAO_HI;

    @Resource(name = "baseCommonTransactionConfirmDAO_HI")
    public void setBaseCommonTransactionConfirmDAO_HI(ViewObject<BaseCommonTransactionConfirmEntity_HI> baseCommonTransactionConfirmDAO_HI) {
        this.baseCommonTransactionConfirmDAO_HI = baseCommonTransactionConfirmDAO_HI;
    }

    /**
     * 切点表达式,拦截带有TransMessageConsumer注解的方法,
     */
    @Pointcut("@annotation(com.sie.saaf.transaction.annotation.TransMessageConsumer)")
    public void startTrasation() {

    }

    /**
     * 前置通知,完成幂等性保证[消息保存到消息消费表],注入事务事件监听[事务提交后通知监听删除redis消息]
     */
    @Before("startTrasation()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        /**
         *
         * 得到事务方法中messageIndex参数
         *
         */
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String messageIndex = null;

        int k = 0;
        Parameter[] parameters = signature.getMethod().getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.getAnnotation(TransMsgParam.class) != null) {
                k++;
                if (args[i]==null) {
                    throw new Exception("messageIndex 为null");
                }
                messageIndex = args[i].toString();
            }
        }

        if (k != 1) {
            throw new Exception("请检查方法参数，TransMsgParam注释的方法参数有且只有一个");
        }

        /**
         *  幂等性保证，消息保存到消息消费表
         *  与拦截方法处于同一事务，既被拦截方法的数据库事务失败，则保存消息消费表也会成败
         *  被拦截方法的数据库事务成功，则也会成功保存消息消费表
         */
        Assert.notNull(messageIndex, "无法校验消息幂等性");
        BaseCommonTransactionConfirmEntity_HI instance = new BaseCommonTransactionConfirmEntity_HI();
        instance.setOperatorUserId(1);
        instance.setMessageIndex(Integer.parseInt(messageIndex));
        baseCommonTransactionConfirmDAO_HI.save(instance);

        /**
         * 触发事务事件,当被动方事务完成后，删除redis中的消息
         */
        context.publishEvent(new PassiveTrasationEvent("TransMessageConsumer", messageIndex));

        logger.info("被动方发起事务事件{},messageIndex:{}", Thread.currentThread().getName(), messageIndex);

    }


}
