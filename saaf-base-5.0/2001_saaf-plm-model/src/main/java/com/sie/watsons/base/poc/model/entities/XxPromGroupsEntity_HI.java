package com.sie.watsons.base.poc.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * XxPromGroupsEntity_HI Entity Object
 * Wed Aug 14 12:12:45 CST 2019  Auto Generate
 */
@Entity
@Table(name="xx_prom_groups")
public class XxPromGroupsEntity_HI {
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

	@Column(name="alc_first_alloc", nullable=true, length=22)
	public java.math.BigDecimal getAlcFirstAlloc() {
		return alcFirstAlloc;
	}

	public void setAlcRefItem(String alcRefItem) {
		this.alcRefItem = alcRefItem;
	}

	@Column(name="alc_ref_item", nullable=true, length=25)
	public String getAlcRefItem() {
		return alcRefItem;
	}

	public void setAlcSalesHistFrom(Date alcSalesHistFrom) {
		this.alcSalesHistFrom = alcSalesHistFrom;
	}

	@Column(name="alc_sales_hist_from", nullable=true, length=7)
	public Date getAlcSalesHistFrom() {
		return alcSalesHistFrom;
	}

	public void setAlcSalesHistTo(Date alcSalesHistTo) {
		this.alcSalesHistTo = alcSalesHistTo;
	}

	@Column(name="alc_sales_hist_to", nullable=true, length=7)
	public Date getAlcSalesHistTo() {
		return alcSalesHistTo;
	}

	public void setAlcType(String alcType) {
		this.alcType = alcType;
	}

	@Column(name="alc_type", nullable=true, length=6)
	public String getAlcType() {
		return alcType;
	}

	public void setAlcSourceWh(java.math.BigDecimal alcSourceWh) {
		this.alcSourceWh = alcSourceWh;
	}

	@Column(name="alc_source_wh", nullable=true, length=22)
	public java.math.BigDecimal getAlcSourceWh() {
		return alcSourceWh;
	}

	public void setSupplier(java.math.BigDecimal supplier) {
		this.supplier = supplier;
	}

	@Column(name="supplier", nullable=true, length=22)
	public java.math.BigDecimal getSupplier() {
		return supplier;
	}

	public void setEopAction(String eopAction) {
		this.eopAction = eopAction;
	}

	@Column(name="eop_action", nullable=true, length=2)
	public String getEopAction() {
		return eopAction;
	}

	public void setMarginAdjBasis(String marginAdjBasis) {
		this.marginAdjBasis = marginAdjBasis;
	}

	@Column(name="margin_adj_basis", nullable=true, length=1)
	public String getMarginAdjBasis() {
		return marginAdjBasis;
	}

	public void setSimpleChangeType(String simpleChangeType) {
		this.simpleChangeType = simpleChangeType;
	}

	@Column(name="simple_change_type", nullable=true, length=2)
	public String getSimpleChangeType() {
		return simpleChangeType;
	}

	public void setSimpleChangeAmt(java.math.BigDecimal simpleChangeAmt) {
		this.simpleChangeAmt = simpleChangeAmt;
	}

	@Column(name="simple_change_amt", nullable=true, length=22)
	public java.math.BigDecimal getSimpleChangeAmt() {
		return simpleChangeAmt;
	}

	public void setPriceChangeId(java.math.BigDecimal priceChangeId) {
		this.priceChangeId = priceChangeId;
	}

	@Column(name="price_change_id", nullable=true, length=22)
	public java.math.BigDecimal getPriceChangeId() {
		return priceChangeId;
	}

	public void setCostChangeId(java.math.BigDecimal costChangeId) {
		this.costChangeId = costChangeId;
	}

	@Column(name="cost_change_id", nullable=true, length=22)
	public java.math.BigDecimal getCostChangeId() {
		return costChangeId;
	}

	public void setDealId(java.math.BigDecimal dealId) {
		this.dealId = dealId;
	}

