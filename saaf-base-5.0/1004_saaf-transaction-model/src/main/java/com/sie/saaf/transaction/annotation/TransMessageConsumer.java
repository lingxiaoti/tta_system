package com.sie.saaf.transaction.annotation;

import java.lang.annotation.*;

/**
 *
 * 自定义注解，用于分步式事务，用于应用被动方标识
 * 代表一个方法是业务被动方【服务提供者】
 * 被动方将自动完成幂等性验证，幂等性保证,REDIS消息自动清除
 * author: duzh
 * date: 2018-06-12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransMessageConsumer {
    /**
     *  被动方服务描述,约定为主动方服务：被动方服务
     * @return
     */
    String desc();
}
