package com.sie.watsons.base.pon.scoring.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * EquPonScoringInfoEntity_HI Entity Object
 * Wed Oct 16 09:53:55 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pon_scoring_info")
public class EquPonScoringInfoEntity_HI {
    private Integer scoringId;
    private String scoringNumber;
    private String scoringStatus;
    private String deptCode;
    private Integer informationId;
    private Integer projectId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date firstOpenDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date secondOpenDate;
    private String supplierConfirmFlag;
    private String nonPriceCalculateFlag;
    private String factoryAuditFlag;
    private String panelTestFlag;
    private String terminateFlag;
	private Integer innovationFileId;
	private String innovationFileName;
	private String innovationFilePath;
	private Integer pkgInnovationFileId;
	private String pkgInnovationFileName;
	private String pkgInnovationFilePath;
	private Integer panelTestFileId;
	private String panelTestFileName;
	private String panelTestFilePath;
	private String effectiveAccurateRemark;
	private String paymentTermsRemark;
	private String panelTestRemark;
	private String innovationRemark;
	private String pkgInnovationRemark;
	private String description;
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
	@SequenceGenerator(name = "EQU_PON_SCORING_INFO_S", sequenceName = "EQU_PON_SCORING_INFO_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_SCORING_INFO_S", strategy = GenerationType.SEQUENCE)
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

	public void setSupplierConfirmFlag(String supplierConfirmFlag) {
		this.supplierConfirmFlag = supplierConfirmFlag;
	}

	@Column(name="supplier_confirm_flag", nullable=true, length=1)
	public String getSupplierConfirmFlag() {
		return supplierConfirmFlag;
	}

	public void setFactoryAuditFlag(String factoryAuditFlag) {
		this.factoryAuditFlag = factoryAuditFlag;
	}

	@Column(name="factory_audit_flag", nullable=true, length=1)
	public String getFactoryAuditFlag() {
		return factoryAuditFlag;
	}

	public void setPanelTestFlag(String panelTestFlag) {
		this.panelTestFlag = panelTestFlag;
	}

	@Column(name="panel_test_flag", nullable=true, length=1)
	public String getPanelTestFlag() {
		return panelTestFlag;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	@Column(name="terminate_flag", nullable=true, length=1)
	public String getTerminateFlag() {
		return terminateFlag;
	}

	public void setNonPriceCalculateFlag(String nonPriceCalculateFlag) {
		this.nonPriceCalculateFlag = nonPriceCalculateFlag;
	}

	@Column(name="non_price_calculate_flag", nullable=true, length=1)
	public String getNonPriceCalculateFlag() {
		return nonPriceCalculateFlag;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	@Column(name="information_id", nullable=true, length=22)	
	public Integer getInformationId() {
		return informationId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=true, length=22)
	public Integer getProjectId() {
		return projectId;
	}

	public void setFirstOpenDate(Date firstOpenDate) {
		this.firstOpenDate = firstOpenDate;
	}

	@Column(name="first_open_date", nullable=true, length=7)	
	public Date getFirstOpenDate() {
		return firstOpenDate;
	}

	public void setSecondOpenDate(Date secondOpenDate) {
		this.secondOpenDate = secondOpenDate;
	}

	@Column(name="second_open_date", nullable=true, length=7)	
	public Date getSecondOpenDate() {
		return secondOpenDate;
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

	@Column(name="innovation_file_id", nullable=true, length=22)
	public Integer getInnovationFileId() {
		return innovationFileId;
	}

	public void setInnovationFileId(Integer innovationFileId) {
		this.innovationFileId = innovationFileId;
	}

	@Column(name="innovation_file_name", nullable=true, length=240)
	public String getInnovationFileName() {
		return innovationFileName;
	}

	public void setInnovationFileName(String innovationFileName) {
		this.innovationFileName = innovationFileName;
	}

	@Column(name="innovation_file_path", nullable=true, length=500)
	public String getInnovationFilePath() {
		return innovationFilePath;
	}

	public void setInnovationFilePath(String innovationFilePath) {
		this.innovationFilePath = innovationFilePath;
	}

	@Column(name="pkg_innovation_file_id", nullable=true, length=22)
	public Integer getPkgInnovationFileId() {
		return pkgInnovationFileId;
	}

	public void setPkgInnovationFileId(Integer pkgInnovationFileId) {
		this.pkgInnovationFileId = pkgInnovationFileId;
	}

	@Column(name="pkg_innovation_file_name", nullable=true, length=240)
	public String getPkgInnovationFileName() {
		return pkgInnovationFileName;
	}

	public void setPkgInnovationFileName(String pkgInnovationFileName) {
		this.pkgInnovationFileName = pkgInnovationFileName;
	}

	@Column(name="pkg_innovation_file_path", nullable=true, length=500)
	public String getPkgInnovationFilePath() {
		return pkgInnovationFilePath;
	}

	public void setPkgInnovationFilePath(String pkgInnovationFilePath) {
		this.pkgInnovationFilePath = pkgInnovationFilePath;
	}

	@Column(name="panel_test_file_id", nullable=true, length=22)
	public Integer getPanelTestFileId() {
		return panelTestFileId;
	}

	public void setPanelTestFileId(Integer panelTestFileId) {
		this.panelTestFileId = panelTestFileId;
	}

	@Column(name="panel_test_file_name", nullable=true, length=240)
	public String getPanelTestFileName() {
		return panelTestFileName;
	}

	public void setPanelTestFileName(String panelTestFileName) {
		this.panelTestFileName = panelTestFileName;
	}

	@Column(name="panel_test_file_path", nullable=true, length=500)
	public String getPanelTestFilePath() {
		return panelTestFilePath;
	}

	public void setPanelTestFilePath(String panelTestFilePath) {
		this.panelTestFilePath = panelTestFilePath;
	}

	@Column(name="effective_accurate_remark", nullable=true, length=4000)
	public String getEffectiveAccurateRemark() {
		return effectiveAccurateRemark;
	}

	public void setEffectiveAccurateRemark(String effectiveAccurateRemark) {
		this.effectiveAccurateRemark = effectiveAccurateRemark;
	}

	@Column(name="payment_terms_remark", nullable=true, length=4000)
	public String getPaymentTermsRemark() {
		return paymentTermsRemark;
	}

	public void setPaymentTermsRemark(String paymentTermsRemark) {
		this.paymentTermsRemark = paymentTermsRemark;
	}

	@Column(name="description", nullable=true, length=1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="panel_test_remark", nullable=true, length=4000)
	public String getPanelTestRemark() {
		return panelTestRemark;
	}

	public void setPanelTestRemark(String panelTestRemark) {
		this.panelTestRemark = panelTestRemark;
	}

	@Column(name="innovation_remark", nullable=true, length=4000)
	public String getInnovationRemark() {
		return innovationRemark;
	}

	public void setInnovationRemark(String innovationRemark) {
		this.innovationRemark = innovationRemark;
	}

	@Column(name="pkg_innovation_remark", nullable=true, length=4000)
	public String getPkgInnovationRemark() {
		return pkgInnovationRemark;
	}

	public void setPkgInnovationRemark(String pkgInnovationRemark) {
		this.pkgInnovationRemark = pkgInnovationRemark;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
