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
 * PlmLocationListEntity_HI Entity Object Tue Oct 22 09:33:19 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_LOCATION_LIST")
public class PlmLocationListEntity_HI {
	private Integer plmLocationListId;
	private String descName;
	private String code;
	private String creatorName;
	private String creatorCode;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private String warehouseCode; // 仓库编号

	public void setPlmLocationListId(Integer plmLocationListId) {
		this.plmLocationListId = plmLocationListId;
	}

	@Id
	@SequenceGenerator(name = "plmLocationListEntity_HISeqGener", sequenceName = "SEQ_PLM_LOCATION_LIST", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "plmLocationListEntity_HISeqGener", strategy = GenerationType.SEQUENCE)
	@Column(name = "plm_location_list_id", nullable = false, length = 22)
	public Integer getPlmLocationListId() {
		return plmLocationListId;
	}

	public void setDescName(String descName) {
		this.descName = descName;
	}

	@Column(name = "warehouse_code", nullable = true, length = 255)
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column(name = "desc_name", nullable = true, length = 800)
	public String getDescName() {
		return descName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "code", nullable = true, length = 50)
	public String getCode() {
		return code;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "creator_name", nullable = true, length = 50)
	public String getCreatorName() {
		return creatorName;
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

	@Column(name = "creator_code", nullable = true, length = 20)
	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
