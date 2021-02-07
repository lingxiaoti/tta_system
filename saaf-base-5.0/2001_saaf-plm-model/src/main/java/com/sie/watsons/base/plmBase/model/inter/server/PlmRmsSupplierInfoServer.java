package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmRmsSupplierInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmRmsSupplierInfoEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmRmsSupplierInfo;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("plmRmsSupplierInfoServer")
public class PlmRmsSupplierInfoServer extends BaseCommonServer<PlmRmsSupplierInfoEntity_HI> implements IPlmRmsSupplierInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsSupplierInfoServer.class);
	@Autowired
	private ViewObject<PlmRmsSupplierInfoEntity_HI> plmRmsSupplierInfoDAO_HI;
	@Autowired
	private BaseViewObject<PlmRmsSupplierInfoEntity_HI_RO> plmRmsSupplierInfoDAO_HI_RO;
	public PlmRmsSupplierInfoServer() {
		super();
	}

	@Override
	public Pagination<PlmRmsSupplierInfoEntity_HI_RO> findPlmRmsSupplierInfoInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmRmsSupplierInfoEntity_HI_RO.TTA_SUPPLIER);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmRmsSupplierInfoEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		Pagination<PlmRmsSupplierInfoEntity_HI_RO> pagination = plmRmsSupplierInfoDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmRmsSupplierInfoEntity_HI savePlmRmsSupplierInfoInfo(JSONObject queryParamJSON) {
		PlmRmsSupplierInfoEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmRmsSupplierInfoEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		plmRmsSupplierInfoDAO_HI.saveOrUpdate(entity);
		return entity;
	}


}
