package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductHeadtempleEntity_HI_RO Entity Object Tue Sep 10 09:45:54 CST 2019
 * Auto Generate
 */

public class PlmProductHeadtempleEntity_HI_RO {
	public static final String QUERY_HEAD = "SELECT\r\n"
			+ "     temp.templ_name AS templName,\r\n"
			+ "     temp.temple_id             AS templeid,\r\n"
			+ "     temp.product_shape         AS productshape,\r\n"
			+ "     temp.day_damage            AS daydamage,\r\n"
			+ "     temp.rate_class_code       AS rateclasscode,\r\n"
			+ "     temp.product_resource      AS productresource,\r\n"
			+ "     temp.special_licence       AS speciallicence,\r\n"
			+ "     temp.unique_commodities    AS uniquecommodities,\r\n"
			+ "     temp.product_categeery     AS productcategeery,\r\n"
			+ "     temp.product_properties    AS productproperties,\r\n"
			+ "     temp.specialty_product     AS specialtyproduct,\r\n"
			+ "     temp.dangerous_product     AS dangerousproduct,\r\n"
			+ "     temp.buying_level          AS buyinglevel,\r\n"
			+ "     temp.internation_product   AS internationproduct,\r\n"
			+ "     temp.pos_info              AS posinfo,\r\n"
			+ "     temp.top_product           AS topproduct,\r\n"
			+ "     temp.sesion_product        AS sesionproduct,\r\n"
			+ "     temp.bluecap_product       AS bluecapproduct,\r\n"
			+ "     temp.mother_company        AS mothercompany,\r\n"
			+ "     temp.count_unit            AS countunit,\r\n"
			+ "     temp.trade_zone            AS tradezone,\r\n"
			+ "     temp.store_type            AS storetype,\r\n"
			+ "     temp.tier345               AS tier345,\r\n"
			+ "     temp.tier2                 AS tier2,\r\n"
			+ "     temp.tier1                 AS tier1,\r\n"
			+ "     temp.all_tier              AS alltier,\r\n"
			+ "     temp.condition             AS condition,\r\n"
			+ "     temp.product_return        AS productreturn,\r\n"
			+ "     temp.is_diaryproduct       AS isdiaryproduct,\r\n"
			+ "     temp.consult_enddate       AS consultenddate,\r\n"
			+ "     temp.consult_date          AS consultdate,\r\n"
			+ "     temp.pricewar_product      AS pricewarproduct,\r\n"
			+ "     temp.consult_productname   AS consultproductname,\r\n"
			+ "     temp.consult_productno     AS consultproductno,\r\n"
			+ "     temp.sales_qty             AS salesqty,\r\n"
			+ "     temp.unit                  AS unit,\r\n"
			+ "     temp.origin_country        AS origincountry,\r\n"
			+ "     temp.warehouse_resource    AS warehouseresource,\r\n"
			+ "     temp.vc_product            AS vcproduct,\r\n"
			+ "     temp.last_update_login     AS lastupdatelogin,\r\n"
			+ "     temp.last_update_date      AS lastupdatedate,\r\n"
			+ "     temp.last_updated_by       AS lastupdatedby,\r\n"
			+ "     temp.created_by            AS createdby,\r\n"
			+ "     temp.creation_date         AS creationdate,\r\n"
			+ "     temp.version_num           AS versionnum,\r\n"
			+ "     temp.start_date            AS startdate,\r\n"
			+ "     temp.transport_storage     AS transportstorage,\r\n"
			+ "     temp.product_license       AS productlicense,\r\n"
			+ "     temp.tier                  AS tier,\r\n"
			+ "     temp.special_requier       AS specialrequier,\r\n"
			+ "     temp.rang_ob               AS rangob,\r\n"
			+ "     temp.power_ob              AS powerob,\r\n"
			+ "     temp.warehouse_get_day     AS warehousegetday,\r\n"
			+ "     temp.warehouse_post_day    AS warehousepostday,\r\n"
			+ "     temp.company_deletion      AS companydeletion,\r\n"
			+ "     temp.crossborder_product   AS crossborderproduct,\r\n"
			+ "     temp.pog_ways   AS pogWays,\r\n"
			+ "     temp.pog_deparment   AS pogDeparment,\r\n"
			+ "     temp.special_requier_name   AS specialRequierName,\r\n"
			+ "     temp.transport_storage_name   AS transportStorageName,\r\n"
			+ "     temp.product_license_name   AS productLicenseName,\r\n"
			+ "     temp.rate_class   AS rateClass, \r\n"
			+ "     temp.standard_of_unit    AS standardOfUnit \r\n"
			+ " FROM\r\n" + "     plm_product_headtemple temp where 1=1";
	private Integer templeId;
	private String templName; // 模板名称
	private String productShape;
	private String productShapestr;
	private String dayDamage;
	private String dayDamagestr;
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
	private String crossborderProduct;
	private String vcProduct;
	private String companyDeletion;
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
	private String warehouseResource;
	private String originCountry;
	private String unit;
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

