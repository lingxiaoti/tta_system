package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgProductionInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgProductionInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgProductionInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgProductionInfoServer")
public class EquPosBgProductionInfoServer extends BaseCommonServer<EquPosBgProductionInfoEntity_HI> implements IEquPosBgProductionInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgProductionInfoServer.class);

	@Autowired
	private ViewObject<EquPosBgProductionInfoEntity_HI> equPosBgProductionInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgProductionInfoEntity_HI_RO> equPosBgProductionInfoEntity_HI_RO;

	public EquPosBgProductionInfoServer() {
		super();
	}

	/**
	 * 档案变更-查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
											 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgProductionInfoEntity_HI_RO> pagination = equPosBgProductionInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosBgProductionInfoEntity_HI_RO findProductionInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgProductionInfoEntity_HI_RO> list = equPosBgProductionInfoEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 档案变更-供应商生产信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgProductionInfoEntity_HI saveBgProductionInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
