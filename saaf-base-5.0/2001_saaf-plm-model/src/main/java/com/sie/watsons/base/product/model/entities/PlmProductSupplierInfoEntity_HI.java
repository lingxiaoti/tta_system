package com.sie.watsons.base.product.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductSupplierInfoEntity_HI Entity Object Thu Aug 29 10:51:53 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_SUPPLIER_INFO")
public class PlmProductSupplierInfoEntity_HI {
	private Integer id;
	@columnNames(name = "供应商id")
	private String supplierId;
	private String supplierCode;
	@columnNames(name = "供应商名称")
	private String supplierName;
	@columnNames(name = "是否优先供应商")
	private String isMainsupplier;
	@columnNames(name = "原产国")
	private String countryCode;
	@columnNames(name = "税务编号")
	private String rateCode;
	@columnNames(name = "成本币种")
	private String currencyCost;
	@columnNames(name = "成本")
	private String price;
	@columnNames(name = "税率")
	private String rate;
	@columnNames(name = "退货条件")
	private String condition;
	@columnNames(name = "商品可退货属性")
	private String productReturn;
	private Integer productHeadId;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String isSubmit;
	private String flags;

	@columnNames(name = "重量")
	private Integer weight;
	@columnNames(name = "外箱长(mm)")
	private Integer boxLength;
	@columnNames(name = "外箱宽度(mm)")
	private Integer boxBreadth;
	@columnNames(name = "外箱高(mm)")
	private Integer boxHeight;
	@columnNames(name = "外箱重")
	private Integer boxWeight;
	@columnNames(name = "商品长")
	private Integer productLength;
	@columnNames(name = "商品高")
	private Integer productHeight;
	@columnNames(name = "商品宽")
	private Integer productBreadth;
	@columnNames(name = "内包装规格(个)")
	private Integer innerpackageSpe;
	@columnNames(name = "包装规格")
	private Integer packageSpe;

	@columnNames(name = "产地")
	private String place;
	@columnNames(name = "多产地")
	private String productively;

	@columnNames(name = "驳回原因")
	private String returnReson;
	private String returnStatus;

	@columnNames(name = "生效方式")
	private String sxWay;
	@columnNames(name = "生效仓库")
	private String sxWarehouse;
	@columnNames(name = "生效门店")
	private String sxStore;
	@columnNames(name = "地点清单")
	private String placeNote;
	@columnNames(name = "区域")
	private String area;
	@columnNames(name = "地点清单编号")
	private String placeCode;
	private String rmsCode;

	private String sxWarehouseId;
	private String sxStoreId;

	private String roundToInnerPct;
	private String roundToCasePct;
	private String roundToLayerPct;
	private String roundToPalletPct;
	private String hi;
	private String ti;
	@columnNames(name = "资质组Id")
	private String groupId; // 资质组id
	@columnNames(name = "资质组名称")
	private String groupName; // 资质组名称

	// private String isUpdate; // 是否在修改中 //默认为0
	private String areaId; // 区域Id

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updatePriceDate; // 修改价格时间

	@Column(name = "update_price_date", nullable = true, length = 7)
	public Date getUpdatePriceDate() {
		return updatePriceDate;
	}

	public void setUpdatePriceDate(Date updatePriceDate) {
		this.updatePriceDate = updatePriceDate;
	}

	@Column(name = "sx_warehouse_id", nullable = true, length = 1000)
	public String getSxWarehouseId() {
		return sxWarehouseId;
	}

	public void setSxWarehouseId(String sxWarehouseId) {
		this.sxWarehouseId = sxWarehouseId;
	}

	@Column(name = "sx_store_id", nullable = true, length = 3000)
	public String getSxStoreId() {
		return sxStoreId;
	}

	public void setSxStoreId(String sxStoreId) {
		this.sxStoreId = sxStoreId;
	}

	@Column(name = "area_id", nullable = true, length = 255)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	// @Column(name = "is_update", nullable = true, length = 50)
	// public String getIsUpdate() {
	// return isUpdate;
	// }
	//
	// public void setIsUpdate(String isUpdate) {
	// this.isUpdate = isUpdate;
	// }

