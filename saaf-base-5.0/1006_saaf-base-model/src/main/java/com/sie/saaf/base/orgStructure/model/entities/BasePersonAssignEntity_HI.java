package com.sie.saaf.base.orgStructure.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BasePersonAssignEntity_HI Entity Object
 * Sat Jul 21 09:15:24 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_person_assign")
public class BasePersonAssignEntity_HI {
    private Integer assignId; //主键ID
    private Integer personId; //人员ID
    private Integer positionId; //职位ID
    private Integer jobId; //职务ID
    @JSONField(format = "yyyy-MM-dd")
    private Date dateFrom; //生效日期
    @JSONField(format = "yyyy-MM-dd")
    private Date dateTo; //失效日期
    private Integer ouId; //事业部
    private String mgrFlag; //部门负责人标识(Y/N)
    private String primaryFlag; //主职位标识(Y/N)
    private String enableFlag; //生效标识(Y/N)
    private String budgetView; //查看预算：1--无，2—查看本级部门预算，3-查看本级及下级部门预算
    private String budgetMaintain; //修改预算：Y/N
    //private Integer attribute1;
    //private Integer attribute2;
    //private Integer attribute3;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    private Integer versionNum; //版本号
    private Integer operatorUserId;
    private Integer lastUpdateLogin;

    @Id
    @SequenceGenerator(name = "SEQ_BASE_PERSON_ASSIGN", sequenceName = "SEQ_BASE_PERSON_ASSIGN", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PERSON_ASSIGN", strategy = GenerationType.SEQUENCE)
    @Column(name = "assign_id", nullable = false, length = 11)
    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    @Column(name = "person_id", nullable = true, length = 11)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Column(name = "position_id", nullable = true, length = 11)
    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    @Column(name = "job_id", nullable = true, length = 11)
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Column(name = "date_from", nullable = true, length = 0)
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Column(name = "date_to", nullable = true, length = 0)
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Column(name = "ou_id", nullable = true, length = 11)
    public Integer getOuId() {
        return ouId;
    }

    public void setOuId(Integer ouId) {
        this.ouId = ouId;
    }

    @Column(name = "mgr_flag", nullable = true, length = 10)
    public String getMgrFlag() {
        return mgrFlag;
    }

    public void setMgrFlag(String mgrFlag) {
        this.mgrFlag = mgrFlag;
    }

    @Column(name = "primary_flag", nullable = true, length = 10)
    public String getPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(String primaryFlag) {
        this.primaryFlag = primaryFlag;
    }

    @Column(name = "enable_flag", nullable = true, length = 10)
    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    @Column(name = "budget_view", nullable = true, length = 10)
    public String getBudgetView() {
        return budgetView;
    }

    public void setBudgetView(String budgetView) {
        this.budgetView = budgetView;
    }

    @Column(name = "budget_maintain", nullable = true, length = 10)
    public String getBudgetMaintain() {
        return budgetMaintain;
    }

    public void setBudgetMaintain(String budgetMaintain) {
        this.budgetMaintain = budgetMaintain;
    }

    /*
    @Column(name = "attribute1", nullable = true, length = 11)
    public Integer getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(Integer attribute1) {
        this.attribute1 = attribute1;
    }

    @Column(name = "attribute2", nullable = true, length = 11)
    public Integer getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(Integer attribute2) {
        this.attribute2 = attribute2;
    }

    @Column(name = "attribute3", nullable = true, length = 11)
    public Integer getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(Integer attribute3) {
        this.attribute3 = attribute3;
    }*/

    @Column(name = "creation_date", nullable = true, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "created_by", nullable = true, length = 11)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "last_update_date", nullable = true, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_updated_by", nullable = true, length = 11)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "delete_flag", nullable = true, length = 11)
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }
}
