package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmBaseLevelEntity_HI_RO Entity Object Thu Nov 14 17:27:58 CST 2019 Auto
 * Generate
 */

public class PlmBaseLevelEntity_HI_RO {
	public static String query = "SELECT\r\n"
			+ "levels.LEVEL_ID AS levelId,\r\n"
			+ "levels.LEVEL_NAME AS levelName,\r\n"
			+ "levels.LEVEL_DESC AS levelDesc,\r\n"
			+ "levels.LEVEL_NUMBER AS levelNumber,\r\n"
			+ "levels.VERSION_NUM AS versionNum,\r\n"
			+ "levels.CREATION_DATE AS creationDate,\r\n"
			+ "levels.CREATED_BY AS createdBy,\r\n"
			+ "levels.LAST_UPDATED_BY AS lastUpdatedBy,\r\n"
			+ "levels.LAST_UPDATE_DATE AS lastUpdateDate,\r\n"
			+ "levels.LAST_UPDATE_LOGIN AS lastUpdateLogin,\r\n"
			+ "levels.PARENT_LEVEL_ID AS parentLevelId,\r\n"
			+ "levels.PARENT_LEVEL_NAME AS parentLevelName,\r\n"
			+ "levels.CREATEDSTR AS createdstr\r\n"
			+ " from PLM_BASE_LEVEL levels where 1=1 ";
	private Integer levelId;
	private String levelName;
	private String levelDesc;
	private String levelNumber;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private Integer parentLevelId;
	private String parentLevelName;
	private String createdstr;

	public String getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

	public String getParentLevelName() {
		return parentLevelName;
	}

	public void setParentLevelName(String parentLevelName) {
		this.parentLevelName = parentLevelName;
	}

	public Integer getParentLevelId() {
		return parentLevelId;
	}

	public void setParentLevelId(Integer parentLevelId) {
		this.parentLevelId = parentLevelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}

	public String getLevelDesc() {
		return levelDesc;
	}

	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getLevelNumber() {
		return levelNumber;
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
