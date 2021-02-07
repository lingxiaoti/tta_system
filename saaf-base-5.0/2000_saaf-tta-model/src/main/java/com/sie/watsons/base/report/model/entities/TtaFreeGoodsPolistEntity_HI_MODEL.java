package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.report.utils.CustomNullToStringConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaFreeGoodsPolistEntity_HI Entity Object
 * Tue Aug 06 14:47:48 CST 2019  Auto Generate
 */
public class TtaFreeGoodsPolistEntity_HI_MODEL {

	@ExcelIgnore
	private Integer id;

	@ExcelProperty(value ="WEEK")
	private Integer week;

	@ExcelProperty(value ="LOCATION")
	private String location;

	@ExcelProperty(value ="SUPPLIER")
	private String supplier;

	@ExcelProperty(value ="ORDER_NO")
	private String orderNo;

	@ExcelProperty(value ="ITEM")
	private String item;

	@ExcelProperty(value ="SUPP_PACK_SIZE")
	private String suppPackSize;

	@ExcelProperty(value ="WRITTEN_DATE")
	private String writtenDate;

	@ExcelProperty(value ="CLOSE_DATE")
	private String closeDate;

	@ExcelProperty(value ="NOT_AFTER_DATE")
	private String notAfterDate;

	@ExcelProperty(value ="UNIT_COST")
	private String unitCost;

	@ExcelProperty(value ="QTY_ORDERED")
	private String qtyOrdered;

	@ExcelProperty(value ="QTY_RECEIVED")
	private String qtyReceived;

	@ExcelProperty(value ="VUL_ORDERED")
	private String vulOrdered;

	@ExcelProperty(value ="VUL_RECEIVED")
	private String vulReceived;

	@ExcelProperty(value ="COMMENTS")
	private String comments;

	@ExcelProperty(value ="SUP_NAME")
	private String supName;

	@ExcelProperty(value ="ORDER_TYPE")
	private String orderType;

	@ExcelProperty(value ="PACKAGE_ID",converter = CustomNullToStringConverter.class)
	private String packageId;

	@ExcelProperty(value ="PROM_NUMBER",converter = CustomNullToStringConverter.class)
	private String promNumber;

	@ExcelProperty(value ="UNIT_COST_INIT")
	private String unitCostInit;

	@ExcelIgnore
	private Date creationDate;

	@ExcelIgnore
	private Integer createdBy;

	@ExcelIgnore
	private Integer lastUpdatedBy;

	@ExcelIgnore
	private Date lastUpdateDate;

	@ExcelIgnore
	private Integer lastUpdateLogin;

	@ExcelIgnore
	private Integer versionNum;

	@ExcelIgnore
	private String exportBatch;

	@ExcelProperty(value ="是否加入计算")
	private String isCalculate;

	@ExcelProperty(value ="费用年度")
	private String chargeYear;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getSuppPackSize() {
		return suppPackSize;
	}

	public void setSuppPackSize(String suppPackSize) {
		this.suppPackSize = suppPackSize;
	}

	public String getWrittenDate() {
		return writtenDate;
	}

	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getNotAfterDate() {
		return notAfterDate;
	}

	public void setNotAfterDate(String notAfterDate) {
		this.notAfterDate = notAfterDate;
	}

	public String getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	public String getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(String qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public String getQtyReceived() {
		return qtyReceived;
	}

	public void setQtyReceived(String qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	public String getVulOrdered() {
		return vulOrdered;
	}

	public void setVulOrdered(String vulOrdered) {
		this.vulOrdered = vulOrdered;
	}

	public String getVulReceived() {
		return vulReceived;
	}

	public void setVulReceived(String vulReceived) {
		this.vulReceived = vulReceived;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPromNumber() {
		return promNumber;
	}

	public void setPromNumber(String promNumber) {
		this.promNumber = promNumber;
	}

	public String getUnitCostInit() {
		return unitCostInit;
	}

	public void setUnitCostInit(String unitCostInit) {
		this.unitCostInit = unitCostInit;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getExportBatch() {
		return exportBatch;
	}

	public void setExportBatch(String exportBatch) {
		this.exportBatch = exportBatch;
	}

	public String getIsCalculate() {
		return isCalculate;
	}

	public void setIsCalculate(String isCalculate) {
		this.isCalculate = isCalculate;
	}

	public String getChargeYear() {
		return chargeYear;
	}

	public void setChargeYear(String chargeYear) {
		this.chargeYear = chargeYear;
	}
}
