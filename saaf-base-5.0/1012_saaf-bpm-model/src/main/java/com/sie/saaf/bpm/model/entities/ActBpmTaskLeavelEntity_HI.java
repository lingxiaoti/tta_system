package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 */
@Entity
@Table(name = "act_bpm_task_leavel")
public class ActBpmTaskLeavelEntity_HI{

	// Fields
	private String taskId;
	private String parentTaskId;
	private String topTaskId;
	private String topProcessInstanceId;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer deleteFlag;
	private Integer operatorUserId;
	
	/** default constructor */
	public ActBpmTaskLeavelEntity_HI() {
	}

	
	@Id
    @Column(name = "task_id", nullable = false, length = 64)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "parent_task_id", length = 64)
	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	@Column(name = "top_task_id", nullable = false, length = 64)
	public String getTopTaskId() {
		return topTaskId;
	}

	public void setTopTaskId(String topTaskId) {
		this.topTaskId = topTaskId;
	}
	
	@Column(name = "top_proc_inst_id", nullable = false, length = 64)
	public String getTopProcessInstanceId() {
		return topProcessInstanceId;
	}

	public void setTopProcessInstanceId(String topProcessInstanceId) {
		this.topProcessInstanceId = topProcessInstanceId;
	}
	
	@Column(name = "created_by", nullable = true, length = 11)
	public Integer getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "creation_date", nullable = false, length = 0)
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}
	

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}
	
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	
	@Column(name="delete_flag",columnDefinition="bit default 0")
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

}