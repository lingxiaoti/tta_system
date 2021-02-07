package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


/**
 * Created by Administrator on Thu Jul 06 17:08:13 CST 2017
 */
public class SaafWebserviceInfoEntity_HI_RO {


    public static final String query =
        "SELECT   swi.webservice_id AS webserviceId,   swi.business_line_code AS businessLineCode,   swi.webservice_code AS webserviceCode,   rbl.rule_business_line_name AS ruleBusinessLineName,   swi.webservice_url AS webserviceUrl,   swi.webservice_name AS webserviceName,   swi.webservice_desc AS webserviceDesc,   swi.webserice_agreement AS websericeAgreement,   swi.webservice_type AS webserviceType,   swi.request_param_demo AS requestParamDemo,   swi.response_param_demo AS responseParamDemo,   swi.version_num AS versionNum,   swi.CREATION_DATE AS creationDate  FROM saaf_webservice_info swi LEFT JOIN rule_business_line rbl ON rbl.rule_business_line_code=swi.business_line_code WHERE 1=1";
    private Integer webserviceId;
    private String businessLineCode;
    private String webserviceCode;
    private String ruleBusinessLineName;
    private String webserviceUrl;
    private String webserviceName;
    private String webserviceDesc;
    private String websericeAgreement;
    private String webserviceType;
    private String requestParamDemo;
    private String responseParamDemo;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public Integer getWebserviceId() {
        return this.webserviceId;
    }

    public void setWebserviceId(Integer webserviceId) {
        this.webserviceId = webserviceId;
    }

    public String getBusinessLineCode() {
        return this.businessLineCode;
    }

    public void setBusinessLineCode(String businessLineCode) {
        this.businessLineCode = businessLineCode;
    }

    public String getWebserviceCode() {
        return this.webserviceCode;
    }

    public void setWebserviceCode(String webserviceCode) {
        this.webserviceCode = webserviceCode;
    }

    public String getRuleBusinessLineName() {
        return this.ruleBusinessLineName;
    }

    public void setRuleBusinessLineName(String ruleBusinessLineName) {
        this.ruleBusinessLineName = ruleBusinessLineName;
    }

    public String getWebserviceUrl() {
        return this.webserviceUrl;
    }

    public void setWebserviceUrl(String webserviceUrl) {
        this.webserviceUrl = webserviceUrl;
    }

    public String getWebserviceName() {
        return this.webserviceName;
    }

    public void setWebserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
    }

    public String getWebserviceDesc() {
        return this.webserviceDesc;
    }

    public void setWebserviceDesc(String webserviceDesc) {
        this.webserviceDesc = webserviceDesc;
    }

    public String getWebsericeAgreement() {
        return this.websericeAgreement;
    }

    public void setWebsericeAgreement(String websericeAgreement) {
        this.websericeAgreement = websericeAgreement;
    }

    public String getWebserviceType() {
        return this.webserviceType;
    }

    public void setWebserviceType(String webserviceType) {
        this.webserviceType = webserviceType;
    }

    public String getRequestParamDemo() {
        return this.requestParamDemo;
    }

    public void setRequestParamDemo(String requestParamDemo) {
        this.requestParamDemo = requestParamDemo;
    }

    public String getResponseParamDemo() {
        return this.responseParamDemo;
    }

    public void setResponseParamDemo(String responseParamDemo) {
        this.responseParamDemo = responseParamDemo;
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
