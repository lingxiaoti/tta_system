package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosProductionInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosProductionInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosProductionInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosProductionInfoServer")
public class EquPosProductionInfoServer extends BaseCommonServer<EquPosProductionInfoEntity_HI> implements IEquPosProductionInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosProductionInfoServer.class);

	@Autowired
	private ViewObject<EquPosProductionInfoEntity_HI> equPosProductionInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosProductionInfoEntity_HI_RO> equPosProductionInfoEntity_HI_RO;

	public EquPosProductionInfoServer() {
		super();
	}

	/**
	 * 查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
											  Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosProductionInfoEntity_HI_RO> pagination = equPosProductionInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询供应商生产信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosProductionInfoEntity_HI_RO findProductionInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosProductionInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosProductionInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosProductionInfoEntity_HI_RO> list = equPosProductionInfoEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 供应商生产信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosProductionInfoEntity_HI saveProductionInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
