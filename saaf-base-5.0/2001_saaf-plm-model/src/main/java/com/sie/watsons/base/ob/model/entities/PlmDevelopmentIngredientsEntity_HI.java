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
 * PlmDevelopmentIngredientsEntity_HI Entity Object
 * Thu Aug 29 14:13:08 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_DEVELOPMENT_INGREDIENTS")
public class PlmDevelopmentIngredientsEntity_HI {
    private String inciOrCiSource;
    private String casNumber;
    private String einecsNumber;
    private String function;
    private java.math.BigDecimal w1;
    private java.math.BigDecimal w2;
    private java.math.BigDecimal w3;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer plmDevelopmentIngredientsId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String rawMaterialName;
    private String rawMaterialProducer;
    private String rawMaterialSourceArea;
    private String standardChineseName;
    private String inciName;
    private Integer operatorUserId;

	public void setInciOrCiSource(String inciOrCiSource) {
		this.inciOrCiSource = inciOrCiSource;
	}

	@Column(name="inci_or_ci_source", nullable=true, length=100)	
	public String getInciOrCiSource() {
		return inciOrCiSource;
	}

	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}

	@Column(name="cas_number", nullable=true, length=100)	
	public String getCasNumber() {
		return casNumber;
	}

	public void setEinecsNumber(String einecsNumber) {
		this.einecsNumber = einecsNumber;
	}

	@Column(name="einecs_number", nullable=true, length=100)	
	public String getEinecsNumber() {
		return einecsNumber;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	@Column(name="function", nullable=true, length=400)	
	public String getFunction() {
		return function;
	}

	public void setW1(java.math.BigDecimal w1) {
		this.w1 = w1;
	}

	@Column(name="w1", nullable=true, length=22)	
	public java.math.BigDecimal getW1() {
		return w1;
	}

	public void setW2(java.math.BigDecimal w2) {
		this.w2 = w2;
	}

	@Column(name="w2", nullable=true, length=22)	
	public java.math.BigDecimal getW2() {
		return w2;
	}

	public void setW3(java.math.BigDecimal w3) {
		this.w3 = w3;
	}

	@Column(name="w3", nullable=true, length=22)	
	public java.math.BigDecimal getW3() {
		return w3;
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

	public void setPlmDevelopmentIngredientsId(Integer plmDevelopmentIngredientsId) {
		this.plmDevelopmentIngredientsId = plmDevelopmentIngredientsId;
	}

	@Id	
	@SequenceGenerator(name="plmDevelopmentIngredientsEntity_HISeqGener", sequenceName="SEQ_PLM_DEVELOP_INGREDIENTS", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmDevelopmentIngredientsEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_development_ingredients_id", nullable=false, length=22)	
	public Integer getPlmDevelopmentIngredientsId() {
		return plmDevelopmentIngredientsId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="plm_development_info_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	@Column(name="plm_project_id", nullable=true, length=22)	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}

	@Column(name="raw_material_name", nullable=true, length=200)	
	public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialProducer(String rawMaterialProducer) {
		this.rawMaterialProducer = rawMaterialProducer;
	}

	@Column(name="raw_material_producer", nullable=true, length=200)	
	public String getRawMaterialProducer() {
		return rawMaterialProducer;
	}

	public void setRawMaterialSourceArea(String rawMaterialSourceArea) {
		this.rawMaterialSourceArea = rawMaterialSourceArea;
	}

	@Column(name="raw_material_source_area", nullable=true, length=200)	
	public String getRawMaterialSourceArea() {
		return rawMaterialSourceArea;
	}

	public void setStandardChineseName(String standardChineseName) {
		this.standardChineseName = standardChineseName;
	}

	@Column(name="standard_chinese_name", nullable=true, length=200)	
	public String getStandardChineseName() {
		return standardChineseName;
	}

	public void setInciName(String inciName) {
		this.inciName = inciName;
	}

	@Column(name="inci_name", nullable=true, length=200)	
	public String getInciName() {
		return inciName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
