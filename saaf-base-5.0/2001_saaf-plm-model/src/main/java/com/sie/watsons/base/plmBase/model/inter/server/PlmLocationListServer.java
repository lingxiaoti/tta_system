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
import com.sie.watsons.base.plmBase.model.entities.PlmLocationListEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmLocationListEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmLocationList;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmLocationListServer")
public class PlmLocationListServer extends
		BaseCommonServer<PlmLocationListEntity_HI> implements IPlmLocationList {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmLocationListServer.class);
	@Autowired
	private ViewObject<PlmLocationListEntity_HI> plmLocationListDAO_HI;
	@Autowired
	private BaseViewObject<PlmLocationListEntity_HI_RO> plmLocationListDAO_HI_RO;

	public PlmLocationListServer() {
		super();
	}

	@Override
	public Pagination<PlmLocationListEntity_HI_RO> findPlmLocationListInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmLocationListEntity_HI_RO.QUERY_SQL);
		if (queryParamJSON.containsKey("code")) {
			sql.append(" and pll.CODE like '%"
					+ queryParamJSON.getString("code") + "%'");
			queryParamJSON.remove("code");
		}
		if (queryParamJSON.containsKey("descName")) {
			sql.append(" and pll.DESC_NAME like '%"
					+ queryParamJSON.getString("descName") + "%'");
			queryParamJSON.remove("descName");
		}
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmLocationListEntity_HI_RO.class, queryParamJSON, sql,
				paramsMap);
		sql.append(" order by pll.LAST_UPDATE_DATE desc");
		Pagination<PlmLocationListEntity_HI_RO> pagination = plmLocationListDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmLocationListEntity_HI savePlmLocationListInfo(
			JSONObject queryParamJSON) {
		PlmLocationListEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(), PlmLocationListEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		plmLocationListDAO_HI.saveOrUpdate(entity);
		return entity;
	}

}
