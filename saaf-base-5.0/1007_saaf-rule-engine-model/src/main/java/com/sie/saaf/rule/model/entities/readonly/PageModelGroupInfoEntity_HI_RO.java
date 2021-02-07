package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


/**
 * Created by Administrator on Fri Jul 07 17:27:23 CST 2017
 */
public class PageModelGroupInfoEntity_HI_RO {


    public static final String query =
        "SELECT   pmgi.group_id AS groupId,   pmgi.model_code AS modelCode,   pmi.model_name AS modelName,   pmgi.group_code AS groupCode,   pmgi.group_name_cn  AS groupNameCn,   pmi.rule_business_line_code AS ruleBusinessLineCode,   rbl.rule_business_line_name AS ruleBusinessLineName,   pmgi.group_level AS groupLevel,   pmgi.group_parent_code AS groupParentCode,   pmgi.group_name_view_flag AS groupNameViewFlag,   pmgi.group_name_view_type AS groupNameViewType,   pmgi.group_order AS groupOrder,   pmgi.version_num AS versionNum,   pmgi.CREATION_DATE AS creationDate  FROM page_model_group_info pmgi LEFT JOIN page_model_info pmi ON pmi.model_code = pmgi.model_code   LEFT JOIN rule_business_line rbl ON rbl.rule_business_line_code = pmi.rule_business_line_code WHERE 1=1";
    private Integer groupId;
    private String modelCode;
    private String modelName;
    private String groupCode;
    private String groupNameCn;
    private String pmgiGroupNameCn;
    private String ruleBusinessLineCode;
    private String ruleBusinessLineName;
    private Integer groupLevel;
    private String groupParentCode;
    private String groupNameViewFlag;
    private String groupNameViewType;
    private Integer groupOrder;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getModelCode() {
        return this.modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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

    public String getPmgiGroupNameCn() {
        return this.pmgiGroupNameCn;
    }

    public void setPmgiGroupNameCn(String pmgiGroupNameCn) {
        this.pmgiGroupNameCn = pmgiGroupNameCn;
    }

    public String getRuleBusinessLineCode() {
        return this.ruleBusinessLineCode;
    }

    public void setRuleBusinessLineCode(String businessLineCode) {
        this.ruleBusinessLineCode = businessLineCode;
    }

    public String getRuleBusinessLineName() {
        return this.ruleBusinessLineName;
    }

    public void setRuleBusinessLineName(String ruleBusinessLineName) {
        this.ruleBusinessLineName = ruleBusinessLineName;
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

    public Integer getGroupOrder() {
        return this.groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
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
