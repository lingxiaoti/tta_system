package com.sie.saaf.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * MsgTdEntity_HI Entity Object
 * Thu Jun 07 11:44:33 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_td")
public class MsgTdEntity_HI {
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

    @Column(name = "msg_source_id", nullable = false, length = 20)
    public Integer getMsgSourceId() {
        return msgSourceId;
    }

    public void setMsgSourceId(Integer msgSourceId) {
        this.msgSourceId = msgSourceId;
    }

    public void setTdId(Integer tdId) {
        this.tdId = tdId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_MSG_TD", sequenceName = "SEQ_MSG_TD", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_TD", strategy = GenerationType.SEQUENCE)
    @Column(name = "td_id", nullable = false, length = 20)
    public Integer getTdId() {
        return tdId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "phone", nullable = true, length = 36)
    public String getPhone() {
        return phone;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "remark", nullable = true, length = 36)
    public String getRemark() {
        return remark;
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

    @Column(name = "send_time", nullable = false, length = 0)
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "pass", nullable = false, length = 20)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Column(name = "content", nullable = false, length = 20)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "org_id", nullable = false, length = 15)
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
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
}
