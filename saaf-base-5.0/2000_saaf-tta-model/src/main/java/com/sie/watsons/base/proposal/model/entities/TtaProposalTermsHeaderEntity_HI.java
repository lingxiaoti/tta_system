package com.sie.watsons.base.proposal.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/1/001.
 */
@Entity
@Table(name="tta_proposal_terms_header")
public class TtaProposalTermsHeaderEntity_HI {
    private Float termsHId;
    private String  agreementEdition;
    private String vendorNbr;
    private String  vendorDesc;
    private String deptCode;
    private String deptDesc;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private String salesType;
    private Float consignmentDiscount;
    private Float fcsPurchse;
    private Float gp;
    private String warehouseCode;
    private String warehouseDesc;
    private Float payDays;
    private Float salesUpScale;
    private String buyerCode;
    private String invoiceType;
    private Float redTicketScale;
    private Float fcsSales;
    private String isReturn;
    private String beoiTax;
    private String sourceProposal;
    private String termsVersion;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Float createdBy;
    private Float lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private String lastUpdateLogin;
    private Float versionNum;
    private String termsClass;
    private Integer proposalId;
    private String salesArea;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date actuallyCountDate;
    private String isSplit;
    private BigDecimal splitGp;

    @Id
    @SequenceGenerator(name = "SEQ_TTA_PROPOSAL_TERMS_HEADER", sequenceName = "SEQ_TTA_PROPOSAL_TERMS_HEADER", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_PROPOSAL_TERMS_HEADER", strategy = GenerationType.SEQUENCE)
    @Column(name="terms_h_id", nullable=false, length=22)
    public Float getTermsHId() {
        return termsHId;
    }

    public void setTermsHId(Float termsHId) {
        this.termsHId = termsHId;
    }

    @Column(name="agreement_edition")
    public String getAgreementEdition() {
        return agreementEdition;
    }

    public void setAgreementEdition(String agreementEdition) {
        this.agreementEdition = agreementEdition;
    }

    @Column(name="vendor_nbr")
    public String getVendorNbr() {
        return vendorNbr;
    }

    public void setVendorNbr(String vendorNbr) {
        this.vendorNbr = vendorNbr;
    }

    @Column(name="vendor_desc")
    public String getVendorDesc() {
        return vendorDesc;
    }

    public void setVendorDesc(String vendorDesc) {
        this.vendorDesc = vendorDesc;
    }

    @Column(name="dept_code")
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Column(name="dept_desc")
    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    @Column(name="brand_code")
    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    @Column(name="brand_cn")
    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }

    @Column(name="brand_en")
    public String getBrandEn() {
        return brandEn;
    }

    public void setBrandEn(String brandEn) {
        this.brandEn = brandEn;
    }

    @Column(name="sales_type")
    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    @Column(name="consignment_discount")
    public Float getConsignmentDiscount() {
        return consignmentDiscount;
    }

    public void setConsignmentDiscount(Float consignmentDiscount) {
        this.consignmentDiscount = consignmentDiscount;
    }

    @Column(name="fcs_purchse")
    public Float getFcsPurchse() {
        return fcsPurchse;
    }

    public void setFcsPurchse(Float fcsPurchse) {
        this.fcsPurchse = fcsPurchse;
    }

    @Column(name="gp")
    public Float getGp() {
        return gp;
    }

    public void setGp(Float gp) {
        this.gp = gp;
    }

    @Column(name="warehouse_code")
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Column(name="warehouse_desc")
    public String getWarehouseDesc() {
        return warehouseDesc;
    }

    public void setWarehouseDesc(String warehouseDesc) {
        this.warehouseDesc = warehouseDesc;
    }

    @Column(name="pay_days")
    public Float getPayDays() {
        return payDays;
    }

    public void setPayDays(Float payDays) {
        this.payDays = payDays;
    }

    @Column(name="sales_up_scale")
    public Float getSalesUpScale() {
        return salesUpScale;
    }

    public void setSalesUpScale(Float salesUpScale) {
        this.salesUpScale = salesUpScale;
    }

    @Column(name="buyer_code")
    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    @Column(name="invoice_type")
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Column(name="red_ticket_scale")
    public Float getRedTicketScale() {
        return redTicketScale;
    }

    public void setRedTicketScale(Float redTicketScale) {
        this.redTicketScale = redTicketScale;
    }

    @Column(name="fcs_sales")
    public Float getFcsSales() {
        return fcsSales;
    }

    public void setFcsSales(Float fcsSales) {
        this.fcsSales = fcsSales;
    }

    @Column(name="is_return")
    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    @Column(name="beoi_tax")
    public String getBeoiTax() {
        return beoiTax;
    }

    public void setBeoiTax(String beoiTax) {
        this.beoiTax = beoiTax;
    }

    @Column(name="source_proposal")
    public String getSourceProposal() {
        return sourceProposal;
    }

    public void setSourceProposal(String sourceProposal) {
        this.sourceProposal = sourceProposal;
    }

    @Column(name="terms_version")
    public String getTermsVersion() {
        return termsVersion;
    }

    public void setTermsVersion(String termsVersion) {
        this.termsVersion = termsVersion;
    }

    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="creation_date")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name="created_by")
    public Float getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Float createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="last_updated_by")
    public Float getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Float lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name="last_update_date")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name="last_update_login")
    public String getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(String lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name="version_num")
    public Float getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Float versionNum) {
        this.versionNum = versionNum;
    }

    @Column(name="terms_class")
    public String getTermsClass() {
        return termsClass;
    }

    public void setTermsClass(String termsClass) {
        this.termsClass = termsClass;
    }

    @Column(name="proposal_id")
    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    @Column(name="sales_area")
    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    @Column(name="actually_count_date")
    public Date getActuallyCountDate() {
        return actuallyCountDate;
    }

    public void setActuallyCountDate(Date actuallyCountDate) {
        this.actuallyCountDate = actuallyCountDate;
    }

    @Column(name="is_split")
    public String getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(String isSplit) {
        this.isSplit = isSplit;
    }

    @Column(name="split_gp", nullable=true, length=22)
    public BigDecimal getSplitGp() {
        return splitGp;
    }

    public void setSplitGp(BigDecimal splitGp) {
        this.splitGp = splitGp;
    }
}
