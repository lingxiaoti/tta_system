package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

public class TtaHwbBaseLineEntity_HI_MODEL {

    @ExcelIgnore
    private Integer hwbBaseLineId;

    @ExcelIgnore
    private String itemCode;

    @ExcelIgnore
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
    private Integer hwbYear;

    @ExcelProperty(value = "促销位置")
    private String promotionLocation;

    @ExcelProperty(value = "促销结束日期")
    private Date promotionEndDate;

    @ExcelProperty(value = "促销开始日期")
    private Date promotionStartDate;

    @ExcelProperty(value = "促销期间")
    private String promotionSection;

    @ExcelIgnore
    private String isCreate;

    @ExcelProperty(value = "主部门")
    private String deptNameMajor;

    @ExcelProperty(value = "AWARD_DESCRIPTION")
    private String descriptionCn;

    @ExcelProperty(value = "AWARD_DESCRIPTION_ON_DM&POP")
    private String awardDescription;

    @ExcelProperty(value = "GROUP_DESC")
    private String groupDesc;

    @ExcelProperty(value = "DEPT_DESC")
    private String deptDesc;

    @ExcelProperty(value = "品牌")
    private String brandCn;

    @ExcelProperty(value = "供应商编号")
    private String vendorCode;

    @ExcelProperty(value = "供应商名称")
    private String vendorName;

    @ExcelProperty(value = "公司标准")
    private BigDecimal companyStandard;

    @ExcelProperty(value = "协定标准")
    private BigDecimal agreementStandard;

    @ExcelProperty(value = "收费标准")
    private String chargeStandards;

    @ExcelProperty(value = "数量")
    private Integer num;

    @ExcelProperty(value = "HWB_TYPE")
    private String hwbType;

    public Integer getHwbBaseLineId() {
        return hwbBaseLineId;
    }

    public void setHwbBaseLineId(Integer hwbBaseLineId) {
        this.hwbBaseLineId = hwbBaseLineId;
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

    public Integer getHwbYear() {
        return hwbYear;
    }

    public void setHwbYear(Integer hwbYear) {
        this.hwbYear = hwbYear;
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

    public String getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(String isCreate) {
        this.isCreate = isCreate;
    }

    public String getDeptNameMajor() {
        return deptNameMajor;
    }

    public void setDeptNameMajor(String deptNameMajor) {
        this.deptNameMajor = deptNameMajor;
    }

    public String getDescriptionCn() {
        return descriptionCn;
    }

    public void setDescriptionCn(String descriptionCn) {
        this.descriptionCn = descriptionCn;
    }

    public String getAwardDescription() {
        return awardDescription;
    }

    public void setAwardDescription(String awardDescription) {
        this.awardDescription = awardDescription;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public BigDecimal getCompanyStandard() {
        return companyStandard;
    }

    public void setCompanyStandard(BigDecimal companyStandard) {
        this.companyStandard = companyStandard;
    }

    public BigDecimal getAgreementStandard() {
        return agreementStandard;
    }

    public void setAgreementStandard(BigDecimal agreementStandard) {
        this.agreementStandard = agreementStandard;
    }

    public String getChargeStandards() {
        return chargeStandards;
    }

    public void setChargeStandards(String chargeStandards) {
        this.chargeStandards = chargeStandards;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getHwbType() {
        return hwbType;
    }

    public void setHwbType(String hwbType) {
        this.hwbType = hwbType;
    }
}
