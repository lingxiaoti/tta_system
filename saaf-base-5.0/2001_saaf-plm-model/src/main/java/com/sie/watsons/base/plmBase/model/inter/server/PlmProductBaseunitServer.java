package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.plmBase.model.entities.PlmProductBaseunitEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmProductBaseunitEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmProductBaseunit;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmProductBaseunitServer")
public class PlmProductBaseunitServer extends
		BaseCommonServer<PlmProductBaseunitEntity_HI> implements
		IPlmProductBaseunit {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBaseunitServer.class);

	@Autowired
	private ViewObject<PlmProductBaseunitEntity_HI> plmProductBaseunitDAO_HI;

	@Autowired
	private BaseViewObject<PlmProductBaseunitEntity_HI_RO> productBaseunitEntity_HI_RO;

	public PlmProductBaseunitServer() {
		super();
	}

	@Override
	public Pagination<PlmProductBaseunitEntity_HI_RO> findPlmProductBaseunit(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmProductBaseunitEntity_HI_RO.Query);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmProductBaseunitEntity_HI_RO.class, queryParamJSON, sql,
				paramsMap);
		Pagination<PlmProductBaseunitEntity_HI_RO> pagination = productBaseunitEntity_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

}
