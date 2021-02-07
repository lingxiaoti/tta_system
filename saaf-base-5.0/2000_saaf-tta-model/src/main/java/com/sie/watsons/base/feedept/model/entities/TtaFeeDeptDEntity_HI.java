package com.sie.watsons.base.feedept.model.entities;

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
 * TtaFeeDeptDEntity_HI Entity Object
 * Wed May 29 11:24:38 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_FEE_DEPT_DETAIL")
public class TtaFeeDeptDEntity_HI {
    private Integer feedeptDetailId;
    private Integer feedeptId;
    private Integer feedeptLineId;
    private String deptCode;
    private String deptDesc;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String unit;
    private Integer standardValue;
    private String remark;
    private Integer operatorUserId;

	public void setFeedeptDetailId(Integer feedeptDetailId) {
		this.feedeptDetailId = feedeptDetailId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_FEE_DEPT_DETAIL", sequenceName = "SEQ_TTA_FEE_DEPT_DETAIL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_FEE_DEPT_DETAIL", strategy = GenerationType.SEQUENCE)
	@Column(name="feedept_detail_id", nullable=false, length=22)	
	public Integer getFeedeptDetailId() {
		return feedeptDetailId;
	}

	public void setFeedeptId(Integer feedeptId) {
		this.feedeptId = feedeptId;
	}

	@Column(name="feedept_id", nullable=false, length=22)	
	public Integer getFeedeptId() {
		return feedeptId;
	}

	public void setFeedeptLineId(Integer feedeptLineId) {
		this.feedeptLineId = feedeptLineId;
	}

	@Column(name="feedept_line_id", nullable=false, length=22)	
	public Integer getFeedeptLineId() {
		return feedeptLineId;
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

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
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

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=100)	
	public String getUnit() {
		return unit;
	}

	public void setStandardValue(Integer standardValue) {
		this.standardValue = standardValue;
	}

	@Column(name="standard_value", nullable=true, length=22)	
	public Integer getStandardValue() {
		return standardValue;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=1500)	
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
}
