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
 * PlmProductHeadtempleEntity_HI Entity Object Tue Sep 10 09:45:54 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_HEADTEMPLE")
public class PlmProductHeadtempleEntity_HI {
	private String templName; // 模板名称
	private Integer templeId;
	private String productShape;
	private String dayDamage;
	private String rateClassCode;
	private String productResource;
	private String specialLicence;
	private String uniqueCommodities;
	private String productCategeery;
	private String productProperties;
	private String specialtyProduct;
	private String dangerousProduct;
	private String buyingLevel;
	private String internationProduct;
	private String posInfo;
	private String topProduct;
	private String sesionProduct;
	private String bluecapProduct;
	private String motherCompany;
	private String vcProduct;
	private String crossborderProduct;
	private String companyDeletion;
	private String warehouseResource;
	private String originCountry;
	private String unit;
	private Integer warehousePostDay;
	private Integer warehouseGetDay;
	private String powerOb;
	private String rangOb;
	private String specialRequier;
	private String tier;
	private String productLicense;
	private String transportStorage;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String salesQty;
	private String consultProductno;
	private String consultProductname;
	private String pricewarProduct;
	@JSONField(format = "yyyy-MM-dd")
	private Date consultDate;
	@JSONField(format = "yyyy-MM-dd")
	private Date consultEnddate;
	private String isDiaryproduct;
	private String productReturn;
	private String condition;

	private String allTier;
	private String tier1;
	private String tier2;
	private String tier345;
	private String storeType;
	private String tradeZone;

	private String countUnit;

	private String pogWays;
	private String pogDeparment;
	private String rateClass;

	private String specialRequierName;
	private String transportStorageName;
	private String productLicenseName;

	private String standardOfUnit;

	@Column(name = "standard_of_unit", nullable = true, length = 500)
	public String getStandardOfUnit() {
		return standardOfUnit;
	}

	public void setStandardOfUnit(String standardOfUnit) {
		this.standardOfUnit = standardOfUnit;
	}

	@Column(name = "transport_storage_name", nullable = true, length = 500)
	public String getTransportStorageName() {
		return transportStorageName;
	}

	public void setTransportStorageName(String transportStorageName) {
		this.transportStorageName = transportStorageName;
	}

	@Column(name = "product_license_name", nullable = true, length = 500)
	public String getProductLicenseName() {
		return productLicenseName;
	}

	public void setProductLicenseName(String productLicenseName) {
		this.productLicenseName = productLicenseName;
	}

	@Column(name = "special_requier_name", nullable = true, length = 500)
	public String getSpecialRequierName() {
		return specialRequierName;
	}

	public void setSpecialRequierName(String specialRequierName) {
		this.specialRequierName = specialRequierName;
	}

