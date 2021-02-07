package com.sie.saaf.business.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaPurchaseEntity_HI Entity Object
 * Mon Jul 08 16:19:55 CST 2019  Auto Generate
 *
 * purchase数据
 */
@Entity
@Table(name="TTA_PURCHASE")
public class TtaPurchaseEntity_HI {
    private Integer purchaseId;//主键
    private String vendorNbr;//供应商编号
    private String vendorName;//供应商名称
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date yearMonthDay;//收货日期
    private String location;//仓库库地址
    private String itemNbr;//ITEM编号
    private Integer receivingQty;//收货数量
    private java.math.BigDecimal receivingAmount;//收货金额
    private Integer cancelReceivingQty;//采购退货数量
    private java.math.BigDecimal cancelReceivingAmt;//采购退货金额
    private String currency;//币种
    private String purchaseType;//采购模式
    private String addressType;//地点类型
    private String poNbr;//PO单号
    private String isDsd;//IS_DSD
    private String store;//store
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

	@Id
	@SequenceGenerator(name = "SEQ_TTA_PURCHASE", sequenceName = "SEQ_TTA_PURCHASE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_PURCHASE", strategy = GenerationType.SEQUENCE)
	@Column(name="purchase_id", nullable=false, length=22)	
	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=false, length=50)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=false, length=230)	
	public String getVendorName() {
		return vendorName;
	}

	public void setYearMonthDay(Date yearMonthDay) {
		this.yearMonthDay = yearMonthDay;
	}

	@Column(name="year_month_day", nullable=false, length=7)	
	public Date getYearMonthDay() {
		return yearMonthDay;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="location", nullable=true, length=500)	
	public String getLocation() {
		return location;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	@Column(name="item_nbr", nullable=true, length=50)	
	public String getItemNbr() {
		return itemNbr;
	}

	public void setReceivingQty(Integer receivingQty) {
		this.receivingQty = receivingQty;
	}

	@Column(name="receiving_qty", nullable=true, length=22)	
	public Integer getReceivingQty() {
		return receivingQty;
	}

	public void setReceivingAmount(java.math.BigDecimal receivingAmount) {
		this.receivingAmount = receivingAmount;
	}

	@Column(name="receiving_amount", nullable=true, length=22)	
	public java.math.BigDecimal getReceivingAmount() {
		return receivingAmount;
	}

	public void setCancelReceivingQty(Integer cancelReceivingQty) {
		this.cancelReceivingQty = cancelReceivingQty;
	}

	@Column(name="cancel_receiving_qty", nullable=true, length=22)	
	public Integer getCancelReceivingQty() {
		return cancelReceivingQty;
	}

	public void setCancelReceivingAmt(java.math.BigDecimal cancelReceivingAmt) {
		this.cancelReceivingAmt = cancelReceivingAmt;
	}

	@Column(name="cancel_receiving_amt", nullable=true, length=22)	
	public java.math.BigDecimal getCancelReceivingAmt() {
		return cancelReceivingAmt;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name="currency", nullable=true, length=10)	
	public String getCurrency() {
		return currency;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Column(name="purchase_type", nullable=true, length=10)	
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	@Column(name="address_type", nullable=true, length=10)	
	public String getAddressType() {
		return addressType;
	}

	public void setPoNbr(String poNbr) {
		this.poNbr = poNbr;
	}

	@Column(name="po_nbr", nullable=true, length=50)	
	public String getPoNbr() {
		return poNbr;
	}

	public void setIsDsd(String isDsd) {
		this.isDsd = isDsd;
	}

	@Column(name="is_dsd", nullable=false, length=1)	
	public String getIsDsd() {
		return isDsd;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Column(name="store", nullable=true, length=50)	
	public String getStore() {
		return store;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
