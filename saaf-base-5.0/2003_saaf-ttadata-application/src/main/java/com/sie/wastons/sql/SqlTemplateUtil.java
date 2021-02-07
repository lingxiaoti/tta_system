package com.sie.wastons.sql;

import com.sie.wastons.sql.annotation.SqlBinder;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 结合 SqlBinder 注解实现sql的动态查询
 */
@Slf4j
public class SqlTemplateUtil {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd");

    public static <T>List<T> findSqlList(BaseViewObject<T> baseViewObject, T entity , CharSequence sql, String groupSql) throws IllegalAccessException {
        if (StringUtils.isBlank(sql)){
            log.error("sql is blank");
            return Collections.emptyList();
        }
        if (entity==null){
            return baseViewObject.findList(sql);
        }
        List list=new ArrayList();
        StringBuilder sqlBuild= appendSql(entity,sql,groupSql,list);
        return baseViewObject.findList(sqlBuild,list.toArray());
    }


    public static <T>List<T> findSqlList(BaseViewObject<T> baseViewObject, T entity , CharSequence sql) throws IllegalAccessException {
        return findSqlList(baseViewObject,entity,sql,null);
    }

    public static <T> Pagination<T> findSqlPagination(BaseViewObject<T> baseViewObject, T entity , CharSequence sql, CharSequence groupSql, Integer pageIndex, Integer pageRows) throws IllegalAccessException {
        if (StringUtils.isBlank(sql)){
            log.error("sql is blank");
            return new Pagination<>();
        }
        if (entity==null){
            return baseViewObject.findPagination(sql,"select count(1) from (" + sql + ") cntemp",pageIndex,pageRows);
        }
        List list=new ArrayList();
        StringBuilder sqlBuild= appendSql(entity,sql,groupSql,list);
        return baseViewObject.findPagination(sqlBuild,"select count(1) from (" + sqlBuild + ") cntemp",pageIndex,pageRows,list.toArray());
    }

    public static <T> Pagination<T> findSqlPagination(BaseViewObject<T> baseViewObject, T entity , CharSequence sql, Integer pageIndex, Integer pageRows) throws IllegalAccessException {
        return findSqlPagination(baseViewObject,entity,sql,null,pageIndex,pageRows);
    }


    private static StringBuilder appendSql(Object entity , CharSequence sql,CharSequence groupSql,List<Object> sqlParams) throws IllegalAccessException {
        Class clzz=entity.getClass();
        StringBuilder sqlBuild=new StringBuilder(sql);
        Field[] fields= clzz.getDeclaredFields();
        List<String> orderList=new ArrayList<>();
        for (Field field:fields){
            SqlBinder sqlBinder = field.getAnnotation(SqlBinder.class);
            if (sqlBinder==null)
                continue;

            String columName= StringUtils.isBlank( sqlBinder.sqlColumn())?field.getName():sqlBinder.sqlColumn();
            String tableAlias=null;
            if (columName.contains(".")){
                String[] strs=columName.split("\\.");
                if (strs.length>0){
                    tableAlias=strs[0];
                }
            }

            if (tableAlias!=null &&  sqlBuild.indexOf(tableAlias)>-1){
                switch (sqlBinder.orderBy()){
                    case ASC:
                        orderList.add(columName);
                        break;
                    case DESC:
                        orderList.add(columName+" DESC");
                        break;
                }
            }

            boolean accessFlag=field.isAccessible();
            if (!accessFlag){
                field.setAccessible(true);
            }
            Object val= field.get(entity);
            if (!accessFlag){
                field.setAccessible(false);
            }
            if (sqlBinder.opreation()== SqlBinder.OPR.NOT_NULL){
                sqlBuild.append(" AND ").append(columName).append(" IS NOT NULL ");
                continue;
            }else if (sqlBinder.opreation()== SqlBinder.OPR.IS_NULL){
                sqlBuild.append(" AND ").append(columName).append(" IS NULL ");
                continue;
            }
            if (val==null){
                continue;
            }else if ((val instanceof String || val instanceof Integer || val instanceof Long || val instanceof BigInteger || val instanceof BigDecimal) && StringUtils.isBlank(val.toString())){
                continue;
            }

            // 日期类型处理
            val=dateFormat(sqlBinder,val);

            switch (sqlBinder.opreation()){
                case EQ:
                    sqlParams.add(val);
                    sqlBuild.append(" AND ").append(columName).append(" = ? ");
                    break;
                case NEQ:
                    sqlParams.add(val);
                    sqlBuild.append(" AND ").append(columName).append(" != ?");
                    break;
                case IN:
                    String str=val.toString().trim();
                    if (str.startsWith("(") && str.endsWith(")")){
                        sqlBuild.append(" AND ").append(columName).append(" IN ").append(str).append(" ");
                    }else{
                        sqlBuild.append(" AND ").append(columName).append(" IN ('").append(str.join(",","','")).append("') ");
                    }
                    break;
                case CONTAIN:
                    sqlParams.add("%"+val+"%");
                    sqlBuild.append(" AND ").append(columName).append(" like ? ");
                    break;
                case GT:
                    sqlParams.add(val);
                    sqlBuild.append(" AND ").append(columName).append(" > ? ");
                    break;
                case LT:
                    sqlParams.add(val);
                    sqlBuild.append(" AND ").append(columName).append(" < ? ");
                    break;
                case GTE:
                    sqlParams.add(val);
                    sqlBuild.append(" AND ").append(columName).append(" >= ? ");
                    break;
                case LTE:
                    sqlParams.add(val);
                    sqlBuild.append(" AND ").append(columName).append(" <= ? ");
                    break;
                case END_WITH:
                    sqlParams.add("%"+val);
                    sqlBuild.append(" AND ").append(columName).append(" like ? ");
                    break;
                case START_WITH:
                    sqlParams.add(val+"%");
                    sqlBuild.append(" AND ").append(columName).append(" like ? ");
                    break;
            }
        }
        if (orderList.size()>0 && StringUtils.isBlank(groupSql)){
            sqlBuild.append(" ORDER BY ").append(String.join(",",orderList));
        }else if (StringUtils.isNotBlank(groupSql)){
            sqlBuild.append(" ").append(groupSql);
        }
        return sqlBuild;
    }


    private static Object dateFormat(SqlBinder sqlBinder,Object val){
        if (!(val instanceof Date)){
            return val;
        }
        try {
            if (sqlBinder.timeClear()){
                val=SIMPLE_DATE_FORMAT.parse(SIMPLE_DATE_FORMAT.format(val));
            }
            if (sqlBinder.dayRoundUp()){
                Calendar cal = Calendar.getInstance();
                cal.setTime((Date) val);
                cal.add(5, 1);
                cal.set(11, 0);
                cal.set(12, 0);
                cal.set(13, 0);
                cal.set(14, 0);
                val= cal.getTime();
            }
        }catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return val;
    }


}
