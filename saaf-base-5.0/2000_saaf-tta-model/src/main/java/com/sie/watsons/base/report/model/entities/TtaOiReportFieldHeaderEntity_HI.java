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
 * TtaOiReportFieldHeaderEntity_HI Entity Object
 * Tue Apr 14 09:36:11 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_OI_REPORT_FIELD_HEADER")
public class TtaOiReportFieldHeaderEntity_HI {
    private Integer fieldId;
    private String tradeMonth;
    private String businessType;
    private String businessName;
    private String dateType;
    private String dataName;
    private Integer orderNo;
    private String isEnable;
    private Integer queryType;
    private Integer reportType;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_OI_REPORT_FIELD_HEADER", sequenceName = "SEQ_TTA_OI_REPORT_FIELD_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_OI_REPORT_FIELD_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="field_id", nullable=true, length=22)	
	public Integer getFieldId() {
		return fieldId;
	}

	public void setTradeMonth(String tradeMonth) {
		this.tradeMonth = tradeMonth;
	}

	@Column(name="trade_month", nullable=true, length=6)	
	public String getTradeMonth() {
		return tradeMonth;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name="business_type", nullable=true, length=8)	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(name="business_name", nullable=true, length=800)	
	public String getBusinessName() {
		return businessName;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Column(name="date_type", nullable=false, length=8)	
	public String getDateType() {
		return dateType;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	@Column(name="data_name", nullable=true, length=800)	
	public String getDataName() {
		return dataName;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=22)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=true, length=2)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	@Column(name="query_type", nullable=true, length=22)	
	public Integer getQueryType() {
		return queryType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	@Column(name="report_type", nullable=true, length=22)	
	public Integer getReportType() {
		return reportType;
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
}
