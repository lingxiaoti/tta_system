package com.sie.saaf.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * MsgUserEntity_HI Entity Object
 * Thu Jun 07 09:54:30 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_user")
public class MsgUserEntity_HI {
    private Integer msgUserId;
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

    public void setMsgUserId(Integer msgUserId) {
        this.msgUserId = msgUserId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_MSG_USER", sequenceName = "SEQ_MSG_USER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_USER", strategy = GenerationType.SEQUENCE)
    @Column(name = "msg_user_id", nullable = false, length = 15)
    public Integer getMsgUserId() {
        return msgUserId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Column(name = "org_id", nullable = true, length = 20)
    public String getOrgId() {
        return orgId;
    }

    public void setMsgUserName(String msgUserName) {
        this.msgUserName = msgUserName;
    }

    @Column(name = "msg_user_name", nullable = true, length = 96)
    public String getMsgUserName() {
        return msgUserName;
    }

    public void setMsgUserPwd(String msgUserPwd) {
        this.msgUserPwd = msgUserPwd;
    }

    @Column(name = "msg_user_pwd", nullable = true, length = 128)
    public String getMsgUserPwd() {
        return msgUserPwd;
    }

    @Column(name = "no_log", nullable = true, length = 2)
    public String getNoLog() {
        return noLog;
    }

    public void setNoLog(String noLog) {
        this.noLog = noLog;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    @Column(name = "enabled_flag", nullable = false, length = 2)
    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_date", nullable = false, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_updated_by", nullable = false, length = 20)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "creation_date", nullable = false, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by", nullable = false, length = 20)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Column(name = "is_delete", nullable = false, length = 11)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }
    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
    }

    @Transient
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() { return lastUpdateLogin; }

    public void setLastUpdateLogin(Integer lastUpdateLogin) { this.lastUpdateLogin = lastUpdateLogin; }
}
