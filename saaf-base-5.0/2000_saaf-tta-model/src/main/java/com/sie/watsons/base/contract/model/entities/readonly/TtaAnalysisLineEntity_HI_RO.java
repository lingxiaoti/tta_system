package com.sie.watsons.base.contract.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaAnalysisLineEntity_HI_RO Entity Object
 * Mon Mar 02 21:16:25 CST 2020  Auto Generate
 */

public class TtaAnalysisLineEntity_HI_RO {
    private Integer id;
    private String anaysisId;
    private String supplierCode;
    private String supplierName;
    private String region;
    private String classs;
    private String buyer;
    private String ownerDept;
    private String tradingMode;
    private String contractVersion;
    private String brand;
    private String newOrExisting;
    private BigDecimal forecastPurchase;
    private BigDecimal purchase;
    private BigDecimal gp;
    private BigDecimal beoi;
    private BigDecimal sroi;
    private BigDecimal aboi;
    private BigDecimal totaloi;
    private BigDecimal nm;
    private String batchNumber;
    private String proposalYear;
    private Integer proposalId;
    private Integer orderNo;
    private String dataType;
    private Integer versionCode;
    private Integer contractLId;
    private String termsComparison;
    private String oiType;
    private String termsCn;
    private String termsEn;
    private BigDecimal formerYearTta;
    private BigDecimal formerYearOntop;
    private BigDecimal formerYearTtaOntop;
    private BigDecimal formerYearActual;
    private BigDecimal currentYearTta;
    private BigDecimal currentYearOntop;
    private BigDecimal currentYearTtaOntop;
    private BigDecimal ttaOntopIncreAmt;
    private BigDecimal ttaOntopPercentGain;
    private BigDecimal ttaOntopActIncreAmt;
    private BigDecimal ttaOntopActPercentGain;
    private String purchaseRemark;
    private String bicRemark;
    private String benchMark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer oldContractLId;
    private BigDecimal splitBeforeTtaAmt;
    private Integer operatorUserId;

