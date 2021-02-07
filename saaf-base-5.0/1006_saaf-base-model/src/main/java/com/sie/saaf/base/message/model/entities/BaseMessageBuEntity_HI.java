package com.sie.saaf.base.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseMessageBuEntity_HI Entity Object
 * Fri Jul 13 09:54:59 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_message_bu")
public class BaseMessageBuEntity_HI {
    private Integer buMessId; //主键Id
    private Integer conMessId; //消息内容ID
    private Integer buId; //接收事业部ID
    private String userType; //接收用户类型
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
    @SequenceGenerator(name = "SEQ_BASE_MESSAGE_BU", sequenceName = "SEQ_BASE_MESSAGE_BU", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_MESSAGE_BU", strategy = GenerationType.SEQUENCE)
    @Column(name = "bu_mess_id", nullable = false, length = 11)
    public Integer getBuMessId() {
        return buMessId;
    }

    public void setBuMessId(Integer buMessId) {
        this.buMessId = buMessId;
    }

    @Column(name = "con_mess_id", nullable = false, length = 11)
    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    @Column(name = "bu_id", nullable = false, length = 11)
    public Integer getBuId() {
        return buId;
    }

    public void setBuId(Integer buId) {
        this.buId = buId;
    }

    @Column(name = "user_type", nullable = false, length = 30)
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
