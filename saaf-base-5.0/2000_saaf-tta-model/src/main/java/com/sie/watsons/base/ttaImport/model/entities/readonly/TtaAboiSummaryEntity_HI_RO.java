package com.sie.watsons.base.ttaImport.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaAboiSummaryEntity_HI_RO Entity Object
 * Tue Oct 22 19:16:23 CST 2019  Auto Generate
 */

public class TtaAboiSummaryEntity_HI_RO {
	public static final String ABOI_SUMMARY_QUERY = "select * from TTA_ABOI_SUMMARY tas where 1=1 ";

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

	
	public Integer getTtaAboiSummaryId() {
		return ttaAboiSummaryId;
	}

	public void setCompanyDepartment(String companyDepartment) {
		this.companyDepartment = companyDepartment;
	}

	
	public String getCompanyDepartment() {
		return companyDepartment;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setBrandAdj(String brandAdj) {
		this.brandAdj = brandAdj;
	}

	
	public String getBrandAdj() {
		return brandAdj;
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

	public void setMappingFlag(String mappingFlag) {
		this.mappingFlag = mappingFlag;
	}

	
	public String getMappingFlag() {
		return mappingFlag;
	}

	public void setDepartmentOld(String departmentOld) {
		this.departmentOld = departmentOld;
	}

	
	public String getDepartmentOld() {
		return departmentOld;
	}

	public void setAccountMonth(Integer accountMonth) {
		this.accountMonth = accountMonth;
	}

	
	public Integer getAccountMonth() {
		return accountMonth;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	
	public String getVenderName() {
		return venderName;
	}

	public void setBrandOld(String brandOld) {
		this.brandOld = brandOld;
	}

	
	public String getBrandOld() {
		return brandOld;
	}

	public void setCategoryOld(String categoryOld) {
		this.categoryOld = categoryOld;
	}

	
	public String getCategoryOld() {
		return categoryOld;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	
	public String getBuyer() {
		return buyer;
	}

	public void setDebitnote(String debitnote) {
		this.debitnote = debitnote;
	}

	
	public String getDebitnote() {
		return debitnote;
	}

	public void setDeductionArea(String deductionArea) {
		this.deductionArea = deductionArea;
	}

	
	public String getDeductionArea() {
		return deductionArea;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
	public Integer getTotal() {
		return total;
	}

	public void setDisplayrentalOthers(Integer displayrentalOthers) {
		this.displayrentalOthers = displayrentalOthers;
	}

	
	public Integer getDisplayrentalOthers() {
		return displayrentalOthers;
	}

	public void setCounterrental(Integer counterrental) {
		this.counterrental = counterrental;
	}

	
	public Integer getCounterrental() {
		return counterrental;
	}

	public void setDisplayrentalNtp(Integer displayrentalNtp) {
		this.displayrentalNtp = displayrentalNtp;
	}

	
	public Integer getDisplayrentalNtp() {
		return displayrentalNtp;
	}

	public void setPromoLeaDm(Integer promoLeaDm) {
		this.promoLeaDm = promoLeaDm;
	}

	
	public Integer getPromoLeaDm() {
		return promoLeaDm;
	}

	public void setHbaward(Integer hbaward) {
		this.hbaward = hbaward;
	}

	
	public Integer getHbaward() {
		return hbaward;
	}

	public void setNewProdDep(Integer newProdDep) {
		this.newProdDep = newProdDep;
	}

	
	public Integer getNewProdDep() {
		return newProdDep;
	}

	public void setBdf(Integer bdf) {
		this.bdf = bdf;
	}

	
	public Integer getBdf() {
		return bdf;
	}

	public void setDataShareFee(Integer dataShareFee) {
		this.dataShareFee = dataShareFee;
	}

	
	public Integer getDataShareFee() {
		return dataShareFee;
	}

	public void setCostReduIncome(Integer costReduIncome) {
		this.costReduIncome = costReduIncome;
	}

	
	public Integer getCostReduIncome() {
		return costReduIncome;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	
	public Integer getOther() {
		return other;
	}

	public void setPromoIncomeMktg(Integer promoIncomeMktg) {
		this.promoIncomeMktg = promoIncomeMktg;
	}

	
	public Integer getPromoIncomeMktg() {
		return promoIncomeMktg;
	}

	public void setOterBaChargeFee(Integer oterBaChargeFee) {
		this.oterBaChargeFee = oterBaChargeFee;
	}

	
	public Integer getOterBaChargeFee() {
		return oterBaChargeFee;
	}

	public void setBaChargeFee(Integer baChargeFee) {
		this.baChargeFee = baChargeFee;
	}

	
	public Integer getBaChargeFee() {
		return baChargeFee;
	}

	public void setOtherIncomeNonTrade(Integer otherIncomeNonTrade) {
		this.otherIncomeNonTrade = otherIncomeNonTrade;
	}

	
	public Integer getOtherIncomeNonTrade() {
		return otherIncomeNonTrade;
	}

	public void setOtherIncomeOemTester(Integer otherIncomeOemTester) {
		this.otherIncomeOemTester = otherIncomeOemTester;
	}

	
	public Integer getOtherIncomeOemTester() {
		return otherIncomeOemTester;
	}

	public void setDisplayrentalOthersRate(Integer displayrentalOthersRate) {
		this.displayrentalOthersRate = displayrentalOthersRate;
	}

	
	public Integer getDisplayrentalOthersRate() {
		return displayrentalOthersRate;
	}

	public void setCounterrentalRate(Integer counterrentalRate) {
		this.counterrentalRate = counterrentalRate;
	}

	
	public Integer getCounterrentalRate() {
		return counterrentalRate;
	}

	public void setDisplayrentalNtpRate(Integer displayrentalNtpRate) {
		this.displayrentalNtpRate = displayrentalNtpRate;
	}

	
	public Integer getDisplayrentalNtpRate() {
		return displayrentalNtpRate;
	}

	public void setPromoLeaDmRate(Integer promoLeaDmRate) {
		this.promoLeaDmRate = promoLeaDmRate;
	}

	
	public Integer getPromoLeaDmRate() {
		return promoLeaDmRate;
	}

	public void setHbawardRate(Integer hbawardRate) {
		this.hbawardRate = hbawardRate;
	}

	
	public Integer getHbawardRate() {
		return hbawardRate;
	}

	public void setNewProdDepRate(Integer newProdDepRate) {
		this.newProdDepRate = newProdDepRate;
	}

	
	public Integer getNewProdDepRate() {
		return newProdDepRate;
	}

	public void setBdfRate(Integer bdfRate) {
		this.bdfRate = bdfRate;
	}

	
	public Integer getBdfRate() {
		return bdfRate;
	}

	public void setDataShareFeeRate(Integer dataShareFeeRate) {
		this.dataShareFeeRate = dataShareFeeRate;
	}

	
	public Integer getDataShareFeeRate() {
		return dataShareFeeRate;
	}

	public void setCostReduIncomeRate(Integer costReduIncomeRate) {
		this.costReduIncomeRate = costReduIncomeRate;
	}

	
	public Integer getCostReduIncomeRate() {
		return costReduIncomeRate;
	}

	public void setOtherRate(Integer otherRate) {
		this.otherRate = otherRate;
	}

	
	public Integer getOtherRate() {
		return otherRate;
	}

	public void setPromoIncomeMktgRate(Integer promoIncomeMktgRate) {
		this.promoIncomeMktgRate = promoIncomeMktgRate;
	}

	
	public Integer getPromoIncomeMktgRate() {
		return promoIncomeMktgRate;
	}

	public void setOterBaChargeFeeRate(Integer oterBaChargeFeeRate) {
		this.oterBaChargeFeeRate = oterBaChargeFeeRate;
	}

	
	public Integer getOterBaChargeFeeRate() {
		return oterBaChargeFeeRate;
	}

	public void setBaChargeFeeRate(Integer baChargeFeeRate) {
		this.baChargeFeeRate = baChargeFeeRate;
	}

	
	public Integer getBaChargeFeeRate() {
		return baChargeFeeRate;
	}

	public void setOtherIncomeNonRate(Integer otherIncomeNonRate) {
		this.otherIncomeNonRate = otherIncomeNonRate;
	}

	
	public Integer getOtherIncomeNonRate() {
		return otherIncomeNonRate;
	}

	public void setOtherIncomeOemRate(Integer otherIncomeOemRate) {
		this.otherIncomeOemRate = otherIncomeOemRate;
	}

	
	public Integer getOtherIncomeOemRate() {
		return otherIncomeOemRate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
