package com.sie.watsons.base.productEco.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductSupplierInfoEcoEntity_HI Entity Object
 * Fri May 22 14:29:00 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_SUPPLIER_INFO_ECO")
public class PlmProductSupplierInfoEcoEntity_HI {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
    private Integer id;
    private String supplierCode;
    private String supplierName;
    private String rateCode;
    private String currencyCost;
    private String price;
    private String rate;
    private String condition;
    private String productReturn;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String supplierId;
    private String isSubmit;
    private String flags;
    private Integer weight;
    private Integer boxLength;
    private Integer boxBreadth;
    private Integer boxHeight;
    private Integer productLength;
    private Integer productHeight;
    private Integer productBreadth;
    private Integer innerpackageSpe;
    private Integer packageSpe;
    private String place;
    private String productively;
    private Integer boxWeight;
    private String isMainsupplier;
    private String returnReson;
    private String returnStatus;
    private String updateSeson;
    private String seson;
    private String sxWay;
    private String sxWarehouse;
    private String sxStore;
    private String placeNote;
    private String area;
    private String placeCode;
    private String rmsCode;
    private String countryCode;
    private String roundToInnerPct;
    private String roundToCasePct;
    private String roundToLayerPct;
    private String roundToPalletPct;
    private String hi;
    private String ti;
    private String groupId;
    private String groupName;
    private String areaId;
    private String sxStoreId;
    private String sxWarehouseId;
    private String isUpdate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatePriceDate;
    private Integer operatorUserId;

	public void setEcoId(Integer ecoId) {
		this.ecoId = ecoId;
	}

