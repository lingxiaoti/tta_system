package com.sie.watsons.base.product.model.inter.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductUpdatepropertisEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductUpdatepropertisEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductUpdatepropertis;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmProductUpdatepropertisServer")
public class PlmProductUpdatepropertisServer extends
		BaseCommonServer<PlmProductUpdatepropertisEntity_HI> implements
		IPlmProductUpdatepropertis {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductUpdatepropertisServer.class);

	@Autowired
	private ViewObject<PlmProductUpdatepropertisEntity_HI> plmProductUpdatepropertisDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductUpdatepropertisEntity_HI_RO> plmProductUpdatepropertisEntity_HI_RO;
	@Autowired
	private BaseViewObject<PlmProductHeadEntity_HI_RO> plmProductHeadEntity_HI_RO;

	public PlmProductUpdatepropertisServer() {
		super();
	}

	@Override
	public List<PlmProductUpdatepropertisEntity_HI> findListAll(
			JSONObject queryParamJSON) {
		StringBuffer sb = new StringBuffer(
				" from PlmProductUpdatepropertisEntity_HI where tablesName "
						+ "in('PLM_PRODUCT_HEAD','PLM_PRODUCT_MEDICALINFO','PLM_PRODUCT_DRUG') ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();

		SaafToolUtils.parperHbmParam(PlmProductUpdatepropertisEntity_HI.class,
				queryParamJSON, sb, queryParamMap);
		changeQuerySort(queryParamJSON, sb, "", true);

		List<PlmProductUpdatepropertisEntity_HI> findList = plmProductUpdatepropertisDAO_HI
				.findList(sb, queryParamMap);
		return findList;
	}

	@Override
	public Pagination<PlmProductUpdatepropertisEntity_HI_RO> findProductUpdateList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmProductUpdatepropertisEntity_HI_RO.query_sql);
		Map<String, Object> params = new HashMap<String, Object>();
		if (param.containsKey("id")) {
			String headid = param.getString("id");
			param.remove("productHeadId");
			param.put("productHeadId", headid);
		}
		SaafToolUtils.parperHbmParam(
				PlmProductUpdatepropertisEntity_HI_RO.class, param, query,
				params);
		query.append(PlmProductUpdatepropertisEntity_HI_RO.getAppend(param));
		query.append(" order by updatetable.CREATION_DATE desc ");

		Pagination<PlmProductUpdatepropertisEntity_HI_RO> pagination = plmProductUpdatepropertisEntity_HI_RO
				.findPagination(query, params, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public Pagination<PlmProductHeadEntity_HI_RO> FindUpdateByPropertiesList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmProductUpdatepropertisEntity_HI_RO.query2);
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmProductHeadEntity_HI_RO.class, param,
				query, params);

		Pagination<PlmProductHeadEntity_HI_RO> pagination = plmProductHeadEntity_HI_RO
				.findPagination(query, params, pageIndex, pageRows);
		return pagination;
	}

}
