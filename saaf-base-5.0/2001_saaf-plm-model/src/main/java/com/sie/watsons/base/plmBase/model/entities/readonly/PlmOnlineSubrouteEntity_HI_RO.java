package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmOnlineSubrouteEntity_HI_RO Entity Object Mon Nov 04 15:37:22 CST 2019 Auto
 * Generate
 */

public class PlmOnlineSubrouteEntity_HI_RO {
	private Integer plmOnlineSubrouteId;
	private Integer plmOnlineRouteId;
	private String plmOnlineRouteCode;
	private String plmOnlineRouteName;
	private String creator;
	private String subrouteName;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private String remarks;
	private String billStatus;
	private String billStatusName;

	public static final String QUERY_SQL = "SELECT pos.PLM_ONLINE_SUBROUTE_ID as plmOnlineSubrouteId,\n"
			+ "       pos.PLM_ONLINE_ROUTE_ID as plmOnlineRouteId,\n"
			+ "       pos.PLM_ONLINE_ROUTE_CODE as plmOnlineRouteCode,\n"
			+ "       pos.PLM_ONLINE_ROUTE_NAME as plmOnlineRouteName,\n"
			+ "       pos.CREATOR as creator,\n"
			+ "		pos.BILL_STATUS as billStatus,\n"
			+ "		pos.BILL_STATUS_NAME as billStatusName,\n"
			+ "       pos.SUBROUTE_NAME as subrouteName,\n"
			+ "       pos.CREATED_BY as createdBy,\n"
			+ "       pos.LAST_UPDATED_BY as lastUpdatedBy,\n"
			+ "       pos.LAST_UPDATE_DATE as lastUpdateDate,\n"
			+ "       pos.LAST_UPDATE_LOGIN as lastUpdateLogin,\n"
			+ "       pos.VERSION_NUM as versionNum,\n"
			+ "       pos.CREATION_DATE as creationDate,\n"
			+ "    route.remarks as remarks "
			+ " FROM PLM_ONLINE_SUBROUTE pos left join PLM_ONLINE_ROUTE route on pos.PLM_ONLINE_ROUTE_CODE=route.PLM_ONLINE_ROUTE_CODE \n"
			+ " WHERE 1=1 ";

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setPlmOnlineSubrouteId(Integer plmOnlineSubrouteId) {
		this.plmOnlineSubrouteId = plmOnlineSubrouteId;
	}

	public Integer getPlmOnlineSubrouteId() {
		return plmOnlineSubrouteId;
	}

	public void setPlmOnlineRouteId(Integer plmOnlineRouteId) {
		this.plmOnlineRouteId = plmOnlineRouteId;
	}

	public Integer getPlmOnlineRouteId() {
		return plmOnlineRouteId;
	}

	public void setPlmOnlineRouteCode(String plmOnlineRouteCode) {
		this.plmOnlineRouteCode = plmOnlineRouteCode;
	}

	public String getPlmOnlineRouteCode() {
		return plmOnlineRouteCode;
	}

	public void setPlmOnlineRouteName(String plmOnlineRouteName) {
		this.plmOnlineRouteName = plmOnlineRouteName;
	}

	public String getPlmOnlineRouteName() {
		return plmOnlineRouteName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setSubrouteName(String subrouteName) {
		this.subrouteName = subrouteName;
	}

	public String getSubrouteName() {
		return subrouteName;
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

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillStatusName() {
		return billStatusName;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}
}
