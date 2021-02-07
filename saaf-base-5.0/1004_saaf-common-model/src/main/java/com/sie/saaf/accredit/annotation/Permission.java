package com.sie.saaf.accredit.annotation;


import java.lang.annotation.*;


/**
 * 自定义权限注解
 * 用于control 层方法做职责、菜单、资源的权限校验
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface Permission {

    /**
     * 菜单code
     * @return
     */
     String menuCode() default "";

    /**
     * app 菜单编码
     * @return
     */
     String appMenuCode() default "";

    /**
     * 资源code
     * @return
     */
    String resourceCode() default "";
}
