package com.sie.saaf.rule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;


/**
 * PageModelGroupInfoEntity_HI Entity Object
 * Thu Jul 06 10:50:38 CST 2017  Auto Generate
 */
@Entity
@Table(name = "page_model_group_info")
public class PageModelGroupInfoEntity_HI {
    private Integer groupId;
    private String groupCode;
    private String modelCode;
    private String groupNameEn;
    private String groupNameCn;
    private Integer groupLevel;
    private String groupParentCode;
    private String groupNameViewFlag;
    private String groupNameViewType;
    private Integer groupOrder;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_PAGE_MODEL_GROUP_INFO", sequenceName = "SEQ_PAGE_MODEL_GROUP_INFO", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_PAGE_MODEL_GROUP_INFO", strategy = GenerationType.SEQUENCE)
    @Column(name = "group_id", nullable = false, length = 11)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name = "group_code", nullable = false, length = 100)
    public String getGroupCode() {
        return groupCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    @Column(name = "model_code", nullable = true, length = 100)
    public String getModelCode() {
        return modelCode;
    }

    public void setGroupNameEn(String groupNameEn) {
        this.groupNameEn = groupNameEn;
    }

    @Column(name = "group_name_en", nullable = true, length = 100)
    public String getGroupNameEn() {
        return groupNameEn;
    }

    public void setGroupNameCn(String groupNameCn) {
        this.groupNameCn = groupNameCn;
    }

    @Column(name = "group_name_cn", nullable = true, length = 100)
    public String getGroupNameCn() {
        return groupNameCn;
    }

    public void setGroupLevel(Integer groupLevel) {
        this.groupLevel = groupLevel;
    }

    @Column(name = "group_level", nullable = true, length = 11)
    public Integer getGroupLevel() {
        return groupLevel;
    }

    public void setGroupParentCode(String groupParentCode) {
        this.groupParentCode = groupParentCode;
    }

    @Column(name = "group_parent_code", nullable = true, length = 100)
    public String getGroupParentCode() {
        return groupParentCode;
    }

    public void setGroupNameViewFlag(String groupNameViewFlag) {
        this.groupNameViewFlag = groupNameViewFlag;
    }

    @Column(name = "group_name_view_flag", nullable = true, length = 10)
    public String getGroupNameViewFlag() {
        return groupNameViewFlag;
    }

    public void setGroupNameViewType(String groupNameViewType) {
        this.groupNameViewType = groupNameViewType;
    }

    @Column(name = "group_name_view_type", nullable = true, length = 45)
    public String getGroupNameViewType() {
        return groupNameViewType;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }

    @Column(name = "group_order", nullable = true, length = 11)
    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
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

    @Column(name = "created_by", nullable = false, length = 11)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_updated_by", nullable = false, length = 11)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_date", nullable = false, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
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
}

