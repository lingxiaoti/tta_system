package com.sie.watsons.base.feedept.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.feedept.model.dao.TtaFeeDeptHDAO_HI;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptHEntity_HI_RO;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptHEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.feedept.model.inter.ITtaFeeDeptH;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component("ttaFeeDeptHServer")
public class TtaFeeDeptHServer extends BaseCommonServer<TtaFeeDeptHEntity_HI> implements ITtaFeeDeptH{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptHServer.class);

	@Autowired
	private ViewObject<TtaFeeDeptHEntity_HI> ttaFeeDeptHDAO_HI;

	@Autowired
	private TtaFeeDeptHDAO_HI ttaFeeDeptHDAO;

	@Autowired
	private BaseViewObject<TtaFeeDeptHEntity_HI_RO> ttaFeeDeptHDAO_HI_RO;

	public TtaFeeDeptHServer() {
		super();
	}

	@Override
	public Pagination<TtaFeeDeptHEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaFeeDeptHEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.feedept_Id", "feedeptId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.year_Code", "yearCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.accord_Type", "accordType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.is_Alert", "isAlert", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.version_Code", "versionCode", sql, paramsMap, "=");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.feedept_Id desc", false);
		Pagination<TtaFeeDeptHEntity_HI_RO> findList = ttaFeeDeptHDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaFeeDeptHEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {

	//	if(SaafToolUtils.isNullOrEmpty(instance.getFeedeptId())){
			String yearCode = paramsJSON.getString("yearCode");
			String accordType = paramsJSON.getString("accordType");
			Integer feedeptId = paramsJSON.getInteger("feedeptId");
		String deptCode = paramsJSON.getString("deptCode");
		Map<String,Object> map=new HashMap<>();
			map.put("yearCode",yearCode);
			map.put("accordType",accordType);
			map.put("isAlert","N");
			map.put("deptCode",deptCode);
			//map.put("status","C");
		String hql = "from TtaFeeDeptHEntity_HI t where t.yearCode =:yearCode and " +
				"t.accordType =:accordType  and t.isAlert =:isAlert and t.deptCode =:deptCode ";
		if(!SaafToolUtils.isNullOrEmpty(feedeptId)){
			hql = hql+" and t.feedeptId <> '"+String.valueOf(feedeptId)+"' ";
		}
		LOGGER.info("部门协定标准保存执行SQL:" + hql);
		List<TtaFeeDeptHEntity_HI> collectionList = ttaFeeDeptHDAO_HI.findList(hql, map);


			if (collectionList!=null&&collectionList.size()>0) {
				TtaFeeDeptHEntity_HI inst = collectionList.get(0);

						throw new IllegalArgumentException("本年度的部门协定收费标准费用类型已存在!请修改!");


			};
	//	}
		TtaFeeDeptHEntity_HI instance = SaafToolUtils.setEntity(TtaFeeDeptHEntity_HI.class, paramsJSON, ttaFeeDeptHDAO_HI, userId);


        ttaFeeDeptHDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaFeeDeptHEntity_HI instance = ttaFeeDeptHDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaFeeDeptHDAO_HI.delete(instance);
	}


	@Override
	public TtaFeeDeptHEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaFeeDeptHEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.feedept_Id", "feedeptId", sql, paramsMap, "=");
		return (TtaFeeDeptHEntity_HI_RO)ttaFeeDeptHDAO_HI_RO.get(sql,paramsMap);
	}


	@Override
	public Map<String, Object> callCopyOrder(JSONObject paramsJSON, int userId)  {
		Integer newPkId =0;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",paramsJSON.getInteger("feedeptId"));
			paramsMap.put("userId",userId);
			paramsMap.put("dealType","COPY");
			resultMap = ttaFeeDeptHDAO.callOrder(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");

			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
				//throw new Exception(xMsg);
			};
		} catch (Exception e) {
			e.printStackTrace();
		}


		return resultMap;
	}


	@Override
	public Map<String, Object> callChangeOrder(JSONObject paramsJSON, int userId)  {
		Integer newPkId =0;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",paramsJSON.getInteger("feedeptId"));
			paramsMap.put("userId",userId);
			paramsMap.put("dealType","CHANGE");
			resultMap = ttaFeeDeptHDAO.callOrder(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");

			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
				//throw new Exception(xMsg);
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}



	@Override
	public TtaFeeDeptHEntity_HI updateApprove(JSONObject paramsJSON, int userId) throws Exception {

		Integer feedeptId = paramsJSON.getInteger("feedeptId");
		StringBuffer stringBuffer = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		stringBuffer.append(TtaFeeDeptHEntity_HI_RO.TTA_DEPT_COUNT);
		TtaFeeDeptHEntity_HI instance = ttaFeeDeptHDAO_HI.getById(feedeptId);
		if (instance == null) {
			throw new IllegalArgumentException("ID:"+feedeptId+"feedeptId不存在!请修改!");
		}
		SaafToolUtils.parperParam(paramsJSON, "tfh.feedept_Id", "feedeptId", stringBuffer, paramsMap, "=");
		TtaFeeDeptHEntity_HI_RO ttaFeeDeptHEntity_hi_ro = ttaFeeDeptHDAO_HI_RO.get(stringBuffer, paramsMap);
		if(!SaafToolUtils.isNullOrEmpty(ttaFeeDeptHEntity_hi_ro)){
			int  nums =	ttaFeeDeptHEntity_hi_ro.getCounts().intValue();
			if(nums>0){
				//不校验小部门是否在某个部门下面的逻辑
				//throw new IllegalArgumentException("部门和单位行的部门存在不是上下级关系的,!请修改!");
			}
		}
		instance.setStatus("C");
		instance.setApproveDate(new Date());
		instance.setOperatorUserId(userId);
		//Y代表是变更单据
		if(null != instance.getSourceFeeId() && "Y".equals(instance.getIsAlert())){
			TtaFeeDeptHEntity_HI instanceOld = ttaFeeDeptHDAO_HI.getById(instance.getSourceFeeId());
			if(!SaafToolUtils.isNullOrEmpty(instanceOld)){
				instanceOld.setStatus("F");
				instanceOld.setOperatorUserId(userId);
				ttaFeeDeptHDAO_HI.saveOrUpdate(instanceOld);
			}

		}

		ttaFeeDeptHDAO_HI.saveOrUpdate(instance);
		return instance;
	}






}
