package com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**   EquPosBgqBankEntity_HI_RO.QUERY_SQL
 * EquPosBgqBankEntity_HI_RO Entity Object
 * Wed Sep 25 01:18:37 CST 2019  Auto Generate
 */

public class EquPosBgqBankEntity_HI_RO {
	//查询供应商基础信息
	public  static  final String  QUERY_SQL =
			"SELECT sb.bank_account_id     bankAccountId\n" +
					"      ,sb.supplier_id         supplierId\n" +
					"      ,sb.bank_account_number bankAccountNumber\n" +
					"      ,sb.bank_user_name      bankUserName\n" +
					"      ,sb.bank_name           bankName\n" +
					"      ,sb.bank_currency       bankCurrency\n" +
					"      ,sb.swift_code          swiftCode\n" +
					"      ,sb.iban_code           ibanCode\n" +
					"      ,sb.dept_code           deptCode\n" +
					"      ,sb.change_id           changeId\n" +
					"      ,sb.source_id           sourceId\n" +
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
					"      ,sb.attribute10 \n" +
					"FROM   equ_pos_Bgq_bank   sb \n" +
					"WHERE  1 = 1 ";

	private Integer bankAccountId;
	private Integer supplierId;
	private String bankAccountNumber;
	private String bankUserName;
	private String bankName;
	private String bankCurrency;
	private String swiftCode;
	private String ibanCode;
	private String deptCode;
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
	private Integer changeId;
	private Integer sourceId;
	private Integer operatorUserId;

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

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
