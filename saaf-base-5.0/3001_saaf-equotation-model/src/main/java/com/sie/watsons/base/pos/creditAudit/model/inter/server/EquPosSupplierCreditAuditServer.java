package com.sie.watsons.base.pos.creditAudit.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.blacklist.model.entities.readonly.EquPosSupplierBlacklistEntity_HI_RO;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosSupplierCreditAuditEntity_HI_RO;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.EquPosSupplierRecoverEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.readonly.EquPosSupplierRecoverEntity_HI_RO;
import com.sie.watsons.base.pos.warehousing.model.entities.readonly.EquPosSupplierWarehousingEntity_HI_RO;
import com.sie.watsons.base.utils.CommonUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosSupplierCreditAuditEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.creditAudit.model.inter.IEquPosSupplierCreditAudit;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosSupplierCreditAuditServer")
public class EquPosSupplierCreditAuditServer extends BaseCommonServer<EquPosSupplierCreditAuditEntity_HI> implements IEquPosSupplierCreditAudit{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCreditAuditServer.class);

	@Autowired
	private ViewObject<EquPosSupplierCreditAuditEntity_HI> equPosSupplierCreditAuditDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierCreditAuditEntity_HI_RO> equPosSupplierCreditAuditDAO_HI_RO;

	@Autowired
	GenerateCodeServer generateCodeServer;

	public EquPosSupplierCreditAuditServer() {
		super();
	}

	@Override
	public Pagination<EquPosSupplierCreditAuditEntity_HI_RO> findEquPosCreditAuditInfo(String params, Integer pageIndex, Integer pageRows){

		JSONObject jsonParam = JSONObject.parseObject(params);
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer( EquPosSupplierCreditAuditEntity_HI_RO.QUERY_AUDIT_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperParam(jsonParam, "T.sup_Credit_Audit_Id", "supCreditAuditId", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "T.sup_Credit_Audit_Id", "id", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "pi.supplier_Name", "supplierName", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "t.sup_Credit_Audit_Type", "supCreditAuditType", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "t.sup_Credit_Audit_Code", "supCreditAuditCode", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "T.sup_Blacklist_Code", "supBlacklistCode", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "pi.supplier_ID", "supplierId", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "t.sup_credit_audit_status", "supCreditAuditStatus", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Blacklist_Status)", "supBlacklistStatus", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Blacklist_Type)", "supBlacklistType", queryString, map, "=");
		// 排序
		JSONObject dateParam = new JSONObject();
		dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
		dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
		SaafToolUtils.parperHbmParam(EquPosSupplierCreditAuditEntity_HI_RO.class, dateParam, queryString, map);
		queryString.append(" order by t.creation_date desc");
		Pagination<EquPosSupplierCreditAuditEntity_HI_RO> sceneManageList = equPosSupplierCreditAuditDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

		return sceneManageList;
	}

	@Override
	public Pagination<EquPosSupplierCreditAuditEntity_HI_RO> findQualificationLov(String params, Integer pageIndex, Integer pageRows) {
		JSONObject jsonParam = JSONObject.parseObject(params);
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(
				EquPosSupplierCreditAuditEntity_HI_RO.QUERY_QUALIFICATION_LOV_SQL);
 		Map<String, Object> map = new HashMap<>();
		//信用审核选择
		if("cred".equals(jsonParam.getString("type"))){
			queryString = new StringBuffer(
					EquPosSupplierCreditAuditEntity_HI_RO.QUERY_QUAL_LOV_SQL);
			//如果已经匹配合格的信用审核单,就不要再查出来了
			queryString.append("    and not exists (SELECT 1\n" +
					"          FROM Equ_Pos_Supplier_Credit_Audit CR\n" +
					"         WHERE CR.QUALIFICATION_ID = a.QUALIFICATION_ID\n" +
					"           AND (CR.CREDIT_AUDIT_RESULE = 'Y' or\n" +
					"               nvl(cr.SPECIAL_RESULTS, 'N') = 'Y')) ");
			SaafToolUtils.parperParam(jsonParam, "a.dept_code", "deptCode", queryString, map, "=");
			SaafToolUtils.parperParam(jsonParam, "a.created_by", "varUserId", queryString, map, "=");
			SaafToolUtils.parperParam(jsonParam, "a.qualification_Number", "qualificationNumber", queryString, map, "like");
		}
		//供应商入库选择
		else if("warehousing".equals(jsonParam.getString("type"))){
			queryString = new StringBuffer(
					EquPosSupplierCreditAuditEntity_HI_RO.QUERY_QUAL_LOV_SQL);
			//如果已经匹配入库审核单,就不要再查出来了
			queryString.append(" and not exists (SELECT 1\n" +
					"          FROM EQU_POS_SUPPLIER_Warehousing CR\n" +
					"         WHERE CR.QUALIFICATION_ID = a.QUALIFICATION_ID ) ");
			SaafToolUtils.parperParam(jsonParam, "z.supplier_Number", "supplierNumber", queryString, map, "=");
		}
		SaafToolUtils.parperParam(jsonParam, "a.SCENE_TYPE", "supCreditAuditType", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "a.SCENE_TYPE", "sceneType", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "a.supplier_Id", "supplierIds", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "z.supplier_Name", "supplierNames", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "z.supplier_Number", "supplierNumbers", queryString, map, "like");
		// 排序
		queryString.append(" order by a.creation_date desc");
		Pagination<EquPosSupplierCreditAuditEntity_HI_RO> sceneManageList = equPosSupplierCreditAuditDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

		return sceneManageList;
	}

	@Override
	public EquPosSupplierCreditAuditEntity_HI saveEquPosCreditAuditSubmit(JSONObject jsonObject, int userId) {
		EquPosSupplierCreditAuditEntity_HI creditAuditEntityHi;
		creditAuditEntityHi = equPosSupplierCreditAuditDAO_HI.getById(jsonObject.getInteger("supCreditAuditId"));
		creditAuditEntityHi.setSupCreditAuditStatus("20");
		creditAuditEntityHi.setOperatorUserId(userId);
		equPosSupplierCreditAuditDAO_HI.save(creditAuditEntityHi);
		return creditAuditEntityHi;
	}

	@Override
	public EquPosSupplierCreditAuditEntity_HI saveEquPosCreditAudit(JSONObject jsonObject, int userId) throws Exception {
		EquPosSupplierCreditAuditEntity_HI jsonEntityHi = JSON.parseObject(jsonObject.toJSONString(), EquPosSupplierCreditAuditEntity_HI.class);
		EquPosSupplierCreditAuditEntity_HI creditAuditEntityHi;
		String supCreditAuditStatus = "10";
		if (jsonObject.get("supCreditAuditId") != null) {
			creditAuditEntityHi = equPosSupplierCreditAuditDAO_HI.getById(jsonObject.getInteger("supCreditAuditId"));
			supCreditAuditStatus = creditAuditEntityHi.getSupCreditAuditStatus();
			PropertyUtils.copyProperties(creditAuditEntityHi, jsonEntityHi);
			if(creditAuditEntityHi.getCreatedBy()==null){
				creditAuditEntityHi.setCreatedBy(userId);
				creditAuditEntityHi.setCreationDate(new Date());
			}
		} else {
			creditAuditEntityHi = jsonEntityHi;
			String code = generateCodeServer.getSupplierSuspendCode("XYSH", 4, true, true);
			creditAuditEntityHi.setSupCreditAuditCode(code);
			creditAuditEntityHi.setCreatedBy(userId);
			creditAuditEntityHi.setCreationDate(new Date());
		}
		String action = jsonObject.getString("action");
		if(!"Y".equals(creditAuditEntityHi.getIsSpecial())){
			creditAuditEntityHi.setSpecialFileId(null);
			creditAuditEntityHi.setSpecialResults(null);
		}
		switch (action) {
			//这里不改状态由回调改状态
//			case "submit":creditAuditEntityHi.setSupCreditAuditStatus("10");break;
//			case "approve":creditAuditEntityHi.setSupCreditAuditStatus("30");creditAuditEntityHi.setApproveDate(new Date());break;
			case "cancel":creditAuditEntityHi.setSupCreditAuditStatus("50");break;
			case "reject":creditAuditEntityHi.setSupCreditAuditStatus("40");break;
		}
		if(((!"10".equals(supCreditAuditStatus)&&!"40".equals(supCreditAuditStatus))&&("save".equals(action)))){
			throw new IllegalArgumentException("单据不是拟定或者驳回状态无法操作.");
		}
		creditAuditEntityHi.setOperatorUserId(userId);
		equPosSupplierCreditAuditDAO_HI.save(creditAuditEntityHi);
		return creditAuditEntityHi;
	}

	@Override
	public EquPosSupplierCreditAuditEntity_HI_RO findSupCreditAuditDatail(String params) {
		JSONObject jsonParam = JSONObject.parseObject(params);
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(
				EquPosSupplierCreditAuditEntity_HI_RO.QUERY_AUDIT_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		if(jsonParam.get("supCreditAuditId")!=null){
			SaafToolUtils.parperParam(jsonParam, "T.sup_Credit_Audit_Id", "supCreditAuditId", queryString, map, "=");
		}else if (jsonParam.get("id")!=null){
			SaafToolUtils.parperParam(jsonParam, "T.sup_Credit_Audit_Id", "id", queryString, map, "=");
		}else{
			queryString.append(" and 1 = 2");
		}
		EquPosSupplierCreditAuditEntity_HI_RO  creditAudit = (EquPosSupplierCreditAuditEntity_HI_RO)equPosSupplierCreditAuditDAO_HI_RO.get(queryString, map);
		return  creditAudit;
	}

	@Override
	public void deleteSupplierCreditAudit(JSONObject jsonObject, int userId) {
		EquPosSupplierCreditAuditEntity_HI creditAuditEntityHi;
		if (jsonObject.get("supCreditAuditId") != null) {
			equPosSupplierCreditAuditDAO_HI.delete(jsonObject.getInteger("supCreditAuditId"));
		}
	}

	@Override
	public EquPosSupplierCreditAuditEntity_HI updateCreditAuditApprovalCallback(JSONObject paramsObject, int userId) {
		Integer headerId = paramsObject.getIntValue("id");//单据Id
		EquPosSupplierCreditAuditEntity_HI entityHeader = this.getById(headerId);
		String orderStatus = null;//状态
		switch (paramsObject.getString("status")) {
			case "REFUSAL":
				orderStatus = "40";
				break;
			case "ALLOW":
				entityHeader.setApproveDate(new Date());
				orderStatus = "30";
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
		CommonUtils.processApprovalEmailToOwner(paramsObject,entityHeader.getCreatedBy(),entityHeader.getSupCreditAuditCode());

		entityHeader.setSupCreditAuditStatus(orderStatus);
//		entityHeader.setSupCreditAuditStatus("30");
		entityHeader.setOperatorUserId(userId);
		this.saveOrUpdate(entityHeader);
		return entityHeader;
	}




}
