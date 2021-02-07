package com.sie.watsons.base.proposal.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTermsHEntity_HI_RO Entity Object
 * Tue Jun 04 08:38:53 CST 2019  Auto Generate
 */

public class TtaTermsHEntity_HI_RO {
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
	private String salesArea;
    private BigDecimal consignmentDiscount;
    private BigDecimal fcsPurchse;
    private BigDecimal gp;
    private String warehouseCode;
    private String warehouseDesc;
    private BigDecimal payDays;
    private BigDecimal salesUpScale;
    private String buyerCode;
    private String invoiceType;
    private BigDecimal redTicketScale;
    private BigDecimal fcsSales;
    private String isCrossYear;
	private String isReturn;
	private String beoiTax;
    private String sourceProposal;
    private String termsVersion;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
	private String employeeNumber;
	private String createdByName;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;

    private Integer proposalId;
	private Integer operatorUserId;
	private String termsClass;
	private String proposalYear;

	private Integer oldYear;
	private Integer oldTermsHId;
	private String oldAgreementEdition;
	private String oldVendorNbr;
	private String oldVendorDesc;
	private String oldDeptCode;
	private String oldDeptDesc;
	private String oldBrandCode;
	private String oldBrandCn;
	private String oldBrandEn;
	private String oldSalesType;
	private String oldSalesArea;
	private BigDecimal oldConsignmentDiscount;
	private BigDecimal oldFcsPurchse;
	private BigDecimal oldGp;
	private String oldWarehouseCode;
	private String oldWarehouseDesc;
	private BigDecimal oldPayDays;
	private BigDecimal oldSalesUpScale;
	private String oldBuyerCode;
	private String oldInvoiceType;
	private BigDecimal oldRedTicketScale;
	private BigDecimal oldFcsSales;
	private String oldIsCrossYear;
	private String oldIsReturn;

	private String oldBeoiTax;
	private String oldSourceProposal;
	private String oldTermsVersion;
	private String oldRemark;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date oldCreationDate;
	private Integer oldCreatedBy;
	private String oldEmployeeNumber;
	private String oldCreatedByName;
	private String isTermsConfirm;
	private Integer oldProposalId;
	private String oldTermsClass;
	private String oldProposalYear;
	private String returnConditions;
	private String oldReturnConditions;
	private BigDecimal oldFcsSplitPurchse ;
	private BigDecimal oldFcsSplitSales ;
	private BigDecimal oldSplitGp;
	private String oldIsSplit ;
	private String returnedPurchaseFlag;
	private String returnFreightFlag;
	private String beoiTaxFlag;
	public static final String TTA_ITEM_OLD_V = "select * from TTA_PROPOSAL_TERMS_HEADEROLD_V V where 1=1 ";
	public void setTermsHId(Integer termsHId) {
		this.termsHId = termsHId;
	}
	public Map<String, Object> dicMap = new HashMap<String, Object>();
	
	public Integer getTermsHId() {
		return termsHId;
	}

