package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaFreeGoodsPolistEntity_HI_RO Entity Object
 * Tue Aug 06 14:47:48 CST 2019  Auto Generate
 */

public class TtaFreeGoodsPolistEntity_HI_RO {

	private Integer id;
	private Integer week;
	private String location;
	private String supplier;
	private String orderNo;
	private String item;
	private String suppPackSize;
	private String writtenDate;
	private String closeDate;
	private String notAfterDate;
	private String unitCost;
	private String qtyOrdered;
	private String qtyReceived;
	private String vulOrdered;
	private String vulReceived;
	private String comments;
	private String supName;
	private String orderType;
	private String packageId;
	private String promNumber;
	private String unitCostInit;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;

	private Integer createdBy;

	private Integer lastUpdatedBy;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;

	private Integer lastUpdateLogin;

	private Integer versionNum;

	private String isCalculate;
	private String exportBatch;
	private Integer chargeYear;

    public static final String TTA_FREE_GOODS_POLIST = "select * from TTA_FREE_GOODS_POLIST s where 1=1";

    public static String getNoEqualSql(String exportBatch){
    	return "select * from TTA_FREE_GOODS_POLIST s where 1=1 and s.export_batch !='" + exportBatch + "'";
	}

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

	public String getIsCalculate() {
		return isCalculate;
	}

	public void setIsCalculate(String isCalculate) {
		this.isCalculate = isCalculate;
	}

	public String getExportBatch() {
		return exportBatch;
	}

	public void setExportBatch(String exportBatch) {
		this.exportBatch = exportBatch;
	}

	public Integer getChargeYear() {
		return chargeYear;
	}

	public void setChargeYear(Integer chargeYear) {
		this.chargeYear = chargeYear;
	}
}
