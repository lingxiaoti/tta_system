package com.sie.watsons.base.pon.itproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * EquPonItProjectInfoEntity_HI Entity Object
 * Mon Dec 16 23:18:00 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_IT_PROJECT_INFO")
public class EquPonItProjectInfoEntity_HI {
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
    private String scoringStandard;
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String rejectReason;
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
    private String contactPhoneNumber;
    private String fullPriceTag;
    private Integer operatorUserId;

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_IT_PROJECT_INFO_S", sequenceName = "EQU_PON_IT_PROJECT_INFO_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_IT_PROJECT_INFO_S", strategy = GenerationType.SEQUENCE)
	@Column(name="project_id", nullable=false, length=22)	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name="project_name", nullable=true, length=200)	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	@Column(name="project_number", nullable=true, length=30)	
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	@Column(name="project_version", nullable=true, length=2)	
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	@Column(name="project_status", nullable=true, length=30)	
	public String getProjectStatus() {
		return projectStatus;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=20)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
		this.projectPurchaseAmount = projectPurchaseAmount;
	}

	@Column(name="project_purchase_amount", nullable=true, length=22)	
	public BigDecimal getProjectPurchaseAmount() {
		return projectPurchaseAmount;
	}

	public void setPurchaseCompany(String purchaseCompany) {
		this.purchaseCompany = purchaseCompany;
	}

	@Column(name="purchase_company", nullable=true, length=20)	
	public String getPurchaseCompany() {
		return purchaseCompany;
	}

	public void setUnionDeptment(String unionDeptment) {
		this.unionDeptment = unionDeptment;
	}

	@Column(name="union_deptment", nullable=true, length=20)	
	public String getUnionDeptment() {
		return unionDeptment;
	}

	public void setUnionDeptmentName(String unionDeptmentName) {
		this.unionDeptmentName = unionDeptmentName;
	}

	@Column(name="union_deptment_name", nullable=true, length=200)
	public String getUnionDeptmentName() {
		return unionDeptmentName;
	}

	public void setQuotationDeadline(Date quotationDeadline) {
		this.quotationDeadline = quotationDeadline;
	}

	@Column(name="quotation_deadline", nullable=true, length=7)	
	public Date getQuotationDeadline() {
		return quotationDeadline;
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard;
	}

	@Column(name="scoring_standard", nullable=true, length=30)	
	public String getScoringStandard() {
		return scoringStandard;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=true, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=100)	
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="file_path", nullable=true, length=240)	
	public String getFilePath() {
		return filePath;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Column(name="reject_reason", nullable=true, length=240)	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=4000)	
	public String getRemark() {
		return remark;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name="attribute_category", nullable=true, length=30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
		this.sendQuotationInvitationDate = sendQuotationInvitationDate;
	}

	@Column(name="send_quotation_invitation_date", nullable=true, length=7)	
	public Date getSendQuotationInvitationDate() {
		return sendQuotationInvitationDate;
	}

	public void setSourceProjectNumber(String sourceProjectNumber) {
		this.sourceProjectNumber = sourceProjectNumber;
	}

	@Column(name="source_project_number", nullable=true, length=30)	
	public String getSourceProjectNumber() {
		return sourceProjectNumber;
	}

	public void setIsChangeProject(String isChangeProject) {
		this.isChangeProject = isChangeProject;
	}

	@Column(name="is_change_project", nullable=true, length=1)	
	public String getIsChangeProject() {
		return isChangeProject;
	}

	public void setCanChangeFlag(String canChangeFlag) {
		this.canChangeFlag = canChangeFlag;
	}

	@Column(name="can_change_flag", nullable=true, length=1)	
	public String getCanChangeFlag() {
		return canChangeFlag;
	}

	public void setParentProjectNumber(String parentProjectNumber) {
		this.parentProjectNumber = parentProjectNumber;
	}

	@Column(name="parent_project_number", nullable=true, length=30)	
	public String getParentProjectNumber() {
		return parentProjectNumber;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	@Column(name="change_type", nullable=true, length=20)	
	public String getChangeType() {
		return changeType;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	@Column(name="terminate_flag", nullable=true, length=1)	
	public String getTerminateFlag() {
		return terminateFlag;
	}

	public void setBrandTeamPersonId(Integer brandTeamPersonId) {
		this.brandTeamPersonId = brandTeamPersonId;
	}

	@Column(name="brand_team_person_id", nullable=true, length=22)	
	public Integer getBrandTeamPersonId() {
		return brandTeamPersonId;
	}

	public void setBrandTeamUserId(Integer brandTeamUserId) {
		this.brandTeamUserId = brandTeamUserId;
	}

	@Column(name="brand_team_user_id", nullable=true, length=22)	
	public Integer getBrandTeamUserId() {
		return brandTeamUserId;
	}

	public void setBrandTeamPersonName(String brandTeamPersonName) {
		this.brandTeamPersonName = brandTeamPersonName;
	}

	@Column(name="brand_team_person_name", nullable=true, length=30)	
	public String getBrandTeamPersonName() {
		return brandTeamPersonName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name="department_name", nullable=true, length=30)	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setQaUserId(Integer qaUserId) {
		this.qaUserId = qaUserId;
	}

	@Column(name="qa_user_id", nullable=true, length=22)	
	public Integer getQaUserId() {
		return qaUserId;
	}

	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}

	@Column(name="ia_user_id", nullable=true, length=22)	
	public Integer getIaUserId() {
		return iaUserId;
	}

	public void setSecurityUserId(Integer securityUserId) {
		this.securityUserId = securityUserId;
	}

	@Column(name="security_user_id", nullable=true, length=22)	
	public Integer getSecurityUserId() {
		return securityUserId;
	}

	public void setQaUserNumber(String qaUserNumber) {
		this.qaUserNumber = qaUserNumber;
	}

	@Column(name="qa_user_number", nullable=true, length=200)	
	public String getQaUserNumber() {
		return qaUserNumber;
	}

	public void setIaUserNumber(String iaUserNumber) {
		this.iaUserNumber = iaUserNumber;
	}

	@Column(name="ia_user_number", nullable=true, length=200)	
	public String getIaUserNumber() {
		return iaUserNumber;
	}

	public void setSecurityUserNumber(String securityUserNumber) {
		this.securityUserNumber = securityUserNumber;
	}

	@Column(name="security_user_number", nullable=true, length=200)	
	public String getSecurityUserNumber() {
		return securityUserNumber;
	}

	@Column(name="contact_phone_number", nullable=true, length=100)
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	@Column(name="full_price_tag", nullable=true, length=1)
	public String getFullPriceTag() {
		return fullPriceTag;
	}

	public void setFullPriceTag(String fullPriceTag) {
		this.fullPriceTag = fullPriceTag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