	@Column(name="deal_id", nullable=true, length=22)
	public java.math.BigDecimal getDealId() {
		return dealId;
	}

	public void setCogsAdjId(java.math.BigDecimal cogsAdjId) {
		this.cogsAdjId = cogsAdjId;
	}

	@Column(name="cogs_adj_id", nullable=true, length=22)
	public java.math.BigDecimal getCogsAdjId() {
		return cogsAdjId;
	}

	public void setAllocNo(java.math.BigDecimal allocNo) {
		this.allocNo = allocNo;
	}

	@Column(name="alloc_no", nullable=true, length=22)
	public java.math.BigDecimal getAllocNo() {
		return allocNo;
	}

	public void setAllocId(java.math.BigDecimal allocId) {
		this.allocId = allocId;
	}

	@Column(name="alloc_id", nullable=true, length=22)
	public java.math.BigDecimal getAllocId() {
		return allocId;
	}

	public void setForecastingIndex(String forecastingIndex) {
		this.forecastingIndex = forecastingIndex;
	}

	@Column(name="forecasting_index", nullable=true, length=1)
	public String getForecastingIndex() {
		return forecastingIndex;
	}

	public void setReturnWh(java.math.BigDecimal returnWh) {
		this.returnWh = returnWh;
	}

	@Column(name="return_wh", nullable=true, length=22)
	public java.math.BigDecimal getReturnWh() {
		return returnWh;
	}

	public void setBrrId(java.math.BigDecimal brrId) {
		this.brrId = brrId;
	}

	@Column(name="brr_id", nullable=true, length=22)
	public java.math.BigDecimal getBrrId() {
		return brrId;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=100)
	public String getRemarks() {
		return remarks;
	}

	public void setOrgRetail(java.math.BigDecimal orgRetail) {
		this.orgRetail = orgRetail;
	}

	@Column(name="org_retail", nullable=true, length=22)
	public java.math.BigDecimal getOrgRetail() {
		return orgRetail;
	}

	public void setCostChangeRevId(java.math.BigDecimal costChangeRevId) {
		this.costChangeRevId = costChangeRevId;
	}

	@Column(name="cost_change_rev_id", nullable=true, length=22)
	public java.math.BigDecimal getCostChangeRevId() {
		return costChangeRevId;
	}

	public void setPriceChangeRevId(java.math.BigDecimal priceChangeRevId) {
		this.priceChangeRevId = priceChangeRevId;
	}

	@Column(name="price_change_rev_id", nullable=true, length=22)
	public java.math.BigDecimal getPriceChangeRevId() {
		return priceChangeRevId;
	}

	public void setXxPromId(java.math.BigDecimal xxPromId) {
		this.xxPromId = xxPromId;
	}

	@Id
	@Column(name="xx_prom_id", nullable=false, length=22)
	public java.math.BigDecimal getXxPromId() {
		return xxPromId;
	}

	public void setXxGroupId(String xxGroupId) {
		this.xxGroupId = xxGroupId;
	}

	@Column(name="xx_group_id", nullable=false, length=6)
	public String getXxGroupId() {
		return xxGroupId;
	}

	public void setXxItemType(String xxItemType) {
		this.xxItemType = xxItemType;
	}

	@Column(name="xx_item_type", nullable=false, length=6)
	public String getXxItemType() {
		return xxItemType;
	}

	public void setDept(java.math.BigDecimal dept) {
		this.dept = dept;
	}

	@Column(name="dept", nullable=true, length=22)
	public java.math.BigDecimal getDept() {
		return dept;
	}

	public void setMidClass(java.math.BigDecimal midClass) {
		this.midClass = midClass;
	}

	@Column(name="class", nullable=true, length=22)
	public java.math.BigDecimal getMidClass() {
		return midClass;
	}

	public void setSubclass(java.math.BigDecimal subclass) {
		this.subclass = subclass;
	}

	@Column(name="subclass", nullable=true, length=22)
	public java.math.BigDecimal getSubclass() {
		return subclass;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name="item", nullable=true, length=25)
	public String getItem() {
		return item;
	}

