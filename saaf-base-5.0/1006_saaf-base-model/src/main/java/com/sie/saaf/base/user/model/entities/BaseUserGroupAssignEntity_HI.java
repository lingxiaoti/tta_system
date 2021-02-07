package com.sie.saaf.base.user.model.entities;


import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "base_user_group_assign")
public class BaseUserGroupAssignEntity_HI {

    private Integer assignId; // 用户Id
    private Integer userId; // 电话号码
    private String userGroupCode; // 用户姓名（拼音）
    private String userGroupName; // 用户姓名（拼音首字母）
    private String enableFlag; // 电话号码
    private Integer createdBy; // 用户姓名（拼音）
    private Integer lastUpdatedBy; // 用户姓名（拼音首字母）
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; // 创建日期
    private Integer versionNum; // 版本号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; // 创建日期



    @Id
    @SequenceGenerator(name = "SEQ_BASE_USER_GROUP_ASSIGN", sequenceName = "SEQ_BASE_USER_GROUP_ASSIGN", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_BASE_USER_GROUP_ASSIGN", strategy = GenerationType.SEQUENCE)
    @Column(name = "assign_id", nullable = false, length = 11)
    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    @Column(name = "user_id", nullable = false, length = 11)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "USER_GROUP_CODE", nullable = true, length = 30)
    public String getUserGroupCode() {
        return userGroupCode;
    }

    public void setUserGroupCode(String userGroupCode) {
        this.userGroupCode = userGroupCode;
    }

    @Column(name = "USER_GROUP_NAME", nullable = true, length = 200)
    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    @Column(name = "ENABLE_FLAG", nullable = true, length = 6)
    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
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

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_update_date", nullable = true, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Column(name = "creation_date", nullable = true, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
