package com.sie.saaf.schedule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

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
@Table(name = "schedule_jobs")
public class ScheduleJobsEntity_HI {
    public ScheduleJobsEntity_HI() {
        super();
    }

    private Integer jobId;
    private String jobName;
    private String description;
    private String executableName;
    private String method;
    private String outputFileType;
    private String system;
    private String module;
    private String jobType;
    private String singleInstance;//限制单例运行

    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_SCHEDULE_JOBS", sequenceName = "SEQ_SCHEDULE_JOBS", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_SCHEDULE_JOBS", strategy = GenerationType.SEQUENCE)
    @Column(name = "JOB_ID", nullable = false, length = 10)
    public Integer getJobId() {
        return jobId;
    }

    @Column(name = "JOB_NAME", nullable = false, length = 480)
    public String getJobName() {
        return jobName;
    }

    @Column(name = "DESCRIPTION", nullable = true, length = 4000)
    public String getDescription() {
        return description;
    }

    @Column(name = "EXECUTABLE_NAME", nullable = false, length = 500)
    public String getExecutableName() {
        return executableName;
    }

    @Column(name = "METHOD", nullable = true, length = 240)
    public String getMethod() {
        return method;
    }

    @Column(name = "OUTPUT_FILE_TYPE", nullable = true, length = 100)
    public String getOutputFileType() {
        return outputFileType;
    }

    @Column(name = "SYSTEM", nullable = true, length = 240)
    public String getSystem() {
        return system;
    }

    @Column(name = "MODULE", nullable = true, length = 240)
    public String getModule() {
        return module;
    }

    @Column(name = "JOB_TYPE", nullable = false, length = 120)
    public String getJobType() {
        return jobType;
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

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setOutputFileType(String outputFileType) {
        this.outputFileType = outputFileType;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
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

    @Column(name = "SINGLE_INSTANCE", nullable = false, length = 1)
    public String getSingleInstance() {
        return singleInstance;
    }

    public void setSingleInstance(String singleInstance) {
        this.singleInstance = singleInstance;
    }
}
