package com.sie.watsons.base.ob.model.entities;

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
 * PlmProductExceptionDetailEntity_HI Entity Object
 * Thu Aug 29 14:13:10 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_EXCEPTION_DETAIL")
public class PlmProductExceptionDetailEntity_HI {
    private Integer complaintSamplesNumber;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer plmProductExcepDetailId;
    private Integer plmProductExceptionInfoId;
    private String barcode;
    private String projectRange;
    private Integer plmDevelopmentInfoId;
    private String projectName;
    private String productName;
    private String productNameCn;
    private String productNameEn;
    private String productCategory;
    private String supplierName;
    private String producer;
    private String batchNumber;
    private Integer operatorUserId;
    private String productNo;

	public void setComplaintSamplesNumber(Integer complaintSamplesNumber) {
		this.complaintSamplesNumber = complaintSamplesNumber;
	}

	@Column(name="complaint_samples_number", nullable=true, length=22)	
	public Integer getComplaintSamplesNumber() {
		return complaintSamplesNumber;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setPlmProductExcepDetailId(Integer plmProductExcepDetailId) {
		this.plmProductExcepDetailId = plmProductExcepDetailId;
	}

	@Id	
	@SequenceGenerator(name="plmProductExceptionDetailEntity_HISeqGener", sequenceName="SEQ_PLM_PROD_EXCEPTION_DETAIL", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmProductExceptionDetailEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_product_excep_detail_id", nullable=false, length=22)	
	public Integer getPlmProductExcepDetailId() {
		return plmProductExcepDetailId;
	}

	public void setPlmProductExceptionInfoId(Integer plmProductExceptionInfoId) {
		this.plmProductExceptionInfoId = plmProductExceptionInfoId;
	}

	@Column(name="plm_product_exception_info_id", nullable=true, length=22)	
	public Integer getPlmProductExceptionInfoId() {
		return plmProductExceptionInfoId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name="barcode", nullable=true, length=50)	
	public String getBarcode() {
		return barcode;
	}

	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}

	@Column(name="project_range", nullable=true, length=400)	
	public String getProjectRange() {
		return projectRange;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="plm_development_info_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name="project_name", nullable=true, length=200)	
	public String getProjectName() {
		return projectName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="product_name", nullable=true, length=400)	
	public String getProductName() {
		return productName;
	}

	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}

	@Column(name="product_name_cn", nullable=true, length=200)	
	public String getProductNameCn() {
		return productNameCn;
	}

	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}

	@Column(name="product_name_en", nullable=true, length=200)	
	public String getProductNameEn() {
		return productNameEn;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Column(name="product_category", nullable=true, length=50)	
	public String getProductCategory() {
		return productCategory;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=200)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Column(name="producer", nullable=true, length=200)	
	public String getProducer() {
		return producer;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	@Column(name="batch_number", nullable=true, length=400)	
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="product_no", nullable=true, length=100)
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
}
