package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductConsaleinfoEntity_HI_RO Entity Object Fri Mar 27 12:26:24 CST 2020
 * Auto Generate
 */

public class PlmProductConsaleinfoEntity_HI_RO {
	private Integer id;
	private String requestId;
	private String whConsignDesc;
	private String currencyCode;
	private String exchangeRate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private String adjBasis;
	private String adjValue;
	private String supplier;
	private String locType;
	private String loc;
	private String consimentType;
	private String consignmentRate;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private Integer storeId;

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setWhConsignDesc(String whConsignDesc) {
		this.whConsignDesc = whConsignDesc;
	}

	public String getWhConsignDesc() {
		return whConsignDesc;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setAdjBasis(String adjBasis) {
		this.adjBasis = adjBasis;
	}

	public String getAdjBasis() {
		return adjBasis;
	}

	public void setAdjValue(String adjValue) {
		this.adjValue = adjValue;
	}

	public String getAdjValue() {
		return adjValue;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setLocType(String locType) {
		this.locType = locType;
	}

	public String getLocType() {
		return locType;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getLoc() {
		return loc;
	}

	public void setConsimentType(String consimentType) {
		this.consimentType = consimentType;
	}

	public String getConsimentType() {
		return consimentType;
	}

	public void setConsignmentRate(String consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	public String getConsignmentRate() {
		return consignmentRate;
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
}
