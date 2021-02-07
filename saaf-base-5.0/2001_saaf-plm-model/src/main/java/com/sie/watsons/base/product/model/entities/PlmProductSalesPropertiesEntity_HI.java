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
 * PlmProductSalesPropertiesEntity_HI Entity Object Thu Aug 29 10:51:52 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_SALES_PROPERTIES")
public class PlmProductSalesPropertiesEntity_HI {
	private Integer salesId;
	@columnNames(name = "地点类型")
	private String placeType;
	@columnNames(name = "地点明细")
	private String placeDetail;
	@columnNames(name = "可销售属性")
	private String salesProperties;
	@columnNames(name = "备注")
	private String remarks;
	@columnNames(name = "状态")
	private String status;
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

	private String placeCode;
	private String placeNote;
	private String sxStore;
	private String sxStoreId;
	private String areaId;
	private String area;

	@Column(name = "place_code", nullable = true, length = 3000)
	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	@Column(name = "place_note", nullable = true, length = 3000)
	public String getPlaceNote() {
		return placeNote;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	@Column(name = "sx_store", nullable = true, length = 3000)
	public String getSxStore() {
		return sxStore;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	@Column(name = "sx_store_id", nullable = true, length = 2000)
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

	@Column(name = "area", nullable = true, length = 500)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_SALES_PRO", sequenceName = "SEQ_PLM_PRODUCT_SALES_PRO", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_SALES_PRO", strategy = GenerationType.SEQUENCE)
	@Column(name = "sales_id", nullable = false, length = 22)
	public Integer getSalesId() {
		return salesId;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	@Column(name = "place_type", nullable = true, length = 150)
	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceDetail(String placeDetail) {
		this.placeDetail = placeDetail;
	}

	@Column(name = "place_detail", nullable = true, length = 255)
	public String getPlaceDetail() {
		return placeDetail;
	}

	public void setSalesProperties(String salesProperties) {
		this.salesProperties = salesProperties;
	}

	@Column(name = "sales_properties", nullable = true, length = 50)
	public String getSalesProperties() {
		return salesProperties;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "remarks", nullable = true, length = 255)
	public String getRemarks() {
		return remarks;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status", nullable = true, length = 50)
	public String getStatus() {
		return status;
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
}
