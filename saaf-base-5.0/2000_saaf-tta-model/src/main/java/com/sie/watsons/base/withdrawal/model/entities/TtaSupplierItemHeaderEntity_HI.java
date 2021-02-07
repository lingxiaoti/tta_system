package com.sie.watsons.base.withdrawal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSupplierItemHeaderEntity_HI Entity Object
 * Sat Jul 20 17:29:31 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_supplier_item_header")
public class TtaSupplierItemHeaderEntity_HI {
	private Integer supplierItemId;//主键
	private String billCode;//单据编号
	private String supplierCode;//供应商编号
	private String supplierName;//供应商名称
	private Integer supplierId;//供应商id
	private String billStatus;//单据状态
	private String splitCondition;//拆分条件
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date confirmDate;//确认日期(提交日期)
	@JSONField(format="yyyy-MM")
	private Date startDate;//生效日期(年月起)
	@JSONField(format="yyyy-MM")
	private Date endDate;//截止日期(年月至)
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String  isComplete;//purchase数据更新状态 0:未更新 1:更新

	private String userGroupCode;//用户group
	private String userGroupName;
	private Integer majorDeptId;
	private String majorDeptCode;
	private String majorDeptDesc;//用户主部门

	private String proposalCode;//proposal编号
	private String vendorNbr;//proposal供应商编号
	private String vendorName;//proposal供应商名称
	private String proposalYear;//proposal年度
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date obsoleteDate;
	private BigDecimal versionCode;

	public void setSupplierItemId(Integer supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	@Id
	@SequenceGenerator(name="seq_tta_supplier_item_header", sequenceName="seq_tta_supplier_item_header", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="seq_tta_supplier_item_header",strategy=GenerationType.SEQUENCE)
	@Column(name="supplier_item_id", nullable=false, length=22)
	public Integer getSupplierItemId() {
		return supplierItemId;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	@Column(name="bill_code", nullable=false, length=50)
	public String getBillCode() {
		return billCode;
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

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name="bill_status", nullable=false, length=10)
	public String getBillStatus() {
		return billStatus;
	}

	public void setSplitCondition(String splitCondition) {
		this.splitCondition = splitCondition;
	}

	@Column(name="split_condition", nullable=false, length=50)
	public String getSplitCondition() {
		return splitCondition;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name="confirm_date", nullable=true, length=7)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=true, length=7)
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=7)
	public Date getEndDate() {
		return endDate;
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


	@Column(name="supplier_id", nullable=true, length=22)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="is_complete", nullable=false, length=5)
	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	@Column(name="user_group_code", nullable=true, length=30)
	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	@Column(name="user_group_name", nullable=true, length=200)
	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	@Column(name="major_dept_id", nullable=false, length=22)
	public Integer getMajorDeptId() {
		return majorDeptId;
	}

	public void setMajorDeptId(Integer majorDeptId) {
		this.majorDeptId = majorDeptId;
	}

	@Column(name="major_dept_code", nullable=false, length=100)
	public String getMajorDeptCode() {
		return majorDeptCode;
	}

	public void setMajorDeptCode(String majorDeptCode) {
		this.majorDeptCode = majorDeptCode;
	}

	@Column(name="major_dept_desc", nullable=false, length=200)
	public String getMajorDeptDesc() {
		return majorDeptDesc;
	}

	public void setMajorDeptDesc(String majorDeptDesc) {
		this.majorDeptDesc = majorDeptDesc;
	}

	@Column(name="proposal_code", nullable=true, length=50)
	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	@Column(name="vendor_nbr", nullable=true, length=50)
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_name", nullable=true, length=150)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="proposal_year", nullable=true, length=30)
	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="obsolete_date", nullable=true, length=7)
	public Date getObsoleteDate() {
		return obsoleteDate;
	}

	public void setObsoleteDate(Date obsoleteDate) {
		this.obsoleteDate = obsoleteDate;
	}

	@Column(name="version_code", nullable=true, length=22)
	public BigDecimal getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(BigDecimal versionCode) {
		this.versionCode = versionCode;
	}
}
