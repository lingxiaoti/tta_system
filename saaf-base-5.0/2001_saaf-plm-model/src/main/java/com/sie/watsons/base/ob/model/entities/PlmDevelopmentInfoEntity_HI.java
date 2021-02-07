package com.sie.watsons.base.ob.model.entities;

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
 * PlmDevelopmentInfoEntity_HI Entity Object Thu Aug 29 14:13:07 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_DEVELOPMENT_INFO")
public class PlmDevelopmentInfoEntity_HI {
	private Integer plmDevelopmentInfoId;
	private String obId;
	private Integer plmProjectId;
	private Integer plmProjectProductDetailId;
	private String barcode;
	private String barcodeType;
	private String projectRange;
	private String projectName;
	private String biddingCode;
	private String brandCn;
	private String productName;
	private String productNo;
	private String productNameCn;
	private String productNameEn;
	private Integer supplierId;
	private String supplierCode;
	private String supplierType;
	private String supplierName;
	private String producer;
	private String developmentBillStatus;
	private String productStatus;
	private String specialApprovalFlag;
	private String productCategory;
	private String projectCreator;
	private String productCreator;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private String remarks;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Id
	@SequenceGenerator(name = "plmDevelopmentInfoEntity_HISeqGener", sequenceName = "SEQ_PLM_DEVELOPMENT_INFO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "plmDevelopmentInfoEntity_HISeqGener", strategy = GenerationType.SEQUENCE)
	@Column(name = "plm_development_info_id", nullable = false, length = 22)
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	@Column(name = "barcode_type", nullable = true, length = 50)
	public String getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(String barcodeType) {
		this.barcodeType = barcodeType;
	}

	@Column(name = "ob_id", nullable = true, length = 50)
	public String getObId() {
		return obId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	@Column(name = "plm_project_id", nullable = true, length = 22)
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name = "plm_project_product_detail_id", nullable = true, length = 22)
	public Integer getPlmProjectProductDetailId() {
		return plmProjectProductDetailId;
	}

	public void setPlmProjectProductDetailId(Integer plmProjectProductDetailId) {
		this.plmProjectProductDetailId = plmProjectProductDetailId;
	}

	@Column(name = "barcode", nullable = true, length = 50)
	public String getBarcode() {
		return barcode;
	}

	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}

	@Column(name = "project_range", nullable = true, length = 400)
	public String getProjectRange() {
		return projectRange;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "bidding_code", nullable = true, length = 20)
	public String getBiddingCode() {
		return biddingCode;
	}

	public void setBiddingCode(String biddingCode) {
		this.biddingCode = biddingCode;
	}

	@Column(name = "project_name", nullable = true, length = 200)
	public String getProjectName() {
		return projectName;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name = "brand_cn", nullable = true, length = 200)
	public String getBrandCn() {
		return brandCn;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_name", nullable = true, length = 400)
	public String getProductName() {
		return productName;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	@Column(name = "product_no", nullable = true, length = 100)
	public String getProductNo() {
		return productNo;
	}

	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}

	@Column(name = "product_name_cn", nullable = true, length = 200)
	public String getProductNameCn() {
		return productNameCn;
	}

	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}

	@Column(name = "product_name_en", nullable = true, length = 200)
	public String getProductNameEn() {
		return productNameEn;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	@Column(name = "supplier_id", nullable = true, length = 22)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "supplier_code", nullable = true, length = 100)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name = "supplier_type", nullable = true, length = 50)
	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "supplier_name", nullable = true, length = 800)
	public String getSupplierName() {
		return supplierName;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Column(name = "producer", nullable = true, length = 800)
	public String getProducer() {
		return producer;
	}

	public void setDevelopmentBillStatus(String developmentBillStatus) {
		this.developmentBillStatus = developmentBillStatus;
	}

	@Column(name = "development_bill_status", nullable = true, length = 20)
	public String getDevelopmentBillStatus() {
		return developmentBillStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	@Column(name = "product_status", nullable = true, length = 20)
	public String getProductStatus() {
		return productStatus;
	}

	public void setSpecialApprovalFlag(String specialApprovalFlag) {
		this.specialApprovalFlag = specialApprovalFlag;
	}

	@Column(name = "special_approval_flag", nullable = true, length = 20)
	public String getSpecialApprovalFlag() {
		return specialApprovalFlag;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Column(name = "product_category", nullable = true, length = 50)
	public String getProductCategory() {
		return productCategory;
	}

	public void setProjectCreator(String projectCreator) {
		this.projectCreator = projectCreator;
	}

	@Column(name = "project_creator", nullable = true, length = 100)
	public String getProjectCreator() {
		return projectCreator;
	}

	public void setProductCreator(String productCreator) {
		this.productCreator = productCreator;
	}

	@Column(name = "product_creator", nullable = true, length = 100)
	public String getProductCreator() {
		return productCreator;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "start_date", nullable = true, length = 7)
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "end_date", nullable = true, length = 7)
	public Date getEndDate() {
		return endDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "remarks", nullable = true, length = 800)
	public String getRemarks() {
		return remarks;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
