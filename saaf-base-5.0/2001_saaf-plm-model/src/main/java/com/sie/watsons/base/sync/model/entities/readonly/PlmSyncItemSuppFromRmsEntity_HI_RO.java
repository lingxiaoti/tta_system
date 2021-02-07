package com.sie.watsons.base.sync.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * PlmSyncItemSuppFromRmsEntity_HI_RO Entity Object
 * Tue Aug 11 11:03:42 GMT+08:00 2020  Auto Generate
 */

public class PlmSyncItemSuppFromRmsEntity_HI_RO {
    private String roundToInnerPct;
    private String roundToCasePct;
    private String roundToLayerPct;
    private String roundToPalletPct;
    private String hi;
    private String ti;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String lastUpdateId;
    private String processStatus;
    private java.math.BigDecimal syncId;
    private String item;
    private String supplier;
    private String primarySuppInd;
    private String whConsignInd;
    private String originCountryId;
    private String unitCost;
    private String suppPackSize;
    private String innerPackSize;
    private Integer operatorUserId;

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

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	
	public String getProcessStatus() {
		return processStatus;
	}

	public void setSyncId(java.math.BigDecimal syncId) {
		this.syncId = syncId;
	}

	
	public java.math.BigDecimal getSyncId() {
		return syncId;
	}

	public void setItem(String item) {
		this.item = item;
	}

	
	public String getItem() {
		return item;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	
	public String getSupplier() {
		return supplier;
	}

	public void setPrimarySuppInd(String primarySuppInd) {
		this.primarySuppInd = primarySuppInd;
	}

	
	public String getPrimarySuppInd() {
		return primarySuppInd;
	}

	public void setWhConsignInd(String whConsignInd) {
		this.whConsignInd = whConsignInd;
	}

	
	public String getWhConsignInd() {
		return whConsignInd;
	}

	public void setOriginCountryId(String originCountryId) {
		this.originCountryId = originCountryId;
	}

	
	public String getOriginCountryId() {
		return originCountryId;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	
	public String getUnitCost() {
		return unitCost;
	}

	public void setSuppPackSize(String suppPackSize) {
		this.suppPackSize = suppPackSize;
	}

	
	public String getSuppPackSize() {
		return suppPackSize;
	}

	public void setInnerPackSize(String innerPackSize) {
		this.innerPackSize = innerPackSize;
	}

	
	public String getInnerPackSize() {
		return innerPackSize;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
