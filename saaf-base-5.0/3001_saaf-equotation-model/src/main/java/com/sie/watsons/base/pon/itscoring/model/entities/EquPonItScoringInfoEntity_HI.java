package com.sie.watsons.base.pon.itscoring.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonItScoringInfoEntity_HI Entity Object
 * Mon Jul 13 16:48:18 CST 2020  Auto Generate
 */
@Entity
@Table(name="equ_pon_it_scoring_info")
public class EquPonItScoringInfoEntity_HI {
    private Integer scoringId;
    private String scoringNumber;
    private String scoringStatus;
    private String deptCode;
    private Integer informationId;
    private String supplierConfirmFlag;
    private String nonPriceCalculateFlag;
    private String departmentScoringFlag;
    private Integer projectId;
    private String description;
    private Integer fileId;
    private String fileName;
    private String filePath;
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

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_IT_SCORING_INFO_S", sequenceName = "EQU_PON_IT_SCORING_INFO_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_IT_SCORING_INFO_S", strategy = GenerationType.SEQUENCE)
	@Column(name="scoring_id", nullable=false, length=22)	
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

	public void setScoringStatus(String scoringStatus) {
		this.scoringStatus = scoringStatus;
	}

	@Column(name="scoring_status", nullable=true, length=30)	
	public String getScoringStatus() {
		return scoringStatus;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=30)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	@Column(name="information_id", nullable=true, length=22)	
	public Integer getInformationId() {
		return informationId;
	}

	public void setSupplierConfirmFlag(String supplierConfirmFlag) {
		this.supplierConfirmFlag = supplierConfirmFlag;
	}

	@Column(name="supplier_confirm_flag", nullable=true, length=1)	
	public String getSupplierConfirmFlag() {
		return supplierConfirmFlag;
	}

	public void setNonPriceCalculateFlag(String nonPriceCalculateFlag) {
		this.nonPriceCalculateFlag = nonPriceCalculateFlag;
	}

	@Column(name="non_price_calculate_flag", nullable=true, length=1)	
	public String getNonPriceCalculateFlag() {
		return nonPriceCalculateFlag;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=true, length=22)	
	public Integer getProjectId() {
		return projectId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=1000)	
	public String getDescription() {
		return description;
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

	@Column(name="department_scoring_flag", nullable=true, length=1)
	public String getDepartmentScoringFlag() {
		return departmentScoringFlag;
	}

	public void setDepartmentScoringFlag(String departmentScoringFlag) {
		this.departmentScoringFlag = departmentScoringFlag;
	}

	@Column(name="file_id", nullable=true, length=22)
	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_name", nullable=true, length=500)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_path", nullable=true, length=1000)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
