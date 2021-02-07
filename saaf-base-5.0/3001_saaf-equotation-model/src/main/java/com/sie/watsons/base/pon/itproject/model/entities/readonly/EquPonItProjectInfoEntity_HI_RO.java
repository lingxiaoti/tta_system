package com.sie.watsons.base.pon.itproject.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * EquPonItProjectInfoEntity_HI_RO Entity Object
 * Mon Dec 16 23:18:00 CST 2019  Auto Generate
 */

public class EquPonItProjectInfoEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL_FOR_TERMINATE);
	}
	public  static  final String  QUERY_SQL = "select t.project_id                     projectId\n" +
			"      ,t.project_name                   projectName\n" +
			"      ,t.project_number                 projectNumber\n" +
			"      ,t.project_version                projectVersion\n" +
			"      ,t.project_status                 projectStatus\n" +
			"      ,t.dept_code                      deptCode\n" +
			"      ,t.project_purchase_amount        projectPurchaseAmount\n" +
			"      ,t.purchase_company               purchaseCompany\n" +
			"      ,t.union_deptment                 unionDeptment\n" +
			"      ,t.union_deptment_name            unionDeptmentName\n" +
			"      ,nvl(t.union_deptment,'-1')       unionDeptmentFlag\n" +
			"      ,t.quotation_deadline             quotationDeadline\n" +
			"      ,t.scoring_standard               scoringStandard\n" +
			"      ,(select s.standards_id\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardId\n" +
			"      ,(select s.standards_name\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardName\n" +
			"      ,t.file_id                        fileId\n" +
			"      ,t.file_name                      fileName\n" +
			"      ,t.file_path                      filePath\n" +
			"      ,t.reject_reason                  rejectReason\n" +
			"      ,t.remark\n" +
			"      ,(select count(1)\n" +
			"        from   equ_pon_quotation_approval qa\n" +
			"        where  qa.project_id = t.project_id\n" +
			"        and    qa.approval_status = '30') approvalCount\n" +
			"      ,t.version_num                    versionNum\n" +
			"      ,t.creation_date                  creationDate\n" +
			"      ,t.created_by                     createdBy\n" +
			"      ,t.last_updated_by                lastUpdatedBy\n" +
			"      ,t.last_update_date               lastUpdateDate\n" +
			"      ,t.last_update_login              lastUpdateLogin\n" +
			"      ,t.attribute_category             attributeCategory\n" +
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
			"      ,t.send_quotation_invitation_date sendQuotationInvitationDate\n" +
			"      ,t.source_project_number          sourceProjectNumber\n" +
			"      ,t.is_change_project              isChangeProject\n" +
			"      ,t.can_change_flag                canChangeFlag\n" +
			"      ,t.parent_project_number          parentProjectNumber\n" +
			"      ,t.change_type                    changeType\n" +
			"      ,t.terminate_flag                 terminateFlag\n" +
			"      ,t.brand_team_person_id           brandTeamPersonId\n" +
			"      ,t.brand_team_user_id             brandTeamUserId\n" +
			"      ,t.brand_team_person_name         brandTeamPersonName\n" +
			"      ,t.department_name                departmentName\n" +
			"      ,t.qa_user_id                     qaUserId\n" +
			"      ,t.ia_user_id                     iaUserId\n" +
			"      ,t.security_user_id               securityUserId\n" +
			"      ,t.qa_user_number                 qaUserNumber\n" +
			"      ,t.ia_user_number                 iaUserNumber\n" +
			"      ,t.security_user_number           securityUserNumber\n" +
			"      ,t.contact_phone_number           contactPhoneNumber\n" +
			"      ,t.full_price_tag                 fullPriceTag\n" +
			"from   equ_pon_it_project_info t where 1 = 1 ";

	public  static  final String  QUERY_SQL_FOR_TERMINATE = "SELECT t.project_id projectId\n" +
			"      ,t.project_name projectName\n" +
			"      ,t.project_number projectNumber\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.project_status projectStatus\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,t.department_name departmentName\n" +
			"      ,decode(t.union_deptment,'','N','Y')       unionDeptmentFlag\n" +
			"      ,t.project_purchase_amount projectPurchaseAmount\n" +
			"      ,t.quotation_deadline quotationDeadline\n" +
			"      ,t.send_quotation_invitation_date sendQuotationInvitationDate\n" +
			"      ,t.scoring_standard scoringStandard\n" +
			"      ,(select s.standards_id\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardId\n" +
			"      ,(select s.standards_name\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardName\n" +
			"      ,t.remark\n" +
			"      ,t.full_price_tag fullPriceTag\n" +
			"      ,t.change_type changeType\n" +
			"      ,t.source_project_number sourceProjectNumber\n" +
			"      ,t.parent_project_number parentProjectNumber\n" +
			"      ,t.is_change_project isChangeProject\n" +
			"      ,t.can_change_flag canChangeFlag\n" +
			"      ,nvl(t.terminate_flag, 'N') terminateFlag\n" +
			"      ,t.brand_team_person_id brandTeamPersonId\n" +
			"      ,t.brand_team_user_id brandTeamUserId\n" +
			"      ,t.brand_team_person_name brandTeamPersonName\n" +
			"      ,t.brand_team_user_id userId\n" +
			"      ,to_char(t.brand_team_person_id) userName\n" +
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
			"FROM   equ_pon_it_project_info t\n" +
			"WHERE  1 = 1\n" +
			"and    t.project_status not in ('50','60')\n" +
			"AND    t.project_id not in\n" +
			"       (SELECT ppi.project_id\n" +
			"         FROM   equ_pon_quotation_approval qa\n" +
			"               ,equ_pon_it_project_info       ppi\n" +
			"         WHERE  qa.project_id = ppi.project_id\n" +
			"         and    qa.approval_status = '30')";

	public static final String QUERY_PON_NAVIGATION_MENU_SQL = "select '立项' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonItProject' link\n" +
			"      ,'1' rowIndex\n" +
			"      ,'LX' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '供应商邀请' title\n" +
			"      ,'立项' pageTitle\n" +
			"      ,'/equPonItProject' link\n" +
			"      ,'2' rowIndex\n" +
			"      ,'GYSYQ' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '报价资料开启' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonInformationIt' link\n" +
			"      ,'3' rowIndex\n" +
			"      ,'BJZLKQ' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '评分' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonItScoring' link\n" +
			"      ,'4' rowIndex\n" +
			"      ,'PF' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '报价结果审批' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonQuotationItApproval' link\n" +
			"      ,'5' rowIndex\n" +
			"      ,'BJJGSP' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n";

	public  static  final String  QUERY_FOR_WAIT_QUOTATION = "SELECT t.project_id                     projectId,\n" +
			"       t.project_name                   projectName,\n" +
			"       t.project_number                 projectNumber,\n" +
			"       t.source_project_number          sourceProjectNumber,\n" +
			"       t.quotation_deadline             quotationDeadline,\n" +
			"       t.send_quotation_invitation_date sendQuotationInvitationDate,\n" +
			"       t.creation_date                  creationDate,\n" +
			"       t.created_by                     createdBy,\n" +
			"       t.last_updated_by                lastUpdatedBy,\n" +
			"       t.last_update_date               lastUpdateDate,\n" +
			"       t.last_update_login              lastUpdateLogin\n" +
			"  FROM equ_pon_it_project_info t,\n" +
			"       (select pi.source_project_number, max(pi.project_id) project_id\n" +
			"          from equ_pon_it_project_info pi\n" +
			"         where 1 = 1\n" +
			"           and pi.project_status = '30'\n" +
			"           and pi.send_quotation_invitation_date is not null\n" +
			"         group by pi.source_project_number) t1\n" +
			" WHERE 1 = 1\n" +
			"   and t.project_id = t1.project_id\n";

	public static final String QUERY_SQL_RELATE_DEPT_BUSINESS_PERSON = "select t.project_id    projectId\n" +
			"      ,t.member_number userName\n" +
			"from   equ_pon_it_business_contact t\n" +
			"where t.member_type = '业务负责人' ";

	public static final String QUERY_SQL_DEFAULT_WITNESS_IA = "select t.project_id     projectId\n" +
			"      ,t.default_member userName\n" +
			"from   equ_pon_it_witness_team t\n" +
			"where  t.dept_name = 'IA'";

	public static final String QUERY_SQL_DEFAULT_WITNESS_SECURITY = "select t.project_id     projectId\n" +
			"      ,t.default_member userName\n" +
			"from   equ_pon_it_witness_team t\n" +
			"where  t.dept_name = 'Security'";

	public static final String QUERY_SQL_WITNESS_IA = "select t.project_id     projectId\n" +
			"      ,t.ia_user_number userName\n" +
			"from   equ_pon_quotation_information t\n" +
			"where  t.dept_code <> '0E' ";

	public static final String QUERY_SQL_WITNESS_SECURITY = "select t.project_id     projectId\n" +
			"      ,t.security_user_number userName\n" +
			"from   equ_pon_quotation_information t\n" +
			"where  t.dept_code <> '0E'";

    private Integer projectId;
    private String projectName;
    private String projectNumber;
    private String projectVersion;
    private String projectStatus;
    private String deptCode;
    private BigDecimal projectPurchaseAmount;
    private String purchaseCompany;
    private String unionDeptment;
    private String unionDeptmentName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date quotationDeadline;
	private Integer scoringStandardId;
	private String scoringStandardName;
	private String scoringStandard;
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String rejectReason;
	private String fullPriceTag;
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
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date sendQuotationInvitationDate;
    private String sourceProjectNumber;
    private String isChangeProject;
    private String canChangeFlag;
    private String parentProjectNumber;
    private String changeType;
    private String terminateFlag;
    private Integer brandTeamPersonId;
    private Integer brandTeamUserId;
    private String brandTeamPersonName;
    private String departmentName;
    private Integer qaUserId;
    private Integer iaUserId;
    private Integer securityUserId;
    private String qaUserNumber;
    private String iaUserNumber;
    private String securityUserNumber;
    private Integer operatorUserId;

	private String title;
	private String pageTitle;
	private String link;
	private String rowIndex;
	private String nodeName;
	private String pathType;
	private String status;
	private String categoryType;
	private String imageName;
	private String contactPhoneNumber;
	private Integer userId;
	private String userName;
	private String unionDeptmentFlag;
	private Integer approvalCount;

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	
	public String getProjectStatus() {
		return projectStatus;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
		this.projectPurchaseAmount = projectPurchaseAmount;
	}

	
	public BigDecimal getProjectPurchaseAmount() {
		return projectPurchaseAmount;
	}

	public void setPurchaseCompany(String purchaseCompany) {
		this.purchaseCompany = purchaseCompany;
	}

	
	public String getPurchaseCompany() {
		return purchaseCompany;
	}

	public void setUnionDeptment(String unionDeptment) {
		this.unionDeptment = unionDeptment;
	}

	
	public String getUnionDeptment() {
		return unionDeptment;
	}

	public void setQuotationDeadline(Date quotationDeadline) {
		this.quotationDeadline = quotationDeadline;
	}

	
	public Date getQuotationDeadline() {
		return quotationDeadline;
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard;
	}

	
	public String getScoringStandard() {
		return scoringStandard;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
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

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	
	public String getRejectReason() {
		return rejectReason;
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

	public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
		this.sendQuotationInvitationDate = sendQuotationInvitationDate;
	}

	
	public Date getSendQuotationInvitationDate() {
		return sendQuotationInvitationDate;
	}

	public void setSourceProjectNumber(String sourceProjectNumber) {
		this.sourceProjectNumber = sourceProjectNumber;
	}

	
	public String getSourceProjectNumber() {
		return sourceProjectNumber;
	}

	public void setIsChangeProject(String isChangeProject) {
		this.isChangeProject = isChangeProject;
	}

	
	public String getIsChangeProject() {
		return isChangeProject;
	}

	public void setCanChangeFlag(String canChangeFlag) {
		this.canChangeFlag = canChangeFlag;
	}

	
	public String getCanChangeFlag() {
		return canChangeFlag;
	}

	public void setParentProjectNumber(String parentProjectNumber) {
		this.parentProjectNumber = parentProjectNumber;
	}

	
	public String getParentProjectNumber() {
		return parentProjectNumber;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	
	public String getChangeType() {
		return changeType;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	
	public String getTerminateFlag() {
		return terminateFlag;
	}

	public void setBrandTeamPersonId(Integer brandTeamPersonId) {
		this.brandTeamPersonId = brandTeamPersonId;
	}

	
	public Integer getBrandTeamPersonId() {
		return brandTeamPersonId;
	}

	public void setBrandTeamUserId(Integer brandTeamUserId) {
		this.brandTeamUserId = brandTeamUserId;
	}

	
	public Integer getBrandTeamUserId() {
		return brandTeamUserId;
	}

	public void setBrandTeamPersonName(String brandTeamPersonName) {
		this.brandTeamPersonName = brandTeamPersonName;
	}

	
	public String getBrandTeamPersonName() {
		return brandTeamPersonName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setQaUserId(Integer qaUserId) {
		this.qaUserId = qaUserId;
	}

	
	public Integer getQaUserId() {
		return qaUserId;
	}

	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}

	
	public Integer getIaUserId() {
		return iaUserId;
	}

	public void setSecurityUserId(Integer securityUserId) {
		this.securityUserId = securityUserId;
	}

	
	public Integer getSecurityUserId() {
		return securityUserId;
	}

	public void setQaUserNumber(String qaUserNumber) {
		this.qaUserNumber = qaUserNumber;
	}

	
	public String getQaUserNumber() {
		return qaUserNumber;
	}

	public void setIaUserNumber(String iaUserNumber) {
		this.iaUserNumber = iaUserNumber;
	}

	
	public String getIaUserNumber() {
		return iaUserNumber;
	}

	public void setSecurityUserNumber(String securityUserNumber) {
		this.securityUserNumber = securityUserNumber;
	}

	
	public String getSecurityUserNumber() {
		return securityUserNumber;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getUnionDeptmentName() {
		return unionDeptmentName;
	}

	public void setUnionDeptmentName(String unionDeptmentName) {
		this.unionDeptmentName = unionDeptmentName;
	}

	public Integer getScoringStandardId() {
		return scoringStandardId;
	}

	public void setScoringStandardId(Integer scoringStandardId) {
		this.scoringStandardId = scoringStandardId;
	}

	public String getScoringStandardName() {
		return scoringStandardName;
	}

	public void setScoringStandardName(String scoringStandardName) {
		this.scoringStandardName = scoringStandardName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUnionDeptmentFlag() {
		return unionDeptmentFlag;
	}

	public void setUnionDeptmentFlag(String unionDeptmentFlag) {
		this.unionDeptmentFlag = unionDeptmentFlag;
	}

	public Integer getApprovalCount() {
		return approvalCount;
	}

	public void setApprovalCount(Integer approvalCount) {
		this.approvalCount = approvalCount;
	}

	public String getFullPriceTag() {
		return fullPriceTag;
	}

	public void setFullPriceTag(String fullPriceTag) {
		this.fullPriceTag = fullPriceTag;
	}
}
