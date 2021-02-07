package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscProductionInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscProductionInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscProductionInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscProductionInfoServer")
public class EquPosZzscProductionInfoServer extends BaseCommonServer<EquPosZzscProductionInfoEntity_HI> implements IEquPosZzscProductionInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscProductionInfoServer.class);

	@Autowired
	private ViewObject<EquPosZzscProductionInfoEntity_HI> equPosZzscProductionInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscProductionInfoEntity_HI_RO> equPosZzscProductionInfoEntity_HI_RO;

	public EquPosZzscProductionInfoServer() {
		super();
	}

	/**
	 * 资质审查-查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
										 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscProductionInfoEntity_HI_RO> pagination = equPosZzscProductionInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosZzscProductionInfoEntity_HI_RO findProductionInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosZzscProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosZzscProductionInfoEntity_HI_RO> list = equPosZzscProductionInfoEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 资质审查-供应商生产信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscProductionInfoEntity_HI saveZzscProductionInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
