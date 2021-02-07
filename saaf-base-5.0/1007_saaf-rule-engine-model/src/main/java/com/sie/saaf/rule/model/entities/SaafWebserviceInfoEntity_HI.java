package com.sie.saaf.rule.model.entities;


import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;


/**
 * SaafWebserviceInfoEntity_HI Entity Object
 * Wed Jul 05 14:59:06 CST 2017  Auto Generate
 */
@Entity
@Table(name = "saaf_webservice_info")
public class SaafWebserviceInfoEntity_HI {
    private Integer webserviceId;
    private String businessLineCode;
    private String webserviceCode;
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
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    public void setWebserviceId(Integer webserviceId) {
        this.webserviceId = webserviceId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_SAAF_WEBSERVICE_INFO", sequenceName = "SEQ_SAAF_WEBSERVICE_INFO", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_SAAF_WEBSERVICE_INFO", strategy = GenerationType.SEQUENCE)
    @Column(name = "webservice_id", nullable = false, length = 11)
    public Integer getWebserviceId() {
        return webserviceId;
    }

    public void setBusinessLineCode(String businessLineCode) {
        this.businessLineCode = businessLineCode;
    }

    @Column(name = "business_line_code", nullable = true, length = 100)
    public String getBusinessLineCode() {
        return businessLineCode;
    }

    public void setWebserviceCode(String webserviceCode) {
        this.webserviceCode = webserviceCode;
    }

    @Column(name = "webservice_code", nullable = false, length = 100)
    public String getWebserviceCode() {
        return webserviceCode;
    }

    public void setWebserviceUrl(String webserviceUrl) {
        this.webserviceUrl = webserviceUrl;
    }

    @Column(name = "webservice_url", nullable = false, length = 3000)
    public String getWebserviceUrl() {
        return webserviceUrl;
    }

    public void setWebserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
    }

    @Column(name = "webservice_name", nullable = true, length = 100)
    public String getWebserviceName() {
        return webserviceName;
    }

    public void setWebserviceDesc(String webserviceDesc) {
        this.webserviceDesc = webserviceDesc;
    }

    @Column(name = "webservice_desc", nullable = true, length = 3000)
    public String getWebserviceDesc() {
        return webserviceDesc;
    }

    public void setWebsericeAgreement(String websericeAgreement) {
        this.websericeAgreement = websericeAgreement;
    }

    @Column(name = "webserice_agreement", nullable = true, length = 10)
    public String getWebsericeAgreement() {
        return websericeAgreement;
    }

    public void setWebserviceType(String webserviceType) {
        this.webserviceType = webserviceType;
    }

    @Column(name = "webservice_type", nullable = true, length = 10)
    public String getWebserviceType() {
        return webserviceType;
    }

    public void setRequestParamDemo(String requestParamDemo) {
        this.requestParamDemo = requestParamDemo;
    }

    @Column(name = "request_param_demo", nullable = true, length = 3000)
    public String getRequestParamDemo() {
        return requestParamDemo;
    }

    public void setResponseParamDemo(String responseParamDemo) {
        this.responseParamDemo = responseParamDemo;
    }

    @Column(name = "response_param_demo", nullable = true, length = 3000)
    public String getResponseParamDemo() {
        return responseParamDemo;
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

    @Column(name = "creation_date", nullable = true, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by", nullable = true, length = 11)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Date lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_updated_by", nullable = true, length = 0)
    public Date getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_date", nullable = true, length = 0)
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

