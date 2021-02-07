package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class TtaPogSpaceLineEntity_HI_MODEL {

    @ExcelIgnore
    private Integer pogSpaceLineId;

    @ExcelProperty(value = "产品编号")
    private String itemCode;

    @ExcelProperty(value = "店铺数量")
    private Integer storesNum;

    @ExcelProperty(value = "周")
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
    private Integer pogYear;

    @ExcelProperty(value = "促销位置")
    private String promotionLocation;

    @ExcelProperty(value = "促销结束日期")
    private Date promotionEndDate;

    @ExcelProperty(value = "促销开始日期")
    private Date promotionStartDate;

    @ExcelProperty(value = "促销期间")
    private String promotionSection;

    @ExcelProperty(value = "部门名称")
    private String deptName;

    public Integer getPogSpaceLineId() {
        return pogSpaceLineId;
    }

    public void setPogSpaceLineId(Integer pogSpaceLineId) {
        this.pogSpaceLineId = pogSpaceLineId;
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

    public Integer getPogYear() {
        return pogYear;
    }

    public void setPogYear(Integer pogYear) {
        this.pogYear = pogYear;
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
