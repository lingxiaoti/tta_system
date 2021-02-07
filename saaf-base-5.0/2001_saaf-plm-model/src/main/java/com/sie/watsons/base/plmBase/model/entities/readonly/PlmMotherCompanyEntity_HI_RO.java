package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmMotherCompanyEntity_HI_RO Entity Object Thu Oct 31 15:07:24 CST 2019 Auto
 * Generate
 */

public class PlmMotherCompanyEntity_HI_RO {
	private Integer plmMotherCompanyId;
	private String plmMotherCompanyName;
	private String remarks;
	private String billStatus;
	private String billStatusName;
	private String creator;
	private Integer createdBy;
	private Integer ba;
	private String baName;
	private Integer ta;
	private String taName;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private Integer udaId;
	private Integer udaValue;

	private String processReject;
	private String processUser;
	private String processIncident;

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

	public static final String QUERY_SQL = "SELECT pmc.PLM_MOTHER_COMPANY_ID as plmMotherCompanyId,\n"
			+ "       pmc.PLM_MOTHER_COMPANY_NAME as plmMotherCompanyName,\n"
			+ "       pmc.REMARKS as remarks,\n"
			+ "       pmc.BILL_STATUS as billStatus,\n"
			+ "       pmc.BILL_STATUS_NAME as billStatusName,\n"
			+ "       pmc.CREATOR as creator,\n"
			+ "       pmc.CREATED_BY as createdBy,\n"
			+ "       pmc.BA as ba,\n"
			+ "       pmc.BA_NAME as baName,\n"
			+ "       pmc.TA as ta,\n"
			+ "       pmc.TA_NAME as taName,\n"
			+ "       pmc.uda_Id as udaId,\n"
			+ "       pmc.uda_Value as udaValue,\n"
			+ "       pmc.LAST_UPDATED_BY as lastUpdatedBy,\n"
			+ "       pmc.LAST_UPDATE_LOGIN as lastUpdateLogin,\n"
			+ "       pmc.LAST_UPDATE_DATE as lastUpdateDate,\n"
			+ "       pmc.VERSION_NUM as versionNum,\n"
			+ "       pmc.CREATION_DATE as creationDate,\n"
			+ "       pmc.process_reject as processReject,\n"
			+ "       pmc.process_user as processUser,	\n"
			+ "       pmc.process_incident as processIncident "
			+ " FROM PLM_MOTHER_COMPANY pmc\n" + "WHERE 1=1 ";

	public static void main(String[] args) {
		System.out.println(" QUERY_SQL:::   " + QUERY_SQL);
	}

	public Integer getUdaId() {
		return udaId;
	}

	public void setUdaId(Integer udaId) {
		this.udaId = udaId;
	}

	public Integer getUdaValue() {
		return udaValue;
	}

	public void setUdaValue(Integer udaValue) {
		this.udaValue = udaValue;
	}

	public void setPlmMotherCompanyId(Integer plmMotherCompanyId) {
		this.plmMotherCompanyId = plmMotherCompanyId;
	}

	public Integer getPlmMotherCompanyId() {
		return plmMotherCompanyId;
	}

	public void setPlmMotherCompanyName(String plmMotherCompanyName) {
		this.plmMotherCompanyName = plmMotherCompanyName;
	}

	public String getPlmMotherCompanyName() {
		return plmMotherCompanyName;
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

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setBa(Integer ba) {
		this.ba = ba;
	}

	public Integer getBa() {
		return ba;
	}

	public void setBaName(String baName) {
		this.baName = baName;
	}

	public String getBaName() {
		return baName;
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
