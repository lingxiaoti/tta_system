package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Admin on 2017/7/5.
 */
public class SaafWebserviceParamInfoEntity_HI_RO {
    public static final String query =
        "SELECT   swpi.param_id AS paramId,   swi.webservice_id AS webserviceId,   swi.webservice_name AS webserviceName,   swpi.webservice_code AS webserviceCode,   swpi.param_code AS paramCode, swpi.param_name     AS paramName,   swpi.param_desc AS paramDesc,   swpi.param_type AS paramType,   swpi.required_flag AS requiredFlag,   swpi.version_num AS versionNum,   swpi.CREATION_DATE AS creationDate  FROM saaf_webservice_param_info swpi LEFT JOIN saaf_webservice_info swi ON swi.webservice_code = swpi.webservice_code WHERE 1=1";
    private Integer paramId;
    private Integer webserviceId;
    private String webserviceName;
    private String webserviceCode;
    private String paramCode;
    private String paramName;
    private String paramDesc;
    private String paramType;
    private String requiredFlag;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getWebserviceId() {
        return webserviceId;
    }

    public void setWebserviceId(Integer webserviceId) {
        this.webserviceId = webserviceId;
    }

    public String getWebserviceName() {
        return webserviceName;
    }

    public void setWebserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
    }

    public String getWebserviceCode() {
        return webserviceCode;
    }

    public void setWebserviceCode(String webserviceCode) {
        this.webserviceCode = webserviceCode;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getRequiredFlag() {
        return requiredFlag;
    }

    public void setRequiredFlag(String requiredFlag) {
        this.requiredFlag = requiredFlag;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
