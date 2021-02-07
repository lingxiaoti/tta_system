package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2019/7/16/016.
 */
public class TtaSalesSiteEntity_HI_RO {

    public static final String  QUERY = "SELECT \n" +
            "\n" +
            "       TOSS.SALES_SITE_ID,\n" +
            "       TOSS.SALES_SITE,\n" +
            "       TOSS.PROMOTION_GUIDELINE,\n" +
            "       TOSS.WORKING,\n" +
            "       TOSS.STATUS,\n" +
            "       TOSS.CREATION_DATE,\n" +
            "       TOSS.CREATED_BY,\n" +
            "       TOSS.LAST_UPDATED_BY,\n" +
            "       TOSS.LAST_UPDATE_DATE,\n" +
            "       TOSS.LAST_UPDATE_LOGIN,\n" +
            "       TOSS.SALES_SITE2,\n" +
            "       TOSS.SALES_YEAR,\n" +
            "       TOSS.DEPT_ITEM_NO,\n" +
            "       TOSS.DEPT_ITEM_NAME,\n" +
            "       TOSS.DEPT,\n" +
            "       TOSS.SUPPLIER_CODE,\n" +
            "       BU.USER_FULL_NAME CREATED_BY_NAME\n" +
            "       \n" +
            "FROM  TTA_OSD_SALES_SITE TOSS\n" +
            "      LEFT JOIN    BASE_USERS BU ON TOSS.CREATED_BY = BU.USER_ID\n" +
            "\n" +
            "WHERE 1=1 ";
    private Integer salesSiteId;
    private String salesSite;
    private String salesSite2;
    private String promotionGuideline;
    private String working;
    private String status;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String createdByName;
    private Integer salesYear;
    private String deptItemNo;
    private String deptItemName;
    private String dept;
    private String supplierCode;


    public static final String TTA_OSD_SALES = "select * from TTA_OSD_SALES_SITE s where 1=1";

    public Integer getSalesSiteId() {
        return salesSiteId;
    }

    public void setSalesSiteId(Integer salesSiteId) {
        this.salesSiteId = salesSiteId;
    }

    public String getSalesSite() {
        return salesSite;
    }

    public void setSalesSite(String salesSite) {
        this.salesSite = salesSite;
    }

    public String getSalesSite2() {
        return salesSite2;
    }

    public void setSalesSite2(String salesSite2) {
        this.salesSite2 = salesSite2;
    }

    public String getPromotionGuideline() {
        return promotionGuideline;
    }

    public void setPromotionGuideline(String promotionGuideline) {
        this.promotionGuideline = promotionGuideline;
    }

    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Integer getSalesYear() {
        return salesYear;
    }

    public void setSalesYear(Integer salesYear) {
        this.salesYear = salesYear;
    }

    public String getDeptItemNo() {
        return deptItemNo;
    }

    public void setDeptItemNo(String deptItemNo) {
        this.deptItemNo = deptItemNo;
    }

    public String getDeptItemName() {
        return deptItemName;
    }

    public void setDeptItemName(String deptItemName) {
        this.deptItemName = deptItemName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
}
