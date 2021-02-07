package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


/**
 * Created by Administrator on Fri Jul 07 17:28:51 CST 2017
 */
public class PageModelGroupDetailEntity_HI_RO {


    public static final String query =
        "SELECT   pmgd.group_det_id AS groupDetId,   pmgd.group_code AS groupCode,   pmgi.group_name_cn AS groupNameCn,   pmgi.group_name_en AS groupNameEn,   pmgi.group_level AS groupLevel,   pmgi.group_parent_code AS groupParentCode,   pmgi.group_name_view_flag AS groupNameViewFlag,   pmgi.group_name_view_type AS groupNameViewType,   pmgd.group_det_dim_type AS groupDetDimType,   pmgd.group_det_dim_action_view_type AS groupDetDimActionViewType,   pmgd.group_det_dim_name_en AS groupDetDimNameEn,   pmgd.group_det_dim_name_cn AS groupDetDimNameCn,   pmgd.group_det_dim_code AS groupDetDimCode,   pmgd.group_det_dim_opt_code AS groupDetDimOptCode,   pmgd.group_det_dim_opt_name AS groupDetDimOptName,   pmgd.version_num AS versionNum,   pmgd.CREATION_DATE AS creationDate  FROM page_model_group_detail pmgd LEFT JOIN page_model_group_info pmgi ON pmgi.group_code =pmgd.group_code WHERE 1=1";
    private Integer groupDetId;
    private String groupCode;
    private String groupNameCn;
    private String groupNameEn;
    private Integer groupLevel;
    private String groupParentCode;
    private String groupNameViewFlag;
    private String groupNameViewType;
    private String groupDetDimType;
    private String groupDetDimActionViewType;
    private String groupDetDimNameEn;
    private String groupDetDimNameCn;
    private String groupDetDimCode;
    private String groupDetDimOptCode;
    private String groupDetDimOptName;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public Integer getGroupDetId() {
        return this.groupDetId;
    }

    public void setGroupDetId(Integer groupDetId) {
        this.groupDetId = groupDetId;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupNameCn() {
        return this.groupNameCn;
    }

    public void setGroupNameCn(String groupNameCn) {
        this.groupNameCn = groupNameCn;
    }

    public String getGroupNameEn() {
        return this.groupNameEn;
    }

    public void setGroupNameEn(String groupNameEn) {
        this.groupNameEn = groupNameEn;
    }

    public Integer getGroupLevel() {
        return this.groupLevel;
    }

    public void setGroupLevel(Integer groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getGroupParentCode() {
        return this.groupParentCode;
    }

    public void setGroupParentCode(String groupParentCode) {
        this.groupParentCode = groupParentCode;
    }

    public String getGroupNameViewFlag() {
        return this.groupNameViewFlag;
    }

    public void setGroupNameViewFlag(String groupNameViewFlag) {
        this.groupNameViewFlag = groupNameViewFlag;
    }

    public String getGroupNameViewType() {
        return this.groupNameViewType;
    }

    public void setGroupNameViewType(String groupNameViewType) {
        this.groupNameViewType = groupNameViewType;
    }

    public String getGroupDetDimType() {
        return this.groupDetDimType;
    }

    public void setGroupDetDimType(String groupDetDimType) {
        this.groupDetDimType = groupDetDimType;
    }

    public String getGroupDetDimActionViewType() {
        return this.groupDetDimActionViewType;
    }

    public void setGroupDetDimActionViewType(String groupDetDimActionViewType) {
        this.groupDetDimActionViewType = groupDetDimActionViewType;
    }

    public String getGroupDetDimNameEn() {
        return this.groupDetDimNameEn;
    }

    public void setGroupDetDimNameEn(String groupDetDimNameEn) {
        this.groupDetDimNameEn = groupDetDimNameEn;
    }

    public String getGroupDetDimNameCn() {
        return this.groupDetDimNameCn;
    }

    public void setGroupDetDimNameCn(String groupDetDimNameCn) {
        this.groupDetDimNameCn = groupDetDimNameCn;
    }

    public String getGroupDetDimCode() {
        return this.groupDetDimCode;
    }

    public void setGroupDetDimCode(String groupDetDimCode) {
        this.groupDetDimCode = groupDetDimCode;
    }

    public String getGroupDetDimOptCode() {
        return this.groupDetDimOptCode;
    }

    public void setGroupDetDimOptCode(String groupDetDimOptCode) {
        this.groupDetDimOptCode = groupDetDimOptCode;
    }

    public String getGroupDetDimOptName() {
        return this.groupDetDimOptName;
    }

    public void setGroupDetDimOptName(String groupDetDimOptName) {
        this.groupDetDimOptName = groupDetDimOptName;
    }

    public Integer getVersionNum() {
        return this.versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
