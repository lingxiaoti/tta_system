package com.sie.saaf.transaction.annotation;

import java.lang.annotation.*;


/**
 *
 * 自定义注解，用于分步式事务，用于标注messageId 参数
 * author: duzh
 * date: 2018-06-12
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface TransMsgParam {

}