	private String specialRequierName;
	private String transportStorageName;
	private String productLicenseName;
	private String standardOfUnit;

	public String getStandardOfUnit() {
		return standardOfUnit;
	}

	public void setStandardOfUnit(String standardOfUnit) {
		this.standardOfUnit = standardOfUnit;
	}

	public String getSpecialRequierName() {
		return specialRequierName;
	}

	public void setSpecialRequierName(String specialRequierName) {
		this.specialRequierName = specialRequierName;
	}

	public String getTransportStorageName() {
		return transportStorageName;
	}

	public void setTransportStorageName(String transportStorageName) {
		this.transportStorageName = transportStorageName;
	}

	public String getProductLicenseName() {
		return productLicenseName;
	}

	public void setProductLicenseName(String productLicenseName) {
		this.productLicenseName = productLicenseName;
	}

	public void setTempleId(Integer templeId) {
		this.templeId = templeId;
	}

	public Integer getTempleId() {
		return templeId;
	}

	public String getTemplName() {
		return templName;
	}

	public void setTemplName(String templName) {
		this.templName = templName;
	}

	public void setProductShape(String productShape) {
		this.productShape = productShape;
	}

	public String getProductShape() {
		return productShape;
	}

	public void setDayDamage(String dayDamage) {
		this.dayDamage = dayDamage;
	}

	public String getDayDamage() {
		return dayDamage;
	}

	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	public String getRateClassCode() {
		return rateClassCode;
	}

	public void setProductResource(String productResource) {
		this.productResource = productResource;
	}

	public String getProductResource() {
		return productResource;
	}

	public String getVcProduct() {
		return vcProduct;
	}

	public void setVcProduct(String vcProduct) {
		this.vcProduct = vcProduct;
	}

	public void setSpecialLicence(String specialLicence) {
		this.specialLicence = specialLicence;
	}

	public String getSpecialLicence() {
		return specialLicence;
	}

	public void setUniqueCommodities(String uniqueCommodities) {
		this.uniqueCommodities = uniqueCommodities;
	}

	public String getUniqueCommodities() {
		return uniqueCommodities;
	}

	public void setProductCategeery(String productCategeery) {
		this.productCategeery = productCategeery;
	}

	public String getProductCategeery() {
		return productCategeery;
	}

	public void setProductProperties(String productProperties) {
		this.productProperties = productProperties;
	}

	public String getProductProperties() {
		return productProperties;
	}

	public void setSpecialtyProduct(String specialtyProduct) {
		this.specialtyProduct = specialtyProduct;
	}

	public String getSpecialtyProduct() {
		return specialtyProduct;
	}

	public void setDangerousProduct(String dangerousProduct) {
		this.dangerousProduct = dangerousProduct;
	}

	public String getDangerousProduct() {
		return dangerousProduct;
	}

