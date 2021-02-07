package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductMedicalinfoEntity_HI_RO Entity Object Tue Sep 17 11:03:50 CST 2019
 * Auto Generate
 */

public class PlmProductMedicalinfoEntity_HI_RO {
	private Integer medicalId;
	private String name;
	private String medicalType;
	private String catalogCategory;
	private String registerNo;
	private String models;
	private String transCondition;
	private String ifBatchnumber;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date productSxdate;
	private String requireCode;
	private String standNo;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMedicalId(Integer medicalId) {
		this.medicalId = medicalId;
	}

	public Integer getMedicalId() {
		return medicalId;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	public String getMedicalType() {
		return medicalType;
	}

	public void setCatalogCategory(String catalogCategory) {
		this.catalogCategory = catalogCategory;
	}

	public String getCatalogCategory() {
		return catalogCategory;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public String getModels() {
		return models;
	}

	public void setTransCondition(String transCondition) {
		this.transCondition = transCondition;
	}

	public String getTransCondition() {
		return transCondition;
	}

	public void setIfBatchnumber(String ifBatchnumber) {
		this.ifBatchnumber = ifBatchnumber;
	}

	public String getIfBatchnumber() {
		return ifBatchnumber;
	}

	public void setProductSxdate(Date productSxdate) {
		this.productSxdate = productSxdate;
	}

	public Date getProductSxdate() {
		return productSxdate;
	}

	public void setRequireCode(String requireCode) {
		this.requireCode = requireCode;
	}

	public String getRequireCode() {
		return requireCode;
	}

	public void setStandNo(String standNo) {
		this.standNo = standNo;
	}

	public String getStandNo() {
		return standNo;
	}

	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}

	public String getSaleUnit() {
		return saleUnit;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
