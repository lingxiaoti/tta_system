package com.sie.saaf.rule.model.entities;


import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;


/**
 * SaafWebserviceParamInfoEntity_HI Entity Object
 * Wed Jul 05 14:59:07 CST 2017  Auto Generate
 */
@Entity
@Table(name = "saaf_webservice_param_info")
public class SaafWebserviceParamInfoEntity_HI {
    private Integer paramId;
    private String webserviceCode;
    private String paramCode;
    private String paramName;
    private String paramDesc;
    private String paramType;
    private String requiredFlag;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_SAAF_WEBSERVICE_PARAM_INFO", sequenceName = "SEQ_SAAF_WEBSERVICE_PARAM_INFO", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_SAAF_WEBSERVICE_PARAM_INFO", strategy = GenerationType.SEQUENCE)
    @Column(name = "param_id", nullable = false, length = 11)
    public Integer getParamId() {
        return paramId;
    }

    public void setWebserviceCode(String webserviceCode) {
        this.webserviceCode = webserviceCode;
    }

    @Column(name = "webservice_code", nullable = false, length = 100)
    public String getWebserviceCode() {
        return webserviceCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    @Column(name = "param_code", nullable = false, length = 80)
    public String getParamCode() {
        return paramCode;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Column(name = "param_name", nullable = true, length = 100)
    public String getParamName() {
        return paramName;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    @Column(name = "param_desc", nullable = true, length = 500)
    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    @Column(name = "param_type", nullable = true, length = 50)
    public String getParamType() {
        return paramType;
    }

    public void setRequiredFlag(String requiredFlag) {
        this.requiredFlag = requiredFlag;
    }

    @Column(name = "required_flag", nullable = true, length = 10)
    public String getRequiredFlag() {
        return requiredFlag;
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

