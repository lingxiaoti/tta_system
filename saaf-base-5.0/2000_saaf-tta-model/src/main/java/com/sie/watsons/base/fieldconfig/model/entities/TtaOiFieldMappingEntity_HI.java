package com.sie.watsons.base.fieldconfig.model.entities;

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
 * TtaOiFieldMappingEntity_HI Entity Object
 * Tue Nov 12 12:12:42 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_oi_field_mapping")
public class TtaOiFieldMappingEntity_HI {
    private Integer fieldId;
    private Integer tradeYear;
    private String sourceFieldName;
    private String sourceFieldRemark;
    private String targetFieldName;
    private String targetFieldRemark;
    private String businessType;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String isEnable;

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	@Id
	@SequenceGenerator(name="seq_tta_oi_field_mapping", sequenceName="seq_tta_oi_field_mapping", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="seq_tta_oi_field_mapping",strategy=GenerationType.SEQUENCE)
	@Column(name="field_id", nullable=true, length=22)	
	public Integer getFieldId() {
		return fieldId;
	}

	public void setTradeYear(Integer tradeYear) {
		this.tradeYear = tradeYear;
	}

	@Column(name="trade_year", nullable=true, length=22)	
	public Integer getTradeYear() {
		return tradeYear;
	}

	public void setSourceFieldName(String sourceFieldName) {
		this.sourceFieldName = sourceFieldName;
	}

	@Column(name="source_field_name", nullable=true, length=50)	
	public String getSourceFieldName() {
		return sourceFieldName;
	}

	public void setSourceFieldRemark(String sourceFieldRemark) {
		this.sourceFieldRemark = sourceFieldRemark;
	}

	@Column(name="source_field_remark", nullable=true, length=1000)	
	public String getSourceFieldRemark() {
		return sourceFieldRemark;
	}

	public void setTargetFieldName(String targetFieldName) {
		this.targetFieldName = targetFieldName;
	}

	@Column(name="target_field_name", nullable=true, length=50)	
	public String getTargetFieldName() {
		return targetFieldName;
	}

	public void setTargetFieldRemark(String targetFieldRemark) {
		this.targetFieldRemark = targetFieldRemark;
	}

	@Column(name="target_field_remark", nullable=true, length=1000)	
	public String getTargetFieldRemark() {
		return targetFieldRemark;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name="business_type", nullable=false, length=2)	
	public String getBusinessType() {
		return businessType;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable")
	public String getIsEnable() {
		return this.isEnable;
	}

}
