package com.sie.saaf.common.util;

import com.sie.saaf.common.bean.FieldAttrIgnore;

import javax.persistence.Id;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象的操作类
 * @author ZhangJun
 * @createTime 2018-03-14 00:19
 * @description
 */
public class SaafBeanUtils {


	/**
	 * 实体对象转成Map
	 *
	 * @param obj 实体对象
	 * @return
	 */
	public static Map<String, Object> object2Map(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				FieldAttrIgnore annotation = field.getAnnotation(FieldAttrIgnore.class);
				if (annotation == null) {
					map.put(field.getName(), field.get(obj));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Map转成实体对象
	 *
	 * @param map   实体对象包含属性
	 * @param clazz 实体对象类型
	 * @return
	 */
	public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
		if (map == null) {
			return null;
		}
		Object obj = null;
		try {
			obj = clazz.newInstance();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);
				field.set(obj, map.get(field.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 两个对象的属性值合并
	 * @param source 源对象
	 * @param target 目标对象
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 * @description 将源对象的属性合并到目标对象中，源对象属性有值时才会将源对象的属性值复制到新对象中
	 * 				如果源对象属性值为null，则保留新对象该属性值不变
	 */
	public static void copyUnionProperties(Object source,Object target) throws Exception{
		if(source == null) {
			return;
		}
		if(target == null){
			throw new IllegalArgumentException("参数target不能为空");
		}
		Field[] fields = target.getClass().getDeclaredFields();
		if(fields != null && fields.length != 0){
			for(Field field : fields){
				String fieldName = field.getName();
				PropertyDescriptor pd = new PropertyDescriptor(fieldName,target.getClass());
				Method writeMethod = pd.getWriteMethod();
				if(writeMethod != null){
					if(pd.getReadMethod().getAnnotation(Id.class)!=null) {
						//主键的值不进行复制
						continue;
					}

					PropertyDescriptor sourcdPd = null ;
					try{
						 sourcdPd = new PropertyDescriptor(fieldName,source.getClass());
					}catch (Exception e){
						continue ;
					}
					Method readMethod = sourcdPd.getReadMethod();
					if(readMethod != null){
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						if(value!=null) {
							writeMethod.invoke(target, value);
						}
					}
				}

			}
		}
	}

}

