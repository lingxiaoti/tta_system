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
 * PlmProductSupplierplaceinfoEntity_HI Entity Object Thu Dec 05 17:07:01 CST
 * 2019 Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_SUPPLIERPLACEINFO")
public class PlmProductSupplierplaceinfoEntity_HI {
	private Integer id;
	private String productHeadId;
	private String locType;
	private String location;
	private String rmsId;
	private String status;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private String supplierCode;
	private String productName;
	private String supplierName;
	private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "PLM_SUPPLIERPLACE", sequenceName = "PLM_SUPPLIERPLACE", allocationSize = 1)
	@GeneratedValue(generator = "PLM_SUPPLIERPLACE", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = true, length = 22)
	public Integer getId() {
		return id;
	}

	public void setProductHeadId(String productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 255)
	public String getProductHeadId() {
		return productHeadId;
	}

	public void setLocType(String locType) {
		this.locType = locType;
	}

	@Column(name = "loc_type", nullable = true, length = 255)
	public String getLocType() {
		return locType;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "location", nullable = true, length = 255)
	public String getLocation() {
		return location;
	}

	public void setRmsId(String rmsId) {
		this.rmsId = rmsId;
	}

	@Column(name = "rms_id", nullable = true, length = 255)
	public String getRmsId() {
		return rmsId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status", nullable = true, length = 255)
	public String getStatus() {
		return status;
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

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name = "supplier_code", nullable = true, length = 255)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_name", nullable = true, length = 255)
	public String getProductName() {
		return productName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "supplier_name", nullable = true, length = 255)
	public String getSupplierName() {
		return supplierName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
