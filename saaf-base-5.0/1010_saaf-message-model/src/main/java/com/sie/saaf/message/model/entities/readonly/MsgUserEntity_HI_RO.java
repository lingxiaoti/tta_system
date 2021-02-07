package com.sie.saaf.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class MsgUserEntity_HI_RO {
    public static final String QUERY_SELECT_SQL="SELECT\n" +
            "\tmu.msg_user_id AS msgUserId,\n" +
            "\tto_char(mu.org_id) AS orgId,\n" +
            "\tmu.msg_user_name AS msgUserName,\n" +
            "\tmu.msg_user_pwd AS msgUserPwd,\n" +
            "\tcase mu.no_log when '1' then '是' when '0' then '否' end AS noLogName,\n" +
            "\tmu.remark AS remark,\n" +
            "\tmu.no_log AS noLog,\n" +
            "\tmu.enabled_flag AS enabledFlag,\n" +
            "\tmu.last_update_date AS lastUpdateDate,\n" +
            "  mu.last_updated_by AS lastUpdatedBy,\n" +
            "\tmu.last_update_login AS lastUpdateLogin,\n" +
            "  mu.creation_date AS creationDate,\n" +
            "\tmu.created_by AS createdBy,\n" +
            "\tmu.is_delete AS isDelete,\n" +
            "\tmu.version_num AS versionNum,\n" +
            "\tblv_org.meaning AS orgName,\n" +
            "\tblv_msg_flag.meaning AS enabledFlagName\n" +
            "\n" +
            "FROM\n" +
            "\tmsg_user mu\n" +
            "LEFT JOIN base_lookup_values blv_org on mu.org_id = blv_org.lookup_code and blv_org.lookup_type = 'BASE_OU' and blv_org.system_code = 'BASE' and blv_org.enabled_flag = 'Y' and blv_org.delete_flag = '0'\n" +
            "LEFT JOIN base_lookup_values blv_msg_flag on mu.enabled_flag = blv_msg_flag.lookup_code and blv_msg_flag.lookup_type = 'MESSAGE_FLAG' and blv_msg_flag.system_code = 'BASE' and blv_msg_flag.enabled_flag = 'Y' and blv_msg_flag.delete_flag = '0'\n" +
            "WHERE\n" +
            "\tmu.is_delete = '0' ";

    public static final String QUERY_USERNAME_EXIST_SQL="SELECT mu.msg_user_id msgUserId from msg_user mu  where mu.is_delete = '0' ";


    private Integer msgUserId;
    private Integer msgInstanceId;
    private String orgId;
    private String msgUserName; //消息服务用户名
    private String msgUserPwd; //消息服务用户密码,不可逆加密存放
    private String noLog;
    private String remark;
    private String enabledFlag; //启用状态:0.已停用 1.启用
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private Integer versionNum;
    private String orgName;
    private Integer lastUpdateLogin;
    private String noLogName;

    public Integer getMsgUserId() {
        return msgUserId;
    }

    public void setMsgUserId(Integer msgUserId) {
        this.msgUserId = msgUserId;
    }

    public Integer getMsgInstanceId() {
        return msgInstanceId;
    }

    public void setMsgInstanceId(Integer msgInstanceId) {
        this.msgInstanceId = msgInstanceId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getMsgUserName() {
        return msgUserName;
    }

    public void setMsgUserName(String msgUserName) {
        this.msgUserName = msgUserName;
    }

    public String getMsgUserPwd() {
        return msgUserPwd;
    }

    public void setMsgUserPwd(String msgUserPwd) {
        this.msgUserPwd = msgUserPwd;
    }

    public String getNoLog() {
        return noLog;
    }

    public void setNoLog(String noLog) {
        this.noLog = noLog;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
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

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getOrgName() { return orgName; }

    public void setOrgName(String orgName) { this.orgName = orgName; }

    public Integer getLastUpdateLogin() { return lastUpdateLogin; }

    public void setLastUpdateLogin(Integer lastUpdateLogin) { this.lastUpdateLogin = lastUpdateLogin; }

    public String getNoLogName() { return noLogName; }

    public void setNoLogName(String noLogName) { this.noLogName = noLogName; }
}
