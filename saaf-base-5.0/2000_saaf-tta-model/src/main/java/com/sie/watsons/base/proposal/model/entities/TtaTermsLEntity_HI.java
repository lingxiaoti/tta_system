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
 * TtaTermsLEntity_HI Entity Object
 * Tue Jun 04 08:38:54 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_PROPOSAL_TERMS_LINE")
public class TtaTermsLEntity_HI {
    private Integer termsLId;
    private Integer termsHId;
    private String incomeType;
    private String yTerms;
    private String collectType;
    private BigDecimal referenceStandard;
    private String yYear;
    private BigDecimal qty;
    private String unit;
    private String termsSystem;
    private BigDecimal feeNotax;
    private BigDecimal feeIntas;
    private String termsCode;
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
	private Integer  clauseId ;
	private Integer  clauseTreeId ;
	private String  orderNo ;
	private String  yTermsEn ;
	private Integer  oldClauseId ;
	private String  businessVersion ;
	private String  oiType ;
	private Integer  oldClauseTreeId2 ;
	private String isMajor;
	private Integer parentUnitId;
	private Integer unitId;
	private BigDecimal feeAcNotax;
	private BigDecimal feeAcIntas;
	private String code;
	private String pCode;
	private String termCategory;
	private String yYearOld;
	private String rule;
	private String ttaValueRefer;
	private BigDecimal feeSplitNotax;
	private BigDecimal feeSplitIntas;
	public void setTermsLId(Integer termsLId) {
		this.termsLId = termsLId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_PROPOSAL_TERMS_LINE", sequenceName = "SEQ_TTA_PROPOSAL_TERMS_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_PROPOSAL_TERMS_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="terms_l_id", nullable=false, length=22)	
	public Integer getTermsLId() {
		return termsLId;
	}

	public void setTermsHId(Integer termsHId) {
		this.termsHId = termsHId;
	}

	@Column(name="terms_h_id", nullable=false, length=22)	
	public Integer getTermsHId() {
		return termsHId;
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

	public void setReferenceStandard(BigDecimal referenceStandard) {
		this.referenceStandard = referenceStandard;
	}

	@Column(name="reference_standard", nullable=true, length=22)	
	public BigDecimal getReferenceStandard() {
		return referenceStandard;
	}

	public void setYYear(String yYear) {
		this.yYear = yYear;
	}

	@Column(name="y_year", nullable=true, length=50)	
	public String getYYear() {
		return yYear;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Column(name="qty", nullable=true, length=22)	
	public BigDecimal getQty() {
		return qty;
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

	@Column(name="terms_system", nullable=true, length=50)	
	public String getTermsSystem() {
		return termsSystem;
	}

	public void setFeeNotax(BigDecimal feeNotax) {
		this.feeNotax = feeNotax;
	}

	@Column(name="fee_notax", nullable=true, length=22)	
	public BigDecimal getFeeNotax() {
		return feeNotax;
	}

	public void setFeeIntas(BigDecimal feeIntas) {
		this.feeIntas = feeIntas;
	}

	@Column(name="fee_intas", nullable=true, length=22)	
	public BigDecimal getFeeIntas() {
		return feeIntas;
	}

	public void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	}

	@Column(name="terms_code", nullable=true, length=50)	
	public String getTermsCode() {
		return termsCode;
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
	@Column(name="clause_id", nullable=true, length=22)
	public Integer getClauseId() {
		return clauseId;
	}

	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}
	@Column(name="clause_tree_id", nullable=true, length=22)
	public Integer getClauseTreeId() {
		return clauseTreeId;
	}

	public void setClauseTreeId(Integer clauseTreeId) {
		this.clauseTreeId = clauseTreeId;
	}
	@Column(name="order_no", nullable=true, length=200)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name="y_terms_en", nullable=true, length=200)
	public String getYTermsEn() {
		return yTermsEn;
	}

	public void setYTermsEn(String yTermsEn) {
		this.yTermsEn = yTermsEn;
	}

	@Column(name="old_clause_id", nullable=true, length=22)
	public Integer getOldClauseId() {
		return oldClauseId;
	}

	public void setOldClauseId(Integer oldClauseId) {
		this.oldClauseId = oldClauseId;
	}
	@Column(name="business_version", nullable=true, length=200)
	public String getBusinessVersion() {
		return businessVersion;
	}

	public void setBusinessVersion(String businessVersion) {
		this.businessVersion = businessVersion;
	}

	@Column(name="oi_type", nullable=true, length=200)
	public String getOiType() {
		return oiType;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}
	@Column(name="old_clause_tree_id2", nullable=true, length=22)
	public Integer getOldClauseTreeId2() {
		return oldClauseTreeId2;
	}

	public void setOldClauseTreeId2(Integer oldClauseTreeId2) {
		this.oldClauseTreeId2 = oldClauseTreeId2;
	}

	@Column(name="is_major")
	public String getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}

	@Column(name="parent_unit_id")
	public Integer getParentUnitId() {
		return parentUnitId;
	}

	public void setParentUnitId(Integer parentUnitId) {
		this.parentUnitId = parentUnitId;
	}

	@Column(name="unit_id")
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Column(name="fee_ac_notax")
	public BigDecimal getFeeAcNotax() {
		return feeAcNotax;
	}


	public void setFeeAcNotax(BigDecimal feeAcNotax) {
		this.feeAcNotax = feeAcNotax;
	}

	@Column(name="fee_ac_intas")
	public BigDecimal getFeeAcIntas() {
		return feeAcIntas;
	}

	public void setFeeAcIntas(BigDecimal feeAcIntas) {
		this.feeAcIntas = feeAcIntas;
	}

	@Column(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="p_code")
	public String getPCode() {
		return pCode;
	}

	public void setPCode(String pCode) {
		this.pCode = pCode;
	}

	@Column(name="term_category")
	public String getTermCategory() {
		return termCategory;
	}

	public void setTermCategory(String termCategory) {
		this.termCategory = termCategory;
	}

	@Column(name="y_year_old")
	public String getYYearOld() {
		return yYearOld;
	}

	public void setYYearOld(String yYearOld) {
		this.yYearOld = yYearOld;
	}

	@Column(name="rule")
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	@Column(name="tta_value_refer")
	public String getTtaValueRefer() {
		return ttaValueRefer;
	}

	public void setTtaValueRefer(String ttaValueRefer) {
		this.ttaValueRefer = ttaValueRefer;
	}

	@Column(name="fee_split_notax")
	public BigDecimal getFeeSplitNotax() {
		return feeSplitNotax;
	}

	public void setFeeSplitNotax(BigDecimal feeSplitNotax) {
		this.feeSplitNotax = feeSplitNotax;
	}

	@Column(name="fee_split_intas")
	public BigDecimal getFeeSplitIntas() {
		return feeSplitIntas;
	}

	public void setFeeSplitIntas(BigDecimal feeSplitIntas) {
		this.feeSplitIntas = feeSplitIntas;
	}
}
