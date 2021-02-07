package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqSupplierEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqSupplier;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqSupplierServer")
public class EquPosBgqSupplierServer extends BaseCommonServer<EquPosBgqSupplierEntity_HI> implements IEquPosBgqSupplier{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqSupplierServer.class);

	@Autowired
	private ViewObject<EquPosBgqSupplierEntity_HI> equPosBgqSupplierDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqSupplierEntity_HI_RO> equPosBgqSupplierEntity_HI_RO;

	public EquPosBgqSupplierServer() {
		super();
	}

	/**
	 * 档案变更前-供应商基础信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findBgqSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
										 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqSupplierEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqSupplierEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgqSupplierEntity_HI_RO> pagination = equPosBgqSupplierEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商基础信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqSupplierEntity_HI saveBgqSupplierInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
