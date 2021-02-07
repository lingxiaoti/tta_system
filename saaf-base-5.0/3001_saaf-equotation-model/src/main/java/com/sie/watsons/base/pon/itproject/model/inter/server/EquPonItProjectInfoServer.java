package com.sie.watsons.base.pon.itproject.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationApprovalEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItProjectInfoEntity_HI_RO;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectInfoEntity_HI_RO;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItProjectInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonItProjectInfoServer")
public class EquPonItProjectInfoServer extends BaseCommonServer<EquPonItProjectInfoEntity_HI> implements IEquPonItProjectInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItProjectInfoServer.class);

	@Autowired
	private ViewObject<EquPonItProjectInfoEntity_HI> equPonItProjectInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItProjectInfoEntity_HI_RO> equPonItProjectInfoEntity_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private EquPonItQuotationContentServer equPonItQuotationContentServer;

	@Autowired
	private EquPonItInvitationRuleServer equPonItInvitationRuleServer;

	@Autowired
	private ViewObject<EquPonQuotationInformationEntity_HI> equPonQuotationInformationDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringInfoEntity_HI> equPonItScoringInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationApprovalEntity_HI> equPonQuotationApprovalDAO_HI;

	public EquPonItProjectInfoServer() {
		super();
	}

	/**
	 * IT报价管理-立项单据查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findItProjectInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
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
			sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL_FOR_TERMINATE);
		}else{
			sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL);
		}
		Map<String, Object> map = new HashMap<>();
		queryParamJSON.remove("userFullName");
		queryParamJSON.remove("userId");
//		queryParamJSON.put("deptCode","03");
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		if(null != userId){
			sql.append(" and t.created_by = " + userId);
		}
		sql.append(" and t.dept_code <> '0E'");
		sql.append(" order by t.creation_date desc");
		Pagination<EquPonItProjectInfoEntity_HI_RO> pagination = equPonItProjectInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
//		StringBuffer sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL);
//		Map<String, Object> map = new HashMap<>();
//		queryParamJSON.remove("userFullName");
//		queryParamJSON.remove("userId");
//		queryParamJSON.put("deptCode","03");
//		SaafToolUtils.parperHbmParam(EquPonItProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
//		sql.append(" order by t.creation_date desc");
//		Pagination<EquPonItProjectInfoEntity_HI_RO> pagination = equPonItProjectInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
//		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-立项单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonItProjectInfoEntity_HI saveItProjectInfo(JSONObject params) throws Exception {
		EquPonItProjectInfoEntity_HI projectEntity = null;
		JSONObject projectInfo = getParamsJSONObject(params,params.getJSONObject("projectInfo"));
		JSONArray quotationContentInfoArray = params.getJSONArray("quotationContentInfo") == null ? new JSONArray() : params.getJSONArray("quotationContentInfo");
		JSONArray invitationRuleInfoArray = params.getJSONArray("invitationRuleInfo") == null ? new JSONArray() : params.getJSONArray("invitationRuleInfo");


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

			if(null == projectInfo.getString("contactPhoneNumber") || "".equals(projectInfo.getString("contactPhoneNumber"))){
				JSONObject queryObj = new JSONObject();
				queryObj.put("createdBy",projectInfo.getInteger("varUserId"));
				JSONObject returnObj = ResultUtils.getUserInfoForCreatedBy(queryObj);
				String phoneNumber = returnObj.getString("phoneNumber");
				projectInfo.put("contactPhoneNumber",phoneNumber);
			}

			projectEntity = this.saveOrUpdate(projectInfo);
		}

		//保存报价内容信息
		for(int i = 0; i < quotationContentInfoArray.size(); i++){
			JSONObject quotationContentInfo = getParamsJSONObject(params,quotationContentInfoArray.getJSONObject(i));
			if(quotationContentInfo.containsKey("contentId")){
				//修改保存
				equPonItQuotationContentServer.saveOrUpdate(quotationContentInfo);
			}else{
				//新增保存
				quotationContentInfo.put("projectId",projectEntity.getProjectId());
				equPonItQuotationContentServer.saveOrUpdate(quotationContentInfo);
			}
		}

		//保存邀请/暂停邀请细则信息
		for(int i = 0; i < invitationRuleInfoArray.size(); i++){
			JSONObject invitationRuleInfo = getParamsJSONObject(params,invitationRuleInfoArray.getJSONObject(i));
			if(invitationRuleInfo.containsKey("ruleId")){
				//修改保存
				equPonItInvitationRuleServer.saveOrUpdate(invitationRuleInfo);
			}else{
				//新增保存
				invitationRuleInfo.put("projectId",projectEntity.getProjectId());
				equPonItInvitationRuleServer.saveOrUpdate(invitationRuleInfo);
			}
		}

		return projectEntity;
	}

	/**
	 * IT报价管理-立项单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public Integer deleteItProjectInfo(JSONObject params) throws Exception {
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

	//查询关联部门业务人员(审批流)
	public JSONObject findRelateDeptBussinessApproval(JSONObject queryParamJSON) {
		StringBuffer sql = null;
		Integer projectId = queryParamJSON.getInteger("projectId");
		String userType = queryParamJSON.getString("userType");
		sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL_RELATE_DEPT_BUSINESS_PERSON);
		sql.append(" and t.project_id = " + projectId);
		Pagination<EquPonItProjectInfoEntity_HI_RO> pagination = equPonItProjectInfoEntity_HI_RO.findPagination(sql,1,10);

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
<<<<<<< .mine
=======
	 * 立项审批回调接口
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public EquPonItProjectInfoEntity_HI projectApprovalCallback(JSONObject parseObject, int userId) throws Exception {
		Integer headerId = parseObject.getIntValue("id");//单据Id
		EquPonItProjectInfoEntity_HI entityHeader = this.getById(headerId);
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
				orderStatus = "30";
				//立项及立项变更流程结束增加邮件到owner
//				JSONObject obj = new JSONObject();
//				obj.put("projectId",headerId);
//				JSONObject result  =findProjectInfoById(obj);
//				List<String> incomingParam = new ArrayList<>();
//				List<String> efferentParam = new ArrayList<>();
//				List<String> typeParam = new ArrayList<>();
//				incomingParam.add("projectStatus");
//				efferentParam.add("projectStatusMeaning");
//				typeParam.add("EQU_PON_PROJECT_STATUS");
//				JSONArray data = result.getJSONArray("data");
//				data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
//				if(data.size() > 0){
//					JSONObject dataObj = data.getJSONObject(0);
//					String email = dataObj.getString("email");
//					System.out.println(email);
//					String mailBody = CommonUtils.projectAllowMailContent(entityHeader.getProjectNumber());
//					EmailUtil.sendMailFromWatsons(email, "立项审批完成", mailBody);
//				}
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
>>>>>>> .r13050
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
	 * 查找导航菜单节点
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findPonItNavigationMenuNodeList(JSONObject queryParamJSON) {
		Integer projectId = queryParamJSON.getInteger("projectId");
		StringBuffer sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_PON_NAVIGATION_MENU_SQL);
		boolean flag = false;
		Map<String, Object> map = new HashMap<>();
		Pagination<EquPonItProjectInfoEntity_HI_RO> pagination = equPonItProjectInfoEntity_HI_RO.findPagination(sql, map, 1, 999);

		List<EquPonItProjectInfoEntity_HI_RO> resultList = pagination.getData();

		if(null == projectId){
			for(int i = 0; i < resultList.size(); i++){
				EquPonItProjectInfoEntity_HI_RO entity = resultList.get(i);
				entity.setStatus("0");
			}
		}else{
			for(int i = 0; i < resultList.size(); i++){
				EquPonItProjectInfoEntity_HI_RO entity = resultList.get(i);
				String nodeName = entity.getNodeName();
				String status = "";
				String imageName = "";
				int count = 0;
				if("LX".equals(nodeName)){
					//查询立项处理状态
					List<EquPonItProjectInfoEntity_HI> projectList = equPonItProjectInfoDAO_HI.findByProperty("projectId",projectId);
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
					List<EquPonItProjectInfoEntity_HI> projectList = equPonItProjectInfoDAO_HI.findByProperty("projectId",projectId);
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
//					Map informationQueryMap = new HashMap();
//					informationQueryMap.put("projectId",projectId);
//					informationQueryMap.put("deptCode","03");
//					List<EquPonQuotationInformationEntity_HI> quotationInformationList = equPonQuotationInformationDAO_HI.findByProperty(informationQueryMap);

					List<EquPonQuotationInformationEntity_HI> quotationInformationList = equPonQuotationInformationDAO_HI.findList("from EquPonQuotationInformationEntity_HI where projectId = " + projectId + " and deptCode <> '0E'");

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
					List<EquPonItScoringInfoEntity_HI> scoringList = equPonItScoringInfoDAO_HI.findByProperty("projectId",projectId);
					if(scoringList.size() == 0){
						status = "0"; //未处理
						imageName = "pf_unhandle.png";
					}else{
						for(int j = 0; j < scoringList.size(); j++){
							if("30".equals(scoringList.get(j).getScoringStatus())){
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
//					Map approvalQueryMap = new HashMap();
//					approvalQueryMap.put("projectId",projectId);
//					approvalQueryMap.put("deptCode","03");
//					List<EquPonQuotationApprovalEntity_HI> quotationApprovalList = equPonQuotationApprovalDAO_HI.findByProperty(approvalQueryMap);

					List<EquPonQuotationApprovalEntity_HI> quotationApprovalList = equPonQuotationApprovalDAO_HI.findList("from EquPonQuotationApprovalEntity_HI where projectId = " + projectId + " and deptCode <> '0E'");

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
			if("SECURITY".equals(deptment)){
				sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL_DEFAULT_WITNESS_SECURITY);
			}else if("IA".equals(deptment)){
				sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL_DEFAULT_WITNESS_IA);
			}
		}else if("Appoint".equals(witnessType)){
			if("SECURITY".equals(deptment)){
				sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL_WITNESS_SECURITY);
			}else if("IA".equals(deptment)){
				sql = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_SQL_WITNESS_IA);
			}
		}
		sql.append(" and t.project_id = " + projectId);
		queryParamJSON.remove("projectId");
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonItProjectInfoEntity_HI_RO> pagination = equPonItProjectInfoEntity_HI_RO.findPagination(sql, map,1,10);
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
}
