package com.sie.watsons.base.pon.itproject.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonItSupplierInvitationEntity_HI_RO Entity Object
 * Mon Dec 16 23:18:03 CST 2019  Auto Generate
 */

public class EquPonItSupplierInvitationEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	public  static  final String  QUERY_SQL = "SELECT t.invitation_id invitationId\n" +
			"      ,t.project_id projectId\n" +
			"      ,t.supplier_id supplierId\n" +
			"      ,t.quotation_contact quotationContact\n" +
			"      ,t.source_id sourceId\n" +
			"      ,t.is_quit isQuit\n" +
			"      ,t.reason\n" +
			"      ,t.invite_Flag inviteFlag\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.quotation_flag quotationFlag\n" +
			"      ,t.project_experience projectExperience\n" +
			"      ,t.invitation_id \"index\"\n" +
			"       ,t.version_num versionNum\n" +
			"       ,t.creation_date creationDate\n" +
			"       ,t.created_by createdBy\n" +
			"       ,t.last_updated_by lastUpdatedBy\n" +
			"       ,t.last_update_date lastUpdateDate\n" +
			"       ,t.last_update_login lastUpdateLogin\n" +
			"       ,t.attribute_category attributeCategory\n" +
			"       ,(CASE\n" +
			"         WHEN epp.Send_Quotation_Invitation_Date IS NULL\n" +
			"              AND epp.project_version = '01' THEN\n" +
			"          '未发送报价邀请'\n" +
			"         ELSE\n" +
			"          decode(nvl(t.is_quit, 'N'),\n" +
			"                 'N',\n" +
			"                 decode(t.quotation_flag,\n" +
			"                        'R',\n" +
			"                        '拒绝参与',\n" +
			"                        (NVL((SELECT DECODE(EPQI.DOC_STATUS,\n" +
			"                                           'BARGAIN',\n" +
			"                                           '待议价',\n" +
			"                                           'BREAK',\n" +
			"                                           '终止',\n" +
			"                                           'COMMIT',\n" +
			"                                           '已提交报价',\n" +
			"                                           'COMPLETE',\n" +
			"                                           '已完成',\n" +
			"                                           'MODIFYING',\n" +
			"                                           '修改报价中',\n" +
			"                                           'QUOTATION',\n" +
			"                                           '报价中',\n" +
			"                                           'STOP',\n" +
			"                                           '已提交报价')\n" +
			"                             FROM   EQU_PON_QUOTATION_INFO_IT EPQI\n" +
			"                             WHERE  epqi.supplier_id = t.supplier_id\n" +
			"                             AND    substr(epqi.project_number, 1, 16) =\n" +
			"                                    epp.source_project_number\n" +
			"                             AND    ROWNUM = 1),\n" +
			"                             '未回应'))),\n" +
			"                 'Y',\n" +
			"                 decode(t.quotation_flag, 'R', '拒绝参与', '退出'))\n" +
			"       END) supplierStatus\n" +
			"       ,t.attribute1\n" +
			"       ,t.attribute2\n" +
			"       ,t.attribute3\n" +
			"       ,t.attribute4\n" +
			"       ,t.attribute5\n" +
			"       ,t.attribute6\n" +
			"       ,t.attribute7\n" +
			"       ,t.attribute8\n" +
			"       ,t.attribute9\n" +
			"       ,t.attribute10\n" +
			"       ,d1.supplier_status supplierStatusName\n" +
			"       ,t.oi_percent oiPercent\n" +
			"       ,s1.supplier_name supplierName\n" +
			"       ,s1.supplier_number supplierNumber\n" +
			"       ,sc.contact_name contactName\n" +
			"       ,sc.email_address emailAddress\n" +
			"       ,c.enroll_capital enrollCapital\n" +
			"FROM   EQU_PON_IT_SUPPLIER_INVITATION t\n" +
			"      ,equ_pon_it_project_info        epp\n" +
			"      ,equ_pos_supplier_info       s1\n" +
			"      ,equ_pos_supp_info_with_dept d1\n" +
			"      ,equ_pos_supplier_contacts   sc\n" +
			"      ,equ_pos_supplier_credentials   c\n" +
			"where  t.supplier_id = s1.supplier_id(+)\n" +
			"and    t.supplier_id = d1.supplier_id(+)\n" +
			"and    t.supplier_id = c.supplier_id\n" +
			"and    epp.project_id = t.project_id\n" +
			"and    d1.dept_code(+) <> '0E'\n" +
			"and    c.dept_code(+) <> '0E'\n" +
			"and    t.quotation_contact = sc.supplier_contact_id(+)\n";

    public static final String QUERY_REJECT_PROJECT_IT = "SELECT t.invitation_id    invitationId,\n" +
            "       t.project_id       projectId,\n" +
            "       pi.project_number  projectNumber,\n" +
            "       pi.project_name    projectName,\n" +
            "       t.quotation_flag   quotationFlag,\n" +
            "       t.is_quit          isQuit,\n" +
            "       t.supplier_id      supplierId,\n" +
            "       t.reason,\n" +
            "       t.creation_date    creationDate,\n" +
            "       t.created_by       createdBy,\n" +
            "       t.last_updated_by  lastUpdatedBy,\n" +
            "       t.last_update_date lastUpdateDate\n" +
            "  FROM EQU_PON_IT_SUPPLIER_INVITATION t, equ_pon_IT_project_info pi\n" +
            " where pi.project_id = t.project_id\n" +
            " and t.quotation_flag = 'R'";

    private Integer invitationId;
    private Integer projectId;
    private Integer supplierId;
    private Integer quotationContact;
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
    private String isQuit;
    private String reason;
    private String projectVersion;
    private String quotationFlag;
    private String inviteFlag;
    private Integer operatorUserId;

    private String contactName;
    private String emailAddress;
    private String supplierName;
    private String projectNumber;
    private String projectName;
    private String projectExperience;
	private String enrollCapital;
	private String supplierNumber;

	private String supplierStatus;
	private Integer index;
	private String  supplierStatusName;
	private BigDecimal oiPercent;
	private String factoryStatusName;

	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}

	
	public Integer getInvitationId() {
		return invitationId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setQuotationContact(Integer quotationContact) {
		this.quotationContact = quotationContact;
	}

	
	public Integer getQuotationContact() {
		return quotationContact;
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

	public void setIsQuit(String isQuit) {
		this.isQuit = isQuit;
	}

	
	public String getIsQuit() {
		return isQuit;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	public String getReason() {
		return reason;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setQuotationFlag(String quotationFlag) {
		this.quotationFlag = quotationFlag;
	}

	
	public String getQuotationFlag() {
		return quotationFlag;
	}

	public BigDecimal getOiPercent() {
		return oiPercent;
	}

	public void setInviteFlag(String inviteFlag) {
		this.inviteFlag = inviteFlag;
	}

	
	public String getInviteFlag() {
		return inviteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

	public String getProjectExperience() {
		return projectExperience;
	}

	public void setProjectExperience(String projectExperience) {
		this.projectExperience = projectExperience;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getEnrollCapital() {
		return enrollCapital;
	}

	public void setEnrollCapital(String enrollCapital) {
		this.enrollCapital = enrollCapital;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getSupplierStatusName() {
		return supplierStatusName;
	}

	public void setSupplierStatusName(String supplierStatusName) {
		this.supplierStatusName = supplierStatusName;
	}

	public void setOiPercent(BigDecimal oiPercent) {
		this.oiPercent = oiPercent;
	}

	public String getFactoryStatusName() {
		return factoryStatusName;
	}

	public void setFactoryStatusName(String factoryStatusName) {
		this.factoryStatusName = factoryStatusName;
	}
}
