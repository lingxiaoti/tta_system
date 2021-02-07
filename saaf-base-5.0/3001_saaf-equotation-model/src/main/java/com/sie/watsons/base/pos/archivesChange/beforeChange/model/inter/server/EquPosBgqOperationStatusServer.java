package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqOperationStatusEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqOperationStatusEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqOperationStatus;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqOperationStatusServer")
public class EquPosBgqOperationStatusServer extends BaseCommonServer<EquPosBgqOperationStatusEntity_HI> implements IEquPosBgqOperationStatus{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqOperationStatusServer.class);

	@Autowired
	private ViewObject<EquPosBgqOperationStatusEntity_HI> equPosBgqOperationStatusDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqOperationStatusEntity_HI_RO> equPosBgqOperationStatusEntity_HI_RO;

	public EquPosBgqOperationStatusServer() {
		super();
	}

	/**
	 * 档案变更前-供应商经营情况查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
												  Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqOperationStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqOperationStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgqOperationStatusEntity_HI_RO> pagination = equPosBgqOperationStatusEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商经营情况查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosBgqOperationStatusEntity_HI_RO findOperationalInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgqOperationStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqOperationStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgqOperationStatusEntity_HI_RO> list = equPosBgqOperationStatusEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 档案变更前-供应商经营情况保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqOperationStatusEntity_HI saveBgqOperationalStatusInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
