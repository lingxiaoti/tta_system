package com.sie.watsons.base.proposal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTermsHEntity_HI Entity Object
 * Tue Jun 04 08:38:53 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_PROPOSAL_TERMS_HEADER")
public class TtaTermsHEntity_HI {
    private Integer termsHId;
    private String agreementEdition;
    private String vendorNbr;
    private String vendorDesc;
    private String deptCode;
    private String deptDesc;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private String salesType;
    private BigDecimal consignmentDiscount;
    private BigDecimal fcsPurchse;
    private BigDecimal gp;
	private String salesArea;
    private String warehouseCode;
    private String warehouseDesc;
    private BigDecimal payDays;
    private BigDecimal salesUpScale;
    private String buyerCode;
    private String invoiceType;
    private BigDecimal redTicketScale;
    private BigDecimal fcsSales;
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
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer proposalId;
    private Integer operatorUserId;
	private String termsClass;
	private String returnConditions;
	private Integer isFill ;
	private BigDecimal fcsSplitPurchse ;
	private BigDecimal fcsSplitSales ;
	private String isSplit ;
	private Integer isSplitFlag ;
	private String splitVendorNbr;
	private BigDecimal splitGp;
	private String recordRunVendor;

	public void setTermsHId(Integer termsHId) {
		this.termsHId = termsHId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_PROPOSAL_TERMS_HEADER", sequenceName = "SEQ_TTA_PROPOSAL_TERMS_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_PROPOSAL_TERMS_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="terms_h_id", nullable=false, length=22)	
	public Integer getTermsHId() {
		return termsHId;
	}

	public void setAgreementEdition(String agreementEdition) {
		this.agreementEdition = agreementEdition;
	}

	@Column(name="agreement_edition", nullable=false, length=50)	
	public String getAgreementEdition() {
		return agreementEdition;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=false, length=30)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorDesc(String vendorDesc) {
		this.vendorDesc = vendorDesc;
	}

	@Column(name="vendor_desc", nullable=false, length=230)	
	public String getVendorDesc() {
		return vendorDesc;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=false, length=30)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=false, length=230)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=true, length=30)	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=false, length=230)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=false, length=230)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	@Column(name="sales_type", nullable=true, length=10)	
	public String getSalesType() {
		return salesType;
	}

	public void setConsignmentDiscount(BigDecimal consignmentDiscount) {
		this.consignmentDiscount = consignmentDiscount;
	}

	@Column(name="consignment_discount", precision = 10, scale = 2)
	public BigDecimal getConsignmentDiscount() {
		return consignmentDiscount;
	}

	public void setFcsPurchse(BigDecimal fcsPurchse) {
		this.fcsPurchse = fcsPurchse;
	}

	@Column(name="fcs_purchse", nullable=true, length=22)	
	public BigDecimal getFcsPurchse() {
		return fcsPurchse;
	}
	@Column(name="gp", nullable=true, length=22)
	public BigDecimal getGp() {
		return gp;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column(name="warehouse_code", nullable=true, length=30)
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseDesc(String warehouseDesc) {
		this.warehouseDesc = warehouseDesc;
	}

	@Column(name="warehouse_desc", nullable=true, length=230)
	public String getWarehouseDesc() {
		return warehouseDesc;
	}

	public void setPayDays(BigDecimal payDays) {
		this.payDays = payDays;
	}

	@Column(name="pay_days", nullable=true, length=22)	
	public BigDecimal getPayDays() {
		return payDays;
	}

	public void setSalesUpScale(BigDecimal salesUpScale) {
		this.salesUpScale = salesUpScale;
	}

	@Column(name="sales_up_scale", nullable=true, length=22)	
	public BigDecimal getSalesUpScale() {
		return salesUpScale;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	@Column(name="buyer_code", nullable=true, length=30)	
	public String getBuyerCode() {
		return buyerCode;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name="invoice_type", nullable=true, length=30)	
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setRedTicketScale(BigDecimal redTicketScale) {
		this.redTicketScale = redTicketScale;
	}

	@Column(name="red_ticket_scale", nullable=true, length=22)	
	public BigDecimal getRedTicketScale() {
		return redTicketScale;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	@Column(name="fcs_sales", nullable=true, length=22)	
	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	@Column(name="is_return", nullable=true, length=1)	
	public String getIsReturn() {
		return isReturn;
	}

	public void setBeoiTax(String beoiTax) {
		this.beoiTax = beoiTax;
	}

	@Column(name="beoi_tax", nullable=true, length=30)	
	public String getBeoiTax() {
		return beoiTax;
	}

	public void setSourceProposal(String sourceProposal) {
		this.sourceProposal = sourceProposal;
	}

	@Column(name="source_proposal", nullable=true, length=50)	
	public String getSourceProposal() {
		return sourceProposal;
	}

	public void setTermsVersion(String termsVersion) {
		this.termsVersion = termsVersion;
	}

	@Column(name="terms_version", nullable=true, length=20)	
	public String getTermsVersion() {
		return termsVersion;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2030)	
	public String getRemark() {
		return remark;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}



	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="sales_area", nullable=true, length=200)
	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	@Column(name="terms_class", nullable=true, length=130)
	public String getTermsClass() {
		return termsClass;
	}

	public void setTermsClass(String termsClass) {
		this.termsClass = termsClass;
	}

	@Column(name="return_conditions", nullable=true, length=10)
	public String getReturnConditions() {
		return returnConditions;
	}

	public void setReturnConditions(String returnConditions) {
		this.returnConditions = returnConditions;
	}

	@Column(name="is_fill")
	public Integer getIsFill() {
		return isFill;
	}

	public void setIsFill(Integer isFill) {
		this.isFill = isFill;
	}

	@Column(name="fcs_split_purchse")
	public BigDecimal getFcsSplitPurchse() {
		return fcsSplitPurchse;
	}

	public void setFcsSplitPurchse(BigDecimal fcsSplitPurchse) {
		this.fcsSplitPurchse = fcsSplitPurchse;
	}
	@Column(name="fcs_split_sales")
	public BigDecimal getFcsSplitSales() {
		return fcsSplitSales;
	}

	public void setFcsSplitSales(BigDecimal fcsSplitSales) {
		this.fcsSplitSales = fcsSplitSales;
	}

	@Column(name="is_split")
	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	@Column(name="is_split_flag")
	public Integer getIsSplitFlag() {
		return isSplitFlag;
	}

	public void setIsSplitFlag(Integer isSplitFlag) {
		this.isSplitFlag = isSplitFlag;
	}

	@Column(name="split_vendor_nbr",nullable=true, length=130)
	public String getSplitVendorNbr() {
		return splitVendorNbr;
	}

	public void setSplitVendorNbr(String splitVendorNbr) {
		this.splitVendorNbr = splitVendorNbr;
	}

	@Column(name="split_gp", nullable=true, length=22)
	public BigDecimal getSplitGp() {
		return splitGp;
	}

	public void setSplitGp(BigDecimal splitGp) {
		this.splitGp = splitGp;
	}

	@Column(name="record_run_vendor", nullable=true, length=500)
	public String getRecordRunVendor() {
		return recordRunVendor;
	}

	public void setRecordRunVendor(String recordRunVendor) {
		this.recordRunVendor = recordRunVendor;
	}
}
