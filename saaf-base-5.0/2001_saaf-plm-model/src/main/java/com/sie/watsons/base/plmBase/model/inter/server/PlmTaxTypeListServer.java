package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.plmBase.model.entities.PlmTaxTypeListEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmTaxTypeListEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmTaxTypeList;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("plmTaxTypeListServer")
public class PlmTaxTypeListServer extends BaseCommonServer<PlmTaxTypeListEntity_HI> implements IPlmTaxTypeList {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmTaxTypeListServer.class);
	@Autowired
	private ViewObject<PlmTaxTypeListEntity_HI> plmTaxTypeListDAO_HI;
	@Autowired
	private BaseViewObject<PlmTaxTypeListEntity_HI_RO> plmTaxTypeListDAO_HI_RO;
	public PlmTaxTypeListServer() {
		super();
	}

	@Override
	public Pagination<PlmTaxTypeListEntity_HI_RO> findPlmTaxTypeListInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmTaxTypeListEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmTaxTypeListEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		Pagination<PlmTaxTypeListEntity_HI_RO> pagination = plmTaxTypeListDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmTaxTypeListEntity_HI savePlmTaxTypeListInfo(JSONObject queryParamJSON) {
		PlmTaxTypeListEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmTaxTypeListEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		plmTaxTypeListDAO_HI.saveOrUpdate(entity);
		return entity;
	}

}
