package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.dao.TtaDeptFeeHeaderDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaDeptFeeLineEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.proposal.model.entities.TtaDeptFeeHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.proposal.model.inter.ITtaDeptFeeHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component("ttaDeptFeeHeaderServer")
public class TtaDeptFeeHeaderServer extends BaseCommonServer<TtaDeptFeeHeaderEntity_HI> implements ITtaDeptFeeHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptFeeHeaderServer.class);

	@Autowired
	private ViewObject<TtaDeptFeeHeaderEntity_HI> ttaDeptFeeHeaderDAO_HI;




	@Autowired
	private ViewObject<TtaDeptFeeLineEntity_HI> ttaDeptFeeLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaDeptFeeHeaderEntity_HI_RO> ttaDeptFeeHeaderDAO_HI_RO;

	@Autowired
	private ViewObject<TtaProposalHeaderEntity_HI> ttaProposalHeaderDAO_HI;

	@Autowired
	private TtaDeptFeeHeaderDAO_HI ttaDeptFeeHeaderDAO;

	public TtaDeptFeeHeaderServer() {
		super();
	}


	@Override
	public Pagination<TtaDeptFeeHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaDeptFeeHeaderEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.is_Trans_Dept", "isTransDept", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.accord_Type", "accordType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.is_Conf", "isConf", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.dept_FeeId desc", false);
		Pagination<TtaDeptFeeHeaderEntity_HI_RO> findList = ttaDeptFeeHeaderDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaDeptFeeHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {


		Integer  proposalId  = paramsJSON.getInteger("proposalId");
		TtaProposalHeaderEntity_HI ins =   ttaProposalHeaderDAO_HI.getById(proposalId);
		ins.setDeptCode1(paramsJSON.getString("deptCode1"));
		ins.setDeptCode2(paramsJSON.getString("deptCode2"));
		ins.setDeptCode3(paramsJSON.getString("deptCode3"));
		ins.setDeptCode4(paramsJSON.getString("deptCode4"));
		ins.setDeptCode5(paramsJSON.getString("deptCode5"));

		ins.setDeptDesc1(paramsJSON.getString("deptDesc1"));
		ins.setDeptDesc2(paramsJSON.getString("deptDesc2"));
		ins.setDeptDesc3(paramsJSON.getString("deptDesc3"));
		ins.setDeptDesc4(paramsJSON.getString("deptDesc4"));
		ins.setDeptDesc5(paramsJSON.getString("deptDesc5"));
		ins.setIsTransdepart(paramsJSON.getString("isTransdepart"));

		ttaProposalHeaderDAO_HI.saveOrUpdate(ins);

		JSONArray paramsArray = paramsJSON.getJSONArray("lineA");
		for(int i=0;i<paramsArray.size();i++){
			JSONObject jsonObject = paramsArray.getJSONObject(i);

			//SaafToolUtils.validateJsonParms(jsonObject,"unit1","standardValue1");
			TtaDeptFeeLineEntity_HI instance2 = SaafToolUtils.setEntity(TtaDeptFeeLineEntity_HI.class, jsonObject, ttaDeptFeeLineDAO_HI, userId);
			ttaDeptFeeLineDAO_HI.saveOrUpdate(instance2);
		}

		paramsArray = paramsJSON.getJSONArray("lineB");
		for(int i=0;i<paramsArray.size();i++){
			JSONObject jsonObject = paramsArray.getJSONObject(i);

			//SaafToolUtils.validateJsonParms(jsonObject,"unit1","standardValue1");
			TtaDeptFeeLineEntity_HI instance2 = SaafToolUtils.setEntity(TtaDeptFeeLineEntity_HI.class, jsonObject, ttaDeptFeeLineDAO_HI, userId);
			ttaDeptFeeLineDAO_HI.saveOrUpdate(instance2);
		}

		return null;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaDeptFeeHeaderEntity_HI instance = ttaDeptFeeHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaDeptFeeHeaderDAO_HI.delete(instance);
	}


	@Override
	public TtaDeptFeeHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaDeptFeeHeaderEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.accord_Type", "accordType", sql, paramsMap, "=");
		return (TtaDeptFeeHeaderEntity_HI_RO)ttaDeptFeeHeaderDAO_HI_RO.get(sql,paramsMap);
	}


	@Override
	public TtaDeptFeeHeaderEntity_HI updateconfirm(JSONObject paramsJSON, int userId) throws Exception {
		//TtaBrandplnHEntity_HI instance = SaafToolUtils.setEntity(TtaBrandplnHEntity_HI.class, paramsJSON, ttaBrandplnHDAO_HI, userId);
		Integer  proposalId  = paramsJSON.getInteger("proposalId");
		TtaProposalHeaderEntity_HI instance =   ttaProposalHeaderDAO_HI.getById(proposalId);
		if(instance.getStatus().equals("B")||instance.getStatus().equals("C")){
			throw new IllegalArgumentException("Proposal单据状态为审批通过、待审批，不允许编辑单据");
		}
		String isDepartConfirm =SaafToolUtils.isNullOrEmpty(instance.getIsDepartConfirm())?"N":instance.getIsDepartConfirm() ;
		if("Y".equals(isDepartConfirm)){
			throw new IllegalArgumentException("Proposal单据部门协定页签已经为确认状态，不允许重复确认单据,请刷新页面重试");
		}
		instance.setIsDepartConfirm("Y");
		ttaProposalHeaderDAO_HI.saveOrUpdate(instance);

		return null;
	}

	@Override
	public TtaDeptFeeHeaderEntity_HI updatecancelConfirm(JSONObject paramsJSON, int userId) throws Exception {
		Integer  proposalId  = paramsJSON.getInteger("proposalId");
		TtaProposalHeaderEntity_HI instance =   ttaProposalHeaderDAO_HI.getById(proposalId);
		if(instance.getStatus().equals("B")||instance.getStatus().equals("C")){
			throw new IllegalArgumentException("Proposal单据状态为审批通过、待审批，不允许编辑单据");
		}
		String isDepartConfirm =SaafToolUtils.isNullOrEmpty(instance.getIsDepartConfirm())?"N":instance.getIsDepartConfirm() ;
		if("N".equals(isDepartConfirm)){
			throw new IllegalArgumentException("Proposal单据部门协定页签已经为取消状态，不允许重复取消单据,请刷新页面重试");
		}
		instance.setIsDepartConfirm("N");
		ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
		return null;
	}

	@Override
	public Map<String, Object> callCount(JSONObject paramsJSON, int userId) throws Exception {

		Integer proposalId   = paramsJSON.getInteger("proposalId");
		String accordType = paramsJSON.getString("accordType");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",proposalId);
			paramsMap.put("userId",userId);
			paramsMap.put("accordType",accordType);
			resultMap = ttaDeptFeeHeaderDAO.callDeptFeeHCount(paramsMap);
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
}
