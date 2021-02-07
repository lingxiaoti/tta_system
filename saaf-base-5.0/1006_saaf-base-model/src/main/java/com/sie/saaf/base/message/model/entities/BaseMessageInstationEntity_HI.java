package com.sie.saaf.base.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseMessageInstationEntity_HI Entity Object
 * Fri Jul 13 09:55:12 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_message_instation")
public class BaseMessageInstationEntity_HI {
    private Integer insMessId; //主键Id
    private Integer conMessId; //消息内容ID
    private String messTitle; //消息标题
    private String messContent; //消息内容
    private Integer messStatus; //消息状态 已读/未读
    private String messFromSystem; //消息的来源系统
    private String messFromModule; //消息的来源模块
    private Integer messReceiverId; //消息接收人ID
    private String messReceiver; //接收人
    private Integer messSenderId; //消息接收人员ID
    private String messSender; //发送人
    private String sourceId; //来源ID，当前存放图片ID
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_BASE_MESSAGE_DEPARTMENT", sequenceName = "SEQ_BASE_MESSAGE_DEPARTMENT", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_MESSAGE_DEPARTMENT", strategy = GenerationType.SEQUENCE)
    @Column(name = "ins_mess_id", nullable = false, length = 11)
    public Integer getInsMessId() {
        return insMessId;
    }

    public void setInsMessId(Integer insMessId) {
        this.insMessId = insMessId;
    }

    @Column(name = "con_mess_id", nullable = false, length = 11)
    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    @Column(name = "mess_title", nullable = true, length = 256)
    public String getMessTitle() {
        return messTitle;
    }

    public void setMessTitle(String messTitle) {
        this.messTitle = messTitle;
    }

    @Column(name = "mess_content", nullable = true, length = 2000)
    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }

    @Column(name = "mess_status", nullable = true, length = 11)
    public Integer getMessStatus() {
        return messStatus;
    }

    public void setMessStatus(Integer messStatus) {
        this.messStatus = messStatus;
    }

    @Column(name = "mess_from_system", nullable = true, length = 32)
    public String getMessFromSystem() {
        return messFromSystem;
    }

    public void setMessFromSystem(String messFromSystem) {
        this.messFromSystem = messFromSystem;
    }

    @Column(name = "mess_from_module", nullable = true, length = 32)
    public String getMessFromModule() {
        return messFromModule;
    }

    public void setMessFromModule(String messFromModule) {
        this.messFromModule = messFromModule;
    }

    @Column(name = "mess_receiver_id", nullable = true, length = 11)
    public Integer getMessReceiverId() {
        return messReceiverId;
    }

    public void setMessReceiverId(Integer messReceiverId) {
        this.messReceiverId = messReceiverId;
    }

    @Column(name = "mess_receiver", nullable = true, length = 150)
    public String getMessReceiver() {
        return messReceiver;
    }

    public void setMessReceiver(String messReceiver) {
        this.messReceiver = messReceiver;
    }

    @Column(name = "mess_sender_id", nullable = true, length = 11)
    public Integer getMessSenderId() {
        return messSenderId;
    }

    public void setMessSenderId(Integer messSenderId) {
        this.messSenderId = messSenderId;
    }

    @Column(name = "mess_sender", nullable = true, length = 150)
    public String getMessSender() {
        return messSender;
    }

    public void setMessSender(String messSender) {
        this.messSender = messSender;
    }

    @Column(name = "source_id", nullable = true, length = 80)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "created_by", nullable = true, length = 11)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "creation_date", nullable = true, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "last_update_date", nullable = true, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_updated_by", nullable = true, length = 11)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Column(name = "delete_flag", nullable = true, length = 255)
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }
}
