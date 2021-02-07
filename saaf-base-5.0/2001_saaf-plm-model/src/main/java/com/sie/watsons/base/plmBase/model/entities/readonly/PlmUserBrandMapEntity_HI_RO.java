package com.sie.watsons.base.plmBase.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Greate Summer on 2020/11/19.
 */
public class PlmUserBrandMapEntity_HI_RO {

    public static final String QUERY_USER_BRAND_MAP_SQL = "SELECT map.MAP_ID mapId, map.SUP_USER_ID supUserId, "
            + " map.BRAND_CN brandCn,map.BRAND_CN_UDA_ID brandCnUdaId,map.BRAND_CN_UDA_VALUE brandCnUdaValue, \n"
            + " map.BRAND_EN brandEn,map.BRAND_EN_UDA_ID brandEnUdaId,map.BRAND_EN_UDA_VALUE brandEnUdaValue, \n"
            + " case map.MASTERRIGHT when 0 then '否'  when 1 then '是' else '否' end as isMasterright,"
            //+ " to_char(map.LAST_UPDATE_DATE, 'yyyy-MM-dd HH:mm:ss') as lastUpdateDate, "
            + " map.LAST_UPDATE_DATE lastUpdateDate, "
            + " map.USER_Email userEmail, "
            + " map.PROFILE_CODE profileCode, "
            + " map.GROUP_BRAND_ID groupBrandId, "
            + " map.MOTHER_COMPANY_ID motherCompanyId, "
            + " map.MOTHER_COMPANY_NAME motherCompanyName, "
            + " map.GROUP_BRAND_NAME groupBrandName, "
            + " map.USER_NAME userName "
            //+ " buser.USER_NAME userName "
            + " FROM PLM_USER_BRAND_MAP map "
          //  + " LEFT JOIN BASE_USERS buser ON map.SUP_USER_ID = buser.USER_ID "
            + " WHERE map.DELETE_FLAG = 0 AND map.STATUS = 10 ";

    private Integer mapId;
    private Integer supUserId;
    private Integer brandMapId;
    private Integer brandInfoId;
    private Integer groupBrandId;
    private Integer motherCompanyId;
    private String brandCn;
    private Integer brandCnUdaId;
    private Integer brandCnUdaValue;
    private String brandEn;
    private Integer brandEnUdaId;
    private Integer brandEnUdaValue;
    private Integer createdBy;
    private Integer masterright;
    private String isMasterright;
    private Integer status = 0;  //是否生效，审批通过后 10:生效
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer deleteFlag = 0;
    private Integer versionNum;
    private Integer operatorUserId;
    private String userName;
    private String userEmail;
    private String profileCode;
    private String motherCompanyName;
    private String groupBrandName;

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public String getIsMasterright() {
        return isMasterright;
    }

    public void setIsMasterright(String isMasterright) {
        this.isMasterright = isMasterright;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getSupUserId() {
        return supUserId;
    }

    public void setSupUserId(Integer supUserId) {
        this.supUserId = supUserId;
    }

    public Integer getBrandMapId() {
        return brandMapId;
    }

    public void setBrandMapId(Integer brandMapId) {
        this.brandMapId = brandMapId;
    }

    public Integer getBrandInfoId() {
        return brandInfoId;
    }

    public void setBrandInfoId(Integer brandInfoId) {
        this.brandInfoId = brandInfoId;
    }

    public Integer getGroupBrandId() {
        return groupBrandId;
    }

    public void setGroupBrandId(Integer groupBrandId) {
        this.groupBrandId = groupBrandId;
    }

    public Integer getMotherCompanyId() {
        return motherCompanyId;
    }

    public void setMotherCompanyId(Integer motherCompanyId) {
        this.motherCompanyId = motherCompanyId;
    }

    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }

    public Integer getBrandCnUdaId() {
        return brandCnUdaId;
    }

    public void setBrandCnUdaId(Integer brandCnUdaId) {
        this.brandCnUdaId = brandCnUdaId;
    }

    public Integer getBrandCnUdaValue() {
        return brandCnUdaValue;
    }

    public void setBrandCnUdaValue(Integer brandCnUdaValue) {
        this.brandCnUdaValue = brandCnUdaValue;
    }

    public String getBrandEn() {
        return brandEn;
    }

    public void setBrandEn(String brandEn) {
        this.brandEn = brandEn;
    }

    public Integer getBrandEnUdaId() {
        return brandEnUdaId;
    }

    public void setBrandEnUdaId(Integer brandEnUdaId) {
        this.brandEnUdaId = brandEnUdaId;
    }

    public Integer getBrandEnUdaValue() {
        return brandEnUdaValue;
    }

    public void setBrandEnUdaValue(Integer brandEnUdaValue) {
        this.brandEnUdaValue = brandEnUdaValue;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getMasterright() {
        return masterright;
    }

    public void setMasterright(Integer masterright) {
        this.masterright = masterright;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMotherCompanyName() {
        return motherCompanyName;
    }

    public void setMotherCompanyName(String motherCompanyName) {
        this.motherCompanyName = motherCompanyName;
    }

    public String getGroupBrandName() {
        return groupBrandName;
    }

    public void setGroupBrandName(String groupBrandName) {
        this.groupBrandName = groupBrandName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }
}
