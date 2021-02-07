package com.sie.watsons.base.pos.supplierinfo.model.entities.readonly;

import javax.persistence.Version;
import java.sql.Clob;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosCredentialsAttachmentEntity_HI_RO Entity Object
 * Thu Sep 12 17:25:51 CST 2019  Auto Generate
 */

public class EquPosCredentialsAttachmentEntity_HI_RO {
	//查询供应商资质文件信息
	public  static  final String  QUERY_SQL =
			"SELECT t.attachment_id attachmentId\n" +
					"      ,t.supplier_id supplierId\n" +
					"      ,t.file_id fileId\n" +
					"      ,t.description\n" +
					"      ,t.invalid_date invalidDate\n" +
					"      ,t.file_name fileName\n" +
					"      ,t.attachment_name attachmentName\n" +
					"      ,t.file_path filePath\n" +
					"      ,t.deptment_code deptmentCode\n" +
					"      ,t.is_product_factory isProductFactory\n" +
					"      ,t.fix_flag fixFlag\n" +
					"      ,t.file_type fileType\n" +
					"      ,t.version_num versionNum\n" +
					"      ,t.creation_date creationDate\n" +
					"      ,t.created_by createdBy\n" +
					"      ,t.last_updated_by lastUpdatedBy\n" +
					"      ,t.last_update_date lastUpdateDate\n" +
					"      ,t.last_update_login lastUpdateLogin\n" +
					"      ,t.attribute_category attributeCategory\n" +
					"      ,t.attribute1\n" +
					"      ,t.attribute2\n" +
					"      ,t.attribute3\n" +
					"      ,t.attribute4\n" +
					"      ,t.attribute5\n" +
					"      ,t.attribute6\n" +
					"      ,t.attribute7\n" +
					"      ,t.attribute8\n" +
					"      ,t.attribute9\n" +
					"      ,t.attribute10\n" +
					"      ,t.supplier_id \"index\"\n" +
					"FROM   equ_pos_credentials_attachment t\n" +
					"WHERE t.supplier_address_id is null ";

