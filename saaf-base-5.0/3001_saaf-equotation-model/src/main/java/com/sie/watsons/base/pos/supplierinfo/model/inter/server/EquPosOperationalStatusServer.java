package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosOperationalStatusEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosOperationalStatusEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosOperationalStatus;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosOperationalStatusServer")
public class EquPosOperationalStatusServer extends BaseCommonServer<EquPosOperationalStatusEntity_HI> implements IEquPosOperationalStatus{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosOperationalStatusServer.class);

	@Autowired
	private ViewObject<EquPosOperationalStatusEntity_HI> equPosOperationalStatusDAO_HI;

	@Autowired
	private BaseViewObject<EquPosOperationalStatusEntity_HI_RO> equPosOperationalStatusEntity_HI_RO;

	public EquPosOperationalStatusServer() {
		super();
	}

	/**
	 * 查询供应商经营情况
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosOperationalStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosOperationalStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosOperationalStatusEntity_HI_RO> pagination = equPosOperationalStatusEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询供应商经营情况
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public EquPosOperationalStatusEntity_HI_RO findOperationalInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosOperationalStatusEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosOperationalStatusEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosOperationalStatusEntity_HI_RO> list = equPosOperationalStatusEntity_HI_RO.findList(sql, map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 供应商经营情况-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosOperationalStatusEntity_HI saveOperationalStatusInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

}
