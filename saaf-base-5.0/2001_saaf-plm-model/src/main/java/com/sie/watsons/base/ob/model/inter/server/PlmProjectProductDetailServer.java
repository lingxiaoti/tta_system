package com.sie.watsons.base.ob.model.inter.server;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectProductDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmProjectProductDetail;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.yhg.base.utils.SToolUtils;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.ob.model.entities.PlmProjectProductDetailEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProjectProductDetailServer")
public class PlmProjectProductDetailServer extends BaseCommonServer<PlmProjectProductDetailEntity_HI> implements IPlmProjectProductDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectProductDetailServer.class);
	@Autowired
	private ViewObject<PlmProjectProductDetailEntity_HI> plmProjectProductDetailDAO_HI;
	@Autowired
	private BaseViewObject<PlmProjectProductDetailEntity_HI_RO> plmProjectProductDetailDAO_HI_RO;
	public PlmProjectProductDetailServer() {
		super();
	}

	@Override
	public Pagination<PlmProjectProductDetailEntity_HI_RO> findPlmProjectProductDetailInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmProjectProductDetailEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(PlmProjectProductDetailEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" order by prpd.CREATION_DATE ");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		Pagination<PlmProjectProductDetailEntity_HI_RO> pagination = plmProjectProductDetailDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmProjectProductDetailEntity_HI> savePlmProjectProductDetailInfo(JSONObject queryParamJSON) {
		List<PlmProjectProductDetailEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("productDetailList").toString(),PlmProjectProductDetailEntity_HI.class);
		dataList = this.changeStatusByCommand(dataList,queryParamJSON.getString("commandStatus"), queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("plmProjectId"));
		plmProjectProductDetailDAO_HI.saveOrUpdateAll(dataList);
		return dataList;
	}

	public List<PlmProjectProductDetailEntity_HI> changeStatusByCommand(List<PlmProjectProductDetailEntity_HI> dataList, String commandStatus, Integer userId, Integer plmProjectId){
		Set<String> productNameSet = new HashSet<>();
		//check productName non-repeatable
		for(PlmProjectProductDetailEntity_HI data: dataList) {
			if(productNameSet.contains(data.getProductName())){
				throw new IllegalArgumentException("产品明细行产品"+data.getProductName()+"重复");
			}
			productNameSet.add(data.getProductName());
			if(!SaafToolUtils.isNullOrEmpty(commandStatus)){
				if(commandStatus.equals("ABANDONED")&&(data.getProductBillStatus().equals("DEVELOPING")||data.getProductBillStatus().equals("TODO"))){
					data.setProductBillStatus(commandStatus);
					data.setProductStatus(commandStatus);
				}
				else if(commandStatus.equals("SUBMITTED")&&data.getProductBillStatus().equals("TODO")){
					data.setProductBillStatus("DEVELOPING");
					data.setProductStatus("DEVELOPING");
				}

			}
			data.setOperatorUserId(userId);
			if(plmProjectId!=null)
				data.setPlmProjectId(plmProjectId);
		}
		return dataList;
	}

	@Override
	public Integer deletePlmProjectProductDetailInfo(JSONObject queryParamJSON) {
		if(SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productDetailList"))){
			PlmProjectProductDetailEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmProjectProductDetailEntity_HI.class);
			plmProjectProductDetailDAO_HI.delete(entity);
			return 1;
		}
		List<PlmProjectProductDetailEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("productDetailList").toString(),PlmProjectProductDetailEntity_HI.class);
		plmProjectProductDetailDAO_HI.deleteAll(dataList);
		return dataList.size();
	}

}
