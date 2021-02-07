package com.sie.watsons.base.plmBase.model.inter.server;

import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineRouteEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmOnlineRouteEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmOnlineRoute;
import com.sie.watsons.base.plmBase.model.inter.IPlmOnlineSubroute;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmOnlineRouteServer")
public class PlmOnlineRouteServer extends
		BaseCommonServer<PlmOnlineRouteEntity_HI> implements IPlmOnlineRoute {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmOnlineRouteServer.class);
	@Autowired
	private ViewObject<PlmOnlineRouteEntity_HI> plmOnlineRouteDAO_HI;
	@Autowired
	private BaseViewObject<PlmOnlineRouteEntity_HI_RO> plmOnlineRouteDAO_HI_RO;
	@Autowired
	private IPlmOnlineSubroute plmOnlineSubrouteServer;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	public PlmOnlineRouteServer() {
		super();
	}

	@Override
	public Pagination<PlmOnlineRouteEntity_HI_RO> findPlmOnlineRouteInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmOnlineRouteEntity_HI_RO.QUERY_SQL);
		if (queryParamJSON.containsKey("creator")) {
			String creator = queryParamJSON.getString("creator").toLowerCase();
			sql.append(" and lower(por.creator) like '%" + creator + "%'");
			queryParamJSON.remove("creator");
		}
		if (queryParamJSON.containsKey("isselect")) {
			sql.append(" and por.BILL_STATUS='Y' ");
		}
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmOnlineRouteEntity_HI_RO.class, queryParamJSON, sql,
				paramsMap);
		sql.append(" order by por.LAST_UPDATE_DATE desc");
		Pagination<PlmOnlineRouteEntity_HI_RO> pagination = plmOnlineRouteDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmOnlineRouteEntity_HI savePlmOnlineRouteInfo(
			JSONObject queryParamJSON) throws ServerException {
		PlmOnlineRouteEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(), PlmOnlineRouteEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		Integer headId = null;
		if (entity.getPlmOnlineRouteId() == null) {
			entity.setPlmOnlineRouteCode(this.getOnline());
			PlmOnlineRouteEntity_HI headobj = plmOnlineRouteDAO_HI
					.saveEntity(entity);
			headId = headobj.getPlmOnlineRouteId();
		} else {
			headId = entity.getPlmOnlineRouteId();
			plmOnlineRouteDAO_HI.update(entity);
		}
		// plmOnlineRouteDAO_HI.saveOrUpdate(entity);

		queryParamJSON.put("plmOnlineRouteId", headId);
		queryParamJSON
				.put("plmOnlineRouteCode", entity.getPlmOnlineRouteCode());
		queryParamJSON
				.put("plmOnlineRouteName", entity.getPlmOnlineRouteName());
		plmOnlineSubrouteServer.savePlmOnlineSubrouteInfo(queryParamJSON);
		return entity;
	}

	// XSQD+四位流水号
	public String getOnline() {
		return generateCodeServer.generateCode("XSQD", new SimpleDateFormat(
				"yyyyMMdd"), 4);

	}
}
