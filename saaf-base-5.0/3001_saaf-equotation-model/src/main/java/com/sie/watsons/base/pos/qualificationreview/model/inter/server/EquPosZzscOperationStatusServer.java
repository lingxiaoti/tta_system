package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscOperationStatusEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscOperationStatusEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscOperationStatus;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscOperationStatusServer")
public class EquPosZzscOperationStatusServer extends BaseCommonServer<EquPosZzscOperationStatusEntity_HI> implements IEquPosZzscOperationStatus{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscOperationStatusServer.class);

	@Autowired
	private ViewObject<EquPosZzscOperationStatusEntity_HI> equPosZzscOperationStatusDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscOperationStatusEntity_HI_RO> equPosZzscOperationStatusEntity_HI_RO;

	public EquPosZzscOperationStatusServer() {
		super();
	}

	/**
	 * 资质审查-供应商经营情况查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
												Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscOperationStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscOperationStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscOperationStatusEntity_HI_RO> pagination = equPosZzscOperationStatusEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-供应商经营情况查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosZzscOperationStatusEntity_HI_RO findOperationalInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosZzscOperationStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscOperationStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosZzscOperationStatusEntity_HI_RO> list = equPosZzscOperationStatusEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 资质审查-供应商经营情况保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscOperationStatusEntity_HI saveZzscOperationalStatusInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
