package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiReportFieldMappingEntity_HI_RO Entity Object
 * Tue Apr 14 09:36:22 CST 2020  Auto Generate
 */

public class TtaOiReportFieldMappingEntity_HI_RO {
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

	
	public Integer getFieldId() {
		return fieldId;
	}

	public void setTradeYear(Integer tradeYear) {
		this.tradeYear = tradeYear;
	}

	
	public Integer getTradeYear() {
		return tradeYear;
	}

	public void setTargetFieldName(String targetFieldName) {
		this.targetFieldName = targetFieldName;
	}

	
	public String getTargetFieldName() {
		return targetFieldName;
	}

	public void setTargetFieldRemark(String targetFieldRemark) {
		this.targetFieldRemark = targetFieldRemark;
	}

	
	public String getTargetFieldRemark() {
		return targetFieldRemark;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	
	public String getBusinessType() {
		return businessType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	
	public String getDataType() {
		return dataType;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	
	public Integer getReportType() {
		return reportType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
