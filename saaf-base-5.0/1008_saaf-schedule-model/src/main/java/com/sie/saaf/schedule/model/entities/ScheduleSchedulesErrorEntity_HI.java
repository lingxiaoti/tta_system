package com.sie.saaf.schedule.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Transient;

/**
 * ScheduleSchedulesErrorEntity_HI Entity Object
 * Tue Mar 27 09:20:44 CST 2018  Auto Generate
 */
@Entity
@Table(name = "schedule_schedules_error")
public class ScheduleSchedulesErrorEntity_HI {
    private Integer errorId; //表ID，主键，供其他表做外键
    private Integer scheduleId; //调度ID
    private String scheduleData; //调度执行信息
    private String errorStr; //执行异常信息
    private String status; //处理状态
    private String description; //说明、备注
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_SCHEDULE_SCHEDULES_ERROR", sequenceName = "SEQ_SCHEDULE_SCHEDULES_ERROR", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_SCHEDULE_SCHEDULES_ERROR", strategy = GenerationType.SEQUENCE)	
	@Column(name = "error_id", nullable = false, length = 11)	
	public Integer getErrorId() {
		return errorId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name = "schedule_id", nullable = false, length = 11)	
	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleData(String scheduleData) {
		this.scheduleData = scheduleData;
	}

	@Column(name = "schedule_data", nullable = true, length = 3000)	
	public String getScheduleData() {
		return scheduleData;
	}

	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}

	@Column(name = "error_str", nullable = true, length = 0)	
	public String getErrorStr() {
		return errorStr;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status", nullable = true, length = 30)	
	public String getStatus() {
		return status;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "description", nullable = true, length = 500)	
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "VERSION_NUM", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "CREATION_DATE", nullable = false, length = 0)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 11)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_BY", nullable = false, length = 11)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "LAST_UPDATE_DATE", nullable = false, length = 0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "LAST_UPDATE_LOGIN", nullable = true, length = 11)
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
