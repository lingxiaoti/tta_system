package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.PlmUserBrandMapEntity_HI;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@Component("plmUserBrandMapDAO_HI")
public class PlmUserBrandMapDAO_HI extends BaseCommonDAO_HI<PlmUserBrandMapEntity_HI> {

	@Autowired
	protected HibernateTemplate hibernateTemplete;

	@Autowired
	protected SessionFactory sessionFactory;

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	public SessionFactory getSuperSessionFactory(){
		return sessionFactory;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(PlmUserBrandMapDAO_HI.class);
	public PlmUserBrandMapDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmUserBrandMapEntity_HI entity) {
		return super.save(entity);
	}
}
