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

import javax.persistence.Transient;

/**
 * SaafJobAccessOrgsEntity_HI Entity Object
 * Wed Oct 25 15:07:59 CST 2017  Auto Generate
 */
@Entity
@Table(name = "schedule_job_access_orgs")
public class ScheduleJobAccessOrgsEntity_HI {
    private Integer accessOrgId;
    private Integer jobId;
    private Integer orgId;
    private String platformCode;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setAccessOrgId(Integer accessOrgId) {
		this.accessOrgId = accessOrgId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_SCHEDULE_JOB_ACCESS_ORGS", sequenceName = "SEQ_SCHEDULE_JOB_ACCESS_ORGS", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_SCHEDULE_JOB_ACCESS_ORGS", strategy = GenerationType.SEQUENCE)	
	@Column(name = "access_org_id", nullable = false, length = 22)	
	public Integer getAccessOrgId() {
		return accessOrgId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Column(name = "job_id", nullable = true, length = 22)
	public Integer getJobId() {
		return jobId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 22)
	public Integer getOrgId() {
		return orgId;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	@Column(name = "platform_code", nullable = true, length = 30)	
	public String getPlatformCode() {
		return platformCode;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "start_date", nullable = true, length = 0)	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "end_date", nullable = true, length = 0)	
	public Date getEndDate() {
		return endDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
