package com.sie.watsons.base.pos.creditAudit.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**   EquPosCreditAuditChangeHEntity_HI_RO.QUERY_AUDIT_SQL
 * EquPosCreditAuditChangeHEntity_HI_RO Entity Object
 * Sat Sep 21 10:50:41 CST 2019  Auto Generate
 */

public class EquPosCreditAuditChangeHEntity_HI_RO {
    private Integer changeCreditAuditHId;
    private String changeCreditAuditStatus;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
	private String changeCreditAuditCode;
	private String fileName;
	private String filePath;
    private String attribute1;
    private String attribute2;
    private String attribute3;
	private String remark;
	private String rejectReason;
	private Integer fileId;
    private String attribute4;
	private String processInstanceId;
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
    private Integer supplierId;
    private String supplierNumber;
	private String userName;
	private String supplierName;
	private String changeCreditAuditStatusName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
    private Integer operatorUserId;
	@JSONField(format="yyyy-MM-dd")
	private Date startDateActive;
	@JSONField(format="yyyy-MM-dd")
	private Date endDateActive;

	public void setChangeCreditAuditHId(Integer changeCreditAuditHId) {
		this.changeCreditAuditHId = changeCreditAuditHId;
	}

	
	public Integer getChangeCreditAuditHId() {
		return changeCreditAuditHId;
	}

	public void setChangeCreditAuditStatus(String changeCreditAuditStatus) {
		this.changeCreditAuditStatus = changeCreditAuditStatus;
	}

	
	public String getChangeCreditAuditStatus() {
		return changeCreditAuditStatus;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getRemark() {
		return remark;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
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

	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
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

	public String getChangeCreditAuditCode() {
		return changeCreditAuditCode;
	}

	public void setChangeCreditAuditCode(String changeCreditAuditCode) {
		this.changeCreditAuditCode = changeCreditAuditCode;
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

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	
	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getChangeCreditAuditStatusName() {
		return changeCreditAuditStatusName;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setChangeCreditAuditStatusName(String changeCreditAuditStatusName) {
		this.changeCreditAuditStatusName = changeCreditAuditStatusName;
	}

	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	
	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	
	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	
	public String getAttribute15() {
		return attribute15;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	
	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	
	public Date getApproveDate() {
		return approveDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public static String QUERY_AUDIT_SQL = "SELECT s.supplier_number supplierNumber\n" +
			"\t\t\t      ,s.supplier_name supplierName\n" +
			"            ,h.change_credit_audit_h_id changeCreditAuditHId\n" +
			"            ,h.change_Credit_Audit_Code changeCreditAuditCode\n" +
			"            ,h.change_credit_audit_status changeCreditAuditStatus\n" +
			"            ,h.remark remark\n" +
			"            ,h.reject_reason rejectReason \n" +
			"            ,h.created_by createdBy\n" +
			"            ,h.file_id fileId\n" +
			"            ,h.dept_code deptCode\n" +
			"            ,h.approve_date approveDate\n" +
			"            ,h.creation_date creationDate\n" +
			"            ,h.file_name fileName\n" +
			"            ,h.file_path filePath\n" +
			"      FROM   EQU_POS_CREDIT_AUDIT_CHANGE_H h\n" +
			"            ,EQU_POS_CREDIT_AUDIT_CHANGE   l\n" +
			"            ,equ_pos_supplier_info         s\n" +
			"      WHERE  l.change_credit_audit_h_id(+) = h.change_credit_audit_h_id\n" +
			"      AND    l.supplier_number = s.supplier_number(+)\n" +
			" ";
}
