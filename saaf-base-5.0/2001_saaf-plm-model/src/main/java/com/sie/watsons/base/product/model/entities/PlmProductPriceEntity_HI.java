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
 * PlmProductPriceEntity_HI Entity Object Thu Aug 29 10:51:51 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_PRICE")
public class PlmProductPriceEntity_HI {
	private Integer priceId;
	@columnNames(name = "售价分组")
	private String priceGroup;
	private String priceGroupCode;
	@columnNames(name = "售价区域")
	private String priceArea;
	@columnNames(name = "零售价")
	private String unitPrice;
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
	private String flags;

	@columnNames(name = "零售价修改原因")
	private String updateSeson; // 修改原因
	@columnNames(name = "零售价修改备注")
	private String Seson;// 原因
	@columnNames(name = "零售价修改类型")
	private String updateType; // 修改类型
	private Integer storeId; // 门店Id

	@columnNames(name = "售价分组Id")
	private String groupId;
	@columnNames(name = "售价区域Id")
	private String areaId; // 区域id

	@columnNames(name = "是否主零售价")
	private String mainPrice; // 主零售价

	private String isUpdate; // 是否在修改中 默认为0

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updatePriceDate; // 修改价格时间

	@Column(name = "update_price_date", nullable = true, length = 7)
	public Date getUpdatePriceDate() {
		return updatePriceDate;
	}

	public void setUpdatePriceDate(Date updatePriceDate) {
		this.updatePriceDate = updatePriceDate;
	}

	@Column(name = "is_update", nullable = true, length = 255)
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	@Column(name = "price_group_code", nullable = true, length = 255)
	public String getPriceGroupCode() {
		return priceGroupCode;
	}

	public void setPriceGroupCode(String priceGroupCode) {
		this.priceGroupCode = priceGroupCode;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_PRICE", sequenceName = "SEQ_PLM_PRODUCT_PRICE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_PRICE", strategy = GenerationType.SEQUENCE)
	@Column(name = "price_id", nullable = false, length = 22)
	public Integer getPriceId() {
		return priceId;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

	@Column(name = "main_price", nullable = true, length = 255)
	public String getMainPrice() {
		return mainPrice;
	}

	public void setMainPrice(String mainPrice) {
		this.mainPrice = mainPrice;
	}

	@Column(name = "price_group", nullable = true, length = 255)
	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceArea(String priceArea) {
		this.priceArea = priceArea;
	}

	@Column(name = "price_area", nullable = true, length = 255)
	public String getPriceArea() {
		return priceArea;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "unit_price", nullable = true, length = 100)
	public String getUnitPrice() {
		return unitPrice;
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

	@Column(name = "update_type", nullable = true, length = 255)
	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	@Column(name = "store_id", nullable = true, length = 255)
	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	@Column(name = "group_id", nullable = true, length = 255)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "area_id", nullable = true, length = 255)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
