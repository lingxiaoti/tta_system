package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

/**
 * PlmOnlineRouteEntity_HI_RO Entity Object Mon Nov 04 15:37:22 CST 2019 Auto
 * Generate
 */

public class PlmOnlineRouteEntity_HI_RO {
	private Integer plmOnlineRouteId;
	private String plmOnlineRouteCode;
	private String plmOnlineRouteName;
	private String billStatus;
	private String billStatusName;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private String creator;
	private String remarks;
	private Integer operatorUserId;
	private String plmOnlineRouteNameStr;

	public static final String QUERY_SQL = "SELECT por.PLM_ONLINE_ROUTE_ID   as plmOnlineRouteId,\n"
			+ "       por.PLM_ONLINE_ROUTE_CODE as plmOnlineRouteCode,\n"
			+ "       por.PLM_ONLINE_ROUTE_NAME as plmOnlineRouteName,\n"
			+ "       por.BILL_STATUS           as billStatus,\n"
			+ "       por.BILL_STATUS_NAME      as billStatusName,\n"
			+ "       por.CREATED_BY            as createdBy,\n"
			+ "       por.LAST_UPDATE_DATE      as lastUpdateDate,\n"
			+ "       por.LAST_UPDATED_BY       as lastUpdatedBy,\n"
			+ "       por.LAST_UPDATE_LOGIN     as lastUpdateLogin,\n"
			+ "       por.VERSION_NUM           as versionNum,\n"
			+ "       por.CREATION_DATE         as creationDate,\n"
			+ "       por.CREATOR               as creator,\n"
			+ "       por.REMARKS               as remarks\n"
			+ " FROM PLM_ONLINE_ROUTE por\n" + " WHERE 1=1 ";

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

	public String getPlmOnlineRouteNameStr() {
		if (this.plmOnlineRouteName != null) {
			if (ResultConstant.PLM_PRODUCT_ONLINETYPE != null) {
				this.plmOnlineRouteNameStr = ResultConstant.PLM_PRODUCT_ONLINETYPE
						.getString(plmOnlineRouteName);
			}

		}
		return plmOnlineRouteNameStr;
	}

	public void setPlmOnlineRouteNameStr(String plmOnlineRouteNameStr) {
		this.plmOnlineRouteNameStr = plmOnlineRouteNameStr;
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

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
