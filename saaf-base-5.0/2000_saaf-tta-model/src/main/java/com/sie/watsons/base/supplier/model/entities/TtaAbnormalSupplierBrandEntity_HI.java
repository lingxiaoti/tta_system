package com.sie.watsons.base.supplier.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldAttrIgnore;
import com.sie.saaf.common.bean.FieldDesc;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaAbnormalSupplierBrandEntity_HI Entity Object
 * Wed Mar 25 11:40:32 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_abnormal_supplier_brand")
public class TtaAbnormalSupplierBrandEntity_HI {
	@FieldDesc(isUpdateWhereKey = true)
    private Integer supplierBrandId;
	@FieldDesc(isUpdateWhereKey = false)
    private String supplierCode;
	@FieldDesc(isUpdateWhereKey = false)
    private String supplierName;
	@FieldDesc(isUpdateWhereKey = false)
    private String groupCode;
	@FieldDesc(isUpdateWhereKey = false)
    private String groupName;
	@FieldDesc(isUpdateWhereKey = false)
    private String deptCode;
	@FieldDesc(isUpdateWhereKey = false)
    private String deptName;
	@FieldDesc(isUpdateWhereKey = false)
    private String brandCode;
	@FieldDesc(isUpdateWhereKey = false)
    private String brandCn;
	@FieldDesc(isUpdateWhereKey = false)
    private String brandEn;
	@FieldDesc(isUpdateWhereKey = false)
    private String activeStatus;
	@FieldDesc(isUpdateWhereKey = false)
    private String buScorecard;
	@FieldDesc(isUpdateWhereKey = false)
    private String winTopSupplier;
	@FieldDesc(isUpdateWhereKey = false)
    private String kbScorecard;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
	@FieldDesc(isUpdateWhereKey = false)
    private Date creationDate;
	@FieldDesc(isUpdateWhereKey = false)
    private Integer createdBy;
	@FieldDesc(isUpdateWhereKey = false)
    private Integer lastUpdatedBy;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
	@FieldDesc(isUpdateWhereKey = false)
    private Date lastUpdateDate;
	@FieldDesc(isUpdateWhereKey = false)
    private Integer lastUpdateLogin;
	@FieldDesc(isUpdateWhereKey = false)
    private Integer versionNum;
	@FieldDesc(isUpdateWhereKey = false)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date actualCallData;
	@FieldAttrIgnore
    private Integer operatorUserId;
	@FieldDesc(isUpdateWhereKey = false)
    private String batchNumber;
	@FieldDesc(isUpdateWhereKey = false)
    private String accountMonth;
	@FieldDesc(isUpdateWhereKey = false)
    private String year;
	@FieldDesc(isUpdateWhereKey = false)
	private String isCreate;//是否生成供应商品牌数据

	public void setSupplierBrandId(Integer supplierBrandId) {
		this.supplierBrandId = supplierBrandId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_ABNORMAL_SUPPLIER_BRAND", sequenceName = "SEQ_TTA_ABNORMAL_SUPPLIER_BRAND", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_ABNORMAL_SUPPLIER_BRAND", strategy = GenerationType.SEQUENCE)
	@Column(name="supplier_brand_id", nullable=false, length=22)	
	public Integer getSupplierBrandId() {
		return supplierBrandId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=100)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=300)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=50)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=150)	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=150)	
	public String getDeptName() {
		return deptName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=true, length=50)	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=300)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=230)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Column(name="active_status", nullable=true, length=3)	
	public String getActiveStatus() {
		return activeStatus;
	}

	public void setBuScorecard(String buScorecard) {
		this.buScorecard = buScorecard;
	}

	@Column(name="bu_scorecard", nullable=true, length=150)	
	public String getBuScorecard() {
		return buScorecard;
	}

	public void setWinTopSupplier(String winTopSupplier) {
		this.winTopSupplier = winTopSupplier;
	}

	@Column(name="win_top_supplier", nullable=true, length=150)	
	public String getWinTopSupplier() {
		return winTopSupplier;
	}

	public void setKbScorecard(String kbScorecard) {
		this.kbScorecard = kbScorecard;
	}

	@Column(name="kb_scorecard", nullable=true, length=150)	
	public String getKbScorecard() {
		return kbScorecard;
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

	public void setActualCallData(Date actualCallData) {
		this.actualCallData = actualCallData;
	}

	@Column(name="actual_call_data", nullable=true, length=7)	
	public Date getActualCallData() {
		return actualCallData;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name="batch_number", nullable=true, length=50)
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	@Column(name="account_month", nullable=true, length=30)
	public String getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}

	@Column(name="year", nullable=true, length=15)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name="is_create", nullable=true, length=15)
	public String getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
