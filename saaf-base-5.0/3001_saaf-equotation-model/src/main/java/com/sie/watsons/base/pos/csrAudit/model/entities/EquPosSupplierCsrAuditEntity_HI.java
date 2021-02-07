package com.sie.watsons.base.pos.csrAudit.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * EquPosSupplierCsrAuditEntity_HI Entity Object
 * Tue Sep 17 16:35:50 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_POS_SUPPLIER_CSR_AUDIT")
public class EquPosSupplierCsrAuditEntity_HI {
    private Integer csrAuditId;
    private Integer supplierId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
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
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private String sceneType;
    private String csrAuditNumber;
    private String csrAuditStatus;
    private String qualificationAuditNumber;
    private String creditAuditNumber;
    private String isExemption;
    private String csrAuditScore;
    private String csrAuditResult;
    @JSONField(format="yyyy-MM-dd")
    private Date effectiveDate;
    private Integer csrReportId;
    private String csrReportName;
    private String csrReportPath;
    private String remark;
    private String deptCode;
	private Integer capRectificationSummaryId;		//CAP整改汇总文件ID
	private String capRectificationSummaryName;		//CAP整改汇总文件名称
	private String capRectificationSummaryPath;		//CAP整改汇总文件路径
    private Integer operatorUserId;

	public void setCsrAuditId(Integer csrAuditId) {
		this.csrAuditId = csrAuditId;
	}

	@Id
	@SequenceGenerator(name = "EQU_POS_SUPPLIER_SCR_AUDIT_S", sequenceName = "EQU_POS_SUPPLIER_SCR_AUDIT_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_POS_SUPPLIER_SCR_AUDIT_S", strategy = GenerationType.SEQUENCE)
	@Column(name="csr_audit_id", nullable=false, length=22)	
	public Integer getCsrAuditId() {
		return csrAuditId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
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

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	@Column(name="attribute11", nullable=true, length=240)	
	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	@Column(name="attribute12", nullable=true, length=240)	
	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	@Column(name="attribute13", nullable=true, length=240)	
	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	@Column(name="attribute14", nullable=true, length=240)	
	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	@Column(name="attribute15", nullable=true, length=240)	
	public String getAttribute15() {
		return attribute15;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	@Column(name="scene_type", nullable=true, length=30)	
	public String getSceneType() {
		return sceneType;
	}

	public void setCsrAuditNumber(String csrAuditNumber) {
		this.csrAuditNumber = csrAuditNumber;
	}

	@Column(name="csr_audit_number", nullable=true, length=20)	
	public String getCsrAuditNumber() {
		return csrAuditNumber;
	}

	public void setCsrAuditStatus(String csrAuditStatus) {
		this.csrAuditStatus = csrAuditStatus;
	}

	@Column(name="csr_audit_status", nullable=true, length=20)	
	public String getCsrAuditStatus() {
		return csrAuditStatus;
	}

	public void setQualificationAuditNumber(String qualificationAuditNumber) {
		this.qualificationAuditNumber = qualificationAuditNumber;
	}

	@Column(name="qualification_audit_number", nullable=true, length=20)	
	public String getQualificationAuditNumber() {
		return qualificationAuditNumber;
	}

	public void setCreditAuditNumber(String creditAuditNumber) {
		this.creditAuditNumber = creditAuditNumber;
	}

	@Column(name="credit_audit_number", nullable=true, length=20)	
	public String getCreditAuditNumber() {
		return creditAuditNumber;
	}

	public void setIsExemption(String isExemption) {
		this.isExemption = isExemption;
	}

	@Column(name="is_exemption", nullable=true, length=1)	
	public String getIsExemption() {
		return isExemption;
	}

	public void setCsrAuditScore(String csrAuditScore) {
		this.csrAuditScore = csrAuditScore;
	}

    @Column(name="csr_audit_score", nullable=true, length=20)
    public String getCsrAuditScore() {
		return csrAuditScore;
	}

	public void setCsrAuditResult(String csrAuditResult) {
		this.csrAuditResult = csrAuditResult;
	}

	@Column(name="csr_audit_result", nullable=true, length=20)	
	public String getCsrAuditResult() {
		return csrAuditResult;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name="effective_date", nullable=true, length=7)	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setCsrReportId(Integer csrReportId) {
		this.csrReportId = csrReportId;
	}

	@Column(name="csr_report_id", nullable=true, length=22)	
	public Integer getCsrReportId() {
		return csrReportId;
	}

	public void setCsrReportName(String csrReportName) {
		this.csrReportName = csrReportName;
	}

	@Column(name="csr_report_name", nullable=true, length=100)	
	public String getCsrReportName() {
		return csrReportName;
	}

	public void setCsrReportPath(String csrReportPath) {
		this.csrReportPath = csrReportPath;
	}

	@Column(name="csr_report_path", nullable=true, length=240)	
	public String getCsrReportPath() {
		return csrReportPath;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=4000)
	public String getRemark() {
		return remark;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=20)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setCapRectificationSummaryId(Integer capRectificationSummaryId) {
		this.capRectificationSummaryId = capRectificationSummaryId;
	}

	@Column(name="cap_rectification_summary_id", nullable=true, length=22)
	public Integer getCapRectificationSummaryId() {
		return capRectificationSummaryId;
	}

	public void setCapRectificationSummaryPath(String capRectificationSummaryPath) {
		this.capRectificationSummaryPath = capRectificationSummaryPath;
	}

	@Column(name="cap_rectification_summary_path", nullable=true, length=240)
	public String getCapRectificationSummaryPath() {
		return capRectificationSummaryPath;
	}

	public void setCapRectificationSummaryName(String capRectificationSummaryName) {
		this.capRectificationSummaryName = capRectificationSummaryName;
	}

	@Column(name="cap_rectification_summary_name", nullable=true, length=100)
	public String getCapRectificationSummaryName() {
		return capRectificationSummaryName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
