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
 * VmiShopEntity_HI Entity Object Mon Dec 02 20:44:11 CST 2019 Auto Generate
 */
@Entity
@Table(name = "vmi_shop")
public class VmiShopEntity_HI2 {
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

	@Id
	@SequenceGenerator(name = "VMI_SHOP_SEQ", sequenceName = "VMI_SHOP_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "VMI_SHOP_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "vs_id", nullable = false, length = 22)
	public Integer getVsId() {
		return vsId;
	}

	public void setVhPreCode(String vhPreCode) {
		this.vhPreCode = vhPreCode;
	}

	@Column(name = "vh_pre_code", nullable = true, length = 20)
	public String getVhPreCode() {
		return vhPreCode;
	}

	public void setVsCode(String vsCode) {
		this.vsCode = vsCode;
	}

	@Column(name = "vs_code", nullable = true, length = 50)
	public String getVsCode() {
		return vsCode;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	@Column(name = "chain", nullable = true, length = 45)
	public String getChain() {
		return chain;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "area_id", nullable = true, length = 45)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}

	@Column(name = "area_en", nullable = true, length = 45)
	public String getAreaEn() {
		return areaEn;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	@Column(name = "region_id", nullable = true, length = 45)
	public String getRegionId() {
		return regionId;
	}

	public void setRegionEn(String regionEn) {
		this.regionEn = regionEn;
	}

	@Column(name = "region_en", nullable = true, length = 45)
	public String getRegionEn() {
		return regionEn;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Column(name = "city_id", nullable = true, length = 45)
	public String getCityId() {
		return cityId;
	}

	public void setCityEn(String cityEn) {
		this.cityEn = cityEn;
	}

	@Column(name = "city_en", nullable = true, length = 45)
	public String getCityEn() {
		return cityEn;
	}

	public void setCityCh(String cityCh) {
		this.cityCh = cityCh;
	}

	@Column(name = "city_ch", nullable = true, length = 45)
	public String getCityCh() {
		return cityCh;
	}

	public void setStoreShortDescCh(String storeShortDescCh) {
		this.storeShortDescCh = storeShortDescCh;
	}

	@Column(name = "store_short_desc_ch", nullable = true, length = 100)
	public String getStoreShortDescCh() {
		return storeShortDescCh;
	}

	public void setStoreShortDescEn(String storeShortDescEn) {
		this.storeShortDescEn = storeShortDescEn;
	}

	@Column(name = "store_short_desc_en", nullable = true, length = 100)
	public String getStoreShortDescEn() {
		return storeShortDescEn;
	}

	public void setStoreNameCh(String storeNameCh) {
		this.storeNameCh = storeNameCh;
	}

	@Column(name = "store_name_ch", nullable = true, length = 100)
	public String getStoreNameCh() {
		return storeNameCh;
	}

	public void setStoreType1(String storeType1) {
		this.storeType1 = storeType1;
	}

	@Column(name = "store_type1", nullable = true, length = 100)
	public String getStoreType1() {
		return storeType1;
	}

	public void setStoreManager(String storeManager) {
		this.storeManager = storeManager;
	}

	@Column(name = "store_manager", nullable = true, length = 20)
	public String getStoreManager() {
		return storeManager;
	}

	public void setRunDay(Date runDay) {
		this.runDay = runDay;
	}

	@Column(name = "run_day", nullable = true, length = 7)
	public Date getRunDay() {
		return runDay;
	}

	public void setCloseDay(Date closeDay) {
		this.closeDay = closeDay;
	}

	@Column(name = "close_day", nullable = true, length = 7)
	public Date getCloseDay() {
		return closeDay;
	}

	public void setVsName(String vsName) {
		this.vsName = vsName;
	}

	@Column(name = "vs_name", nullable = true, length = 100)
	public String getVsName() {
		return vsName;
	}

	public void setStoreSquare(Integer storeSquare) {
		this.storeSquare = storeSquare;
	}

	@Column(name = "store_square", nullable = true, length = 22)
	public Integer getStoreSquare() {
		return storeSquare;
	}

	public void setSalesSquare(Integer salesSquare) {
		this.salesSquare = salesSquare;
	}

	@Column(name = "sales_square", nullable = true, length = 22)
	public Integer getSalesSquare() {
		return salesSquare;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	@Column(name = "comp", nullable = true, length = 45)
	public String getComp() {
		return comp;
	}

	public void setPriceZone(String priceZone) {
		this.priceZone = priceZone;
	}

	@Column(name = "price_zone", nullable = true, length = 45)
	public String getPriceZone() {
		return priceZone;
	}

	public void setStoreSeg(String storeSeg) {
		this.storeSeg = storeSeg;
	}

	@Column(name = "store_seg", nullable = true, length = 45)
	public String getStoreSeg() {
		return storeSeg;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name = "store_type", nullable = true, length = 45)
	public String getStoreType() {
		return storeType;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	@Column(name = "market", nullable = true, length = 45)
	public String getMarket() {
		return market;
	}

	public void setCityTier(String cityTier) {
		this.cityTier = cityTier;
	}

	@Column(name = "city_tier", nullable = true, length = 45)
	public String getCityTier() {
		return cityTier;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "district", nullable = true, length = 45)
	public String getDistrict() {
		return district;
	}

	public void setDistrictEn(String districtEn) {
		this.districtEn = districtEn;
	}

	@Column(name = "district_en", nullable = true, length = 45)
	public String getDistrictEn() {
		return districtEn;
	}

	public void setDeliveryLt(String deliveryLt) {
		this.deliveryLt = deliveryLt;
	}

	@Column(name = "delivery_lt", nullable = true, length = 45)
	public String getDeliveryLt() {
		return deliveryLt;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "order_date", nullable = true, length = 100)
	public String getOrderDate() {
		return orderDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	@Column(name = "arrive_date", nullable = true, length = 100)
	public String getArriveDate() {
		return arriveDate;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	@Column(name = "store_status", nullable = true, length = 100)
	public String getStoreStatus() {
		return storeStatus;
	}

	public void setFixStartDate(Date fixStartDate) {
		this.fixStartDate = fixStartDate;
	}

	@Column(name = "fix_start_date", nullable = true, length = 7)
	public Date getFixStartDate() {
		return fixStartDate;
	}

	public void setFixEndDate(Date fixEndDate) {
		this.fixEndDate = fixEndDate;
	}

	@Column(name = "fix_end_date", nullable = true, length = 7)
	public Date getFixEndDate() {
		return fixEndDate;
	}

	public void setForbidSold(String forbidSold) {
		this.forbidSold = forbidSold;
	}

	@Column(name = "forbid_sold", nullable = true, length = 100)
	public String getForbidSold() {
		return forbidSold;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	@Column(name = "business_hours", nullable = true, length = 45)
	public String getBusinessHours() {
		return businessHours;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	@Column(name = "leader_id", nullable = true, length = 22)
	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	@Column(name = "leader", nullable = true, length = 45)
	public String getLeader() {
		return leader;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	@Column(name = "hub", nullable = true, length = 45)
	public String getHub() {
		return hub;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	@Column(name = "supervisor", nullable = true, length = 45)
	public String getSupervisor() {
		return supervisor;
	}

	public void setLocalFeature(String localFeature) {
		this.localFeature = localFeature;
	}

	@Column(name = "local_feature", nullable = true, length = 100)
	public String getLocalFeature() {
		return localFeature;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	@Column(name = "licence", nullable = true, length = 100)
	public String getLicence() {
		return licence;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	@Column(name = "address_detail", nullable = true, length = 200)
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setContracts(String contracts) {
		this.contracts = contracts;
	}

	@Column(name = "contracts", nullable = true, length = 100)
	public String getContracts() {
		return contracts;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "phone", nullable = true, length = 100)
	public String getPhone() {
		return phone;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setRegionalManager(String regionalManager) {
		this.regionalManager = regionalManager;
	}

	@Column(name = "regional_manager", nullable = true, length = 255)
	public String getRegionalManager() {
		return regionalManager;
	}

	public void setAreaCh(String areaCh) {
		this.areaCh = areaCh;
	}

	@Column(name = "area_ch", nullable = true, length = 255)
	public String getAreaCh() {
		return areaCh;
	}

	public void setRegionCh(String regionCh) {
		this.regionCh = regionCh;
	}

	@Column(name = "region_ch", nullable = true, length = 255)
	public String getRegionCh() {
		return regionCh;
	}

	public void setDrugstoreAttribute(String drugstoreAttribute) {
		this.drugstoreAttribute = drugstoreAttribute;
	}

	@Column(name = "drugstore_attribute", nullable = true, length = 255)
	public String getDrugstoreAttribute() {
		return drugstoreAttribute;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
