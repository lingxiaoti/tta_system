package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class TtaSalesSiteEntity_HI_MODEL {

    @ExcelIgnore
    private Integer salesSiteId;
    @ExcelProperty(value = "促销位置")
    private String salesSite;
    @ExcelProperty(value = "促销位置-2")
    private String salesSite2;
    @ExcelIgnore
    private String promotionGuideline;
    @ExcelIgnore
    private String working;
    @ExcelIgnore
    private String status;
    @ExcelIgnore
    private Date creationDate;
    @ExcelIgnore
    private Integer createdBy;
    @ExcelIgnore
    private Integer lastUpdatedBy;
    @ExcelIgnore
    private Date lastUpdateDate;
    @ExcelIgnore
    private Integer lastUpdateLogin;

    @ExcelProperty(value = "部门")
    private String dept;

    @ExcelProperty(value = "供应商")
    private String supplierCode;

    @ExcelProperty(value = "年度")
    private Integer salesYear;

    @ExcelProperty(value = "收费标准序号")
    @NumberFormat("#.####")
    private String deptItemNo;

    @ExcelProperty(value = "收费标准")
    private String deptItemName;

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
