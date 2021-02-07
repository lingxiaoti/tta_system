package com.sie.saaf.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class MsgLogEntity_HI_RO {
    public static final String QUERY_SELECT_SQL="SELECT\n" +
            "\tml.log_id AS logId,\n" +
            "\tml.request_param AS requestParam,\n" +
            "\tml.return_data AS returnData,\n" +
            "\tml.job_id AS jobId,\n" +
            "\tml.org_id AS orgId,\n" +
            "\tml.user_name AS userName,\n" +
            "\tml.request_id AS requestId,\n" +
            "\tml.last_updated_by AS lastUpdatedBy,\n" +
            "\tml.last_update_date AS lastUpdateDate,\n" +
            "\tml.creation_date AS creationDate,\n" +
            "\tml.created_by AS createdBy,\n" +
            "\tml.last_update_login AS lastUpdateLogin,\n" +
            "\tml.version_num AS versionNum,\n" +
            "\tml.is_delete AS isDelete,\n" +
            "\tblv_org.meaning AS orgName\n" +
            "FROM\n" +
            "\tmsg_log ml\n" +
            "LEFT JOIN base_lookup_values blv_org on ml.org_id = blv_org.lookup_code and blv_org.lookup_type = 'BASE_OU' and blv_org.system_code = 'BASE'  and blv_org.enabled_flag = 'Y' and blv_org.delete_flag = '0'\n" +
            "WHERE\n" +
            "\tml.is_delete = '0' ";
    private Integer logId; //消息配置ID
    private String requestParam;
    private String returnData;
    private String jobId;
    private Integer orgId;
    private String userName;
    private String requestId;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer lastUpdateLogin;
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private String orgName;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getVersionNum() { return versionNum; }

    public void setVersionNum(Integer versionNum) { this.versionNum = versionNum; }
}
