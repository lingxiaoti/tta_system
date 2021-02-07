package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmBaseSeriesEntity_HI_RO Entity Object Wed Nov 13 11:28:41 CST 2019 Auto
 * Generate
 */

public class PlmBaseSeriesEntity_HI_RO {
	public static String query = "SELECT\r\n" + "SERIES.ID AS \"id\",\r\n"
			+ "SERIES.SERIAL_NAME AS \"serialName\",\r\n"
			+ "SERIES.SERIAL_DESC AS \"serialDesc\",\r\n"
			+ "SERIES.VERSION_NUM AS \"versionNum\",\r\n"
			+ "SERIES.CREATION_DATE AS \"creationDate\",\r\n"
			+ "SERIES.CREATED_BY AS \"createdBy\",\r\n"
			+ "SERIES.LAST_UPDATED_BY AS \"lastUpdatedBy\",\r\n"
			+ "SERIES.LAST_UPDATE_DATE AS \"lastUpdateDate\",\r\n"
			+ "SERIES.LAST_UPDATE_LOGIN AS \"lastUpdateLogin\"\r\n"
			+ "FROM\r\n" + "PLM_BASE_SERIES SERIES where 1=1";
	private Integer id;
	private String serialName;
	private String serialDesc;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialDesc(String serialDesc) {
		this.serialDesc = serialDesc;
	}

	public String getSerialDesc() {
		return serialDesc;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
