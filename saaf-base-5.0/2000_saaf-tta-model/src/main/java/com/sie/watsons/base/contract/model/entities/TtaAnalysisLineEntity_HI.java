package com.sie.watsons.base.contract.model.entities;

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
 * TtaAnalysisLineEntity_HI Entity Object
 * Mon Mar 02 21:16:25 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_analysis_line")
public class TtaAnalysisLineEntity_HI {
    private Integer id;
    private String anaysisId;//行唯一标识
    private String supplierCode;//供应商编号
    private String supplierName;//供应商名称
    private String region;//区域
    private String classs;//类型
    private String buyer;//采购人
    private String ownerDept;//部门
    private String tradingMode;//交易类型(销售方式)
    private String contractVersion;//合同版本
    private String brand;//品牌
    private String newOrExisting;//是否新续签供应商
    private BigDecimal forecastPurchase;
    private BigDecimal purchase;
    private BigDecimal gp;
    private BigDecimal beoi;
    private BigDecimal sroi;
    private BigDecimal aboi;
    private BigDecimal totaloi;
    private BigDecimal nm;
    private String batchNumber;//批次号
    private String proposalYear;//proposal制作年度
    private Integer proposalId;
    private Integer orderNo;//排序号
    private String dataType;//数据类型
    private Integer versionCode;//版本号
    private Integer contractLId;//合同行id
    private String termsComparison;
    private String oiType;//oi费用类型
    private String termsCn;//中文条款
    private String termsEn;//英文条款
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
    private BigDecimal splitBeforeTtaAmt;//拆分前tta费用
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_ANALYSIS_LINE", sequenceName = "SEQ_TTA_ANALYSIS_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_ANALYSIS_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=true, length=22)	
	public Integer getId() {
		return id;
	}

	public void setAnaysisId(String anaysisId) {
		this.anaysisId = anaysisId;
	}

	@Column(name="anaysis_id", nullable=true, length=50)	
	public String getAnaysisId() {
		return anaysisId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=50)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=150)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name="region", nullable=true, length=200)	
	public String getRegion() {
		return region;
	}

	public void setClasss(String classs) {
		this.classs = classs;
	}

	@Column(name="classs", nullable=true, length=100)	
	public String getClasss() {
		return classs;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	@Column(name="buyer", nullable=true, length=80)	
	public String getBuyer() {
		return buyer;
	}

	public void setOwnerDept(String ownerDept) {
		this.ownerDept = ownerDept;
	}

	@Column(name="owner_dept", nullable=true, length=100)	
	public String getOwnerDept() {
		return ownerDept;
	}

	public void setTradingMode(String tradingMode) {
		this.tradingMode = tradingMode;
	}

	@Column(name="trading_mode", nullable=true, length=150)	
	public String getTradingMode() {
		return tradingMode;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	@Column(name="contract_version", nullable=true, length=30)	
	public String getContractVersion() {
		return contractVersion;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=300)	
	public String getBrand() {
		return brand;
	}

	public void setNewOrExisting(String newOrExisting) {
		this.newOrExisting = newOrExisting;
	}

	@Column(name="new_or_existing", nullable=true, length=50)	
	public String getNewOrExisting() {
		return newOrExisting;
	}

	public void setForecastPurchase(BigDecimal forecastPurchase) {
		this.forecastPurchase = forecastPurchase;
	}

	@Column(name="forecast_purchase", nullable=true, length=22)	
	public BigDecimal getForecastPurchase() {
		return forecastPurchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	@Column(name="purchase", nullable=true, length=22)	
	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	@Column(name="gp", nullable=true, length=22)	
	public BigDecimal getGp() {
		return gp;
	}

	public void setBeoi(BigDecimal beoi) {
		this.beoi = beoi;
	}

	@Column(name="beoi", nullable=true, length=22)	
	public BigDecimal getBeoi() {
		return beoi;
	}

	public void setSroi(BigDecimal sroi) {
		this.sroi = sroi;
	}

	@Column(name="sroi", nullable=true, length=22)	
	public BigDecimal getSroi() {
		return sroi;
	}

	public void setAboi(BigDecimal aboi) {
		this.aboi = aboi;
	}

	@Column(name="aboi", nullable=true, length=22)	
	public BigDecimal getAboi() {
		return aboi;
	}

	public void setTotaloi(BigDecimal totaloi) {
		this.totaloi = totaloi;
	}

	@Column(name="totaloi", nullable=true, length=22)	
	public BigDecimal getTotaloi() {
		return totaloi;
	}

	public void setNm(BigDecimal nm) {
		this.nm = nm;
	}

	@Column(name="nm", nullable=true, length=22)	
	public BigDecimal getNm() {
		return nm;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	@Column(name="batch_number", nullable=true, length=100)	
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="proposal_year", nullable=true, length=50)	
	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=22)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name="data_type", nullable=true, length=50)	
	public String getDataType() {
		return dataType;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	@Column(name="version_code", nullable=true, length=22)	
	public Integer getVersionCode() {
		return versionCode;
	}

	public void setContractLId(Integer contractLId) {
		this.contractLId = contractLId;
	}

	@Column(name="contract_l_id", nullable=true, length=22)	
	public Integer getContractLId() {
		return contractLId;
	}

	public void setTermsComparison(String termsComparison) {
		this.termsComparison = termsComparison;
	}

	@Column(name="terms_comparison", nullable=true, length=200)	
	public String getTermsComparison() {
		return termsComparison;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	@Column(name="oi_type", nullable=true, length=80)	
	public String getOiType() {
		return oiType;
	}

	public void setTermsCn(String termsCn) {
		this.termsCn = termsCn;
	}

	@Column(name="terms_cn", nullable=true, length=300)	
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

	public void setFormerYearTta(BigDecimal formerYearTta) {
		this.formerYearTta = formerYearTta;
	}

	@Column(name="former_year_tta", nullable=true, length=22)	
	public BigDecimal getFormerYearTta() {
		return formerYearTta;
	}

	public void setFormerYearOntop(BigDecimal formerYearOntop) {
		this.formerYearOntop = formerYearOntop;
	}

	@Column(name="former_year_ontop", nullable=true, length=22)	
	public BigDecimal getFormerYearOntop() {
		return formerYearOntop;
	}

	public void setFormerYearTtaOntop(BigDecimal formerYearTtaOntop) {
		this.formerYearTtaOntop = formerYearTtaOntop;
	}

	@Column(name="former_year_tta_ontop", nullable=true, length=22)	
	public BigDecimal getFormerYearTtaOntop() {
		return formerYearTtaOntop;
	}

	public void setFormerYearActual(BigDecimal formerYearActual) {
		this.formerYearActual = formerYearActual;
	}

	@Column(name="former_year_actual", nullable=true, length=22)	
	public BigDecimal getFormerYearActual() {
		return formerYearActual;
	}

	public void setCurrentYearTta(BigDecimal currentYearTta) {
		this.currentYearTta = currentYearTta;
	}

	@Column(name="current_year_tta", nullable=true, length=22)	
	public BigDecimal getCurrentYearTta() {
		return currentYearTta;
	}

	public void setCurrentYearOntop(BigDecimal currentYearOntop) {
		this.currentYearOntop = currentYearOntop;
	}

	@Column(name="current_year_ontop", nullable=true, length=22)	
	public BigDecimal getCurrentYearOntop() {
		return currentYearOntop;
	}

	public void setCurrentYearTtaOntop(BigDecimal currentYearTtaOntop) {
		this.currentYearTtaOntop = currentYearTtaOntop;
	}

	@Column(name="current_year_tta_ontop", nullable=true, length=22)	
	public BigDecimal getCurrentYearTtaOntop() {
		return currentYearTtaOntop;
	}

	public void setTtaOntopIncreAmt(BigDecimal ttaOntopIncreAmt) {
		this.ttaOntopIncreAmt = ttaOntopIncreAmt;
	}

	@Column(name="tta_ontop_incre_amt", nullable=true, length=22)	
	public BigDecimal getTtaOntopIncreAmt() {
		return ttaOntopIncreAmt;
	}

	public void setTtaOntopPercentGain(BigDecimal ttaOntopPercentGain) {
		this.ttaOntopPercentGain = ttaOntopPercentGain;
	}

	@Column(name="tta_ontop_percent_gain", nullable=true, length=22)	
	public BigDecimal getTtaOntopPercentGain() {
		return ttaOntopPercentGain;
	}

	public void setTtaOntopActIncreAmt(BigDecimal ttaOntopActIncreAmt) {
		this.ttaOntopActIncreAmt = ttaOntopActIncreAmt;
	}

	@Column(name="tta_ontop_act_incre_amt", nullable=true, length=22)	
	public BigDecimal getTtaOntopActIncreAmt() {
		return ttaOntopActIncreAmt;
	}

	public void setTtaOntopActPercentGain(BigDecimal ttaOntopActPercentGain) {
		this.ttaOntopActPercentGain = ttaOntopActPercentGain;
	}

	@Column(name="tta_ontop_act_percent_gain", nullable=true, length=22)	
	public BigDecimal getTtaOntopActPercentGain() {
		return ttaOntopActPercentGain;
	}

	public void setPurchaseRemark(String purchaseRemark) {
		this.purchaseRemark = purchaseRemark;
	}

	@Column(name="purchase_remark", nullable=true, length=3000)	
	public String getPurchaseRemark() {
		return purchaseRemark;
	}

	public void setBicRemark(String bicRemark) {
		this.bicRemark = bicRemark;
	}

	@Column(name="bic_remark", nullable=true, length=4000)	
	public String getBicRemark() {
		return bicRemark;
	}

	public void setBenchMark(String benchMark) {
		this.benchMark = benchMark;
	}

	@Column(name="bench_mark", nullable=true, length=2000)	
	public String getBenchMark() {
		return benchMark;
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

	public void setOldContractLId(Integer oldContractLId) {
		this.oldContractLId = oldContractLId;
	}

	@Column(name="old_contract_l_id", nullable=true, length=22)	
	public Integer getOldContractLId() {
		return oldContractLId;
	}

	public void setSplitBeforeTtaAmt(BigDecimal splitBeforeTtaAmt) {
		this.splitBeforeTtaAmt = splitBeforeTtaAmt;
	}

	@Column(name="split_before_tta_amt", nullable=true, length=22)	
	public BigDecimal getSplitBeforeTtaAmt() {
		return splitBeforeTtaAmt;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
