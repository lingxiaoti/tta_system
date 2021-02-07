package com.sie.watsons.base.feedept.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFeeDeptHEntity_HI Entity Object
 * Wed May 29 11:24:37 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_FEE_DEPT_HEADER")
public class TtaFeeDeptHEntity_HI {
    private Integer feedeptId;
    private String yearCode;
    private String accordType;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
    private Integer sourceFeeId;
    private String isAlert;
    private String versionCode;
    private String remark;
	private String deptCode;
	private String deptDesc;
	private Integer deptId;
    private Integer operatorUserId;

	public void setFeedeptId(Integer feedeptId) {
		this.feedeptId = feedeptId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_FEE_DEPT_HEADER", sequenceName = "SEQ_TTA_FEE_DEPT_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_FEE_DEPT_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="feedept_id", nullable=false, length=22)	
	public Integer getFeedeptId() {
		return feedeptId;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	@Column(name="year_code", nullable=false, length=50)	
	public String getYearCode() {
		return yearCode;
	}

	public void setAccordType(String accordType) {
		this.accordType = accordType;
	}

	@Column(name="accord_type")
	public String getAccordType() {
		return accordType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=10)	
	public String getStatus() {
		return status;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	@Column(name="last_update_date")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login")
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num")
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name="approve_date")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setSourceFeeId(Integer sourceFeeId) {
		this.sourceFeeId = sourceFeeId;
	}

	@Column(name="source_fee_id")
	public Integer getSourceFeeId() {
		return sourceFeeId;
	}

	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}

	@Column(name="is_alert")
	public String getIsAlert() {
		return isAlert;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	@Column(name="version_code")
	public String getVersionCode() {
		return versionCode;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}




	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code",nullable=false, length=50)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc",nullable=false, length=200)
	public String getDeptDesc() {
		return deptDesc;
	}

	@Column(name="dept_id", nullable=false, length=22)
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
}
