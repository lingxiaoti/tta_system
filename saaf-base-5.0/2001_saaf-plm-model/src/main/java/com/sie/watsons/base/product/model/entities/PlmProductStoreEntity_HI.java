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
 * PlmProductStoreEntity_HI Entity Object Tue Nov 12 15:01:21 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_STORE")
public class PlmProductStoreEntity_HI {
	private Integer storeId;
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
	@columnNames(name = "代销类型")
	private String substituteType;
	@columnNames(name = "代销比例")
	private String substitutePropetion;
	@columnNames(name = "生效日期")
	@JSONField(format = "yyyy-MM-dd")
	private Date startDate;
	@columnNames(name = "失效日期")
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String supplierId;
	private Integer productHeadId;
	@columnNames(name = "地点清单编号")
	private String placeCode;

	private String areaId; // 区域Id

	private String sxWarehouseId;
	private String sxStoreId;

	private String currencyCost;
	private Float exRate;

	@Column(name = "currency_cost", nullable = true, length = 50)
	public String getCurrencyCost() {
		return currencyCost;
	}

	public void setCurrencyCost(String currencyCost) {
		this.currencyCost = currencyCost;
	}

	@Column(name = "ex_rate", nullable = true, length = 4)
	public Float getExRate() {
		return exRate;
	}

	public void setExRate(Float exRate) {
		this.exRate = exRate;
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

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_STORE", sequenceName = "SEQ_PLM_PRODUCT_STORE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_STORE", strategy = GenerationType.SEQUENCE)
	@Column(name = "store_id", nullable = true, length = 22)
	public Integer getStoreId() {
		return storeId;
	}

	@Column(name = "area_id", nullable = true, length = 255)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setSxWay(String sxWay) {
		this.sxWay = sxWay;
	}

	@Column(name = "sx_way", nullable = true, length = 255)
	public String getSxWay() {
		return sxWay;
	}

	@Column(name = "place_code", nullable = true, length = 255)
	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public void setSxWarehouse(String sxWarehouse) {
		this.sxWarehouse = sxWarehouse;
	}

	@Column(name = "sx_warehouse", nullable = true, length = 255)
	public String getSxWarehouse() {
		return sxWarehouse;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	@Column(name = "sx_store", nullable = true, length = 255)
	public String getSxStore() {
		return sxStore;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	@Column(name = "place_note", nullable = true, length = 255)
	public String getPlaceNote() {
		return placeNote;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "area", nullable = true, length = 255)
	public String getArea() {
		return area;
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

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "supplier_id", nullable = true, length = 22)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "start_date", nullable = true, length = 7)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date", nullable = true, length = 7)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setSubstituteType(String substituteType) {
		this.substituteType = substituteType;
	}

	@Column(name = "substitute_type", nullable = true, length = 100)
	public String getSubstituteType() {
		return substituteType;
	}

	public void setSubstitutePropetion(String substitutePropetion) {
		this.substitutePropetion = substitutePropetion;
	}

	@Column(name = "substitute_propetion", nullable = true, length = 100)
	public String getSubstitutePropetion() {
		return substitutePropetion;
	}

}
