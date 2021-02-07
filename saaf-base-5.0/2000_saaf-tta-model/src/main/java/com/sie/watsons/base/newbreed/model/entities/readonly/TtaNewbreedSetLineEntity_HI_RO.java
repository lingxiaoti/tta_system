package com.sie.watsons.base.newbreed.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaNewbreedSetLineEntity_HI_RO Entity Object
 * Wed Jun 05 09:59:11 CST 2019  Auto Generate
 */

public class TtaNewbreedSetLineEntity_HI_RO {

	public static final String QUERY_FIND =  "select tnb.newbreed_set_line_id,\n" +
			"       tnb.range range,\n" +
			"       tnb.unit unit,\n" +
			"       tnb.standard standard,\n" +
			"       tnb.is_enable isEnable,\n" +
			"       tnb.creation_date creationDate,\n" +
			"       tnb.created_by createdBy,\n" +
			"       tnb.last_update_date lastUpdateDate,\n" +
			"       tnb.last_updated_by lastUpdatedBy,\n" +
			"       tnb.last_update_login lastUpdateLogin,\n" +
			"       tnb.delete_flag deleteFlag,\n" +
			"       tnb.version_num versionNum,\n" +
			"       tnb.newbreed_set_id newbreedSetId,\n" +
			"       tnb.purchase_type purchaseType,\n" +
			"       tnb.store_type storeType,\n" +
			"       tnb.dept_code deptCode,\n" +
			"       tnb.dept_name deptName from  tta_newbreed_set_line tnb where 1=1 ";
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

	
	public Integer getNewbreedSetLineId() {
		return newbreedSetLineId;
	}

	public void setRange(String range) {
		this.range = range;
	}

	
	public String getRange() {
		return range;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}

	
	public BigDecimal getStandard() {
		return standard;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setNewbreedSetId(Integer newbreedSetId) {
		this.newbreedSetId = newbreedSetId;
	}

	
	public Integer getNewbreedSetId() {
		return newbreedSetId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
}
