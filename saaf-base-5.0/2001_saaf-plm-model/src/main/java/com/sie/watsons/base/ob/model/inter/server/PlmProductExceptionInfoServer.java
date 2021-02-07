package com.sie.watsons.base.ob.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmProductExceptionInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProductExceptionInfoEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmProductExceptionDetail;
import com.sie.watsons.base.ob.model.inter.IPlmProductExceptionInfo;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("plmProductExceptionInfoServer")
public class PlmProductExceptionInfoServer extends BaseCommonServer<PlmProductExceptionInfoEntity_HI> implements IPlmProductExceptionInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductExceptionInfoServer.class);
	@Autowired
	private ViewObject<PlmProductExceptionInfoEntity_HI> plmProductExceptionInfoDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductExceptionInfoEntity_HI_RO> plmProductExceptionInfoDAO_HI_RO;
	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private IPlmProductExceptionDetail plmProductExceptionDetailServer;

	public PlmProductExceptionInfoServer() {
		super();
	}


	@Override
	public Pagination<PlmProductExceptionInfoEntity_HI_RO> findPlmProductExceptionInfoInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmProductExceptionInfoEntity_HI_RO.QUERY_SQL);
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))){
			sql = new StringBuffer(PlmProductExceptionInfoEntity_HI_RO.REPORT_SQL);
		}
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(PlmProductExceptionInfoEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" order by ppei.last_update_date desc ");
		Pagination<PlmProductExceptionInfoEntity_HI_RO> pagination = plmProductExceptionInfoDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmProductExceptionInfoEntity_HI savePlmProductExceptionInfoInfo(JSONObject queryParamJSON) {
		PlmProductExceptionInfoEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmProductExceptionInfoEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		if(SaafToolUtils.isNullOrEmpty(entity.getProductExceptionNum())){
			entity.setProductExceptionNum(generateCodeServer.generateCode("YC",new SimpleDateFormat("yy"),5));
		}
		if(SaafToolUtils.isNullOrEmpty(entity.getCreator())){
			entity.setCreator(queryParamJSON.getString("varUserFullName"));
		}
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("commandStatus"))&&queryParamJSON.getString("commandStatus").equals("CLOSED")){
			entity.setEndDate(new Date());
		}
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("commandStatus"))&&(queryParamJSON.getString("commandStatus").equals("CLOSED")||queryParamJSON.getString("commandStatus").equals("FOLLOWING"))){
			if(SaafToolUtils.isNullOrEmpty(entity.getStartDate())){
				entity.setStartDate(new Date());
			}
		}
		PlmProductExceptionInfoEntity_HI resultData = plmProductExceptionInfoDAO_HI.saveEntity(entity);
		queryParamJSON.put("plmProductExceptionInfoId",resultData.getPlmProductExceptionInfoId());
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productExceptionDetailList"))&&queryParamJSON.getJSONArray("productExceptionDetailList").size()!=0) {
			plmProductExceptionDetailServer.savePlmProductExceptionDetailInfo(queryParamJSON);
		}
		return resultData;
	}






}
