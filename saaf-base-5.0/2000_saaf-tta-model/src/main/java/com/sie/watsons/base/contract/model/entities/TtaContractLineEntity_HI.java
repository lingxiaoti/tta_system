package com.sie.watsons.base.contract.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldDesc;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaContractLineEntity_HI Entity Object
 * Mon Jun 24 17:28:23 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_contract_line")
	public class TtaContractLineEntity_HI {
	@FieldDesc(isUpdateWhereKey = true)
	private Integer contractLId;
	private Integer contractHId;
	private String vendorCode;
	private String vendorName;
	private String orgCode;
	private String tradeMark;

	@FieldDesc(isUpdateWhereKey = false)
	private java.math.BigDecimal purchase;

	@FieldDesc(isUpdateWhereKey = false)
	private java.math.BigDecimal sales;

	@FieldDesc(isUpdateWhereKey = false)
	private String salesArea;

	@FieldDesc(isUpdateWhereKey = false)
	private String deliveryGranary;

	private String oiType;
	private String termsCn;
	private String termsEn;

	@FieldDesc(isUpdateWhereKey = false)
	private String ttaValue;

	private java.math.BigDecimal feeIntas;
	private java.math.BigDecimal feeNotax;
	private String status;
	private Integer proposalId;
	private Integer clauseId;
	private String incomeType;
	private String yTerms;
	private String collectType;
	private Integer referenceStandard;
	private String unit;
	@FieldDesc(isUpdateWhereKey = false)
	private String termsSystem;
	private String proposalYear;
	private String orderNo;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String  remark ;
	private String  termCategory ;
	private String  termsSystemOld ;
	private java.math.BigDecimal feeIntasOld;
	private java.math.BigDecimal feeNotaxOld;
	private BigDecimal feeSplitIntas;
	private BigDecimal feeSplitNotax;
	private BigDecimal splitPurchase;
	private BigDecimal splitSales;
	private String isSplitMoney;
	private String isSplitFee;
	//@FieldDesc(isUpdateWhereKey = false)
	private String  termsSystemUp ;

	private String  invoiceTypeName ;
    private String  sumMoney ;

    @FieldDesc(isUpdateWhereKey = false)
	private String isSpecial;

	public void setContractLId(Integer contractLId) {
		this.contractLId = contractLId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_TTA_CONTRACT_LINE", sequenceName = "SEQ_TTA_CONTRACT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_CONTRACT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="contract_l_id", nullable=false, length=22)
	public Integer getContractLId() {
		return contractLId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	@Column(name="contract_h_id", nullable=true, length=22)
	public Integer getContractHId() {
		return contractHId;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=true, length=50)
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=200)
	public String getVendorName() {
		return vendorName;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_code", nullable=true, length=50)
	public String getOrgCode() {
		return orgCode;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	@Column(name="trade_mark", nullable=true, length=2000)
	public String getTradeMark() {
		return tradeMark;
	}

	public void setPurchase(java.math.BigDecimal purchase) {
		this.purchase = purchase;
	}

	@Column(name="purchase", nullable=true, length=22)
	public java.math.BigDecimal getPurchase() {
		return purchase;
	}

	public void setSales(java.math.BigDecimal sales) {
		this.sales = sales;
	}

	@Column(name="sales", nullable=true, length=22)
	public java.math.BigDecimal getSales() {
		return sales;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	@Column(name="sales_area", nullable=true, length=200)
	public String getSalesArea() {
		return salesArea;
	}

	public void setDeliveryGranary(String deliveryGranary) {
		this.deliveryGranary = deliveryGranary;
	}

	@Column(name="delivery_granary", nullable=true, length=200)
	public String getDeliveryGranary() {
		return deliveryGranary;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	@Column(name="oi_type", nullable=true, length=50)
	public String getOiType() {
		return oiType;
	}

	public void setTermsCn(String termsCn) {
		this.termsCn = termsCn;
	}

	@Column(name="terms_cn", nullable=true, length=200)
	public String getTermsCn() {
		return termsCn;
	}

	public void setTermsEn(String termsEn) {
		this.termsEn = termsEn;
	}

	@Column(name="terms_en", nullable=true, length=200)
	public String getTermsEn() {
		return termsEn;
	}

	public void setTtaValue(String ttaValue) {
		this.ttaValue = ttaValue;
	}

	@Column(name="tta_value", nullable=true, length=50)
	public String getTtaValue() {
		return ttaValue;
	}

	public void setFeeIntas(java.math.BigDecimal feeIntas) {
		this.feeIntas = feeIntas;
	}

	@Column(name="fee_intas", nullable=true, length=22)
	public java.math.BigDecimal getFeeIntas() {
		return feeIntas;
	}

	public void setFeeNotax(java.math.BigDecimal feeNotax) {
		this.feeNotax = feeNotax;
	}

	@Column(name="fee_notax", nullable=true, length=22)
	public java.math.BigDecimal getFeeNotax() {
		return feeNotax;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)
	public String getStatus() {
		return status;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)
	public Integer getProposalId() {
		return proposalId;
	}

	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}

	@Column(name="clause_id", nullable=true, length=22)
	public Integer getClauseId() {
		return clauseId;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	@Column(name="income_type", nullable=true, length=200)
	public String getIncomeType() {
		return incomeType;
	}

	public void setYTerms(String yTerms) {
		this.yTerms = yTerms;
	}

	@Column(name="y_terms", nullable=true, length=200)
	public String getYTerms() {
		return yTerms;
	}

	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}

	@Column(name="collect_type", nullable=true, length=20)
	public String getCollectType() {
		return collectType;
	}

	public void setReferenceStandard(Integer referenceStandard) {
		this.referenceStandard = referenceStandard;
	}

	@Column(name="reference_standard", nullable=true, length=22)
	public Integer getReferenceStandard() {
		return referenceStandard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=50)
	public String getUnit() {
		return unit;
	}

	public void setTermsSystem(String termsSystem) {
		this.termsSystem = termsSystem;
	}

	@Column(name="terms_system", nullable=true, length=500)
	public String getTermsSystem() {
		return termsSystem;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="proposal_year", nullable=true, length=50)
	public String getProposalYear() {
		return proposalYear;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=200)
	public String getOrderNo() {
		return orderNo;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="remark", nullable=true, length=1500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="term_category", nullable=true, length=2)
	public String getTermCategory() {
		return termCategory;
	}

	public void setTermCategory(String termCategory) {
		this.termCategory = termCategory;
	}

    @Transient
    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

	@Column(name="fee_intas_old")
	public BigDecimal getFeeIntasOld() {
		return feeIntasOld;
	}

	public void setFeeIntasOld(BigDecimal feeIntasOld) {
		this.feeIntasOld = feeIntasOld;
	}

	@Column(name="fee_notax_old")
	public BigDecimal getFeeNotaxOld() {
		return feeNotaxOld;
	}

	public void setFeeNotaxOld(BigDecimal feeNotaxOld) {
		this.feeNotaxOld = feeNotaxOld;
	}

	@Column(name="terms_system_old")
	public String getTermsSystemOld() {
		return termsSystemOld;
	}

	public void setTermsSystemOld(String termsSystemOld) {
		this.termsSystemOld = termsSystemOld;
	}


	@Column(name="terms_system_up")
	public String getTermsSystemUp() {
		return termsSystemUp;
	}

	public void setTermsSystemUp(String termsSystemUp) {
		this.termsSystemUp = termsSystemUp;
	}

	@Column(name="invoice_type_name")
	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	@Column(name="fee_split_intas")
	public BigDecimal getFeeSplitIntas() {
		return feeSplitIntas;
	}

	public void setFeeSplitIntas(BigDecimal feeSplitIntas) {
		this.feeSplitIntas = feeSplitIntas;
	}

	@Column(name="fee_split_notax")
	public BigDecimal getFeeSplitNotax() {
		return feeSplitNotax;
	}

	public void setFeeSplitNotax(BigDecimal feeSplitNotax) {
		this.feeSplitNotax = feeSplitNotax;
	}

	@Column(name="split_purchase")
	public BigDecimal getSplitPurchase() {
		return splitPurchase;
	}

	public void setSplitPurchase(BigDecimal splitPurchase) {
		this.splitPurchase = splitPurchase;
	}

	@Column(name="split_sales")
	public BigDecimal getSplitSales() {
		return splitSales;
	}

	public void setSplitSales(BigDecimal splitSales) {
		this.splitSales = splitSales;
	}

	@Column(name="is_split_money")
	public String getIsSplitMoney() {
		return isSplitMoney;
	}

	public void setIsSplitMoney(String isSplitMoney) {
		this.isSplitMoney = isSplitMoney;
	}

	@Column(name="is_split_fee")
	public String getIsSplitFee() {
		return isSplitFee;
	}

	public void setIsSplitFee(String isSplitFee) {
		this.isSplitFee = isSplitFee;
	}

	@Column(name="is_special")
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}
}
