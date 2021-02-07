package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmDeptSubclassEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptSubclassEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmDeptSubclass;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDeptSubclassServer")
public class PlmDeptSubclassServer extends
		BaseCommonServer<PlmDeptSubclassEntity_HI> implements IPlmDeptSubclass {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDeptSubclassServer.class);
	@Autowired
	private ViewObject<PlmDeptSubclassEntity_HI> plmDeptSubclassDAO_HI;
	@Autowired
	private BaseViewObject<PlmDeptSubclassEntity_HI_RO> plmDeptSubclassDAO_HI_RO;
	@Autowired
	private ViewObject<PlmSupplierQaNonObInfoEntity_HI> plmSupplierQaNonObInfoDAO_HI;

	public PlmDeptSubclassServer() {
		super();
	}

	@Override
	public Pagination<PlmDeptSubclassEntity_HI_RO> findPlmDeptSubclassInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmDeptSubclassEntity_HI_RO.QUERY_SQL);
		// plmSubclassName

		if (queryParamJSON.containsKey("plmSubclassName")) {
			sql.append(" and upper(pds.PLM_SUBCLASS_NAME) like '%"
					+ queryParamJSON.getString("plmSubclassName").toUpperCase()
					+ "%' ");
			queryParamJSON.remove("plmSubclassName");
		}
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmDeptSubclassEntity_HI_RO.class, queryParamJSON, sql,
				paramsMap);
		sql.append(" ORDER BY pds.PLM_SUBCLASS_CODE ");
		Pagination<PlmDeptSubclassEntity_HI_RO> pagination = plmDeptSubclassDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

	public List<PlmDeptSubclassEntity_HI> savePlmDeptSubclassInfo(
			JSONObject queryParamJSON) {

		if (queryParamJSON.get("plmDeptSubclassList") instanceof JSONArray) {
			List<PlmDeptSubclassEntity_HI> dataList = JSON.parseArray(
					queryParamJSON.getJSONArray("plmDeptSubclassList")
							.toString(), PlmDeptSubclassEntity_HI.class);
			Set<String> qaBrandSet = new HashSet<>();
			Set<String> qaBrandEnSet = new HashSet<>();
			for (PlmDeptSubclassEntity_HI data : dataList) {
				data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
				plmDeptSubclassDAO_HI.saveOrUpdate(data);
			}
			return dataList;
		}

		return null;

	}

	public Integer deletePlmDeptSubclassInfo(JSONObject queryParamJSON) {
		if (SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getJSONArray("plmDeptSubclassList"))) {
			PlmDeptSubclassEntity_HI entity = JSON.parseObject(
					queryParamJSON.toString(), PlmDeptSubclassEntity_HI.class);
			plmDeptSubclassDAO_HI.delete(entity);
			return 1;
		}
		List<PlmDeptSubclassEntity_HI> dataList = JSON.parseArray(
				queryParamJSON.getJSONArray("plmDeptSubclassList").toString(),
				PlmDeptSubclassEntity_HI.class);
		plmDeptSubclassDAO_HI.deleteAll(dataList);
		return dataList.size();
	}

}
