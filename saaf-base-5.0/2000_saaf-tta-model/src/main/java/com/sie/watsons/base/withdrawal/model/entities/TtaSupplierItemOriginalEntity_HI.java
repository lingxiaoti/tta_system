package com.sie.watsons.base.withdrawal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSupplierItemOriginalEntity_HI Entity Object
 * Sat Jul 20 17:29:30 CST 2019  Auto Generate
 * 供应商物料原始记录表
 */
@Entity
@Table(name="tta_supplier_item_original")
public class TtaSupplierItemOriginalEntity_HI {
	private Integer supplierItemOriginalId;//主键
	private String supplierCode;//供应商编码
	private String supplierName;//供应商名称
	private String deptCode;//部门编码
	private String deptName;//部门名称
	private String brandCode;//品牌编码
	private String brandName;//品牌名称
	private String itemCode;//物料编码
	private String itemName;//物料名称
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setSupplierItemOriginalId(Integer supplierItemOriginalId) {
		this.supplierItemOriginalId = supplierItemOriginalId;
	}

	@Id
	@SequenceGenerator(name="seq_tta_supplier_item_original", sequenceName="seq_tta_supplier_item_original", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="seq_tta_supplier_item_original",strategy=GenerationType.SEQUENCE)
	@Column(name="supplier_item_original_id", nullable=false, length=22)
	public Integer getSupplierItemOriginalId() {
		return supplierItemOriginalId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=false, length=50)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=false, length=100)
	public String getSupplierName() {
		return supplierName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=false, length=50)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=100)
	public String getDeptName() {
		return deptName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=false, length=100)
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name="brand_name", nullable=true, length=100)
	public String getBrandName() {
		return brandName;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=false, length=50)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name="item_name", nullable=false, length=100)
	public String getItemName() {
		return itemName;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