	@Column(name="eco_id", nullable=false, length=22)	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_LINE", sequenceName = "SEQ_PLM_PRODUCT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="line_id", nullable=false, length=22)	
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

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=150)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=100)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	@Column(name="rate_code", nullable=true, length=255)	
	public String getRateCode() {
		return rateCode;
	}

	public void setCurrencyCost(String currencyCost) {
		this.currencyCost = currencyCost;
	}

	@Column(name="currency_cost", nullable=true, length=50)	
	public String getCurrencyCost() {
		return currencyCost;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name="price", nullable=true, length=100)	
	public String getPrice() {
		return price;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column(name="rate", nullable=true, length=100)	
	public String getRate() {
		return rate;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name="condition", nullable=true, length=255)	
	public String getCondition() {
		return condition;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	@Column(name="product_return", nullable=true, length=50)	
	public String getProductReturn() {
		return productReturn;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name="product_head_id", nullable=true, length=22)	
	public Integer getProductHeadId() {
		return productHeadId;
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

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=100)	
	public String getSupplierId() {
		return supplierId;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	@Column(name="is_submit", nullable=true, length=100)	
	public String getIsSubmit() {
		return isSubmit;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@Column(name="flags", nullable=true, length=255)	
	public String getFlags() {
		return flags;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name="weight", nullable=true, length=22)	
	public Integer getWeight() {
		return weight;
	}

	public void setBoxLength(Integer boxLength) {
		this.boxLength = boxLength;
	}

	@Column(name="box_length", nullable=true, length=22)	
	public Integer getBoxLength() {
		return boxLength;
	}

	public void setBoxBreadth(Integer boxBreadth) {
		this.boxBreadth = boxBreadth;
	}

	@Column(name="box_breadth", nullable=true, length=22)	
	public Integer getBoxBreadth() {
		return boxBreadth;
	}

	public void setBoxHeight(Integer boxHeight) {
		this.boxHeight = boxHeight;
	}

	@Column(name="box_height", nullable=true, length=22)	
	public Integer getBoxHeight() {
		return boxHeight;
	}

	public void setProductLength(Integer productLength) {
		this.productLength = productLength;
	}

	@Column(name="product_length", nullable=true, length=22)	
	public Integer getProductLength() {
		return productLength;
	}

	public void setProductHeight(Integer productHeight) {
		this.productHeight = productHeight;
	}

	@Column(name="product_height", nullable=true, length=22)	
	public Integer getProductHeight() {
		return productHeight;
	}

	public void setProductBreadth(Integer productBreadth) {
		this.productBreadth = productBreadth;
	}

	@Column(name="product_breadth", nullable=true, length=22)	
	public Integer getProductBreadth() {
		return productBreadth;
	}

	public void setInnerpackageSpe(Integer innerpackageSpe) {
		this.innerpackageSpe = innerpackageSpe;
	}

	@Column(name="innerpackage_spe", nullable=true, length=22)	
	public Integer getInnerpackageSpe() {
		return innerpackageSpe;
	}

	public void setPackageSpe(Integer packageSpe) {
		this.packageSpe = packageSpe;
	}

	@Column(name="package_spe", nullable=true, length=22)	
	public Integer getPackageSpe() {
		return packageSpe;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name="place", nullable=true, length=255)	
	public String getPlace() {
		return place;
	}

	public void setProductively(String productively) {
		this.productively = productively;
	}

	@Column(name="productively", nullable=true, length=255)	
	public String getProductively() {
		return productively;
	}

	public void setBoxWeight(Integer boxWeight) {
		this.boxWeight = boxWeight;
	}

	@Column(name="box_weight", nullable=true, length=22)	
	public Integer getBoxWeight() {
		return boxWeight;
	}

	public void setIsMainsupplier(String isMainsupplier) {
		this.isMainsupplier = isMainsupplier;
	}

	@Column(name="is_mainsupplier", nullable=true, length=255)	
	public String getIsMainsupplier() {
		return isMainsupplier;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	@Column(name="return_reson", nullable=true, length=2000)	
	public String getReturnReson() {
		return returnReson;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	@Column(name="return_status", nullable=true, length=255)	
	public String getReturnStatus() {
		return returnStatus;
	}

	public void setUpdateSeson(String updateSeson) {
		this.updateSeson = updateSeson;
	}

	@Column(name="update_seson", nullable=true, length=255)	
	public String getUpdateSeson() {
		return updateSeson;
	}

	public void setSeson(String seson) {
		this.seson = seson;
	}

	@Column(name="seson", nullable=true, length=255)	
	public String getSeson() {
		return seson;
	}

	public void setSxWay(String sxWay) {
		this.sxWay = sxWay;
	}

	@Column(name="sx_way", nullable=true, length=255)	
	public String getSxWay() {
		return sxWay;
	}

	public void setSxWarehouse(String sxWarehouse) {
		this.sxWarehouse = sxWarehouse;
	}

	@Column(name="sx_warehouse", nullable=true, length=255)	
	public String getSxWarehouse() {
		return sxWarehouse;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	@Column(name="sx_store", nullable=true, length=3000)	
	public String getSxStore() {
		return sxStore;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	@Column(name="place_note", nullable=true, length=255)	
	public String getPlaceNote() {
		return placeNote;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name="area", nullable=true, length=255)	
	public String getArea() {
		return area;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	@Column(name="place_code", nullable=true, length=255)	
	public String getPlaceCode() {
		return placeCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name="rms_code", nullable=true, length=255)	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name="country_code", nullable=true, length=255)	
	public String getCountryCode() {
		return countryCode;
	}

	public void setRoundToInnerPct(String roundToInnerPct) {
		this.roundToInnerPct = roundToInnerPct;
	}

	@Column(name="round_to_inner_pct", nullable=true, length=255)	
	public String getRoundToInnerPct() {
		return roundToInnerPct;
	}

	public void setRoundToCasePct(String roundToCasePct) {
		this.roundToCasePct = roundToCasePct;
	}

	@Column(name="round_to_case_pct", nullable=true, length=255)	
	public String getRoundToCasePct() {
		return roundToCasePct;
	}

	public void setRoundToLayerPct(String roundToLayerPct) {
		this.roundToLayerPct = roundToLayerPct;
	}

	@Column(name="round_to_layer_pct", nullable=true, length=255)	
	public String getRoundToLayerPct() {
		return roundToLayerPct;
	}

	public void setRoundToPalletPct(String roundToPalletPct) {
		this.roundToPalletPct = roundToPalletPct;
	}

	@Column(name="round_to_pallet_pct", nullable=true, length=255)	
	public String getRoundToPalletPct() {
		return roundToPalletPct;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}

	@Column(name="hi", nullable=true, length=255)	
	public String getHi() {
		return hi;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	@Column(name="ti", nullable=true, length=255)	
	public String getTi() {
		return ti;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name="group_id", nullable=true, length=255)	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=255)	
	public String getGroupName() {
		return groupName;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name="area_id", nullable=true, length=255)	
	public String getAreaId() {
		return areaId;
	}

	public void setSxStoreId(String sxStoreId) {
		this.sxStoreId = sxStoreId;
	}

	@Column(name="sx_store_id", nullable=true, length=3000)	
	public String getSxStoreId() {
		return sxStoreId;
	}

	public void setSxWarehouseId(String sxWarehouseId) {
		this.sxWarehouseId = sxWarehouseId;
	}

	@Column(name="sx_warehouse_id", nullable=true, length=1000)	
	public String getSxWarehouseId() {
		return sxWarehouseId;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name="is_update", nullable=true, length=255)	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setUpdatePriceDate(Date updatePriceDate) {
		this.updatePriceDate = updatePriceDate;
	}

	@Column(name="update_price_date", nullable=true, length=7)	
	public Date getUpdatePriceDate() {
		return updatePriceDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
