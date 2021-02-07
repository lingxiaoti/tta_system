package com.sie.watsons.base.api.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * VmiPromotionStoreEntity_HI_RO Entity Object
 * Fri Dec 20 11:47:46 CST 2019  Auto Generate
 */

public class VmiPromotionStoreEntity_HI_RO {
    public static final String QUERY_SQL = "select distinct t.offercode, t.packageId, t.packageDesc from (select \n" +
            "t.PROMOTION_NO packageId,\n" +
            "t.OFFER_CODE offerCode,\n" +
            "vp.prom_number packageDesc\n" +
            "from VMI_PROMOTION_STORE t\n" +
            "LEFT JOIN vmi_promotion vp on t.promotion_no = vp.promotion_no\n" +
            "where 1=1 and t.PROMOTION_NO=:packageId";


    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer id;
    private Integer vsId;
    private String packageId;
    private String packageDesc;
    private String promotionNo;
    private String promNumber;
    private String offerCode;
    private String vsCode;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;


    public String getPromNumber() {
        return promNumber;
    }

    public void setPromNumber(String promNumber) {
        this.promNumber = promNumber;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setVsId(Integer vsId) {
        this.vsId = vsId;
    }


    public Integer getVsId() {
        return vsId;
    }

    public void setPromotionNo(String promotionNo) {
        this.promotionNo = promotionNo;
    }


    public String getPromotionNo() {
        return promotionNo;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }


    public String getOfferCode() {
        return offerCode;
    }

    public void setVsCode(String vsCode) {
        this.vsCode = vsCode;
    }


    public String getVsCode() {
        return vsCode;
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

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
