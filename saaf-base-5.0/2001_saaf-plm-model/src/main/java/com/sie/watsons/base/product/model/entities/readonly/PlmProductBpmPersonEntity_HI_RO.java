package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductBpmPersonEntity_HI_RO Entity Object Thu Oct 24 15:52:35 CST 2019
 * Auto Generate
 */

public class PlmProductBpmPersonEntity_HI_RO {
	public static final String QUERY_PERSON = "SELECT\r\n"
			+ "person.ID AS id,\r\n" + "person.NAME AS name,\r\n"
			+ "person.USERID_LIST AS useridList,\r\n"
			+ "person.VERSION_NUM AS versionNum,\r\n"
			+ "person.CREATION_DATE AS creationDate,\r\n"
			+ "person.CREATED_BY AS createdBy,\r\n"
			+ "person.LAST_UPDATED_BY AS lastUpdatedBy,\r\n"
			+ "person.LAST_UPDATE_DATE AS lastUpdateDate,\r\n"
			+ "person.LAST_UPDATE_LOGIN AS lastUpdateLogin,\r\n"
			+ "person.USER_TYPE AS userType,\r\n"
			+ "person.DEPT_TYPE AS deptType\r\n" + "FROM\r\n"
			+ "PLM_PRODUCT_BPM_PERSON person where 1=1 ";

	public static final String QUERY_USER_BY_PERSON_ID_SQL = "select "
			+ "	a.user_id, " + "	a.user_name, " + "	a.user_type\n"
			+ " from base_users a\n" + " inner join base_person b\n"
			+ " on a.person_id = b.person_id where a.person_id=:personId ";
	private Integer id;
	private String name;
	private String useridList;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private String userType;
	private Integer operatorUserId;
	private String deptType;

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUseridList(String useridList) {
		this.useridList = useridList;
	}

	public String getUseridList() {
		return useridList;
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

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
