package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmBaseClassEntity_HI_RO Entity Object Thu Nov 14 14:41:18 CST 2019 Auto
 * Generate
 */

public class PlmBaseClassEntity_HI_RO {
	public static String query = "SELECT\r\n"
			+ "class.CLASS_ID AS classId,\r\n"
			+ "class.CLASS_NAME AS className,\r\n"
			+ "class.CLASS_DESC AS classDesc,\r\n"
			+ "class.CLASS_LEVEL1 AS classLevel1,\r\n"
			+ "class.CLASS_LEVEL2 AS classLevel2,\r\n"
			+ "class.CLASS_LEVEL3 AS classLevel3,\r\n"
			+ "class.CLASS_LEVEL4 AS classLevel4,\r\n"
			+ "class.CLASS_LEVEL5 AS classLevel5,\r\n"
			+ "class.CLASS_LEVELID1 AS classLevelid1,\r\n"
			+ "class.CLASS_LEVELID2 AS classLevelid2,\r\n"
			+ "class.CLASS_LEVELID3 AS classLevelid3,\r\n"
			+ "class.CLASS_LEVELID4 AS classLevelid4,\r\n"
			+ "class.CLASS_LEVELID5 AS classLevelid5,\r\n"
			+ "class.VERSION_NUM AS versionNum,\r\n"
			+ "class.CREATION_DATE AS creationDate,\r\n"
			+ "class.CREATED_BY AS createdBy,\r\n"
			+ "class.LAST_UPDATED_BY AS lastUpdatedBy,\r\n"
			+ "class.LAST_UPDATE_DATE AS lastUpdateDate,\r\n"
			+ "class.LAST_UPDATE_LOGIN AS lastUpdateLogin\r\n" + "FROM\r\n"
			+ "PLM_BASE_CLASS class where 1=1";
	private Integer classId;
	private String className;
	private String classDesc;
	private String classLevel1;

	private String classLevel2;
	private String classLevel3;
	private String classLevel4;
	private String classLevel5;

	private String classLevelid1;
	private String classLevelid2;
	private String classLevelid3;
	private String classLevelid4;
	private String classLevelid5;

	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public String getClassLevel1() {
		return classLevel1;
	}

	public void setClassLevel1(String classLevel1) {
		this.classLevel1 = classLevel1;
	}

	public String getClassLevel2() {
		return classLevel2;
	}

	public void setClassLevel2(String classLevel2) {
		this.classLevel2 = classLevel2;
	}

	public String getClassLevel3() {
		return classLevel3;
	}

	public void setClassLevel3(String classLevel3) {
		this.classLevel3 = classLevel3;
	}

	public String getClassLevel4() {
		return classLevel4;
	}

	public void setClassLevel4(String classLevel4) {
		this.classLevel4 = classLevel4;
	}

	public String getClassLevel5() {
		return classLevel5;
	}

	public void setClassLevel5(String classLevel5) {
		this.classLevel5 = classLevel5;
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

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	public String getClassLevelid1() {
		return classLevelid1;
	}

	public void setClassLevelid1(String classLevelid1) {
		this.classLevelid1 = classLevelid1;
	}

	public String getClassLevelid2() {
		return classLevelid2;
	}

	public void setClassLevelid2(String classLevelid2) {
		this.classLevelid2 = classLevelid2;
	}

	public String getClassLevelid3() {
		return classLevelid3;
	}

	public void setClassLevelid3(String classLevelid3) {
		this.classLevelid3 = classLevelid3;
	}

	public String getClassLevelid4() {
		return classLevelid4;
	}

	public void setClassLevelid4(String classLevelid4) {
		this.classLevelid4 = classLevelid4;
	}

	public String getClassLevelid5() {
		return classLevelid5;
	}

	public void setClassLevelid5(String classLevelid5) {
		this.classLevelid5 = classLevelid5;
	}

}
