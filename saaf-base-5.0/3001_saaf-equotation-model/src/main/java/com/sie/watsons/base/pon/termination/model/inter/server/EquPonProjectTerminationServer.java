package com.sie.watsons.base.pon.termination.model.inter.server;

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
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonWitnessTeamEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.watsons.base.pon.termination.model.entities.readonly.EquPonProjectTerminationEntity_HI_RO;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pon.termination.model.entities.EquPonProjectTerminationEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.termination.model.inter.IEquPonProjectTermination;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonProjectTerminationServer")
public class EquPonProjectTerminationServer extends BaseCommonServer<EquPonProjectTerminationEntity_HI> implements IEquPonProjectTermination{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectTerminationServer.class);

	@Autowired
	private ViewObject<EquPonProjectTerminationEntity_HI> equPonProjectTerminationDAO_HI;

	@Autowired
	private BaseViewObject<EquPonProjectTerminationEntity_HI_RO> equPonProjectTerminationEntity_HI_RO;

	@Autowired
	private ViewObject<EquPonProjectInfoEntity_HI> equPonProjectInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonItProjectInfoEntity_HI> equPonItProjectInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInfoEntity_HI> equPonQuotationInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInfoItEntity_HI> equPonQuotationInfoItDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInformationEntity_HI> equPonQuotationInformationDAO_HI;

	@Autowired
	private ViewObject<EquPonScoringInfoEntity_HI> equPonScoringInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringInfoEntity_HI> equPonItScoringInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationApprovalEntity_HI> equPonQuotationApprovalDAO_HI;

	@Autowired
	private ViewObject<EquPonWitnessTeamEntity_HI> equPonWitnessTeamDAO_HI;


	@Autowired
	private GenerateCodeServer generateCodeServer;

	public EquPonProjectTerminationServer() {
		super();
	}

	/**
	 * 报价管理-立项终止单据查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findProjectTermination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPonProjectTerminationEntity_HI_RO.QUERY_SQL);
		if(queryParamJSON.containsKey("deptCode")){
			String deptCode = queryParamJSON.getString("deptCode");
			if("0E".equals(deptCode)){
				sql = new StringBuffer(EquPonProjectTerminationEntity_HI_RO.QUERY_SQL);
			}else{
				sql = new StringBuffer(EquPonProjectTerminationEntity_HI_RO.QUERY_SQL_IT);
			}
		}
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonProjectTerminationEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.creation_date desc");
		Pagination<EquPonProjectTerminationEntity_HI_RO> pagination = equPonProjectTerminationEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public JSONObject findItProjectTermination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonProjectTerminationEntity_HI_RO.QUERY_SQL_IT);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonProjectTerminationEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.creation_date desc");
		Pagination<EquPonProjectTerminationEntity_HI_RO> pagination = equPonProjectTerminationEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-立项终止单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonProjectTerminationEntity_HI saveProjectTermination(JSONObject params) throws Exception {
		EquPonProjectTerminationEntity_HI projectEntity = null;
		if(!params.containsKey("terminationId")){
			params.put("status","10");

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String currentDate = format.format(new Date());
			String prefix = "ZZ-" + currentDate;
			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
			String terminationNumber = prefix + "-" + sequenceId;
			params.put("terminationNumber",terminationNumber);
		}
		projectEntity = this.saveOrUpdate(params);
		return projectEntity;
	}

	/**
	 * 报价管理-立项终止单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public Integer deleteProjectTermination(JSONObject params) throws Exception {
		String status = params.getString("status");
		String terminationNumber = params.getString("terminationNumber");
		this.deleteById(params.getInteger("id"));
		if("40".equals(status)){
			//查询流程信息，提取流程id
			JSONObject b = new JSONObject();
			b.put("code", terminationNumber);
			JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
			//根据流程id，终止流程
			Integer listId = resultJSON.getInteger("listId");
			return listId;
		}
		return null;
	}

	/**
	 * 立项终止审批回调接口
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public EquPonProjectTerminationEntity_HI projectTerminationCallback(JSONObject parseObject, int userId) throws Exception {
		Integer headerId = parseObject.getIntValue("id");//单据Id
		EquPonProjectTerminationEntity_HI entityHeader = this.getById(headerId);
		Integer projectId = entityHeader.getProjectId(); //立项id
		String orderStatus = null;//状态
		switch (parseObject.getString("status")) {
			case "REFUSAL":
				orderStatus = "40";
				break;
			case "ALLOW":
				orderStatus = "30";
				//查找立项信息，修改状态为终止，并且终止审批流程
				EquPonProjectInfoEntity_HI projectEntity = equPonProjectInfoDAO_HI.getById(projectId);
				String sourceProjectNumber = projectEntity.getSourceProjectNumber();
				//查找立项所有版本信息
//				List<EquPonProjectInfoEntity_HI> allVersionProjectArray = equPonProjectInfoDAO_HI.findByProperty("sourceProjectNumber",sourceProjectNumber);
				Map map =new HashMap();
				map.put("sourceProjectNumber",sourceProjectNumber);
				List<EquPonProjectInfoEntity_HI> allVersionProjectArray = equPonProjectInfoDAO_HI.findList(" from EquPonProjectInfoEntity_HI where sourceProjectNumber = :sourceProjectNumber order by projectVersion desc", map);
				for(int i = 0; i < allVersionProjectArray.size(); i++){
					EquPonProjectInfoEntity_HI versionProjectEntity = allVersionProjectArray.get(i);
					Integer versionProjectId = versionProjectEntity.getProjectId();
					String versionProjectNumber = versionProjectEntity.getProjectNumber();
					//查找当前立项版本对应的报价单
					List<EquPonQuotationInfoEntity_HI> quotationInfoArray = equPonQuotationInfoDAO_HI.findByProperty("projectId",versionProjectId);
					for(int j = 0; j < quotationInfoArray.size(); j++){
						EquPonQuotationInfoEntity_HI quotationInfoEntity = quotationInfoArray.get(j);
						//修改该报价单状态为终止
						quotationInfoEntity.setDocStatus("BREAK");
					}

					//查找当前立项版本对应的资料开启单据
					Map map2 = new HashMap();
					map2.put("projectId",versionProjectId);
					map2.put("deptCode","0E");
					List<EquPonQuotationInformationEntity_HI> quotationInformationArray = equPonQuotationInformationDAO_HI.findByProperty(map2);
					for(int j = 0; j < quotationInformationArray.size(); j++){
						EquPonQuotationInformationEntity_HI quotationInformationEntity = quotationInformationArray.get(j);
						//修改该资料开启单据状态为终止
						quotationInformationEntity.setInformationStatus("50");
					}

					//查找当前立项版本对应的评分单据
					List<EquPonScoringInfoEntity_HI> scoringArray = equPonScoringInfoDAO_HI.findByProperty("projectId",versionProjectId);
					for(int j = 0; j < scoringArray.size(); j++){
						EquPonScoringInfoEntity_HI scoringInfoEntity = scoringArray.get(j);
						//当前评分单据状态为审批中/驳回，终止审批流程
						if("20".equals(scoringInfoEntity.getScoringStatus()) || "40".equals(scoringInfoEntity.getScoringStatus())){
							stopProcess(scoringInfoEntity.getScoringNumber(),"立项终止，流程随之终止!");
						}
						//修改该评分单据状态为终止
						scoringInfoEntity.setScoringStatus("50");
					}

					//查找当前立项版本对应的报价结果审批单据
					Map map3 = new HashMap();
					map3.put("projectId",versionProjectId);
					map3.put("deptCode","0E");
					List<EquPonQuotationApprovalEntity_HI> quotationApprovalArray = equPonQuotationApprovalDAO_HI.findByProperty(map3);
					for(int j = 0; j < quotationApprovalArray.size(); j++){
						EquPonQuotationApprovalEntity_HI quotationApprovalEntity = quotationApprovalArray.get(j);
						//当前报价结果审批单据状态为审批中/驳回，终止审批流程
						if("20".equals(quotationApprovalEntity.getApprovalStatus()) || "40".equals(quotationApprovalEntity.getApprovalStatus())){
							stopProcess(quotationApprovalEntity.getApprovalNumber(),"立项终止，流程随之终止!");
						}
						//修改该报价结果审批单据状态为终止
						quotationApprovalEntity.setApprovalStatus("50");
					}

					if("20".equals(versionProjectEntity.getProjectStatus()) || "40".equals(versionProjectEntity.getProjectStatus())){
						//当该立项版本的状态为审批中/驳回，终止审批流程
						stopProcess(versionProjectNumber,"立项终止，流程随之终止!");
					}
					//修改该立项版本的状态为终止
					versionProjectEntity.setProjectStatus("50");

					/**
					 * 发送邮件通知见证人员(取立项最高版本见证人员列表)
					 * 条件：1.采购金额需大于采购金额阈值
					 *      2.已发送报价邀请
					 */
					if(i == 0 && null != versionProjectEntity.getSendQuotationInvitationDate()){
						if(versionProjectEntity.getProjectPurchaseAmount().doubleValue() > versionProjectEntity.getPurchaseAmountThreshold().doubleValue()){
							List<EquPonWitnessTeamEntity_HI> witnessTeamArray = equPonWitnessTeamDAO_HI.findByProperty("projectId",versionProjectEntity.getProjectId());
							for(int j = 0; j < witnessTeamArray.size(); j++){
								EquPonWitnessTeamEntity_HI witnessTeamEntity = witnessTeamArray.get(j);
								String witnessPersonNumber = witnessTeamEntity.getDefaultMember();

								JSONObject paramsJson = new JSONObject();
								paramsJson = new JSONObject();
								paramsJson.put("userName", witnessPersonNumber);
								JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
								String mailBody = CommonUtils.projectTerminateMailContent(versionProjectEntity.getProjectName());
								if (resultJson2.get("email") != null) {
									EmailUtil.sendInMail("立项终止通知",mailBody,resultJson2.getString("email"));
								}
							}
						}
					}
				}
				break;
			case "DRAFT":
				orderStatus = "10";
				break;
			case "APPROVAL":
				orderStatus = "20";
				break;
		}

		//流程节点审批通过,邮件通知owner
		CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getTerminationNumber());

		entityHeader.setStatus(orderStatus);
		entityHeader.setOperatorUserId(userId);
		this.saveOrUpdate(entityHeader);
		return entityHeader;
	}


	/**
	 * 立项终止审批回调接口
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public EquPonProjectTerminationEntity_HI itProjectTerminationCallback(JSONObject parseObject, int userId) throws Exception {
		Integer headerId = parseObject.getIntValue("id");//单据Id
		EquPonProjectTerminationEntity_HI entityHeader = this.getById(headerId);
		Integer projectId = entityHeader.getProjectId(); //立项id
		String orderStatus = null;//状态
		switch (parseObject.getString("status")) {
			case "REFUSAL":
				orderStatus = "40";
				break;
			case "ALLOW":
				orderStatus = "30";
				//查找立项信息，修改状态为终止，并且终止审批流程
				EquPonItProjectInfoEntity_HI projectEntity = equPonItProjectInfoDAO_HI.getById(projectId);
				String sourceProjectNumber = projectEntity.getSourceProjectNumber();
				//查找立项所有版本信息
//				List<EquPonProjectInfoEntity_HI> allVersionProjectArray = equPonProjectInfoDAO_HI.findByProperty("sourceProjectNumber",sourceProjectNumber);
				Map map =new HashMap();
				map.put("sourceProjectNumber",sourceProjectNumber);
				List<EquPonItProjectInfoEntity_HI> allVersionProjectArray = equPonItProjectInfoDAO_HI.findList(" from EquPonItProjectInfoEntity_HI where sourceProjectNumber = :sourceProjectNumber order by projectVersion desc", map);
				for(int i = 0; i < allVersionProjectArray.size(); i++){
					EquPonItProjectInfoEntity_HI versionProjectEntity = allVersionProjectArray.get(i);
					Integer versionProjectId = versionProjectEntity.getProjectId();
					String versionProjectNumber = versionProjectEntity.getProjectNumber();
					//查找当前立项版本对应的报价单
					List<EquPonQuotationInfoItEntity_HI> quotationInfoArray = equPonQuotationInfoItDAO_HI.findByProperty("projectId",versionProjectId);
					for(int j = 0; j < quotationInfoArray.size(); j++){
						EquPonQuotationInfoItEntity_HI quotationInfoEntity = quotationInfoArray.get(j);
						//修改该报价单状态为终止
						quotationInfoEntity.setDocStatus("BREAK");
					}

					//查找当前立项版本对应的资料开启单据
//					Map map2 = new HashMap();
//					map2.put("projectId",versionProjectId);
//					map2.put("deptCode","03");
//					List<EquPonQuotationInformationEntity_HI> quotationInformationArray = equPonQuotationInformationDAO_HI.findByProperty(map2);

					List<EquPonQuotationInformationEntity_HI> quotationInformationArray = equPonQuotationInformationDAO_HI.findList("from EquPonQuotationInformationEntity_HI where projectId = " + versionProjectId + " and deptCode <> '0E'");

					for(int j = 0; j < quotationInformationArray.size(); j++){
						EquPonQuotationInformationEntity_HI quotationInformationEntity = quotationInformationArray.get(j);
						//修改该资料开启单据状态为终止
						quotationInformationEntity.setInformationStatus("50");
					}

					//查找当前立项版本对应的评分单据
					List<EquPonItScoringInfoEntity_HI> scoringArray = equPonItScoringInfoDAO_HI.findByProperty("projectId",versionProjectId);
					for(int j = 0; j < scoringArray.size(); j++){
						EquPonItScoringInfoEntity_HI scoringInfoEntity = scoringArray.get(j);
						//当前评分单据状态为审批中/驳回，终止审批流程
						if("20".equals(scoringInfoEntity.getScoringStatus()) || "40".equals(scoringInfoEntity.getScoringStatus())){
							stopProcess(scoringInfoEntity.getScoringNumber(),"立项终止，流程随之终止!");
						}
						//修改该评分单据状态为终止
						scoringInfoEntity.setScoringStatus("50");
					}

					//查找当前立项版本对应的报价结果审批单据
//					Map map3 = new HashMap();
//					map3.put("projectId",versionProjectId);
//					map3.put("deptCode","03");
//					List<EquPonQuotationApprovalEntity_HI> quotationApprovalArray = equPonQuotationApprovalDAO_HI.findByProperty(map3);

					List<EquPonQuotationApprovalEntity_HI> quotationApprovalArray = equPonQuotationApprovalDAO_HI.findList("from EquPonQuotationApprovalEntity_HI where projectId = " + versionProjectId + " and deptCode <> '0E'");

					for(int j = 0; j < quotationApprovalArray.size(); j++){
						EquPonQuotationApprovalEntity_HI quotationApprovalEntity = quotationApprovalArray.get(j);
						//当前报价结果审批单据状态为审批中/驳回，终止审批流程
						if("20".equals(quotationApprovalEntity.getApprovalStatus()) || "40".equals(quotationApprovalEntity.getApprovalStatus())){
							stopProcess(quotationApprovalEntity.getApprovalNumber(),"立项终止，流程随之终止!");
						}
						//修改该报价结果审批单据状态为终止
						quotationApprovalEntity.setApprovalStatus("50");
					}

					if("20".equals(versionProjectEntity.getProjectStatus()) || "40".equals(versionProjectEntity.getProjectStatus())){
						//当该立项版本的状态为审批中/驳回，终止审批流程
						stopProcess(versionProjectNumber,"立项终止，流程随之终止!");
					}
					//修改该立项版本的状态为终止
					versionProjectEntity.setProjectStatus("50");

					/**
					 * 发送邮件通知见证人员(取立项最高版本见证人员列表)
					 * 条件：1.采购金额需大于采购金额阈值
					 *      2.已发送报价邀请
					 */
