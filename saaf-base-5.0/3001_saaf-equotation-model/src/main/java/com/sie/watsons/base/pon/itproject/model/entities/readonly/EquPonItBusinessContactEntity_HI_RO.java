package com.sie.watsons.base.pon.itproject.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * EquPonItBusinessContactEntity_HI_RO Entity Object
 * Mon Dec 16 23:18:07 CST 2019  Auto Generate
 */

public class EquPonItBusinessContactEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_LIST_SQL);
	}
	public  static  final String  QUERY_LIST_SQL = "select t.contact_id         contactId\n" +
			"      ,t.project_id         projectId\n" +
			"      ,t.member_type        memberType\n" +
			"      ,t.member_number      memberNumber\n" +
			"      ,t.member_name        memberName\n" +
			"      ,t.member_phone       memberPhone\n" +
			"      ,t.member_email       memberEmail\n" +
			"      ,t.remark\n" +
			"      ,t.version_num        versionNum\n" +
			"      ,t.creation_date      creationDate\n" +
			"      ,t.created_by         createdBy\n" +
			"      ,t.last_updated_by    lastUpdatedBy\n" +
			"      ,t.last_update_date   lastUpdateDate\n" +
			"      ,t.last_update_login  lastUpdateLogin\n" +
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
			"      ,t.source_id          sourceId\n" +
			"from   equ_pon_it_business_contact t\n" +
			"where  1 = 1\n";


    public static final String QUERY_SQL = "select t.projectId,\n" +
            "       t.sort,\n" +
            "       t.contentId,\n" +
            "       t.memberType,\n" +
            "       t.memberNumber,\n" +
            "       t.memberName,\n" +
            "       t.memberPhone,\n" +
            "       t.memberEmail,\n" +
            "       t.REMARK\n" +
            "  from (SELECT bc.PROJECT_ID    projectId,\n" +
            "               1                sort,\n" +
            "               bc.CONTACT_ID    contentId,\n" +
            "               bc.MEMBER_TYPE   memberType,\n" +
            "               bc.MEMBER_NUMBER memberNumber,\n" +
            "               bc.MEMBER_NAME   memberName,\n" +
            "               bc.MEMBER_PHONE  memberPhone,\n" +
            "               bc.MEMBER_EMAIL  memberEmail,\n" +
            "               bc.REMARK\n" +
            "          FROM equ_pon_it_business_contact bc\n" +
            "         where bc.member_type = 'PROJECT_LEADER'\n" +
            "        union all\n" +
            "        SELECT bc.PROJECT_ID    projectId,\n" +
            "               2                sort,\n" +
            "               bc.CONTACT_ID    contentId,\n" +
            "               bc.MEMBER_TYPE   memberType,\n" +
            "               bc.MEMBER_NUMBER memberNumber,\n" +
            "               bc.MEMBER_NAME   memberName,\n" +
            "               bc.MEMBER_PHONE  memberPhone,\n" +
            "               bc.MEMBER_EMAIL  memberEmail,\n" +
            "               bc.REMARK\n" +
            "          FROM equ_pon_it_business_contact bc\n" +
            "         where bc.member_type = 'QUOTATION_LEADER'\n" +
            "        union all\n" +
            "        SELECT bc.PROJECT_ID    projectId,\n" +
            "               3                sort,\n" +
            "               bc.CONTACT_ID    contentId,\n" +
            "               bc.MEMBER_TYPE   memberType,\n" +
            "               bc.MEMBER_NUMBER memberNumber,\n" +
            "               bc.MEMBER_NAME   memberName,\n" +
            "               bc.MEMBER_PHONE  memberPhone,\n" +
            "               bc.MEMBER_EMAIL  memberEmail,\n" +
            "               bc.REMARK\n" +
            "          FROM equ_pon_it_business_contact bc\n" +
            "         where bc.member_type = 'BUSINESS_LEADER') t\n" +
            " where 1 = 1";
    private Integer contactId;
    private Integer projectId;
    private String memberType;
    private String memberNumber;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String remark;
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
    private Integer sourceId;
    private Integer operatorUserId;

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	
	public Integer getContactId() {
		return contactId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	
	public String getMemberType() {
		return memberType;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	
	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	
	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	
	public String getMemberEmail() {
		return memberEmail;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
