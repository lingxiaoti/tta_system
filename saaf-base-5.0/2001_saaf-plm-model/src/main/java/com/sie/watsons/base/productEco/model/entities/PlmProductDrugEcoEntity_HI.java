package com.sie.watsons.base.productEco.model.entities;

import javax.persistence.*;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductDrugEcoEntity_HI Entity Object
 * Tue May 26 11:05:57 CST 2020  Auto Generate
 */
@Entity
@Table(name="plm_product_drug_eco")
public class PlmProductDrugEcoEntity_HI {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
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

	@Column(name="eco_id", nullable=true, length=22)	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_LINE", sequenceName = "SEQ_PLM_PRODUCT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="line_id", nullable=true, length=22)	
	public Integer getLineId() {
		return lineId;
	}

	public void setAcdType(String acdType) {
		this.acdType = acdType;
	}

	@Column(name="acd_type", nullable=true, length=20)	
	public String getAcdType() {
		return acdType;
	}

	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	@Column(name="drug_id", nullable=false, length=22)	
	public Integer getDrugId() {
		return drugId;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=255)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=255)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="product_name", nullable=true, length=255)	
	public String getProductName() {
		return productName;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	@Column(name="product_ename", nullable=true, length=255)	
	public String getProductEname() {
		return productEname;
	}

	public void setDrugIns(String drugIns) {
		this.drugIns = drugIns;
	}

	@Column(name="drug_ins", nullable=true, length=255)	
	public String getDrugIns() {
		return drugIns;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	@Column(name="common_name", nullable=true, length=255)	
	public String getCommonName() {
		return commonName;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	@Column(name="drug_code", nullable=true, length=255)	
	public String getDrugCode() {
		return drugCode;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Column(name="standard", nullable=true, length=255)	
	public String getStandard() {
		return standard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=255)	
	public String getUnit() {
		return unit;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Column(name="producer", nullable=true, length=255)	
	public String getProducer() {
		return producer;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Column(name="symbol", nullable=true, length=255)	
	public String getSymbol() {
		return symbol;
	}

	public void setStoreCondition(String storeCondition) {
		this.storeCondition = storeCondition;
	}

	@Column(name="store_condition", nullable=true, length=255)	
	public String getStoreCondition() {
		return storeCondition;
	}

	public void setSymbolDate(Date symbolDate) {
		this.symbolDate = symbolDate;
	}

	@Column(name="symbol_date", nullable=true, length=7)	
	public Date getSymbolDate() {
		return symbolDate;
	}

	public void setDisplayCycle(String displayCycle) {
		this.displayCycle = displayCycle;
	}

	@Column(name="display_cycle", nullable=true, length=255)	
	public String getDisplayCycle() {
		return displayCycle;
	}

	public void setSymbolDays(Integer symbolDays) {
		this.symbolDays = symbolDays;
	}

	@Column(name="symbol_days", nullable=true, length=22)	
	public Integer getSymbolDays() {
		return symbolDays;
	}

	public void setDisplayDays(Integer displayDays) {
		this.displayDays = displayDays;
	}

	@Column(name="display_days", nullable=true, length=22)	
	public Integer getDisplayDays() {
		return displayDays;
	}

	public void setDrugForm(String drugForm) {
		this.drugForm = drugForm;
	}

	@Column(name="drug_form", nullable=true, length=255)	
	public String getDrugForm() {
		return drugForm;
	}

	public void setIsBatchnumber(String isBatchnumber) {
		this.isBatchnumber = isBatchnumber;
	}

	@Column(name="is_batchnumber", nullable=true, length=255)	
	public String getIsBatchnumber() {
		return isBatchnumber;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

	@Column(name="sx_days", nullable=true, length=22)	
	public Integer getSxDays() {
		return sxDays;
	}

	public void setGspManage(String gspManage) {
		this.gspManage = gspManage;
	}

	@Column(name="gsp_manage", nullable=true, length=255)	
	public String getGspManage() {
		return gspManage;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name="type_code", nullable=true, length=255)	
	public String getTypeCode() {
		return typeCode;
	}

	public void setIsRx(String isRx) {
		this.isRx = isRx;
	}

	@Column(name="is_rx", nullable=true, length=255)	
	public String getIsRx() {
		return isRx;
	}

	public void setDrugStandcode(String drugStandcode) {
		this.drugStandcode = drugStandcode;
	}

	@Column(name="drug_standcode", nullable=true, length=255)	
	public String getDrugStandcode() {
		return drugStandcode;
	}

	public void setDrugStandard(String drugStandard) {
		this.drugStandard = drugStandard;
	}

	@Column(name="drug_standard", nullable=true, length=255)	
	public String getDrugStandard() {
		return drugStandard;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	@Column(name="rang", nullable=true, length=255)	
	public String getRang() {
		return rang;
	}

	public void setQaStandard(String qaStandard) {
		this.qaStandard = qaStandard;
	}

	@Column(name="qa_standard", nullable=true, length=255)	
	public String getQaStandard() {
		return qaStandard;
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

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name="product_head_id", nullable=true, length=22)	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	@Column(name="business_category", nullable=true, length=255)	
	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name="category_name", nullable=true, length=255)	
	public String getCategoryName() {
		return categoryName;
	}

	public void setPresType(String presType) {
		this.presType = presType;
	}

	@Column(name="pres_type", nullable=true, length=255)	
	public String getPresType() {
		return presType;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Column(name="file_info", nullable=true, length=255)	
	public String getFileInfo() {
		return fileInfo;
	}

	public void setBackageUnit(String backageUnit) {
		this.backageUnit = backageUnit;
	}

	@Column(name="backage_unit", nullable=true, length=255)	
	public String getBackageUnit() {
		return backageUnit;
	}

	public void setDrugUnit(String drugUnit) {
		this.drugUnit = drugUnit;
	}

	@Column(name="drug_unit", nullable=true, length=255)	
	public String getDrugUnit() {
		return drugUnit;
	}

	public void setIsProtect(String isProtect) {
		this.isProtect = isProtect;
	}

	@Column(name="is_protect", nullable=true, length=255)	
	public String getIsProtect() {
		return isProtect;
	}

	public void setIsPull(String isPull) {
		this.isPull = isPull;
	}

	@Column(name="is_pull", nullable=true, length=255)	
	public String getIsPull() {
		return isPull;
	}

	public void setIsCold(String isCold) {
		this.isCold = isCold;
	}

	@Column(name="is_cold", nullable=true, length=255)	
	public String getIsCold() {
		return isCold;
	}

	public void setIsEphedrine(String isEphedrine) {
		this.isEphedrine = isEphedrine;
	}

	@Column(name="is_ephedrine", nullable=true, length=255)	
	public String getIsEphedrine() {
		return isEphedrine;
	}

	public void setDrugPlace(String drugPlace) {
		this.drugPlace = drugPlace;
	}

	@Column(name="drug_place", nullable=true, length=255)	
	public String getDrugPlace() {
		return drugPlace;
	}

	public void setProtectPeriod(String protectPeriod) {
		this.protectPeriod = protectPeriod;
	}

	@Column(name="protect_period", nullable=true, length=255)	
	public String getProtectPeriod() {
		return protectPeriod;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name="flag", nullable=true, length=255)	
	public String getFlag() {
		return flag;
	}

	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}

	@Column(name="second_class", nullable=true, length=255)	
	public String getSecondClass() {
		return secondClass;
	}

	public void setProjectClass(String projectClass) {
		this.projectClass = projectClass;
	}

	@Column(name="project_class", nullable=true, length=255)	
	public String getProjectClass() {
		return projectClass;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Column(name="project", nullable=true, length=255)	
	public String getProject() {
		return project;
	}

	public void setMarketPerson(String marketPerson) {
		this.marketPerson = marketPerson;
	}

	@Column(name="market_person", nullable=true, length=255)	
	public String getMarketPerson() {
		return marketPerson;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name="rms_code", nullable=true, length=30)	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setOpk(String opk) {
		this.opk = opk;
	}

	@Column(name="opk", nullable=true, length=255)	
	public String getOpk() {
		return opk;
	}

	public void setRangName(String rangName) {
		this.rangName = rangName;
	}

	@Column(name="rang_name", nullable=true, length=1000)	
	public String getRangName() {
		return rangName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
