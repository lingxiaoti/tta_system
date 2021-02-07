package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaAboiSummaryEntity_HI Entity Object
 * Tue Oct 22 19:16:23 CST 2019  Auto Generate
 */
public class TtaAboiSummaryEntity_HI_MODEL {

	private Integer ttaAboiSummaryId;
	@ExcelProperty(value ="公司部门")
	private String companyDepartment;
	@ExcelProperty(value ="Group")
	private String groupDesc;
	@ExcelProperty(value ="Department")
	private String department;
	@ExcelProperty(value ="Brand Adj")
	private String brandAdj;
	@ExcelProperty(value ="Brand_CN")
	private String brandCn;
	@ExcelProperty(value ="Brand_EN")
	private String brandEn;
	@ExcelProperty(value ="Mapping Flag")
	private String mappingFlag;
	@ExcelProperty(value ="部门")
	private String departmentOld;
	@ExcelProperty(value ="入账月份")
	private Integer accountMonth;
	@ExcelProperty(value ="供应商编号")
	private String rmsCode;
	@ExcelProperty(value ="供应商名称")
	private String venderName;
	@ExcelProperty(value ="品牌")
	private String brandOld;
	@ExcelProperty(value ="品类")
	private String categoryOld;
	@ExcelProperty(value ="下单人员")
	private String buyer;
	@ExcelProperty(value ="借记单编号")
	private String debitnote;
	@ExcelProperty(value ="扣款地区")
	private String deductionArea;
	@ExcelProperty(value ="合计")
	private Integer total;
	@ExcelProperty(value ="促销陈列服务费")
	private Integer displayrentalOthers;
	@ExcelProperty(value ="专柜促销陈列服务费")
	private Integer counterrental;
	@ExcelProperty(value ="促销陈列服务费-NewTrialProjects")
	private Integer displayrentalNtp;
	@ExcelProperty(value ="宣传单张、宣传牌及促销用品推广服务费")
	private Integer promoLeaDm;
	@ExcelProperty(value ="健与美")
	private Integer hbaward;
	@ExcelProperty(value ="新品种宣传推广服务费")
	private Integer newProdDep;
	@ExcelProperty(value ="商业发展服务费")
	private Integer bdf;
	@ExcelProperty(value ="数据分享费")
	private Integer dataShareFee;
	@ExcelProperty(value ="成本补差")
	private Integer costReduIncome;
	@ExcelProperty(value ="其他")
	private Integer other;
	@ExcelProperty(value ="市场推广服务费")
	private Integer promoIncomeMktg;
	@ExcelProperty(value ="其他促销服务费")
	private Integer oterBaChargeFee;
	@ExcelProperty(value ="促销服务费")
	private Integer baChargeFee;
	@ExcelProperty(value ="Non-Trade 其他业务费用")
	private Integer otherIncomeNonTrade;
	@ExcelProperty(value ="OEM试用装")
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

	private Date creationDate;

	private Integer createdBy;

	private Integer lastUpdatedBy;

	private Date lastUpdateDate;

	private Integer lastUpdateLogin;

	private Integer versionNum;

	public Integer getTtaAboiSummaryId() {
		return ttaAboiSummaryId;
	}

	public void setTtaAboiSummaryId(Integer ttaAboiSummaryId) {
		this.ttaAboiSummaryId = ttaAboiSummaryId;
	}

	public String getCompanyDepartment() {
		return companyDepartment;
	}

