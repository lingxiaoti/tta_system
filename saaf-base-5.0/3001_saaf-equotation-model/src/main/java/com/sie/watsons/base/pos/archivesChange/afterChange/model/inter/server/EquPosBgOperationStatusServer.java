package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgOperationStatusEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgOperationStatusEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgOperationStatus;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgOperationStatusServer")
public class EquPosBgOperationStatusServer extends BaseCommonServer<EquPosBgOperationStatusEntity_HI> implements IEquPosBgOperationStatus{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgOperationStatusServer.class);

	@Autowired
	private ViewObject<EquPosBgOperationStatusEntity_HI> equPosBgOperationStatusDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgOperationStatusEntity_HI_RO> equPosBgOperationStatusEntity_HI_RO;

	public EquPosBgOperationStatusServer() {
		super();
	}

	/**
	 * 档案变更-供应商经营情况查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
													Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgOperationStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgOperationStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgOperationStatusEntity_HI_RO> pagination = equPosBgOperationStatusEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商经营情况查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosBgOperationStatusEntity_HI_RO findOperationalInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgOperationStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgOperationStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgOperationStatusEntity_HI_RO> list = equPosBgOperationStatusEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 档案变更-供应商经营情况保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgOperationStatusEntity_HI saveBgOperationalStatusInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
