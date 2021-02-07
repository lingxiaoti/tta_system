package com.sie.watsons.base.product.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductDrugEntity_HI Entity Object Mon Sep 09 16:06:42 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_DRUG")
public class PlmProductDrugEntity_HI {
	private Integer drugId;
	@columnNames(name = "供应商名称")
	private String supplierName;
	@columnNames(name = "供应商编码")
	private String supplierCode;
	@columnNames(name = "产品中文名称")
	private String productName;
	@columnNames(name = "产品英文名称")
	private String productEname;
	@columnNames(name = "医保药品")
	private String drugIns;
	@columnNames(name = "通用名称")
	private String commonName;
	@columnNames(name = "供应商id")
	private Integer supplierId;
	@columnNames(name = "药品代码")
	private String drugCode;
	@columnNames(name = "规格")
	private String standard;
	@columnNames(name = "单位")
	private String unit;
	@columnNames(name = "生产商")
	private String producer;
	@columnNames(name = "批准文号")
	private String symbol;
	@columnNames(name = "存储条件")
	private String storeCondition;
	@columnNames(name = "批文有效期")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date symbolDate;
	@columnNames(name = "陈列周期")
	private String displayCycle;
	@columnNames(name = "批文预警天数")
	private Integer symbolDays;
	@columnNames(name = "陈列预警天数")
	private Integer displayDays;
	@columnNames(name = "剂型")
	private String drugForm;
	@columnNames(name = "是否批号")
	private String isBatchnumber;
	@columnNames(name = "效期预警天数")
	private Integer sxDays;
	@columnNames(name = "gsp管理级别")
	private String gspManage;
	@columnNames(name = "类别代码")
	private String typeCode;
	@columnNames(name = "是否处方药")
	private String isRx;
	@columnNames(name = "药监统一编码")
	private String drugStandcode;
	@columnNames(name = "药品本位码")
	private String drugStandard;
	// @columnNames(name = "所属经营范围")
	private String rang;
	@columnNames(name = "所属经营范围")
	private String rangName;

	@columnNames(name = "质量标准")
	private String qaStandard;

	@columnNames(name = "药品二级分类")
	private String secondClass;
	@columnNames(name = "药品项目品种")
	private String projectClass;
	@columnNames(name = "药品项目")
	private String project;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer productHeadId;
	private Integer operatorUserId;

	@columnNames(name = " 经营类别")
	private String businessCategory;// 经营类别
	@columnNames(name = "类别名称")
	private String categoryName;// 类别名称
	@columnNames(name = "处方药类别")
	private String presType;// 处方药类别
	@columnNames(name = "批准证明文件及其附件")
	private String fileInfo;// 批准证明文件及其附件
	@columnNames(name = "包装规格")
	private String backageUnit;// 包装规格
	@columnNames(name = "药监包装规格")
	private String drugUnit;// 药监包装规格
	@columnNames(name = "是否重点养护")
	private String isProtect;// 是否重点养护
	@columnNames(name = "是否拆零")
	private String isPull;// 是否拆零
	@columnNames(name = "是否冷藏药品")
	private String isCold;// 是否冷藏药品
	@columnNames(name = "是否含麻黄碱")
	private String isEphedrine;// 是否含麻黄碱
	@columnNames(name = "产地")
	private String drugPlace;// 产地（中药饮片）
	@columnNames(name = "保质期")
	private String protectPeriod;// 保质期
	@columnNames(name = "上市许可持有人")
	private String marketPerson; // 上市许可持有人

	@columnNames(name = "OPK")
	private String OPK;

	@Column(name = "OPK", nullable = true, length = 255)
	public String getOPK() {
		return OPK;
	}

	public void setOPK(String oPK) {
		OPK = oPK;
	}

	@Column(name = "rang_name", nullable = true, length = 1000)
	public String getRangName() {
		return rangName;
	}

