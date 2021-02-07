package com.sie.watsons.base.api.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * VmiPromotionEntity_HI_RO Entity Object
 * Fri Dec 20 09:40:23 CST 2019  Auto Generate
 */

public class VmiPromotionEntity_HI_RO {

    public static final String QUERY_SQL="select \n"+
            "t.ID id,\n"+
            "t.START_DATE startDate,\n"+
            "t.END_DATE endDate,\n"+
            "t.CREATED_BY createdBy,\n"+
            "t.LAST_UPDATED_BY lastUpdatedBy,\n"+
            "t.CREATION_DATE creationDate,\n"+
            "t.LAST_UPDATE_LOGIN lastUpdateLogin,\n"+
            "t.VERSION_NUM versionNum,\n"+
            "t.PROMOTION_NO promotionNo,\n"+
            "t.LAST_UPDATE_DATE lastUpdateDate,\n"+
            "t.PROM_NUMBER promNumber\n"+
            "from VMI_PROMOTION t\n";


    private String promNumber;
    private Integer id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String promotionNo;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer operatorUserId;

    public void setPromNumber(String promNumber) {
        this.promNumber = promNumber;
    }


    public String getPromNumber() {
        return promNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Date getEndDate() {
        return endDate;
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

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }


    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }


    public Integer getVersionNum() {
        return versionNum;
    }

    public void setPromotionNo(String promotionNo) {
        this.promotionNo = promotionNo;
    }


    public String getPromotionNo() {
        return promotionNo;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}
