package com.sie.saaf.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class MsgSourceCfgEntity_HI_RO {
    public static final String QUERY_SELECT_SQL="SELECT\n" +
            "\tmsc.msg_source_id AS msgSourceId,\n" +
            "  msc.org_id AS orgId,\n" +
            "\tmsc.msg_type_code AS msgTypeCode,\n" +
            "\tmsc.param_cfg AS paramCfg,\n" +
            "\tmsc.source_user AS sourceUser,\n" +
            "\tmsc.source_pwd AS sourcePwd,\n" +
            "\tmsc.source_name AS sourceName,\n" +
            "\tmsc.source_desc AS sourceDesc,\n" +
            "\tmsc.enabled_flag AS enabledFlag,\n" +
            "\tmsc.version_num AS versionNum,\n" +
            "\tmsc.last_update_date AS lastUpdateDate,\n" +
            "\tmsc.last_updated_by AS lastUpdatedBy,\n" +
            "\tmsc.last_update_login AS lastUpdateLogin,\n" +
            "\tmsc.creation_date AS creationDate,\n" +
            "\tmsc.created_by AS createdBy,\n" +
            "\tmsc.is_delete AS isDelete,\n" +
            "\tblv_org.meaning AS orgName,\n" +
            "\tblv_type.meaning AS msgTypeName,\n" +
            "\tblv_msg_flag.meaning AS enabledFlagName\n" +
            "FROM\n" +
            "\tmsg_source_cfg msc\n" +
            "LEFT JOIN base_lookup_values blv_org on msc.org_id = blv_org.lookup_code and blv_org.lookup_type = 'BASE_OU' AND blv_org.system_code = 'BASE' AND blv_org.enabled_flag = 'Y' and blv_org.delete_flag = '0'\n" +
            "LEFT JOIN base_lookup_values blv_type on msc.msg_type_code = blv_type.lookup_code and blv_type.lookup_type = 'MESSAGE_TYPE' AND blv_type.system_code = 'PUBLIC' AND blv_type.enabled_flag = 'Y' and blv_type.delete_flag = '0'\n" +
            "LEFT JOIN base_lookup_values blv_msg_flag on msc.enabled_flag = blv_msg_flag.lookup_code and blv_msg_flag.lookup_type = 'MESSAGE_FLAG' AND blv_msg_flag.system_code = 'BASE' AND blv_msg_flag.enabled_flag = 'Y' and blv_msg_flag.delete_flag = '0'\n" +
            "WHERE\n" +
            "\tmsc.is_delete = '0' ";
    public static final String QUERY_TEMPLENAME_EXIST_SQL="SELECT msc.source_name sourceName from msg_source_cfg msc  where msc.is_delete = '0' ";

    private Integer msgSourceId; //消息配置ID
    private Integer orgId; //OU
    private String msgTypeCode; //消息类型CODE
    private String msgTypeName; //消息类型名称
    private String paramCfg; //接口配置参数
    private String sourceUser; //接口用户名
    private String sourcePwd; //接口密码,des加密存放
    private String sourceName; //消息接口名称
    private String sourceDesc; //接口描述
    private String enabledFlag; //启用状态:0.已停用 1.启用
    private Integer versionNum;
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private String enabledFlagName;
    private String orgName;

    public Integer getMsgSourceId() {
        return msgSourceId;
    }

    public void setMsgSourceId(Integer msgSourceId) {
        this.msgSourceId = msgSourceId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getMsgTypeCode() {
        return msgTypeCode;
    }

    public void setMsgTypeCode(String msgTypeCode) {
        this.msgTypeCode = msgTypeCode;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public String getParamCfg() {
        return paramCfg;
    }

    public void setParamCfg(String paramCfg) {
        this.paramCfg = paramCfg;
    }

    public String getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    public String getSourcePwd() {
        return sourcePwd;
    }

    public void setSourcePwd(String sourcePwd) {
        this.sourcePwd = sourcePwd;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
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

    public String getEnabledFlagName() {
        return enabledFlagName;
    }

    public void setEnabledFlagName(String enabledFlagName) {
        this.enabledFlagName = enabledFlagName;
    }

    public String getOrgName() { return orgName; }

    public void setOrgName(String orgName) { this.orgName = orgName; }
}
