package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.WxUserEntity_HI;
import com.sie.saaf.base.user.model.inter.IWxUser;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzg on 2018/3/7.
 */
@Component("wxUserServer")
public class WxUserServer extends BaseCommonServer<WxUserEntity_HI> implements IWxUser {
//    private static final Logger LOGGER = LoggerFactory.getLogger(WxUserServer.class);

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    private ViewObject<WxUserEntity_HI> wxUserDAO_HI;

    @Override
    public List<String> findByName(JSONObject queryParamJSON) {
        List<String> ret = new ArrayList<>();
        String hql = String.format("from WxUserEntity_HI where name like '%%%s%%'", queryParamJSON.get("name"));
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        query.setMaxResults(10);
        List<WxUserEntity_HI> list = query.list();
        for(WxUserEntity_HI user :list){
            ret.add(user.getName());
        }
        session.close();
        return ret;
    }

    @Override
    public List<WxUserEntity_HI> findByCondition(JSONObject queryParamJSON) {
        List<String> ret = new ArrayList<String>();
        return wxUserDAO_HI.findList("from WxUserEntity_HI where name ='" + queryParamJSON.get("name") + "' or mobile='" + queryParamJSON.get("name") + "' or email='" + queryParamJSON.get("name")+"'");
    }
}
