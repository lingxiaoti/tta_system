package com.sie.watsons.base.proposal.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/1/001.
 */
public class TtaProposalTermsHeaderEntity_HI_RO {
    private Integer termsHId;
    private String  agreementEdition;
    private String vendorNbr;
    private String  vendorDesc;
    private String deptCode;
    private String deptDesc;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private String salesType;
    private Integer consignmentDiscount;
    private Integer fcsPurchse;
    private Integer gp;
    private String warehouseCode;
    private String warehouseDesc;
    private Integer payDays;
    private Integer salesUpScale;
    private String buyerCode;
    private String invoiceType;
    private Integer redTicketScale;
    private Integer fcsSales;
    private String isReturn;
    private String beoiTax;
    private String sourceProposal;
    private String termsVersion;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private String lastUpdateLogin;
    private Integer versionNum;
    private String termsClass;
    private Integer proposalId;
    private String salesArea;
    private String isSplit;
    private BigDecimal splitGp;

    public Integer getTermsHId() {
        return termsHId;
    }

    public void setTermsHId(Integer termsHId) {
        this.termsHId = termsHId;
    }

    public String getAgreementEdition() {
        return agreementEdition;
    }

    public void setAgreementEdition(String agreementEdition) {
        this.agreementEdition = agreementEdition;
    }

    public String getVendorNbr() {
        return vendorNbr;
    }

    public void setVendorNbr(String vendorNbr) {
        this.vendorNbr = vendorNbr;
    }

    public String getVendorDesc() {
        return vendorDesc;
    }

    public void setVendorDesc(String vendorDesc) {
        this.vendorDesc = vendorDesc;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
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

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public Integer getConsignmentDiscount() {
        return consignmentDiscount;
    }

    public void setConsignmentDiscount(Integer consignmentDiscount) {
        this.consignmentDiscount = consignmentDiscount;
    }

    public Integer getFcsPurchse() {
        return fcsPurchse;
    }

    public void setFcsPurchse(Integer fcsPurchse) {
        this.fcsPurchse = fcsPurchse;
    }

    public Integer getGp() {
        return gp;
    }

    public void setGp(Integer gp) {
        this.gp = gp;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseDesc() {
        return warehouseDesc;
    }

    public void setWarehouseDesc(String warehouseDesc) {
        this.warehouseDesc = warehouseDesc;
    }

    public Integer getPayDays() {
        return payDays;
    }

    public void setPayDays(Integer payDays) {
        this.payDays = payDays;
    }

    public Integer getSalesUpScale() {
        return salesUpScale;
    }

    public void setSalesUpScale(Integer salesUpScale) {
        this.salesUpScale = salesUpScale;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getRedTicketScale() {
        return redTicketScale;
    }

    public void setRedTicketScale(Integer redTicketScale) {
        this.redTicketScale = redTicketScale;
    }

    public Integer getFcsSales() {
        return fcsSales;
    }

    public void setFcsSales(Integer fcsSales) {
        this.fcsSales = fcsSales;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getBeoiTax() {
        return beoiTax;
    }

    public void setBeoiTax(String beoiTax) {
        this.beoiTax = beoiTax;
    }

    public String getSourceProposal() {
        return sourceProposal;
    }

    public void setSourceProposal(String sourceProposal) {
        this.sourceProposal = sourceProposal;
    }

    public String getTermsVersion() {
        return termsVersion;
    }

    public void setTermsVersion(String termsVersion) {
        this.termsVersion = termsVersion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(String lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getTermsClass() {
        return termsClass;
    }

    public void setTermsClass(String termsClass) {
        this.termsClass = termsClass;
    }

    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    public String getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(String isSplit) {
        this.isSplit = isSplit;
    }

    public BigDecimal getSplitGp() {
        return splitGp;
    }

    public void setSplitGp(BigDecimal splitGp) {
        this.splitGp = splitGp;
    }
}
