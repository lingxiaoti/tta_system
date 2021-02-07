package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaRowEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSalesAreaRowEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSalesAreaRow;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("plmSalesAreaRowServer")
public class PlmSalesAreaRowServer extends BaseCommonServer<PlmSalesAreaRowEntity_HI> implements IPlmSalesAreaRow {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSalesAreaRowServer.class);
	@Autowired
	private ViewObject<PlmSalesAreaRowEntity_HI> plmSalesAreaRowDAO_HI;
	@Autowired
	private BaseViewObject<PlmSalesAreaRowEntity_HI_RO> plmSalesAreaRowDAO_HI_RO;


	public PlmSalesAreaRowServer() {
		super();
	}

	@Override
	public List<PlmSalesAreaRowEntity_HI_RO> findListByGroup(List<String> priceGroups) throws Exception {
		if(CollectionUtils.isEmpty(priceGroups)){
			throw new Exception("查询传参为空");
		}
		StringBuffer sql = new StringBuffer(PlmSalesAreaRowEntity_HI_RO.QUERY_SQL);
		sql.append(" and  psar.GROUP_CODE in (''");
		for ( String priceGroup : priceGroups) {
			sql.append(" ,'"+priceGroup+"'");
		}
		sql.append(")");
		List<PlmSalesAreaRowEntity_HI_RO> resultList = new ArrayList<>();
		resultList= plmSalesAreaRowDAO_HI_RO.findList(sql,new HashMap<>());
		return resultList;
	}

	@Override
	public Pagination<PlmSalesAreaRowEntity_HI_RO> findPlmSalesAreaRowInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmSalesAreaRowEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmSalesAreaRowEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" order by psar.PLM_SALES_AREA_ROW_ID");
		Pagination<PlmSalesAreaRowEntity_HI_RO> pagination = plmSalesAreaRowDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmSalesAreaRowEntity_HI> savePlmSalesAreaRowInfo(JSONObject queryParamJSON) {

		if (queryParamJSON.get("plmSalesAreaRowList") instanceof JSONArray) {
			List<PlmSalesAreaRowEntity_HI> dataList = JSON.parseArray(
					queryParamJSON.getJSONArray("plmSalesAreaRowList")
							.toString(), PlmSalesAreaRowEntity_HI.class);
			Set<String> qaBrandSet = new HashSet<>();
			Set<String> qaBrandEnSet = new HashSet<>();
			for (PlmSalesAreaRowEntity_HI data : dataList) {
				data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
				if(SaafToolUtils.isNullOrEmpty(data.getCreator())){
					data.setCreator(queryParamJSON.getString("creator"));
				}
				plmSalesAreaRowDAO_HI.saveOrUpdate(data);
			}
			return dataList;
		}

		return null;

	}

	@Override
	public Integer deletePlmSalesAreaRowInfo(JSONObject queryParamJSON) {
		if (SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getJSONArray("plmSalesAreaRowList"))) {
			PlmSalesAreaRowEntity_HI entity = JSON.parseObject(
					queryParamJSON.toString(),
					PlmSalesAreaRowEntity_HI.class);
			plmSalesAreaRowDAO_HI.delete(entity);
			return 1;
		}
		List<PlmSalesAreaRowEntity_HI> dataList = JSON.parseArray(
				queryParamJSON.getJSONArray("plmSalesAreaRowList")
						.toString(), PlmSalesAreaRowEntity_HI.class);
		plmSalesAreaRowDAO_HI.deleteAll(dataList);
		return dataList.size();
	}


}
