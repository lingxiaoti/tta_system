package com.sie.watsons.base.supplement.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

/**
 * PlmSupplementHeadEntity_HI_RO Entity Object Mon Sep 09 15:57:23 GMT+08:00
 * 2019 Auto Generate
 */

public class PlmSupplementHeadEntity_HI_RO {
	public static final String FINDPIMSINFO = "SELECT DISTINCT "
			// + " l.plm_supplement_line_id as plmSupplementLineId,"
			+ " h.PLM_SUPPLEMENT_HEAD_ID as plmSupplementHeadId,"
			+ " h.sup_code as supCode,"
			+ " l.rms_code as rmsCode, "
			+ " h.ORDER_TYPE as orderType,"
			+ " h.ORDER_STATUS as orderStatus,"
			+ " h.CREATOR as creator,"
			+ " h.CREATED_BY as createdBy,"
			+ " h.ORG_CODE as orgCode,"
			+
			// "LAST_UPDATED_BY as lastUpdatedBy," +
			// "LAST_UPDATE_DATE," +
			// "LAST_UPDATE_LOGIN," +
			// "VERSION_NUM," +
			"h.CREATION_DATE as creationDate" + " from PLM_SUPPLEMENT_HEAD h"
			+ " LEFT JOIN plm_supplement_line l "
			+ " on h.PLM_SUPPLEMENT_HEAD_ID = l.HEAD_ID  " + " where 1=1 ";
	// 用于查询详情
	public static final String FINDPIMSINFO_2 = "SELECT  "
			+ " h.PLM_SUPPLEMENT_HEAD_ID as plmSupplementHeadId,"
			+ " h.sup_code as supCode," + " h.ORDER_TYPE as orderType,"
			+ " h.ORDER_STATUS as orderStatus," + " h.USER_DEPT as userDept,"
			+ " h.user_group_code as userGroupCode,"
			+ " h.org_code as orgCode," + " h.CREATOR as creator,"
			+ " h.CREATED_BY as createdBy,"
			+ " h.CREATION_DATE as creationDate,"
			+ " h.PROCESS_USER as processUser,"
			+ " h.RMS_REVER_BUSINESSKEY as rmsReverBusinesskey,"
			+ " h.PROCESS_REPARAM as processReparam "
			+ " from PLM_SUPPLEMENT_HEAD h" + " where 1=1 ";

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date exeDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date expireDate;
	private String rmsCode;
	private String productName;
	private String meter;
	private String supplementStatus;
	private String stopReason;
	private String brandnameCn;
	private String orgCode;
	private Integer plmSupplementHeadId;
	private Integer plmSupplementLineId;
	private String supCode;
	private String orderType;
	private String orderTypeDesc;
	private String orderStatus;
	private String orderStatusDesc;
	// private String orderStatusDesc;
	private String creator;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;
	private String userDept;
	private String userGroupCode;

	private String processUser; // 流程发起人
	private String rmsReverBusinesskey; // 流程版本
	private String processReparam; // 流程驳回参数保存

	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	public String getRmsReverBusinesskey() {
		return rmsReverBusinesskey;
	}

	public void setRmsReverBusinesskey(String rmsReverBusinesskey) {
		this.rmsReverBusinesskey = rmsReverBusinesskey;
	}

	public String getProcessReparam() {
		return processReparam;
	}

	public void setProcessReparam(String processReparam) {
		this.processReparam = processReparam;
	}

	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public Date getExeDate() {
		return exeDate;
	}

	public void setExeDate(Date exeDate) {
		this.exeDate = exeDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMeter() {
		return meter;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	public String getSupplementStatus() {
		return supplementStatus;
	}

	public void setSupplementStatus(String supplementStatus) {
		this.supplementStatus = supplementStatus;
	}

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	public void setPlmSupplementHeadId(Integer plmSupplementHeadId) {
		this.plmSupplementHeadId = plmSupplementHeadId;
	}

	public Integer getPlmSupplementHeadId() {
		return plmSupplementHeadId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	public String getOrderTypeDesc() {
		if (this.orderType != null) {
			if (createdBy != null) {
				creator = ResultConstant.userMap.get(createdBy).getString(
						"userFullName");
			}
			if (this.orderType.equals("0")) {
				orderTypeDesc = "促销订单";
			}
			if (this.orderType.equals("1")) {
				orderTypeDesc = "陈列图订单";
			}
			if (this.orderType.equals("2")) {
				orderTypeDesc = "促销及陈列图订单";
			}
		}
		return orderTypeDesc;
	}

	public String getOrderStatusDesc() {
		if (this.orderStatus != null) {
			if (this.orderStatus.equals("0")) {
				orderStatusDesc = "未提交";
			}
			if (this.orderStatus.equals("1")) {
				orderStatusDesc = "审批中";
			}
			if (this.orderStatus.equals("2")) {
				orderStatusDesc = "已审批";
			}
			if (this.orderStatus.equals("3")) {
				orderStatusDesc = "已驳回";
			}
			if (this.orderStatus.equals("4")) {
				orderStatusDesc = "已作废";
			}
			if (this.orderStatus.equals("5")) {
				orderStatusDesc = "待审批";
			}
		}
		return orderStatusDesc;
	}

	public Integer getPlmSupplementLineId() {
		return plmSupplementLineId;
	}

	public void setPlmSupplementLineId(Integer plmSupplementLineId) {
		this.plmSupplementLineId = plmSupplementLineId;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	// public String getOrderStatusDesc() {
	// if (orderStatus != null){
	// if (orderStatus.equals("0")){
	// orderStatusDesc = "作废";
	// }
	// if (orderStatus.equals("1")){
	// orderStatusDesc = "制单中";
	// }
	// if (orderStatus.equals("2")){
	// orderStatusDesc = "审批中";
	// }
	// if (orderStatus.equals("3")){
	// orderStatusDesc = "审批完成";
	// }
	// }
	// return orderStatusDesc;
	// }
}