	//查询供应商经营资质文件信息
	public  static  final String  QUERY_SQL_OPERATIONAL =
			"SELECT t.attachment_id attachmentId\n" +
					"      ,t.supplier_id supplierId\n" +
					"      ,t.file_id fileId\n" +
					"      ,t.description\n" +
					"      ,t.invalid_date invalidDate\n" +
					"      ,t.file_name fileName\n" +
					"      ,t.attachment_name attachmentName\n" +
					"      ,t.file_path filePath\n" +
					"      ,t.deptment_code deptmentCode\n" +
					"      ,t.is_product_factory isProductFactory\n" +
					"      ,t.fix_flag fixFlag\n" +
					"      ,t.supplier_address_id supplierAddressId\n" +
					"      ,t.file_type fileType\n" +
					"      ,t.version_num versionNum\n" +
					"      ,t.creation_date creationDate\n" +
					"      ,t.created_by createdBy\n" +
					"      ,t.last_updated_by lastUpdatedBy\n" +
					"      ,t.last_update_date lastUpdateDate\n" +
					"      ,t.last_update_login lastUpdateLogin\n" +
					"      ,t.attribute_category attributeCategory\n" +
					"      ,t.attribute1\n" +
					"      ,t.attribute2\n" +
					"      ,t.attribute3\n" +
					"      ,t.attribute4\n" +
					"      ,t.attribute5\n" +
					"      ,t.attribute6\n" +
					"      ,t.attribute7\n" +
					"      ,t.attribute8\n" +
					"      ,t.attribute9\n" +
					"      ,t.attribute10\n" +
					"      ,t.supplier_id \"index\"\n" +
					"FROM   equ_pos_credentials_attachment t\n" +
					"WHERE t.supplier_address_id is not null ";
	public static void main(String[] args) {
		System.out.println(QUERY_SQL_CREDENTIAL_ATTACHMENT_REPORT_FORM);
	}
	public  static  final String  QUERY_SQL_CREDENTIAL_ATTACHMENT_REPORT_FORM = "SELECT t.supplier_id             supplierId\n" +
			"      ,t.supplier_number         supplierNumber\n" +
			"      ,t.supplier_name           supplierName\n" +
			"      ,t.supplier_short_name     supplierShortName\n" +
			"      ,t.supplier_in_charge_name supplierInChargeName\n" +
			"      ,t.supplier_status         supplierStatus\n" +
			"      ,t.supplier_type           supplierType\n" +
			"      ,t.country\n" +
			"      ,t.major_customer          majorCustomer\n" +
			"      ,t.company_description     companyDescription\n" +
			"      ,t.license_num             licenseNum\n" +
			"      ,t.category_count          categoryCount\n" +
			"      ,t.expire_days             expireDays\n" +
			"      ,t.attachment_name               fileName\n" +
			"      ,t.description\n" +
			"      ,t.invalid_date            invalidDate\n" +
			"FROM   (SELECT psi.supplier_id\n" +
			"              ,psi.supplier_number\n" +
			"              ,psi.supplier_name\n" +
			"              ,psi.supplier_short_name\n" +
			"              ,d.supplier_in_charge_name\n" +
			"              ,d.supplier_status\n" +
			"              ,d.supplier_type\n" +
			"              ,psi.country\n" +
			"              ,d.major_customer\n" +
			"              ,d.company_description\n" +
			"              ,sc.license_num\n" +
			"              ,(select count(1)\n" +
			"                from   equ_pos_supplier_product_cat t\n" +
			"                where  t.supplier_id = psi.supplier_id\n" +
			"                and    t.dept_code = d.dept_code\n" +
			"                and    t.category_id = :serviceScope) category_count\n" +
			"              ,(select nvl(MIN(TRUNC(nvl(ca.invalid_date, sysdate)) -\n" +
			"                               TRUNC(SYSDATE)),\n" +
			"                           9999999999)\n" +
			"                from   equ_pos_credentials_attachment ca\n" +
			"                where  ca.supplier_id = psi.supplier_id\n" +
			"                and    ca.deptment_code <> '0E'\n" +
			"                and    ca.is_product_factory = 'N'\n" +
			"                AND    ca.file_type is null\n" +
			"                and    ca.invalid_date >= sysdate) expire_days\n" +
			"              ,spc.attachment_name\n" +
			"              ,spc.description\n" +
			"              ,spc.invalid_date\n" +
			"        FROM   equ_pos_supplier_info        psi\n" +
			"              ,equ_pos_supp_info_with_dept  d\n" +
			"              ,equ_pos_supplier_credentials sc\n" +
			"              ,equ_pos_credentials_attachment    spc\n" +
			"        WHERE  psi.supplier_id = d.supplier_id(+)\n" +
			"        AND    psi.supplier_id = sc.supplier_id(+)\n" +
			"        and    psi.supplier_id = spc.supplier_id(+)\n" +
			"        AND    d.dept_code <> '0E'\n" +
			"        AND    sc.dept_code(+) <> '0E'\n" +
			"        and    spc.deptment_code(+) <> '0E'\n" +
			"        and    spc.is_product_factory = 'N') t\n" +
			"where  1 = 1 ";

	private Integer attachmentId;
    private Integer supplierId;
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
    private Integer fileId;
    private String description;
    @JSONField(format="yyyy-MM-dd")
    private Date invalidDate;
    private String fileName;
    private String attachmentName;
    private String filePath;
    private String deptmentCode;
    private String isProductFactory;
    private String fixFlag;
    private Integer supplierAddressId;
    private String fileType;
    private Integer index;
    private Integer operatorUserId;

	private String supplierNumber;
	private String supplierName;
	private String supplierShortName;
	private String supplierInChargeName;
	private String supplierStatus;
	private String supplierType;
	private String country;
	private String majorCustomer;
	private Clob companyDescription;

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	
	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
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

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	
	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getFilePath() {
		return filePath;
	}

	public void setDeptmentCode(String deptmentCode) {
		this.deptmentCode = deptmentCode;
	}

	
	public String getDeptmentCode() {
		return deptmentCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getIsProductFactory() {
		return isProductFactory;
	}

	public void setIsProductFactory(String isProductFactory) {
		this.isProductFactory = isProductFactory;
	}

	public String getFixFlag() {
		return fixFlag;
	}

	public void setFixFlag(String fixFlag) {
		this.fixFlag = fixFlag;
	}

	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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

	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}

	public String getSupplierInChargeName() {
		return supplierInChargeName;
	}

	public void setSupplierInChargeName(String supplierInChargeName) {
		this.supplierInChargeName = supplierInChargeName;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMajorCustomer() {
		return majorCustomer;
	}

	public void setMajorCustomer(String majorCustomer) {
		this.majorCustomer = majorCustomer;
	}

	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}
}
