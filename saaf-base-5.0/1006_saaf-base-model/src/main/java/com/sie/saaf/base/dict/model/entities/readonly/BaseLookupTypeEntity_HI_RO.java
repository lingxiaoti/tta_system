package com.sie.saaf.base.dict.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseLookupValuesEntity_HI_RO Entity Object
 * Wed Dec 06 10:52:15 CST 2017  Auto Generate
 */

public class BaseLookupTypeEntity_HI_RO {

    public static final String query="SELECT   blt.lookup_type_id AS lookupTypeId,   blt.lookup_type AS  lookupType,   blt.meaning AS meaning,   blt.customization_level AS customizationLevel,   blt.system_code AS systemCode,   bss.system_name AS systemName,   blt.creation_date AS creationDate,   blt.version_num AS versionNum,   blv.meaning AS customizationLevelMeaning,   blt.parent_type_id AS parentTypeId , blt.description as description,  bltp.lookup_type AS  pLookupType,   bltp.meaning AS pMeaning, blt.parent_lookup_type_id AS parentLookupTypeId  FROM base_lookup_types blt LEFT JOIN base_lookup_types bltp ON blt.parent_lookup_type_id = bltp.lookup_type_id  JOIN base_lookup_values blv     ON blv.lookup_type = 'CUSTOMIZATION_LEVEL' AND blt.customization_level = blv.lookup_code   LEFT JOIN base_system_sso bss ON bss.system_code = blt.system_code  WHERE blt.delete_flag='0' ";
    private Integer lookupTypeId;
    private String lookupType;
    private String meaning;
    private String customizationLevel;
    private String systemCode;
    private String systemName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer versionNum;
    private String customizationLevelMeaning;
    private Integer parentTypeId;
    private Integer parentLookupTypeId;
    private String description;   //描述
    private String pLookupType;  // 父级
    private String pMeaning;     // 父级


    public Integer getLookupTypeId() {
        return lookupTypeId;
    }

    public void setLookupTypeId(Integer lookupTypeId) {
        this.lookupTypeId = lookupTypeId;
    }

    public String getLookupType() {
        return lookupType;
    }

    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getCustomizationLevel() {
        return customizationLevel;
    }

    public void setCustomizationLevel(String customizationLevel) {
        this.customizationLevel = customizationLevel;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getCustomizationLevelMeaning() {
        return customizationLevelMeaning;
    }

    public void setCustomizationLevelMeaning(String customizationLevelMeaning) {
        this.customizationLevelMeaning = customizationLevelMeaning;
    }

    public Integer getParentLookupTypeId() {
        return parentLookupTypeId;
    }

    public void setParentLookupTypeId(Integer parentLookupTypeId) {
        this.parentLookupTypeId = parentLookupTypeId;
    }

    public Integer getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Integer parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getpLookupType() {
        return pLookupType;
    }

    public void setpLookupType(String pLookupType) {
        this.pLookupType = pLookupType;
    }

    public String getpMeaning() {
        return pMeaning;
    }

    public void setpMeaning(String pMeaning) {
        this.pMeaning = pMeaning;
    }

}
