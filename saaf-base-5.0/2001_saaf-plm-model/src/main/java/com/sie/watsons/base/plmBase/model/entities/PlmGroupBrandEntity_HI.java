package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmGroupBrandEntity_HI Entity Object Thu Oct 31 15:07:24 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_GROUP_BRAND")
public class PlmGroupBrandEntity_HI {
	private Integer plmGroupBrandId;
	private String plmGroupBrandName;
	private String remarks;
	private String billStatus;
	private String billStatusName;
	private String creator;
	private Integer ta;
	private String taName;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;

	private String processReject;
	private String processUser;
	private String processIncident;

	@Column(name = "process_incident", nullable = true, length = 255)
	public String getProcessIncident() {
		return processIncident;
	}

	public void setProcessIncident(String processIncident) {
		this.processIncident = processIncident;
	}

	@Column(name = "process_user", nullable = true, length = 255)
	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	@Column(name = "process_reject", nullable = true, length = 255)
	public String getProcessReject() {
		return processReject;
	}

	public void setProcessReject(String processReject) {
		this.processReject = processReject;
	}

	public void setPlmGroupBrandId(Integer plmGroupBrandId) {
		this.plmGroupBrandId = plmGroupBrandId;
	}

	@Id
	@SequenceGenerator(name = "plmGroupBrandEntity_HISeqGener", sequenceName = "SEQ_PLM_GROUP_BRAND", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "plmGroupBrandEntity_HISeqGener", strategy = GenerationType.SEQUENCE)
	@Column(name = "plm_group_brand_id", nullable = false, length = 22)
	public Integer getPlmGroupBrandId() {
		return plmGroupBrandId;
	}

	public void setPlmGroupBrandName(String plmGroupBrandName) {
		this.plmGroupBrandName = plmGroupBrandName;
	}

	@Column(name = "plm_group_brand_name", nullable = true, length = 200)
	public String getPlmGroupBrandName() {
		return plmGroupBrandName;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "remarks", nullable = true, length = 400)
	public String getRemarks() {
		return remarks;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name = "bill_status", nullable = true, length = 20)
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	@Column(name = "bill_status_name", nullable = true, length = 50)
	public String getBillStatusName() {
		return billStatusName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "creator", nullable = true, length = 100)
	public String getCreator() {
		return creator;
	}

	public void setTa(Integer ta) {
		this.ta = ta;
	}

	@Column(name = "ta", nullable = true, length = 22)
	public Integer getTa() {
		return ta;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	@Column(name = "ta_name", nullable = true, length = 100)
	public String getTaName() {
		return taName;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
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
