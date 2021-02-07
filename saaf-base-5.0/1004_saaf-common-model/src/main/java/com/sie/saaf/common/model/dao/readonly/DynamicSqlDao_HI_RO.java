package com.sie.saaf.common.model.dao.readonly;

import com.alibaba.fastjson.JSON;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于以sql做为动态参数传入来执行sql的dao类
 *
 * @author Zhangbingshan(sam)
 * @email bszzhang@qq.com
 * @date 2019-01-11 10:17
 */
@Component
public class DynamicSqlDao_HI_RO extends DynamicViewObjectImpl<Object> {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     *
     * @param sql 传入的sql 占位符为：变量名，需要与map中参数的key相对应
     * @param map 执行sql时的参数
     * @return
     */
    public List<Map<String,Object>> executeSql(String sql, Map<String,Object> map) throws Exception {
        Session currentSession = getHibernateTemplate().getSessionFactory().getCurrentSession();
        SQLQuery sqlQuery = currentSession.createSQLQuery(sql);
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        for (String s : map.keySet()) {
            sqlQuery.setString(s,map.get(s).toString());
        }
        List list = new ArrayList();
        try {
             list = sqlQuery.list();
        }catch (Exception e) {
            logger.error("规则引擎执行表达式失败，sql:{},参数：{}",sql, JSON.toJSONString(map),e);
            return list;
        }
        releaseSession(currentSession);
        return list;
    }
}
