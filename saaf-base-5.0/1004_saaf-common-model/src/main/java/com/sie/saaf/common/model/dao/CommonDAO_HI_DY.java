package com.sie.saaf.common.model.dao;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.HibernateHandler;
import com.yhg.hibernate.core.dao.ViewObjectDynamicFormImpl;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Component("commonDAO_HI_DY")
public class CommonDAO_HI_DY extends ViewObjectDynamicFormImpl {
    public CommonDAO_HI_DY() {
        super();
    }

    @Override
    public Pagination<JSONObject> findPagination(CharSequence queryString, CharSequence countString, int pageIndex, int pageSize, Object... params) {
        return super.findPagination(queryString, countString, pageIndex, pageSize, params);
    }

    @Override
    public List<JSONObject> findList(CharSequence queryString, Map params) {
        return super.findList(queryString, params);
    }

    @Override
    public List<JSONObject> findList(CharSequence queryString, Object... params) {
        return super.findList(queryString, params);
    }

    @Override
    public Pagination<JSONObject> findPagination(CharSequence queryString, CharSequence countString, Map params, int pageIndex, int pageSize) {
        return super.findPagination(queryString, countString, params, pageIndex, pageSize);
    }

    @Override
    public Pagination<JSONObject> findPagination(CharSequence queryString, int pageIndex, int pageSize, Object... params) {
        return super.findPagination(queryString, SaafToolUtils.getSqlCountString(new StringBuffer(queryString)), pageIndex, pageSize, params);
    }

    @Override
    public Pagination<JSONObject> findPagination(CharSequence queryString, Map params, int pageIndex, int pageSize) {
        return super.findPagination(queryString, SaafToolUtils.getSqlCountString(new StringBuffer(queryString)),  params, pageIndex, pageSize);
    }

    @Override
    public Pagination<JSONObject> findSqlPagination(CharSequence queryString, CharSequence countString, Map params, int pageIndex, int pageSize) {
        return super.findSqlPagination(queryString, countString, params, pageIndex, pageSize);
    }

//    @Override
//    public void saveOrUpdateExecuteSQL(String sql) {
//        super.saveOrUpdateExecuteSQL(sql);
//    }

    @Override
    public Integer saveOrUpdateExecuteSQL(String tableName, JSONObject entity) {
        return super.saveOrUpdateExecuteSQL(tableName, entity);
    }

    @Override
    public void setMyHibernateTemplace(HibernateTemplate hibernateTemplete) {
        super.setMyHibernateTemplace(hibernateTemplete);
    }

    @Override
    public String createTableEntity(String tableName, String createTableSQL) {
        return super.createTableEntity(tableName, createTableSQL);
    }

    public JSONObject findById(String tableName, Serializable id) {
        String queryString = "select * from " + tableName + " where auto_id=?";
        List<JSONObject> list = findList(queryString, id);
        if (list == null || list.isEmpty()) {
            return new JSONObject();
        }

        //以下代码将字符串true、false转为boolean类型，因为在angular绑定时，字符串"true"或"false"不能让checkbox选中
        Map<String, Object> retMap = list.get(0);
        for (String key : retMap.keySet()) {
            if (retMap.get(key) != null) {
                String value = retMap.get(key).toString();
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    retMap.put(key, new Boolean(value));
                }
            }
        }
        //end

        String jsonstr = JSONObject.toJSONString(retMap);
        return JSONObject.parseObject(jsonstr);
    }

    @Override
    public String createOrAlterTableEntity(String string, String string2) {
        return super.createOrAlterTableEntity(string, string2);
    }

    @Override
    public Object executeQuery(HibernateHandler hibernateHandler) {
        return super.executeQuery(hibernateHandler);
    }

    @Override
    public JSONObject get(CharSequence charSequence, Map map) {
        return super.get(charSequence, map);
    }

    @Override
    public JSONObject get(CharSequence charSequence, Object... object) {
        return super.get(charSequence, object);
    }

    @Override
    public JSONObject get(Class c, Serializable serializable) {
        return super.get(c, serializable);
    }

    @Override
    public JSONObject getById(Serializable serializable) {
        return super.getById(serializable);
    }

    @Override
    public JSONObject getById(String string, Serializable serializable) {
        return super.getById(string, serializable);
    }

    @Override
    public String getQuerySQL() {
        return super.getQuerySQL();
    }
}