	@Column(name = "rate_class", nullable = true, length = 255)
	public String getRateClass() {
		return rateClass;
	}

	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}

	public void setTempleId(Integer templeId) {
		this.templeId = templeId;
	}

	@Column(name = "pog_ways", nullable = true, length = 255)
	public String getPogWays() {
		return pogWays;
	}

	public void setPogWays(String pogWays) {
		this.pogWays = pogWays;
	}

	@Column(name = "pog_deparment", nullable = true, length = 255)
	public String getPogDeparment() {
		return pogDeparment;
	}

	public void setPogDeparment(String pogDeparment) {
		this.pogDeparment = pogDeparment;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_TEMPLE", sequenceName = "SEQ_PLM_PRODUCT_TEMPLE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_TEMPLE", strategy = GenerationType.SEQUENCE)
	@Column(name = "temple_id", nullable = false, length = 22)
	public Integer getTempleId() {
		return templeId;
	}

	public void setProductShape(String productShape) {
		this.productShape = productShape;
	}

	@Column(name = "product_shape", nullable = true, length = 255)
	public String getProductShape() {
		return productShape;
	}

	public void setDayDamage(String dayDamage) {
		this.dayDamage = dayDamage;
	}

	@Column(name = "day_damage", nullable = true, length = 255)
	public String getDayDamage() {
		return dayDamage;
	}

	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	@Column(name = "rate_class_code", nullable = true, length = 255)
	public String getRateClassCode() {
		return rateClassCode;
	}

	public void setProductResource(String productResource) {
		this.productResource = productResource;
	}

	@Column(name = "product_resource", nullable = true, length = 255)
	public String getProductResource() {
		return productResource;
	}

	public void setSpecialLicence(String specialLicence) {
		this.specialLicence = specialLicence;
	}

	@Column(name = "special_licence", nullable = true, length = 255)
	public String getSpecialLicence() {
		return specialLicence;
	}

	public void setUniqueCommodities(String uniqueCommodities) {
		this.uniqueCommodities = uniqueCommodities;
	}

	@Column(name = "unique_commodities", nullable = true, length = 255)
	public String getUniqueCommodities() {
		return uniqueCommodities;
	}

	public void setProductCategeery(String productCategeery) {
		this.productCategeery = productCategeery;
	}

	@Column(name = "product_categeery", nullable = true, length = 255)
	public String getProductCategeery() {
		return productCategeery;
	}

	public void setProductProperties(String productProperties) {
		this.productProperties = productProperties;
	}

	@Column(name = "product_properties", nullable = true, length = 255)
	public String getProductProperties() {
		return productProperties;
	}

	public void setSpecialtyProduct(String specialtyProduct) {
		this.specialtyProduct = specialtyProduct;
	}

	@Column(name = "vc_product", nullable = true, length = 255)
	public String getVcProduct() {
		return vcProduct;
	}

	public void setVcProduct(String vcProduct) {
		this.vcProduct = vcProduct;
	}

	@Column(name = "specialty_product", nullable = true, length = 255)
	public String getSpecialtyProduct() {
		return specialtyProduct;
	}

	public void setDangerousProduct(String dangerousProduct) {
		this.dangerousProduct = dangerousProduct;
	}

	@Column(name = "dangerous_product", nullable = true, length = 255)
	public String getDangerousProduct() {
		return dangerousProduct;
	}

	public void setBuyingLevel(String buyingLevel) {
		this.buyingLevel = buyingLevel;
	}

	@Column(name = "buying_level", nullable = true, length = 255)
	public String getBuyingLevel() {
		return buyingLevel;
	}

	public void setInternationProduct(String internationProduct) {
		this.internationProduct = internationProduct;
	}

	@Column(name = "internation_product", nullable = true, length = 255)
	public String getInternationProduct() {
		return internationProduct;
	}

	public void setPosInfo(String posInfo) {
		this.posInfo = posInfo;
	}

	@Column(name = "pos_info", nullable = true, length = 255)
	public String getPosInfo() {
		return posInfo;
	}

	public void setTopProduct(String topProduct) {
		this.topProduct = topProduct;
	}

	@Column(name = "top_product", nullable = true, length = 255)
	public String getTopProduct() {
		return topProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}

	@Column(name = "sesion_product", nullable = true, length = 255)
	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setBluecapProduct(String bluecapProduct) {
		this.bluecapProduct = bluecapProduct;
	}

	@Column(name = "bluecap_product", nullable = true, length = 255)
	public String getBluecapProduct() {
		return bluecapProduct;
	}

	public void setMotherCompany(String motherCompany) {
		this.motherCompany = motherCompany;
	}

	@Column(name = "mother_company", nullable = true, length = 255)
	public String getMotherCompany() {
		return motherCompany;
	}

	public void setCrossborderProduct(String crossborderProduct) {
		this.crossborderProduct = crossborderProduct;
	}

	@Column(name = "crossborder_product", nullable = true, length = 255)
	public String getCrossborderProduct() {
		return crossborderProduct;
	}

	public void setCompanyDeletion(String companyDeletion) {
		this.companyDeletion = companyDeletion;
	}

	@Column(name = "company_deletion", nullable = true, length = 255)
	public String getCompanyDeletion() {
		return companyDeletion;
	}

	public void setWarehousePostDay(Integer warehousePostDay) {
		this.warehousePostDay = warehousePostDay;
	}

	@Column(name = "warehouse_post_day", nullable = true, length = 22)
	public Integer getWarehousePostDay() {
		return warehousePostDay;
	}

	public void setWarehouseGetDay(Integer warehouseGetDay) {
		this.warehouseGetDay = warehouseGetDay;
	}

	@Column(name = "warehouse_get_day", nullable = true, length = 22)
	public Integer getWarehouseGetDay() {
		return warehouseGetDay;
	}

	public void setPowerOb(String powerOb) {
		this.powerOb = powerOb;
	}

	@Column(name = "power_ob", nullable = true, length = 255)
	public String getPowerOb() {
		return powerOb;
	}

	public void setRangOb(String rangOb) {
		this.rangOb = rangOb;
	}

	@Column(name = "rang_ob", nullable = true, length = 255)
	public String getRangOb() {
		return rangOb;
	}

	public void setSpecialRequier(String specialRequier) {
		this.specialRequier = specialRequier;
	}

	@Column(name = "special_requier", nullable = true, length = 255)
	public String getSpecialRequier() {
		return specialRequier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	@Column(name = "tier", nullable = true, length = 255)
	public String getTier() {
		return tier;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
	}

	@Column(name = "product_license", nullable = true, length = 255)
	public String getProductLicense() {
		return productLicense;
	}

	public void setTransportStorage(String transportStorage) {
		this.transportStorage = transportStorage;
	}

	@Column(name = "transport_storage", nullable = true, length = 255)
	public String getTransportStorage() {
		return transportStorage;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "start_date", nullable = true, length = 7)
	public Date getStartDate() {
		return startDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "warehouse_resource", nullable = true, length = 255)
	public String getWarehouseResource() {
		return warehouseResource;
	}

	public void setWarehouseResource(String warehouseResource) {
		this.warehouseResource = warehouseResource;
	}

	@Column(name = "origin_country", nullable = true, length = 255)
	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	@Column(name = "unit", nullable = true, length = 255)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "sales_qty", nullable = true, length = 255)
	public String getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	@Column(name = "consult_productno", nullable = true, length = 255)
	public String getConsultProductno() {
		return consultProductno;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	@Column(name = "consult_productname", nullable = true, length = 255)
	public String getConsultProductname() {
		return consultProductname;
	}

	public void setConsultProductname(String consultProductname) {
		this.consultProductname = consultProductname;
	}

	@Column(name = "pricewar_product", nullable = true, length = 255)
	public String getPricewarProduct() {
		return pricewarProduct;
	}

	public void setPricewarProduct(String pricewarProduct) {
		this.pricewarProduct = pricewarProduct;
	}

	@Column(name = "consult_date", nullable = true, length = 7)
	public Date getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}

	@Column(name = "consult_enddate", nullable = true, length = 7)
	public Date getConsultEnddate() {
		return consultEnddate;
	}

	public void setConsultEnddate(Date consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	@Column(name = "is_diaryproduct", nullable = true, length = 255)
	public String getIsDiaryproduct() {
		return isDiaryproduct;
	}

	public void setIsDiaryproduct(String isDiaryproduct) {
		this.isDiaryproduct = isDiaryproduct;
	}

	@Column(name = "product_return", nullable = true, length = 255)
	public String getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	@Column(name = "condition", nullable = true, length = 255)
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name = "all_tier", nullable = true, length = 255)
	public String getAllTier() {
		return allTier;
	}

	public void setAllTier(String allTier) {
		this.allTier = allTier;
	}

	@Column(name = "tier1", nullable = true, length = 255)
	public String getTier1() {
		return tier1;
	}

	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}

	@Column(name = "tier2", nullable = true, length = 255)
	public String getTier2() {
		return tier2;
	}

	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}

	@Column(name = "tier345", nullable = true, length = 255)
	public String getTier345() {
		return tier345;
	}

	public void setTier345(String tier345) {
		this.tier345 = tier345;
	}

	@Column(name = "store_type", nullable = true, length = 255)
	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name = "trade_zone", nullable = true, length = 255)
	public String getTradeZone() {
		return tradeZone;
	}

	public void setTradeZone(String tradeZone) {
		this.tradeZone = tradeZone;
	}

	@Column(name = "count_unit", nullable = true, length = 255)
	public String getCountUnit() {
		return countUnit;
	}

	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}

	@Column(name = "templ_name", nullable = true, length = 255)
	public String getTemplName() {
		return templName;
	}

	public void setTemplName(String templName) {
		this.templName = templName;
	}

}
