package com.sie.saaf.rule.utils;


import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Util {
    private static final Logger log= LoggerFactory.getLogger(Util.class);

    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 实体类数据set
     *
     * @param <T>    泛型
     * @param t      实体类
     * @param vo     ViewObjectImpl
     * @param id     表主键ID Integer格式数据
     * @param userId 当前用户ID
     * @return 返回实体行
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static <T> T setEntity(T t, JSONObject parameters, ViewObject vo, Object id, int userId) throws InstantiationException, IllegalAccessException, NoSuchMethodException,
                                                                                                           InvocationTargetException {
        t = getEditRow1(t, parameters, vo, id, userId);
        Method[] methodArr = t.getClass().getMethods();
        for (Method m : methodArr) {
            String methodName = m.getName();
            if (methodName.startsWith("set")) {
                String attribute = methodName.substring(3).toString();
                Object obj = toLowerCaseFirst(parameters, attribute.toString());
                if (obj != null && attribute.indexOf("setCreatedBy|setCreationDate|setLastUpdatedBy|setLastUpdateDate|setLastUpdateLogin") == -1) {
                    try {
                        //获取参数定义
                        Class<?>[] clazz = m.getParameterTypes();
                        String type = clazz[0].getName();
                        if (type.equals("java.lang.String")) {
                            m.invoke(t, obj.toString());
                        } else if (type.equals("java.util.Date") || type.equals("java.sql.Date")) {

                            if (obj.toString().length() > 10) {
                                try {
                                    m.invoke(t, formatDateTime.parse(obj.toString()));
                                } catch (Exception e) {
                                    log.error("",e);
                                }
                            } else {
                                try {
                                    m.invoke(t, formatDate.parse(obj.toString()));
                                } catch (Exception e) {
                                    log.error("",e);
                                }
                            }
                        } else if (type.equals("java.sql.Timestamp")) {
                            m.invoke(t, obj.toString());
                        } else if (type.equals("java.lang.Short") || type.equals("short")) {
                            m.invoke(t, Short.parseShort(obj.toString()));
                        } else if (type.equals("java.lang.Long") || type.equals("long")) {
                            m.invoke(t, Long.parseLong(obj.toString()));
                        } else if (type.equals("java.lang.Integer") || type.equals("int")) {
                            m.invoke(t, Integer.parseInt(obj.toString()));
                        } else if (type.equals("java.lang.Float") || type.equals("float")) {
                            m.invoke(t, obj.toString());
                        } else if (type.equals("java.lang.Double") || type.equals("double")) {
                            m.invoke(t, Double.parseDouble(obj.toString()));
                        } else if (type.equals("java.math.BigDecimal") || type.equals("BigDecimal")) {
                            BigDecimal bigDecimal = new BigDecimal(obj.toString());
                            m.invoke(t, bigDecimal);
                        } else if (type.equals("java.lang.Boolean") || type.equals("boolean")) {
                            m.invoke(t, obj.toString());
                        } else {
                            // more other types
                        }
                    } catch (IllegalAccessException e) {
                        log.error("",e);
                    } catch (IllegalArgumentException e) {
                        log.error("",e);
                    } catch (InvocationTargetException e) {
                       log.error("",e);
                    }
                }
            }
        }
        return t;
    }

    /**
     * 获取新增或修改行
     *
     * @param <T>    泛型
     * @param t      实体类
     * @param vo     ViewObjectImpl
     * @param id     表主键ID Integer格式数据
     * @param userId 当前用户ID
     * @return 返回实体行
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static <T> T getEditRow(T t, ViewObjectImpl vo, Object id, int userId) throws InstantiationException, IllegalAccessException, NoSuchMethodException,
                                                                                         InvocationTargetException {
        if (id == null || id.equals("")) {
            t.getClass().getDeclaredMethod("setCreatedBy", Integer.class).invoke(t, userId);
            t.getClass().getDeclaredMethod("setCreationDate", Date.class).invoke(t, new Date());
            t.getClass().getDeclaredMethod("setLastUpdatedBy", Integer.class).invoke(t, userId);
            t.getClass().getDeclaredMethod("setLastUpdateDate", Date.class).invoke(t, new Date());
            t.getClass().getDeclaredMethod("setLastUpdateLogin", Integer.class).invoke(t, userId);
        } else {
            t = (T)vo.getById(Integer.parseInt(id.toString()));
            t.getClass().getDeclaredMethod("setLastUpdatedBy", Integer.class).invoke(t, userId);
            t.getClass().getDeclaredMethod("setLastUpdateDate", Date.class).invoke(t, new Date());
            t.getClass().getDeclaredMethod("setLastUpdateLogin", Integer.class).invoke(t, userId);
        }
        return t;
    }

    /**
     * json取值字符大小写问题
     *
     * @param str
     * @return
     */
    private static Object toLowerCaseFirst(JSONObject paramters, String str) {
        Object obj = paramters.get(str);
        if (obj == null) {
            return paramters.get(str.substring(0, 1).toLowerCase() + str.substring(1));
        } else {
            return obj;
        }
    }

    /**
     * 判断某数据是否存在
     *
     * @param <T>        泛型
     * @param t          实体类
     * @param vo
     * @param field      字段对象(数据库唯一<UNIQUE>)
     * @param parameters 字段对象值
     * @return
     */
    public <T> boolean isExistenceData(T t, ViewObject vo, String field, JSONObject parameters) {
        Object value = toLowerCaseFirst(parameters, field);
        if (value != null || "".equals(value)) {
            Map map = new HashMap();
            map.put(field, value);
            List<T> list = vo.findByProperty(map);
            if (list.size() == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }



    /**
     * 自动查询功能
     *
     * @param dao           ：Dao层
     * @param sql           查询sql
     * @param params        查询参数
     * @param pagIndex      页码
     * @param pageRows      每页查询数量
     * @param sort          排序语句
     * @param map1           预设的map
     * @return
     */
    public static Pagination autoSearchPagination(BaseViewObject dao, String sql, JSONObject params, int pagIndex, int pageRows, String sort, Map map1) {
        String str2 = sql.substring(sql.toLowerCase().indexOf("select"), sql.toLowerCase().lastIndexOf("from")).trim() + ",";
        String regEx = "\\s*([A-Za-z0-9_]{2,40})\\s*,";
        String regEx2 = "([A-Za-z0-9_]{2,50}\\.[A-Za-z0-9_]{2,50})";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str2);
        List<String> attributes = new ArrayList<String>();
        while (m.find()) {
            attributes.add(m.group().replace(",", "").trim());
        }

        String[] attributeName = (String[])attributes.toArray(new String[attributes.size()]);

        Pattern p2 = Pattern.compile(regEx2);
        Matcher m2 = p2.matcher(str2);
        List<String> names = new ArrayList<String>();
        while (m2.find()) {
            names.add(m2.group().trim());
        }

        String[] sqlName = (String[])names.toArray(new String[names.size()]);
        return autoSearchPagination(dao, sql, sqlName, attributeName, params, pagIndex, pageRows, sort, map1);
    }

    /**
     * 自动查询功能
     * @return
     */
    public static Pagination autoSearchPagination(BaseViewObject dao, String sql, JSONObject params, int pagIndex, int pageRows) {
        return autoSearchPagination(dao, sql, params, pagIndex, pageRows, null, null);
    }

    /**
     * 自动查询功能
     */
    public static Pagination autoSearchPagination(BaseViewObject dao, String sql, JSONObject params, int pagIndex, int pageRows, String sort) {
        return autoSearchPagination(dao, sql, params, pagIndex, pageRows, sort, null);
    }

    /**
     * 自动查询功能
     */
    public static Pagination autoSearchPagination(BaseViewObject dao, String sql, JSONObject params, int pagIndex, int pageRows, Map map) {
        return autoSearchPagination(dao, sql, params, pagIndex, pageRows, null, map);
    }


    /**
     * 自动查询功能
     *
     * @param dao           Dao层
     * @param sql           查询sql
     * @param sqlName       sql上面对应的数据库的列名
     * @param attributeName bean上面的attribute的名称
     * @param params        查询参数
     * @param pagIndex      页码
     * @param pageRows      每页查询数量
     * @param sort          排序语句
     * @param map1          查询已经设置的map
     * @return
     */
    public static Pagination autoSearchPagination(BaseViewObject dao, String sql, String[] sqlName, String[] attributeName, JSONObject params, int pagIndex, int pageRows,
                                                  String sort, Map map1) {
        StringBuffer query = new StringBuffer();
        query.append(sql);
        Map map;
        if (map1 != null) {
            map = map1;
        } else {
            map = new HashMap();
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().startsWith("var_min_")) {
                String key = entry.getKey().substring(8);
                int index = -1;
                for (int i = 0, length = attributeName.length; i < length; i++) {
                    if (attributeName[i].equals(key)) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    map.put(entry.getKey(), entry.getValue().toString());
                    query.append(" and  " + sqlName[index] + " >= :" + entry.getKey() + "  ");
                }

            } else if (entry.getKey().startsWith("var_max_")) {
                String key = entry.getKey().substring(8);
                int index = -1;
                for (int i = 0, length = attributeName.length; i < length; i++) {
                    if (attributeName[i].equals(key)) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    map.put(entry.getKey(), entry.getValue().toString());
                    query.append(" and  " + sqlName[index] + " <= :" + entry.getKey() + "  ");
                }

            } else if (entry.getKey().startsWith("var_equal_")) {
                String key = entry.getKey().substring(10);
                int index = -1;
                for (int i = 0, length = attributeName.length; i < length; i++) {
                    if (attributeName[i].equals(key)) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    map.put(entry.getKey(), entry.getValue().toString());
                    query.append(" and  " + sqlName[index] + " = :" + entry.getKey() + "  ");
                }

            } else if (entry.getKey().startsWith("var_like_")) {
                String key = entry.getKey().substring(9);
                int index = -1;
                for (int i = 0, length = attributeName.length; i < length; i++) {
                    if (attributeName[i].equals(key)) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    map.put(entry.getKey(), "%" + entry.getValue().toString() + "%");
                    query.append(" and  " + sqlName[index] + " like :" + entry.getKey() + "  ");
                }
            } else if (entry.getKey().startsWith("var_noEqual_")) {
                String key = entry.getKey().substring(12);
                int index = -1;
                for (int i = 0, length = attributeName.length; i < length; i++) {
                    if (attributeName[i].equals(key)) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    map.put(entry.getKey(), entry.getValue().toString());
                    query.append(" and  " + sqlName[index] + " <> :" + entry.getKey() + "  ");
                }

            }

        }

        query.append("  " + (sort == null ? (" order by " + sqlName[0].split("\\.")[0] + ".LAST_UPDATE_DATE desc") : sort) + " ");

        Pagination findListResult = dao.findPagination(query.toString(), map, pagIndex, pageRows);
        return findListResult;

    }

    /**
     * @param <T>
     * @param t
     * @param json
     * @param vo
     * @param id
     * @param userId
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static <T> T getEditRow1(T t, JSONObject json, ViewObject vo, Object id, int userId) throws InstantiationException, IllegalAccessException, NoSuchMethodException,
                                                                                                       InvocationTargetException {
        try {
            if (id==null || StringUtils.isBlank(id.toString())) {
                t.getClass().getDeclaredMethod("setCreatedBy", Integer.class).invoke(t, userId);
                t.getClass().getDeclaredMethod("setCreationDate", Date.class).invoke(t, new Date());
                t.getClass().getDeclaredMethod("setLastUpdatedBy", Integer.class).invoke(t, userId);
                t.getClass().getDeclaredMethod("setLastUpdateDate", Date.class).invoke(t, new Date());
                t.getClass().getDeclaredMethod("setLastUpdateLogin", Integer.class).invoke(t, userId);
            } else {
                t = (T)vo.getById(Integer.parseInt(id.toString()));
                t.getClass().getDeclaredMethod("setLastUpdatedBy", Integer.class).invoke(t, userId);
                t.getClass().getDeclaredMethod("setLastUpdateDate", Date.class).invoke(t, new Date());
                t.getClass().getDeclaredMethod("setLastUpdateLogin", Integer.class).invoke(t, userId);
            }
        } catch (NoSuchMethodException e) {
            log.error("",e);
        }
        return t;
    }
}

