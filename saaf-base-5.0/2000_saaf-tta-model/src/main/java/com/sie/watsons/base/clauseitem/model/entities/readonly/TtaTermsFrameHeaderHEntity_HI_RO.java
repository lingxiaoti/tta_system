package com.sie.watsons.base.clauseitem.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaTermsFrameHeaderHEntity_HI_RO Entity Object
 * Sun Jun 02 13:49:44 CST 2019  Auto Generate
 */

public class TtaTermsFrameHeaderHEntity_HI_RO {
	public static final String QUERY_ATF_H_SQL = "select \n" +
			" atfh.team_framework_id  teamFrameworkId\n" +
			",atfh.frame_code frameCode\n" +
			",atfh.frame_name frameName\n" +
			",atfh.bill_status billStatus\n" +
			",atfh.pass_date passDate\n" +
			",atfh.effective_date  effectiveDate\n" +
			",atfh.expiration_date expirationDate\n" +
			",atfh.created_by\n" +
			",atfh.dept_id deptId\n" +
			",atfh.dept_code deptCode\n" +
			",atfh.dept_name deptName\n" +
			",atfh.year\n" +
			",atfh.business_Version   businessVersion\n" +
			",bu.user_name createdByName\n" +
			",atfh.creation_date creationDate\n" +
			",blv.meaning billStatusName\n" +
			"from tta_terms_frame_header_h atfh,\n" +
			"     base_users bu, \n" +
			"     base_lookup_values blv\n" +
			"where  \n" +
			"    atfh.created_by = bu.user_id \n" +
			"    and atfh.bill_status = blv.lookup_code\n" +
			"    and blv.lookup_type = 'DS_STATUS'\n" +
			"    and blv.system_code = 'BASE' and nvl(atfh.delete_flag,0)=0";

    private Integer teamFrameworkId;
    private String frameCode;
    private Integer year;
    private String frameName;
    private String billStatus;
	private String billStatusName ;
	private String createdByName ;
    private String businessVersion;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date passDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;
    private Integer resouceId;
    private String resouceBusinessVersion;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;
	private Integer deptId;
	private String deptCode;
	private String deptName;

	public void setTeamFrameworkId(Integer teamFrameworkId) {
		this.teamFrameworkId = teamFrameworkId;
	}

	
	public Integer getTeamFrameworkId() {
		return teamFrameworkId;
	}

	public void setFrameCode(String frameCode) {
		this.frameCode = frameCode;
	}

	
	public String getFrameCode() {
		return frameCode;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	
	public Integer getYear() {
		return year;
	}

	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}

	
	public String getFrameName() {
		return frameName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	
	public String getBillStatus() {
		return billStatus;
	}

	public void setBusinessVersion(String businessVersion) {
		this.businessVersion = businessVersion;
	}

	
	public String getBusinessVersion() {
		return businessVersion;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	
	public Date getPassDate() {
		return passDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setResouceId(Integer resouceId) {
		this.resouceId = resouceId;
	}

	
	public Integer getResouceId() {
		return resouceId;
	}

	public void setResouceBusinessVersion(String resouceBusinessVersion) {
		this.resouceBusinessVersion = resouceBusinessVersion;
	}

	
	public String getResouceBusinessVersion() {
		return resouceBusinessVersion;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getBillStatusName() {
		return billStatusName;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
