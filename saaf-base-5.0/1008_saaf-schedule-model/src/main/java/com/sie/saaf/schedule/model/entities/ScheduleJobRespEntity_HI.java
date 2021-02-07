package com.sie.saaf.schedule.model.entities;

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
 * SaafJobRespEntity_HI Entity Object
 * Wed Oct 25 15:08:04 CST 2017  Auto Generate
 */
@Entity
@Table(name = "schedule_job_resp")
public class ScheduleJobRespEntity_HI {
    private Integer jobRespId;
    private Integer jobId;
    private String jobRespName;
    private Integer responsibilityId;
    private String platformCode;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDateActive;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDateActive;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setJobRespId(Integer jobRespId) {
		this.jobRespId = jobRespId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_SCHEDULE_JOB_RESP", sequenceName = "SEQ_SCHEDULE_JOB_RESP", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_SCHEDULE_JOB_RESP", strategy = GenerationType.SEQUENCE)
	@Column(name = "job_resp_id", nullable = false, length = 22)	
	public Integer getJobRespId() {
		return jobRespId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Column(name = "job_id", nullable = false, length = 22)	
	public Integer getJobId() {
		return jobId;
	}

	public void setJobRespName(String jobRespName) {
		this.jobRespName = jobRespName;
	}

	@Column(name = "job_resp_name", nullable = true, length = 100)	
	public String getJobRespName() {
		return jobRespName;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	@Column(name = "responsibility_id", nullable = false, length = 22)	
	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	@Column(name = "platform_code", nullable = false, length = 30)	
	public String getPlatformCode() {
		return platformCode;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name = "start_date_active", nullable = true, length = 0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name = "end_date_active", nullable = true, length = 0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)	
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

	@Column(name = "last_update_login", nullable = true, length = 22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
