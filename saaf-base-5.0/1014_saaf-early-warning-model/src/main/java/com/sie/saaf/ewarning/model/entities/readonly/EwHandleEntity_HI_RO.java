package com.sie.saaf.ewarning.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * EwHandleEntity_HI_RO Entity Object
 * Thu Apr 18 14:20:49 CST 2019  Auto Generate
 */

public class EwHandleEntity_HI_RO {
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

	
	public Integer getHandleId() {
		return handleId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	
	public Integer getBatchId() {
		return batchId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	
	public Integer getCityId() {
		return cityId;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEwLevel(String ewLevel) {
		this.ewLevel = ewLevel;
	}

	
	public String getEwLevel() {
		return ewLevel;
	}

	public void setEwValve(BigDecimal ewValve) {
		this.ewValve = ewValve;
	}

	
	public BigDecimal getEwValve() {
		return ewValve;
	}

	public void setHouseholds(Integer households) {
		this.households = households;
	}

	
	public Integer getHouseholds() {
		return households;
	}

	public void setInvolvementDate(Date involvementDate) {
		this.involvementDate = involvementDate;
	}

	
	public Date getInvolvementDate() {
		return involvementDate;
	}

	public void setInvolvementHouseholds(Integer involvementHouseholds) {
		this.involvementHouseholds = involvementHouseholds;
	}

	
	public Integer getInvolvementHouseholds() {
		return involvementHouseholds;
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

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	
	public String getProductType() {
		return productType;
	}

	public void setProgressDeviation(BigDecimal progressDeviation) {
		this.progressDeviation = progressDeviation;
	}

	
	public BigDecimal getProgressDeviation() {
		return progressDeviation;
	}

	public void setProgressRatio(BigDecimal progressRatio) {
		this.progressRatio = progressRatio;
	}

	
	public BigDecimal getProgressRatio() {
		return progressRatio;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setSecurityDay(Integer securityDay) {
		this.securityDay = securityDay;
	}

	
	public Integer getSecurityDay() {
		return securityDay;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	
	public String getProjectStatus() {
		return projectStatus;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
