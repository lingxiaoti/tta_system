package com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * EquPosBgContactsEntity_HI_RO Entity Object
 * Tue Sep 24 14:10:20 CST 2019  Auto Generate
 */

public class EquPosBgContactsEntity_HI_RO {
	//查询供应商联系人信息
	public  static  final String  QUERY_SQL =
			"SELECT sc.supplier_contact_id supplierContactId\n" +
					"      ,sc.supplier_id supplierId\n" +
					"      ,sc.contact_name contactName\n" +
					"      ,sc.mobile_phone mobilePhone\n" +
					"      ,sc.fixed_phone fixedPhone\n" +
					"      ,sc.email_address emailAddress\n" +
					"      ,sc.position_name positionName\n" +
					"      ,sc.resp_category respCategory\n" +
					"      ,sc.remark\n" +
					"      ,sc.send_email_flag sendEmailFlag\n" +
					"      ,sc.dept_code deptCode\n" +
					"      ,sc.change_id changeId\n" +
					"      ,sc.source_id sourceId\n" +
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
					"FROM   equ_pos_bg_contacts sc where 1 = 1 ";

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
	private Integer changeId;
	private Integer sourceId;
	private Integer operatorUserId;

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

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}


	public String getDeptCode() {
		return deptCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
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
}
