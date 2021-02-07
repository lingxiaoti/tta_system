package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductStoreEntity_HI_RO Entity Object Tue Nov 12 15:01:21 CST 2019 Auto
 * Generate
 */

public class PlmProductStoreEntity_HI_RO {

	private Integer storeId;
	private String sxWay;
	private String sxWarehouse;
	private String sxStore;
	private String placeNote;
	private String area;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String supplierId;
	private Integer productHeadId;
	@JSONField(format = "yyyy-MM-dd")
	private Date startDate;
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate;

	private String substituteType;

	private String substitutePropetion;
	private String placeCode;

	private String areaId; // 区域Id

	private String sxWarehouseId;
	private String sxStoreId;

	private String currencyCost;
	private Float exRate;

	public String getCurrencyCost() {
		return currencyCost;
	}

	public void setCurrencyCost(String currencyCost) {
		this.currencyCost = currencyCost;
	}

	public Float getExRate() {
		return exRate;
	}

	public void setExRate(Float exRate) {
		this.exRate = exRate;
	}

	public String getSxWarehouseId() {
		return sxWarehouseId;
	}

	public void setSxWarehouseId(String sxWarehouseId) {
		this.sxWarehouseId = sxWarehouseId;
	}

	public String getSxStoreId() {
		return sxStoreId;
	}

	public void setSxStoreId(String sxStoreId) {
		this.sxStoreId = sxStoreId;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
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

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSubstituteType() {
		return substituteType;
	}

	public void setSubstituteType(String substituteType) {
		this.substituteType = substituteType;
	}

	public String getSubstitutePropetion() {
		return substitutePropetion;
	}

	public void setSubstitutePropetion(String substitutePropetion) {
		this.substitutePropetion = substitutePropetion;
	}

}