	public void setRangName(String rangName) {
		this.rangName = rangName;
	}

	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_DRUG", sequenceName = "SEQ_PLM_PRODUCT_DRUG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_DRUG", strategy = GenerationType.SEQUENCE)
	@Column(name = "drug_id", nullable = false, length = 22)
	public Integer getDrugId() {
		return drugId;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "supplier_name", nullable = true, length = 255)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name = "supplier_code", nullable = true, length = 255)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_name", nullable = true, length = 255)
	public String getProductName() {
		return productName;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	@Column(name = "product_ename", nullable = true, length = 255)
	public String getProductEname() {
		return productEname;
	}

	public void setDrugIns(String drugIns) {
		this.drugIns = drugIns;
	}

	@Column(name = "drug_ins", nullable = true, length = 255)
	public String getDrugIns() {
		return drugIns;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	@Column(name = "common_name", nullable = true, length = 255)
	public String getCommonName() {
		return commonName;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "supplier_id", nullable = true, length = 22)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	@Column(name = "drug_code", nullable = true, length = 255)
	public String getDrugCode() {
		return drugCode;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Column(name = "standard", nullable = true, length = 255)
	public String getStandard() {
		return standard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "unit", nullable = true, length = 255)
	public String getUnit() {
		return unit;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Column(name = "producer", nullable = true, length = 255)
	public String getProducer() {
		return producer;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Column(name = "symbol", nullable = true, length = 255)
	public String getSymbol() {
		return symbol;
	}

	public void setStoreCondition(String storeCondition) {
		this.storeCondition = storeCondition;
	}

	@Column(name = "store_condition", nullable = true, length = 255)
	public String getStoreCondition() {
		return storeCondition;
	}

	public void setSymbolDate(Date symbolDate) {
		this.symbolDate = symbolDate;
	}

	@Column(name = "symbol_date", nullable = true, length = 7)
	public Date getSymbolDate() {
		return symbolDate;
	}

	public void setDisplayCycle(String displayCycle) {
		this.displayCycle = displayCycle;
	}

	@Column(name = "display_cycle", nullable = true, length = 255)
	public String getDisplayCycle() {
		return displayCycle;
	}

	public void setSymbolDays(Integer symbolDays) {
		this.symbolDays = symbolDays;
	}

	@Column(name = "symbol_days", nullable = true, length = 22)
	public Integer getSymbolDays() {
		return symbolDays;
	}

	public void setDisplayDays(Integer displayDays) {
		this.displayDays = displayDays;
	}

	@Column(name = "display_days", nullable = true, length = 22)
	public Integer getDisplayDays() {
		return displayDays;
	}

	public void setDrugForm(String drugForm) {
		this.drugForm = drugForm;
	}

	@Column(name = "drug_form", nullable = true, length = 255)
	public String getDrugForm() {
		return drugForm;
	}

	public void setIsBatchnumber(String isBatchnumber) {
		this.isBatchnumber = isBatchnumber;
	}

	@Column(name = "is_batchnumber", nullable = true, length = 255)
	public String getIsBatchnumber() {
		return isBatchnumber;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

	@Column(name = "sx_days", nullable = true, length = 22)
	public Integer getSxDays() {
		return sxDays;
	}

	public void setGspManage(String gspManage) {
		this.gspManage = gspManage;
	}

	@Column(name = "gsp_manage", nullable = true, length = 255)
	public String getGspManage() {
		return gspManage;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "type_code", nullable = true, length = 255)
	public String getTypeCode() {
		return typeCode;
	}

	public void setIsRx(String isRx) {
		this.isRx = isRx;
	}

	@Column(name = "is_rx", nullable = true, length = 255)
	public String getIsRx() {
		return isRx;
	}

	public void setDrugStandcode(String drugStandcode) {
		this.drugStandcode = drugStandcode;
	}

	@Column(name = "drug_standcode", nullable = true, length = 255)
	public String getDrugStandcode() {
		return drugStandcode;
	}

	public void setDrugStandard(String drugStandard) {
		this.drugStandard = drugStandard;
	}

	@Column(name = "drug_standard", nullable = true, length = 255)
	public String getDrugStandard() {
		return drugStandard;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	@Column(name = "rang", nullable = true, length = 255)
	public String getRang() {
		return rang;
	}

	public void setQaStandard(String qaStandard) {
		this.qaStandard = qaStandard;
	}

	@Column(name = "qa_standard", nullable = true, length = 255)
	public String getQaStandard() {
		return qaStandard;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "business_category", nullable = true, length = 255)
	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	@Column(name = "category_name", nullable = true, length = 255)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "pres_type", nullable = true, length = 255)
	public String getPresType() {
		return presType;
	}

	public void setPresType(String presType) {
		this.presType = presType;
	}

	@Column(name = "file_info", nullable = true, length = 255)
	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Column(name = "backage_unit", nullable = true, length = 255)
	public String getBackageUnit() {
		return backageUnit;
	}

	public void setBackageUnit(String backageUnit) {
		this.backageUnit = backageUnit;
	}

	@Column(name = "drug_unit", nullable = true, length = 255)
	public String getDrugUnit() {
		return drugUnit;
	}

	public void setDrugUnit(String drugUnit) {
		this.drugUnit = drugUnit;
	}

	@Column(name = "is_protect", nullable = true, length = 255)
	public String getIsProtect() {
		return isProtect;
	}

	public void setIsProtect(String isProtect) {
		this.isProtect = isProtect;
	}

	@Column(name = "is_pull", nullable = true, length = 255)
	public String getIsPull() {
		return isPull;
	}

	public void setIsPull(String isPull) {
		this.isPull = isPull;
	}

	@Column(name = "is_cold", nullable = true, length = 255)
	public String getIsCold() {
		return isCold;
	}

	public void setIsCold(String isCold) {
		this.isCold = isCold;
	}

	@Column(name = "is_ephedrine", nullable = true, length = 255)
	public String getIsEphedrine() {
		return isEphedrine;
	}

	public void setIsEphedrine(String isEphedrine) {
		this.isEphedrine = isEphedrine;
	}

	@Column(name = "drug_place", nullable = true, length = 255)
	public String getDrugPlace() {
		return drugPlace;
	}

	public void setDrugPlace(String drugPlace) {
		this.drugPlace = drugPlace;
	}

	@Column(name = "protect_period", nullable = true, length = 255)
	public String getProtectPeriod() {
		return protectPeriod;
	}

	public void setProtectPeriod(String protectPeriod) {
		this.protectPeriod = protectPeriod;
	}

	@Column(name = "second_class", nullable = true, length = 255)
	public String getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}

	@Column(name = "project_class", nullable = true, length = 255)
	public String getProjectClass() {
		return projectClass;
	}

	public void setProjectClass(String projectClass) {
		this.projectClass = projectClass;
	}

	@Column(name = "project", nullable = true, length = 255)
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Column(name = "market_person", nullable = true, length = 255)
	public String getMarketPerson() {
		return marketPerson;
	}

	public void setMarketPerson(String marketPerson) {
		this.marketPerson = marketPerson;
	}

}
