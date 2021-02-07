package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmDataAclLineEntity_HI_RO Entity Object Tue Mar 17 10:47:45 CST 2020 Auto
 * Generate
 */

public class PlmDataAclLineEntity_HI_RO {
	public static String query=" select head_id as headId,line_id as lineId,acl_type as " +
			" aclType,group_code as groupCode,main_class as mainClass,sub_class as subClass," +
			" enable_flag as enableFlag,created_by as createdBy,last_updated_by as lastUpdatedBy," +
			" creation_date as creationDate,last_update_login as lastUpdateLogin,last_update_date as " +
			" lastUpdateDate,version_num as versionNum from plm_data_acl_line  where 1=1 ";
	private Integer headId;
	private Integer lineId;
	private String aclType;
	private String groupCode;
	private String mainClass;
	private String subClass;
	private String enableFlag;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer lastUpdateLogin;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer versionNum;
	private Integer operatorUserId;
	private String aclTypeName;

	public String getAclTypeName() {
		return aclTypeName;
	}

	public void setAclTypeName(String aclTypeName) {
		this.aclTypeName = aclTypeName;
	}

	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	public Integer getHeadId() {
		return headId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setAclType(String aclType) {
		this.aclType = aclType;
	}

	public String getAclType() {
		return aclType;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getEnableFlag() {
		return enableFlag;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
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
}
