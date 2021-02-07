package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmGroupBrandEntity_HI_RO Entity Object Thu Oct 31 15:07:24 CST 2019 Auto
 * Generate
 */

public class PlmGroupBrandEntity_HI_RO {
	private Integer plmGroupBrandId;
	private String plmGroupBrandName;
	private String remarks;
	private String billStatus;
	private String billStatusName;
	private String creator;
	private Integer ta;
	private String taName;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private String processReject;
	private String processUser;
	private String processIncident;

	public static final String QUERY_SQL = "SELECT pgb.PLM_GROUP_BRAND_ID as plmGroupBrandId,\n"
			+ "       pgb.PLM_GROUP_BRAND_NAME as plmGroupBrandName,\n"
			+ "       pgb.REMARKS as remarks,\n"
			+ "       pgb.BILL_STATUS as billStatus,\n"
			+ "       pgb.BILL_STATUS_NAME as billStatusName,\n"
			+ "       pgb.CREATOR as creator,\n"
			+ "       pgb.TA as ta,\n"
			+ "       pgb.TA_NAME as taName,\n"
			+ "       pgb.CREATED_BY as createdBy,\n"
			+ "       pgb.LAST_UPDATED_BY as lastUpdatedBy,\n"
			+ "       pgb.LAST_UPDATE_DATE as lastUpdateDate,\n"
			+ "       pgb.LAST_UPDATE_LOGIN as lastUpdateLogin,\n"
			+ "       pgb.VERSION_NUM as versionNum,\n"
			+ "       pgb.CREATION_DATE as creationDate,\n"
			+ "       pgb.process_reject as processReject,\n"
			+ "       pgb.process_user as processUser,\n"
			+ "       pgb.process_incident as processIncident\n"
			+ " FROM PLM_GROUP_BRAND pgb\n" + "WHERE 1=1 ";

	public String getProcessReject() {
		return processReject;
	}

	public void setProcessReject(String processReject) {
		this.processReject = processReject;
	}

	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	public String getProcessIncident() {
		return processIncident;
	}

	public void setProcessIncident(String processIncident) {
		this.processIncident = processIncident;
	}

	public void setPlmGroupBrandId(Integer plmGroupBrandId) {
		this.plmGroupBrandId = plmGroupBrandId;
	}

	public Integer getPlmGroupBrandId() {
		return plmGroupBrandId;
	}

	public void setPlmGroupBrandName(String plmGroupBrandName) {
		this.plmGroupBrandName = plmGroupBrandName;
	}

	public String getPlmGroupBrandName() {
		return plmGroupBrandName;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	public String getBillStatusName() {
		return billStatusName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setTa(Integer ta) {
		this.ta = ta;
	}

	public Integer getTa() {
		return ta;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	public String getTaName() {
		return taName;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