//					if(i == 0 && null != versionProjectEntity.getSendQuotationInvitationDate()){
//						if(versionProjectEntity.getProjectPurchaseAmount().doubleValue() > versionProjectEntity.getPurchaseAmountThreshold().doubleValue()){
//							List<EquPonWitnessTeamEntity_HI> witnessTeamArray = equPonWitnessTeamDAO_HI.findByProperty("projectId",versionProjectEntity.getProjectId());
//							for(int j = 0; j < witnessTeamArray.size(); j++){
//								EquPonWitnessTeamEntity_HI witnessTeamEntity = witnessTeamArray.get(i);
//								String witnessPersonNumber = witnessTeamEntity.getDefaultMember();
//
//								JSONObject paramsJson = new JSONObject();
//								paramsJson = new JSONObject();
//								paramsJson.put("userName", witnessPersonNumber);
//								JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
//								String mailBody = CommonUtils.projectTerminateMailContent(versionProjectEntity.getProjectName());
//								if (resultJson2.get("email") != null) {
//									EmailUtil.sendMailFromWatsons(resultJson2.getString("email"), "立项终止通知", mailBody);
//								}
//							}
//						}
//					}
				}
				break;
			case "DRAFT":
				orderStatus = "10";
				break;
			case "APPROVAL":
				orderStatus = "20";
				break;
		}

		//流程节点审批通过,邮件通知owner
		CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getTerminationNumber());

		entityHeader.setStatus(orderStatus);
		entityHeader.setOperatorUserId(userId);
		this.saveOrUpdate(entityHeader);
		return entityHeader;
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
