package com.sie.saaf.base.orgStructure.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseJobEntity_HI_RO Entity Object
 * Sat Jul 21 09:15:09 CST 2018  Auto Generate
 */

public class BaseJobEntity_HI_RO {
    /*public static final String QUERY_BASE_JOB_SQL = "select bj.job_id jobId\n" +
            "\t\t\t,bj.job_code jobCode\n" +
            "\t\t\t,bj.job_name jobName\n" +
            "\t\t\t,bj.\"COMMENT\" \n" +
            "\t\t\t,bj.date_from dateFrom\n" +
            "\t\t\t,bj.date_to dateTo\n" +
            "\t\t\t,bj.enable_flag enableFlag\n" +
            "\t\t\t,bj.ou_id ouId\n" +
            "      ,ouBlv.meaning ouName\n" +
            "\t\t\t,bj.creation_date creationDate\n" +
            "\t\t\t,bu.user_name createdByName\n" +
            "  from base_job bj\n" +
            "\t\t\t,base_users bu\n" +
            "      ,base_lookup_values ouBlv\n" +
            " where 1 = 1\n" +
            "\t and bj.created_by = bu.user_id\n" +
            "   AND bj.ou_id = ouBlv.lookup_code\n" +
            "   AND ouBlv.lookup_type = 'BASE_OU'\n" +
            "   AND ouBlv.system_code = 'BASE'\n";*/
    public static final String QUERY_BASE_JOB_SQL = "select bj.job_id, bj.job_code, bj.job_name from base_job bj where 1 = 1";

    private Integer jobId; //主键ID
    private String jobCode; //职务编码;
    private String jobName; //职务名称
    private String comment; //备注
    @JSONField(format = "yyyy-MM-dd")
    private Date dateFrom; //生效日期
    @JSONField(format = "yyyy-MM-dd")
    private Date dateTo; //失效日期
    private Integer ouId; //事业部
    private String ouName;
    private String enableFlag;
    private Integer deleteFlag; //'是否删除（0：未删除；1：已删除）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private String createdByName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer versionNum; //版本号
    private Integer operatorUserId;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getOuId() {
        return ouId;
    }

    public void setOuId(Integer ouId) {
        this.ouId = ouId;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }
}
