package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmCountryOfOriginEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmCountryOfOriginEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmCountryOfOrigin;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmCountryOfOriginServer")
public class PlmCountryOfOriginServer extends
		BaseCommonServer<PlmCountryOfOriginEntity_HI> implements
		IPlmCountryOfOrigin {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmCountryOfOriginServer.class);
	@Autowired
	private ViewObject<PlmCountryOfOriginEntity_HI> plmCountryOfOriginDAO_HI;
	@Autowired
	private BaseViewObject<PlmCountryOfOriginEntity_HI_RO> plmCountryOfOriginDAO_HI_RO;

	public PlmCountryOfOriginServer() {
		super();
	}

	private static String nameAbbreviationQuery = "nameAbbreviation_like";

	@Override
	public Pagination<PlmCountryOfOriginEntity_HI_RO> findPlmCountryOfOriginInfo(
			JSONObject queryParams, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmCountryOfOriginEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		if (!SaafToolUtils.isNullOrEmpty(queryParams
				.getString(nameAbbreviationQuery))) {
			sql.append(" and pcoo.NAME_ABBREVIATION LIKE UPPER('"
					+ queryParams.getString(nameAbbreviationQuery) + "') ");
			queryParams.remove(nameAbbreviationQuery);
		}

		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmCountryOfOriginEntity_HI_RO.class, queryParams, sql,
				paramsMap);
		sql.append(" order by pcoo.plm_country_of_origin_id");
		Pagination<PlmCountryOfOriginEntity_HI_RO> pagination = plmCountryOfOriginDAO_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmCountryOfOriginEntity_HI savePlmCountryOfOriginInfo(
			JSONObject queryParams) {
		PlmCountryOfOriginEntity_HI entity = JSON.parseObject(
				queryParams.toString(), PlmCountryOfOriginEntity_HI.class);
		entity.setOperatorUserId(queryParams.getInteger("varUserId"));
		plmCountryOfOriginDAO_HI.saveOrUpdate(entity);
		return entity;
	}

}
