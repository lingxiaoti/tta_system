package com.sie.watsons.base.newbreed.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaNewbreedSetLineEntity_HI Entity Object
 * Wed Jun 05 09:59:11 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_NEWBREED_SET_LINE")
public class TtaNewbreedSetLineEntity_HI {
    private Integer newbreedSetLineId;
    private String range;
    private String unit;
    private BigDecimal standard;
    private String isEnable;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer lastUpdateLogin;
    private Integer deleteFlag;
    private Integer versionNum;
    private Integer newbreedSetId;
    private Integer operatorUserId;
	private String purchaseType;
	private String storeType;
	private String deptCode;
	private String deptName;

	public void setNewbreedSetLineId(Integer newbreedSetLineId) {
		this.newbreedSetLineId = newbreedSetLineId;
	}
	@Id
	@SequenceGenerator(name="SEQ_TTA_NEWBREED_SET_LINE", sequenceName="SEQ_TTA_NEWBREED_SET_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_NEWBREED_SET_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="newbreed_set_line_id", nullable=false, length=22)
	public Integer getNewbreedSetLineId() {
		return newbreedSetLineId;
	}

	public void setRange(String range) {
		this.range = range;
	}

	@Column(name="range", nullable=true, length=100)	
	public String getRange() {
		return range;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=100)	
	public String getUnit() {
		return unit;
	}

	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}

	@Column(name="standard", precision = 10, scale = 2)
	public BigDecimal getStandard() {
		return standard;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=true, length=2)	
	public String getIsEnable() {
		return isEnable;
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

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setNewbreedSetId(Integer newbreedSetId) {
		this.newbreedSetId = newbreedSetId;
	}

	@Column(name="newbreed_set_id", nullable=true, length=22)	
	public Integer getNewbreedSetId() {
		return newbreedSetId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="store_type", nullable=true, length=100)
	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name="purchase_type", nullable=true, length=100)
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	@Column(name="dept_code", nullable=false, length=50)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_name", nullable=false, length=200)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
