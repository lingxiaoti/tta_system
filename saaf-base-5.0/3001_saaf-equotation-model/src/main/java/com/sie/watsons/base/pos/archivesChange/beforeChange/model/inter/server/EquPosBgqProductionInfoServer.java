package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqProductionInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqProductionInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqProductionInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqProductionInfoServer")
public class EquPosBgqProductionInfoServer extends BaseCommonServer<EquPosBgqProductionInfoEntity_HI> implements IEquPosBgqProductionInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqProductionInfoServer.class);

	@Autowired
	private ViewObject<EquPosBgqProductionInfoEntity_HI> equPosBgqProductionInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqProductionInfoEntity_HI_RO> equPosBgqProductionInfoEntity_HI_RO;

	public EquPosBgqProductionInfoServer() {
		super();
	}

	/**
	 * 档案变更前-查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgqProductionInfoEntity_HI_RO> pagination = equPosBgqProductionInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosBgqProductionInfoEntity_HI_RO findProductionInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgqProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgqProductionInfoEntity_HI_RO> list = equPosBgqProductionInfoEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 档案变更前-供应商生产信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqProductionInfoEntity_HI saveBgqProductionInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
