package com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Clob;
import java.util.Date;

/**   EquPosBgSupplierEntity_HI_RO.QUERY_SQL
 * EquPosBgSupplierEntity_HI_RO Entity Object
 * Tue Sep 24 14:10:21 CST 2019  Auto Generate
 */

public class EquPosBgSupplierEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	//查询供应商基础信息
	public  static  final String  QUERY_SQL =
			"SELECT psi.supplier_id supplierId\n" +
					"      ,psi.supplier_number supplierNumber\n" +
					"      ,psi.supplier_name supplierName\n" +
					"      ,psi.supplier_short_name supplierShortName\n" +
					"      ,psi.home_url homeUrl\n" +
					"      ,psi.company_phone companyPhone\n" +
					"      ,psi.company_fax companyFax\n" +
					"      ,d.company_description companyDescription\n" +
					"      ,d.supplier_file_id supplierFileId\n" +
					"      ,d.file_name fileName\n" +
					"      ,d.file_path filePath\n" +
					"      ,psi.blacklist_flag blacklistFlag\n" +
					"      ,psi.country\n" +
					"      ,d.major_customer majorCustomer\n" +
					"      ,psi.login_account loginAccount\n" +
					"      ,d.cooperative_project cooperativeProject\n" +
					"      ,d.agent_level agentLevel\n" +
					"      ,psi.change_id changeId\n" +
					"      ,psi.source_id sourceId\n" +
					"      ,psi.version_num versionNum\n" +
					"      ,psi.creation_date creationDate\n" +
					"      ,psi.created_by createdBy\n" +
					"      ,psi.last_updated_by lastUpdatedBy\n" +
					"      ,psi.last_update_date lastUpdateDate\n" +
					"      ,psi.last_update_login lastUpdateLogin\n" +
					"      ,psi.attribute_category attributeCategory\n" +
					"      ,psi.attribute1\n" +
					"      ,psi.attribute2\n" +
					"      ,psi.attribute3\n" +
					"      ,psi.attribute4\n" +
					"      ,psi.attribute5\n" +
					"      ,psi.attribute6\n" +
					"      ,psi.attribute7\n" +
					"      ,psi.attribute8\n" +
					"      ,psi.attribute9\n" +
					"      ,psi.attribute10 \n" +
//					"      ,psi.supplier_file_name fileName\n" +
//					"      ,psi.supplier_file_path filePath\n" +
					"      ,d.supplier_type supplierType\n" +
					"      ,d.dept_code deptCode\n" +
					"      ,d.supplier_status supplierStatus\n" +
					"      ,d.supplier_in_charge_number supplierInChargeNumber\n" +
					"      ,d.supplier_in_charge_name supplierInChargeName\n" +
					"      ,(SELECT COUNT(1)\n" +
					"        FROM   equ_pos_Bg_credential_attach a\n" +
					"        WHERE  a.supplier_id = psi.supplier_id\n" +
					"        AND    a.attachment_name = 'CSR报告'\n" +
					"        AND    a.invalid_date > SYSDATE\n" +
					"        AND    a.file_id IS NOT NULL) csrReportCount\n" +
					"FROM   equ_pos_Bg_supplier       psi \n" +
					"      ,equ_pos_Bg_supp_dept_info d\n" +
					"WHERE  psi.supplier_id = d.supplier_id(+)\n" +
					"AND    psi.change_id = d.change_id(+)" ;

	private Integer supplierId; //供应商ID
	private String supplierNumber; //供应商编码
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
	private String country; //国家
	private String majorCustomer; //主要客户
	private Integer operatorUserId;
	private String supplierTypeMeaning; //供应商类型中文
	private String supplierStatusMeaning; //供应商状态中文
	private String countryMeaning; //国家中文
	private String loginAccount;//登录账号
	private String cooperativeProject;//已合作项目
	private String fileName; //公司简介附件名称
	private String filePath;//公司简介附件路径
	private String deptCode;
	private String licenseNum;
	private String agentLevel;
	private Integer csrFileId; //CSR报告id
	private String csrFileName; //CSR报告名称
	private String csrFilePath; //CSR报告路径
	private Integer changeId;
	private Integer sourceId;
	private String supplierInChargeNumber;
	private String supplierInChargeName;

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

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}


	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}


	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}


	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}


	public String getHomeUrl() {
		return homeUrl;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}


	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}


	public String getCompanyFax() {
		return companyFax;
	}

	public void setSupplierFileId(Integer supplierFileId) {
		this.supplierFileId = supplierFileId;
	}


	public Integer getSupplierFileId() {
		return supplierFileId;
	}

	public void setBlacklistFlag(String blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
	}


	public String getBlacklistFlag() {
		return blacklistFlag;
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

	public void setCountry(String country) {
		this.country = country;
	}


	public String getCountry() {
		return country;
	}

	public void setMajorCustomer(String majorCustomer) {
		this.majorCustomer = majorCustomer;
	}


	public String getMajorCustomer() {
		return majorCustomer;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}


	public String getLoginAccount() {
		return loginAccount;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}


	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setCooperativeProject(String cooperativeProject) {
		this.cooperativeProject = cooperativeProject;
	}


	public String getCooperativeProject() {
		return cooperativeProject;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}


	public String getAgentLevel() {
		return agentLevel;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getSupplierTypeMeaning() {
		return supplierTypeMeaning;
	}

	public void setSupplierTypeMeaning(String supplierTypeMeaning) {
		this.supplierTypeMeaning = supplierTypeMeaning;
	}

	public String getSupplierStatusMeaning() {
		return supplierStatusMeaning;
	}

	public void setSupplierStatusMeaning(String supplierStatusMeaning) {
		this.supplierStatusMeaning = supplierStatusMeaning;
	}

	public String getCountryMeaning() {
		return countryMeaning;
	}

	public void setCountryMeaning(String countryMeaning) {
		this.countryMeaning = countryMeaning;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public Integer getCsrFileId() {
		return csrFileId;
	}

	public void setCsrFileId(Integer csrFileId) {
		this.csrFileId = csrFileId;
	}

	public String getCsrFileName() {
		return csrFileName;
	}

	public void setCsrFileName(String csrFileName) {
		this.csrFileName = csrFileName;
	}

	public String getCsrFilePath() {
		return csrFilePath;
	}

	public void setCsrFilePath(String csrFilePath) {
		this.csrFilePath = csrFilePath;
	}

	public Integer getChangeId() {
		return changeId;
	}

	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getSupplierInChargeNumber() {
		return supplierInChargeNumber;
	}

	public void setSupplierInChargeNumber(String supplierInChargeNumber) {
		this.supplierInChargeNumber = supplierInChargeNumber;
	}

	public String getSupplierInChargeName() {
		return supplierInChargeName;
	}

	public void setSupplierInChargeName(String supplierInChargeName) {
		this.supplierInChargeName = supplierInChargeName;
	}
}
