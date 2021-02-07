package com.sie.watsons.base.pon.project.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonProjectInfoEntity_HI Entity Object
 * Fri Oct 04 11:17:38 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_PROJECT_INFO")
public class EquPonProjectInfoEntity_HI {
    private Integer projectId;
    private String projectName;
    private String projectNumber;
    private String projectVersion;
    private String projectStatus;
    private String deptCode;
    private String relevantCatetory;
	private String nodePath;
    private String projectCategory;
    private String seriesName;
    private BigDecimal projectPurchaseAmount;
	private BigDecimal purchaseAmountThreshold;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date projectCycleFrom;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date projectCycleTo;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date quotationDeadline;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sendQuotationInvitationDate;
    private String scoringStandard;
    private String projectLeader;
    private String presentCooperationSupplier;
    private String presentCooperationSupplierNumber;
    private String sensoryTestTypes;
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String rejectReason;
    private String remark;
    private String sourceProjectNumber;
    private String parentProjectNumber;
    private String isChangeProject;
    private String canChangeFlag;
    private String changeType;
    private String terminateFlag;
	private String categoryType;
    private Integer brandTeamPersonId;
    private Integer brandTeamUserId;
    private String brandTeamPersonName;
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
    private String departmentName;
	private Integer qaUserId;
	private Integer iaUserId;
	private Integer securityUserId;
	private String qaUserNumber;
	private String 	iaUserNumber;
	private String securityUserNumber;
	private String terminateReason;

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_PROJECT_INFO_S", sequenceName = "EQU_PON_PROJECT_INFO_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_PROJECT_INFO_S", strategy = GenerationType.SEQUENCE)
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

	public void setRelevantCatetory(String relevantCatetory) {
		this.relevantCatetory = relevantCatetory;
	}

	@Column(name="relevant_catetory", nullable=true, length=20)	
	public String getRelevantCatetory() {
		return relevantCatetory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	@Column(name="project_category", nullable=true, length=20)	
	public String getProjectCategory() {
		return projectCategory;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	@Column(name="series_name", nullable=true, length=100)	
	public String getSeriesName() {
		return seriesName;
	}

	public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
		this.projectPurchaseAmount = projectPurchaseAmount;
	}

	@Column(name="project_purchase_amount", nullable=true, length=22)	
	public BigDecimal getProjectPurchaseAmount() {
		return projectPurchaseAmount;
	}

	public void setPurchaseAmountThreshold(BigDecimal purchaseAmountThreshold) {
		this.purchaseAmountThreshold = purchaseAmountThreshold;
	}

	@Column(name="purchase_amount_threshold", nullable=true, length=22)
	public BigDecimal getPurchaseAmountThreshold() {
		return purchaseAmountThreshold;
	}

	public void setProjectCycleFrom(Date projectCycleFrom) {
		this.projectCycleFrom = projectCycleFrom;
	}

	@Column(name="project_cycle_from", nullable=true, length=7)	
	public Date getProjectCycleFrom() {
		return projectCycleFrom;
	}

	public void setProjectCycleTo(Date projectCycleTo) {
		this.projectCycleTo = projectCycleTo;
	}

	@Column(name="CATEGORY_TYPE", nullable=true, length=22)
	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	@Column(name="project_cycle_to", nullable=true, length=7)
	public Date getProjectCycleTo() {
		return projectCycleTo;
	}

	public void setQuotationDeadline(Date quotationDeadline) {
		this.quotationDeadline = quotationDeadline;
	}

	@Column(name="quotation_deadline", nullable=true, length=7)	
	public Date getQuotationDeadline() {
		return quotationDeadline;
	}

	public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
		this.sendQuotationInvitationDate = sendQuotationInvitationDate;
	}

	@Column(name="send_quotation_invitation_date", nullable=true, length=7)
	public Date getSendQuotationInvitationDate() {
		return sendQuotationInvitationDate;
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard;
	}

