package com.sie.watsons.base.poc.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * XxPromGroupsEntity_HI_RO Entity Object
 * Tue Aug 13 12:21:42 CST 2019  Auto Generate
 */

public class XxPromGroupsEntity_HI_RO {
    private java.math.BigDecimal alcFirstAlloc;
    private String alcRefItem;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date alcSalesHistFrom;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date alcSalesHistTo;
    private String alcType;
    private java.math.BigDecimal alcSourceWh;
    private java.math.BigDecimal supplier;
    private String eopAction;
    private String marginAdjBasis;
    private String simpleChangeType;
    private java.math.BigDecimal simpleChangeAmt;
    private java.math.BigDecimal priceChangeId;
    private java.math.BigDecimal costChangeId;
    private java.math.BigDecimal dealId;
    private java.math.BigDecimal cogsAdjId;
    private java.math.BigDecimal allocNo;
    private java.math.BigDecimal allocId;
    private String forecastingIndex;
    private java.math.BigDecimal returnWh;
    private java.math.BigDecimal brrId;
    private String remarks;
    private java.math.BigDecimal orgRetail;
    private java.math.BigDecimal costChangeRevId;
    private java.math.BigDecimal priceChangeRevId;
    private java.math.BigDecimal xxPromId;
    private String xxGroupId;
    private String xxItemType;
    private java.math.BigDecimal dept;
    private java.math.BigDecimal midClass;
    private java.math.BigDecimal subclass;
    private String item;
    private String includeInd;
    private String status;
    private String createId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDatetime;
    private String extractStatus;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date extractDatetime;
    private String lastUpdateId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String targetMsgDisplay;
    private java.math.BigDecimal targetMsgId;
    private String targetMsgTiming;
    private java.math.BigDecimal retailPrice;
    private java.math.BigDecimal marginAdjValue;
    private java.math.BigDecimal unitCost;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date costStartDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date costEndDate;
    private java.math.BigDecimal deal;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date dealStartDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date dealEndDate;
    private java.math.BigDecimal orgCost;
    private java.math.BigDecimal alcTargetQty;
    private Integer operatorUserId;

	public void setAlcFirstAlloc(java.math.BigDecimal alcFirstAlloc) {
		this.alcFirstAlloc = alcFirstAlloc;
	}

	
	public java.math.BigDecimal getAlcFirstAlloc() {
		return alcFirstAlloc;
	}

	public void setAlcRefItem(String alcRefItem) {
		this.alcRefItem = alcRefItem;
	}

	
	public String getAlcRefItem() {
		return alcRefItem;
	}

	public void setAlcSalesHistFrom(Date alcSalesHistFrom) {
		this.alcSalesHistFrom = alcSalesHistFrom;
	}

	
	public Date getAlcSalesHistFrom() {
		return alcSalesHistFrom;
	}

	public void setAlcSalesHistTo(Date alcSalesHistTo) {
		this.alcSalesHistTo = alcSalesHistTo;
	}

	
	public Date getAlcSalesHistTo() {
		return alcSalesHistTo;
	}

	public void setAlcType(String alcType) {
		this.alcType = alcType;
	}

	
	public String getAlcType() {
		return alcType;
	}

	public void setAlcSourceWh(java.math.BigDecimal alcSourceWh) {
		this.alcSourceWh = alcSourceWh;
	}

	
	public java.math.BigDecimal getAlcSourceWh() {
		return alcSourceWh;
	}

	public void setSupplier(java.math.BigDecimal supplier) {
		this.supplier = supplier;
	}

	
	public java.math.BigDecimal getSupplier() {
		return supplier;
	}

	public void setEopAction(String eopAction) {
		this.eopAction = eopAction;
	}

	
	public String getEopAction() {
		return eopAction;
	}

	public void setMarginAdjBasis(String marginAdjBasis) {
		this.marginAdjBasis = marginAdjBasis;
	}

	
	public String getMarginAdjBasis() {
		return marginAdjBasis;
	}

	public void setSimpleChangeType(String simpleChangeType) {
		this.simpleChangeType = simpleChangeType;
	}

	
	public String getSimpleChangeType() {
		return simpleChangeType;
	}

	public void setSimpleChangeAmt(java.math.BigDecimal simpleChangeAmt) {
		this.simpleChangeAmt = simpleChangeAmt;
	}

	
	public java.math.BigDecimal getSimpleChangeAmt() {
		return simpleChangeAmt;
	}

	public void setPriceChangeId(java.math.BigDecimal priceChangeId) {
		this.priceChangeId = priceChangeId;
	}

	
	public java.math.BigDecimal getPriceChangeId() {
		return priceChangeId;
	}

	public void setCostChangeId(java.math.BigDecimal costChangeId) {
		this.costChangeId = costChangeId;
	}

	
	public java.math.BigDecimal getCostChangeId() {
		return costChangeId;
	}

	public void setDealId(java.math.BigDecimal dealId) {
		this.dealId = dealId;
	}

	
	public java.math.BigDecimal getDealId() {
		return dealId;
	}

	public void setCogsAdjId(java.math.BigDecimal cogsAdjId) {
		this.cogsAdjId = cogsAdjId;
	}

	
	public java.math.BigDecimal getCogsAdjId() {
		return cogsAdjId;
	}

	public void setAllocNo(java.math.BigDecimal allocNo) {
		this.allocNo = allocNo;
	}

	
	public java.math.BigDecimal getAllocNo() {
		return allocNo;
	}

