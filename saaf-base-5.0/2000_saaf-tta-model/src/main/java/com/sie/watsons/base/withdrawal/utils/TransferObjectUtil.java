package com.sie.watsons.base.withdrawal.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.sie.saaf.common.bean.FieldAttrIgnore;
import com.sie.watsons.base.withdrawal.model.entities.TtaSplitBrandDetailEntity_HI;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 下划线和驼峰格式互换 下划线的Map转驼峰类型实体类
 */
public class TransferObjectUtil {
    public static final char UNDERLINE_CHAR = '_';
    private static Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE);

    /**
     * 下划线转驼峰
     *
     * @param underlineStr
     * @return
     */
    public static String underline2Camel(String underlineStr) {
        if (StringUtils.isEmpty(underlineStr)) {

            return StringUtils.EMPTY;
        }

        int len = underlineStr.length();
        StringBuilder strb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {

            char c = underlineStr.charAt(i);
            if (c == UNDERLINE_CHAR && (++i) < len) {

                c = underlineStr.charAt(i);
                strb.append(Character.toUpperCase(c));
            } else {

                strb.append(c);
            }
        }
        return strb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param camelStr
     * @return
     */
    public static String camel2Underline(String camelStr) {
        if (StringUtils.isEmpty(camelStr)) {

            return StringUtils.EMPTY;
        }

        int len = camelStr.length();
        StringBuilder strb = new StringBuilder(len + len >> 1);
        for (int i = 0; i < len; i++) {

            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {

                strb.append(UNDERLINE_CHAR);
                strb.append(Character.toLowerCase(c));
            } else {

                strb.append(c);
            }
        }
        return strb.toString();
    }

    /**
     * 带下划线的key的Map转成驼峰形式的实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static <T>  T map2Object(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(camel2Underline(field.getName()))); // 获取带_下划线的名称的value值
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    //Object转换为Map(实体对象转换成带下划线的map)
    public static Map<String,Object> objToMap(Object obj) throws Exception{
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            FieldAttrIgnore fieldAttrIgnore = field.getAnnotation(FieldAttrIgnore.class);
            if (fieldAttrIgnore == null) {
                map.put(converter.convert(field.getName()) , field.get(obj));
            }
        }
        return map;
    }

    public static Map toUnderlineJSONString2Map(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String reqJson = mapper.writeValueAsString(object);
        Map map = JSON.parseObject(reqJson, Map.class);
        return map;
    }

    /**
     * 复制集合对象属性
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(input)) {
            for (E source : input) {
                T target = BeanUtils.instantiate(clzz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }

    /**
     * 复制集合对象属性
     * @param obj 来源集合
     * @param list2 目标集合
     * @param classObj 类型
     */
    public static void copyList(Object obj, List<T> list2, Class<T> classObj) {
        if ((!Objects.isNull(obj)) && (!Objects.isNull(list2))) {
            List list1 = (List) obj;
            list1.forEach(item -> {
                try {
                    T data = classObj.newInstance();
                    BeanUtils.copyProperties(item, data);
                    list2.add(data);
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                }


            });
        }
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("supplier_code","10204303");
        map.put("group_code","3");
        map.put("brand_cn","舒芙蕾");
        TtaSplitBrandDetailEntity_HI splitBrandDetailEntity_hi = map2Object(map, TtaSplitBrandDetailEntity_HI.class);
        String s = JSON.toJSONString(splitBrandDetailEntity_hi);
        System.out.println(s);
        System.out.println("----------------------------------");
        String str = "{\"SUPPLIERCODE\":\"13434343\",\"proposal_Code\":\"3434342432\",\"brand_Name\":\"很好\"}";
        TtaSplitBrandDetailEntity_HI teacher = JSON.parseObject(str, TtaSplitBrandDetailEntity_HI.class);
        System.out.println(JSON.toJSONString(teacher));

    }
}
