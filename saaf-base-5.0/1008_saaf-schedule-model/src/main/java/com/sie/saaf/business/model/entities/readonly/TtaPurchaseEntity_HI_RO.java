package com.sie.saaf.business.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaPurchaseEntity_HI_RO Entity Object
 * Mon Jul 08 16:19:55 CST 2019  Auto Generate
 * purchase数据
 */

public class TtaPurchaseEntity_HI_RO {
    private Integer purchaseId;//主键
    private String vendorNbr;//供应商编号
    private String vendorName;//供应商名称
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date yearMonthDay;//收货日期
    private String location;//仓库地址
    private String itemNbr;//item编号
    private Integer receivingQty;//收货数量
    private java.math.BigDecimal receivingAmount;//收货金额
    private Integer cancelReceivingQty;//采购退货数量
    private java.math.BigDecimal cancelReceivingAmt;//采购退货金额
    private String currency;//币种
    private String purchaseType;//采购类型
    private String addressType;//地址类型
    private String poNbr;//PO单号
    private String isDsd;//是否DSD
    private String store;//STORE
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	
	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setYearMonthDay(Date yearMonthDay) {
		this.yearMonthDay = yearMonthDay;
	}

	
	public Date getYearMonthDay() {
		return yearMonthDay;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public String getLocation() {
		return location;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	
	public String getItemNbr() {
		return itemNbr;
	}

	public void setReceivingQty(Integer receivingQty) {
		this.receivingQty = receivingQty;
	}

	
	public Integer getReceivingQty() {
		return receivingQty;
	}

	public void setReceivingAmount(java.math.BigDecimal receivingAmount) {
		this.receivingAmount = receivingAmount;
	}

	
	public java.math.BigDecimal getReceivingAmount() {
		return receivingAmount;
	}

	public void setCancelReceivingQty(Integer cancelReceivingQty) {
		this.cancelReceivingQty = cancelReceivingQty;
	}

	
	public Integer getCancelReceivingQty() {
		return cancelReceivingQty;
	}

	public void setCancelReceivingAmt(java.math.BigDecimal cancelReceivingAmt) {
		this.cancelReceivingAmt = cancelReceivingAmt;
	}

	
	public java.math.BigDecimal getCancelReceivingAmt() {
		return cancelReceivingAmt;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
	public String getCurrency() {
		return currency;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	
	public String getAddressType() {
		return addressType;
	}

	public void setPoNbr(String poNbr) {
		this.poNbr = poNbr;
	}

	
	public String getPoNbr() {
		return poNbr;
	}

	public void setIsDsd(String isDsd) {
		this.isDsd = isDsd;
	}

	
	public String getIsDsd() {
		return isDsd;
	}

	public void setStore(String store) {
		this.store = store;
	}

	
	public String getStore() {
		return store;
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
