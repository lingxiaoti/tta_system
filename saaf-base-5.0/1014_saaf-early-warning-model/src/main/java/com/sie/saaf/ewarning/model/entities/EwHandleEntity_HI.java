package com.sie.saaf.ewarning.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * EwHandleEntity_HI Entity Object
 * Thu Apr 18 14:20:49 CST 2019  Auto Generate
 */
@Entity
@Table(name="ew_handle")
public class EwHandleEntity_HI {
    private Integer handleId;
    private Integer batchId;
    private Integer cityId;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private String description;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDateActive;
    private String ewLevel;
    private BigDecimal ewValve;
    private Integer households;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date involvementDate;
    private Integer involvementHouseholds;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private String productType;
    private BigDecimal progressDeviation;
    private BigDecimal progressRatio;
    private Integer projectId;
    private Integer securityDay;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDateActive;
    private Integer versionNum;
    private String approveStatus; //审批状态
    private String projectStatus; //项目批次完工状态
    private String remark; //备注
    private String processInstanceId; //流程ID
    private Integer operatorUserId;

	public void setHandleId(Integer handleId) {
		this.handleId = handleId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_EW_HANDLE", sequenceName = "SEQ_EW_HANDLE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_EW_HANDLE", strategy = GenerationType.SEQUENCE)	
	@Column(name="handle_id", nullable=false, length=11)	
	public Integer getHandleId() {
		return handleId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	@Column(name="batch_id", nullable=true, length=11)	
	public Integer getBatchId() {
		return batchId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	@Column(name="city_id", nullable=true, length=11)	
	public Integer getCityId() {
		return cityId;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=0)	
	public String getDescription() {
		return description;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name="end_date_active", nullable=true, length=0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEwLevel(String ewLevel) {
		this.ewLevel = ewLevel;
	}

	@Column(name="ew_level", nullable=true, length=20)	
	public String getEwLevel() {
		return ewLevel;
	}

	public void setEwValve(BigDecimal ewValve) {
		this.ewValve = ewValve;
	}

	@Column(name="ew_valve", precision=20, scale=2)	
	public BigDecimal getEwValve() {
		return ewValve;
	}

	public void setHouseholds(Integer households) {
		this.households = households;
	}

	@Column(name="households", nullable=true, length=11)	
	public Integer getHouseholds() {
		return households;
	}

	public void setInvolvementDate(Date involvementDate) {
		this.involvementDate = involvementDate;
	}

	@Column(name="involvement_date", nullable=true, length=0)	
	public Date getInvolvementDate() {
		return involvementDate;
	}

	public void setInvolvementHouseholds(Integer involvementHouseholds) {
		this.involvementHouseholds = involvementHouseholds;
	}

	@Column(name="involvement_households", nullable=true, length=11)	
	public Integer getInvolvementHouseholds() {
		return involvementHouseholds;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name="product_type", nullable=true, length=100)	
	public String getProductType() {
		return productType;
	}

	public void setProgressDeviation(BigDecimal progressDeviation) {
		this.progressDeviation = progressDeviation;
	}

	@Column(name="progress_deviation", precision=20, scale=2)	
	public BigDecimal getProgressDeviation() {
		return progressDeviation;
	}

	public void setProgressRatio(BigDecimal progressRatio) {
		this.progressRatio = progressRatio;
	}

	@Column(name="progress_ratio", precision=20, scale=2)	
	public BigDecimal getProgressRatio() {
		return progressRatio;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=true, length=11)	
	public Integer getProjectId() {
		return projectId;
	}

	public void setSecurityDay(Integer securityDay) {
		this.securityDay = securityDay;
	}

	@Column(name="security_day", nullable=true, length=11)	
	public Integer getSecurityDay() {
		return securityDay;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name="start_date_active", nullable=true, length=0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	@Column(name="approve_status", nullable=true, length=20)	
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	@Column(name="project_status", nullable=true, length=20)	
	public String getProjectStatus() {
		return projectStatus;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=500)	
	public String getRemark() {
		return remark;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Column(name="process_instance_id", nullable=true, length=50)	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
