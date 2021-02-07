package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgSupplierEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgSupplier;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgSupplierServer")
public class EquPosBgSupplierServer extends BaseCommonServer<EquPosBgSupplierEntity_HI> implements IEquPosBgSupplier{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgSupplierServer.class);

	@Autowired
	private ViewObject<EquPosBgSupplierEntity_HI> equPosBgSupplierDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgSupplierEntity_HI_RO> equPosBgSupplierEntity_HI_RO;

	public EquPosBgSupplierServer() {
		super();
	}

	/**
	 * 档案变更-供应商基础信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgSupplierEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgSupplierEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgSupplierEntity_HI_RO> pagination = equPosBgSupplierEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商基础信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgSupplierEntity_HI saveBgSupplierInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

}
