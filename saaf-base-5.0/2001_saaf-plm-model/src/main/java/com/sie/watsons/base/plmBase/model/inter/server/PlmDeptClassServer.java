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
import com.sie.watsons.base.plmBase.model.entities.PlmDeptClassEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptClassEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmDeptClass;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDeptClassServer")
public class PlmDeptClassServer extends BaseCommonServer<PlmDeptClassEntity_HI>
		implements IPlmDeptClass {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDeptClassServer.class);
	@Autowired
	private ViewObject<PlmDeptClassEntity_HI> plmDeptClassDAO_HI;
	@Autowired
	private BaseViewObject<PlmDeptClassEntity_HI_RO> plmDeptClassDAO_HI_RO;
	@Autowired
	private ViewObject<PlmSupplierQaNonObInfoEntity_HI> plmSupplierQaNonObInfoDAO_HI;

	public PlmDeptClassServer() {
		super();
	}

	@Override
	public Pagination<PlmDeptClassEntity_HI_RO> findPlmDeptClassInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmDeptClassEntity_HI_RO.QUERY_SQL);
		String result = "";
		if (queryParamJSON.containsKey("mainclass")) {
			if (!queryParamJSON.getString("mainclass").equals("")) {
				String mainclass = queryParamJSON.getString("mainclass");
				for (String ma : mainclass.split(",")) {
					result += "'" + ma + "',";
				}
				sql.append(" and plm_class_code in("
						+ result.substring(0, result.length() - 1) + ")");
			}
			queryParamJSON.remove("mainclass");
		}

		// plmClassName
		if (queryParamJSON.containsKey("plmClassName")) {
			sql.append(" and upper(pdc.PLM_CLASS_NAME) like '%"
					+ queryParamJSON.getString("plmClassName").toUpperCase()
					+ "%' ");
			queryParamJSON.remove("plmClassName");
		}

		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmDeptClassEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" ORDER BY pdc.PLM_CLASS_CODE ");
		Pagination<PlmDeptClassEntity_HI_RO> pagination = plmDeptClassDAO_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmDeptClassEntity_HI> savePlmDeptClassInfo(
			JSONObject queryParamJSON) {

		if (queryParamJSON.get("plmDeptClassList") instanceof JSONArray) {
			List<PlmDeptClassEntity_HI> dataList = JSON.parseArray(
					queryParamJSON.getJSONArray("plmDeptClassList").toString(),
					PlmDeptClassEntity_HI.class);
			Set<String> qaBrandSet = new HashSet<>();
			Set<String> qaBrandEnSet = new HashSet<>();
			for (PlmDeptClassEntity_HI data : dataList) {
				data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
				plmDeptClassDAO_HI.saveOrUpdate(data);
			}
			return dataList;
		}

		return null;

	}

	@Override
	public Integer deletePlmDeptClassInfo(JSONObject queryParamJSON) {
		if (SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getJSONArray("plmDeptClassList"))) {
			PlmDeptClassEntity_HI entity = JSON.parseObject(
					queryParamJSON.toString(), PlmDeptClassEntity_HI.class);
			plmDeptClassDAO_HI.delete(entity);
			return 1;
		}
		List<PlmDeptClassEntity_HI> dataList = JSON.parseArray(queryParamJSON
				.getJSONArray("plmDeptClassList").toString(),
				PlmDeptClassEntity_HI.class);
		plmDeptClassDAO_HI.deleteAll(dataList);
		return dataList.size();
	}

}
