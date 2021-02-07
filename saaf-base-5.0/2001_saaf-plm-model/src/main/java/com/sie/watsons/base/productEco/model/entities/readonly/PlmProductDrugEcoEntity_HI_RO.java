package com.sie.watsons.base.productEco.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import jodd.vtor.constraint.NotBlank;
import jodd.vtor.constraint.NotNull;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProductDrugEcoEntity_HI_RO Entity Object
 * Tue May 26 11:05:57 CST 2020  Auto Generate
 */

public class PlmProductDrugEcoEntity_HI_RO {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
	@NotBlank( message = "drugId不能为空",profiles = {"insert"})
	@NotNull( message = "drugId不能为空",profiles = {"insert"})
    private Integer drugId;
    private String supplierName;
    private String supplierCode;
    private String productName;
    private String productEname;
    private String drugIns;
    private String commonName;
    private Integer supplierId;
    private String drugCode;
    private String standard;
    private String unit;
    private String producer;
    private String symbol;
    private String storeCondition;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date symbolDate;
    private String displayCycle;
    private Integer symbolDays;
    private Integer displayDays;
    private String drugForm;
    private String isBatchnumber;
    private Integer sxDays;
    private String gspManage;
    private String typeCode;
    private String isRx;
    private String drugStandcode;
    private String drugStandard;
    private String rang;
    private String qaStandard;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer productHeadId;
    private String businessCategory;
    private String categoryName;
    private String presType;
    private String fileInfo;
    private String backageUnit;
    private String drugUnit;
    private String isProtect;
    private String isPull;
    private String isCold;
    private String isEphedrine;
    private String drugPlace;
    private String protectPeriod;
    private String flag;
    private String secondClass;
    private String projectClass;
    private String project;
    private String marketPerson;
    private String rmsCode;
    private String opk;
    private String rangName;
    private Integer operatorUserId;

	public void setEcoId(Integer ecoId) {
		this.ecoId = ecoId;
	}

	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	
	public Integer getLineId() {
		return lineId;
	}

	public void setAcdType(String acdType) {
		this.acdType = acdType;
	}

	
	public String getAcdType() {
		return acdType;
	}

	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	
	public Integer getDrugId() {
		return drugId;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	
	public String getProductEname() {
		return productEname;
	}

	public void setDrugIns(String drugIns) {
		this.drugIns = drugIns;
	}

	
	public String getDrugIns() {
		return drugIns;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	
	public String getCommonName() {
		return commonName;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	
	public String getDrugCode() {
		return drugCode;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	
	public String getStandard() {
		return standard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	
	public String getProducer() {
		return producer;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
	public String getSymbol() {
		return symbol;
	}

	public void setStoreCondition(String storeCondition) {
		this.storeCondition = storeCondition;
	}

	
	public String getStoreCondition() {
		return storeCondition;
	}

	public void setSymbolDate(Date symbolDate) {
		this.symbolDate = symbolDate;
	}

	
	public Date getSymbolDate() {
		return symbolDate;
	}

	public void setDisplayCycle(String displayCycle) {
		this.displayCycle = displayCycle;
	}

	
	public String getDisplayCycle() {
		return displayCycle;
	}

	public void setSymbolDays(Integer symbolDays) {
		this.symbolDays = symbolDays;
	}

	
	public Integer getSymbolDays() {
		return symbolDays;
	}

	public void setDisplayDays(Integer displayDays) {
		this.displayDays = displayDays;
	}

	
	public Integer getDisplayDays() {
		return displayDays;
	}

	public void setDrugForm(String drugForm) {
		this.drugForm = drugForm;
	}

	
	public String getDrugForm() {
		return drugForm;
	}

	public void setIsBatchnumber(String isBatchnumber) {
		this.isBatchnumber = isBatchnumber;
	}

	
	public String getIsBatchnumber() {
		return isBatchnumber;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

	
	public Integer getSxDays() {
		return sxDays;
	}

	public void setGspManage(String gspManage) {
		this.gspManage = gspManage;
	}

	
	public String getGspManage() {
		return gspManage;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	
	public String getTypeCode() {
		return typeCode;
	}

	public void setIsRx(String isRx) {
		this.isRx = isRx;
	}

	
	public String getIsRx() {
		return isRx;
	}

	public void setDrugStandcode(String drugStandcode) {
		this.drugStandcode = drugStandcode;
	}

	
	public String getDrugStandcode() {
		return drugStandcode;
	}

	public void setDrugStandard(String drugStandard) {
		this.drugStandard = drugStandard;
	}

	
	public String getDrugStandard() {
		return drugStandard;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	
	public String getRang() {
		return rang;
	}

	public void setQaStandard(String qaStandard) {
		this.qaStandard = qaStandard;
	}

	
	public String getQaStandard() {
		return qaStandard;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
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

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	
	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	public String getCategoryName() {
		return categoryName;
	}

	public void setPresType(String presType) {
		this.presType = presType;
	}

	
	public String getPresType() {
		return presType;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	
	public String getFileInfo() {
		return fileInfo;
	}

	public void setBackageUnit(String backageUnit) {
		this.backageUnit = backageUnit;
	}

	
	public String getBackageUnit() {
		return backageUnit;
	}

	public void setDrugUnit(String drugUnit) {
		this.drugUnit = drugUnit;
	}

	
	public String getDrugUnit() {
		return drugUnit;
	}

	public void setIsProtect(String isProtect) {
		this.isProtect = isProtect;
	}

	
	public String getIsProtect() {
		return isProtect;
	}

	public void setIsPull(String isPull) {
		this.isPull = isPull;
	}

	
	public String getIsPull() {
		return isPull;
	}

	public void setIsCold(String isCold) {
		this.isCold = isCold;
	}

	
	public String getIsCold() {
		return isCold;
	}

	public void setIsEphedrine(String isEphedrine) {
		this.isEphedrine = isEphedrine;
	}

	
	public String getIsEphedrine() {
		return isEphedrine;
	}

	public void setDrugPlace(String drugPlace) {
		this.drugPlace = drugPlace;
	}

	
	public String getDrugPlace() {
		return drugPlace;
	}

	public void setProtectPeriod(String protectPeriod) {
		this.protectPeriod = protectPeriod;
	}

	
	public String getProtectPeriod() {
		return protectPeriod;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
	public String getFlag() {
		return flag;
	}

	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}

	
	public String getSecondClass() {
		return secondClass;
	}

	public void setProjectClass(String projectClass) {
		this.projectClass = projectClass;
	}

	
	public String getProjectClass() {
		return projectClass;
	}

	public void setProject(String project) {
		this.project = project;
	}

	
	public String getProject() {
		return project;
	}

	public void setMarketPerson(String marketPerson) {
		this.marketPerson = marketPerson;
	}

	
	public String getMarketPerson() {
		return marketPerson;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setOpk(String opk) {
		this.opk = opk;
	}

	
	public String getOpk() {
		return opk;
	}

	public void setRangName(String rangName) {
		this.rangName = rangName;
	}

	
	public String getRangName() {
		return rangName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
