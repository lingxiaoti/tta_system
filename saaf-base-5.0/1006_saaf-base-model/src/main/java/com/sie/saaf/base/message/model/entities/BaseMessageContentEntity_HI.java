package com.sie.saaf.base.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseMessageContentEntity_HI Entity Object
 * Fri Jul 13 09:54:55 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_message_content")
public class BaseMessageContentEntity_HI {
    private Integer conMessId; //主键Id
    private String conMessTitle; //消息标题
    private String conMessType; //消息类型(COMMON/URL)
    private Integer conMessState; //消息状态 0:未发送给任何人；1:已发送；2:撤回
    private String conMessSystem; //消息的接收系统
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date conSendDate; //消息的发送时间
    private String conMessContent; //消息内容
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
    @SequenceGenerator(name = "SEQ_BASE_MESSAGE_CONTENT", sequenceName = "SEQ_BASE_MESSAGE_CONTENT", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_MESSAGE_CONTENT", strategy = GenerationType.SEQUENCE)
    @Column(name = "con_mess_id", nullable = false, length = 11)
    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    @Column(name = "con_mess_title", nullable = true, length = 256)
    public String getConMessTitle() {
        return conMessTitle;
    }

    public void setConMessTitle(String conMessTitle) {
        this.conMessTitle = conMessTitle;
    }

    @Column(name = "con_mess_type", nullable = true, length = 10)
    public String getConMessType() {
        return conMessType;
    }

    public void setConMessType(String conMessType) {
        this.conMessType = conMessType;
    }

    @Column(name = "con_mess_state", nullable = true, length = 11)
    public Integer getConMessState() {
        return conMessState;
    }

    public void setConMessState(Integer conMessState) {
        this.conMessState = conMessState;
    }

    @Column(name = "con_mess_system", nullable = true, length = 32)
    public String getConMessSystem() {
        return conMessSystem;
    }

    public void setConMessSystem(String conMessSystem) {
        this.conMessSystem = conMessSystem;
    }

    @Column(name = "con_send_date", nullable = true, length = 0)
    public Date getConSendDate() {
        return conSendDate;
    }

    public void setConSendDate(Date conSendDate) {
        this.conSendDate = conSendDate;
    }

    @Column(name = "con_mess_content", nullable = true, length = 2000)
    public String getConMessContent() {
        return conMessContent;
    }

    public void setConMessContent(String conMessContent) {
        this.conMessContent = conMessContent;
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

    @Column(name = "delete_flag", nullable = true, length = 11)
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
