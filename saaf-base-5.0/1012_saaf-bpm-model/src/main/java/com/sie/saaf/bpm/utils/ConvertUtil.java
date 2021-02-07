package com.sie.saaf.bpm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConvertUtil {
    
    public static String dateToString(java.util.Date date) {
        if(null != date) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        }else {
            return null;
        }
    }
    
    public static String sqlDateToString(Date date) {
        if(null != date) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        }else {
            return null;
        }
    }

    public static java.util.Date stringToDateYMD(String date){
        if(null != date && !date.isEmpty()) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static List<String> getJSONArrayField(JSONArray array, String jsonField){
        return getJSONArrayField(array, jsonField, false);
    }

    /**
     * 从
     * @param array
     * @param jsonField
     * @param isArray
     * @return
     */
    public static List<String> getJSONArrayField(JSONArray array, String jsonField, boolean isArray){
        List<String> fieldValues = new ArrayList<String>();
        for(int i=0; i<array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            if(!object.containsKey(jsonField) || StringUtils.isBlank(object.getString(jsonField))) {
                continue;
            }
            String valueStr = object.getString(jsonField);
            if(isArray) {
                String[] values = valueStr.split(",");
                for(String value :values) {
                    if(StringUtils.isNotBlank(value) && !fieldValues.contains(value)) {
                        fieldValues.add(value);
                    }
                }
            }else {
                if(!fieldValues.contains(valueStr)) {
                    fieldValues.add(valueStr);
                }
            }
        }
        return fieldValues;
    }

    public static void appendToJSONObject(JSONObject jsonObject, Object entity) {
        appendToJSONObject(jsonObject, entity, null, null, false);
    }

    public static void appendToJSONObject(JSONObject jsonObject, Object entity, Map<String,String> fields,
            String alias, boolean override) {
        if(null == jsonObject || null == entity) {
            return;
        }
        JSONArray array = new JSONArray();
        array.add(jsonObject);
        String keyField = "_for_temp_json_convert_" + new java.util.Date().getTime();
        jsonObject.put(keyField, "1");
        Map<String,Object> entities = new HashMap<String,Object>();
        entities.put("1", entity);
        appendToJSONArray(array, entities, fields, keyField, null, alias, override);
        jsonObject.remove(keyField);
    }

    public static void appendToJSONArray(JSONArray array, @SuppressWarnings("rawtypes") Map entities, String jsonKeyField) {
        appendToJSONArray(array, entities, null, jsonKeyField, null, null, false);
    }

    public static void appendToJSONArray(JSONArray array, @SuppressWarnings("rawtypes") Map entities,Map<String,String> fields,
            String jsonKeyField, String alias, boolean override) {
        appendToJSONArray(array, entities,fields, jsonKeyField, null, alias, override);
    }


    public static void appendToJSONArray(JSONArray array, @SuppressWarnings("rawtypes") Map entities,Map<String,String> fields,
            String jsonKeyField, String arrayField, String alias, boolean override) {
        for(int i=0; i<array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            if(!jsonObject.containsKey(jsonKeyField) || StringUtils.isBlank(jsonObject.getString(jsonKeyField))) {
                continue;
            }
            String valueStr = jsonObject.getString(jsonKeyField);
            if(null != arrayField) {
                JSONArray jsons = new JSONArray();
                String[] values = valueStr.split(",");
                for(String value :values) {
                   if(entities.containsKey(value)) {
                       JSONObject json = objectToJSON(entities.get(value), fields);
                       jsons.add(json);
                   }
                }
                if(!jsonObject.containsKey(arrayField) || override) {
                    jsonObject.put(arrayField, jsons);
                }
            }else {
                JSONObject json = objectToJSON(entities.get(valueStr), fields);
                if(null != json) {
                    for(String jsonField: json.keySet()) {
                        Object value = json.get(jsonField);
                        //增加别名前缀
                        if(StringUtils.isNotBlank(alias)) {
                            jsonField = alias + "_" + jsonField;
                        }
                        if(!jsonObject.containsKey(jsonField) || override) {
                            jsonObject.put(jsonField, value);
                        }
                    }
                }
            }

        }
    }

    public static JSONObject objectToJSON(Object object, Map<String,String> fields) {
        if(null == object) {
            return new JSONObject();
        }else if(null == fields || fields.isEmpty()){
            return (JSONObject) JSON.toJSON(object);
        }
        JSONObject jsonObject = new JSONObject();
        for(String entityField: fields.keySet()) {
            Object value = getPropValue(object, entityField);
            //时间格式化
            if(null != value &&  !value.toString().isEmpty()
                    && (value.getClass().equals(Date.class)
                    || value.getClass().equals(java.util.Date.class)
                    || value.getClass().equals(java.sql.Timestamp.class))) {
                java.util.Date date = null;
                if(value.getClass().equals(java.sql.Timestamp.class)){
                    date = new Date(((java.sql.Timestamp) value).getTime());
                }else {
                    date = (java.util.Date) value;
                }
                value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            }
            jsonObject.put(fields.get(entityField), value);
        }
        return jsonObject;
        
    }
    
    @SuppressWarnings("rawtypes")
    public static Map listToMap(List<?> list, String keyField){
        HashMap<String, Object> map = new HashMap<String,Object>();
        if(null != list && !list.isEmpty()) {
            for(Object object: list) {
                Object key = getPropValue(object, keyField);
                if(null != key) {
                    map.put(key.toString(), object);
                }
            }
        }
        return map;
    }
    
    
    /**
     * 取对象中的某个属性值 ，所定义的对象中需有相应的get方法
     * @param o 传入对象
     * @param prop 属性名称
     * @return
     */
    public static Object getPropValue(Object o,String prop){
        try {
            @SuppressWarnings("rawtypes")
            Class c = o.getClass();
            Method[] methods = c.getMethods();
            if(null == methods) {
                return null;
            }
            List<String> methodNames = new ArrayList<String>();
            for(Method method: methods) {
                methodNames.add(method.getName());
            }
            String[] props = prop.split("\\.");
            for(int i=0; i<props.length; i++){
                String methodName = "get" + props[i].substring(0, 1).toUpperCase() + props[i].substring(1);
                if(!methodNames.contains(methodName)) {
                    methodName = methodName.replace("get", "is");
                    if(!methodNames.contains(methodName)) {
                        continue;
                    }
                }
                @SuppressWarnings("unchecked")
                Method m = c.getMethod(methodName);
                o = m.invoke(o);
                if(null == o){
                    break;
                }
                c = o.getClass();
            }
        }catch (Exception e) {
        }
        return o; 
    }
    
    
    public static String getDurationDH(long milliseconds) {
    	long hours = milliseconds/3600000;
    	long days = hours/24;
    	hours = hours%24;
    	String timeStr = (days>0?days + "天":"") + (hours>0?hours + "小时":"");
    	if(StringUtils.isBlank(timeStr)) {
			return "0小时";
		}else {
			return timeStr;
		}
    }
    
    public static String getDurationDHM(long milliseconds) {
    	long minutes = milliseconds/60000;
    	long days = minutes/1440;
    	long hours = minutes%1440/60;
    	minutes = minutes%60;
    	String timeStr =  (days>0?days + "天":"") + (hours>0?hours + "小时":"") + (minutes>0?minutes + "分":"");
    	if(StringUtils.isBlank(timeStr)) {
			return "0分";
		}else {
			return timeStr;
		}
    }
    
    public static String getDurationDHMS(long milliseconds) {
    	long seconds = milliseconds/1000;
    	long minutes = seconds/60;
    	long days = minutes/1440;
    	long hours = minutes%1440/60;
    	minutes = minutes%60;
    	seconds = seconds%60;
		String timeStr = (days>0?days + "天":"") + (hours>0?hours + "小时":"") + (minutes>0?minutes + "分":"") + (seconds>0?seconds + "秒":"");
		if(StringUtils.isBlank(timeStr)) {
			return "0秒";
		}else {
			return timeStr;
		}
    }

}
