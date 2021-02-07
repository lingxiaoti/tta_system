package com.sie.watsons.base.plmBase.model.inter.server;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineSubrouteEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmOnlineSubrouteEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmOnlineSubroute;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmOnlineSubrouteServer")
public class PlmOnlineSubrouteServer extends
		BaseCommonServer<PlmOnlineSubrouteEntity_HI> implements
		IPlmOnlineSubroute {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmOnlineSubrouteServer.class);
	@Autowired
	private ViewObject<PlmOnlineSubrouteEntity_HI> plmOnlineSubrouteDAO_HI;
	@Autowired
	private IPlmOnlineSubroute subroute;
	@Autowired
	private BaseViewObject<PlmOnlineSubrouteEntity_HI_RO> plmOnlineSubrouteDAO_HI_RO;

	public PlmOnlineSubrouteServer() {
		super();
	}

	@Override
	public Pagination<PlmOnlineSubrouteEntity_HI_RO> findPlmOnlineSubrouteInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmOnlineSubrouteEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmOnlineSubrouteEntity_HI_RO.class, queryParamJSON, sql,
				paramsMap);
		sql.append(" order by pos.LAST_UPDATE_DATE desc");
		Pagination<PlmOnlineSubrouteEntity_HI_RO> pagination = plmOnlineSubrouteDAO_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	public List<PlmOnlineSubrouteEntity_HI> savePlmOnlineSubrouteInfo(
			JSONObject queryParamJSON) throws ServerException {

		if (queryParamJSON.containsKey("plmOnlineSubrouteList")) {
			List<PlmOnlineSubrouteEntity_HI> dataList = JSON.parseArray(
					queryParamJSON.getJSONArray("plmOnlineSubrouteList")
							.toString(), PlmOnlineSubrouteEntity_HI.class);
			for (PlmOnlineSubrouteEntity_HI data : dataList) {

				data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
				data.setPlmOnlineRouteId(queryParamJSON
						.getInteger("plmOnlineRouteId"));
				data.setPlmOnlineRouteCode(queryParamJSON
						.getString("plmOnlineRouteCode"));
				data.setPlmOnlineRouteName(queryParamJSON
						.getString("plmOnlineRouteName"));
				if (SaafToolUtils.isNullOrEmpty(data.getCreator())) {
					data.setCreator(queryParamJSON.getString("creator"));
				}
				plmOnlineSubrouteDAO_HI.saveOrUpdate(data);

			}
			return dataList;
		}

		return null;

	}

	public Integer deletePlmOnlineSubrouteInfo(JSONObject queryParamJSON) {
		if (SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getJSONArray("plmOnlineSubrouteList"))) {
			PlmOnlineSubrouteEntity_HI entity = JSON
					.parseObject(queryParamJSON.toString(),
							PlmOnlineSubrouteEntity_HI.class);
			plmOnlineSubrouteDAO_HI.delete(entity);
			return 1;
		}
		List<PlmOnlineSubrouteEntity_HI> dataList = JSON
				.parseArray(queryParamJSON
						.getJSONArray("plmOnlineSubrouteList").toString(),
						PlmOnlineSubrouteEntity_HI.class);
		plmOnlineSubrouteDAO_HI.deleteAll(dataList);
		return dataList.size();
	}

	@Override
	public String updatePlmOnlineSubrouteInfo(JSONObject parseObject) {
		Integer subrouteid = parseObject.getInteger("onlineSubrouteid");
		PlmOnlineSubrouteEntity_HI online = this.getById(subrouteid);
		String status = parseObject.getString("status");
		try {
			if (status.equals("Y")) // 生效
			{
				online.setBillStatus("Y");
				online.setBillStatusName("已生效");
			} else if (status.equals("N")) {
				online.setBillStatus("N");
				online.setBillStatusName("已失效");
			}
			this.update(online);
			return SToolUtils.convertResultJSONObj("S", "操作成功", 0, online)
					.toString();
		} catch (Exception e) {
			return "操作失败:" + e.getMessage();
		}

	}

}