	public void setBuyingLevel(String buyingLevel) {
		this.buyingLevel = buyingLevel;
	}

	public String getBuyingLevel() {
		return buyingLevel;
	}

	public void setInternationProduct(String internationProduct) {
		this.internationProduct = internationProduct;
	}

	public String getInternationProduct() {
		return internationProduct;
	}

	public void setPosInfo(String posInfo) {
		this.posInfo = posInfo;
	}

	public String getPosInfo() {
		return posInfo;
	}

	public void setTopProduct(String topProduct) {
		this.topProduct = topProduct;
	}

	public String getTopProduct() {
		return topProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}

	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setBluecapProduct(String bluecapProduct) {
		this.bluecapProduct = bluecapProduct;
	}

	public String getBluecapProduct() {
		return bluecapProduct;
	}

	public void setMotherCompany(String motherCompany) {
		this.motherCompany = motherCompany;
	}

	public String getMotherCompany() {
		return motherCompany;
	}

	public void setCrossborderProduct(String crossborderProduct) {
		this.crossborderProduct = crossborderProduct;
	}

	public String getCrossborderProduct() {
		return crossborderProduct;
	}

	public void setCompanyDeletion(String companyDeletion) {
		this.companyDeletion = companyDeletion;
	}

	public String getCompanyDeletion() {
		return companyDeletion;
	}

	public void setWarehousePostDay(Integer warehousePostDay) {
		this.warehousePostDay = warehousePostDay;
	}

	public Integer getWarehousePostDay() {
		return warehousePostDay;
	}

	public void setWarehouseGetDay(Integer warehouseGetDay) {
		this.warehouseGetDay = warehouseGetDay;
	}

	public Integer getWarehouseGetDay() {
		return warehouseGetDay;
	}

	public void setPowerOb(String powerOb) {
		this.powerOb = powerOb;
	}

	public String getPowerOb() {
		return powerOb;
	}

	public void setRangOb(String rangOb) {
		this.rangOb = rangOb;
	}

	public String getRangOb() {
		return rangOb;
	}

	public void setSpecialRequier(String specialRequier) {
		this.specialRequier = specialRequier;
	}

	public String getSpecialRequier() {
		return specialRequier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getTier() {
		return tier;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
	}

	public String getProductLicense() {
		return productLicense;
	}

	public void setTransportStorage(String transportStorage) {
		this.transportStorage = transportStorage;
	}

	public String getTransportStorage() {
		return transportStorage;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getWarehouseResource() {
		return warehouseResource;
	}

	public void setWarehouseResource(String warehouseResource) {
		this.warehouseResource = warehouseResource;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	public String getConsultProductno() {
		return consultProductno;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	public String getConsultProductname() {
		return consultProductname;
	}

	public void setConsultProductname(String consultProductname) {
		this.consultProductname = consultProductname;
	}

	public String getPricewarProduct() {
		return pricewarProduct;
	}

	public void setPricewarProduct(String pricewarProduct) {
		this.pricewarProduct = pricewarProduct;
	}

	public Date getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}

	public Date getConsultEnddate() {
		return consultEnddate;
	}

	public void setConsultEnddate(Date consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	public String getIsDiaryproduct() {
		return isDiaryproduct;
	}

	public void setIsDiaryproduct(String isDiaryproduct) {
		this.isDiaryproduct = isDiaryproduct;
	}

	public String getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getAllTier() {
		return allTier;
	}

	public void setAllTier(String allTier) {
		this.allTier = allTier;
	}

	public String getTier1() {
		return tier1;
	}

	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}

	public String getTier2() {
		return tier2;
	}

	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}

	public String getTier345() {
		return tier345;
	}

	public void setTier345(String tier345) {
		this.tier345 = tier345;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getTradeZone() {
		return tradeZone;
	}

	public void setTradeZone(String tradeZone) {
		this.tradeZone = tradeZone;
	}

	public String getCountUnit() {
		return countUnit;
	}

	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}

}
