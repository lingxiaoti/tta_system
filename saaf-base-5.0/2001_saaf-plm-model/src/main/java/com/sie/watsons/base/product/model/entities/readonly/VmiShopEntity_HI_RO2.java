package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * VmiShopEntity_HI_RO Entity Object Mon Dec 02 20:44:11 CST 2019 Auto Generate
 */

public class VmiShopEntity_HI_RO2 {
	public static String query = "SELECT\r\n" + "shop.VS_ID AS vsId,\r\n"
			+ "shop.VH_PRE_CODE AS vhPreCode,\r\n"
			+ "shop.VS_CODE AS vsCode,\r\n" + "shop.CHAIN AS chain,\r\n"
			+ "shop.AREA_ID AS areaId,\r\n" + "shop.AREA_EN AS areaEn,\r\n"
			+ "shop.REGION_ID AS regionId,\r\n"
			+ "shop.REGION_EN AS regionEn,\r\n" + "shop.CITY_ID AS cityId,\r\n"
			+ "shop.CITY_EN AS cityEn,\r\n" + "shop.CITY_CH AS cityCh,\r\n"
			+ "shop.STORE_SHORT_DESC_CH AS storeShortDescCh,\r\n"
			+ "shop.STORE_SHORT_DESC_EN AS storeShortDescEn,\r\n"
			+ "shop.STORE_NAME_CH AS storeNameCh,\r\n"
			+ "shop.STORE_TYPE1 AS storeType1,\r\n"
			+ "shop.STORE_MANAGER AS storeManager,\r\n"
			+ "shop.RUN_DAY AS runDay,\r\n" + "shop.CLOSE_DAY AS closeDay,\r\n"
			+ "shop.VS_NAME AS vsName,\r\n"
			+ "shop.STORE_SQUARE AS storeSquare,\r\n"
			+ "shop.SALES_SQUARE AS salesSquare,\r\n"
			+ "shop.COMP AS comp,\r\n" + "shop.PRICE_ZONE AS priceZone,\r\n"
			+ "shop.STORE_SEG AS storeSeg,\r\n"
			+ "shop.STORE_TYPE AS storeType,\r\n"
			+ "shop.MARKET AS market,\r\n" + "shop.CITY_TIER AS cityTier,\r\n"
			+ "shop.DISTRICT AS district,\r\n"
			+ "shop.DISTRICT_EN AS districtEn,\r\n"
			+ "shop.DELIVERY_LT AS deliveryLt,\r\n"
			+ "shop.ORDER_DATE AS orderDate,\r\n"
			+ "shop.ARRIVE_DATE AS arriveDate,\r\n"
			+ "shop.STORE_STATUS AS storeStatus,\r\n"
			+ "shop.FIX_START_DATE AS fixStartDate,\r\n"
			+ "shop.FIX_END_DATE AS fixEndDate,\r\n"
			+ "shop.FORBID_SOLD AS forbidSold,\r\n"
			+ "shop.BUSINESS_HOURS AS businessHours,\r\n"
			+ "shop.LEADER_ID AS leaderId,\r\n" + "shop.LEADER AS leader,\r\n"
			+ "shop.HUB AS hub,\r\n" + "shop.SUPERVISOR AS supervisor,\r\n"
			+ "shop.LOCAL_FEATURE AS localFeature,\r\n"
			+ "shop.LICENCE AS licence,\r\n"
			+ "shop.ADDRESS_DETAIL AS addressDetail,\r\n"
			+ "shop.CONTRACTS AS contracts,\r\n" + "shop.PHONE AS phone,\r\n"
			+ "shop.CREATED_BY AS createdBy,\r\n"
			+ "shop.LAST_UPDATED_BY AS lastUpdatedBy,\r\n"
			+ "shop.LAST_UPDATE_DATE AS lastUpdateDate,\r\n"
			+ "shop.LAST_UPDATE_LOGIN AS lastUpdateLogin,\r\n"
			+ "shop.CREATION_DATE AS creationDate,\r\n"
			+ "shop.VERSION_NUM AS versionNum,\r\n"
			+ "shop.REGIONAL_MANAGER AS regionalManager,\r\n"
			+ "shop.AREA_CH AS areaCh,\r\n" + "shop.REGION_CH AS regionCh,\r\n"
			+ "shop.DRUGSTORE_ATTRIBUTE AS drugstoreAttribute\r\n" + "FROM\r\n"
			+ "VMI_SHOP shop where  close_day is null ";

	// public static String query2 =
	// " select area_id,area_en,area_ch from (select area_id,area_en,area_ch from vmi_shop where area_ch is not null and \r\n"
	// +
	// "  area_en!='E-COM'  group by area_id,area_en,area_ch order by area_id asc) area where 1=1 ";

	public static String getSqlQuery(String condition) {
		return " select area_id,area_en,area_ch from (select area_id,area_en,area_ch from vmi_shop where area_ch is not null and \r\n"
				+ "  area_en!='E-COM' "
				+ condition
				+ " group by area_id,area_en,area_ch order by area_id asc) area where 1=1 ";
	}

