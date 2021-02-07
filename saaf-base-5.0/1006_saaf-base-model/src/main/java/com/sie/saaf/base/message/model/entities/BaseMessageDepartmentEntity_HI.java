package com.sie.saaf.base.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseMessageDepartmentEntity_HI Entity Object
 * Fri Jul 13 09:55:02 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_message_department")
public class BaseMessageDepartmentEntity_HI {
    private Integer depMessId; //主键Id
    private Integer conMessId; //消息内容ID
    private Integer buMessId; //接收对象组合表主键ID
    private Integer deptId; //接收部门ID
    private String deptCode; //接收部门编号
    private String deptName; //接收部门名称
    private Integer sendingNum; //发送人数
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
    @Column(name = "dep_mess_id", nullable = false, length = 11)
    public Integer getDepMessId() {
        return depMessId;
    }

    public void setDepMessId(Integer depMessId) {
        this.depMessId = depMessId;
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

    @Column(name = "dept_id", nullable = false, length = 11)
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Column(name = "dept_code", nullable = false, length = 20)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Column(name = "dept_name", nullable = true, length = 200)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name = "sending_num", nullable = true, length = 11)
    public Integer getSendingNum() {
        return sendingNum;
    }

    public void setSendingNum(Integer sendingNum) {
        this.sendingNum = sendingNum;
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
