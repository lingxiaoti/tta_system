package com.sie.watsons.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/16/016.
 */
@Entity
@Table(name="TTA_OSD_SALES_SITE")
public class TtaSalesSiteEntity_HI {
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
    private Integer salesYear;
    private String deptItemNo;
    private String deptItemName;
    private Integer versionNum;
    private String dept;
    private String supplierCode;
    @Id
    @SequenceGenerator(name = "SEQ_TTA_OSD_SALES_SITE", sequenceName = "SEQ_TTA_OSD_SALES_SITE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_OSD_SALES_SITE", strategy = GenerationType.SEQUENCE)
    @Column(name="SALES_SITE_ID", nullable=false, length=22)
    public Integer getSalesSiteId() {
        return salesSiteId;
    }

    public void setSalesSiteId(Integer salesSiteId) {
        this.salesSiteId = salesSiteId;
    }

    @Column(name="SALES_SITE")
    public String getSalesSite() {
        return salesSite;
    }

    public void setSalesSite(String salesSite) {
        this.salesSite = salesSite;
    }
    @Column(name="SALES_SITE2")
    public String getSalesSite2() {
        return salesSite2;
    }

    public void setSalesSite2(String salesSite2) {
        this.salesSite2 = salesSite2;
    }

    @Column(name="PROMOTION_GUIDELINE")
    public String getPromotionGuideline() {
        return promotionGuideline;
    }

    public void setPromotionGuideline(String promotionGuideline) {
        this.promotionGuideline = promotionGuideline;
    }

    @Column(name="WORKING")
    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    @Column(name="STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name="CREATION_DATE")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name="CREATED_BY")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="LAST_UPDATED_BY")
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name="LAST_UPDATE_DATE")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name="LAST_UPDATE_LOGIN")
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name="SALES_YEAR")
    public Integer getSalesYear() {
        return salesYear;
    }

    public void setSalesYear(Integer salesYear) {
        this.salesYear = salesYear;
    }

    @Column(name="DEPT_ITEM_NO")
    public String getDeptItemNo() {
        return deptItemNo;
    }

    public void setDeptItemNo(String deptItemNo) {
        this.deptItemNo = deptItemNo;
    }

    @Column(name="DEPT_ITEM_NAME")
    public String getDeptItemName() {
        return deptItemName;
    }

    public void setDeptItemName(String deptItemName) {
        this.deptItemName = deptItemName;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name="version_num", nullable=true, length=22)
    public Integer getVersionNum() {
        return versionNum;
    }

    @Column(name="DEPT")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Column(name="SUPPLIER_CODE")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
}
