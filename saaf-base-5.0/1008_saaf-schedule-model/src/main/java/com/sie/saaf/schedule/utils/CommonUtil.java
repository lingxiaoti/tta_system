package com.sie.saaf.schedule.utils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(SaafToolUtils.class);


	/**
	 * 将map转换成javabean对象
	 * @param map
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public static <T>T convertMapToObj(Map<String, Object> map, Class<T> beanClass) { 
		if (beanClass == null) { 
			return null;
		}
		try {
			Object obj = beanClass.newInstance();
        	if (map == null || map.isEmpty()) {  
        		return (T) obj;
        	}  
        	DateConverter dateConverter = new DateConverter();
            ConvertUtils.register(dateConverter, Timestamp.class); 
            BeanUtils.populate(obj, map);
            return (T) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  
	
	/**
	 * 将javabean对象转换成map对象
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> convertObjToMap(Object obj){
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null){
			return reMap;
		}
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {    
	            field.setAccessible(true);  
	            reMap.put(field.getName(), field.get(obj));  
	        }    
		} catch (Exception e) {
			logger.error("convertObjToMap() Exception", e); 
		}
		return reMap;
	}
	
	public static String formatNumber(double value, int digits){
		if(digits < 0){
			digits = 2;
		}
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(digits);
		return numberFormat.format(value);
	}

	/**
	 * 将map转换成javabean对象--处理时间类型Timestamp的转换
	 * @param paramMap
	 * @return
	 */
	public static Object handleRequest(Map<String, Object> paramMap, Class<?> clazz) {
		Object obj = null;
		if(null == clazz){
			return null;
		}
		Field fields [] = clazz.getDeclaredFields();
		try {
			obj = clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();// 获取属性名
				if("serialVersionUID".equals(fieldName)){
					continue;
				}
				if(field.getType() == Timestamp.class){
					Object date = paramMap.get(fieldName);
					if(date != null && !date.toString().equals("")){
						String dateStr = date.toString() + " 00:00:00";
						Timestamp timestamp = Timestamp.valueOf(dateStr);
						field.set(obj, timestamp);
					}
				} else {
					Object value = paramMap.get(fieldName);
					if (value != null && !value.toString().equals("")) {
						field.set(obj, convertValType(value, field.getType()));
					}
				}
			}
		} catch(Exception e){
			logger.error("handleRequest-exception", e);
		}
		return obj;
	}
	
	/**
     * 将Object类型的值，转换成bean对象属性里对应的类型值
     *
     * @param value Object对象值
     * @param fieldTypeClass 属性的类型
     * @return 转换后的值
     */
    public static Object convertValType(Object value, Class<?> fieldTypeClass) {
        Object retVal = null;
        if(Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if(Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if(Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if(Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else {
            retVal = value;
        }
        return retVal;
    }
	
	
	/**
	 * 用于去除list中的重复数据，并且保持原来的顺序
	 * @param list 代去重的list集合
	 * @return
	 */
	public static List<Integer> removeDuplicate(List<Integer> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	}
	
	/** 
     * 对象属性转换为字段  例如：userName to user_name 
     * @param property 字段名 
     * @return 
     */  
    public static String propertyToField(String property) {  
        if (null == property) {  
            return "";  
        }  
        char[] chars = property.toCharArray();  
        StringBuffer sb = new StringBuffer();  
        for (char c : chars) {  
            if (CharUtils.isAsciiAlphaUpper(c)) {  
                sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));  
            } else {  
                sb.append(c);  
            }  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 字段转换成对象属性 例如：user_name to userName 
     * @param field 
     * @return 
     */  
    public static String fieldToProperty(String field) {  
        if (null == field) {  
            return "";  
        }  
        char[] chars = field.toCharArray();  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < chars.length; i++) {  
            char c = chars[i];  
            if (c == '_') {  
                int j = i + 1;  
                if (j < chars.length) {  
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));  
                    i++;  
                }  
            } else {  
                sb.append(c);  
            }  
        }  
        return sb.toString();  
    }  
    	  
     /** 
      * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
      * 
      * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
      * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
      * 
      * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
      * 192.168.1.100 
      * 
      * 用户真实IP为： 192.168.1.110 
      * 
      * @param request 
      * @return 
      */
     public static String getIpAddress(HttpServletRequest request) {
         String ip = request.getHeader("x-forwarded-for"); 
         if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
             // 多次反向代理后会有多个ip值，第一个ip才是真实ip
             if( ip.indexOf(",")!=-1 ){
                 ip = ip.split(",")[0];
             }
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("Proxy-Client-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("WL-Proxy-Client-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("HTTP_CLIENT_IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("X-Real-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getRemoteAddr();  
         } 
         return ip;  
     }
	
    /**
     * 格式化数字，不足length的时候前面补0
    * @Description: TODO 
    * @params @param number	格式化的数字
    * @params @param length	期望的长度
    * @params @param type	类型标识，可为null
    * @params @return 	格式化后的字符串
    * @return String
    * @author Administrator
    * @date 2018年7月16日 下午5:32:04
     */
 	public static String getAutoNumber(int number, int length, String type){
 		String str = String.format("%0"+ length +"d", number); 
 		return type == null ? str : type + str;
 	}
    
}
