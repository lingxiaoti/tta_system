package com.sie.watsons.base.ob.model.inter.server;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProductExceptionDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmProductExceptionDetail;
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
import com.sie.watsons.base.ob.model.entities.PlmProductExceptionDetailEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductExceptionDetailServer")
public class PlmProductExceptionDetailServer extends BaseCommonServer<PlmProductExceptionDetailEntity_HI> implements IPlmProductExceptionDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductExceptionDetailServer.class);
	@Autowired
	private ViewObject<PlmProductExceptionDetailEntity_HI> plmProductExceptionDetailDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductExceptionDetailEntity_HI_RO> plmProductExceptionDetailDAO_HI_RO;
	public PlmProductExceptionDetailServer() {
		super();
	}

	@Override
	public Pagination<PlmProductExceptionDetailEntity_HI_RO> findPlmProductExceptionDetailInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmProductExceptionDetailEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(PlmProductExceptionDetailEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		Pagination<PlmProductExceptionDetailEntity_HI_RO> pagination = plmProductExceptionDetailDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmProductExceptionDetailEntity_HI> savePlmProductExceptionDetailInfo(JSONObject queryParamJSON) {
		if(SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productExceptionDetailList"))){
			return null;
		}
		List<PlmProductExceptionDetailEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("productExceptionDetailList").toString(),PlmProductExceptionDetailEntity_HI.class);
		for(PlmProductExceptionDetailEntity_HI data: dataList) {
			data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
			if(SaafToolUtils.isNullOrEmpty(data.getPlmProductExceptionInfoId())&&!SaafToolUtils.isNullOrEmpty(queryParamJSON.getInteger("plmProductExceptionInfoId"))){
				data.setPlmProductExceptionInfoId(queryParamJSON.getInteger("plmProductExceptionInfoId"));
			}
		}
		plmProductExceptionDetailDAO_HI.saveOrUpdateAll(dataList);
		return dataList;
	}

	/**
	 *
	 * @param queryParamJSON ProductExceptionDetailList 需删除的JSONArray形式行
	 *                       ProductExceptionDetailList，默认参数为单个实体
	 * @return
	 */
	@Override
	public Integer deletePlmProductExceptionDetailInfo(JSONObject queryParamJSON) {
		if(SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("ProductExceptionDetailList"))){
			PlmProductExceptionDetailEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmProductExceptionDetailEntity_HI.class);
			plmProductExceptionDetailDAO_HI.delete(entity);
			return 1;
		}
		List<PlmProductExceptionDetailEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("ProductExceptionDetailList").toString(),PlmProductExceptionDetailEntity_HI.class);
		plmProductExceptionDetailDAO_HI.deleteAll(dataList);
		return dataList.size();
	}

}
