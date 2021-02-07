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
 * PlmProductModifyCheckEntity_HI Entity Object Tue Mar 31 17:24:42 CST 2020
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_MODIFY_CHECK")
public class PlmProductModifyCheckEntity_HI {
	private Integer checkId;
	private String rmsCode;
	private Integer productHeadId;
	private String tableName;
	private Integer lineId;
	private String columnName;
	private String oldValue;
	private String newValue;
	private String status;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdatedBy;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;
	private Integer ecoId;
	private String message;
	private String columnDesc;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date  effectiveDate;
	private String detailInfo;
	@Column(name = "effective_date", nullable = true, length = 7)
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	@Column(name = "detail_info", nullable = true, length = 255)
	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	@Column(name = "eco_id", nullable = false, length = 22)
	public Integer getEcoId() {		return ecoId;	}

	public void setEcoId(Integer ecoId) {		this.ecoId = ecoId;	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_MODIFYCHECK", sequenceName = "SEQ_PLM_PRODUCT_MODIFYCHECK", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_MODIFYCHECK", strategy = GenerationType.SEQUENCE)
	@Column(name = "check_id", nullable = false, length = 22)
	public Integer getCheckId() {
		return checkId;
	}

	@Column(name = "column_Desc", nullable = true, length = 255)
	public String getColumnDesc() {return columnDesc;}

	public void setColumnDesc(String columnDesc) {this.columnDesc = columnDesc;}

	@Column(name = "message", nullable = true, length = 500)
	public String getMessage() {return message;	}

	public void setMessage(String message) {this.message = message;}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name = "rms_code", nullable = true, length = 255)
	public String getRmsCode() {
		return rmsCode;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "table_name", nullable = true, length = 255)
	public String getTableName() {
		return tableName;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	@Column(name = "line_id", nullable = true, length = 22)
	public Integer getLineId() {
		return lineId;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "column_name", nullable = true, length = 255)
	public String getColumnName() {
		return columnName;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	@Column(name = "old_value", nullable = true, length = 255)
	public String getOldValue() {
		return oldValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Column(name = "new_value", nullable = true, length = 255)
	public String getNewValue() {
		return newValue;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status", nullable = true, length = 20)
	public String getStatus() {
		return status;
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

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
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
