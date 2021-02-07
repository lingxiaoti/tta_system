package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class TtaOsdBaseLineEntity_HI_MODEL {

    @ExcelIgnore
    private Integer osdBaseLineId;

    @ExcelProperty(value = "产品编号")
    private String itemCode;

    @ExcelProperty(value = "生效区域店铺计数")
    private Integer storesNum;

    @ExcelIgnore
    private Integer timeDimension;

    @ExcelIgnore
    private Integer createdBy;

    @ExcelIgnore
    private Integer lastUpdatedBy;

    @ExcelIgnore
    private Date lastUpdateDate;

    @ExcelIgnore
    private Date creationDate;

    @ExcelIgnore
    private Integer lastUpdateLogin;

    @ExcelIgnore
    private Integer versionNum;

    @ExcelProperty(value = "年份")
    private Integer osdYear;

    @ExcelProperty(value = "促销位置")
    private String promotionLocation;

    @ExcelProperty(value = "促销结束日期")
    private Date promotionEndDate;

    @ExcelProperty(value = "促销开始日期")
    private Date promotionStartDate;

    @ExcelProperty(value = "促销档期")
    private String promotionSection;

    @ExcelProperty(value = "部门名称")
    private String deptName;

    public Integer getOsdBaseLineId() {
        return osdBaseLineId;
    }

    public void setOsdBaseLineId(Integer osdBaseLineId) {
        this.osdBaseLineId = osdBaseLineId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getStoresNum() {
        return storesNum;
    }

    public void setStoresNum(Integer storesNum) {
        this.storesNum = storesNum;
    }

    public Integer getTimeDimension() {
        return timeDimension;
    }

    public void setTimeDimension(Integer timeDimension) {
        this.timeDimension = timeDimension;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getOsdYear() {
        return osdYear;
    }

    public void setOsdYear(Integer osdYear) {
        this.osdYear = osdYear;
    }

    public String getPromotionLocation() {
        return promotionLocation;
    }

    public void setPromotionLocation(String promotionLocation) {
        this.promotionLocation = promotionLocation;
    }

    public Date getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionEndDate(Date promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

    public Date getPromotionStartDate() {
        return promotionStartDate;
    }

    public void setPromotionStartDate(Date promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }

    public String getPromotionSection() {
        return promotionSection;
    }

    public void setPromotionSection(String promotionSection) {
        this.promotionSection = promotionSection;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