	public void setAgreementEdition(String agreementEdition) {
		this.agreementEdition = agreementEdition;
	}

	
	public String getAgreementEdition() {
		return agreementEdition;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorDesc(String vendorDesc) {
		this.vendorDesc = vendorDesc;
	}

	
	public String getVendorDesc() {
		return vendorDesc;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	
	public String getBrandEn() {
		return brandEn;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	
	public String getSalesType() {
		return salesType;
	}

	public void setConsignmentDiscount(BigDecimal consignmentDiscount) {
		this.consignmentDiscount = consignmentDiscount;
	}

	
	public BigDecimal getConsignmentDiscount() {
		return consignmentDiscount;
	}

	public void setFcsPurchse(BigDecimal fcsPurchse) {
		this.fcsPurchse = fcsPurchse;
	}

	
	public BigDecimal getFcsPurchse() {
		return fcsPurchse;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	
	public BigDecimal getGp() {
		return gp;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseDesc(String warehouseDesc) {
		this.warehouseDesc = warehouseDesc;
	}

	
	public String getWarehouseDesc() {
		return warehouseDesc;
	}

	public void setPayDays(BigDecimal payDays) {
		this.payDays = payDays;
	}

	
	public BigDecimal getPayDays() {
		return payDays;
	}

	public void setSalesUpScale(BigDecimal salesUpScale) {
		this.salesUpScale = salesUpScale;
	}

	
	public BigDecimal getSalesUpScale() {
		return salesUpScale;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	
	public String getBuyerCode() {
		return buyerCode;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setRedTicketScale(BigDecimal redTicketScale) {
		this.redTicketScale = redTicketScale;
	}

	
	public BigDecimal getRedTicketScale() {
		return redTicketScale;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	
	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	
	public String getIsReturn() {
		return isReturn;
	}

	public void setBeoiTax(String beoiTax) {
		this.beoiTax = beoiTax;
	}

	
	public String getBeoiTax() {
		return beoiTax;
	}

	public void setSourceProposal(String sourceProposal) {
		this.sourceProposal = sourceProposal;
	}

	
	public String getSourceProposal() {
		return sourceProposal;
	}

	public void setTermsVersion(String termsVersion) {
		this.termsVersion = termsVersion;
	}

	
	public String getTermsVersion() {
		return termsVersion;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}


	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	public String getIsCrossYear() {
		return isCrossYear;
	}

	public void setIsCrossYear(String isCrossYear) {
		this.isCrossYear = isCrossYear;
	}

	public String getTermsClass() {
		return termsClass;
	}

	public void setTermsClass(String termsClass) {
		this.termsClass = termsClass;
	}

	public Integer getOldYear() {
		return oldYear;
	}

	public void setOldYear(Integer oldYear) {
		this.oldYear = oldYear;
	}

	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	public Integer getOldTermsHId() {
		return oldTermsHId;
	}

	public void setOldTermsHId(Integer oldTermsHId) {
		this.oldTermsHId = oldTermsHId;
	}

	public String getOldAgreementEdition() {
		return oldAgreementEdition;
	}

	public void setOldAgreementEdition(String oldAgreementEdition) {
		this.oldAgreementEdition = oldAgreementEdition;
	}

	public String getOldVendorNbr() {
		return oldVendorNbr;
	}

	public void setOldVendorNbr(String oldVendorNbr) {
		this.oldVendorNbr = oldVendorNbr;
	}

	public String getOldVendorDesc() {
		return oldVendorDesc;
	}

	public void setOldVendorDesc(String oldVendorDesc) {
		this.oldVendorDesc = oldVendorDesc;
	}

	public String getOldDeptCode() {
		return oldDeptCode;
	}

	public void setOldDeptCode(String oldDeptCode) {
		this.oldDeptCode = oldDeptCode;
	}

	public String getOldDeptDesc() {
		return oldDeptDesc;
	}

	public void setOldDeptDesc(String oldDeptDesc) {
		this.oldDeptDesc = oldDeptDesc;
	}

	public String getOldBrandCode() {
		return oldBrandCode;
	}

	public void setOldBrandCode(String oldBrandCode) {
		this.oldBrandCode = oldBrandCode;
	}

	public String getOldBrandCn() {
		return oldBrandCn;
	}

	public void setOldBrandCn(String oldBrandCn) {
		this.oldBrandCn = oldBrandCn;
	}

	public String getOldBrandEn() {
		return oldBrandEn;
	}

	public void setOldBrandEn(String oldBrandEn) {
		this.oldBrandEn = oldBrandEn;
	}

	public String getOldSalesType() {
		return oldSalesType;
	}

	public void setOldSalesType(String oldSalesType) {
		this.oldSalesType = oldSalesType;
	}

	public String getOldSalesArea() {
		return oldSalesArea;
	}

	public void setOldSalesArea(String oldSalesArea) {
		this.oldSalesArea = oldSalesArea;
	}

	public BigDecimal getOldConsignmentDiscount() {
		return oldConsignmentDiscount;
	}

	public void setOldConsignmentDiscount(BigDecimal oldConsignmentDiscount) {
		this.oldConsignmentDiscount = oldConsignmentDiscount;
	}

	public BigDecimal getOldFcsPurchse() {
		return oldFcsPurchse;
	}

	public void setOldFcsPurchse(BigDecimal oldFcsPurchse) {
		this.oldFcsPurchse = oldFcsPurchse;
	}

	public BigDecimal getOldGp() {
		return oldGp;
	}

	public void setOldGp(BigDecimal oldGp) {
		this.oldGp = oldGp;
	}

	public String getOldWarehouseCode() {
		return oldWarehouseCode;
	}

	public void setOldWarehouseCode(String oldWarehouseCode) {
		this.oldWarehouseCode = oldWarehouseCode;
	}

	public String getOldWarehouseDesc() {
		return oldWarehouseDesc;
	}

	public void setOldWarehouseDesc(String oldWarehouseDesc) {
		this.oldWarehouseDesc = oldWarehouseDesc;
	}

	public BigDecimal getOldPayDays() {
		return oldPayDays;
	}

	public void setOldPayDays(BigDecimal oldPayDays) {
		this.oldPayDays = oldPayDays;
	}

	public BigDecimal getOldSalesUpScale() {
		return oldSalesUpScale;
	}

	public void setOldSalesUpScale(BigDecimal oldSalesUpScale) {
		this.oldSalesUpScale = oldSalesUpScale;
	}

	public String getOldBuyerCode() {
		return oldBuyerCode;
	}

	public void setOldBuyerCode(String oldBuyerCode) {
		this.oldBuyerCode = oldBuyerCode;
	}

	public String getOldInvoiceType() {
		return oldInvoiceType;
	}

	public void setOldInvoiceType(String oldInvoiceType) {
		this.oldInvoiceType = oldInvoiceType;
	}

	public BigDecimal getOldRedTicketScale() {
		return oldRedTicketScale;
	}

	public void setOldRedTicketScale(BigDecimal oldRedTicketScale) {
		this.oldRedTicketScale = oldRedTicketScale;
	}

	public BigDecimal getOldFcsSales() {
		return oldFcsSales;
	}

	public void setOldFcsSales(BigDecimal oldFcsSales) {
		this.oldFcsSales = oldFcsSales;
	}

	public String getOldIsCrossYear() {
		return oldIsCrossYear;
	}

	public void setOldIsCrossYear(String oldIsCrossYear) {
		this.oldIsCrossYear = oldIsCrossYear;
	}

	public String getOldIsReturn() {
		return oldIsReturn;
	}

	public void setOldIsReturn(String oldIsReturn) {
		this.oldIsReturn = oldIsReturn;
	}

	public String getOldBeoiTax() {
		return oldBeoiTax;
	}

	public void setOldBeoiTax(String oldBeoiTax) {
		this.oldBeoiTax = oldBeoiTax;
	}

	public String getOldSourceProposal() {
		return oldSourceProposal;
	}

	public void setOldSourceProposal(String oldSourceProposal) {
		this.oldSourceProposal = oldSourceProposal;
	}

	public String getOldTermsVersion() {
		return oldTermsVersion;
	}

	public void setOldTermsVersion(String oldTermsVersion) {
		this.oldTermsVersion = oldTermsVersion;
	}

	public String getOldRemark() {
		return oldRemark;
	}

	public void setOldRemark(String oldRemark) {
		this.oldRemark = oldRemark;
	}

	public Date getOldCreationDate() {
		return oldCreationDate;
	}

	public void setOldCreationDate(Date oldCreationDate) {
		this.oldCreationDate = oldCreationDate;
	}

	public Integer getOldCreatedBy() {
		return oldCreatedBy;
	}

	public void setOldCreatedBy(Integer oldCreatedBy) {
		this.oldCreatedBy = oldCreatedBy;
	}

	public String getOldEmployeeNumber() {
		return oldEmployeeNumber;
	}

	public void setOldEmployeeNumber(String oldEmployeeNumber) {
		this.oldEmployeeNumber = oldEmployeeNumber;
	}

	public String getOldCreatedByName() {
		return oldCreatedByName;
	}

	public void setOldCreatedByName(String oldCreatedByName) {
		this.oldCreatedByName = oldCreatedByName;
	}

	public Integer getOldProposalId() {
		return oldProposalId;
	}

	public void setOldProposalId(Integer oldProposalId) {
		this.oldProposalId = oldProposalId;
	}

	public String getOldTermsClass() {
		return oldTermsClass;
	}

	public void setOldTermsClass(String oldTermsClass) {
		this.oldTermsClass = oldTermsClass;
	}

	public String getOldProposalYear() {
		return oldProposalYear;
	}

	public void setOldProposalYear(String oldProposalYear) {
		this.oldProposalYear = oldProposalYear;
	}

	public String getIsTermsConfirm() {
		return isTermsConfirm;
	}

	public void setIsTermsConfirm(String isTermsConfirm) {
		this.isTermsConfirm = isTermsConfirm;
	}

	public String getReturnConditions() {
		return returnConditions;
	}

	public void setReturnConditions(String returnConditions) {
		this.returnConditions = returnConditions;
	}

	public String getOldReturnConditions() {
		return oldReturnConditions;
	}

	public void setOldReturnConditions(String oldReturnConditions) {
		this.oldReturnConditions = oldReturnConditions;
	}

	public String getReturnedPurchaseFlag() {
		return returnedPurchaseFlag;
	}

	public void setReturnedPurchaseFlag(String returnedPurchaseFlag) {
		this.returnedPurchaseFlag = returnedPurchaseFlag;
	}

	public String getReturnFreightFlag() {
		return returnFreightFlag;
	}

	public void setReturnFreightFlag(String returnFreightFlag) {
		this.returnFreightFlag = returnFreightFlag;
	}

	public String getBeoiTaxFlag() {
		return beoiTaxFlag;
	}

	public void setBeoiTaxFlag(String beoiTaxFlag) {
		this.beoiTaxFlag = beoiTaxFlag;
	}

	public BigDecimal getOldFcsSplitPurchse() {
		return oldFcsSplitPurchse;
	}

	public void setOldFcsSplitPurchse(BigDecimal oldFcsSplitPurchse) {
		this.oldFcsSplitPurchse = oldFcsSplitPurchse;
	}

	public BigDecimal getOldFcsSplitSales() {
		return oldFcsSplitSales;
	}

	public void setOldFcsSplitSales(BigDecimal oldFcsSplitSales) {
		this.oldFcsSplitSales = oldFcsSplitSales;
	}

	public String getOldIsSplit() {
		return oldIsSplit;
	}

	public void setOldIsSplit(String oldIsSplit) {
		this.oldIsSplit = oldIsSplit;
	}

	public BigDecimal getOldSplitGp() {
		return oldSplitGp;
	}

	public void setOldSplitGp(BigDecimal oldSplitGp) {
		this.oldSplitGp = oldSplitGp;
	}

	public void setDicMap(Map<String, Object> dicMap) {
		this.dicMap = dicMap;
	}
	public Map<String, Object> getDicMap() {
		return dicMap;
	}
}
