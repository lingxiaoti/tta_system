package com.sie.watsons.base.pos.supplierinfo.model.entities.readonly;

import javax.persistence.Version;
import java.sql.Clob;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosSupplierContactsEntity_HI_RO Entity Object
 * Thu Sep 05 14:15:15 CST 2019  Auto Generate
 */

public class EquPosSupplierContactsEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	//查询供应商基础信息
	public  static  final String  QUERY_SQL =
			"SELECT sc.supplier_contact_id supplierContactId\n" +
					"      ,sc.supplier_id supplierId\n" +
					"      ,sc.contact_name contactName\n" +
					"      ,sc.mobile_phone mobilePhone\n" +
					"      ,sc.fixed_phone fixedPhone\n" +
					"      ,sc.email_address emailAddress\n" +
					"      ,sc.position_name positionName\n" +
					"      ,sc.resp_category respCategory\n" +
					"      ,sc.user_id userId\n" +
					"      ,sc.system_account systemAccount\n" +
					"      ,sc.remark\n" +
					"      ,sc.send_email_flag sendEmailFlag\n" +
					"      ,sc.dept_code deptCode\n" +
					"      ,sc.version_num         versionNum\n" +
					"      ,sc.creation_date       creationNate\n" +
					"      ,sc.created_by          createdBy\n" +
					"      ,sc.last_updated_by     lastUpdatedBy\n" +
					"      ,sc.last_update_date    lastUpdateDate\n" +
					"      ,sc.last_update_login   lastUpdateLogin\n" +
					"      ,sc.attribute_category  attributeCategory\n" +
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
					"FROM   equ_pos_supplier_contacts sc where 1 = 1 ";

	public  static  final String  QUERY_SQL_SUPPLIER_CONTACT_REPORT_FORM = "SELECT t.supplier_id             supplierId\n" +
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
			"      ,t.contact_name            contactName\n" +
			"      ,t.mobile_phone            mobilePhone\n" +
			"      ,t.email_address           emailAddress\n" +
			"      ,t.fixed_phone             fixedPhone\n" +
			"      ,t.resp_category           respCategory\n" +
			"      ,t.system_account          systemAccount\n" +
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
			"              ,spc.contact_name\n" +
			"              ,spc.mobile_phone\n" +
			"              ,spc.email_address\n" +
			"              ,spc.fixed_phone\n" +
			"              ,spc.resp_category\n" +
			"              ,spc.system_account\n" +
			"        FROM   equ_pos_supplier_info        psi\n" +
			"              ,equ_pos_supp_info_with_dept  d\n" +
			"              ,equ_pos_supplier_credentials sc\n" +
			"              ,equ_pos_supplier_contacts    spc\n" +
			"        WHERE  psi.supplier_id = d.supplier_id(+)\n" +
			"        AND    psi.supplier_id = sc.supplier_id(+)\n" +
			"        and    psi.supplier_id = spc.supplier_id(+)\n" +
			"        AND    d.dept_code <> '0E'\n" +
			"        AND    sc.dept_code(+) <> '0E'\n" +
			"        and    spc.dept_code(+) <> '0E') t\n" +
			"where  1 = 1 ";

	private Integer supplierContactId; //供应商联系人ID
    private Integer supplierId; //供应商ID
    private String contactName; //联系人姓名
    private String mobilePhone; //手机号码
    private String fixedPhone; //固定电话
    private String emailAddress; //联系人邮箱
    private String positionName; //职位
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
    private String respCategory; //负责品类
    private String remark; //备注
    private String sendEmailFlag; //发送邮件标志
	private String deptCode;
	private Integer userId;
	private String systemAccount;
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

	public void setSupplierContactId(Integer supplierContactId) {
		this.supplierContactId = supplierContactId;
	}

	
	public Integer getSupplierContactId() {
		return supplierContactId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	
	public String getContactName() {
		return contactName;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}

	
	public String getFixedPhone() {
		return fixedPhone;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	
	public String getPositionName() {
		return positionName;
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

	public void setRespCategory(String respCategory) {
		this.respCategory = respCategory;
	}

	
	public String getRespCategory() {
		return respCategory;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setSendEmailFlag(String sendEmailFlag) {
		this.sendEmailFlag = sendEmailFlag;
	}

	
	public String getSendEmailFlag() {
		return sendEmailFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSystemAccount() {
		return systemAccount;
	}

	public void setSystemAccount(String systemAccount) {
		this.systemAccount = systemAccount;
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
