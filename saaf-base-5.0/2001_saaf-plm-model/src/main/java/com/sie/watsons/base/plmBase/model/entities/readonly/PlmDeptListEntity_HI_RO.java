package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmDeptListEntity_HI_RO Entity Object Wed Oct 23 15:32:20 CST 2019 Auto
 * Generate
 */

public class PlmDeptListEntity_HI_RO {
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer plmDeptListId;
	private String plmGroupCode;
	private String plmGroupDesc;
	private String plmDeptCode;
	private String plmDeptName;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	private Integer operatorUserId;

	public static final String QUERY_SQL = "SELECT pdl.PLM_DEPT_LIST_ID  as plmDeptListId,\n"
			+ "       pdl.PLM_GROUP_CODE    as plmGroupCode,\n"
			+ "       pdl.PLM_GROUP_DESC    as plmGroupDesc,\n"
			+ "       pdl.PLM_DEPT_CODE     as plmDeptCode,\n"
			+ "       pdl.PLM_DEPT_NAME     as plmDeptName,\n"
			+ "       pdl.CREATED_BY        as createdBy,\n"
			+ "       pdl.LAST_UPDATED_BY   as lastUpdatedBy,\n"
			+ "       pdl.LAST_UPDATE_DATE  as lastUpdateDate,\n"
			+ "       pdl.LAST_UPDATE_LOGIN as lastUpdateLogin,\n"
			+ "       pdl.VERSION_NUM       as versionNum,\n"
			+ "       pdl.CREATION_DATE     as creationDate\n"
			+ " FROM PLM_DEPT_LIST pdl\n" + " WHERE 1 = 1 ";

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

	public void setPlmDeptListId(Integer plmDeptListId) {
		this.plmDeptListId = plmDeptListId;
	}

	public Integer getPlmDeptListId() {
		return plmDeptListId;
	}

	public void setPlmGroupCode(String plmGroupCode) {
		this.plmGroupCode = plmGroupCode;
	}

	public String getPlmGroupCode() {
		return plmGroupCode;
	}

	public void setPlmGroupDesc(String plmGroupDesc) {
		this.plmGroupDesc = plmGroupDesc;
	}

	public String getPlmGroupDesc() {
		return plmGroupDesc;
	}

	public void setPlmDeptCode(String plmDeptCode) {
		this.plmDeptCode = plmDeptCode;
	}

	public String getPlmDeptCode() {
		return plmDeptCode;
	}

	public void setPlmDeptName(String plmDeptName) {
		this.plmDeptName = plmDeptName;
	}

	public String getPlmDeptName() {
		return plmDeptName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
