package com.sie.watsons.base.pon.project.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationApprovalEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectInfoEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProjectInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonProjectInfoServer")
public class EquPonProjectInfoServer extends BaseCommonServer<EquPonProjectInfoEntity_HI> implements IEquPonProjectInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectInfoServer.class);

	@Autowired
	private ViewObject<EquPonProjectInfoEntity_HI> equPonProjectInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPonProjectInfoEntity_HI_RO> equPonProjectInfoEntity_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private EquPonProductSpecsServer equPonProductSpecsServer;

	@Autowired
	private EquPonProjectAttachmentServer equPonProjectAttachmentServer;

	@Autowired
	private EquPonScoringTeamServer equPonScoringTeamServer;

	@Autowired
	private EquPonSupplierInvitationServer equPonSupplierInvitationServer;

	@Autowired
	private ViewObject<EquPonQuotationInformationEntity_HI> equPonQuotationInformationDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInfoEntity_HI> equPonQuotationInfoDAO_HI;

	@Autowired
	private EquPonWitnessTeamServer equPonWitnessTeamServer;
	@Autowired
	private EquPonProjectChangeCauseServer equPonProjectChangeCauseServer;

	@Autowired
	private ViewObject<EquPonScoringInfoEntity_HI> equPonScoringInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationApprovalEntity_HI> equPonQuotationApprovalDAO_HI;

	public EquPonProjectInfoServer() {
		super();
	}

	/**
	 * 报价管理-立项单据查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findProjectInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Integer userId = null;
		String terminateFlag = null;
		StringBuffer sql = null;
		if(queryParamJSON.containsKey("userId")){
			userId = queryParamJSON.getInteger("userId");
		}
		if(queryParamJSON.containsKey("isTerminate")){
			terminateFlag = queryParamJSON.getString("isTerminate");
		}

		if(null != terminateFlag && "Y".equals(terminateFlag)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_FOR_TERMINATE);
		}else{
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL);
		}
		Map<String, Object> map = new HashMap<>();
		queryParamJSON.remove("userFullName");
		queryParamJSON.remove("userId");
		queryParamJSON.put("deptCode","0E");
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		if(null != userId){
			sql.append(" and t.created_by = " + userId);
		}
		sql.append(" order by t.creation_date desc");
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-根据立项id查询立项单据
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findProjectInfoById(JSONObject queryParamJSON) {

		StringBuffer sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_BY_ID);
		Map<String, Object> map = new HashMap<>();
		queryParamJSON.remove("userFullName");
		queryParamJSON.remove("userId");
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.creation_date desc");
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql, map, 1, 10);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询立项见证人员
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findProjectWitnessApproval(JSONObject queryParamJSON) {
		StringBuffer sql = null;
//		Map<String, Object> map = new HashMap<>();
		String deptment = queryParamJSON.getString("deptment");
		Integer projectId = queryParamJSON.getInteger("projectId");
		if("QA".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_PROJECT_QA);
		}else if("IA".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_PROJECT_IA);
		}else if("SECURITY".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_PROJECT_SECURITY);
		}
		sql.append(" and p.project_id = " + projectId);
//		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql);
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql,1,10);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询立项QA评分人员
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findProjectScoringApproval(JSONObject queryParamJSON) {
		StringBuffer sql = null;
		Integer projectId = queryParamJSON.getInteger("projectId");
		String userType = queryParamJSON.getString("userType");
		sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_PROJECT_SCORING_QA);
		sql.append(" and t.project_id = " + projectId);
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql,1,10);

		if(null != pagination.getData().get(0).getUserName()){
			JSONObject paramsJson = new JSONObject();
			paramsJson.put("userName", pagination.getData().get(0).getUserName());
			JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
			if (resultJson.get("userId") != null) {
				if(userType != null){
					JSONObject paramsJson2 = new JSONObject();
					paramsJson2.put("userId", resultJson.get("userId"));
					paramsJson2.put("userType", userType);
					JSONObject reqParams = new JSONObject();
					reqParams.put("params", paramsJson2);
					JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/basePersonService/findProccessStartUser", reqParams);
					if (resultJson2.getJSONArray("data").size() > 0) {
						JSONObject obj = resultJson2.getJSONArray("data").getJSONObject(0);
						pagination.getData().get(0).setUserId(obj.getInteger("userId"));
						pagination.getData().get(0).setUserName(obj.getString("userName"));
					}else{
						pagination.getData().get(0).setUserId(null);
						pagination.getData().get(0).setUserName(null);
					}
				}else{
					pagination.getData().get(0).setUserId(resultJson.getInteger("userId"));
				}
			}
		}

		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询报价结果审批见证人员
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findWitnessApproval(JSONObject queryParamJSON) {
		StringBuffer sql = null;
		Map<String, Object> map = new HashMap<>();
		String deptment = queryParamJSON.getString("deptment");
		Integer approvalId = queryParamJSON.getInteger("approvalId");
		queryParamJSON.remove("approvalId");
		if("QA".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_QA);
		}else if("IA".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_IA);
		}else if("SECURITY".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_SECURITY);
		}
		sql.append(" and p.approval_id = " + approvalId);
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql, map,1,10);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询评分审批见证人员
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findQuoInformationWitnessApproval(JSONObject queryParamJSON) {
		StringBuffer sql = null;
		Map<String, Object> map = new HashMap<>();
		String deptment = queryParamJSON.getString("deptment");
		Integer informationId = queryParamJSON.getInteger("informationId");
		if("QA".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_QUOTATION_INFORMATION_QA);
		}else if("IA".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_QUOTATION_INFORMATION_IA);
		}else if("SECURITY".equals(deptment)){
			sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_QUOTATION_INFORMATION_SECURITY);
		}
		sql.append(" and p.information_id = " + informationId);
		queryParamJSON.remove("informationId");
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql, map,1,10);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询评分审批默认见证人员
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findQuoInformationDefaultWitnessApproval(JSONObject queryParamJSON) {
		StringBuffer sql = null;
		Map<String, Object> map = new HashMap<>();
		String deptment = queryParamJSON.getString("deptment");
		Integer projectId = queryParamJSON.getInteger("projectId");
		String witnessType = queryParamJSON.getString("Default");
		String userType = queryParamJSON.getString("userType");
		if("Default".equals(witnessType)){
			if("QA".equals(deptment)){
				sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_DEFAULT_WITNESS_QA);
			}else if("IA".equals(deptment)){
				sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_DEFAULT_WITNESS_IA);
			}else if("SECURITY".equals(deptment)){
				sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_DEFAULT_WITNESS_SECURITY);
			}
		}else if("Appoint".equals(witnessType)){
			if("QA".equals(deptment)){
				sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_WITNESS_QA);
			}else if("IA".equals(deptment)){
				sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_WITNESS_IA);
			}else if("SECURITY".equals(deptment)){
				sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SQL_WITNESS_SECURITY);
			}
		}
		sql.append(" and t.project_id = " + projectId);
		queryParamJSON.remove("projectId");
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql, map,1,10);
        if(null != pagination.getData().get(0).getUserName()){
			JSONObject paramsJson = new JSONObject();
			paramsJson.put("userName", pagination.getData().get(0).getUserName());
			JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
			if (resultJson.get("userId") != null) {

				if(userType != null){
					JSONObject paramsJson2 = new JSONObject();
					paramsJson2.put("userId", resultJson.get("userId"));
					paramsJson2.put("userType", userType);
					JSONObject reqParams = new JSONObject();
					reqParams.put("params", paramsJson2);
					JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/basePersonService/findProccessStartUser", reqParams);
					if (resultJson2.getJSONArray("data").size() > 0) {
						JSONObject obj = resultJson2.getJSONArray("data").getJSONObject(0);
						pagination.getData().get(0).setUserId(obj.getInteger("userId"));
						pagination.getData().get(0).setUserName(obj.getString("userName"));
					}else{
						pagination.getData().get(0).setUserId(null);
						pagination.getData().get(0).setUserName(null);
					}
				}else{
					pagination.getData().get(0).setUserId(resultJson.getInteger("userId"));
				}

			}
		}
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public void operateValidate(String operator,Integer projectId,Integer userId) throws Exception{
//		if(null != projectId){
//			JSONObject queryObj = new JSONObject();
//			queryObj.put("projectId",projectId);
//			JSONObject projectObj = this.findProjectInfo(queryObj,1,999);
//			JSONArray projectArr = projectObj.getJSONArray("data");
//			JSONObject projectJson = projectArr.getJSONObject(0);
//			String projectStatus = projectJson.getString("projectStatus");
//			String canChangeFlag2 = projectJson.getString("canChangeFlag2");
//			Date sendQuotationInvitationDate = projectJson.getDate("sendQuotationInvitationDate");
//			Integer createdBy = projectJson.getInteger("createdBy");
//			//保存操作，状态校验
//			if(userId.intValue() != createdBy.intValue()){
//				throw new Exception("非法操作,系统拒绝响应!");
//			}
//			if("SAVE".equals(operator) || "SUBMIT".equals(operator)){
//				//owner进行保存/提交操作,单据状态必须是拟定(10)/驳回(40)
//				if(!"10".equals(projectStatus) && !"40".equals(projectStatus)){
//					throw new Exception("非法操作,系统拒绝响应!");
//				}
//			}
//			if("TERMINATE".equals(operator)){
//				//owner进行保存/提交操作,单据状态必须是拟定(10)/驳回(40)
//				if(!"40".equals(projectStatus)){
//					throw new Exception("非法操作,系统拒绝响应!");
//				}
//			}
//			if("CHANGE".equals(operator)){
//				//owner进行变更操作,单据状态必须是已批准(30)
//				if(!"30".equals(projectStatus) || !"Y".equals(canChangeFlag2)){
//					throw new Exception("非法操作,系统拒绝响应!");
//				}
//			}
//			if("QUOTATION".equals(operator)){
//				//owner发送报价邀请，单据状态必须为已批准
//				if(!"30".equals(projectStatus) || null != sendQuotationInvitationDate){
//					throw new Exception("非法操作,系统拒绝响应!");
//				}
//			}
//		}
	}

	/**
	 * 报价管理-立项单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonProjectInfoEntity_HI saveProjectInfo(JSONObject params) throws Exception {
		EquPonProjectInfoEntity_HI projectEntity = null;
		JSONObject projectInfo = getParamsJSONObject(params,params.getJSONObject("projectInfo"));
		JSONArray projectFileInfoArray = params.getJSONArray("projectFileInfo") == null ? new JSONArray() : params.getJSONArray("projectFileInfo");
		JSONArray productSpecsInfoArray = params.getJSONArray("productSpecsInfo") == null ? new JSONArray() : params.getJSONArray("productSpecsInfo");
		JSONArray supplierInvitationInfoArray = params.getJSONArray("supplierInvitationInfo") == null ? new JSONArray() : params.getJSONArray("supplierInvitationInfo");
		JSONArray nonPriceFileInfoArray = params.getJSONArray("nonPriceFileInfo") == null ? new JSONArray() : params.getJSONArray("nonPriceFileInfo");
		JSONArray nonPriceSelectFileInfoArray = params.getJSONArray("nonPriceSelectFileInfo") == null ? new JSONArray() : params.getJSONArray("nonPriceSelectFileInfo");
		JSONArray priceFileInfoArray = params.getJSONArray("priceFileInfo") == null ? new JSONArray() : params.getJSONArray("priceFileInfo");
		JSONArray scoringTeamInfoArray = params.getJSONArray("scoringTeamInfo") == null ? new JSONArray() : params.getJSONArray("scoringTeamInfo");
		JSONArray witnessTeamInfoArray = params.getJSONArray("witnessTeamInfo") == null ? new JSONArray() : params.getJSONArray("witnessTeamInfo");
		JSONArray changeCauseDataArray = params.getJSONArray("changeCauseDataTable") == null ? new JSONArray() : params.getJSONArray("changeCauseDataTable");

		//校验用户操作权限
		Integer userId = params.getInteger("varUserId");
		Integer projectId = projectInfo.getInteger("projectId");
		operateValidate("SAVE",projectId,userId);

		//保存立项单据
		if(projectInfo.containsKey("projectId") || projectInfo.containsKey("projectNumber")){
			//修改保存
			projectEntity = this.saveOrUpdate(projectInfo);

			//失效历史版本上的变更立项资料按钮
//			List<EquPonProjectInfoEntity_HI> parentEntityList = equPonProjectInfoDAO_HI.findByProperty("sourceProjectNumber",projectEntity.getSourceProjectNumber());
//			if(parentEntityList.size() > 0){
//				EquPonProjectInfoEntity_HI parentEntity = parentEntityList.get(0);
//				parentEntity.setCanChangeFlag("N");
//				parentEntity.setOperatorUserId(params.getInteger("varUserId"));
//				equPonProjectInfoDAO_HI.update(parentEntity);
//			}
		}else{
			//新增保存
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String currentDate = format.format(new Date());
			String prefix = "LX-" + currentDate;
			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
			String projectNumber = prefix + "-" + sequenceId;
			projectInfo.put("projectNumber", projectNumber);
			projectInfo.put("sourceProjectNumber",projectNumber);
			projectInfo.put("projectStatus","10");
			projectInfo.put("projectVersion","01");
			projectInfo.put("isChangeProject","N");
			projectInfo.put("canChangeFlag","Y");
			projectEntity = this.saveOrUpdate(projectInfo);
		}

		//保存立项资料信息
		for(int i = 0; i < projectFileInfoArray.size(); i++){
			JSONObject projectFileInfo = getParamsJSONObject(params,projectFileInfoArray.getJSONObject(i));
			Object attachmentId = projectFileInfo.get("attachmentId");
			if(projectFileInfo.containsKey("attachmentId") && !"".equals(attachmentId)){
				//修改保存
				equPonProjectAttachmentServer.saveOrUpdate(projectFileInfo);
			}else{
				//新增保存
				projectFileInfo.remove("attachmentId");
				projectFileInfo.put("projectId",projectEntity.getProjectId());
				equPonProjectAttachmentServer.saveOrUpdate(projectFileInfo);
			}
		}

		//保存产品规格信息
		for(int i = 0; i < productSpecsInfoArray.size(); i++){
			JSONObject productSpecsInfo = getParamsJSONObject(params,productSpecsInfoArray.getJSONObject(i));
			Object specsId = productSpecsInfo.get("specsId");
			if(productSpecsInfo.containsKey("specsId") && !"".equals(specsId)){
				//修改保存
				equPonProductSpecsServer.saveOrUpdate(productSpecsInfo);
			}else{
				//新增保存
				productSpecsInfo.put("projectId",projectEntity.getProjectId());
				equPonProductSpecsServer.saveOrUpdate(productSpecsInfo);
			}
		}

		//保存邀请供应商列表信息
		for(int i = 0; i < supplierInvitationInfoArray.size(); i++){
			JSONObject supplierInvitationInfo = getParamsJSONObject(params,supplierInvitationInfoArray.getJSONObject(i));
			Object invitationId = supplierInvitationInfo.get("invitationId");
			String quotationFlag = supplierInvitationInfo.getString("quotationFlag");
			if(supplierInvitationInfo.containsKey("invitationId") && !"".equals(invitationId)){
				//修改保存
				equPonSupplierInvitationServer.saveOrUpdate(supplierInvitationInfo);
			}else{
				//新增保存
				supplierInvitationInfo.put("projectId",projectEntity.getProjectId());
				if(null == quotationFlag || "".equals(quotationFlag)){
					supplierInvitationInfo.put("quotationFlag","N");
				}
				equPonSupplierInvitationServer.saveOrUpdate(supplierInvitationInfo);
			}
		}

		//保存非价格文件信息
		for(int i = 0; i < nonPriceFileInfoArray.size(); i++){
			JSONObject nonPriceFileInfo = getParamsJSONObject(params,nonPriceFileInfoArray.getJSONObject(i));
			Object attachmentId = nonPriceFileInfo.get("attachmentId");
			if(nonPriceFileInfo.containsKey("attachmentId") && !"".equals(attachmentId)){
				//修改保存
				equPonProjectAttachmentServer.saveOrUpdate(nonPriceFileInfo);
			}else{
				//新增保存
				nonPriceFileInfo.put("projectId",projectEntity.getProjectId());
				equPonProjectAttachmentServer.saveOrUpdate(nonPriceFileInfo);
			}
		}

		//保存非价格(选择)文件信息
		for(int i = 0; i < nonPriceSelectFileInfoArray.size(); i++){
			JSONObject nonPriceSelectFileInfo = getParamsJSONObject(params,nonPriceSelectFileInfoArray.getJSONObject(i));
			Object attachmentId = nonPriceSelectFileInfo.get("attachmentId");
			if(nonPriceSelectFileInfo.containsKey("attachmentId") && !"".equals(attachmentId)){
				//修改保存
				equPonProjectAttachmentServer.saveOrUpdate(nonPriceSelectFileInfo);
			}else{
				//新增保存
				nonPriceSelectFileInfo.put("projectId",projectEntity.getProjectId());
				equPonProjectAttachmentServer.saveOrUpdate(nonPriceSelectFileInfo);
			}
		}

		//保存非价格文件信息
		for(int i = 0; i < priceFileInfoArray.size(); i++){
			JSONObject priceFileInfo = getParamsJSONObject(params,priceFileInfoArray.getJSONObject(i));
			Object attachmentId = priceFileInfo.get("attachmentId");
			if(priceFileInfo.containsKey("attachmentId") && !"".equals(attachmentId)){
				//修改保存
				equPonProjectAttachmentServer.saveOrUpdate(priceFileInfo);
			}else{
				//新增保存
				priceFileInfo.put("projectId",projectEntity.getProjectId());
				equPonProjectAttachmentServer.saveOrUpdate(priceFileInfo);
			}
		}

		//保存评分小组信息
		for(int i = 0; i < scoringTeamInfoArray.size(); i++){
			JSONObject scoringTeamInfo = getParamsJSONObject(params,scoringTeamInfoArray.getJSONObject(i));
			Object teamId = scoringTeamInfo.get("teamId");
			if(scoringTeamInfo.containsKey("teamId") && !"".equals(teamId)){
				//修改保存
				equPonScoringTeamServer.saveOrUpdate(scoringTeamInfo);
			}else{
				//新增保存
				scoringTeamInfo.put("projectId",projectEntity.getProjectId());
				equPonScoringTeamServer.saveOrUpdate(scoringTeamInfo);
			}
		}

		//保存见证小组信息
		for(int i = 0; i < witnessTeamInfoArray.size(); i++){
			JSONObject witnessTeamInfo = getParamsJSONObject(params,witnessTeamInfoArray.getJSONObject(i));
			Object teamId = witnessTeamInfo.get("teamId");
			if(witnessTeamInfo.containsKey("teamId") && !"".equals(teamId)){
				//修改保存
				equPonWitnessTeamServer.saveOrUpdate(witnessTeamInfo);
			}else{
				//新增保存
				witnessTeamInfo.put("projectId",projectEntity.getProjectId());
				equPonWitnessTeamServer.saveOrUpdate(witnessTeamInfo);
			}
		}

		for(int i = 0; i < changeCauseDataArray.size(); i++){
			JSONObject changeCause = getParamsJSONObject(params,changeCauseDataArray.getJSONObject(i));
			equPonProjectChangeCauseServer.saveOrUpdate(changeCause);
		}

		//立项变更单据首次保存成功后，判断采购金额是否小于采购金额阈值
		//如果是，则修改上一版本立项及其相关单据(报价资料开启/评分/报价结果审批)的状态为"暂停"
		purchaseAmountLtEvent(projectInfo);

		return projectEntity;
	}

	/**
	 * 条件：1.立项版本大于01
	 * 		2.首次保存成功
	 * 		3.采购金额小于采购金额阈值
	 *符合以上条件的立项需要修改上一版本立项及其相关单据(报价资料开启/评分/报价结果审批)的状态为"暂停"
	 */
	public void purchaseAmountLtEvent(JSONObject params) throws Exception {
		Integer projectId = params.getInteger("projectId"); //立项id
		String projectVersion = params.getString("projectVersion"); //立项版本
//		BigDecimal projectPurchaseAmount = params.getBigDecimal("projectPurchaseAmount"); //立项采购金额
//		BigDecimal purchaseAmountThreshold = params.getBigDecimal("purchaseAmountThreshold"); //采购金额阈值

		if(projectId != null){
			return;
		}

		if("01".equals(projectVersion)){
			return;
		}

		String parentProjectNumber = params.getString("parentProjectNumber");

		List<EquPonProjectInfoEntity_HI> projectList = equPonProjectInfoDAO_HI.findByProperty("projectNumber",parentProjectNumber);

		if(projectList.size() > 0){
			EquPonProjectInfoEntity_HI projectEntity = projectList.get(0);
			int parentProjectId = projectEntity.getProjectId().intValue();

			BigDecimal projectPurchaseAmount = projectEntity.getProjectPurchaseAmount(); //立项采购金额
			BigDecimal purchaseAmountThreshold = projectEntity.getPurchaseAmountThreshold(); //采购金额阈值

			if(projectPurchaseAmount.doubleValue() > purchaseAmountThreshold.doubleValue()){
				return;
			}

			//查找当前立项版本对应的报价单
			List<EquPonQuotationInfoEntity_HI> quotationInfoArray = equPonQuotationInfoDAO_HI.findByProperty("projectId",parentProjectId);
			for(int j = 0; j < quotationInfoArray.size(); j++){
				EquPonQuotationInfoEntity_HI quotationInfoEntity = quotationInfoArray.get(j);
				//修改该报价单状态为暂停
				quotationInfoEntity.setDocStatus("SUSPEND");
				quotationInfoEntity.setOperatorUserId(quotationInfoEntity.getCreatedBy());
				equPonQuotationInfoDAO_HI.save(quotationInfoEntity);
			}

			//查找当前立项版本对应的资料开启单据
			List<EquPonQuotationInformationEntity_HI> quotationInformationArray = equPonQuotationInformationDAO_HI.findByProperty("projectId",parentProjectId);
			for(int j = 0; j < quotationInformationArray.size(); j++){
				EquPonQuotationInformationEntity_HI quotationInformationEntity = quotationInformationArray.get(j);
				//修改该资料开启单据状态为暂停
				quotationInformationEntity.setInformationStatus("70");
				quotationInformationEntity.setOperatorUserId(quotationInformationEntity.getCreatedBy());
				equPonQuotationInformationDAO_HI.save(quotationInformationEntity);
			}

			//查找当前立项版本对应的评分单据
			List<EquPonScoringInfoEntity_HI> scoringArray = equPonScoringInfoDAO_HI.findByProperty("projectId",parentProjectId);
			for(int j = 0; j < scoringArray.size(); j++){
				EquPonScoringInfoEntity_HI scoringInfoEntity = scoringArray.get(j);
				//当前评分单据状态为审批中/驳回，终止审批流程
				if("20".equals(scoringInfoEntity.getScoringStatus()) || "40".equals(scoringInfoEntity.getScoringStatus())){
					stopProcess(scoringInfoEntity.getScoringNumber(),"立项终止，流程随之终止!");
				}
				//修改该评分单据状态为暂停
				scoringInfoEntity.setScoringStatus("70");
				scoringInfoEntity.setOperatorUserId(scoringInfoEntity.getCreatedBy());
				equPonScoringInfoDAO_HI.save(scoringInfoEntity);
			}

			//查找当前立项版本对应的报价结果审批单据
			List<EquPonQuotationApprovalEntity_HI> quotationApprovalArray = equPonQuotationApprovalDAO_HI.findByProperty("projectId",parentProjectId);
			for(int j = 0; j < quotationApprovalArray.size(); j++){
				EquPonQuotationApprovalEntity_HI quotationApprovalEntity = quotationApprovalArray.get(j);
				//当前报价结果审批单据状态为审批中/驳回，终止审批流程
				if("20".equals(quotationApprovalEntity.getApprovalStatus()) || "40".equals(quotationApprovalEntity.getApprovalStatus())){
					stopProcess(quotationApprovalEntity.getApprovalNumber(),"立项终止，流程随之终止!");
				}
				//修改该报价结果审批单据状态为暂停
				quotationApprovalEntity.setApprovalStatus("70");
				quotationApprovalEntity.setOperatorUserId(quotationApprovalEntity.getCreatedBy());
				equPonQuotationApprovalDAO_HI.save(quotationApprovalEntity);
			}

			if("20".equals(projectEntity.getProjectStatus()) || "40".equals(projectEntity.getProjectStatus())){
				//当该立项版本的状态为审批中/驳回，终止审批流程
				stopProcess(parentProjectNumber,"立项终止，流程随之终止!");
			}
			//修改该立项版本的状态为暂停
			projectEntity.setProjectStatus("60");
			projectEntity.setOperatorUserId(projectEntity.getCreatedBy());
			equPonProjectInfoDAO_HI.save(projectEntity);
		}

	}

	/**
	 * 报价管理-立项单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonProjectInfoEntity_HI saveProjectInfos(JSONObject params) throws Exception {
		EquPonProjectInfoEntity_HI projectEntity = null;
		JSONObject projectInfo = getParamsJSONObject(params,params.getJSONObject("projectInfo"));

		//保存立项单据
		if(projectInfo.containsKey("projectId") || projectInfo.containsKey("projectNumber")){
			//修改保存
			projectEntity = this.saveOrUpdate(projectInfo);
		}else{
			//新增保存
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String currentDate = format.format(new Date());
			String prefix = "LX-" + currentDate;
			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
			String projectNumber = prefix + "-" + sequenceId;
			projectInfo.put("projectNumber", projectNumber);
			projectInfo.put("sourceProjectNumber",projectNumber);
			projectInfo.put("projectStatus","10");
			projectInfo.put("projectVersion","01");
			projectInfo.put("isChangeProject","N");
			projectInfo.put("canChangeFlag","Y");
			projectEntity = this.saveOrUpdate(projectInfo);
		}
		return projectEntity;
	}

	/**
	 * 报价管理-立项单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public Integer deleteProjectInfo(JSONObject params) throws Exception {
		String projectStatus = params.getString("projectStatus");
		String projectNumber = params.getString("projectNumber");
		this.deleteById(params.getInteger("id"));
		if("40".equals(projectStatus)){
			//查询流程信息，提取流程id
			JSONObject b = new JSONObject();
			b.put("code", projectNumber);
			JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
			//根据流程id，终止流程
			Integer listId = resultJSON.getInteger("listId");
			return listId;
		}
		return null;
	}

	/**
	 * 报价管理-立项单据终止
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void terminateProjectInfo(JSONObject params) throws Exception {
		EquPonProjectInfoEntity_HI entity = this.getById(params.getInteger("projectId"));
		entity.setTerminateFlag("Y");
		entity.setOperatorUserId(params.getInteger("varUserId"));
	}

	/**
	 * 查找导航菜单节点
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findPonNavigationMenuNodeList(JSONObject queryParamJSON) {
		Integer projectId = queryParamJSON.getInteger("projectId");
		StringBuffer sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_PON_NAVIGATION_MENU_SQL);
		boolean flag = false;
		Map<String, Object> map = new HashMap<>();
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql, map, 1, 999);

		List<EquPonProjectInfoEntity_HI_RO> resultList = pagination.getData();

		if(null == projectId){
			for(int i = 0; i < resultList.size(); i++){
				EquPonProjectInfoEntity_HI_RO entity = resultList.get(i);
				entity.setStatus("0");
			}
		}else{
			for(int i = 0; i < resultList.size(); i++){
				EquPonProjectInfoEntity_HI_RO entity = resultList.get(i);
				String nodeName = entity.getNodeName();
				String status = "";
				String imageName = "";
				int count = 0;
				if("LX".equals(nodeName)){
					//查询立项处理状态
					List<EquPonProjectInfoEntity_HI> projectList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
					if(projectList.size() == 0){
						status = "0"; //未处理
						imageName = "lx_unhandle.png";
					}else{
						for(int j = 0; j < projectList.size(); j++){
							if("30".equals(projectList.get(j).getProjectStatus())){
								count++;
							}
						}
						if(count > 0){
							status = "2"; //已完成
							imageName = "lx_complete.png";
						}else{
							status = "1"; //处理中
							imageName = "lx_handling.png";
						}
					}
				}else if("GYSYQ".equals(nodeName)){
					//查询供应商邀请处理状态
					List<EquPonProjectInfoEntity_HI> projectList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
					if(projectList.size() == 0){
						status = "0"; //未处理
						imageName = "gysyq_unhandle.png";
					}else{
						for(int j = 0; j < projectList.size(); j++){
							if(projectList.get(j).getSendQuotationInvitationDate() != null){
								count++;
							}
						}
						if(count > 0){
							status = "2"; //已完成
							imageName = "gysyq_complete.png";
						}else{
							status = "0"; //未处理
							imageName = "gysyq_unhandle.png";
						}
					}
				}else if("BJZLKQ".equals(nodeName)){
					//查询报价资料开启处理状态
					Map informationQueryMap = new HashMap();
					informationQueryMap.put("projectId",projectId);
					informationQueryMap.put("deptCode","0E");
					List<EquPonQuotationInformationEntity_HI> quotationInformationList = equPonQuotationInformationDAO_HI.findByProperty(informationQueryMap);
					if(quotationInformationList.size() == 0){
						status = "0"; //未处理
						imageName = "bjzlkq_unhandle.png";
					}else{
						for(int j = 0; j < quotationInformationList.size(); j++){
							if("30".equals(quotationInformationList.get(j).getInformationStatus())){
								count++;
							}
						}
						if(count > 0){
							status = "2"; //已完成
							imageName = "bjzlkq_complete.png";
						}else{
							status = "1"; //处理中
							imageName = "bjzlkq_handling.png";
						}
					}
				}else if("PF".equals(nodeName)){
					//查询评分处理状态
					List<EquPonScoringInfoEntity_HI> scoringList = equPonScoringInfoDAO_HI.findByProperty("projectId",projectId);
					if(scoringList.size() == 0){
						status = "0"; //未处理
						imageName = "pf_unhandle.png";
					}else{
						for(int j = 0; j < scoringList.size(); j++){
							if("60".equals(scoringList.get(j).getScoringStatus())){
								count++;
							}
						}
						if(count > 0){
							status = "2"; //已完成
							imageName = "pf_complete.png";
						}else{
							status = "1"; //处理中
							imageName = "pf_handling.png";
						}
					}
				}else if("BJJGSP".equals(nodeName)){
					//查询报价结果审批处理状态
					Map approvalQueryMap = new HashMap();
					approvalQueryMap.put("projectId",projectId);
					approvalQueryMap.put("deptCode","0E");
					List<EquPonQuotationApprovalEntity_HI> quotationApprovalList = equPonQuotationApprovalDAO_HI.findByProperty(approvalQueryMap);
					if(quotationApprovalList.size() == 0){
						status = "0"; //未处理
						imageName = "bjjgsp_unhandle.png";
					}else{
						for(int j = 0; j < quotationApprovalList.size(); j++){
							if("30".equals(quotationApprovalList.get(j).getApprovalStatus())){
								count++;
							}
						}
						if(count > 0){
							status = "2"; //已完成
							imageName = "bjjgsp_complete.png";
						}else{
							status = "1"; //处理中
							imageName = "bjjgsp_handling.png";
						}
					}
				}
				entity.setStatus(status);
				entity.setImageName(imageName);
			}
		}
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商报价管理查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findSupplierQuotationList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_SUPPLIER_QUOTATION_SQL);
		Map<String, Object> map = new HashMap<>();
		map.put("varUserId",queryParamJSON.getInteger("varUserId"));
		Pagination<EquPonProjectInfoEntity_HI_RO> pagination = equPonProjectInfoEntity_HI_RO.findPagination(sql, map, 1, 999);

		List<EquPonProjectInfoEntity_HI_RO> resultList = pagination.getData();

//		List<EquPonProjectInfoEntity_HI_RO> csrList = new ArrayList();

		for(int i = 0; i < resultList.size(); i++){
			EquPonProjectInfoEntity_HI_RO entity = resultList.get(i);
			String approvalNumber = entity.getApprovalNumber();
			String approvalStatus = entity.getApprovalStatus();
			Date approvalLastUpdateDate = entity.getApprovalLastUpdateDate();
			Integer approvalId = entity.getApprovalId();
			if(approvalNumber != null && !"".equals(approvalNumber)){
				entity.setCurrentHandleOrderStatus(approvalStatus);
				entity.setCurrentHandleOrderNumber(approvalNumber);
				entity.setCurrentLastUpdateDate(approvalLastUpdateDate);
				entity.setCurrentHandleOrderId(approvalId);
				entity.setCurrentNodeName("报价结果审批");
			}
		}

		for(int i = 0; i < resultList.size(); i++){
			EquPonProjectInfoEntity_HI_RO entity = resultList.get(i);
			if(null == entity.getCurrentHandleOrderNumber() || "".equals(entity.getCurrentHandleOrderNumber())){
				String scoringNumber = entity.getScoringNumber();
				String scoringStatus = entity.getScoringStatus();
				Date scoringLastUpdateDate = entity.getScoringLastUpdateDate();
				Integer scoringId = entity.getScoringId();
				if(scoringNumber != null && !"".equals(scoringNumber)){
					entity.setCurrentHandleOrderStatus(scoringStatus);
					entity.setCurrentHandleOrderNumber(scoringNumber);
					entity.setCurrentLastUpdateDate(scoringLastUpdateDate);
					entity.setCurrentHandleOrderId(scoringId);
					entity.setCurrentNodeName("评分");
				}
			}
		}

		for(int i = 0; i < resultList.size(); i++){
			EquPonProjectInfoEntity_HI_RO entity = resultList.get(i);
			if(null == entity.getCurrentHandleOrderNumber() || "".equals(entity.getCurrentHandleOrderNumber())){
				String informationNumber = entity.getInformationCode();
				String informationStatus = entity.getInformationStatus();
				Date informationLastUpdateDate = entity.getInformationLastUpdateDate();
				Integer informationId = entity.getInformationId();
				if(informationNumber != null && !"".equals(informationNumber)){
					entity.setCurrentHandleOrderStatus(informationStatus);
					entity.setCurrentHandleOrderNumber(informationNumber);
					entity.setCurrentLastUpdateDate(informationLastUpdateDate);
					entity.setCurrentHandleOrderId(informationId);
					entity.setCurrentNodeName("报价资料开启");
				}
			}
		}

		for(int i = 0; i < resultList.size(); i++){
			EquPonProjectInfoEntity_HI_RO entity = resultList.get(i);
			if(null == entity.getCurrentHandleOrderNumber() || "".equals(entity.getCurrentHandleOrderNumber())){
				String projectNumber = entity.getProjectNumber();
				String projectStatus = entity.getProjectStatus();
				Integer projectId = entity.getProjectId();
				Date sendQuotationInvitationDate = entity.getSendQuotationInvitationDate();
				if(sendQuotationInvitationDate != null && !"".equals(sendQuotationInvitationDate)){
					entity.setCurrentHandleOrderStatus(projectStatus);
					entity.setCurrentHandleOrderNumber(projectNumber);
					entity.setCurrentLastUpdateDate(sendQuotationInvitationDate);
					entity.setCurrentHandleOrderId(projectId);
					entity.setCurrentNodeName("邀请供应商报价");
				}
			}
		}

		for(int i = 0; i < resultList.size(); i++){
			EquPonProjectInfoEntity_HI_RO entity = resultList.get(i);
			if(null == entity.getCurrentHandleOrderNumber() || "".equals(entity.getCurrentHandleOrderNumber())){
				String projectNumber = entity.getProjectNumber();
				String projectStatus = entity.getProjectStatus();
				Date projectLastUpdateDate = entity.getProjectLastUpdateDate();
				Integer projectId = entity.getProjectId();
				if(projectNumber != null && !"".equals(projectNumber)){
					entity.setCurrentHandleOrderStatus(projectStatus);
					entity.setCurrentHandleOrderNumber(projectNumber);
					entity.setCurrentLastUpdateDate(projectLastUpdateDate);
					entity.setCurrentHandleOrderId(projectId);
					entity.setCurrentNodeName("项目立项");
				}
			}
		}
//		List<EquPonProjectInfoEntity_HI_RO> resultList
		Collections.sort(resultList, new Comparator<EquPonProjectInfoEntity_HI_RO>() {
			public int compare(EquPonProjectInfoEntity_HI_RO o1, EquPonProjectInfoEntity_HI_RO o2) {
				if (o1.getCurrentLastUpdateDate().getTime() > o2.getCurrentLastUpdateDate().getTime()) {
					return -1;
				}
				if (o1.getCurrentLastUpdateDate().getTime() == o2.getCurrentLastUpdateDate().getTime()) {
					return 0;
				}
				return 1;
			}
		});

		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 解析json参数
	 */
	public JSONObject getParamsJSONObject(JSONObject params,JSONObject targetJsonObject){
		if(params.containsKey("varUserId")){
			targetJsonObject.put("varUserId",params.get("varUserId"));
		}
		if(params.containsKey("username")){
			targetJsonObject.put("username",params.get("username"));
		}
		if(params.containsKey("varSystemCode")){
			targetJsonObject.put("varSystemCode",params.get("varSystemCode"));
		}
		if(params.containsKey("varUserName")){
			targetJsonObject.put("varUserName",params.get("varUserName"));
		}
		if(params.containsKey("varEmployeeNumber")){
			targetJsonObject.put("varEmployeeNumber",params.get("varEmployeeNumber"));
		}
		if(params.containsKey("varUserFullName")){
			targetJsonObject.put("varUserFullName",params.get("varUserFullName"));
		}
		if(params.containsKey("varOrgId")){
			targetJsonObject.put("varOrgId",params.get("varOrgId"));
		}
		if(params.containsKey("varOrgCode")){
			targetJsonObject.put("varOrgCode",params.get("varOrgCode"));
		}
		if(params.containsKey("varLeaderId")){
			targetJsonObject.put("varLeaderId",params.get("varLeaderId"));
		}
		if(params.containsKey("varIsadmin")){
			targetJsonObject.put("varIsadmin",params.get("varIsadmin"));
		}
		if(params.containsKey("varUserType")){
			targetJsonObject.put("varUserType",params.get("varUserType"));
		}
		if(params.containsKey("operationOrgIds")){
			targetJsonObject.put("operationOrgIds",params.get("operationOrgIds"));
		}
		if(params.containsKey("operationOrgId")){
			targetJsonObject.put("operationOrgId",params.get("operationOrgId"));
		}
		if(params.containsKey("operationRespId")){
			targetJsonObject.put("operationRespId",params.get("operationRespId"));
		}
		if(params.containsKey("operatorUserId")){
			targetJsonObject.put("operatorUserId",params.get("operatorUserId"));
		}
		return targetJsonObject;
	}

	/**
	 * 立项审批回调接口
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public EquPonProjectInfoEntity_HI projectApprovalCallback(JSONObject parseObject, int userId) throws Exception {
		Integer headerId = parseObject.getIntValue("id");//单据Id
		EquPonProjectInfoEntity_HI entityHeader = this.getById(headerId);
		String orderStatus = null;//状态
		switch (parseObject.getString("status")) {
			case "REFUSAL":
				if(!"50".equals(entityHeader.getProjectStatus())){
					orderStatus = "40";
				}else{
					orderStatus = "50";
				}
				break;
			case "ALLOW":
//				//查找报价单
//				List<EquPonQuotationInfoEntity_HI> infoEntityList =  equPonQuotationInfoDAO_HI.findByProperty("projectId",headerId);
//				//查询资料开启单据
//				List<EquPonQuotationInformationEntity_HI> informationEntityList = equPonQuotationInformationDAO_HI.findByProperty("projectId",headerId);
//
//
//				//如果单据终止状态terminateFlag为Y,报价资料开启单/报价单/立项单据状态全部调整为终止
//				if("Y".equals(entityHeader.getTerminateFlag())){
//					//1.修改报价资料开启单据状态为终止
//					if(infoEntityList.size() > 0){
//						EquPonQuotationInformationEntity_HI informationEntity = informationEntityList.get(0);
//						informationEntity.setInformationStatus("50");
//						informationEntity.setOperatorUserId(userId);
//					}
//					//2.修改立项单据状态为终止
//					entityHeader.setProjectStatus("50");
//					entityHeader.setOperatorUserId(userId);
//					//3.修改报价单状态为终止
//					for(int i = 0; i < infoEntityList.size(); i++){
//						EquPonQuotationInfoEntity_HI infoEntity = infoEntityList.get(i);
//						infoEntity.setDocStatus("BREAK");
//						infoEntity.setOperatorUserId(userId);
//					}
//				}
				//如果单据终止状态terminateFlag为Y,单据状态修改为终止,否则为已批准
//				if("Y".equals(entityHeader.getTerminateFlag())){
//					orderStatus = "50";
//				}else{
//					orderStatus = "30";
//				}
				orderStatus = "30";
				//立项及立项变更流程结束增加邮件到owner
				JSONObject obj = new JSONObject();
				obj.put("projectId",headerId);
				JSONObject result  =findProjectInfoById(obj);
				List<String> incomingParam = new ArrayList<>();
				List<String> efferentParam = new ArrayList<>();
				List<String> typeParam = new ArrayList<>();
				incomingParam.add("projectStatus");
				efferentParam.add("projectStatusMeaning");
				typeParam.add("EQU_PON_PROJECT_STATUS");
				JSONArray data = result.getJSONArray("data");
				data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
				if(data.size() > 0){
					JSONObject dataObj = data.getJSONObject(0);
					String email = dataObj.getString("email");
					System.out.println(email);
					String mailBody = CommonUtils.projectAllowMailContent(entityHeader.getProjectNumber());
					EmailUtil.sendInMail("立项审批完成", mailBody,email);
				}
				break;
			case "DRAFT":
				orderStatus = "10";
				break;
			case "APPROVAL":
				orderStatus = "20";
				break;
			case "CLOSED":
				orderStatus = "50";
				break;
		}

		//流程节点审批通过,邮件通知owner
		CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getProjectNumber());

		entityHeader.setProjectStatus(orderStatus);
		entityHeader.setOperatorUserId(userId);
		this.saveOrUpdate(entityHeader);
		return entityHeader;
	}

	/**
	 * 校验用户操作权限
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String validateProjectUserOperator(JSONObject params) throws Exception {
		Integer userId = params.getInteger("varUserId");
		Integer qualificationId = params.getInteger("projectId");
		String operator = params.getString("operator");
		operateValidate(operator,qualificationId,userId);
		return "S";
	}

	public void stopProcess(String billNo,String reason) throws Exception{
		JSONObject b = new JSONObject();
		b.put("code", billNo);
		JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
		//根据流程id，终止流程
		Integer listId = resultJSON.getInteger("listId");
		JSONObject reqParams = new JSONObject();
		JSONObject paramsObj = new JSONObject();
		paramsObj.put("listId",listId);
		paramsObj.put("reason", reason);
		reqParams.put("params", paramsObj);
		ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/bpmServer/bpmProcessService/stopProcessByListId", reqParams);
	}
}