	private Integer vsId;
	private String vhPreCode;
	private String vsCode;
	private String chain;
	private String areaId;
	private String areaEn;
	private String regionId;
	private String regionEn;
	private String cityId;
	private String cityEn;
	private String cityCh;
	private String storeShortDescCh;
	private String storeShortDescEn;
	private String storeNameCh;
	private String storeType1;
	private String storeManager;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date runDay;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date closeDay;
	private String vsName;
	private Integer storeSquare;
	private Integer salesSquare;
	private String comp;
	private String priceZone;
	private String storeSeg;
	private String storeType;
	private String market;
	private String cityTier;
	private String district;
	private String districtEn;
	private String deliveryLt;
	private String orderDate;
	private String arriveDate;
	private String storeStatus;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date fixStartDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date fixEndDate;
	private String forbidSold;
	private String businessHours;
	private Integer leaderId;
	private String leader;
	private String hub;
	private String supervisor;
	private String localFeature;
	private String licence;
	private String addressDetail;
	private String contracts;
	private String phone;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer versionNum;
	private String regionalManager;
	private String areaCh;
	private String regionCh;
	private String drugstoreAttribute;
	private Integer operatorUserId;

	public void setVsId(Integer vsId) {
		this.vsId = vsId;
	}

	public Integer getVsId() {
		return vsId;
	}

	public void setVhPreCode(String vhPreCode) {
		this.vhPreCode = vhPreCode;
	}

	public String getVhPreCode() {
		return vhPreCode;
	}

	public void setVsCode(String vsCode) {
		this.vsCode = vsCode;
	}

	public String getVsCode() {
		return vsCode;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getChain() {
		return chain;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}

	public String getAreaEn() {
		return areaEn;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionEn(String regionEn) {
		this.regionEn = regionEn;
	}

	public String getRegionEn() {
		return regionEn;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityEn(String cityEn) {
		this.cityEn = cityEn;
	}

	public String getCityEn() {
		return cityEn;
	}

	public void setCityCh(String cityCh) {
		this.cityCh = cityCh;
	}

	public String getCityCh() {
		return cityCh;
	}

	public void setStoreShortDescCh(String storeShortDescCh) {
		this.storeShortDescCh = storeShortDescCh;
	}

	public String getStoreShortDescCh() {
		return storeShortDescCh;
	}

	public void setStoreShortDescEn(String storeShortDescEn) {
		this.storeShortDescEn = storeShortDescEn;
	}

	public String getStoreShortDescEn() {
		return storeShortDescEn;
	}

	public void setStoreNameCh(String storeNameCh) {
		this.storeNameCh = storeNameCh;
	}

	public String getStoreNameCh() {
		return storeNameCh;
	}

	public void setStoreType1(String storeType1) {
		this.storeType1 = storeType1;
	}

	public String getStoreType1() {
		return storeType1;
	}

	public void setStoreManager(String storeManager) {
		this.storeManager = storeManager;
	}

	public String getStoreManager() {
		return storeManager;
	}

	public void setRunDay(Date runDay) {
		this.runDay = runDay;
	}

	public Date getRunDay() {
		return runDay;
	}

	public void setCloseDay(Date closeDay) {
		this.closeDay = closeDay;
	}

	public Date getCloseDay() {
		return closeDay;
	}

	public void setVsName(String vsName) {
		this.vsName = vsName;
	}

	public String getVsName() {
		return vsName;
	}

	public void setStoreSquare(Integer storeSquare) {
		this.storeSquare = storeSquare;
	}

	public Integer getStoreSquare() {
		return storeSquare;
	}

	public void setSalesSquare(Integer salesSquare) {
		this.salesSquare = salesSquare;
	}

	public Integer getSalesSquare() {
		return salesSquare;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getComp() {
		return comp;
	}

	public void setPriceZone(String priceZone) {
		this.priceZone = priceZone;
	}

	public String getPriceZone() {
		return priceZone;
	}

	public void setStoreSeg(String storeSeg) {
		this.storeSeg = storeSeg;
	}

	public String getStoreSeg() {
		return storeSeg;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getMarket() {
		return market;
	}

	public void setCityTier(String cityTier) {
		this.cityTier = cityTier;
	}

	public String getCityTier() {
		return cityTier;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrictEn(String districtEn) {
		this.districtEn = districtEn;
	}

	public String getDistrictEn() {
		return districtEn;
	}

	public void setDeliveryLt(String deliveryLt) {
		this.deliveryLt = deliveryLt;
	}

	public String getDeliveryLt() {
		return deliveryLt;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setFixStartDate(Date fixStartDate) {
		this.fixStartDate = fixStartDate;
	}

	public Date getFixStartDate() {
		return fixStartDate;
	}

	public void setFixEndDate(Date fixEndDate) {
		this.fixEndDate = fixEndDate;
	}

	public Date getFixEndDate() {
		return fixEndDate;
	}

	public void setForbidSold(String forbidSold) {
		this.forbidSold = forbidSold;
	}

	public String getForbidSold() {
		return forbidSold;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getLeader() {
		return leader;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getHub() {
		return hub;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setLocalFeature(String localFeature) {
		this.localFeature = localFeature;
	}

	public String getLocalFeature() {
		return localFeature;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getLicence() {
		return licence;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setContracts(String contracts) {
		this.contracts = contracts;
	}

	public String getContracts() {
		return contracts;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setRegionalManager(String regionalManager) {
		this.regionalManager = regionalManager;
	}

	public String getRegionalManager() {
		return regionalManager;
	}

	public void setAreaCh(String areaCh) {
		this.areaCh = areaCh;
	}

	public String getAreaCh() {
		return areaCh;
	}

	public void setRegionCh(String regionCh) {
		this.regionCh = regionCh;
	}

	public String getRegionCh() {
		return regionCh;
	}

	public void setDrugstoreAttribute(String drugstoreAttribute) {
		this.drugstoreAttribute = drugstoreAttribute;
	}

	public String getDrugstoreAttribute() {
		return drugstoreAttribute;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
