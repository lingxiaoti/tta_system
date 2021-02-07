package com.sie.saaf.base.dict.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseLookupValuesEntity_HI_RO Entity Object
 * Wed Dec 06 10:52:15 CST 2017  Auto Generate
 */

public class BaseLookupValuesEntity_HI_RO {

    public static String DIC_LIST_KEY = "DIC_LIST_KEY";//全量数据字典
    
    public static final String QUERY_DIC_SQL = "SELECT\n" +
            "  baseLookupValues.lookup_code AS lookupCode,\n" +
            "  type.lookup_type_id AS lookupTypeId,\n" +
            "  type.parent_lookup_type_id AS parentLookupTypeId,\n" +
            "  baseLookupValues.lookup_code AS lookupCode,\n" +
            "  baseLookupValues.meaning     AS meaning,\n" +
            "  baseLookupValues.lookup_type     AS lookupType,\n" +
            "  baseLookupValues.lookup_values_id     AS lookupValuesId,\n" +
            "  baseLookupValues.system_code     AS systemCode,\n" +
            "  baseLookupValues.description AS description," +
            "  baseLookupValues.bu_org_id AS buOrgId," +
            "  baseLookupValues.parent_lookup_values_id as parentLookupValuesId \n" +
            " FROM base_lookup_values baseLookupValues\n" +
            " join base_lookup_types type  on baseLookupValues.lookup_type=type.lookup_type and   type.system_code=baseLookupValues.system_code \n" +
            " WHERE 1 = 1 AND baseLookupValues.delete_flag='0' AND trunc(nvl(baseLookupValues.start_date_active,sysdate)) <= trunc(sysdate) AND trunc(nvl(baseLookupValues.end_date_active,sysdate))>=trunc(sysdate)  AND baseLookupValues.enabled_flag = 'Y' ";

    public static final String QUERY_DIC_SQL_LIST = "SELECT\n" +
            "  type.lookup_type_id AS lookupTypeId,\n" +
            "  type.parent_lookup_type_id AS parentLookupTypeId,\n" +
            "  baseLookupValues.lookup_code AS lookupCode,\n" +
            "  baseLookupValues.meaning     AS meaning,\n" +
            "  baseLookupValues.lookup_type     AS lookupType,\n" +
            "  baseLookupValues.lookup_values_id     AS lookupValuesId,\n" +
            "  baseLookupValues.system_code     AS systemCode,\n" +
            "  baseLookupValues.description AS description," +
            "  baseLookupValues.bu_org_id AS buOrgId," +
            "  baseLookupValues.parent_lookup_values_id as parentLookupValuesId \n" +
            " FROM base_lookup_values baseLookupValues\n" +
            " join base_lookup_types type  on baseLookupValues.lookup_type=type.lookup_type and   type.system_code=baseLookupValues.system_code \n" +
            " WHERE 1 = 1 AND baseLookupValues.delete_flag='0' AND trunc(nvl(baseLookupValues.start_date_active,sysdate)) <= trunc(sysdate) AND trunc(nvl(baseLookupValues.end_date_active,sysdate))>=trunc(sysdate)  AND baseLookupValues.enabled_flag = 'Y' ";

    public static final String QUERY_LOOK_DIC_SQL = "SELECT B.lookup_code as lookupCode,B.meaning as description\n" +
            "     FROM base_lookup_types A,\n" +
            "          base_lookup_values B \n" +
            "        WHERE A.lookup_type = B.lookup_type \n" +
            " AND A.lookup_type = :lookUpType\n";
            //"          AND A.last_update_date > :lastUpdateDate\n";

    public static final String QUERY_PARENT_DIC_SQL = "select t2.lookup_type,t2.lookup_code,t2.meaning,t2.lookup_values_id\n" +
            "from base_lookup_types t1,base_lookup_values t2\n" +
            "where t2.lookup_type = t1.lookup_type\n" +
            "and t2.system_code=t1.system_code";

