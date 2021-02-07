package com.sie.watsons.base.clauseitem.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTermsFrameHeaderHEntity_HI Entity Object
 * Sun Jun 02 13:49:44 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_TERMS_FRAME_HEADER_H")
public class TtaTermsFrameHeaderHEntity_HI {
    private Integer teamFrameworkId;
    private String frameCode;
    private Integer year;
    private String frameName;
    private String billStatus;
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
	@Id
	@SequenceGenerator(name="SEQ_TTA_TERMS_FRAME_HEADER_H", sequenceName="SEQ_TTA_TERMS_FRAME_HEADER_H", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_TERMS_FRAME_HEADER_H",strategy=GenerationType.SEQUENCE)
	@Column(name="team_framework_id", nullable=false, length=22)	
	public Integer getTeamFrameworkId() {
		return teamFrameworkId;
	}

	public void setFrameCode(String frameCode) {
		this.frameCode = frameCode;
	}

	@Column(name="frame_code", nullable=false, length=50)	
	public String getFrameCode() {
		return frameCode;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name="year", nullable=false, length=22)	
	public Integer getYear() {
		return year;
	}

	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}

	@Column(name="frame_name", nullable=true, length=50)	
	public String getFrameName() {
		return frameName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name="bill_status", nullable=false, length=20)	
	public String getBillStatus() {
		return billStatus;
	}

	public void setBusinessVersion(String businessVersion) {
		this.businessVersion = businessVersion;
	}

	@Column(name="business_version", nullable=false, length=20)	
	public String getBusinessVersion() {
		return businessVersion;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	@Column(name="pass_date", nullable=true, length=7)	
	public Date getPassDate() {
		return passDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name="effective_date", nullable=true, length=7)	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name="expiration_date", nullable=true, length=7)	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setResouceId(Integer resouceId) {
		this.resouceId = resouceId;
	}

	@Column(name="resouce_id", nullable=true, length=22)	
	public Integer getResouceId() {
		return resouceId;
	}

	public void setResouceBusinessVersion(String resouceBusinessVersion) {
		this.resouceBusinessVersion = resouceBusinessVersion;
	}

	@Column(name="resouce_business_version", nullable=true, length=20)	
	public String getResouceBusinessVersion() {
		return resouceBusinessVersion;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="dept_id", nullable=false, length=22)
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Column(name="dept_code", nullable=false, length=50)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_name", nullable=false, length=200)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
