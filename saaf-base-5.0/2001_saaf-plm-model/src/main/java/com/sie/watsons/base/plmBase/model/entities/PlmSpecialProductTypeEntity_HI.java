package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmSpecialProductTypeEntity_HI Entity Object
 * Thu Oct 10 10:13:32 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_SPECIAL_PRODUCT_TYPE")
public class PlmSpecialProductTypeEntity_HI {
    private Integer plmSpecialProductTypeId;
    private String specialProductType;
    private String specialProductTypeName;
    private String controlType;
    private String controlValue;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

	public void setPlmSpecialProductTypeId(Integer plmSpecialProductTypeId) {
		this.plmSpecialProductTypeId = plmSpecialProductTypeId;
	}

	@Id	
	@SequenceGenerator(name="plmSpecialProductTypeEntity_HISeqGener", sequenceName="SEQ_PLM_SPECIAL_PRODUCT_TYPE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmSpecialProductTypeEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_special_product_type_id", nullable=false, length=22)	
	public Integer getPlmSpecialProductTypeId() {
		return plmSpecialProductTypeId;
	}

	public void setSpecialProductType(String specialProductType) {
		this.specialProductType = specialProductType;
	}

	@Column(name="special_product_type", nullable=true, length=50)	
	public String getSpecialProductType() {
		return specialProductType;
	}

	public void setSpecialProductTypeName(String specialProductTypeName) {
		this.specialProductTypeName = specialProductTypeName;
	}

	@Column(name="special_product_type_name", nullable=true, length=50)	
	public String getSpecialProductTypeName() {
		return specialProductTypeName;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	@Column(name="control_type", nullable=true, length=50)	
	public String getControlType() {
		return controlType;
	}

	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

	@Column(name="control_value", nullable=true, length=50)	
	public String getControlValue() {
		return controlValue;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