    public static final String QUERY_PARENT_SQL = "SELECT \ta.lookup_values_id lookupValuesId,\n" +
            "\ta.lookup_type lookupType,\n" +
            "\ta.lookup_code lookupCode,\n" +
            "\ta.meaning meaning,\n" +
            "\ta.parent_lookup_values_id parentLookupValuesId,\n" +
            "\ta.start_date_active startDateActive,\n" +
            "\ta.end_date_active endDateActive,\n" +
            "\ta.enabled_flag enabledFlag,\n" +
            "\ta.system_code systemCode,\n" +
            "\ta.version_num versionNum,\n" +
            "\ta.description description,\n" +
            "  a.bu_org_id AS buOrgId," +
            "\tb.lookup_code parentLookupCode,\n" +
            "\tb.meaning parentMeaning \n " +
            "FROM base_lookup_values a LEFT JOIN base_lookup_values b on a.parent_lookup_values_id = b.lookup_values_id \n" +
            "where a.delete_flag='0' ";

    private Integer lookupValuesId; //主键ID
    private String lookupType; //数据字典所属类型
    private Integer lookupTypeId; //数据字典所属类型id
    private Integer parentLookupTypeId; //数据字典所属类型父级id
    private String lookupCode; //数据字典编码
    private String meaning; //说明
    private String description; //描述
    private String parentLookupValuesId; //父节点Id
    private String enabledFlag; //是否启用
    private String buOrgId;//BU所属组织Id
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive; //生效日期
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive; //失效日期
    private String systemCode; //系统编码
    private String languageType; //EN:英文，CN:中文 默认值为CN
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer operatorUserId;

    private String parentLookupCode; // 父级字典编码
    private String parentMeaning;   // 父级说明
    private Integer orderNo;

    public Integer getLookupTypeId() {
        return lookupTypeId;
    }

    public void setLookupTypeId(Integer lookupTypeId) {
        this.lookupTypeId = lookupTypeId;
    }

    public Integer getParentLookupTypeId() {
        return parentLookupTypeId;
    }

    public void setParentLookupTypeId(Integer parentLookupTypeId) {
        this.parentLookupTypeId = parentLookupTypeId;
    }

    public void setLookupValuesId(Integer lookupValuesId) {
        this.lookupValuesId = lookupValuesId;
    }


    public Integer getLookupValuesId() {
        return lookupValuesId;
    }

    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }


    public String getLookupType() {
        return lookupType;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }


    public String getLookupCode() {
        return lookupCode;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }


    public String getMeaning() {
        return meaning;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setParentLookupValuesId(String parentLookupValuesId) {
        this.parentLookupValuesId = parentLookupValuesId;
    }


    public String getParentLookupValuesId() {
        return parentLookupValuesId;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }


    public String getEnabledFlag() {
        return enabledFlag;
    }

    public String getBuOrgId() {
        return buOrgId;
    }


    public void setBuOrgId(String buOrgId) {
        this.buOrgId = buOrgId;
    }


    public void setStartDateActive(Date startDateActive) {
        this.startDateActive = startDateActive;
    }


    public Date getStartDateActive() {
        return startDateActive;
    }

    public void setEndDateActive(Date endDateActive) {
        this.endDateActive = endDateActive;
    }


    public Date getEndDateActive() {
        return endDateActive;
    }

    public String getSystemCode() {
        return systemCode;
    }


    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }


    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }


    public String getLanguageType() {
        return languageType;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }


    public Integer getVersionNum() {
        return versionNum;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public String getParentLookupCode() {
        return parentLookupCode;
    }

    public void setParentLookupCode(String parentLookupCode) {
        this.parentLookupCode = parentLookupCode;
    }

    public String getParentMeaning() {
        return parentMeaning;
    }

    public void setParentMeaning(String parentMeaning) {
        this.parentMeaning = parentMeaning;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }
}
