package com.sie.saaf.schedule.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldDesc {

	/**
	 * 是否持久化(即保存到数据)，默认为true
	 * @return
	 */
	boolean persist() default true;
	
	/**
	 * 是否更新0值，默认为false
	 * @return
	 */
	boolean updateZero() default false;
	
	/**
	 * 是否保存0值，默认为true
	 * @return
	 */
	boolean insertZero() default true;
	
	/**
	 * 是否插入主键值，默认为false
	 * @return
	 */
	boolean insertPrimary() default false;
	
	/**
	 * 是否更新空("")值，默认为false
	 * @return
	 */
	boolean updateEmpty() default false;
	
	/**
	 * 是否更新null，默认为false
	 * @return
	 */
	boolean updateNull() default false;
	
	/**
	 * 是否保存null或空值，默认为false
	 * @return
	 */
	boolean insertNull() default false;
	
	/**
	 * 是否保存空值，默认为false
	 * @return
	 */
	boolean insertEmpty() default false;
}
