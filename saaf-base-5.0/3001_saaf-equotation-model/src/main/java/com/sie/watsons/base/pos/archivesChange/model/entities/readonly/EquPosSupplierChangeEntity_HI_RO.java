package com.sie.watsons.base.pos.archivesChange.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**   EquPosSupplierChangeEntity_HI_RO.QUERY_SQL
 * EquPosSupplierChangeEntity_HI_RO Entity Object
 * Tue Sep 24 08:55:36 CST 2019  Auto Generate
 */

public class EquPosSupplierChangeEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	//查询供应商档案变更单据
	public  static  final String  QUERY_SQL =
			"SELECT sc.change_id          changeId\n" +
					"      ,sc.change_number      changeNumber\n" +
					"      ,sc.status             status\n" +
					"      ,sc.supplier_id        supplierId\n" +
					"      ,sc.dept_code          deptCode\n" +
					"      ,sc.change_desc        changeDesc\n" +
					"      ,sc.change_file_id     changeFileId\n" +
					"      ,sc.change_file_name   changeFileName\n" +
					"      ,sc.change_file_path   changeFilePath\n" +
					"      ,sc.reject_reson       rejectReson\n" +
					"      ,sc.version_num        versionNum\n" +
					"      ,sc.creation_date      creationDate\n" +
					"      ,sc.created_by         createdBy\n" +
					"      ,sc.last_updated_by    lastUpdatedBy\n" +
					"      ,sc.last_update_date   lastUpdateDate\n" +
					"      ,sc.last_update_login  lastUpdateLogin\n" +
					"      ,sc.attribute_category attributeCategory\n" +
					"      ,sc.attribute1\n" +
					"      ,sc.attribute2\n" +
					"      ,sc.attribute3\n" +
					"      ,sc.attribute4\n" +
					"      ,sc.attribute5\n" +
					"      ,sc.attribute6\n" +
					"      ,sc.attribute7\n" +
					"      ,sc.attribute8\n" +
					"      ,sc.attribute9\n" +
					"      ,sc.attribute10\n" +
					"      ,sc.qa_approval_flag qaApprovalFlag\n" +
					"      ,psi.supplier_number   supplierNumber\n" +
					"      ,psi.supplier_name supplierName\n" +
					"      ,psc.license_num       licenseNum\n" +
					"      ,d.supplier_status     supplierStatus\n" +
					"FROM   equ_pos_supplier_change      sc\n" +
					"      ,equ_pos_supplier_info        psi\n" +
					"      ,equ_pos_supplier_credentials psc\n" +
					"      ,equ_pos_supp_info_with_dept  d\n" +
					"WHERE  sc.supplier_id = psi.supplier_id(+)\n" +
					"AND    sc.supplier_id = psc.supplier_id(+)\n" +
					"AND    sc.dept_code = psc.dept_code(+)\n" +
					"AND    sc.supplier_id = d.supplier_id(+)\n" +
					"AND    sc.dept_code = d.dept_code(+)";

	public  static  final String  QUERY_SQL2 = "SELECT sc.change_id          changeId\n" +
			"      ,sc.change_number      changeNumber\n" +
			"      ,sc.status             status\n" +
			"      ,sc.supplier_id        supplierId\n" +
			"      ,sc.dept_code          deptCode\n" +
			"      ,sc.change_desc        changeDesc\n" +
			"      ,sc.change_file_id     changeFileId\n" +
			"      ,sc.change_file_name   changeFileName\n" +
			"      ,sc.change_file_path   changeFilePath\n" +
			"      ,sc.reject_reson       rejectReson\n" +
			"      ,sc.version_num        versionNum\n" +
			"      ,sc.creation_date      creationDate\n" +
			"      ,sc.created_by         createdBy\n" +
			"      ,sc.last_updated_by    lastUpdatedBy\n" +
			"      ,sc.last_update_date   lastUpdateDate\n" +
			"      ,sc.last_update_login  lastUpdateLogin\n" +
			"      ,sc.attribute_category attributeCategory\n" +
			"      ,sc.attribute1\n" +
			"      ,sc.attribute2\n" +
			"      ,sc.attribute3\n" +
			"      ,sc.attribute4\n" +
			"      ,sc.attribute5\n" +
			"      ,sc.attribute6\n" +
			"      ,sc.attribute7\n" +
			"      ,sc.attribute8\n" +
			"      ,sc.attribute9\n" +
			"      ,sc.attribute10\n" +
			"      ,sc.qa_approval_flag qaApprovalFlag\n" +
			"      ,psi.supplier_number   supplierNumber\n" +
			"      ,psi.supplier_name supplierName\n" +
			"      ,psc.license_num       licenseNum\n" +
			"      ,d.supplier_status     supplierStatus\n" +
			"FROM   equ_pos_supplier_change      sc\n" +
			"      ,equ_pos_supplier_info        psi\n" +
			"      ,equ_pos_supplier_credentials psc\n" +
			"      ,equ_pos_supp_info_with_dept  d\n" +
			"WHERE  sc.supplier_id = psi.supplier_id(+)\n" +
			"AND    psi.supplier_id = psc.supplier_id(+)\n" +
			"AND    psi.supplier_id = d.supplier_id(+)\n" +
			"AND    d.dept_code <> '0E'\n" +
			"AND    psc.dept_code <> '0E' ";

	private Integer changeId;
    private String changeNumber;
    private String status;
    private Integer supplierId;
    private String deptCode;
    private String changeDesc;
    private Integer changeFileId;
    private String changeFileName;
    private String changeFilePath;
    private String rejectReson;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String qaApprovalFlag;
    private Integer operatorUserId;

    private String supplierNumber;
    private String supplierName;
    private String licenseNum;
    private String supplierStatus;
    private String statusMeaning;
    private String supplierStatusMeaning;
    private String userFullName;
    private String procInstId;

	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}

	
	public Integer getChangeId() {
		return changeId;
	}

	public void setChangeNumber(String changeNumber) {
		this.changeNumber = changeNumber;
	}

	
	public String getChangeNumber() {
		return changeNumber;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	
	public String getChangeDesc() {
		return changeDesc;
	}

	public void setChangeFileId(Integer changeFileId) {
		this.changeFileId = changeFileId;
	}

	
	public Integer getChangeFileId() {
		return changeFileId;
	}

	public void setChangeFileName(String changeFileName) {
		this.changeFileName = changeFileName;
	}

	
	public String getChangeFileName() {
		return changeFileName;
	}

	public void setChangeFilePath(String changeFilePath) {
		this.changeFilePath = changeFilePath;
	}

	
	public String getChangeFilePath() {
		return changeFilePath;
	}

	public void setRejectReson(String rejectReson) {
		this.rejectReson = rejectReson;
	}

	
	public String getRejectReson() {
		return rejectReson;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	
	public String getAttribute10() {
		return attribute10;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public String getStatusMeaning() {
		return statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	public String getSupplierStatusMeaning() {
		return supplierStatusMeaning;
	}

	public void setSupplierStatusMeaning(String supplierStatusMeaning) {
		this.supplierStatusMeaning = supplierStatusMeaning;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getQaApprovalFlag() {
		return qaApprovalFlag;
	}

	public void setQaApprovalFlag(String qaApprovalFlag) {
		this.qaApprovalFlag = qaApprovalFlag;
	}
}
