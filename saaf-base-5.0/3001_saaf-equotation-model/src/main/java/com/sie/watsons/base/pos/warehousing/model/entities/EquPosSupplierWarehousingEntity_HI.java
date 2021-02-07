package com.sie.watsons.base.pos.warehousing.model.entities;

import java.math.BigDecimal;
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
 * EquPosSupplierWarehousingEntity_HI Entity Object
 * Mon Sep 16 16:45:35 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_POS_SUPPLIER_Warehousing")
public class EquPosSupplierWarehousingEntity_HI {
    private Integer supWarehousingId;
    private String supWarehousingCode;
    private String sceneType;
    private Integer supplierId;
    private String supplierNumber;
    private String supWarehousingStatus;
    private Integer qualificationId;
    private Integer supCreditAuditId;
    private Integer supCsrAuditId;
    private Integer supQualityAuditId;
    private String rejectReason;
    private String remark;
	private String deptCode;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date approveDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
	private String creditAuditScore;
	private String csrAuditScore;
	private BigDecimal qualityAuditScore;
	private String qualificationStatus;
	private String creditAuditResule;
	private String supCreditAuditStatus;
	private String creditAuditStatus;
	private String csrAuditResult;
	private String csrAuditStatus;
	private String qualityAuditResult;
	private String qualityAuditStatus;
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
    private Integer operatorUserId;

	public void setSupWarehousingId(Integer supWarehousingId) {
		this.supWarehousingId = supWarehousingId;
	}
	@Id
	@SequenceGenerator(name = "EQU_POS_SUPPLIER_Warehousing_S", sequenceName = "EQU_POS_SUPPLIER_Warehousing_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_POS_SUPPLIER_Warehousing_S", strategy = GenerationType.SEQUENCE)
	@Column(name="sup_warehousing_id", nullable=false, length=22)	
	public Integer getSupWarehousingId() {
		return supWarehousingId;
	}

	public void setSupWarehousingCode(String supWarehousingCode) {
		this.supWarehousingCode = supWarehousingCode;
	}

	@Column(name="sup_warehousing_code", nullable=true, length=240)	
	public String getSupWarehousingCode() {
		return supWarehousingCode;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	@Column(name="scene_type", nullable=true, length=240)	
	public String getSceneType() {
		return sceneType;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	@Column(name="supplier_number", nullable=true, length=240)	
	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupWarehousingStatus(String supWarehousingStatus) {
		this.supWarehousingStatus = supWarehousingStatus;
	}

	@Column(name="sup_warehousing_status", nullable=true, length=24)	
	public String getSupWarehousingStatus() {
		return supWarehousingStatus;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	@Column(name="qualification_id", nullable=true, length=22)	
	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setSupCreditAuditId(Integer supCreditAuditId) {
		this.supCreditAuditId = supCreditAuditId;
	}
	@Column(name="approve_Date", nullable=false, length=7)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name="sup_credit_audit_id", nullable=true, length=22)
	public Integer getSupCreditAuditId() {
		return supCreditAuditId;
	}

	public void setSupCsrAuditId(Integer supCsrAuditId) {
		this.supCsrAuditId = supCsrAuditId;
	}

	@Column(name="sup_csr_audit_id", nullable=true, length=22)	
	public Integer getSupCsrAuditId() {
		return supCsrAuditId;
	}

	public void setSupQualityAuditId(Integer supQualityAuditId) {
		this.supQualityAuditId = supQualityAuditId;
	}

	@Column(name="dept_code", nullable=true, length=30)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="sup_quality_audit_id", nullable=true, length=22)
	public Integer getSupQualityAuditId() {
		return supQualityAuditId;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	@Column(name="credit_Audit_Score", nullable=true, length=240)
	public String getCreditAuditScore() {
		return creditAuditScore;
	}

	public void setCreditAuditScore(String creditAuditScore) {
		this.creditAuditScore = creditAuditScore;
	}

	@Column(name="Csr_Audit_Score", nullable=true, length=240)
	public String getCsrAuditScore() {
		return csrAuditScore;
	}

	public void setCsrAuditScore(String csrAuditScore) {
		this.csrAuditScore = csrAuditScore;
	}

	@Column(name="Quality_Audit_Score", nullable=true, length=240)
	public BigDecimal getQualityAuditScore() {
		return qualityAuditScore;
	}

	public void setQualityAuditScore(BigDecimal qualityAuditScore) {
		this.qualityAuditScore = qualityAuditScore;
	}

	@Column(name="QUALIFICATION_STATUS", nullable=true, length=30)
	public String getQualificationStatus() {
		return qualificationStatus;
	}

	public void setQualificationStatus(String qualificationStatus) {
		this.qualificationStatus = qualificationStatus;
	}
	@Column(name="CREDIT_AUDIT_RESULE", nullable=true, length=30)
	public String getCreditAuditResule() {
		return creditAuditResule;
	}

	public void setCreditAuditResule(String creditAuditResule) {
		this.creditAuditResule = creditAuditResule;
	}
	@Column(name="CREDIT_AUDIT_STATUS", nullable=true, length=30)
	public String getCreditAuditStatus() {
		return creditAuditStatus;
	}

	public void setCreditAuditStatus(String creditAuditStatus) {
		this.creditAuditStatus = creditAuditStatus;
	}
	@Column(name="CSR_AUDIT_RESULT", nullable=true, length=30)
	public String getCsrAuditResult() {
		return csrAuditResult;
	}

	public void setCsrAuditResult(String csrAuditResult) {
		this.csrAuditResult = csrAuditResult;
	}

	@Column(name="CSR_AUDIT_Status", nullable=true, length=30)
	public String getCsrAuditStatus() {
		return csrAuditStatus;
	}

	public void setCsrAuditStatus(String csrAuditStatus) {
		this.csrAuditStatus = csrAuditStatus;
	}

	@Column(name="QUALITY_AUDIT_RESULT", nullable=true, length=30)
	public String getQualityAuditResult() {
		return qualityAuditResult;
	}

	public void setQualityAuditResult(String qualityAuditResult) {
		this.qualityAuditResult = qualityAuditResult;
	}
	@Column(name="QUALITY_AUDIT_STATUS", nullable=true, length=30)
	public String getQualityAuditStatus() {
		return qualityAuditStatus;
	}

	public void setQualityAuditStatus(String qualityAuditStatus) {
		this.qualityAuditStatus = qualityAuditStatus;
	}
	@Column(name="sup_Credit_Audit_Status", nullable=true, length=30)
	public String getSupCreditAuditStatus() {
		return supCreditAuditStatus;
	}

	public void setSupCreditAuditStatus(String supCreditAuditStatus) {
		this.supCreditAuditStatus = supCreditAuditStatus;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
