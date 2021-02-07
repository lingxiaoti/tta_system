package com.sie.saaf.message.model.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component("messageDAO_HI")
public class MessageDAO_HI {

    protected HibernateTemplate hibernateTemplate;
    protected SessionFactory sessionFactory;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource(name = "hibernateTemplete")
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void executeUpdateSql(String sql, Map<String, Object> parameterMap) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Iterator iterator = parameterMap.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            query.setParameter(key, parameterMap.get(key));
        }
        query.executeUpdate();
    }


    public List<Map<String, Object>> executeQuerySql(String sql, Map<String, Object> parameterMap) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Iterator iterator = parameterMap.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = parameterMap.get(key);
            if (value instanceof List) {
                query.setParameterList(key, (List) value);
            } else {
                query.setParameter(key, value);
            }
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
}
