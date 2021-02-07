package com.sie.watsons.base.productEco.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProductMedicalinfoEcoEntity_HI_RO Entity Object
 * Tue Jun 02 11:32:26 CST 2020  Auto Generate
 */

public class PlmProductMedicalinfoEcoEntity_HI_RO {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
    private Integer medicalId;
    private String medicalType;
    private String catalogCategory;
    private String registerNo;
    private String models;
    private String transCondition;
    private String ifBatchnumber;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date productSxdate;
    private String requireCode;
    private String standNo;
    private String saleUnit;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer productHeadId;
    private String name;
    private Integer operatorUserId;

	public void setEcoId(Integer ecoId) {
		this.ecoId = ecoId;
	}

	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	
	public Integer getLineId() {
		return lineId;
	}

	public void setAcdType(String acdType) {
		this.acdType = acdType;
	}

	
	public String getAcdType() {
		return acdType;
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

	public void setName(String name) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
