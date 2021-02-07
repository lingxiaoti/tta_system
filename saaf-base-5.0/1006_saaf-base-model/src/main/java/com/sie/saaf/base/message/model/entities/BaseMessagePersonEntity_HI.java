package com.sie.saaf.base.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseMessagePersonEntity_HI Entity Object
 * Fri Jul 13 09:55:07 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_message_person")
public class BaseMessagePersonEntity_HI {
    private Integer perMessId; //主键Id
    private Integer conMessId; //消息内容ID
    private Integer buMessId; //接收对象组合表主键ID
    private Integer depMessId; //接收部门表主键ID
    private Integer deptId; //接收部门ID
    private String userType; //接收用户类型
    private Integer userId; //接收用户ID
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
    @SequenceGenerator(name = "SEQ_BASE_MESSAGE_PERSON", sequenceName = "SEQ_BASE_MESSAGE_PERSON", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_MESSAGE_PERSON", strategy = GenerationType.SEQUENCE)
    @Column(name = "per_mess_id", nullable = false, length = 11)
    public Integer getPerMessId() {
        return perMessId;
    }

    public void setPerMessId(Integer perMessId) {
        this.perMessId = perMessId;
    }

    @Column(name = "con_mess_id", nullable = false, length = 11)
    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    @Column(name = "bu_mess_id", nullable = false, length = 11)
    public Integer getBuMessId() {
        return buMessId;
    }

    public void setBuMessId(Integer buMessId) {
        this.buMessId = buMessId;
    }

    @Column(name = "dep_mess_id", nullable = false, length = 11)
    public Integer getDepMessId() {
        return depMessId;
    }

    public void setDepMessId(Integer depMessId) {
        this.depMessId = depMessId;
    }

    @Column(name = "dept_id", nullable = false, length = 11)
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Column(name = "user_type", nullable = false, length = 30)
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "user_id", nullable = false, length = 11)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
