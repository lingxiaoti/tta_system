package com.sie.saaf.schedule.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ScheduleSchedulesEntity_HI_RO {

    public ScheduleSchedulesEntity_HI_RO() {
        super();
    }
    public static final String QUERY_SQL_SINGLE_INSTANCE_JOB ="SELECT\n" +
            "\tss.SCHEDULE_ID scheduleId\n" +
            "FROM\n" +
            "\tschedule_schedules ss \n" +
            "WHERE\n" +
            "\tss.JOB_ID = :IN_JOB_ID\n" +
            "\tAND ss.PHASE_CODE IN (\n" +
            "\t'JOBADDED_PHASECODE',\n" +
            "\t'JOBSCHEDULED_PHASECODE',\n" +
            "\t'JOBTOBEEXECUTED_PHASECODE',\n" +
            "\t'JOBEXECUTING_PHASECODE',\n" +
            "\t'JOBRESUMED_PHASECODE',\n" +
            "\t'JOBWAITING_PHASECODE',\n" +
            "\t'JOBUNDEFINED_PHASECODE' \n" +
            "\t) ";


    public static final String QUERY_SQL =
            "select ss.SCHEDULE_ID                scheduleId, " +
                    "       ss.PHASE_CODE                 phaseCode, " +
                    "       ss.STATUS_CODE                statusCode, " +
                    "       ss.ACTUAL_START_DATE          actualStartDate, " +
                    "       ss.ACTUAL_COMPLETION_DATE     actualCompletionDate, " +
                    "       ss.LOG_FILE_NAME              logFileName, " +
                    "       ss.SCHEDULE_EXPECT_START_DATE scheduleExpectStartDate, " +
                    "       ss.SCHEDULE_EXPECT_END_DATE   scheduleExpectEndDate, " +
                    "       ss.JOB_ID                     jobId, " +
                    "       ss.QUARTZ_JOB_NAME            quartzJobName, " +
                    "       ss.CORNEXPRESS                cornexpress, " +
                    "       ss.PRIORITY                   priority, " +
                    "       ss.PREVIOUS_FIRE_TIME         previousFireTime, " +
                    "       ss.NEXT_FIRE_TIME             nextFireTime, " +
                    "       ss.SCHEDULE_TYPE              scheduleType, " +
                    "       ss.WAS_EXECUTED_TOTAL_COUNT   wasExecutedTotalCount, " +
                    "       ss.WAS_EXECUTED_SUCCESS_COUNT wasExecutedSuccessCount, " +
                    "       ss.WAS_EXECUTED_FAIL_COUNT    wasExecutedFailCount, " +
                    "       ss.ARGUMENTS_TEXT             argumentsText, " +
                    "       ss.FAIL_ATTEMPT_FREQUENCY     failAttemptFrequency, " +
                    "       ss.TIMEOUT                    timeout, " +
                    "       ss.CREATED_BY                 createdBy, " +
                    "       sj.JOB_NAME                   jobName, " +
                    "       sj.SYSTEM                     system, " +
                    "       sj.MODULE                     module, " +
                    "       su.USER_NAME                  userName, " +
                    "       lv1.MEANING                   phaseCodeMeaning, " +
                    "       lv2.MEANING                   statusCodeMeaning, " +
                    "       lv3.MEANING                   scheduleTypeMeaning, " +
                    "       lv4.MEANING                   systemMeaning, " +
                    "       lv5.MEANING                   moduleMeaning,case when ss.PHASE_CODE='JOBUNSCHEDULED_PHASECODE' or ss.PHASE_CODE='JOBWASEXECUTED_PHASECODE' then 'Y' else 'N' end isAbleDelete " +
                    "  from schedule_schedules ss " +
                    "  left join base_users su " +
                    "    on ss.created_by = su.user_id " +
                    "  left join schedule_jobs sj " +
                    "    on ss.JOB_ID = sj.JOB_ID " +
                    "  left join base_lookup_values lv1 " +
                    "    on lv1.LOOKUP_TYPE = 'CP_PHASE_CODE' " +
                    "   and lv1.LOOKUP_CODE = ss.PHASE_CODE " +
                    "  left join base_lookup_values lv2 " +
                    "    on lv2.LOOKUP_TYPE = 'CP_STATUS_CODE' " +
                    "   and lv2.LOOKUP_CODE = ss.STATUS_CODE " +
                    "  left join base_lookup_values lv3 " +
                    "    on lv3.LOOKUP_TYPE = 'SAAF_SCHE_TYPE' " +
                    "   and lv3.LOOKUP_CODE = ss.SCHEDULE_TYPE " +
                    "  left join base_lookup_values lv4 " +
                    "   on lv4.LOOKUP_TYPE = 'SCHEDULED_SYSTEM' " +
                    "   and lv4.LOOKUP_CODE = sj.SYSTEM " +
                    "  left join base_lookup_values lv5 " +
                    "    on lv5.LOOKUP_TYPE = 'SCHEDULED_SYSTEM_MODULE' " +
                    "   and lv5.LOOKUP_CODE = sj.MODULE " +
                    " where ss.IS_DELETED is null ";

    private Integer scheduleId;
    private String phaseCode;
    private String statusCode;
    private String phaseCodeMeaning;
    private String statusCodeMeaning;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date actualStartDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date actualCompletionDate;
    private String logFileName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date scheduleExpectStartDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date scheduleExpectEndDate;
    private Integer jobId;
    private String quartzJobName;
    private String cornexpress;
    private Integer priority;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date previousFireTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date nextFireTime;
    private String scheduleType;
    private String scheduleTypeMeaning;
    private Integer wasExecutedTotalCount;
    private Integer wasExecutedSuccessCount;
    private Integer wasExecutedFailCount;
    private String argumentsText;
    private Integer failAttemptFrequency;
    private Integer timeout;
    private Integer createdBy;

    private String jobName;
    private String userName;

    private String system;
    private String module;

    private String systemMeaning;
    private String moduleMeaning;
    private String isAbleDelete;//是否能够删除

    public String getIsAbleDelete() {
        return isAbleDelete;
    }

    public void setIsAbleDelete(String isAbleDelete) {
        this.isAbleDelete = isAbleDelete;
    }

    //
    public void setSystemMeaning(String systemMeaning) {
        this.systemMeaning = systemMeaning;
    }

    public String getSystemMeaning() {
        return systemMeaning;
    }

    public void setModuleMeaning(String moduleMeaning) {
        this.moduleMeaning = moduleMeaning;
    }

    public String getModuleMeaning() {
        return moduleMeaning;
    }
    //

    public void setScheduleTypeMeaning(String scheduleTypeMeaning) {
        this.scheduleTypeMeaning = scheduleTypeMeaning;
    }

    public String getScheduleTypeMeaning() {
        return scheduleTypeMeaning;
    }

    public void setPhaseCodeMeaning(String phaseCodeMeaning) {
        this.phaseCodeMeaning = phaseCodeMeaning;
    }

    public String getPhaseCodeMeaning() {
        return phaseCodeMeaning;
    }

    public void setStatusCodeMeaning(String statusCodeMeaning) {
        this.statusCodeMeaning = statusCodeMeaning;
    }

    public String getStatusCodeMeaning() {
        return statusCodeMeaning;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getPhaseCode() {
        return phaseCode;
    }

    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }


    public Date getActualCompletionDate() {
        return actualCompletionDate;
    }

    public void setActualCompletionDate(Date actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }


    public Date getScheduleExpectStartDate() {
        return scheduleExpectStartDate;
    }

    public void setScheduleExpectStartDate(Date scheduleExpectStartDate) {
        this.scheduleExpectStartDate = scheduleExpectStartDate;
    }


    public Date getScheduleExpectEndDate() {
        return scheduleExpectEndDate;
    }

    public void setScheduleExpectEndDate(Date scheduleExpectEndDate) {
        this.scheduleExpectEndDate = scheduleExpectEndDate;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getQuartzJobName() {
        return quartzJobName;
    }

    public void setQuartzJobName(String quartzJobName) {
        this.quartzJobName = quartzJobName;
    }

    public String getCornexpress() {
        return cornexpress;
    }

    public void setCornexpress(String cornexpress) {
        this.cornexpress = cornexpress;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }


    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Integer getWasExecutedTotalCount() {
        return wasExecutedTotalCount;
    }

    public void setWasExecutedTotalCount(Integer wasExecutedTotalCount) {
        this.wasExecutedTotalCount = wasExecutedTotalCount;
    }

    public Integer getWasExecutedSuccessCount() {
        return wasExecutedSuccessCount;
    }

    public void setWasExecutedSuccessCount(Integer wasExecutedSuccessCount) {
        this.wasExecutedSuccessCount = wasExecutedSuccessCount;
    }

    public Integer getWasExecutedFailCount() {
        return wasExecutedFailCount;
    }

    public void setWasExecutedFailCount(Integer wasExecutedFailCount) {
        this.wasExecutedFailCount = wasExecutedFailCount;
    }

    public String getArgumentsText() {
        return argumentsText;
    }

    public void setArgumentsText(String argumentsText) {
        this.argumentsText = argumentsText;
    }

    public Integer getFailAttemptFrequency() {
        return failAttemptFrequency;
    }

    public void setFailAttemptFrequency(Integer failAttemptFrequency) {
        this.failAttemptFrequency = failAttemptFrequency;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
