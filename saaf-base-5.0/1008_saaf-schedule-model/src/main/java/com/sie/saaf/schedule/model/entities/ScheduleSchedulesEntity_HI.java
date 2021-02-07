package com.sie.saaf.schedule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "schedule_schedules")
public class ScheduleSchedulesEntity_HI {

    public ScheduleSchedulesEntity_HI() {
        super();
    }

    private Integer scheduleId;
    private String phaseCode;
    private String statusCode;
    private Date actualStartDate;
    private Date actualCompletionDate;
    private String logFileName;
    private String outputFileName;
    private String outputFileType;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date scheduleExpectStartDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date scheduleExpectEndDate;
    private Integer jobId;
    private String quartzJobName;
    private String cornexpress;
    private Integer priority;
    private Date previousFireTime;
    private Date nextFireTime;
    private String scheduleType;
    private Blob argumentObj;
    private String isDeleted;
    private Integer wasExecutedTotalCount;
    private Integer wasExecutedSuccessCount;
    private Integer wasExecutedFailCount;
    private String argumentsText;
    private Integer failAttemptFrequency;
    private Integer timeout;

    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_SCHEDULE_SCHEDULES", sequenceName = "SEQ_SCHEDULE_SCHEDULES", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_SCHEDULE_SCHEDULES", strategy = GenerationType.SEQUENCE)
    @Column(name = "SCHEDULE_ID", nullable = false, length = 10)
    public Integer getScheduleId() {
        return scheduleId;
    }

    @Column(name = "PHASE_CODE", nullable = true, length = 240)
    public String getPhaseCode() {
        return phaseCode;
    }

    @Column(name = "STATUS_CODE", nullable = true, length = 240)
    public String getStatusCode() {
        return statusCode;
    }

    @Column(name = "ACTUAL_START_DATE", nullable = true, length = 0)
    public Date getActualStartDate() {
        return actualStartDate;
    }

    @Column(name = "ACTUAL_COMPLETION_DATE", nullable = true, length = 0)
    public Date getActualCompletionDate() {
        return actualCompletionDate;
    }

    @Column(name = "LOG_FILE_NAME", nullable = true, length = 240)
    public String getLogFileName() {
        return logFileName;
    }

    @Column(name = "OUTPUT_FILE_NAME", nullable = true, length = 240)
    public String getOutputFileName() {
        return outputFileName;
    }

    @Column(name = "OUTPUT_FILE_TYPE", nullable = true, length = 10)
    public String getOutputFileType() {
        return outputFileType;
    }

    @Column(name = "SCHEDULE_EXPECT_START_DATE", nullable = true, length = 0)
    public Date getScheduleExpectStartDate() {
        return scheduleExpectStartDate;
    }

    @Column(name = "SCHEDULE_EXPECT_END_DATE", nullable = true, length = 0)
    public Date getScheduleExpectEndDate() {
        return scheduleExpectEndDate;
    }

    @Column(name = "JOB_ID", nullable = true, length = 10)
    public Integer getJobId() {
        return jobId;
    }

    @Column(name = "QUARTZ_JOB_NAME", nullable = false, length = 480)
    public String getQuartzJobName() {
        return quartzJobName;
    }

    @Column(name = "CORNEXPRESS", nullable = true, length = 240)
    public String getCornexpress() {
        return cornexpress;
    }

    @Column(name = "PRIORITY", nullable = true, length = 10)
    public Integer getPriority() {
        return priority;
    }

    @Column(name = "PREVIOUS_FIRE_TIME", nullable = true, length = 0)
    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    @Column(name = "NEXT_FIRE_TIME", nullable = true, length = 0)
    public Date getNextFireTime() {
        return nextFireTime;
    }

    @Column(name = "SCHEDULE_TYPE", nullable = true, length = 120)
    public String getScheduleType() {
        return scheduleType;
    }

    @Column(name = "ARGUMENT_OBJ", nullable = true, length = 0)
    public Blob getArgumentObj() {
        return argumentObj;
    }

    @Column(name = "IS_DELETED", nullable = true, length = 1)
    public String getIsDeleted() {
        return isDeleted;
    }

    @Column(name = "WAS_EXECUTED_TOTAL_COUNT", nullable = true, length = 10)
    public Integer getWasExecutedTotalCount() {
        return wasExecutedTotalCount;
    }

    @Column(name = "WAS_EXECUTED_SUCCESS_COUNT", nullable = true, length = 10)
    public Integer getWasExecutedSuccessCount() {
        return wasExecutedSuccessCount;
    }

    @Column(name = "WAS_EXECUTED_FAIL_COUNT", nullable = true, length = 10)
    public Integer getWasExecutedFailCount() {
        return wasExecutedFailCount;
    }

    @Column(name = "ARGUMENTS_TEXT", nullable = true, length = 4000)
    public String getArgumentsText() {
        return argumentsText;
    }

    @Column(name = "FAIL_ATTEMPT_FREQUENCY", nullable = true, length = 10)
    public Integer getFailAttemptFrequency() {
        return failAttemptFrequency;
    }

    @Column(name = "TIMEOUT", nullable = true, length = 10)
    public Integer getTimeout() {
        return timeout;
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
    public Date getLastUpdateDate() {
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

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public void setActualCompletionDate(Date actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setOutputFileType(String outputFileType) {
        this.outputFileType = outputFileType;
    }

    public void setScheduleExpectStartDate(Date scheduleExpectStartDate) {
        this.scheduleExpectStartDate = scheduleExpectStartDate;
    }

    public void setScheduleExpectEndDate(Date scheduleExpectEndDate) {
        this.scheduleExpectEndDate = scheduleExpectEndDate;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setQuartzJobName(String quartzJobName) {
        this.quartzJobName = quartzJobName;
    }

    public void setCornexpress(String cornexpress) {
        this.cornexpress = cornexpress;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public void setArgumentObj(Blob argumentObj) {
        this.argumentObj = argumentObj;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setWasExecutedTotalCount(Integer wasExecutedTotalCount) {
        this.wasExecutedTotalCount = wasExecutedTotalCount;
    }

    public void setWasExecutedSuccessCount(Integer wasExecutedSuccessCount) {
        this.wasExecutedSuccessCount = wasExecutedSuccessCount;
    }

    public void setWasExecutedFailCount(Integer wasExecutedFailCount) {
        this.wasExecutedFailCount = wasExecutedFailCount;
    }

    public void setArgumentsText(String argumentsText) {
        this.argumentsText = argumentsText;
    }

    public void setFailAttemptFrequency(Integer failAttemptFrequency) {
        this.failAttemptFrequency = failAttemptFrequency;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
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
