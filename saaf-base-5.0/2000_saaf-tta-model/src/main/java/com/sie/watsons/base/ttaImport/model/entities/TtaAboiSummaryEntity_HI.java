package com.sie.watsons.base.ttaImport.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaAboiSummaryEntity_HI Entity Object
 * Tue Oct 22 19:16:23 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_ABOI_SUMMARY")
public class TtaAboiSummaryEntity_HI {
    private Integer ttaAboiSummaryId;
    private String companyDepartment;
    private String groupDesc;
    private String department;
    private String brandAdj;
    private String brandCn;
    private String brandEn;
    private String mappingFlag;
    private String departmentOld;
    private Integer accountMonth;
    private String rmsCode;
    private String venderName;
    private String brandOld;
    private String categoryOld;
    private String buyer;
    private String debitnote;
    private String deductionArea;
    private Integer total;
    private Integer displayrentalOthers;
    private Integer counterrental;
    private Integer displayrentalNtp;
    private Integer promoLeaDm;
    private Integer hbaward;
    private Integer newProdDep;
    private Integer bdf;
    private Integer dataShareFee;
    private Integer costReduIncome;
    private Integer other;
    private Integer promoIncomeMktg;
    private Integer oterBaChargeFee;
    private Integer baChargeFee;
    private Integer otherIncomeNonTrade;
    private Integer otherIncomeOemTester;
    private Integer displayrentalOthersRate;
    private Integer counterrentalRate;
    private Integer displayrentalNtpRate;
    private Integer promoLeaDmRate;
    private Integer hbawardRate;
    private Integer newProdDepRate;
    private Integer bdfRate;
    private Integer dataShareFeeRate;
    private Integer costReduIncomeRate;
    private Integer otherRate;
    private Integer promoIncomeMktgRate;
    private Integer oterBaChargeFeeRate;
    private Integer baChargeFeeRate;
    private Integer otherIncomeNonRate;
    private Integer otherIncomeOemRate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setTtaAboiSummaryId(Integer ttaAboiSummaryId) {
		this.ttaAboiSummaryId = ttaAboiSummaryId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_ABOI_SUMMARY", sequenceName="SEQ_TTA_ABOI_SUMMARY", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_ABOI_SUMMARY",strategy=GenerationType.SEQUENCE)
	@Column(name="tta_aboi_summary_id", nullable=true, length=22)	
	public Integer getTtaAboiSummaryId() {
		return ttaAboiSummaryId;
	}

	public void setCompanyDepartment(String companyDepartment) {
		this.companyDepartment = companyDepartment;
	}

