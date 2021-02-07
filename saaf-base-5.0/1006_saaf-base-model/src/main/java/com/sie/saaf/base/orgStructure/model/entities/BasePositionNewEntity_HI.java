package com.sie.saaf.base.orgStructure.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BasePositionEntity_HI Entity Object
 * Sat Jul 21 09:15:32 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_position")
public class BasePositionNewEntity_HI {
    private Integer positionId; //职位ID
    private String positionCode; //职位编号
    private Integer departmentId; //部门Id
    private Integer jobId; //职务ID
    private String positionName; //职位名称
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dateFrom; //生效日期
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dateTo; //失效日期
    private String enableFlag; //是否启用（Y：启用；N：禁用）
    private String bizLineType; //业务线类型
    private String channel; //渠道
    private Integer ouId; //事业部
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_BASE_POSITION", sequenceName = "SEQ_BASE_POSITION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_POSITION", strategy = GenerationType.SEQUENCE)
    @Column(name = "position_id", nullable = false, length = 11)
    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    @Column(name = "position_code", nullable = true, length = 40)
    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @Column(name = "department_id", nullable = true, length = 11)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "job_id", nullable = true, length = 11)
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Column(name = "position_name", nullable = true, length = 200)
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

    @Column(name = "enable_flag", nullable = true, length = 1)
    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    @Column(name = "biz_line_type", nullable = true, length = 50)
    public String getBizLineType() {
        return bizLineType;
    }

    public void setBizLineType(String bizLineType) {
        this.bizLineType = bizLineType;
    }

    @Column(name = "channel", nullable = true, length = 50)
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Column(name = "ou_id", nullable = true, length = 11)
    public Integer getOuId() {
        return ouId;
    }

    public void setOuId(Integer ouId) {
        this.ouId = ouId;
    }

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

    @Column(name = "last_updated_by", nullable = true, length = 11)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_update_date", nullable = true, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }
}
