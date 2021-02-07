package com.sie.watsons.base.ob.model.inter.server;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectExceptionEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmProjectException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.yhg.base.utils.SToolUtils;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.ob.model.entities.PlmProjectExceptionEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProjectExceptionServer")
public class PlmProjectExceptionServer extends BaseCommonServer<PlmProjectExceptionEntity_HI> implements IPlmProjectException{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectExceptionServer.class);
	@Autowired
	private ViewObject<PlmProjectExceptionEntity_HI> plmProjectExceptionDAO_HI;
	@Autowired
	private BaseViewObject<PlmProjectExceptionEntity_HI_RO> plmProjectExceptionDAO_HI_RO;
	public PlmProjectExceptionServer() {
		super();
	}

	@Override
	public Pagination<PlmProjectExceptionEntity_HI_RO> findPlmProjectExceptionInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmProjectExceptionEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(PlmProjectExceptionEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" order by ppe.last_update_date desc ");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		Pagination<PlmProjectExceptionEntity_HI_RO> pagination = plmProjectExceptionDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmProjectExceptionEntity_HI> savePlmProjectExceptionInfo(JSONObject queryParamJSON) {
		if(SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("projectExceptionList"))){
			return null;
		}

		//校验日期格式
		for(int i = 0; i < queryParamJSON.getJSONArray("projectExceptionList").size(); i++){
			JSONObject dataObject = queryParamJSON.getJSONArray("projectExceptionList").getJSONObject(i);
			if(dataObject.getString("exceptionOccurrenceTime").matches(".*[a-zA-z].*")){
				throw new IllegalArgumentException("日期"+dataObject.getString("exceptionOccurrenceTime")+"不符规范！");
			}
		}

		List<PlmProjectExceptionEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("projectExceptionList").toString(),PlmProjectExceptionEntity_HI.class);
		for(PlmProjectExceptionEntity_HI data: dataList) {
			data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
			if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getInteger("plmProjectId")))
				data.setPlmProjectId(queryParamJSON.getInteger("plmProjectId"));
		}
		plmProjectExceptionDAO_HI.saveOrUpdateAll(dataList);
		return dataList;
	}

	/**
	 *
	 * @param queryParamJSON projectExceptionList 需删除的JSONArray形式行
	 *                       projectExceptionList，默认参数为单个实体
	 * @return
	 */
	@Override
	public Integer deletePlmProjectExceptionInfo(JSONObject queryParamJSON) {
		if(SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("projectExceptionList"))){
			PlmProjectExceptionEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmProjectExceptionEntity_HI.class);
			plmProjectExceptionDAO_HI.delete(entity);
			return 1;
		}
		List<PlmProjectExceptionEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("projectExceptionList").toString(),PlmProjectExceptionEntity_HI.class);
		plmProjectExceptionDAO_HI.deleteAll(dataList);
		return dataList.size();
	}

}
