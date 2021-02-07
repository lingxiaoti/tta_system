package com.sie.watsons.base.withdrawal.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSupplierItemHeaderEntity_HI_RO Entity Object
 * Sat Jul 20 17:29:31 CST 2019  Auto Generate
 */

public class TtaSupplierItemHeaderEntity_HI_RO {
	public static final String QUERY_SELECT_SQL = "select tsih.supplier_item_id  as supplierItemId,\n" +
			"       tsih.bill_code         billCode,\n" +
			"       tsih.supplier_code     supplierCode,\n" +
			"       tsih.supplier_name     supplierName,\n" +
			"       tsih.supplier_id     supplierId,\n" +
			"       tsih.bill_status       billStatus,\n" +
			"       lookup1.meaning        statusName,\n" +
			"       tsih.split_condition   splitCondition,\n" +
			"       tsih.confirm_date      confirmDate,\n" +
			"       tsih.start_date        startDate,\n" +
			"       tsih.end_date          endDate,\n" +
			"       tsih.version_num       versionNum,\n" +
			"       tsih.creation_date     creationDate,\n" +
			"       tsih.created_by        createdBy,\n" +
			"       tsih.last_updated_by   lastUpdatedBy,\n" +
			"       tsih.last_update_date  lastUpdateDate,\n" +
			"       tsih.last_update_login lastUpdateLogin,\n" +
			"       tsih.is_complete isComplete,\n" +
			"       tsih.proposal_code proposalCode,\n" +
			"       tsih.vendor_nbr vendorNbr,\n" +
			"       tsih.vendor_name vendorName,\n" +
			"       tsih.proposal_year proposalYear," +
			"       tsih.version_code versionCode," +
			"       bu.user_full_name      createByUser\n" +
				"  from tta_supplier_item_header tsih\n" +
			"  join base_users bu\n" +
			"    on tsih.created_by = bu.user_id\n" +
			"  left join (select lookup_type, lookup_code, meaning\n" +
			"               from base_lookup_values\n" +
			"              where lookup_type = 'SPLITMERGE_STATUS'\n" +
			"                and enabled_flag = 'Y'\n" +
			"                and delete_flag = 0\n" +
			"                and start_date_active < sysdate\n" +
			"                and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                and system_code = 'BASE') lookup1\n" +
			"    on tsih.bill_status = lookup1.lookup_code\n" +
			"  where 1=1 ";

	private Integer supplierItemId;
	private String billCode;
	private String supplierCode;
	private String supplierName;
	private Integer supplierId;
	private String billStatus;
	private String splitCondition;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date confirmDate;
	@JSONField(format="yyyy-MM")
	private Date startDate;
	@JSONField(format="yyyy-MM")
	private Date endDate;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String createByUser;//创建用户
	private String statusName;
	private String  isComplete;//purchase数据更新状态

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


	public Integer getSupplierItemId() {
		return supplierItemId;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}


	public String getBillCode() {
		return billCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}


	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	public String getSupplierName() {
		return supplierName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}


	public String getBillStatus() {
		return billStatus;
	}

	public void setSplitCondition(String splitCondition) {
		this.splitCondition = splitCondition;
	}


	public String getSplitCondition() {
		return splitCondition;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}


	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Date getEndDate() {
		return endDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}


	public Integer getVersionNum() {
		return versionNum;
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

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}


	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreateByUser() {
		return createByUser;
	}

	public void setCreateByUser(String createByUser) {
		this.createByUser = createByUser;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public static String getQuerySelectSql() {
		return QUERY_SELECT_SQL;
	}

	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public Integer getMajorDeptId() {
		return majorDeptId;
	}

	public void setMajorDeptId(Integer majorDeptId) {
		this.majorDeptId = majorDeptId;
	}

	public String getMajorDeptCode() {
		return majorDeptCode;
	}

	public void setMajorDeptCode(String majorDeptCode) {
		this.majorDeptCode = majorDeptCode;
	}

	public String getMajorDeptDesc() {
		return majorDeptDesc;
	}

	public void setMajorDeptDesc(String majorDeptDesc) {
		this.majorDeptDesc = majorDeptDesc;
	}

	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	public Date getObsoleteDate() {
		return obsoleteDate;
	}

	public void setObsoleteDate(Date obsoleteDate) {
		this.obsoleteDate = obsoleteDate;
	}

	public BigDecimal getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(BigDecimal versionCode) {
		this.versionCode = versionCode;
	}
}