    public static final String QUERY_ANALYSIS = "select * from tta_analysis_line tal where tal.proposal_id =:proposalId order by tal.order_no ";

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setAnaysisId(String anaysisId) {
		this.anaysisId = anaysisId;
	}

	
	public String getAnaysisId() {
		return anaysisId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
	public String getRegion() {
		return region;
	}

	public void setClasss(String classs) {
		this.classs = classs;
	}

	
	public String getClasss() {
		return classs;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	
	public String getBuyer() {
		return buyer;
	}

	public void setOwnerDept(String ownerDept) {
		this.ownerDept = ownerDept;
	}

	
	public String getOwnerDept() {
		return ownerDept;
	}

	public void setTradingMode(String tradingMode) {
		this.tradingMode = tradingMode;
	}

	
	public String getTradingMode() {
		return tradingMode;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	
	public String getContractVersion() {
		return contractVersion;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	public String getBrand() {
		return brand;
	}

	public void setNewOrExisting(String newOrExisting) {
		this.newOrExisting = newOrExisting;
	}

	
	public String getNewOrExisting() {
		return newOrExisting;
	}

	public void setForecastPurchase(BigDecimal forecastPurchase) {
		this.forecastPurchase = forecastPurchase;
	}

	
	public BigDecimal getForecastPurchase() {
		return forecastPurchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	
	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	
	public BigDecimal getGp() {
		return gp;
	}

	public void setBeoi(BigDecimal beoi) {
		this.beoi = beoi;
	}

	
	public BigDecimal getBeoi() {
		return beoi;
	}

	public void setSroi(BigDecimal sroi) {
		this.sroi = sroi;
	}

	
	public BigDecimal getSroi() {
		return sroi;
	}

	public void setAboi(BigDecimal aboi) {
		this.aboi = aboi;
	}

	
	public BigDecimal getAboi() {
		return aboi;
	}

	public void setTotaloi(BigDecimal totaloi) {
		this.totaloi = totaloi;
	}

	
	public BigDecimal getTotaloi() {
		return totaloi;
	}

	public void setNm(BigDecimal nm) {
		this.nm = nm;
	}

	
	public BigDecimal getNm() {
		return nm;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	
	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	
	public String getDataType() {
		return dataType;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	
	public Integer getVersionCode() {
		return versionCode;
	}

	public void setContractLId(Integer contractLId) {
		this.contractLId = contractLId;
	}

	
	public Integer getContractLId() {
		return contractLId;
	}

	public void setTermsComparison(String termsComparison) {
		this.termsComparison = termsComparison;
	}

	
	public String getTermsComparison() {
		return termsComparison;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	
	public String getOiType() {
		return oiType;
	}

	public void setTermsCn(String termsCn) {
		this.termsCn = termsCn;
	}

	
	public String getTermsCn() {
		return termsCn;
	}

	public void setTermsEn(String termsEn) {
		this.termsEn = termsEn;
	}

	
	public String getTermsEn() {
		return termsEn;
	}

	public void setFormerYearTta(BigDecimal formerYearTta) {
		this.formerYearTta = formerYearTta;
	}

	
	public BigDecimal getFormerYearTta() {
		return formerYearTta;
	}

	public void setFormerYearOntop(BigDecimal formerYearOntop) {
		this.formerYearOntop = formerYearOntop;
	}

	
	public BigDecimal getFormerYearOntop() {
		return formerYearOntop;
	}

	public void setFormerYearTtaOntop(BigDecimal formerYearTtaOntop) {
		this.formerYearTtaOntop = formerYearTtaOntop;
	}

	
	public BigDecimal getFormerYearTtaOntop() {
		return formerYearTtaOntop;
	}

	public void setFormerYearActual(BigDecimal formerYearActual) {
		this.formerYearActual = formerYearActual;
	}

	
	public BigDecimal getFormerYearActual() {
		return formerYearActual;
	}

	public void setCurrentYearTta(BigDecimal currentYearTta) {
		this.currentYearTta = currentYearTta;
	}

	
	public BigDecimal getCurrentYearTta() {
		return currentYearTta;
	}

	public void setCurrentYearOntop(BigDecimal currentYearOntop) {
		this.currentYearOntop = currentYearOntop;
	}

	
	public BigDecimal getCurrentYearOntop() {
		return currentYearOntop;
	}

	public void setCurrentYearTtaOntop(BigDecimal currentYearTtaOntop) {
		this.currentYearTtaOntop = currentYearTtaOntop;
	}

	
	public BigDecimal getCurrentYearTtaOntop() {
		return currentYearTtaOntop;
	}

	public void setTtaOntopIncreAmt(BigDecimal ttaOntopIncreAmt) {
		this.ttaOntopIncreAmt = ttaOntopIncreAmt;
	}

	
	public BigDecimal getTtaOntopIncreAmt() {
		return ttaOntopIncreAmt;
	}

	public void setTtaOntopPercentGain(BigDecimal ttaOntopPercentGain) {
		this.ttaOntopPercentGain = ttaOntopPercentGain;
	}

	
	public BigDecimal getTtaOntopPercentGain() {
		return ttaOntopPercentGain;
	}

	public void setTtaOntopActIncreAmt(BigDecimal ttaOntopActIncreAmt) {
		this.ttaOntopActIncreAmt = ttaOntopActIncreAmt;
	}

	
	public BigDecimal getTtaOntopActIncreAmt() {
		return ttaOntopActIncreAmt;
	}

	public void setTtaOntopActPercentGain(BigDecimal ttaOntopActPercentGain) {
		this.ttaOntopActPercentGain = ttaOntopActPercentGain;
	}

	
	public BigDecimal getTtaOntopActPercentGain() {
		return ttaOntopActPercentGain;
	}

	public void setPurchaseRemark(String purchaseRemark) {
		this.purchaseRemark = purchaseRemark;
	}

	
	public String getPurchaseRemark() {
		return purchaseRemark;
	}

	public void setBicRemark(String bicRemark) {
		this.bicRemark = bicRemark;
	}

	
	public String getBicRemark() {
		return bicRemark;
	}

	public void setBenchMark(String benchMark) {
		this.benchMark = benchMark;
	}

	
	public String getBenchMark() {
		return benchMark;
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

	public void setOldContractLId(Integer oldContractLId) {
		this.oldContractLId = oldContractLId;
	}

	
	public Integer getOldContractLId() {
		return oldContractLId;
	}

	public void setSplitBeforeTtaAmt(BigDecimal splitBeforeTtaAmt) {
		this.splitBeforeTtaAmt = splitBeforeTtaAmt;
	}

	
	public BigDecimal getSplitBeforeTtaAmt() {
		return splitBeforeTtaAmt;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