	@Column(name="company_department", nullable=true, length=50)	
	public String getCompanyDepartment() {
		return companyDepartment;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=50)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="department", nullable=true, length=50)	
	public String getDepartment() {
		return department;
	}

	public void setBrandAdj(String brandAdj) {
		this.brandAdj = brandAdj;
	}

	@Column(name="brand_adj", nullable=true, length=500)
	public String getBrandAdj() {
		return brandAdj;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=500)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=500)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setMappingFlag(String mappingFlag) {
		this.mappingFlag = mappingFlag;
	}

	@Column(name="mapping_flag", nullable=true, length=500)	
	public String getMappingFlag() {
		return mappingFlag;
	}

	public void setDepartmentOld(String departmentOld) {
		this.departmentOld = departmentOld;
	}

	@Column(name="department_old", nullable=true, length=50)	
	public String getDepartmentOld() {
		return departmentOld;
	}

	public void setAccountMonth(Integer accountMonth) {
		this.accountMonth = accountMonth;
	}

	@Column(name="account_month", nullable=true, length=22)	
	public Integer getAccountMonth() {
		return accountMonth;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name="rms_code", nullable=true, length=50)	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	@Column(name="vender_name", nullable=true, length=500)	
	public String getVenderName() {
		return venderName;
	}

	public void setBrandOld(String brandOld) {
		this.brandOld = brandOld;
	}

	@Column(name="brand_old", nullable=true, length=500)	
	public String getBrandOld() {
		return brandOld;
	}

	public void setCategoryOld(String categoryOld) {
		this.categoryOld = categoryOld;
	}

	@Column(name="category_old", nullable=true, length=50)	
	public String getCategoryOld() {
		return categoryOld;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	@Column(name="buyer", nullable=true, length=50)	
	public String getBuyer() {
		return buyer;
	}

	public void setDebitnote(String debitnote) {
		this.debitnote = debitnote;
	}

	@Column(name="debitnote", nullable=true, length=50)	
	public String getDebitnote() {
		return debitnote;
	}

	public void setDeductionArea(String deductionArea) {
		this.deductionArea = deductionArea;
	}

	@Column(name="deduction_area", nullable=true, length=50)	
	public String getDeductionArea() {
		return deductionArea;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name="total", nullable=true, length=22)	
	public Integer getTotal() {
		return total;
	}

	public void setDisplayrentalOthers(Integer displayrentalOthers) {
		this.displayrentalOthers = displayrentalOthers;
	}

	@Column(name="displayrental_others", nullable=true, length=22)	
	public Integer getDisplayrentalOthers() {
		return displayrentalOthers;
	}

	public void setCounterrental(Integer counterrental) {
		this.counterrental = counterrental;
	}

	@Column(name="counterrental", nullable=true, length=22)	
	public Integer getCounterrental() {
		return counterrental;
	}

	public void setDisplayrentalNtp(Integer displayrentalNtp) {
		this.displayrentalNtp = displayrentalNtp;
	}

	@Column(name="displayrental_ntp", nullable=true, length=22)	
	public Integer getDisplayrentalNtp() {
		return displayrentalNtp;
	}

	public void setPromoLeaDm(Integer promoLeaDm) {
		this.promoLeaDm = promoLeaDm;
	}

	@Column(name="promo_lea_dm", nullable=true, length=22)	
	public Integer getPromoLeaDm() {
		return promoLeaDm;
	}

	public void setHbaward(Integer hbaward) {
		this.hbaward = hbaward;
	}

	@Column(name="hbaward", nullable=true, length=22)	
	public Integer getHbaward() {
		return hbaward;
	}

	public void setNewProdDep(Integer newProdDep) {
		this.newProdDep = newProdDep;
	}

	@Column(name="new_prod_dep", nullable=true, length=22)	
	public Integer getNewProdDep() {
		return newProdDep;
	}

	public void setBdf(Integer bdf) {
		this.bdf = bdf;
	}

	@Column(name="bdf", nullable=true, length=22)	
	public Integer getBdf() {
		return bdf;
	}

	public void setDataShareFee(Integer dataShareFee) {
		this.dataShareFee = dataShareFee;
	}

	@Column(name="data_share_fee", nullable=true, length=22)	
	public Integer getDataShareFee() {
		return dataShareFee;
	}

	public void setCostReduIncome(Integer costReduIncome) {
		this.costReduIncome = costReduIncome;
	}

	@Column(name="cost_redu_income", nullable=true, length=22)	
	public Integer getCostReduIncome() {
		return costReduIncome;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	@Column(name="other", nullable=true, length=22)	
	public Integer getOther() {
		return other;
	}

	public void setPromoIncomeMktg(Integer promoIncomeMktg) {
		this.promoIncomeMktg = promoIncomeMktg;
	}

	@Column(name="promo_income_mktg", nullable=true, length=22)	
	public Integer getPromoIncomeMktg() {
		return promoIncomeMktg;
	}

	public void setOterBaChargeFee(Integer oterBaChargeFee) {
		this.oterBaChargeFee = oterBaChargeFee;
	}

	@Column(name="oter_ba_charge_fee", nullable=true, length=22)	
	public Integer getOterBaChargeFee() {
		return oterBaChargeFee;
	}

	public void setBaChargeFee(Integer baChargeFee) {
		this.baChargeFee = baChargeFee;
	}

	@Column(name="ba_charge_fee", nullable=true, length=22)	
	public Integer getBaChargeFee() {
		return baChargeFee;
	}

	public void setOtherIncomeNonTrade(Integer otherIncomeNonTrade) {
		this.otherIncomeNonTrade = otherIncomeNonTrade;
	}

	@Column(name="other_income_non_trade", nullable=true, length=22)	
	public Integer getOtherIncomeNonTrade() {
		return otherIncomeNonTrade;
	}

	public void setOtherIncomeOemTester(Integer otherIncomeOemTester) {
		this.otherIncomeOemTester = otherIncomeOemTester;
	}

	@Column(name="other_income_oem_tester", nullable=true, length=22)	
	public Integer getOtherIncomeOemTester() {
		return otherIncomeOemTester;
	}

	public void setDisplayrentalOthersRate(Integer displayrentalOthersRate) {
		this.displayrentalOthersRate = displayrentalOthersRate;
	}

	@Column(name="displayrental_others_rate", nullable=true, length=22)	
	public Integer getDisplayrentalOthersRate() {
		return displayrentalOthersRate;
	}

	public void setCounterrentalRate(Integer counterrentalRate) {
		this.counterrentalRate = counterrentalRate;
	}

	@Column(name="counterrental_rate", nullable=true, length=22)	
	public Integer getCounterrentalRate() {
		return counterrentalRate;
	}

	public void setDisplayrentalNtpRate(Integer displayrentalNtpRate) {
		this.displayrentalNtpRate = displayrentalNtpRate;
	}

	@Column(name="displayrental_ntp_rate", nullable=true, length=22)	
	public Integer getDisplayrentalNtpRate() {
		return displayrentalNtpRate;
	}

	public void setPromoLeaDmRate(Integer promoLeaDmRate) {
		this.promoLeaDmRate = promoLeaDmRate;
	}

	@Column(name="promo_lea_dm_rate", nullable=true, length=22)	
	public Integer getPromoLeaDmRate() {
		return promoLeaDmRate;
	}

	public void setHbawardRate(Integer hbawardRate) {
		this.hbawardRate = hbawardRate;
	}

	@Column(name="hbaward_rate", nullable=true, length=22)	
	public Integer getHbawardRate() {
		return hbawardRate;
	}

	public void setNewProdDepRate(Integer newProdDepRate) {
		this.newProdDepRate = newProdDepRate;
	}

	@Column(name="new_prod_dep_rate", nullable=true, length=22)	
	public Integer getNewProdDepRate() {
		return newProdDepRate;
	}

	public void setBdfRate(Integer bdfRate) {
		this.bdfRate = bdfRate;
	}

	@Column(name="bdf_rate", nullable=true, length=22)	
	public Integer getBdfRate() {
		return bdfRate;
	}

	public void setDataShareFeeRate(Integer dataShareFeeRate) {
		this.dataShareFeeRate = dataShareFeeRate;
	}

	@Column(name="data_share_fee_rate", nullable=true, length=22)	
	public Integer getDataShareFeeRate() {
		return dataShareFeeRate;
	}

	public void setCostReduIncomeRate(Integer costReduIncomeRate) {
		this.costReduIncomeRate = costReduIncomeRate;
	}

	@Column(name="cost_redu_income_rate", nullable=true, length=22)	
	public Integer getCostReduIncomeRate() {
		return costReduIncomeRate;
	}

	public void setOtherRate(Integer otherRate) {
		this.otherRate = otherRate;
	}

	@Column(name="other_rate", nullable=true, length=22)	
	public Integer getOtherRate() {
		return otherRate;
	}

	public void setPromoIncomeMktgRate(Integer promoIncomeMktgRate) {
		this.promoIncomeMktgRate = promoIncomeMktgRate;
	}

	@Column(name="promo_income_mktg_rate", nullable=true, length=22)	
	public Integer getPromoIncomeMktgRate() {
		return promoIncomeMktgRate;
	}

	public void setOterBaChargeFeeRate(Integer oterBaChargeFeeRate) {
		this.oterBaChargeFeeRate = oterBaChargeFeeRate;
	}

	@Column(name="oter_ba_charge_fee_rate", nullable=true, length=22)	
	public Integer getOterBaChargeFeeRate() {
		return oterBaChargeFeeRate;
	}

	public void setBaChargeFeeRate(Integer baChargeFeeRate) {
		this.baChargeFeeRate = baChargeFeeRate;
	}

	@Column(name="ba_charge_fee_rate", nullable=true, length=22)	
	public Integer getBaChargeFeeRate() {
		return baChargeFeeRate;
	}

	public void setOtherIncomeNonRate(Integer otherIncomeNonRate) {
		this.otherIncomeNonRate = otherIncomeNonRate;
	}

	@Column(name="other_income_non_rate", nullable=true, length=22)	
	public Integer getOtherIncomeNonRate() {
		return otherIncomeNonRate;
	}

	public void setOtherIncomeOemRate(Integer otherIncomeOemRate) {
		this.otherIncomeOemRate = otherIncomeOemRate;
	}

	@Column(name="other_income_oem_rate", nullable=true, length=22)	
	public Integer getOtherIncomeOemRate() {
		return otherIncomeOemRate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