	public void setAllocId(java.math.BigDecimal allocId) {
		this.allocId = allocId;
	}

	
	public java.math.BigDecimal getAllocId() {
		return allocId;
	}

	public void setForecastingIndex(String forecastingIndex) {
		this.forecastingIndex = forecastingIndex;
	}

	
	public String getForecastingIndex() {
		return forecastingIndex;
	}

	public void setReturnWh(java.math.BigDecimal returnWh) {
		this.returnWh = returnWh;
	}

	
	public java.math.BigDecimal getReturnWh() {
		return returnWh;
	}

	public void setBrrId(java.math.BigDecimal brrId) {
		this.brrId = brrId;
	}

	
	public java.math.BigDecimal getBrrId() {
		return brrId;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setOrgRetail(java.math.BigDecimal orgRetail) {
		this.orgRetail = orgRetail;
	}

	
	public java.math.BigDecimal getOrgRetail() {
		return orgRetail;
	}

	public void setCostChangeRevId(java.math.BigDecimal costChangeRevId) {
		this.costChangeRevId = costChangeRevId;
	}

	
	public java.math.BigDecimal getCostChangeRevId() {
		return costChangeRevId;
	}

	public void setPriceChangeRevId(java.math.BigDecimal priceChangeRevId) {
		this.priceChangeRevId = priceChangeRevId;
	}

	
	public java.math.BigDecimal getPriceChangeRevId() {
		return priceChangeRevId;
	}

	public void setXxPromId(java.math.BigDecimal xxPromId) {
		this.xxPromId = xxPromId;
	}

	
	public java.math.BigDecimal getXxPromId() {
		return xxPromId;
	}

	public void setXxGroupId(String xxGroupId) {
		this.xxGroupId = xxGroupId;
	}

	
	public String getXxGroupId() {
		return xxGroupId;
	}

	public void setXxItemType(String xxItemType) {
		this.xxItemType = xxItemType;
	}

	
	public String getXxItemType() {
		return xxItemType;
	}

	public void setDept(java.math.BigDecimal dept) {
		this.dept = dept;
	}

	
	public java.math.BigDecimal getDept() {
		return dept;
	}

	public void setClass(java.math.BigDecimal midClass) {
		this.midClass = midClass;
	}

	
	public java.math.BigDecimal getMidClass() {
		return midClass;
	}

	public void setSubclass(java.math.BigDecimal subclass) {
		this.subclass = subclass;
	}

	
	public java.math.BigDecimal getSubclass() {
		return subclass;
	}

	public void setItem(String item) {
		this.item = item;
	}

	
	public String getItem() {
		return item;
	}

	public void setIncludeInd(String includeInd) {
		this.includeInd = includeInd;
	}

	
	public String getIncludeInd() {
		return includeInd;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	
	public String getCreateId() {
		return createId;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	
	public String getExtractStatus() {
		return extractStatus;
	}

	public void setExtractDatetime(Date extractDatetime) {
		this.extractDatetime = extractDatetime;
	}

	
	public Date getExtractDatetime() {
		return extractDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setTargetMsgDisplay(String targetMsgDisplay) {
		this.targetMsgDisplay = targetMsgDisplay;
	}

	
	public String getTargetMsgDisplay() {
		return targetMsgDisplay;
	}

	public void setTargetMsgId(java.math.BigDecimal targetMsgId) {
		this.targetMsgId = targetMsgId;
	}

	
	public java.math.BigDecimal getTargetMsgId() {
		return targetMsgId;
	}

	public void setTargetMsgTiming(String targetMsgTiming) {
		this.targetMsgTiming = targetMsgTiming;
	}

	
	public String getTargetMsgTiming() {
		return targetMsgTiming;
	}

	public void setRetailPrice(java.math.BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	
	public java.math.BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setMarginAdjValue(java.math.BigDecimal marginAdjValue) {
		this.marginAdjValue = marginAdjValue;
	}

	
	public java.math.BigDecimal getMarginAdjValue() {
		return marginAdjValue;
	}

	public void setUnitCost(java.math.BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	
	public java.math.BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setCostStartDate(Date costStartDate) {
		this.costStartDate = costStartDate;
	}

	
	public Date getCostStartDate() {
		return costStartDate;
	}

	public void setCostEndDate(Date costEndDate) {
		this.costEndDate = costEndDate;
	}

	
	public Date getCostEndDate() {
		return costEndDate;
	}

	public void setDeal(java.math.BigDecimal deal) {
		this.deal = deal;
	}

	
	public java.math.BigDecimal getDeal() {
		return deal;
	}

	public void setDealStartDate(Date dealStartDate) {
		this.dealStartDate = dealStartDate;
	}

	
	public Date getDealStartDate() {
		return dealStartDate;
	}

	public void setDealEndDate(Date dealEndDate) {
		this.dealEndDate = dealEndDate;
	}

	
	public Date getDealEndDate() {
		return dealEndDate;
	}

	public void setOrgCost(java.math.BigDecimal orgCost) {
		this.orgCost = orgCost;
	}

	
	public java.math.BigDecimal getOrgCost() {
		return orgCost;
	}

	public void setAlcTargetQty(java.math.BigDecimal alcTargetQty) {
		this.alcTargetQty = alcTargetQty;
	}

	
	public java.math.BigDecimal getAlcTargetQty() {
		return alcTargetQty;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
