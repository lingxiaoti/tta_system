package com.sie.watsons.base.pos.creditAudit.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * EquPosCreditAuditChangeEntity_HI_RO Entity Object
 * Sat Sep 21 10:50:40 CST 2019  Auto Generate
 */

public class EquPosCreditAuditChangeEntity_HI_RO {
    private Integer changeCreditAuditId;
	private Integer index;
    private Integer changeCreditAuditHId;
    private Integer supCreditAuditId;
	private String sceneTypeName;
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
	private String supplierName;
    private Integer supplierId;
    private String supplierNumber;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
	private String startDateActive;
	private String endDateActive ;
	private String userName;
    private Integer operatorUserId;

	public void setChangeCreditAuditId(Integer changeCreditAuditId) {
		this.changeCreditAuditId = changeCreditAuditId;
	}

	
	public Integer getChangeCreditAuditId() {
		return changeCreditAuditId;
	}

	public void setChangeCreditAuditHId(Integer changeCreditAuditHId) {
		this.changeCreditAuditHId = changeCreditAuditHId;
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

	public Integer getChangeCreditAuditHId() {
		return changeCreditAuditHId;
	}

	public void setSupCreditAuditId(Integer supCreditAuditId) {
		this.supCreditAuditId = supCreditAuditId;
	}

	
	public Integer getSupCreditAuditId() {
		return supCreditAuditId;
	}

	public void setChangeCreditAuditCode(String changeCreditAuditCode) {
		this.changeCreditAuditCode = changeCreditAuditCode;
	}

	public String getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(String startDateActive) {
		this.startDateActive = startDateActive;
	}

	public String getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(String endDateActive) {
		this.endDateActive = endDateActive;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}


	public String getChangeCreditAuditCode() {
		return changeCreditAuditCode;
	}

	public void setChangeCreditAuditStatus(String changeCreditAuditStatus) {
		this.changeCreditAuditStatus = changeCreditAuditStatus;
	}

	
	public String getChangeCreditAuditStatus() {
		return changeCreditAuditStatus;
	}

	public void setCreditAuditScore(String creditAuditScore) {
		this.creditAuditScore = creditAuditScore;
	}

	public String getSceneTypeName() {
		return sceneTypeName;
	}

	public void setSceneTypeName(String sceneTypeName) {
		this.sceneTypeName = sceneTypeName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCreditAuditScore() {
		return creditAuditScore;
	}

	public void setCreditAuditResule(String creditAuditResule) {
		this.creditAuditResule = creditAuditResule;
	}

	
	public String getCreditAuditResule() {
		return creditAuditResule;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setValidityPeriodDate(String validityPeriodDate) {
		this.validityPeriodDate = validityPeriodDate;
	}

	
	public String getValidityPeriodDate() {
		return validityPeriodDate;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setSpecialResults(String specialResults) {
		this.specialResults = specialResults;
	}

	
	public String getSpecialResults() {
		return specialResults;
	}

	public void setSpecialFileId(Integer specialFileId) {
		this.specialFileId = specialFileId;
	}

	
	public Integer getSpecialFileId() {
		return specialFileId;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
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

	public static String   QUERY_LINE_SQL = " SELECT a.start_date_active startDateActive\n" +
			"      ,a.end_date_active endDateActive\n" +
			"      ,'年度信用审核' sceneTypeName\n" +
			"      ,b.supplier_name supplierName\n" +
			"      ,a.change_credit_audit_id as \"index\"\n" +
			"      ,a.approve_date approveDate\n" +
			"      ,a.change_credit_audit_id changeCreditAuditId\n" +
			"      ,a.change_credit_audit_h_id changeCreditAuditHId\n" +
			"      ,a.credit_audit_score creditAuditScore\n" +
			"      ,a.file_name fileName\n" +
			"      ,a.file_path filePath\n" +
			"      ,a.credit_audit_resule creditAuditResule\n" +
			"      ,a.file_id fileId\n" +
			"      ,a.creation_date creationDate\n" +
			"      ,a.version_num versionNum\n" +
			"      ,a.supplier_id supplierId\n" +
			"      ,a.supplier_number supplierNumber\n" +
			"      ,a.created_by createdBy\n" +
			"      ,a.last_update_date lastUpdateDate\n" +
			"FROM   EQU_POS_CREDIT_AUDIT_CHANGE a\n" +
			"      ,equ_pos_supplier_info       b\n" +
			"WHERE  a.supplier_id = b.supplier_id(+)";

	public static String   QUERY_SUP_SQL = "SELECT a.supplier_number supplierNumber, a.supplier_id supplierId FROM EQU_POS_SUPPLIER_INFO A where 1 = 1";
}
