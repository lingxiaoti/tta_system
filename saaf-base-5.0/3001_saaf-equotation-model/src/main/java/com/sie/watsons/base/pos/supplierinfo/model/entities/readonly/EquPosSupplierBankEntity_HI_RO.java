package com.sie.watsons.base.pos.supplierinfo.model.entities.readonly;

import javax.persistence.Version;
import java.sql.Clob;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosSupplierBankEntity_HI_RO Entity Object
 * Thu Sep 05 16:52:22 CST 2019  Auto Generate
 */

public class EquPosSupplierBankEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	//查询供应商基础信息   EquPosSupplierBankEntity_HI_RO.QUERY_SQL
	public  static  final String  QUERY_SQL =
			"SELECT sb.bank_account_id bankAccountId\n" +
					"      ,sb.supplier_id supplierId\n" +
					"      ,sb.bank_account_number bankAccountNumber\n" +
					"      ,sb.bank_user_name      bankUserName\n" +
					"      ,sb.bank_name           bankName\n" +
					"      ,sb.bank_currency       bankCurrency\n" +
					"      ,sb.swift_code          swiftCode\n" +
					"      ,sb.iban_code           ibanCode\n" +
					"      ,sb.dept_code           deptCode\n" +
					"      ,sb.version_num         versionNum\n" +
					"      ,sb.creation_date       creationNate\n" +
					"      ,sb.created_by          createdBy\n" +
					"      ,sb.last_updated_by     lastUpdatedBy\n" +
					"      ,sb.last_update_date    lastUpdateDate\n" +
					"      ,sb.last_update_login   lastUpdateLogin\n" +
					"      ,sb.attribute_category  attributeCategory\n" +
					"      ,sb.attribute1\n" +
					"      ,sb.attribute2\n" +
					"      ,sb.attribute3\n" +
					"      ,sb.attribute4\n" +
					"      ,sb.attribute5\n" +
					"      ,sb.attribute6\n" +
					"      ,sb.attribute7\n" +
					"      ,sb.attribute8\n" +
					"      ,sb.attribute9\n" +
					"      ,sb.attribute10\n" +
					"FROM   equ_pos_supplier_bank sb\n" +
					"where 1 = 1";

	public  static  final String  SUPPLIER_BANK_REPORT_QUERY_SQL = "SELECT t.supplier_id             supplierId\n" +
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
			"      ,t.bank_user_name        bankUserName\n" +
			"      ,t.bank_account_number   bankAccountNumber\n" +
			"      ,t.bank_name             bankName\n" +
			"      ,t.bank_currency         bankCurrency\n" +
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
			"               ,spc.bank_user_name\n" +
			"               ,spc.bank_account_number\n" +
			"               ,spc.bank_name\n" +
			"               ,spc.bank_currency\n" +
			"        FROM   equ_pos_supplier_info        psi\n" +
			"              ,equ_pos_supp_info_with_dept  d\n" +
			"              ,equ_pos_supplier_credentials sc\n" +
			"              ,equ_pos_supplier_bank    spc\n" +
			"        WHERE  psi.supplier_id = d.supplier_id(+)\n" +
			"        AND    psi.supplier_id = sc.supplier_id(+)\n" +
			"        and    psi.supplier_id = spc.supplier_id(+)\n" +
			"        AND    d.dept_code <> '0E'\n" +
			"        AND    sc.dept_code(+) <> '0E'\n" +
			"        and    spc.dept_code(+) <> '0E') t\n" +
			"where  1 = 1 ";

	private Integer bankAccountId;
    private Integer supplierId;
    private String bankAccountNumber;
    private String bankUserName;
    private String bankName;
    private String bankCurrency;
    private String swiftCode;
    private String ibanCode;
    private String bankCurrencyMeaning;
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
    private Integer operatorUserId;
    private String deptCode;

	private String supplierNumber;
	private String supplierName;
	private String supplierShortName;
	private String supplierInChargeName;
	private String supplierStatus;
	private String supplierType;
	private String country;
	private String majorCustomer;
	private Clob companyDescription;

	public void setBankAccountId(Integer bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	
	public Integer getBankAccountId() {
		return bankAccountId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	
	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	public String getBankName() {
		return bankName;
	}

	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}

	
	public String getBankCurrency() {
		return bankCurrency;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	
	public String getSwiftCode() {
		return swiftCode;
	}

	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	
	public String getIbanCode() {
		return ibanCode;
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

	public String getBankCurrencyMeaning() {
		return bankCurrencyMeaning;
	}

	public void setBankCurrencyMeaning(String bankCurrencyMeaning) {
		this.bankCurrencyMeaning = bankCurrencyMeaning;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
