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
 * PlmProductMedicalinfoEntity_HI Entity Object Tue Sep 17 11:03:50 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_MEDICALINFO")
public class PlmProductMedicalinfoEntity_HI {
	private Integer medicalId;
	@columnNames(name = "医疗器械名称")
	private String name;
	@columnNames(name = "医疗器械类型")
	private String medicalType;
	@columnNames(name = "医疗器械目录类别")
	private String catalogCategory;
	@columnNames(name = "医疗器械注册证号")
	private String registerNo;
	@columnNames(name = "医疗器械规格型号")
	private String models;
	@columnNames(name = "医疗器械储运条件")
	private String transCondition;
	@columnNames(name = "医疗器械(是否灭菌批号管理)")
	private String ifBatchnumber;
	@columnNames(name = "医疗器械产品有效期")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date productSxdate;
	@columnNames(name = "医疗器械唯一标识码")
	private String requireCode;
	@columnNames(name = "医疗器械产品技术要求编号")
	private String standNo;
	@columnNames(name = "医疗器械销售单位")
	private String saleUnit;

	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer productHeadId;
	private Integer operatorUserId;

	public void setMedicalId(Integer medicalId) {
		this.medicalId = medicalId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_MEDICAL", sequenceName = "SEQ_PLM_PRODUCT_MEDICAL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_MEDICAL", strategy = GenerationType.SEQUENCE)
	@Column(name = "medical_id", nullable = false, length = 22)
	public Integer getMedicalId() {
		return medicalId;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	@Column(name = "medical_type", nullable = true, length = 255)
	public String getMedicalType() {
		return medicalType;
	}

	public void setCatalogCategory(String catalogCategory) {
		this.catalogCategory = catalogCategory;
	}

	@Column(name = "catalog_category", nullable = true, length = 255)
	public String getCatalogCategory() {
		return catalogCategory;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	@Column(name = "register_no", nullable = true, length = 255)
	public String getRegisterNo() {
		return registerNo;
	}

	public void setModels(String models) {
		this.models = models;
	}

	@Column(name = "models", nullable = true, length = 255)
	public String getModels() {
		return models;
	}

	public void setTransCondition(String transCondition) {
		this.transCondition = transCondition;
	}

	@Column(name = "trans_condition", nullable = true, length = 255)
	public String getTransCondition() {
		return transCondition;
	}

	public void setIfBatchnumber(String ifBatchnumber) {
		this.ifBatchnumber = ifBatchnumber;
	}

	@Column(name = "if_batchnumber", nullable = true, length = 255)
	public String getIfBatchnumber() {
		return ifBatchnumber;
	}

	public void setProductSxdate(Date productSxdate) {
		this.productSxdate = productSxdate;
	}

	@Column(name = "product_sxdate", nullable = true, length = 7)
	public Date getProductSxdate() {
		return productSxdate;
	}

	public void setRequireCode(String requireCode) {
		this.requireCode = requireCode;
	}

	@Column(name = "require_code", nullable = true, length = 255)
	public String getRequireCode() {
		return requireCode;
	}

	public void setStandNo(String standNo) {
		this.standNo = standNo;
	}

	@Column(name = "stand_no", nullable = true, length = 255)
	public String getStandNo() {
		return standNo;
	}

	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}

	@Column(name = "sale_unit", nullable = true, length = 255)
	public String getSaleUnit() {
		return saleUnit;
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

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "name", nullable = true, length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