	public void setIncludeInd(String includeInd) {
		this.includeInd = includeInd;
	}

	@Column(name="include_ind", nullable=false, length=6)
	public String getIncludeInd() {
		return includeInd;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=1)
	public String getStatus() {
		return status;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name="create_id", nullable=false, length=40)
	public String getCreateId() {
		return createId;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name="create_datetime", nullable=false, length=7)
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	@Column(name="extract_status", nullable=false, length=1)
	public String getExtractStatus() {
		return extractStatus;
	}

	public void setExtractDatetime(Date extractDatetime) {
		this.extractDatetime = extractDatetime;
	}

	@Column(name="extract_datetime", nullable=true, length=7)
	public Date getExtractDatetime() {
		return extractDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	@Column(name="last_update_id", nullable=false, length=40)
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Column(name="last_update_datetime", nullable=false, length=7)
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setTargetMsgDisplay(String targetMsgDisplay) {
		this.targetMsgDisplay = targetMsgDisplay;
	}

	@Column(name="target_msg_display", nullable=true, length=2)
	public String getTargetMsgDisplay() {
		return targetMsgDisplay;
	}

	public void setTargetMsgId(java.math.BigDecimal targetMsgId) {
		this.targetMsgId = targetMsgId;
	}

	@Column(name="target_msg_id", nullable=true, length=22)
	public java.math.BigDecimal getTargetMsgId() {
		return targetMsgId;
	}

	public void setTargetMsgTiming(String targetMsgTiming) {
		this.targetMsgTiming = targetMsgTiming;
	}

	@Column(name="target_msg_timing", nullable=true, length=2)
	public String getTargetMsgTiming() {
		return targetMsgTiming;
	}

	public void setRetailPrice(java.math.BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Column(name="retail_price", nullable=true, length=22)
	public java.math.BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setMarginAdjValue(java.math.BigDecimal marginAdjValue) {
		this.marginAdjValue = marginAdjValue;
	}

	@Column(name="margin_adj_value", nullable=true, length=22)
	public java.math.BigDecimal getMarginAdjValue() {
		return marginAdjValue;
	}

	public void setUnitCost(java.math.BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	@Column(name="unit_cost", nullable=true, length=22)
	public java.math.BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setCostStartDate(Date costStartDate) {
		this.costStartDate = costStartDate;
	}

	@Column(name="cost_start_date", nullable=true, length=7)
	public Date getCostStartDate() {
		return costStartDate;
	}

	public void setCostEndDate(Date costEndDate) {
		this.costEndDate = costEndDate;
	}

	@Column(name="cost_end_date", nullable=true, length=7)
	public Date getCostEndDate() {
		return costEndDate;
	}

	public void setDeal(java.math.BigDecimal deal) {
		this.deal = deal;
	}

	@Column(name="deal", nullable=true, length=22)
	public java.math.BigDecimal getDeal() {
		return deal;
	}

	public void setDealStartDate(Date dealStartDate) {
		this.dealStartDate = dealStartDate;
	}

	@Column(name="deal_start_date", nullable=true, length=7)
	public Date getDealStartDate() {
		return dealStartDate;
	}

	public void setDealEndDate(Date dealEndDate) {
		this.dealEndDate = dealEndDate;
	}

	@Column(name="deal_end_date", nullable=true, length=7)
	public Date getDealEndDate() {
		return dealEndDate;
	}

	public void setOrgCost(java.math.BigDecimal orgCost) {
		this.orgCost = orgCost;
	}

	@Column(name="org_cost", nullable=true, length=22)
	public java.math.BigDecimal getOrgCost() {
		return orgCost;
	}

	public void setAlcTargetQty(java.math.BigDecimal alcTargetQty) {
		this.alcTargetQty = alcTargetQty;
	}

	@Column(name="alc_target_qty", nullable=true, length=22)
	public java.math.BigDecimal getAlcTargetQty() {
		return alcTargetQty;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
