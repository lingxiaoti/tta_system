package com.sie.wastons.sql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * huangtao
 * 2019年9月18日17:17:33
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlBinder {

    /**
     * EQ           等于
     * CONTAIN      包含
     * START_WITH   like '%xxx'
     * END_WITH     like 'xxx%'
     * NEQ          不等于
     * LT           小于
     * LTE          小于等于
     * GT           大于
     * GTE          大于等于
     * IN           in
     * IS_NULL      为 null
     * NOT_NULL     不为null
     */
    enum OPR {
        EQ, CONTAIN, START_WITH, END_WITH, NEQ, LT, LTE, GT, GTE, IN, IS_NULL, NOT_NULL
    }

    /**
     * 排序
     */
    enum SORT{ASC,DESC,NONE}

    String sqlColumn() default "";

    OPR opreation() default OPR.EQ;

    SORT orderBy() default SORT.NONE;

    /**
     * 数据类型为Date 时，日期向后加一天，时分秒清零
     * eg: 2019年9月18日17:13:41 -> 2019年9月19日00:00:00
     * @return
     */
    boolean dayRoundUp() default false;

    /**
     * 数据类型为Date 时，时分秒清零
     * eg: 2019年9月18日17:17:09 -> 2019年9月18日00:00:00
     * @return
     */
    boolean timeClear() default false;


}
