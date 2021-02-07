package com.sie.watsons.base.product.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.model.entities.PlmUdaAttributeEntity_HI;
import com.sie.watsons.base.api.model.entities.readonly.PlmUdaAttributeEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmUdaAttribute;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmUdaAttributeServer")
public class PlmUdaAttributeServer extends
		BaseCommonServer<PlmUdaAttributeEntity_HI> implements IPlmUdaAttribute {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmUdaAttributeServer.class);

	@Autowired
	private ViewObject<PlmUdaAttributeEntity_HI> plmUdaAttributeDAO_HI;
	@Autowired
	private BaseViewObject<PlmUdaAttributeEntity_HI_RO> plmUdaAttributeEntity_HI_RO;
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public PlmUdaAttributeServer() {
		super();
	}

	@Override
	public Pagination<PlmUdaAttributeEntity_HI_RO> findUdaById(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmUdaAttributeEntity_HI_RO.query);
		if (queryParamJSON.containsKey("udaValueDesc_fulllike")) {
			query.append(" and upper(attrs.uda_value_desc) like '%"
					+ queryParamJSON.getString("udaValueDesc_fulllike")
							.toUpperCase() + "%' ");
			queryParamJSON.remove("udaValueDesc_fulllike");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmUdaAttributeEntity_HI_RO.class,
				queryParamJSON, query, params);

		Pagination<PlmUdaAttributeEntity_HI_RO> pagination = plmUdaAttributeEntity_HI_RO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		return pagination;
	}

	public PlmUdaAttributeEntity_HI findByUdaDesc(String udaDesc) {
		String hql = "FROM PlmUdaAttributeEntity_HI WHERE uda_value_desc=?";
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString(0,udaDesc);
		return (PlmUdaAttributeEntity_HI) query.setMaxResults(1).uniqueResult();
	}

}
