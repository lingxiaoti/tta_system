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
 * PlmProductUpdatepropertisEntity_HI Entity Object Wed Sep 25 17:30:01 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_UPDATEPROPERTIS")
public class PlmProductUpdatepropertisEntity_HI {
	private Integer id;
	private String productNo;
	private String productName;
	private String deptName;
	private Integer deptId;
	private String updateName; // 更新字段中文名称
	private String oldValue;
	private String newValue;
	private String productHeadId;
	private String tablesName;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer businessId;
	private Integer operatorUserId;
	private String columnNames;// 更新字段数据库名称
	private String supplierId; // 供应商编号 //用于修改商品包装信息
	private String supplierName; // 供应商名称
	private String flag;
	private String createdstr;

	private String dataType; // 修改类型
	private String remake; // 同步备注

	private String orderStatus; // 状态
	private String orderStatusname; // 状态名称
	private String lastUpdateId;
	private String rmsReturnstatus;
	private String rmsReturnmark;
	private String billNo;
	private String isRmsFlag;

	@Column(name = "is_rms_flag", nullable = true, length = 1)
	public String getIsRmsFlag() {		return isRmsFlag;	}

	public void setIsRmsFlag(String isRmsFlag) {		this.isRmsFlag = isRmsFlag;	}

	@Column(name = "rms_returnstatus", nullable = true, length = 255)
	public String getRmsReturnstatus() {
		return rmsReturnstatus;
	}

	public void setRmsReturnstatus(String rmsReturnstatus) {
		this.rmsReturnstatus = rmsReturnstatus;
	}

	@Column(name = "rms_returnmark", nullable = true, length = 255)
	public String getRmsReturnmark() {
		return rmsReturnmark;
	}

	public void setRmsReturnmark(String rmsReturnmark) {
		this.rmsReturnmark = rmsReturnmark;
	}

	@Column(name = "bill_no", nullable = true, length = 255)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Column(name = "last_update_id", nullable = true, length = 255)
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	@Column(name = "order_status", nullable = true, length = 50)
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "order_statusname", nullable = true, length = 50)
	public String getOrderStatusname() {
		return orderStatusname;
	}

	public void setOrderStatusname(String orderStatusname) {
		this.orderStatusname = orderStatusname;
	}

	@Column(name = "data_type", nullable = true, length = 25)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "remake", nullable = true, length = 255)
	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_UPDATEPRO", sequenceName = "SEQ_PLM_PRODUCT_UPDATEPRO", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_UPDATEPRO", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 22)
	public Integer getId() {
		return id;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	@Column(name = "product_no", nullable = true, length = 255)
	public String getProductNo() {
		return productNo;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_name", nullable = true, length = 255)
	public String getProductName() {
		return productName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "dept_name", nullable = true, length = 255)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Column(name = "dept_id", nullable = true, length = 22)
	public Integer getDeptId() {
		return deptId;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "update_name", nullable = true, length = 255)
	public String getUpdateName() {
		return updateName;
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

	public void setProductHeadId(String productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 255)
	public String getProductHeadId() {
		return productHeadId;
	}

	public void setTablesName(String tablesName) {
		this.tablesName = tablesName;
	}

	@Column(name = "tables_name", nullable = true, length = 255)
	public String getTablesName() {
		return tablesName;
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

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	@Column(name = "business_id", nullable = true, length = 22)
	public Integer getBusinessId() {
		return businessId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "column_names", nullable = true, length = 255)
	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	@Column(name = "supplier_id", nullable = true, length = 255)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "supplier_name", nullable = true, length = 255)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "flag", nullable = true, length = 255)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "createdstr", nullable = true, length = 255)
	public String getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

}
