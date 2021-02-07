package com.sie.watsons.base.pos.creditAudit.model.entities;

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
 * EquPosCreditAuditChangeEntity_HI Entity Object
 * Sat Sep 21 10:50:40 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_POS_CREDIT_AUDIT_CHANGE")
public class EquPosCreditAuditChangeEntity_HI {
    private Integer changeCreditAuditId;
    private Integer changeCreditAuditHId;
    private Integer supCreditAuditId;
    private String changeCreditAuditCode;
    private String changeCreditAuditStatus;
    private String creditAuditScore;
    private String creditAuditResule;
    private Integer fileId;
    private String validityPeriodDate;
    private String isSpecial;
    private String specialResults;
    private Integer specialFileId;
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
	private String fileName;
	private String filePath;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private Integer supplierId;
    private String supplierNumber;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
	private String startDateActive;
	private String endDateActive;
    private Integer operatorUserId;

	public void setChangeCreditAuditId(Integer changeCreditAuditId) {
		this.changeCreditAuditId = changeCreditAuditId;
	}

	@Id
	@SequenceGenerator(name = "EQU_POS_CREDIT_AUDIT_CHANGE_S", sequenceName = "EQU_POS_CREDIT_AUDIT_CHANGE_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_POS_CREDIT_AUDIT_CHANGE_S", strategy = GenerationType.SEQUENCE)
	@Column(name="change_credit_audit_id", nullable=false, length=22)	
	public Integer getChangeCreditAuditId() {
		return changeCreditAuditId;
	}

	public void setChangeCreditAuditHId(Integer changeCreditAuditHId) {
		this.changeCreditAuditHId = changeCreditAuditHId;
	}

	@Column(name="change_credit_audit_h_id", nullable=false, length=22)	
	public Integer getChangeCreditAuditHId() {
		return changeCreditAuditHId;
	}

	public void setSupCreditAuditId(Integer supCreditAuditId) {
		this.supCreditAuditId = supCreditAuditId;
	}

	@Column(name="sup_credit_audit_id", nullable=true, length=22)	
	public Integer getSupCreditAuditId() {
		return supCreditAuditId;
	}

	public void setChangeCreditAuditCode(String changeCreditAuditCode) {
		this.changeCreditAuditCode = changeCreditAuditCode;
	}

	@Column(name="change_credit_audit_code", nullable=false, length=200)	
	public String getChangeCreditAuditCode() {
		return changeCreditAuditCode;
	}

	public void setChangeCreditAuditStatus(String changeCreditAuditStatus) {
		this.changeCreditAuditStatus = changeCreditAuditStatus;
	}

	@Column(name="change_credit_audit_status", nullable=true, length=200)	
	public String getChangeCreditAuditStatus() {
		return changeCreditAuditStatus;
	}

	public void setCreditAuditScore(String creditAuditScore) {
		this.creditAuditScore = creditAuditScore;
	}

	@Column(name="credit_audit_score", nullable=true, length=240)	
	public String getCreditAuditScore() {
		return creditAuditScore;
	}

	public void setCreditAuditResule(String creditAuditResule) {
		this.creditAuditResule = creditAuditResule;
	}
	@Column(name="file_Name", nullable=true, length=240)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name="file_Path", nullable=true, length=240)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="credit_audit_resule", nullable=true, length=240)
	public String getCreditAuditResule() {
		return creditAuditResule;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=true, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setValidityPeriodDate(String validityPeriodDate) {
		this.validityPeriodDate = validityPeriodDate;
	}

	@Column(name="validity_period_date", nullable=true, length=240)	
	public String getValidityPeriodDate() {
		return validityPeriodDate;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	@Column(name="is_special", nullable=true, length=240)	
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setSpecialResults(String specialResults) {
		this.specialResults = specialResults;
	}

	@Column(name="special_results", nullable=true, length=240)	
	public String getSpecialResults() {
		return specialResults;
	}

	public void setSpecialFileId(Integer specialFileId) {
		this.specialFileId = specialFileId;
	}

	@Column(name="special_file_id", nullable=true, length=22)	
	public Integer getSpecialFileId() {
		return specialFileId;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

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

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name="approve_date", nullable=true, length=7)	
	public Date getApproveDate() {
		return approveDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
	@Column(name="start_Date_Active", nullable=true, length=7)
	public String getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(String startDateActive) {
		this.startDateActive = startDateActive;
	}
	@Column(name="end_Date_Active", nullable=true, length=7)
	public String getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(String endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
