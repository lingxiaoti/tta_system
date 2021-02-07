package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductSupplierInfoEntity_HI_RO Entity Object Thu Aug 29 10:51:53 CST 2019
 * Auto Generate
 */

public class PlmProductSupplierInfoEntity_HI_RO {
	public static final String QUERY_STORE = "select * from PLM_PRODUCT_SUPPLIER_INFO info left join \r\n"
			+ "PLM_PRODUCT_STORE sto on (INFO.PRODUCT_HEAD_ID=STO.PRODUCT_HEAD_ID and \r\n"
			+ "INFO.SUPPLIER_ID=STO.SUPPLIER_ID) where info.PRODUCT_HEAD_ID=:productHeadId ";
	private Integer id;
	private String supplierId;
	private String supplierCode;
	private String supplierName;
	private String isMainsupplier;

	private String rateCode;
	private String currencyCost;
	private String price;
	private String rate;
	private String condition;
	private String productReturn;
	private Integer productHeadId;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

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
	private String updateSeson; // 修改成本原因 快码
	private String Seson;// 原因
	private Integer storeId;

	private String groupId; // 资质组id

	private String groupName; // 资质组名称

	// private String isUpdate; // 是否在修改中 //默认为0

	private String areaId; // 区域Id

	private String sxWarehouseId;
	private String sxStoreId;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updatePriceDate; // 修改价格时间

	public Date getUpdatePriceDate() {
		return updatePriceDate;
	}

	public void setUpdatePriceDate(Date updatePriceDate) {
		this.updatePriceDate = updatePriceDate;
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	// public String getIsUpdate() {
	// return isUpdate;
	// }
	//
	// public void setIsUpdate(String isUpdate) {
	// this.isUpdate = isUpdate;
	// }

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private String sxWay;
	private String sxWarehouse;
	private String sxStore;
	private String placeNote;
	private String area;
	private String returnReson;
	private String rmsCode;

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getBoxLength() {
		return boxLength;
	}

	public void setBoxLength(Integer boxLength) {
		this.boxLength = boxLength;
	}

	public Integer getBoxBreadth() {
		return boxBreadth;
	}

	public void setBoxBreadth(Integer boxBreadth) {
		this.boxBreadth = boxBreadth;
	}

	public Integer getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(Integer boxHeight) {
		this.boxHeight = boxHeight;
	}

	public Integer getProductLength() {
		return productLength;
	}

	public void setProductLength(Integer productLength) {
		this.productLength = productLength;
	}

	public Integer getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(Integer productHeight) {
		this.productHeight = productHeight;
	}

	public Integer getProductBreadth() {
		return productBreadth;
	}

	public void setProductBreadth(Integer productBreadth) {
		this.productBreadth = productBreadth;
	}

	public Integer getInnerpackageSpe() {
		return innerpackageSpe;
	}

	public void setInnerpackageSpe(Integer innerpackageSpe) {
		this.innerpackageSpe = innerpackageSpe;
	}

	public Integer getPackageSpe() {
		return packageSpe;
	}

	public void setPackageSpe(Integer packageSpe) {
		this.packageSpe = packageSpe;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getProductively() {
		return productively;
	}

	public void setProductively(String productively) {
		this.productively = productively;
	}

	public Integer getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(Integer boxWeight) {
		this.boxWeight = boxWeight;
	}

	public String getIsMainsupplier() {
		return isMainsupplier;
	}

	public void setIsMainsupplier(String isMainsupplier) {
		this.isMainsupplier = isMainsupplier;
	}

	public String getUpdateSeson() {
		return updateSeson;
	}

	public void setUpdateSeson(String updateSeson) {
		this.updateSeson = updateSeson;
	}

	public String getSeson() {
		return Seson;
	}

	public void setSeson(String seson) {
		Seson = seson;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
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

	public String getSxWay() {
		return sxWay;
	}

	public void setSxWay(String sxWay) {
		this.sxWay = sxWay;
	}

	public String getSxWarehouse() {
		return sxWarehouse;
	}

	public void setSxWarehouse(String sxWarehouse) {
		this.sxWarehouse = sxWarehouse;
	}

	public String getSxStore() {
		return sxStore;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	public String getPlaceNote() {
		return placeNote;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getReturnReson() {
		return returnReson;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

}
