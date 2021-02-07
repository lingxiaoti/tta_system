package com.sie.saaf.transaction.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，用于分步式事务，用于业务主动方标识
 * 代表一个方法是业务主动方应用
 * 主动方保证事务提交才调用服务【从redis中发起请求】
 * author: duzh
 * date: 2018-06-12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransMessageProvider {

    /**
     * 主动方服务描述
     *
     * @return
     */
    String desc();

}
