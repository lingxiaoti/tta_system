package com.sie.watsons.base.pon.information.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonInformationApprovalConfEntity_HI_RO Entity Object
 * Tue Jun 09 21:20:11 CST 2020  Auto Generate
 */

public class EquPonInformationApprovalConfEntity_HI_RO {
	public  static  final String  QUERY_SQL = "select t.conf_id           confId\n" +
			"      ,t.dept_code         deptCode\n" +
			"      ,t.project_category  projectCategory\n" +
			"      ,t.nodepath          nodepath\n" +
			"      ,t.status            status\n" +
			"      ,t.version_num       versionNum\n" +
			"      ,t.creation_date     creationDate\n" +
			"      ,t.created_by        createdBy\n" +
			"      ,t.last_updated_by   lastUpdatedBy\n" +
			"      ,t.last_update_date  lastUpdateDate\n" +
			"      ,t.last_update_login lastUpdateLogin\n" +
			"from   EQU_PON_INFORMATION_APPROVAL_CONF t\n" +
			"where  1 = 1";

	private Integer confId;
    private String deptCode;
    private String projectCategory;
    private String nodepath;
    private String status;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private Integer operatorUserId;

	public void setConfId(Integer confId) {
		this.confId = confId;
	}

	
	public Integer getConfId() {
		return confId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	
	public String getProjectCategory() {
		return projectCategory;
	}

	public void setNodepath(String nodepath) {
		this.nodepath = nodepath;
	}

	
	public String getNodepath() {
		return nodepath;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	
	public String getAttribute5() {
		return attribute5;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
