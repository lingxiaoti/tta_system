package com.sie.saaf.base.orgStructure.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BasePersonAccessTempEntity_HI Entity Object
 * Mon Aug 06 22:50:31 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_person_access_temp")
public class BasePersonAccessTempEntity_HI {
    private Integer accessTempId; //主键ID
    private String batchCode; //批次号
    private Integer orgId; //事业部
    private String accessType; //访问类型
    private Integer userId; //用户ID
    private Integer personId; //人员ID
    private Integer positionId; //职位
    private Integer subordinatePersonId; //下级人员ID
    private Integer subordinatePositionId; //下级职位ID
    private String channelType; //渠道类型
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新时间
    private Integer lastUpdatedBy; //更新人
    private Integer lastUpdateLogin; //最后更新登录ID
    private Integer deleteFlag; //删除标识
    private Integer versionNum; //版本号
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_BASE_PERSON_ACCESS_TEMP", sequenceName = "SEQ_BASE_PERSON_ACCESS_TEMP", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PERSON_ACCESS_TEMP", strategy = GenerationType.SEQUENCE)
    @Column(name = "access_temp_id", nullable = false, length = 11)
    public Integer getAccessTempId() {
        return accessTempId;
    }

    public void setAccessTempId(Integer accessTempId) {
        this.accessTempId = accessTempId;
    }

    @Column(name = "batch_code", nullable = true, length = 30)
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "org_id", nullable = true, length = 11)
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Column(name = "access_type", nullable = true, length = 10)
    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @Column(name = "user_id", nullable = true, length = 11)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @Column(name = "subordinate_person_id", nullable = true, length = 11)
    public Integer getSubordinatePersonId() {
        return subordinatePersonId;
    }

    public void setSubordinatePersonId(Integer subordinatePersonId) {
        this.subordinatePersonId = subordinatePersonId;
    }

    @Column(name = "subordinate_position_id", nullable = true, length = 11)
    public Integer getSubordinatePositionId() {
        return subordinatePositionId;
    }

    public void setSubordinatePositionId(Integer subordinatePositionId) {
        this.subordinatePositionId = subordinatePositionId;
    }

    @Column(name = "channel_type", nullable = true, length = 30)
    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
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

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
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
}
