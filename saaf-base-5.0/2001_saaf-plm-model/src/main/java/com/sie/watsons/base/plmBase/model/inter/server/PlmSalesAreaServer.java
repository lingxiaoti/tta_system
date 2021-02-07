package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaRowEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSalesAreaEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSalesArea;
import com.sie.watsons.base.plmBase.model.inter.IPlmSalesAreaRow;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.yhg.base.utils.SToolUtils;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmSalesAreaServer")
public class PlmSalesAreaServer extends BaseCommonServer<PlmSalesAreaEntity_HI> implements IPlmSalesArea {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSalesAreaServer.class);
	@Autowired
	private ViewObject<PlmSalesAreaEntity_HI> plmSalesAreaDAO_HI;
	@Autowired
	private BaseViewObject<PlmSalesAreaEntity_HI_RO> plmSalesAreaDAO_HI_RO;
	@Autowired
	private IPlmSalesAreaRow plmSalesAreaRowServer;

	public PlmSalesAreaServer() {
		super();
	}

	@Override
	public Pagination<PlmSalesAreaEntity_HI_RO> findPlmSalesAreaInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmSalesAreaEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmSalesAreaEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		Pagination<PlmSalesAreaEntity_HI_RO> pagination = plmSalesAreaDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmSalesAreaEntity_HI savePlmSalesAreaInfo(JSONObject queryParamJSON) {
		PlmSalesAreaEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmSalesAreaEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		plmSalesAreaDAO_HI.saveOrUpdate(entity);
		queryParamJSON.put("plmSalesAreaId", entity.getPlmSalesAreaId());
		plmSalesAreaRowServer.savePlmSalesAreaRowInfo(queryParamJSON);
		return entity;
	}

}
