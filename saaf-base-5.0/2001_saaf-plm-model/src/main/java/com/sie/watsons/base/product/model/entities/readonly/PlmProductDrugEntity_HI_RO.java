package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductDrugEntity_HI_RO Entity Object Mon Sep 09 16:06:42 CST 2019 Auto
 * Generate
 */

public class PlmProductDrugEntity_HI_RO {

	public static final String QUERY = "SELECT ppd.drug_ins drugIns,p.* from PLM_PRODUCT_HEAD p "
			+ "   left join PLM_PRODUCT_DRUG ppd on ppd.product_head_id= p.product_head_id\n "
			+ "where order_status = '6' "
			+ "and to_char(p.creation_date,'yyyy-mm-dd') = ':creatDate'";

	public static final String storeSql = "SELECT v.vs_code             code\n"
			+ "                    ,v.drugstore_attribute storetype\n"
			+ "                    , p.\"shop_id\" shopId\n"
			+ "                FROM plm_product_supplierplaceinfo s\n"
			+ "                LEFT JOIN vmi_shop v\n"
			+ "                  ON v.vs_code = s.location\n"
			+ "                LEFT JOIN plm_chain_store p\n"
			+ "                  ON v.vs_code =  p.\"shop_id\" \n"
			+ "               WHERE 1 = 1\n"
			+ "                 AND s.loc_type = 'S'\n"
			+ "                 AND v.drugstore_attribute IS NOT NULL\n"
			+ "                 AND s.product_head_id = 97422";

	public static final String storeChainSql = "select s.\"shop_id\" shopId,s.\"shop_name\" shopName,s.\"parent_id\" parentId   "
			+ " from PLM_CHAIN_STORE s  ";

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
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
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

	private String secondClass;

	private String projectClass;

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

	private String businessCategory;// 经营类别
	private String categoryName;// 类别名称
	private String presType;// 处方药类别
	private String fileInfo;// 批准证明文件及其附件
	private String backageUnit;// 包装规格
	private String drugUnit;// 药监包装规格
	private String isProtect;// 是否重点养护
	private String isPull;// 是否拆零
	private String isCold;// 是否冷藏药品
	private String isEphedrine;// 是否含麻黄碱
	private String drugPlace;// 产地（中药饮片）
	private String protectPeriod;// 保质期

	private String marketPerson; // 上市许可持有人
	private String OPK;

	private String rangName;

	public String getRangName() {
		return rangName;
	}

	public void setRangName(String rangName) {
		this.rangName = rangName;
	}

	public String getOPK() {
		return OPK;
	}

	public void setOPK(String oPK) {
		OPK = oPK;
	}

	public static void main(String[] args) {
		System.out.println("SQL--->>>:::" + QUERY);
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getPresType() {
		return presType;
	}

	public void setPresType(String presType) {
		this.presType = presType;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getBackageUnit() {
		return backageUnit;
	}

	public void setBackageUnit(String backageUnit) {
		this.backageUnit = backageUnit;
	}

	public String getDrugUnit() {
		return drugUnit;
	}

	public void setDrugUnit(String drugUnit) {
		this.drugUnit = drugUnit;
	}

	public String getIsProtect() {
		return isProtect;
	}

	public void setIsProtect(String isProtect) {
		this.isProtect = isProtect;
	}

	public String getIsPull() {
		return isPull;
	}

	public void setIsPull(String isPull) {
		this.isPull = isPull;
	}

	public String getIsCold() {
		return isCold;
	}

	public void setIsCold(String isCold) {
		this.isCold = isCold;
	}

	public String getIsEphedrine() {
		return isEphedrine;
	}

	public void setIsEphedrine(String isEphedrine) {
		this.isEphedrine = isEphedrine;
	}

	public String getDrugPlace() {
		return drugPlace;
	}

	public void setDrugPlace(String drugPlace) {
		this.drugPlace = drugPlace;
	}

	public String getProtectPeriod() {
		return protectPeriod;
	}

	public void setProtectPeriod(String protectPeriod) {
		this.protectPeriod = protectPeriod;
	}

	public String getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}

	public String getProjectClass() {
		return projectClass;
	}

	public void setProjectClass(String projectClass) {
		this.projectClass = projectClass;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getMarketPerson() {
		return marketPerson;
	}

	public void setMarketPerson(String marketPerson) {
		this.marketPerson = marketPerson;
	}

}
