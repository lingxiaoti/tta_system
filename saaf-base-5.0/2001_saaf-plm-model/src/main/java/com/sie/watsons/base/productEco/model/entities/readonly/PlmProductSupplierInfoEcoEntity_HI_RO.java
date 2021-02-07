package com.sie.watsons.base.productEco.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductSupplierInfoEcoEntity_HI_RO Entity Object
 * Fri May 22 14:29:00 CST 2020  Auto Generate
 */

public class PlmProductSupplierInfoEcoEntity_HI_RO {
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

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	
	public String getRateCode() {
		return rateCode;
	}

	public void setCurrencyCost(String currencyCost) {
		this.currencyCost = currencyCost;
	}

	
	public String getCurrencyCost() {
		return currencyCost;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	public String getPrice() {
		return price;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	
	public String getRate() {
		return rate;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	
	public String getCondition() {
		return condition;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	
	public String getProductReturn() {
		return productReturn;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	
	public Integer getProductHeadId() {
		return productHeadId;
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

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	
	public String getSupplierId() {
		return supplierId;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	
	public String getIsSubmit() {
		return isSubmit;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	
	public String getFlags() {
		return flags;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	
	public Integer getWeight() {
		return weight;
	}

	public void setBoxLength(Integer boxLength) {
		this.boxLength = boxLength;
	}

	
	public Integer getBoxLength() {
		return boxLength;
	}

	public void setBoxBreadth(Integer boxBreadth) {
		this.boxBreadth = boxBreadth;
	}

	
	public Integer getBoxBreadth() {
		return boxBreadth;
	}

	public void setBoxHeight(Integer boxHeight) {
		this.boxHeight = boxHeight;
	}

	
	public Integer getBoxHeight() {
		return boxHeight;
	}

	public void setProductLength(Integer productLength) {
		this.productLength = productLength;
	}

	
	public Integer getProductLength() {
		return productLength;
	}

	public void setProductHeight(Integer productHeight) {
		this.productHeight = productHeight;
	}

	
	public Integer getProductHeight() {
		return productHeight;
	}

	public void setProductBreadth(Integer productBreadth) {
		this.productBreadth = productBreadth;
	}

	
	public Integer getProductBreadth() {
		return productBreadth;
	}

	public void setInnerpackageSpe(Integer innerpackageSpe) {
		this.innerpackageSpe = innerpackageSpe;
	}

	
	public Integer getInnerpackageSpe() {
		return innerpackageSpe;
	}

	public void setPackageSpe(Integer packageSpe) {
		this.packageSpe = packageSpe;
	}

	
	public Integer getPackageSpe() {
		return packageSpe;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	
	public String getPlace() {
		return place;
	}

	public void setProductively(String productively) {
		this.productively = productively;
	}

	
	public String getProductively() {
		return productively;
	}

	public void setBoxWeight(Integer boxWeight) {
		this.boxWeight = boxWeight;
	}

	
	public Integer getBoxWeight() {
		return boxWeight;
	}

	public void setIsMainsupplier(String isMainsupplier) {
		this.isMainsupplier = isMainsupplier;
	}

	
	public String getIsMainsupplier() {
		return isMainsupplier;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	
	public String getReturnReson() {
		return returnReson;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	
	public String getReturnStatus() {
		return returnStatus;
	}

	public void setUpdateSeson(String updateSeson) {
		this.updateSeson = updateSeson;
	}

	
	public String getUpdateSeson() {
		return updateSeson;
	}

	public void setSeson(String seson) {
		this.seson = seson;
	}

	
	public String getSeson() {
		return seson;
	}

	public void setSxWay(String sxWay) {
		this.sxWay = sxWay;
	}

	
	public String getSxWay() {
		return sxWay;
	}

	public void setSxWarehouse(String sxWarehouse) {
		this.sxWarehouse = sxWarehouse;
	}

	
	public String getSxWarehouse() {
		return sxWarehouse;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	
	public String getSxStore() {
		return sxStore;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	
	public String getPlaceNote() {
		return placeNote;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
	public String getArea() {
		return area;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	
	public String getPlaceCode() {
		return placeCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	
	public String getCountryCode() {
		return countryCode;
	}

	public void setRoundToInnerPct(String roundToInnerPct) {
		this.roundToInnerPct = roundToInnerPct;
	}

	
	public String getRoundToInnerPct() {
		return roundToInnerPct;
	}

	public void setRoundToCasePct(String roundToCasePct) {
		this.roundToCasePct = roundToCasePct;
	}

	
	public String getRoundToCasePct() {
		return roundToCasePct;
	}

	public void setRoundToLayerPct(String roundToLayerPct) {
		this.roundToLayerPct = roundToLayerPct;
	}

	
	public String getRoundToLayerPct() {
		return roundToLayerPct;
	}

	public void setRoundToPalletPct(String roundToPalletPct) {
		this.roundToPalletPct = roundToPalletPct;
	}

	
	public String getRoundToPalletPct() {
		return roundToPalletPct;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}

	
	public String getHi() {
		return hi;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	
	public String getTi() {
		return ti;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	
	public String getAreaId() {
		return areaId;
	}

	public void setSxStoreId(String sxStoreId) {
		this.sxStoreId = sxStoreId;
	}

	
	public String getSxStoreId() {
		return sxStoreId;
	}

	public void setSxWarehouseId(String sxWarehouseId) {
		this.sxWarehouseId = sxWarehouseId;
	}

	
	public String getSxWarehouseId() {
		return sxWarehouseId;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setUpdatePriceDate(Date updatePriceDate) {
		this.updatePriceDate = updatePriceDate;
	}

	
	public Date getUpdatePriceDate() {
		return updatePriceDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
