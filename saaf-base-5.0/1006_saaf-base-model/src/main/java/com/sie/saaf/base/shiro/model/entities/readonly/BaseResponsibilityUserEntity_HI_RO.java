package com.sie.saaf.base.shiro.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.Date;

public class BaseResponsibilityUserEntity_HI_RO {
    public static final String QUERY_RESP_USER_SQL = "select br.responsibility_id   responsibilityId,\n" +
            "       br.responsibility_code responsibilityCode,\n" +
            "       br.responsibility_name responsibilityName,\n" +
            "       br.responsibility_desc responsibilityDesc,\n" +
            "       br.start_date_active   startDateActive,\n" +
            "       br.end_date_active     endDateActive,\n" +
            "       br.system_code         systemCode,\n" +
            "       br.creation_date       creationDate,\n" +
            "       br.created_by          createdBy,\n" +
            "       br.last_updated_by     lastUpdatedBy,\n" +
            "       br.last_update_date    lastUpdateDate,\n" +
            "       br.version_num         versionNum,\n" +
            "       br.last_update_login   lastUpdateLogin\n" +
            "  From base_responsibility br\n" +
            " where sysdate >= br.start_date_active\n" +
            "   and sysdate < nvl(br.end_date_active, sysdate + 1)\n" +
            "   and exists\n" +
            " (select 1\n" +
            "          from base_user_responsibility baseUserResponsibility,\n" +
            "               base_users               baseUsers\n" +
            "         where baseUserResponsibility.Responsibility_Id =\n" +
            "               br.responsibility_id\n" +
            "           and baseUserResponsibility.user_id = baseUsers.user_id\n" +
            "           AND baseUsers.user_id = :varUserId \n" +
            "           AND (baseUsers.start_date - 1) <= CURRENT_DATE\n" +
            "           AND (baseUsers.end_date IS NULL OR\n" +
            "               baseUsers.end_date >= CURRENT_DATE)\n" +
            "           AND (baseUserResponsibility.end_date_active > sysdate or\n" +
            "               baseUserResponsibility.end_date_active is null)\n" +
            "           AND baseUsers.delete_flag = 0)\n";

    private BigInteger responsibilityId;
    private String responsibilityCode;
    private String responsibilityName;
    private String responsibilityDesc;
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive;
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive;
    private String systemCode;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer versionNum;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;

    public BigInteger getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(BigInteger responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public String getResponsibilityCode() {
        return responsibilityCode;
    }

    public void setResponsibilityCode(String responsibilityCode) {
        this.responsibilityCode = responsibilityCode;
    }

    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    public String getResponsibilityDesc() {
        return responsibilityDesc;
    }

    public void setResponsibilityDesc(String responsibilityDesc) {
        this.responsibilityDesc = responsibilityDesc;
    }

    public Date getStartDateActive() {
        return startDateActive;
    }

    public void setStartDateActive(Date startDateActive) {
        this.startDateActive = startDateActive;
    }

    public Date getEndDateActive() {
        return endDateActive;
    }

    public void setEndDateActive(Date endDateActive) {
        this.endDateActive = endDateActive;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
