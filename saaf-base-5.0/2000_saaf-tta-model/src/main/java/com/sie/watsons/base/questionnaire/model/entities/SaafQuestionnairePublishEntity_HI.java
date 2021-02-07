package com.sie.watsons.base.questionnaire.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * SaafQuestionnairePublishEntity_HI Entity Object
 * Mon Nov 12 09:28:34 CST 2018  Auto Generate
 */
@Entity
@Table(name="tta_questionnaire_publish")
public class SaafQuestionnairePublishEntity_HI {
    private Integer publishId; //表ID，主键，供其他表做外键
    private Integer testQnHeadId; //调查问卷头ID
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


	@Id
	@SequenceGenerator(name = "SEQ_TTA_QUESTIONNAIRE_PUBLISH", sequenceName = "SEQ_TTA_QUESTIONNAIRE_PUBLISH", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_QUESTIONNAIRE_PUBLISH", strategy = GenerationType.SEQUENCE)
	@Column(name="publish_id", nullable=false, length=11)	
	public Integer getPublishId() {
		return publishId;
	}

	public void setTestQnHeadId(Integer testQnHeadId) {
		this.testQnHeadId = testQnHeadId;
	}

	@Column(name="test_qn_head_id", nullable=true, length=11)
	public Integer getTestQnHeadId() {
		return testQnHeadId;
	}

	public void setPublishCode(String publishCode) {
		this.publishCode = publishCode;
	}

	@Column(name="publish_code", nullable=true, length=100)	
	public String getPublishCode() {
		return publishCode;
	}

	public void setQnType(String qnType) {
		this.qnType = qnType;
	}

	@Column(name="qn_type", nullable=true, length=100)	
	public String getQnType() {
		return qnType;
	}

	public void setOrgsId(String orgsId) {
		this.orgsId = orgsId;
	}

	@Column(name="orgs_id", nullable=true, length=500)	
	public String getOrgsId() {
		return orgsId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	@Column(name="users_id", nullable=true, length=2000)	
	public String getUsersId() {
		return usersId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=100)	
	public String getStatus() {
		return status;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	@Column(name="flow_status", nullable=true, length=100)	
	public String getFlowStatus() {
		return flowStatus;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name="start_date_active", nullable=true, length=0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name="end_date_active", nullable=true, length=0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Column(name="process_instance_id", nullable=true, length=100)	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=500)	
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
