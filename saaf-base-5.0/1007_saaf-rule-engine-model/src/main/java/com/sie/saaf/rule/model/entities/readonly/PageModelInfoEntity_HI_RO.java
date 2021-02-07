package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


/**
 * Created by Administrator on Thu Jul 06 17:24:57 CST 2017
 */
public class PageModelInfoEntity_HI_RO {


    public static final String query =
        "SELECT   pmi.model_id AS modelId,   pmi.rule_business_line_code AS ruleBusinessLineCode,   rbl.rule_business_line_name AS ruleBusinessLineName,   pmi.model_code AS modelCode,   pmi.model_name AS modelName,   pmi.model_desc AS modelDesc,   pmi.version_num AS versionNum,   pmi.CREATION_DATE AS creationDate  FROM page_model_info pmi LEFT JOIN rule_business_line rbl ON rbl.rule_business_line_code = pmi.rule_business_line_code WHERE 1=1";
    private Integer modelId;
    private String ruleBusinessLineCode;
    private String ruleBusinessLineName;
    private String modelCode;
    private String modelName;
    private String modelDesc;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public Integer getModelId() {
        return this.modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
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

    public String getModelDesc() {
        return this.modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
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
