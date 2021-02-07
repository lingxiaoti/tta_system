package com.sie.watsons.base.productEco.model.entities;

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
 * PlmProductMedicalinfoEcoEntity_HI Entity Object
 * Tue Jun 02 11:32:26 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_MEDICALINFO_ECO")
public class PlmProductMedicalinfoEcoEntity_HI {
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

	@Column(name="eco_id", nullable=true, length=22)	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_LINE", sequenceName = "SEQ_PLM_PRODUCT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="line_id", nullable=true, length=22)	
	public Integer getLineId() {
		return lineId;
	}

	public void setAcdType(String acdType) {
		this.acdType = acdType;
	}

	@Column(name="acd_type", nullable=true, length=20)	
	public String getAcdType() {
		return acdType;
	}

	public void setMedicalId(Integer medicalId) {
		this.medicalId = medicalId;
	}

	@Column(name="medical_id", nullable=false, length=22)	
	public Integer getMedicalId() {
		return medicalId;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	@Column(name="medical_type", nullable=true, length=255)	
	public String getMedicalType() {
		return medicalType;
	}

	public void setCatalogCategory(String catalogCategory) {
		this.catalogCategory = catalogCategory;
	}

	@Column(name="catalog_category", nullable=true, length=255)	
	public String getCatalogCategory() {
		return catalogCategory;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	@Column(name="register_no", nullable=true, length=255)	
	public String getRegisterNo() {
		return registerNo;
	}

	public void setModels(String models) {
		this.models = models;
	}

	@Column(name="models", nullable=true, length=255)	
	public String getModels() {
		return models;
	}

	public void setTransCondition(String transCondition) {
		this.transCondition = transCondition;
	}

	@Column(name="trans_condition", nullable=true, length=255)	
	public String getTransCondition() {
		return transCondition;
	}

	public void setIfBatchnumber(String ifBatchnumber) {
		this.ifBatchnumber = ifBatchnumber;
	}

	@Column(name="if_batchnumber", nullable=true, length=255)	
	public String getIfBatchnumber() {
		return ifBatchnumber;
	}

	public void setProductSxdate(Date productSxdate) {
		this.productSxdate = productSxdate;
	}

	@Column(name="product_sxdate", nullable=true, length=7)	
	public Date getProductSxdate() {
		return productSxdate;
	}

	public void setRequireCode(String requireCode) {
		this.requireCode = requireCode;
	}

	@Column(name="require_code", nullable=true, length=255)	
	public String getRequireCode() {
		return requireCode;
	}

	public void setStandNo(String standNo) {
		this.standNo = standNo;
	}

	@Column(name="stand_no", nullable=true, length=255)	
	public String getStandNo() {
		return standNo;
	}

	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}

	@Column(name="sale_unit", nullable=true, length=255)	
	public String getSaleUnit() {
		return saleUnit;
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

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name="product_head_id", nullable=true, length=22)	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="name", nullable=true, length=255)	
	public String getName() {
		return name;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
