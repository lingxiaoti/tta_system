package com.sie.watsons.base.pos.recover.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Clob;
import java.util.Date;

/**   EquPosSupplierRecoverEntity_HI_RO.QUERY_SCENE_SQL
 * EquPosSupplierRecoverEntity_HI_RO Entity Object
 * Thu Sep 05 17:41:22 CST 2019  Auto Generate
 */

public class EquPosSupplierRecoverEntity_HI_RO {
    private String supRecoverReason;
    private Integer fileId;
    private String remark;
    private String rejectReason;
	private String  department;
    private Integer versionNum;
	private String piSupplierStatus;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
	private String filePath;
	private String fileName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
	private String supplierDeptStatusName;
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
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private Integer supRecoverId;
    private Integer supplierId;
    private String supplierNumber;
    private String supRecoverCode;
    private String supRecoverStatus;
	private String userName;
    private String supRecoverType;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date supRecoverDate;
	private String supRecoverStatusName;
	private String supplierStatusName;
    private Integer operatorUserId;
	private String supplierName; //供应商名称
	private String supplierShortName; //供应商简称
	private String supplierType; //供应商类型
	private String supplierStatus; //供应商状态
	private String homeUrl; //企业网址
	private String companyPhone; //公司电话
	private String companyFax; //公司传真
	private Clob companyDescription; //公司简介
	private Integer supplierFileId; //公司简介附件
	private String blacklistFlag; //黑名单供应商(Y/N)
    private String procInstId;

	public void setSupRecoverReason(String supRecoverReason) {
		this.supRecoverReason = supRecoverReason;
	}

	
	public String getSupRecoverReason() {
		return supRecoverReason;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getPiSupplierStatus() {
		return piSupplierStatus;
	}

	public void setPiSupplierStatus(String piSupplierStatus) {
		this.piSupplierStatus = piSupplierStatus;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSupplierDeptStatusName() {
		return supplierDeptStatusName;
	}

	public void setSupplierDeptStatusName(String supplierDeptStatusName) {
		this.supplierDeptStatusName = supplierDeptStatusName;
	}

	public String getSupRecoverStatusName() {
		return supRecoverStatusName;
	}

	public void setSupRecoverStatusName(String supRecoverStatusName) {
		this.supRecoverStatusName = supRecoverStatusName;
	}

	public String getSupplierStatusName() {
		return supplierStatusName;
	}

	public void setSupplierStatusName(String supplierStatusName) {
		this.supplierStatusName = supplierStatusName;
	}

	public String getRejectReason() {
		return rejectReason;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}

	public Integer getSupplierFileId() {
		return supplierFileId;
	}

	public void setSupplierFileId(Integer supplierFileId) {
		this.supplierFileId = supplierFileId;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBlacklistFlag() {
		return blacklistFlag;
	}

	public void setBlacklistFlag(String blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	
	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	
	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	
	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	
	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	
	public String getAttribute15() {
		return attribute15;
	}

	public void setSupRecoverId(Integer supRecoverId) {
		this.supRecoverId = supRecoverId;
	}

	
	public Integer getSupRecoverId() {
		return supRecoverId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	
	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupRecoverCode(String supRecoverCode) {
		this.supRecoverCode = supRecoverCode;
	}

	
	public String getSupRecoverCode() {
		return supRecoverCode;
	}

	public void setSupRecoverStatus(String supRecoverStatus) {
		this.supRecoverStatus = supRecoverStatus;
	}

	
	public String getSupRecoverStatus() {
		return supRecoverStatus;
	}

	public void setSupRecoverType(String supRecoverType) {
		this.supRecoverType = supRecoverType;
	}

	
	public String getSupRecoverType() {
		return supRecoverType;
	}

	public void setSupRecoverDate(Date supRecoverDate) {
		this.supRecoverDate = supRecoverDate;
	}

	
	public Date getSupRecoverDate() {
		return supRecoverDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

//	EquPosSupplierRecoverEntity_HI_RO.QUERY_SCENE_SQL
    public static String QUERY_SCENE_SQL="SELECT t.sup_recover_id suprecoverId\n" +
		"      ,t.supplier_id supplierId\n" +
		"      ,t.supplier_number supplierNumber\n" +
		"      ,t.sup_recover_code supRecoverCode\n" +
		"      ,t.sup_recover_status supRecoverStatus\n" +
		"      ,t.supplier_name supplierName\n" +
		"      ,t.supplier_status supplierStatus\n" +
		"      ,t.department department\n" +
		"      ,pi.supplier_status piSupplierStatus\n" +
		"      ,t.sup_recover_type suprecoverType\n" +
		"      ,t.sup_recover_date suprecoverDate\n" +
		"      ,t.sup_recover_reason suprecoverReason\n" +
		"      ,t.file_id fileId\n" +
		"      ,t.file_name fileName\n" +
		"      ,t.file_path filePath\n" +
		"      ,t.remark remark \n" +
		"      ,t.reject_reason rejectReason\n" +
		"      ,t.creation_date creationDate\n" +
		"      ,t.created_by createdBy\n" +
		"      ,t.last_update_date lastUpdateDate\n" +
		"      ,t.last_updated_by lastUpdatedBy\n" +
		"FROM   EQU_POS_SUPPLIER_recover t\n" +
		"      ,equ_pos_supplier_info    pi \n" +
		"WHERE  t.supplier_id = pi.supplier_id(+) ";

	public static final String QUERY_SUPPLIER_SQL = "SELECT t.supplier_id supplierId,\n" +
			"       t.supplier_number supplierNumber,\n" +
			"       t.supplier_name supplierName,\n" +
			"       t.supplier_short_name supplierShortName,\n" +
			"       t.supplier_type supplierType,\n" +
			"       t.supplier_status supplierStatus\n" +
			"  FROM EQU_POS_SUPPLIER_INFO t\n" +
			" where 1 = 1\n";
}
