package com.sie.saaf.schedule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "schedule_job_parameters")
public class ScheduleJobParametersEntity_HI {

    public ScheduleJobParametersEntity_HI() {
        super();
    }

    private Integer paramId;
    private Integer jobId;
    private Integer paramSeqNum;
    private String paramName;
    private String paramType;
    private String description;
    private String isRequired;
    private String isEnabled;
    private String defaultValue;
    private String paramRegion;

    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_SCHEDULE_JOB_PARAMETERS", sequenceName = "SEQ_SCHEDULE_JOB_PARAMETERS", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_SCHEDULE_JOB_PARAMETERS", strategy = GenerationType.SEQUENCE)
    @Column(name = "PARAM_ID", nullable = false, length = 10)
    public Integer getParamId() {
        return paramId;
    }

    @Column(name = "JOB_ID", nullable = false, length = 10)
    public Integer getJobId() {
        return jobId;
    }

    @Column(name = "PARAM_SEQ_NUM", nullable = false, length = 10)
    public Integer getParamSeqNum() {
        return paramSeqNum;
    }

    @Column(name = "PARAM_NAME", nullable = false, length = 500)
    public String getParamName() {
        return paramName;
    }

    @Column(name = "PARAM_TYPE", nullable = false, length = 50)
    public String getParamType() {
        return paramType;
    }

    @Column(name = "DESCRIPTION", nullable = true, length = 4000)
    public String getDescription() {
        return description;
    }

    @Column(name = "IS_REQUIRED", nullable = true, length = 1)
    public String getIsRequired() {
        return isRequired;
    }

    @Column(name = "IS_ENABLED", nullable = true, length = 1)
    public String getIsEnabled() {
        return isEnabled;
    }

    @Column(name = "DEFAULT_VALUE", nullable = true, length = 4000)
    public String getDefaultValue() {
        return defaultValue;
    }

    @Column(name = "PARAM_REGION", nullable = true, length = 10)
    public String getParamRegion() {
        return paramRegion;
    }

    @Column(name = "CREATED_BY", nullable = true, length = 10)
    public Integer getCreatedBy() {
        return createdBy;
    }

    @Column(name = "LAST_UPDATED_BY", nullable = true, length = 10)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    @Column(name = "LAST_UPDATE_DATE", nullable = true, length = 0)
    public java.util.Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Column(name = "LAST_UPDATE_LOGIN", nullable = true, length = 10)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    @Column(name = "CREATION_DATE", nullable = true, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setParamSeqNum(Integer paramSeqNum) {
        this.paramSeqNum = paramSeqNum;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setParamRegion(String paramRegion) {
        this.paramRegion = paramRegion;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

}
