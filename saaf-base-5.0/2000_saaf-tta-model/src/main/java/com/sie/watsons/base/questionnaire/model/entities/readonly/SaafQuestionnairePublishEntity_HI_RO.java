package com.sie.watsons.base.questionnaire.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * SaafQuestionnairePublishEntity_HI_RO Entity Object
 * Mon Nov 12 09:28:34 CST 2018  Auto Generate
 */

public class SaafQuestionnairePublishEntity_HI_RO {
    private Integer publishId; //表ID，主键，供其他表做外键
    private Integer qnHeadId; //调查问卷头ID
    private String publishCode; //发布编号
    private String qnType; //问卷类型
    private String orgsId; //组织(多选)
    private String usersId; //用户(多选)
    private String status; //状态
    private String flowStatus; //审批状态
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDateActive; //起始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDateActive; //终止日期
    private String processInstanceId; //流程标识
    private String description; //说明、备注
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setPublishId(Integer publishId) {
		this.publishId = publishId;
	}

	
	public Integer getPublishId() {
		return publishId;
	}

	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}

	
	public Integer getQnHeadId() {
		return qnHeadId;
	}

	public void setPublishCode(String publishCode) {
		this.publishCode = publishCode;
	}

	
	public String getPublishCode() {
		return publishCode;
	}

	public void setQnType(String qnType) {
		this.qnType = qnType;
	}

	
	public String getQnType() {
		return qnType;
	}

	public void setOrgsId(String orgsId) {
		this.orgsId = orgsId;
	}

	
	public String getOrgsId() {
		return orgsId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	
	public String getUsersId() {
		return usersId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	
	public String getFlowStatus() {
		return flowStatus;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
