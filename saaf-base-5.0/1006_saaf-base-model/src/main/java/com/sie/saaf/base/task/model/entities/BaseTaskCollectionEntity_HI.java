package com.sie.saaf.base.task.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseTaskCollectionEntity_HI Entity Object
 * Fri Jan 26 09:46:41 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_task_collection")
public class BaseTaskCollectionEntity_HI {
    private Integer taskCollectionId;
    private String taskId;//待办Id，各业务系统待办的主键
    private String instanceId;//流程实例Id，各业务系统的流程实例主键
    private String businessKeyId;//业务主键id，各业务系统的业务主键
    private String taskTitle;//待办名
    private String systemCode;//系统编码
    private String systemName;//系统名
    private String status;//待办状态 pending/finished
    private String handlers;//处理人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private Integer versionNum;
    private String functionUrl;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setTaskCollectionId(Integer taskCollectionId) {
		this.taskCollectionId = taskCollectionId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_TASK_COLLECTION", sequenceName = "SEQ_BASE_TASK_COLLECTION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_TASK_COLLECTION", strategy = GenerationType.SEQUENCE)	
	@Column(name = "task_collection_id", nullable = false, length = 11)	
	public Integer getTaskCollectionId() {
		return taskCollectionId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "task_id", nullable = true, length = 64)	
	public String getTaskId() {
		return taskId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "instance_id", nullable = true, length = 64)	
	public String getInstanceId() {
		return instanceId;
	}

	public void setBusinessKeyId(String businessKeyId) {
		this.businessKeyId = businessKeyId;
	}

	@Column(name = "business_key_id", nullable = true, length = 64)	
	public String getBusinessKeyId() {
		return businessKeyId;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	@Column(name = "task_title", nullable = true, length = 200)	
	public String getTaskTitle() {
		return taskTitle;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "system_code", nullable = true, length = 64)	
	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@Column(name = "system_name", nullable = true, length = 200)	
	public String getSystemName() {
		return systemName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status", nullable = true, length = 10)	
	public String getStatus() {
		return status;
	}

	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}

	@Column(name = "handler", nullable = true, length = 64)	
	public String getHandlers() {
		return handlers;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_date", nullable = true, length = 0)	
	public Date getCreateDate() {
		return createDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	@Column(name = "function_url", nullable = true, length = 200)	
	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
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
