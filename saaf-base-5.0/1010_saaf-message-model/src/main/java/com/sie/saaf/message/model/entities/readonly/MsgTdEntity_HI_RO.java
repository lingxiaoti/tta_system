package com.sie.saaf.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class MsgTdEntity_HI_RO {
    public static final String QUERY_SELECT_SQL="SELECT\n" +
            "\tmt.td_id AS tdId,\n" +
            "\tmt.phone AS phone,\n" +
            "  mt.send_time AS sendTime,\n" +
            "\tmt.pass AS pass,\n" +
            "  mt.content AS content,\n" +
            "\tmt.remark AS remark,\n" +
            "  mt.org_id AS orgId,\n" +
            "\tmt.last_update_date AS lastUpdateDate,\n" +
            "  mt.last_updated_by AS lastUpdatedBy,\n" +
            "\tmt.last_update_login AS lastUpdateLogin,\n" +
            "  mt.creation_date AS creationDate,\n" +
            "\tmt.created_by AS createdBy,\n" +
            "\tmt.is_delete AS isDelete,\n" +
            "\tmt.version_num AS versionNum,\n" +
            "\tmt.msg_source_id AS msgSourceId,\n" +
            "\tblv_org.meaning AS orgName,\n" +
            "\tmsc.source_name AS sourceName\n" +
            "FROM\n" +
            "\tmsg_td mt\n" +
            "LEFT JOIN msg_source_cfg msc on mt.msg_source_id = msc.msg_source_id \n" +
            "LEFT JOIN base_lookup_values blv_org on mt.org_id = blv_org.lookup_code and blv_org.lookup_type = 'BASE_OU' and blv_org.system_code = 'BASE' and blv_org.enabled_flag = 'Y' and blv_org.delete_flag = '0'\n" +
            "WHERE\n" +
            "\tmt.is_delete = '0' ";
    private Integer tdId; //消息配置ID
    private String phone;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    private String pass;
    private String content;
    private String remark;
    private Integer orgId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private Integer versionNum;
    private Integer msgSourceId;
    private String orgName;
    private String msgSourceName;

    public Integer getTdId() {
        return tdId;
    }

    public void setTdId(Integer tdId) {
        this.tdId = tdId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
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

    public Integer getMsgSourceId() {
        return msgSourceId;
    }

    public void setMsgSourceId(Integer msgSourceId) {
        this.msgSourceId = msgSourceId;
    }

    public String getOrgName() { return orgName; }

    public void setOrgName(String orgName) { this.orgName = orgName; }

    public String getMsgSourceName() { return msgSourceName; }

    public void setMsgSourceName(String msgSourceName) { this.msgSourceName = msgSourceName; }
}
