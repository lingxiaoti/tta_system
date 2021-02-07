package com.sie.saaf.rule.model.entities;


import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;


/**
 * PageModelGroupDetailEntity_HI Entity Object
 * Thu Jul 06 10:50:38 CST 2017  Auto Generate
 */
@Entity
@Table(name = "page_model_group_detail")
public class PageModelGroupDetailEntity_HI {
    private Integer groupDetId;
    private String groupCode;
    private String groupDetDimCode;
    private String groupDetDimType;
    private String groupDetDimActionViewType;
    private String groupDetDimNameEn;
    private String groupDetDimNameCn;
    private String groupDetDimOptCode;
    private String groupDetDimOptName;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    public void setGroupDetId(Integer groupDetId) {
        this.groupDetId = groupDetId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_PAGE_MODEL_GROUP_DETAIL", sequenceName = "SEQ_PAGE_MODEL_GROUP_DETAIL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PAGE_MODEL_GROUP_DETAIL", strategy = GenerationType.SEQUENCE)
    @Column(name = "group_det_id", nullable = false, length = 11)
    public Integer getGroupDetId() {
        return groupDetId;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name = "group_code", nullable = false, length = 100)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupDetDimCode(String groupDetDimCode) {
        this.groupDetDimCode = groupDetDimCode;
    }

    @Column(name = "group_det_dim_code", nullable = false, length = 30)
    public String getGroupDetDimCode() {
        return groupDetDimCode;
    }

    public void setGroupDetDimType(String groupDetDimType) {
        this.groupDetDimType = groupDetDimType;
    }

    @Column(name = "group_det_dim_type", nullable = true, length = 30)
    public String getGroupDetDimType() {
        return groupDetDimType;
    }

    public void setGroupDetDimActionViewType(String groupDetDimActionViewType) {
        this.groupDetDimActionViewType = groupDetDimActionViewType;
    }

    @Column(name = "group_det_dim_action_view_type", nullable = true, length = 30)
    public String getGroupDetDimActionViewType() {
        return groupDetDimActionViewType;
    }

    public void setGroupDetDimNameEn(String groupDetDimNameEn) {
        this.groupDetDimNameEn = groupDetDimNameEn;
    }

    @Column(name = "group_det_dim_name_en", nullable = true, length = 100)
    public String getGroupDetDimNameEn() {
        return groupDetDimNameEn;
    }

    public void setGroupDetDimNameCn(String groupDetDimNameCn) {
        this.groupDetDimNameCn = groupDetDimNameCn;
    }

    @Column(name = "group_det_dim_name_cn", nullable = true, length = 100)
    public String getGroupDetDimNameCn() {
        return groupDetDimNameCn;
    }

    public void setGroupDetDimOptCode(String groupDetDimOptCode) {
        this.groupDetDimOptCode = groupDetDimOptCode;
    }

    @Column(name = "group_det_dim_opt_code", nullable = true, length = 20)
    public String getGroupDetDimOptCode() {
        return groupDetDimOptCode;
    }

    public void setGroupDetDimOptName(String groupDetDimOptName) {
        this.groupDetDimOptName = groupDetDimOptName;
    }

    @Column(name = "group_det_dim_opt_name", nullable = true, length = 30)
    public String getGroupDetDimOptName() {
        return groupDetDimOptName;
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

