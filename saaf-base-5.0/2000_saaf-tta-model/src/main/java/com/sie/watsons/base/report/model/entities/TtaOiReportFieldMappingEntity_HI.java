package com.sie.watsons.base.report.model.entities;

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
 * TtaOiReportFieldMappingEntity_HI Entity Object
 * Tue Apr 14 09:36:22 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_oi_report_field_mapping")
public class TtaOiReportFieldMappingEntity_HI {
    private Integer fieldId;
    private Integer tradeYear;
    private String targetFieldName;
    private String targetFieldRemark;
    private String businessType;
    private String dataType;
    private String isEnable;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer reportType;
    private Integer operatorUserId;

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_OI_REPORT_FIELD_MAPPING", sequenceName = "SEQ_TTA_OI_REPORT_FIELD_MAPPING", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_OI_REPORT_FIELD_MAPPING", strategy = GenerationType.SEQUENCE)
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

	@Column(name="business_type", nullable=false, length=10)	
	public String getBusinessType() {
		return businessType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name="data_type", nullable=true, length=10)	
	public String getDataType() {
		return dataType;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=true, length=2)	
	public String getIsEnable() {
		return isEnable;
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

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	@Column(name="report_type", nullable=true, length=22)	
	public Integer getReportType() {
		return reportType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
