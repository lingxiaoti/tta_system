package com.sie.watsons.base.report.model.entities;

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
 * TtaFreeGoodsOrderDetailEntity_HI Entity Object
 * Wed Nov 13 17:39:16 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_Free_Goods_Order_detail")
public class TtaFreeGoodsOrderDetailEntity_HI {
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
    private Integer relWeek;
    private String supplierCode;
    private String supplierName;
    private String relPo;
    private String relComments;
    private Integer itemCode;
    private String itemName;
    private String brand;
    private String groupDesc;
    private String deptDesc;
    private String relUnitCost;
    private String relQtyReceived;
    private String promotionPrice;
    private String actualMoney;
    private String relRderType;
    private String isCalculate;
    private String timesVersion;
    private Integer operatorUserId;
    private Integer chargeYear;
    private String openSelect;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_FREE_GOODS_O_D", sequenceName = "SEQ_TTA_FREE_GOODS_O_D", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_FREE_GOODS_O_D", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	@Column(name="week", nullable=true, length=22)	
	public Integer getWeek() {
		return week;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="location", nullable=true, length=100)	
	public String getLocation() {
		return location;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name="supplier", nullable=true, length=100)	
	public String getSupplier() {
		return supplier;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=100)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name="item", nullable=true, length=250)	
	public String getItem() {
		return item;
	}

	public void setSuppPackSize(String suppPackSize) {
		this.suppPackSize = suppPackSize;
	}

	@Column(name="supp_pack_size", nullable=true, length=50)	
	public String getSuppPackSize() {
		return suppPackSize;
	}

	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}

	@Column(name="written_date", nullable=true, length=50)	
	public String getWrittenDate() {
		return writtenDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name="close_date", nullable=true, length=550)	
	public String getCloseDate() {
		return closeDate;
	}

	public void setNotAfterDate(String notAfterDate) {
		this.notAfterDate = notAfterDate;
	}

	@Column(name="not_after_date", nullable=true, length=50)	
	public String getNotAfterDate() {
		return notAfterDate;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	@Column(name="unit_cost", nullable=true, length=250)	
	public String getUnitCost() {
		return unitCost;
	}

	public void setQtyOrdered(String qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	@Column(name="qty_ordered", nullable=true, length=250)	
	public String getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyReceived(String qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	@Column(name="qty_received", nullable=true, length=50)	
	public String getQtyReceived() {
		return qtyReceived;
	}

	public void setVulOrdered(String vulOrdered) {
		this.vulOrdered = vulOrdered;
	}

	@Column(name="vul_ordered", nullable=true, length=50)	
	public String getVulOrdered() {
		return vulOrdered;
	}

	public void setVulReceived(String vulReceived) {
		this.vulReceived = vulReceived;
	}

	@Column(name="vul_received", nullable=true, length=500)	
	public String getVulReceived() {
		return vulReceived;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name="comments", nullable=true, length=100)	
	public String getComments() {
		return comments;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	@Column(name="sup_name", nullable=true, length=150)
	public String getSupName() {
		return supName;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name="order_type", nullable=true, length=50)	
	public String getOrderType() {
		return orderType;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	@Column(name="package_id", nullable=true, length=50)	
	public String getPackageId() {
		return packageId;
	}

	public void setPromNumber(String promNumber) {
		this.promNumber = promNumber;
	}

	@Column(name="prom_number", nullable=true, length=50)	
	public String getPromNumber() {
		return promNumber;
	}

	public void setUnitCostInit(String unitCostInit) {
		this.unitCostInit = unitCostInit;
	}

	@Column(name="unit_cost_init", nullable=true, length=50)	
	public String getUnitCostInit() {
		return unitCostInit;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setRelWeek(Integer relWeek) {
		this.relWeek = relWeek;
	}

	@Column(name="rel_week", nullable=true, length=22)	
	public Integer getRelWeek() {
		return relWeek;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=100)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=150)
	public String getSupplierName() {
		return supplierName;
	}

	public void setRelPo(String relPo) {
		this.relPo = relPo;
	}

	@Column(name="rel_po", nullable=true, length=100)	
	public String getRelPo() {
		return relPo;
	}

	public void setRelComments(String relComments) {
		this.relComments = relComments;
	}

	@Column(name="rel_comments", nullable=true, length=300)	
	public String getRelComments() {
		return relComments;
	}

	public void setItemCode(Integer itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=22)	
	public Integer getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name="item_name", nullable=true, length=50)	
	public String getItemName() {
		return itemName;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=550)	
	public String getBrand() {
		return brand;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=50)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=250)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setRelUnitCost(String relUnitCost) {
		this.relUnitCost = relUnitCost;
	}

	@Column(name="rel_unit_cost", nullable=true, length=250)	
	public String getRelUnitCost() {
		return relUnitCost;
	}

	public void setRelQtyReceived(String relQtyReceived) {
		this.relQtyReceived = relQtyReceived;
	}

	@Column(name="rel_qty_received", nullable=true, length=50)	
	public String getRelQtyReceived() {
		return relQtyReceived;
	}

	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	@Column(name="promotion_price", nullable=true, length=50)	
	public String getPromotionPrice() {
		return promotionPrice;
	}

	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}

	@Column(name="actual_money", nullable=true, length=100)	
	public String getActualMoney() {
		return actualMoney;
	}

	public void setRelRderType(String relRderType) {
		this.relRderType = relRderType;
	}

	@Column(name="rel_rder_type", nullable=true, length=100)	
	public String getRelRderType() {
		return relRderType;
	}

	public void setIsCalculate(String isCalculate) {
		this.isCalculate = isCalculate;
	}

	@Column(name="is_calculate", nullable=true, length=50)	
	public String getIsCalculate() {
		return isCalculate;
	}

	public void setTimesVersion(String timesVersion) {
		this.timesVersion = timesVersion;
	}

	@Column(name="times_version", nullable=true, length=50)	
	public String getTimesVersion() {
		return timesVersion;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="charge_year", nullable=true, length=22)
	public Integer getChargeYear() {
		return chargeYear;
	}

	public void setChargeYear(Integer chargeYear) {
		this.chargeYear = chargeYear;
	}

	@Column(name="open_select", nullable=true, length=30)
	public String getOpenSelect() {
		return openSelect;
	}

	public void setOpenSelect(String openSelect) {
		this.openSelect = openSelect;
	}
}
