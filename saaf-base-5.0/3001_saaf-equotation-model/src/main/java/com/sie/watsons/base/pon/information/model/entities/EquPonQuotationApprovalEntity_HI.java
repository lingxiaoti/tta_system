package com.sie.watsons.base.pon.information.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * EquPonQuotationApprovalEntity_HI Entity Object
 * Mon Oct 21 11:32:37 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_QUOTATION_APPROVAL")
public class EquPonQuotationApprovalEntity_HI {
    private Integer approvalId;
    private String approvalNumber;
    private String approvalName;
    private Integer projectId;
    private String projectName;
    private String projectNumber;
	private String seriesName;
	private Integer fileId;
	private String fileName;
	private String filePath;
    private String approvalStatus;
    private String approvalType;
    private String orderType;
    private String deptCode;
    private String relevantCatetory;
    private String nodePath;
    private String projectCategory;
	private Integer scoringId;
	private String scoringNumber;
    private BigDecimal projectPurchaseAmount;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date projectCycleFrom;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date projectCycleTo;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date quotationDeadline;
    private String scoringStandard;
    private String presentCooperationSupplier;
    private String rejectReason;
    private String remark;
	private Integer qaUserId;
	private Integer iaUserId;
	private Integer securityUserId;
	private String qaUserNumber;
	private String 	iaUserNumber;
	private String securityUserNumber;
    private Integer informationId;
    private String informationCode;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date secondQuotationDeadline;
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
    private Integer brandTeamUserId;
    private String terminateFlag;
	private String purchaseCompany;
	private String unionDeptmentName;
	private Integer inspectionListFileId;
	private String inspectionListFileName;
	private String inspectionListFilePath;
	private String sendQuotationResultFlag;

	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_QUOTATION_APPROVAL_S", sequenceName = "EQU_PON_QUOTATION_APPROVAL_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_QUOTATION_APPROVAL_S", strategy = GenerationType.SEQUENCE)
	@Column(name="approval_id", nullable=true, length=22)	
	public Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	@Column(name="approval_number", nullable=true, length=30)	
	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	@Column(name="approval_name", nullable=true, length=240)	
	public String getApprovalName() {
		return approvalName;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=true, length=22)	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name="project_name", nullable=true, length=240)	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	@Column(name="series_Name", nullable=true, length=240)
	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	@Column(name="project_number", nullable=true, length=30)
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Column(name="approval_status", nullable=true, length=30)	
	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	@Column(name="file_Id", nullable=true, length=22)
	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_Name", nullable=true, length=300)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_Path", nullable=true, length=300)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="approval_type", nullable=true, length=30)	
	public String getApprovalType() {
		return approvalType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name="order_type", nullable=true, length=30)	
	public String getOrderType() {
		return orderType;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=30)	
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

	@Column(name="node_path", nullable=true, length=20)
	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	@Column(name="second_Quotation_Deadline", nullable=true, length=20)
	public Date getSecondQuotationDeadline() {
		return secondQuotationDeadline;
	}

	public void setSecondQuotationDeadline(Date secondQuotationDeadline) {
		this.secondQuotationDeadline = secondQuotationDeadline;
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

	@Column(name="project_category", nullable=true, length=20)	
	public String getProjectCategory() {
		return projectCategory;
	}



	public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
		this.projectPurchaseAmount = projectPurchaseAmount;
	}

	@Column(name="project_purchase_amount", nullable=true, length=22)	
	public BigDecimal getProjectPurchaseAmount() {
		return projectPurchaseAmount;
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

	@Column(name="project_cycle_to", nullable=true, length=7)	
	public Date getProjectCycleTo() {
		return projectCycleTo;
	}

	public void setQuotationDeadline(Date quotationDeadline) {
		this.quotationDeadline = quotationDeadline;
	}
	@Column(name="scoring_Id", nullable=true, length=22)
	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringNumber(String scoringNumber) {
		this.scoringNumber = scoringNumber;
	}

	@Column(name="scoring_number", nullable=false, length=30)
	public String getScoringNumber() {
		return scoringNumber;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
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

	public void setPresentCooperationSupplier(String presentCooperationSupplier) {
		this.presentCooperationSupplier = presentCooperationSupplier;
	}

	@Column(name="present_cooperation_supplier", nullable=true, length=100)	
	public String getPresentCooperationSupplier() {
		return presentCooperationSupplier;
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

	@Column(name="remark", nullable=true, length=240)	
	public String getRemark() {
		return remark;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	@Column(name="information_id", nullable=true, length=22)
	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationCode(String informationCode) {
		this.informationCode = informationCode;
	}

	@Column(name="information_Code", nullable=true, length=30)
	public String getInformationCode() {
		return informationCode;
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

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setBrandTeamUserId(Integer brandTeamUserId) {
		this.brandTeamUserId = brandTeamUserId;
	}

	@Column(name="brand_team_user_id", nullable=true, length=22)
	public Integer getBrandTeamUserId() {
		return brandTeamUserId;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	@Column(name="terminate_flag", nullable=false, length=1)
	public String getTerminateFlag() {
		return terminateFlag;
	}

	public void setPurchaseCompany(String purchaseCompany) {
		this.purchaseCompany = purchaseCompany;
	}

	@Column(name="purchase_company", nullable=true, length=20)
	public String getPurchaseCompany() {
		return purchaseCompany;
	}

	public void setUnionDeptmentName(String unionDeptmentName) {
		this.unionDeptmentName = unionDeptmentName;
	}

	@Column(name="union_deptment_name", nullable=true, length=200)
	public String getUnionDeptmentName() {
		return unionDeptmentName;
	}

	@Column(name="inspection_list_file_id", nullable=true, length=22)
	public Integer getInspectionListFileId() {
		return inspectionListFileId;
	}

	public void setInspectionListFileId(Integer inspectionListFileId) {
		this.inspectionListFileId = inspectionListFileId;
	}

	@Column(name="inspection_list_file_name", nullable=true, length=500)
	public String getInspectionListFileName() {
		return inspectionListFileName;
	}

	public void setInspectionListFileName(String inspectionListFileName) {
		this.inspectionListFileName = inspectionListFileName;
	}

	@Column(name="inspection_list_file_path", nullable=true, length=1000)
	public String getInspectionListFilePath() {
		return inspectionListFilePath;
	}

	public void setInspectionListFilePath(String inspectionListFilePath) {
		this.inspectionListFilePath = inspectionListFilePath;
	}

	@Column(name="send_quotation_result_flag", nullable=true, length=1)
	public String getSendQuotationResultFlag() {
		return sendQuotationResultFlag;
	}

	public void setSendQuotationResultFlag(String sendQuotationResultFlag) {
		this.sendQuotationResultFlag = sendQuotationResultFlag;
	}
}
