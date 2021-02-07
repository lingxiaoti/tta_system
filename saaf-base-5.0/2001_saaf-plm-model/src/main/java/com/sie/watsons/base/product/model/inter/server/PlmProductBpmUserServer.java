package com.sie.watsons.base.product.model.inter.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductBpmUserEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmUserEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductBpmUser;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmProductBpmUserServer")
public class PlmProductBpmUserServer extends
		BaseCommonServer<PlmProductBpmUserEntity_HI> implements
		IPlmProductBpmUser {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBpmUserServer.class);

	@Autowired
	private ViewObject<PlmProductBpmUserEntity_HI> plmProductBpmUserDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductBpmUserEntity_HI_RO> plmProductBpmUserEntity_HI_RO;

	public PlmProductBpmUserServer() {
		super();
	}

	@Override
	public Pagination<PlmProductBpmUserEntity_HI_RO> findBpmUserByCondition(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmProductBpmUserEntity_HI_RO.querySql);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		String processNodeCode = queryParamJSON.getString("processNodeCode");
		Integer ownerUserId = queryParamJSON.getInteger("ownerUserId");
		SaafToolUtils.parperHbmParam(PlmProductBpmUserEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		if (!"".equals(processNodeCode) && processNodeCode != null) {
			sql.append(" and pbu.process_node_code = '" + processNodeCode
					+ "' ");
		}
		if (!"".equals(ownerUserId) && ownerUserId != null) {
			sql.append(" and pbu.owner_user_id = '" + ownerUserId + "' ");
		}
		sql.append(" order by pbu.last_update_date ");
		Pagination<PlmProductBpmUserEntity_HI_RO> findListResult = plmProductBpmUserEntity_HI_RO
				.findPagination(sql, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	@Override
	public List<PlmProductBpmUserEntity_HI_RO> plmProductBpmUserServer(
			JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(
				PlmProductBpmUserEntity_HI_RO.querySql);
		String processNodeCode = queryParamJSON.getString("processNodeCode");
		Integer ownerUserId = queryParamJSON.getInteger("ownerUserId");
		if (!"".equals(processNodeCode) && processNodeCode != null) {
			sql.append(" and pbu.process_node_code = '" + processNodeCode
					+ "' ");
		}
		if (!"".equals(ownerUserId) && ownerUserId != null) {
			sql.append(" and pbu.owner_user_id = '" + ownerUserId + "' ");
		}
		sql.append(" order by pbu.last_update_date ");

		List<PlmProductBpmUserEntity_HI_RO> bpmUsers = plmProductBpmUserEntity_HI_RO
				.findList(sql.toString());

		return bpmUsers;
	}

	@Override
	public String deletedByCondition(JSONObject queryParamJSON) {
		JSONArray ids = queryParamJSON.getJSONArray("seqIds");
		String sql = "delete from plm_product_bpm_user where seq_id in (''";
		StringBuilder sb = new StringBuilder(sql);
		if (CollectionUtils.isNotEmpty(ids)) {
			Map<String, Object> paramsMap = new HashMap<>();
			List<Integer> seqIds = ids.toJavaList(Integer.class);
			if (CollectionUtils.isNotEmpty(seqIds)) {
				paramsMap.put("seqIds", seqIds);
				for (Integer id : seqIds) {
					sql = sql + "," + id;
					sb.append("," + id);
				}
			}
			sql = sql + ")";
			sb.append(")");
			plmProductBpmUserDAO_HI.executeSql(sb.toString());
		}
		return "S";
	}

	@Override
	public String saveProductBpmUser(JSONObject queryParamJSON) {
		PlmProductBpmUserEntity_HI plmProductBpmUserEntity = JSON.parseObject(
				queryParamJSON.toString(), PlmProductBpmUserEntity_HI.class);
		plmProductBpmUserDAO_HI.saveOrUpdate(plmProductBpmUserEntity);
		// JSONObject result = new JSONObject();
		// result.put("data",plmSupWarehouseEntity_HI);
		return JSONObject.toJSONString(plmProductBpmUserEntity);
	}

}