	public void setCompanyDepartment(String companyDepartment) {
		this.companyDepartment = companyDepartment;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBrandAdj() {
		return brandAdj;
	}

	public void setBrandAdj(String brandAdj) {
		this.brandAdj = brandAdj;
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

	public String getMappingFlag() {
		return mappingFlag;
	}

	public void setMappingFlag(String mappingFlag) {
		this.mappingFlag = mappingFlag;
	}

	public String getDepartmentOld() {
		return departmentOld;
	}

	public void setDepartmentOld(String departmentOld) {
		this.departmentOld = departmentOld;
	}

	public Integer getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(Integer accountMonth) {
		this.accountMonth = accountMonth;
	}

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getBrandOld() {
		return brandOld;
	}

	public void setBrandOld(String brandOld) {
		this.brandOld = brandOld;
	}

	public String getCategoryOld() {
		return categoryOld;
	}

	public void setCategoryOld(String categoryOld) {
		this.categoryOld = categoryOld;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getDebitnote() {
		return debitnote;
	}

	public void setDebitnote(String debitnote) {
		this.debitnote = debitnote;
	}

	public String getDeductionArea() {
		return deductionArea;
	}

	public void setDeductionArea(String deductionArea) {
		this.deductionArea = deductionArea;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getDisplayrentalOthers() {
		return displayrentalOthers;
	}

	public void setDisplayrentalOthers(Integer displayrentalOthers) {
		this.displayrentalOthers = displayrentalOthers;
	}

	public Integer getCounterrental() {
		return counterrental;
	}

	public void setCounterrental(Integer counterrental) {
		this.counterrental = counterrental;
	}

	public Integer getDisplayrentalNtp() {
		return displayrentalNtp;
	}

	public void setDisplayrentalNtp(Integer displayrentalNtp) {
		this.displayrentalNtp = displayrentalNtp;
	}

	public Integer getPromoLeaDm() {
		return promoLeaDm;
	}

	public void setPromoLeaDm(Integer promoLeaDm) {
		this.promoLeaDm = promoLeaDm;
	}

	public Integer getHbaward() {
		return hbaward;
	}

	public void setHbaward(Integer hbaward) {
		this.hbaward = hbaward;
	}

	public Integer getNewProdDep() {
		return newProdDep;
	}

	public void setNewProdDep(Integer newProdDep) {
		this.newProdDep = newProdDep;
	}

	public Integer getBdf() {
		return bdf;
	}

	public void setBdf(Integer bdf) {
		this.bdf = bdf;
	}

	public Integer getDataShareFee() {
		return dataShareFee;
	}

	public void setDataShareFee(Integer dataShareFee) {
		this.dataShareFee = dataShareFee;
	}

	public Integer getCostReduIncome() {
		return costReduIncome;
	}

	public void setCostReduIncome(Integer costReduIncome) {
		this.costReduIncome = costReduIncome;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	public Integer getPromoIncomeMktg() {
		return promoIncomeMktg;
	}

	public void setPromoIncomeMktg(Integer promoIncomeMktg) {
		this.promoIncomeMktg = promoIncomeMktg;
	}

	public Integer getOterBaChargeFee() {
		return oterBaChargeFee;
	}

	public void setOterBaChargeFee(Integer oterBaChargeFee) {
		this.oterBaChargeFee = oterBaChargeFee;
	}

	public Integer getBaChargeFee() {
		return baChargeFee;
	}

	public void setBaChargeFee(Integer baChargeFee) {
		this.baChargeFee = baChargeFee;
	}

	public Integer getOtherIncomeNonTrade() {
		return otherIncomeNonTrade;
	}

	public void setOtherIncomeNonTrade(Integer otherIncomeNonTrade) {
		this.otherIncomeNonTrade = otherIncomeNonTrade;
	}

	public Integer getOtherIncomeOemTester() {
		return otherIncomeOemTester;
	}

	public void setOtherIncomeOemTester(Integer otherIncomeOemTester) {
		this.otherIncomeOemTester = otherIncomeOemTester;
	}

	public Integer getDisplayrentalOthersRate() {
		return displayrentalOthersRate;
	}

	public void setDisplayrentalOthersRate(Integer displayrentalOthersRate) {
		this.displayrentalOthersRate = displayrentalOthersRate;
	}

	public Integer getCounterrentalRate() {
		return counterrentalRate;
	}

	public void setCounterrentalRate(Integer counterrentalRate) {
		this.counterrentalRate = counterrentalRate;
	}

	public Integer getDisplayrentalNtpRate() {
		return displayrentalNtpRate;
	}

	public void setDisplayrentalNtpRate(Integer displayrentalNtpRate) {
		this.displayrentalNtpRate = displayrentalNtpRate;
	}

	public Integer getPromoLeaDmRate() {
		return promoLeaDmRate;
	}

	public void setPromoLeaDmRate(Integer promoLeaDmRate) {
		this.promoLeaDmRate = promoLeaDmRate;
	}

	public Integer getHbawardRate() {
		return hbawardRate;
	}

	public void setHbawardRate(Integer hbawardRate) {
		this.hbawardRate = hbawardRate;
	}

	public Integer getNewProdDepRate() {
		return newProdDepRate;
	}

	public void setNewProdDepRate(Integer newProdDepRate) {
		this.newProdDepRate = newProdDepRate;
	}

	public Integer getBdfRate() {
		return bdfRate;
	}

	public void setBdfRate(Integer bdfRate) {
		this.bdfRate = bdfRate;
	}

	public Integer getDataShareFeeRate() {
		return dataShareFeeRate;
	}

	public void setDataShareFeeRate(Integer dataShareFeeRate) {
		this.dataShareFeeRate = dataShareFeeRate;
	}

	public Integer getCostReduIncomeRate() {
		return costReduIncomeRate;
	}

	public void setCostReduIncomeRate(Integer costReduIncomeRate) {
		this.costReduIncomeRate = costReduIncomeRate;
	}

	public Integer getOtherRate() {
		return otherRate;
	}

	public void setOtherRate(Integer otherRate) {
		this.otherRate = otherRate;
	}

	public Integer getPromoIncomeMktgRate() {
		return promoIncomeMktgRate;
	}

	public void setPromoIncomeMktgRate(Integer promoIncomeMktgRate) {
		this.promoIncomeMktgRate = promoIncomeMktgRate;
	}

	public Integer getOterBaChargeFeeRate() {
		return oterBaChargeFeeRate;
	}

	public void setOterBaChargeFeeRate(Integer oterBaChargeFeeRate) {
		this.oterBaChargeFeeRate = oterBaChargeFeeRate;
	}

	public Integer getBaChargeFeeRate() {
		return baChargeFeeRate;
	}

	public void setBaChargeFeeRate(Integer baChargeFeeRate) {
		this.baChargeFeeRate = baChargeFeeRate;
	}

	public Integer getOtherIncomeNonRate() {
		return otherIncomeNonRate;
	}

	public void setOtherIncomeNonRate(Integer otherIncomeNonRate) {
		this.otherIncomeNonRate = otherIncomeNonRate;
	}

	public Integer getOtherIncomeOemRate() {
		return otherIncomeOemRate;
	}

	public void setOtherIncomeOemRate(Integer otherIncomeOemRate) {
		this.otherIncomeOemRate = otherIncomeOemRate;
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

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
}
