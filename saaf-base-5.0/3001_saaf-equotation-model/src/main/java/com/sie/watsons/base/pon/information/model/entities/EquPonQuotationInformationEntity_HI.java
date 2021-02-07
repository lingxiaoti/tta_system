package com.sie.watsons.base.pon.information.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * EquPonQuotationInformationEntity_HI Entity Object
 * Thu Oct 10 10:15:49 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_QUOTATION_INFORMATION")
public class EquPonQuotationInformationEntity_HI {
	private Integer informationId;
	private String informationCode;
	private String informationStatus;
	private Integer projectId;
	private Integer fileId;
	private String projectNumber;
	private String projectLeader;
	private String rejectionReasons;
	private String fileName;
	private String filePath;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstRoundTime;
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
	private Integer standardsId;
	private Integer qaUserId;
	private Integer iaUserId;
	private Integer securityUserId;
	private String standardsCode;
	private String deptCode;
	private String deptName;
	private String projectName;
	private String projectVersion;
	private String projectStatus;
	private String relevantCatetory;
	private String projectCategory;
	private String seriesName;
	private BigDecimal projectPurchaseAmount;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date projectCycleFrom;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date projectCycleTo;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date quotationDeadline;
	private String presentCooperationSupplier;
	private String sensoryTestTypes;
	private String remark;
	private Integer operatorUserId;


	private String qaUserNumber;
	private String 	iaUserNumber;
	private String securityUserNumber;
	private Integer scoringId;

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_standards_H_S", sequenceName = "EQU_PON_standards_H_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_standards_H_S", strategy = GenerationType.SEQUENCE)
	@Column(name="information_id", nullable=false, length=22)	
	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationCode(String informationCode) {
		this.informationCode = informationCode;
	}

	@Column(name="information_code", nullable=true, length=30)
	public String getInformationCode() {
		return informationCode;
	}

	public void setInformationStatus(String informationStatus) {
		this.informationStatus = informationStatus;
	}
	@Column(name="dept_Name", nullable=true, length=300)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="information_status", nullable=true, length=30)
	public String getInformationStatus() {
		return informationStatus;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=true, length=22)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
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

	@Column(name="project_number", nullable=true, length=30)
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	@Column(name="project_leader", nullable=true, length=30)
	public String getProjectLeader() {
		return projectLeader;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
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

	@Column(name="rejection_reasons", nullable=true, length=300)
	public String getRejectionReasons() {
		return rejectionReasons;
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
	@Column(name="First_Round_Time", nullable=false, length=7)
	public Date getFirstRoundTime() {
		return firstRoundTime;
	}

	public void setFirstRoundTime(Date firstRoundTime) {
		this.firstRoundTime = firstRoundTime;
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

	public void setStandardsId(Integer standardsId) {
		this.standardsId = standardsId;
	}

	@Column(name="standards_id", nullable=true, length=22)
	public Integer getStandardsId() {
		return standardsId;
	}

	public void setStandardsCode(String standardsCode) {
		this.standardsCode = standardsCode;
	}

	@Column(name="standards_code", nullable=true, length=30)
	public String getStandardsCode() {
		return standardsCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=20)
	public String getDeptCode() {
		return deptCode;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name="project_name", nullable=true, length=200)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	@Column(name="project_version", nullable=true, length=5)
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

	@Column(name="quotation_deadline", nullable=true, length=7)
	public Date getQuotationDeadline() {
		return quotationDeadline;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=4000)
	public String getRemark() {
		return remark;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	@Column(name="scoring_id", nullable=false, length=22)
	public Integer getScoringId() {
		return scoringId;
	}
}
