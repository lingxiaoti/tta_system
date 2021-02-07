package com.sie.watsons.base.productEco.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductPriceEcoEntity_HI_RO Entity Object
 * Fri May 22 14:29:48 CST 2020  Auto Generate
 */

public class PlmProductPriceEcoEntity_HI_RO {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
    private Integer priceId;
    private String priceGroup;
    private String priceArea;
    private String unitPrice;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String flags;
    private String updateSeson;
    private String seson;
    private String updateType;
    private Integer storeId;
    private String groupId;
    private String areaId;
    private String mainPrice;
    private String isUpdate;
    private String priceGroupCode;
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

	public void setFlags(String flags) {
		this.flags = flags;
	}

	
	public String getFlags() {
		return flags;
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

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	
	public String getUpdateType() {
		return updateType;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	
	public Integer getStoreId() {
		return storeId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	
	public String getGroupId() {
		return groupId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	
	public String getAreaId() {
		return areaId;
	}

	public void setMainPrice(String mainPrice) {
		this.mainPrice = mainPrice;
	}

	
	public String getMainPrice() {
		return mainPrice;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setPriceGroupCode(String priceGroupCode) {
		this.priceGroupCode = priceGroupCode;
	}

	
	public String getPriceGroupCode() {
		return priceGroupCode;
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