	@Column(name="scoring_standard", nullable=true, length=30)	
	public String getScoringStandard() {
		return scoringStandard;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	@Column(name="project_leader", nullable=true, length=30)	
	public String getProjectLeader() {
		return projectLeader;
	}

	public void setPresentCooperationSupplier(String presentCooperationSupplier) {
		this.presentCooperationSupplier = presentCooperationSupplier;
	}

	@Column(name="present_cooperation_supplier", nullable=true, length=100)	
	public String getPresentCooperationSupplier() {
		return presentCooperationSupplier;
	}

	public void setSensoryTestTypes(String sensoryTestTypes) {
		this.sensoryTestTypes = sensoryTestTypes;
	}

	@Column(name="sensory_test_types", nullable=true, length=20)	
	public String getSensoryTestTypes() {
		return sensoryTestTypes;
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

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	@Column(name="change_type", nullable=true, length=20)
	public String getChangeType() {
		return changeType;
	}

	public void setSourceProjectNumber(String sourceProjectNumber) {
		this.sourceProjectNumber = sourceProjectNumber;
	}

	@Column(name="source_project_number", nullable=true, length=30)
	public String getSourceProjectNumber() {
		return sourceProjectNumber;
	}

	public void setParentProjectNumber(String parentProjectNumber) {
		this.parentProjectNumber = parentProjectNumber;
	}

	@Column(name="parent_project_number", nullable=true, length=30)
	public String getParentProjectNumber() {
		return parentProjectNumber;
	}

	public void setCanChangeFlag(String canChangeFlag) {
		this.canChangeFlag = canChangeFlag;
	}

	@Column(name="can_change_flag", nullable=true, length=1)
	public String getCanChangeFlag() {
		return canChangeFlag;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	@Column(name="terminate_flag", nullable=true, length=1)
	public String getTerminateFlag() {
		return terminateFlag;
	}

	@Column(name="brand_team_person_id", nullable=true, length=22)
	public Integer getBrandTeamPersonId() {
		return brandTeamPersonId;
	}

	public void setBrandTeamPersonId(Integer brandTeamPersonId) {
		this.brandTeamPersonId = brandTeamPersonId;
	}

	@Column(name="brand_team_user_id", nullable=true, length=22)
	public Integer getBrandTeamUserId() {
		return brandTeamUserId;
	}

	public void setBrandTeamUserId(Integer brandTeamUserId) {
		this.brandTeamUserId = brandTeamUserId;
	}

	public void setBrandTeamPersonName(String brandTeamPersonName) {
		this.brandTeamPersonName = brandTeamPersonName;
	}

	@Column(name="brand_team_person_name", nullable=true, length=30)
	public String getBrandTeamPersonName() {
		return brandTeamPersonName;
	}

	public void setIsChangeProject(String isChangeProject) {
		this.isChangeProject = isChangeProject;
	}

	@Column(name="is_change_project", nullable=true, length=1)
	public String getIsChangeProject() {
		return isChangeProject;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
    @Column(name="department_name", nullable=true, length=30)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setPresentCooperationSupplierNumber(String presentCooperationSupplierNumber) {
		this.presentCooperationSupplierNumber = presentCooperationSupplierNumber;
	}

	@Column(name="present_cooperation_supplier_number", nullable=true, length=30)
	public String getPresentCooperationSupplierNumber() {
		return presentCooperationSupplierNumber;
	}

	@Column(name="qa_User_Number", nullable=true, length=300)
	public String getQaUserNumber() {
		return qaUserNumber;
	}

	public void setQaUserNumber(String qaUserNumber) {
		this.qaUserNumber = qaUserNumber;
	}

	@Column(name="ia_User_Number", nullable=true, length=300)
	public String getIaUserNumber() {
		return iaUserNumber;
	}

	public void setIaUserNumber(String iaUserNumber) {
		this.iaUserNumber = iaUserNumber;
	}

	@Column(name="security_User_Number", nullable=true, length=300)
	public String getSecurityUserNumber() {
		return securityUserNumber;
	}

	public void setSecurityUserNumber(String securityUserNumber) {
		this.securityUserNumber = securityUserNumber;
	}


	@Column(name="qa_User_Id", nullable=false, length=22)
	public Integer getQaUserId() {
		return qaUserId;
	}

	public void setQaUserId(Integer qaUserId) {
		this.qaUserId = qaUserId;
	}
	@Column(name="ia_User_Id", nullable=false, length=22)
	public Integer getIaUserId() {
		return iaUserId;
	}

	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}
	@Column(name="security_User_Id", nullable=false, length=22)
	public Integer getSecurityUserId() {
		return securityUserId;
	}

	public void setSecurityUserId(Integer securityUserId) {
		this.securityUserId = securityUserId;
	}

	@Column(name="terminate_reason", nullable=false, length=2000)
	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	@Column(name="node_path", nullable=true, length=20)
	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

    @Override
    public String toString() {
        return "EquPonProjectInfoEntity_HI{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projectNumber='" + projectNumber + '\'' +
                ", projectVersion='" + projectVersion + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", relevantCatetory='" + relevantCatetory + '\'' +
                ", projectCategory='" + projectCategory + '\'' +
                ", seriesName='" + seriesName + '\'' +
                ", projectPurchaseAmount=" + projectPurchaseAmount +
                ", projectCycleFrom=" + projectCycleFrom +
                ", projectCycleTo=" + projectCycleTo +
                ", quotationDeadline=" + quotationDeadline +
                ", sendQuotationInvitationDate=" + sendQuotationInvitationDate +
                ", scoringStandard='" + scoringStandard + '\'' +
                ", projectLeader='" + projectLeader + '\'' +
                ", presentCooperationSupplier='" + presentCooperationSupplier + '\'' +
                ", presentCooperationSupplierNumber='" + presentCooperationSupplierNumber + '\'' +
                ", sensoryTestTypes='" + sensoryTestTypes + '\'' +
                ", fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", remark='" + remark + '\'' +
                ", sourceProjectNumber='" + sourceProjectNumber + '\'' +
                ", parentProjectNumber='" + parentProjectNumber + '\'' +
                ", isChangeProject='" + isChangeProject + '\'' +
                ", canChangeFlag='" + canChangeFlag + '\'' +
                ", changeType='" + changeType + '\'' +
                ", terminateFlag='" + terminateFlag + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", brandTeamPersonId=" + brandTeamPersonId +
                ", brandTeamUserId=" + brandTeamUserId +
                ", brandTeamPersonName='" + brandTeamPersonName + '\'' +
                ", versionNum=" + versionNum +
                ", creationDate=" + creationDate +
                ", createdBy=" + createdBy +
                ", lastUpdatedBy=" + lastUpdatedBy +
                ", lastUpdateDate=" + lastUpdateDate +
                ", lastUpdateLogin=" + lastUpdateLogin +
                ", attributeCategory='" + attributeCategory + '\'' +
                ", attribute1='" + attribute1 + '\'' +
                ", attribute2='" + attribute2 + '\'' +
                ", attribute3='" + attribute3 + '\'' +
                ", attribute4='" + attribute4 + '\'' +
                ", attribute5='" + attribute5 + '\'' +
                ", attribute6='" + attribute6 + '\'' +
                ", attribute7='" + attribute7 + '\'' +
                ", attribute8='" + attribute8 + '\'' +
                ", attribute9='" + attribute9 + '\'' +
                ", attribute10='" + attribute10 + '\'' +
                ", operatorUserId=" + operatorUserId +
                ", departmentName='" + departmentName + '\'' +
                ", qaUserId=" + qaUserId +
                ", iaUserId=" + iaUserId +
                ", securityUserId=" + securityUserId +
                ", qaUserNumber='" + qaUserNumber + '\'' +
                ", iaUserNumber='" + iaUserNumber + '\'' +
                ", securityUserNumber='" + securityUserNumber + '\'' +
                ", terminateReason='" + terminateReason + '\'' +
                '}';
    }
}
