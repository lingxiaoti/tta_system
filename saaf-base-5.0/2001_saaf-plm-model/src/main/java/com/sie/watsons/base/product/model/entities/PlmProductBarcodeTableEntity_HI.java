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
 * PlmProductBarcodeTableEntity_HI Entity Object Thu Aug 29 10:51:48 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_BARCODE_TABLE")
public class PlmProductBarcodeTableEntity_HI {
	private Integer barcodeId;
	@columnNames(name = "obId")
	private String obId;
	@columnNames(name = "barcode")
	private String barcode;
	// @columnNames(name = "条码类型")
	// private String barcodeType;
	@columnNames(name = "是否主条码")
	private String isMain;
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
	@columnNames(name = "条码类型")
	private String barcodeType;

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_BARCODE_TABLE", sequenceName = "SEQ_PLM_PRODUCT_BARCODE_TABLE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_BARCODE_TABLE", strategy = GenerationType.SEQUENCE)
	@Column(name = "barcode_id", nullable = false, length = 22)
	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	@Column(name = "ob_id", nullable = true, length = 50)
	public String getObId() {
		return obId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name = "barcode", nullable = true, length = 255)
	public String getBarcode() {
		return barcode;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	@Column(name = "is_main", nullable = true, length = 50)
	public String getIsMain() {
		return isMain;
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

	@Column(name = "last_update_login", nullable = true, length = 7)
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

	@Column(name = "barcode_type", nullable = true, length = 255)
	public String getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(String barcodeType) {
		this.barcodeType = barcodeType;
	}

}
