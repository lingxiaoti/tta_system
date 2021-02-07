package com.sie.saaf.common.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

/**
 * 自定义的数据库字库转换成POJO
 */
public class EscColumnToBean implements ResultTransformer {
	
	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;
	private Setter[] setters;
	private PropertyAccessor propertyAccessor;

	public EscColumnToBean(Class<?> resultClass) {
		if(resultClass == null){
			throw new IllegalArgumentException("resultClass cannot be null");
		}
		this.resultClass = resultClass;
		propertyAccessor = new ChainedPropertyAccessor(
				new PropertyAccessor[] { 
						PropertyAccessorFactory.getPropertyAccessor(resultClass, null), 
						PropertyAccessorFactory.getPropertyAccessor("field")
				}
		); 		
	}

	// 结果转换时，hibernate调用此方法
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		try {
			if(setters==null) {//首先初始化，取得目标POJO类的所有SETTER方法
				setters = new Setter[aliases.length];
				for (int i = 0; i < aliases.length; i++) {
					String alias = aliases[i];
					if(alias != null) {
						//我的逻辑主要是在getSetterByColumnName方法里面，其它都是HIBERNATE的另一个类中COPY的
						//这里填充所需要的SETTER方法
						setters[i] = getSetterByColumnName(alias);
					}
				}
			}
			result = resultClass.newInstance();
			
			//这里使用SETTER方法填充POJO对象
			for (int i = 0; i < aliases.length; i++) {
				if(null != setters[i]) {
					setters[i].set(result, tuple[i], null);
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		} 
		return result;
	}

	// 根据数据库字段名在POJO查找JAVA属性名
	private Setter getSetterByColumnName(String alias) {
		// 取得POJO所有属性名
		Field[] fields = resultClass.getDeclaredFields();
		if(fields == null || fields.length == 0){
			return null;
		}
		// 把字段名中所有的下杠去除
		String proName = alias.replaceAll("_", "").toLowerCase();
		for (Field field : fields) {
			field.setAccessible(true);
			// 去除下杠的字段名如果和属性名对得上，就取这个SETTER方法
			if(field.getName().toLowerCase().equals(proName)){
				return propertyAccessor.getSetter(resultClass, field.getName());
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List arg0) {
		return arg0;
	}
	
	public String convertColumnToProperty(String columnName) {  
        columnName = columnName.toLowerCase();  
        StringBuffer buff = new StringBuffer(columnName.length());  
        StringTokenizer st = new StringTokenizer(columnName, "_");  
        while (st.hasMoreTokens()) {  
            buff.append(StringUtils.capitalize(st.nextToken()));  
        }  
        buff.setCharAt(0, Character.toLowerCase(buff.charAt(0)));  
        return buff.toString();  
    }  
	
}


