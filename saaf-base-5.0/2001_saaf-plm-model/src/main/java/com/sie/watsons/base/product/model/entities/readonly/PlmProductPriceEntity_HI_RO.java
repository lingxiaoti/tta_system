package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductPriceEntity_HI_RO Entity Object Thu Aug 29 10:51:51 CST 2019 Auto
 * Generate
 */

public class PlmProductPriceEntity_HI_RO {
	private Integer priceId;
	private String priceGroup;
	private String priceArea;
	private String unitPrice;
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

	private String updateSeson; // 修改原因
	private String Seson;// 原因
	private String updateType; // 修改类型
	private Integer storeId;

	private String groupId; // 分组id
	private String areaId; // 区域id
	private String mainPrice;

	private String isUpdate; // 是否在修改中 默认为0
	private String priceGroupCode;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updatePriceDate; // 修改价格时间

	public Date getUpdatePriceDate() {
		return updatePriceDate;
	}

	public void setUpdatePriceDate(Date updatePriceDate) {
		this.updatePriceDate = updatePriceDate;
	}

	public String getPriceGroupCode() {
		return priceGroupCode;
	}

	public void setPriceGroupCode(String priceGroupCode) {
		this.priceGroupCode = priceGroupCode;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getMainPrice() {
		return mainPrice;
	}

	public void setMainPrice(String mainPrice) {
		this.mainPrice = mainPrice;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	public Integer getPriceId() {
		return priceId;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceArea(String priceArea) {
		this.priceArea = priceArea;
	}

	public String getPriceArea() {
		return priceArea;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getUnitPrice() {
		return unitPrice;
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

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

}