	@Column(name = "group_id", nullable = true, length = 255)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "group_name", nullable = true, length = 255)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "ROUND_TO_INNER_PCT", nullable = true, length = 255)
	public String getRoundToInnerPct() {
		return roundToInnerPct;
	}

	public void setRoundToInnerPct(String roundToInnerPct) {
		this.roundToInnerPct = roundToInnerPct;
	}

	@Column(name = "ROUND_TO_CASE_PCT", nullable = true, length = 255)
	public String getRoundToCasePct() {
		return roundToCasePct;
	}

	public void setRoundToCasePct(String roundToCasePct) {
		this.roundToCasePct = roundToCasePct;
	}

	@Column(name = "ROUND_TO_LAYER_PCT", nullable = true, length = 255)
	public String getRoundToLayerPct() {
		return roundToLayerPct;
	}

	public void setRoundToLayerPct(String roundToLayerPct) {
		this.roundToLayerPct = roundToLayerPct;
	}

	@Column(name = "ROUND_TO_PALLET_PCT", nullable = true, length = 255)
	public String getRoundToPalletPct() {
		return roundToPalletPct;
	}

	public void setRoundToPalletPct(String roundToPalletPct) {
		this.roundToPalletPct = roundToPalletPct;
	}

	@Column(name = "HI", nullable = true, length = 255)
	public String getHi() {
		return hi;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}

	@Column(name = "TI", nullable = true, length = 255)
	public String getTi() {
		return ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	@Column(name = "country_code", nullable = true, length = 255)
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "rms_code", nullable = true, length = 255)
	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name = "sx_way", nullable = true, length = 255)
	public String getSxWay() {
		return sxWay;
	}

	public void setSxWay(String sxWay) {
		this.sxWay = sxWay;
	}

	@Column(name = "sx_warehouse", nullable = true, length = 255)
	public String getSxWarehouse() {
		return sxWarehouse;
	}

	public void setSxWarehouse(String sxWarehouse) {
		this.sxWarehouse = sxWarehouse;
	}

	@Column(name = "sx_store", nullable = true, length = 255)
	public String getSxStore() {
		return sxStore;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	@Column(name = "place_note", nullable = true, length = 255)
	public String getPlaceNote() {
		return placeNote;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	@Column(name = "area", nullable = true, length = 255)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@columnNames(name = "修改成本价原因")
	private String updateSeson; // 修改成本原因 快码
	@columnNames(name = "修改成本价备注")
	private String Seson;// 原因

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_SUPPLIER_INFO", sequenceName = "SEQ_PLM_PRODUCT_SUPPLIER_INFO", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_SUPPLIER_INFO", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 22)
	public Integer getId() {
		return id;
	}

	@Column(name = "supplier_id", nullable = true, length = 100)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name = "supplier_code", nullable = true, length = 150)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "supplier_name", nullable = true, length = 100)
	public String getSupplierName() {
		return supplierName;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	@Column(name = "rate_code", nullable = true, length = 255)
	public String getRateCode() {
		return rateCode;
	}

	public void setCurrencyCost(String currencyCost) {
		this.currencyCost = currencyCost;
	}

	@Column(name = "currency_cost", nullable = true, length = 50)
	public String getCurrencyCost() {
		return currencyCost;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "price", nullable = true, length = 100)
	public String getPrice() {
		return price;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column(name = "rate", nullable = true, length = 100)
	public String getRate() {
		return rate;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name = "condition", nullable = true, length = 255)
	public String getCondition() {
		return condition;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	@Column(name = "product_return", nullable = true, length = 50)
	public String getProductReturn() {
		return productReturn;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name = "is_submit", nullable = true, length = 100)
	public String getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "flags", nullable = true, length = 255)
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name = "weight", nullable = true, length = 22)
	public Integer getWeight() {
		return weight;
	}

	public void setBoxLength(Integer boxLength) {
		this.boxLength = boxLength;
	}

	@Column(name = "box_length", nullable = true, length = 22)
	public Integer getBoxLength() {
		return boxLength;
	}

	public void setBoxBreadth(Integer boxBreadth) {
		this.boxBreadth = boxBreadth;
	}

	@Column(name = "box_breadth", nullable = true, length = 22)
	public Integer getBoxBreadth() {
		return boxBreadth;
	}

	public void setBoxHeight(Integer boxHeight) {
		this.boxHeight = boxHeight;
	}

	@Column(name = "box_height", nullable = true, length = 22)
	public Integer getBoxHeight() {
		return boxHeight;
	}

	public void setProductLength(Integer productLength) {
		this.productLength = productLength;
	}

	@Column(name = "product_length", nullable = true, length = 22)
	public Integer getProductLength() {
		return productLength;
	}

	public void setProductHeight(Integer productHeight) {
		this.productHeight = productHeight;
	}

	@Column(name = "product_height", nullable = true, length = 22)
	public Integer getProductHeight() {
		return productHeight;
	}

	public void setProductBreadth(Integer productBreadth) {
		this.productBreadth = productBreadth;
	}

	@Column(name = "product_breadth", nullable = true, length = 22)
	public Integer getProductBreadth() {
		return productBreadth;
	}

	public void setInnerpackageSpe(Integer innerpackageSpe) {
		this.innerpackageSpe = innerpackageSpe;
	}

	@Column(name = "innerpackage_spe", nullable = true, length = 22)
	public Integer getInnerpackageSpe() {
		return innerpackageSpe;
	}

	public void setPackageSpe(Integer packageSpe) {
		this.packageSpe = packageSpe;
	}

	@Column(name = "package_spe", nullable = true, length = 22)
	public Integer getPackageSpe() {
		return packageSpe;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "place", nullable = true, length = 255)
	public String getPlace() {
		return place;
	}

	@Column(name = "productively", nullable = true, length = 255)
	public String getProductively() {
		return productively;
	}

	public void setProductively(String productively) {
		this.productively = productively;
	}

	@Column(name = "box_weight", nullable = true, length = 7)
	public Integer getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(Integer boxWeight) {
		this.boxWeight = boxWeight;
	}

	@Column(name = "is_mainsupplier", nullable = true, length = 255)
	public String getIsMainsupplier() {
		return isMainsupplier;
	}

	public void setIsMainsupplier(String isMainsupplier) {
		this.isMainsupplier = isMainsupplier;
	}

	@Column(name = "return_reson", nullable = true, length = 255)
	public String getReturnReson() {
		return returnReson;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	@Column(name = "return_status", nullable = true, length = 255)
	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	@Column(name = "update_seson", nullable = true, length = 255)
	public String getUpdateSeson() {
		return updateSeson;
	}

	public void setUpdateSeson(String updateSeson) {
		this.updateSeson = updateSeson;
	}

	@Column(name = "seson", nullable = true, length = 255)
	public String getSeson() {
		return Seson;
	}

	public void setSeson(String seson) {
		Seson = seson;
	}

	@Column(name = "place_code", nullable = true, length = 255)
	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

}
