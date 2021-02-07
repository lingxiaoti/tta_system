package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.sie.saaf.common.bean.FieldDesc;

import java.math.BigDecimal;
import java.util.Date;

public class TtaCwCheckingEntity_HI_MODEL {
    @ExcelProperty(value ="唯一标识")
    @FieldDesc(isUpdateWhereKey = true)
    private Integer cwId ;
    @ExcelIgnore
    private String promotionSection ;
    @ExcelIgnore
    private Date promotionStartDate ;
    @ExcelIgnore
    private Date promotionEndDate ;
    @ExcelIgnore
    private String promotionLocation ;
    @ExcelIgnore
    private Integer timeDimension ;
    @ExcelIgnore
    private Integer storesNum ;
    @ExcelIgnore
    private String groupDesc ;
    @ExcelIgnore
    private String deptDesc ;
    @ExcelIgnore
    private String deptCode ;
    @ExcelIgnore
    private String groupCode ;
    @ExcelIgnore
    private String content ;
    @ExcelIgnore
    private String itemCode ;
    @ExcelIgnore
    private String cnName ;
    @ExcelIgnore
    private String brandCn ;
    @ExcelIgnore
    private String brandEn ;
    @ExcelIgnore
    private String priorVendorCode ;
    @ExcelIgnore
    private String priorVendorName ;
    @ExcelIgnore
    private String contractYear ;
    @ExcelIgnore
    private String contractStatus ;
    @ExcelIgnore
    private String chargeStandards ;
    @ExcelIgnore
    private BigDecimal chargeMoney ;
    @ExcelIgnore
    private String chargeUnit ;
    @ExcelIgnore
    private BigDecimal receiveAmount ;
    @ExcelIgnore
    private BigDecimal originalAmount ;
    @ExcelIgnore
    private BigDecimal unconfirmAmount ;
    @ExcelIgnore
    private BigDecimal difference ;
    @ExcelIgnore
    private String collect ;
    @ExcelIgnore
    private String purchase ;
    @ExcelIgnore
    private String exemptionReason ;
    @ExcelIgnore
    private String exemptionReason2 ;
    @ExcelProperty(value ="借记单编号")
    @FieldDesc(isUpdateWhereKey = false)
    private String debitOrderCode ;
    @ExcelProperty(value ="实收金额")
    @FieldDesc(isUpdateWhereKey = false)
    private BigDecimal receipts ;
    @ExcelIgnore
    private String batchCode ;
    @ExcelIgnore
    private Integer transferPerson ;
    @ExcelIgnore
    private String remark ;
    @ExcelIgnore
    private Integer status ;
    @ExcelIgnore
    private Integer parentId ;
    @ExcelIgnore
    private String parentVendorCode ;
    @ExcelIgnore
    private String parentVendorName ;
    @ExcelIgnore
    private Date creationDate ;
    @ExcelIgnore
    private Integer createdBy ;
    @ExcelIgnore
    private Integer lastUpdatedBy ;
    @ExcelIgnore
    private Date lastUpdateDate ;
    @ExcelIgnore
    private Integer lastUpdateLogin ;

    public Integer getCwId() {
        return cwId;
    }

    public void setCwId(Integer cwId) {
        this.cwId = cwId;
    }

    public String getPromotionSection() {
        return promotionSection;
    }

    public void setPromotionSection(String promotionSection) {
        this.promotionSection = promotionSection;
    }

    public Date getPromotionStartDate() {
        return promotionStartDate;
    }

    public void setPromotionStartDate(Date promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }

    public Date getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionEndDate(Date promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

    public String getPromotionLocation() {
        return promotionLocation;
    }

    public void setPromotionLocation(String promotionLocation) {
        this.promotionLocation = promotionLocation;
    }

    public Integer getTimeDimension() {
        return timeDimension;
    }

    public void setTimeDimension(Integer timeDimension) {
        this.timeDimension = timeDimension;
    }

    public Integer getStoresNum() {
        return storesNum;
    }

    public void setStoresNum(Integer storesNum) {
        this.storesNum = storesNum;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }

    public String getBrandEn() {
        return brandEn;
    }

    public void setBrandEn(String brandEn) {
        this.brandEn = brandEn;
    }

    public String getPriorVendorCode() {
        return priorVendorCode;
    }

    public void setPriorVendorCode(String priorVendorCode) {
        this.priorVendorCode = priorVendorCode;
    }

    public String getPriorVendorName() {
        return priorVendorName;
    }

    public void setPriorVendorName(String priorVendorName) {
        this.priorVendorName = priorVendorName;
    }

    public String getContractYear() {
        return contractYear;
    }

    public void setContractYear(String contractYear) {
        this.contractYear = contractYear;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getChargeStandards() {
        return chargeStandards;
    }

    public void setChargeStandards(String chargeStandards) {
        this.chargeStandards = chargeStandards;
    }

    public BigDecimal getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(BigDecimal chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getUnconfirmAmount() {
        return unconfirmAmount;
    }

    public void setUnconfirmAmount(BigDecimal unconfirmAmount) {
        this.unconfirmAmount = unconfirmAmount;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getExemptionReason() {
        return exemptionReason;
    }

    public void setExemptionReason(String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }

    public String getExemptionReason2() {
        return exemptionReason2;
    }

    public void setExemptionReason2(String exemptionReason2) {
        this.exemptionReason2 = exemptionReason2;
    }

    public String getDebitOrderCode() {
        return debitOrderCode;
    }

    public void setDebitOrderCode(String debitOrderCode) {
        this.debitOrderCode = debitOrderCode;
    }

    public BigDecimal getReceipts() {
        return receipts;
    }

    public void setReceipts(BigDecimal receipts) {
        this.receipts = receipts;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Integer getTransferPerson() {
        return transferPerson;
    }

    public void setTransferPerson(Integer transferPerson) {
        this.transferPerson = transferPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentVendorCode() {
        return parentVendorCode;
    }

    public void setParentVendorCode(String parentVendorCode) {
        this.parentVendorCode = parentVendorCode;
    }

    public String getParentVendorName() {
        return parentVendorName;
    }

    public void setParentVendorName(String parentVendorName) {
        this.parentVendorName = parentVendorName;
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
}
