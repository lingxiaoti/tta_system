package com.sie.watsons.base.pos.creditAudit.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**  EquPosSupplierCreditAuditEntity_HI_RO.QUERY_QUAL_LOV_SQL
 * EquPosSupplierCreditAuditEntity_HI_RO Entity Object
 * Wed Sep 11 17:07:13 CST 2019  Auto Generate
 */

public class EquPosSupplierCreditAuditEntity_HI_RO {
    private Integer supCreditAuditId;
    private String supCreditAuditCode;
    private String supCreditAuditType;
	private String qualificationStatus;
    private String supCreditAuditStatus;
	private String creditAuditResuleName;
    private String pcnReportStatus;
	private String supplierNumber;
	private Integer supplierId;
	private String supplierStatus;
	private String supplierName;
	private String fileName;
	private String filePath;
	private String specialFileName;
	private String specialFilePath;
	private String supCreditAuditStatusName;
	private String supCreditAuditTypeName;
	private String supplierStatusName;
	private String qualificationNumber;
    private Integer qualificationId;
    private String qualificationCode;
	private String sceneTypeName;
	private String sceneType;
    private String creditAuditScore;
    private String creditAuditResule;
	private String userName;
	private String statusName;
    private Integer fileId;
	@JSONField(format="yyyy-MM-dd")
	private Date creditAuditDate;

	private String processInstanceId;
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
	private String specialResultsName;
	private String creditAuditScoreName;
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

	public void setSupCreditAuditId(Integer supCreditAuditId) {
		this.supCreditAuditId = supCreditAuditId;
	}

	
	public Integer getSupCreditAuditId() {
		return supCreditAuditId;
	}

	public void setSupCreditAuditCode(String supCreditAuditCode) {
		this.supCreditAuditCode = supCreditAuditCode;
	}

	
	public String getSupCreditAuditCode() {
		return supCreditAuditCode;
	}

	public void setSupCreditAuditType(String supCreditAuditType) {
		this.supCreditAuditType = supCreditAuditType;
	}

	
	public String getSupCreditAuditType() {
		return supCreditAuditType;
	}

	public void setSupCreditAuditStatus(String supCreditAuditStatus) {
		this.supCreditAuditStatus = supCreditAuditStatus;
	}

	public String getQualificationStatus() {
		return qualificationStatus;
	}

	public void setQualificationStatus(String qualificationStatus) {
		this.qualificationStatus = qualificationStatus;
	}

	public String getSceneType() {
		return sceneType;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	public String getSupCreditAuditStatus() {
		return supCreditAuditStatus;
	}

	public Date getCreditAuditDate() {
		return creditAuditDate;
	}

	public void setCreditAuditDate(Date creditAuditDate) {
		this.creditAuditDate = creditAuditDate;
	}

	public void setPcnReportStatus(String pcnReportStatus) {
		this.pcnReportStatus = pcnReportStatus;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getPcnReportStatus() {
		return pcnReportStatus;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public String getQualificationNumber() {
		return qualificationNumber;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setQualificationNumber(String qualificationNumber) {
		this.qualificationNumber = qualificationNumber;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setCreditAuditScore(String creditAuditScore) {
		this.creditAuditScore = creditAuditScore;
	}

	public String getCreditAuditResuleName() {
		return creditAuditResuleName;
	}

	public void setCreditAuditResuleName(String creditAuditResuleName) {
		this.creditAuditResuleName = creditAuditResuleName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreditAuditScore() {
		return creditAuditScore;
	}

	public void setCreditAuditResule(String creditAuditResule) {
		this.creditAuditResule = creditAuditResule;
	}

	public String getSpecialResultsName() {
		return specialResultsName;
	}

	public void setSpecialResultsName(String specialResultsName) {
		this.specialResultsName = specialResultsName;
	}

	public String getCreditAuditScoreName() {
		return creditAuditScoreName;
	}

	public void setCreditAuditScoreName(String creditAuditScoreName) {
		this.creditAuditScoreName = creditAuditScoreName;
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

	public String getSceneTypeName() {
		return sceneTypeName;
	}

	public void setSceneTypeName(String sceneTypeName) {
		this.sceneTypeName = sceneTypeName;
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

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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

	public String getSpecialFileName() {
		return specialFileName;
	}

	public void setSpecialFileName(String specialFileName) {
		this.specialFileName = specialFileName;
	}

	public String getSpecialFilePath() {
		return specialFilePath;
	}

	public void setSpecialFilePath(String specialFilePath) {
		this.specialFilePath = specialFilePath;
	}

	public String getSupCreditAuditStatusName() {
		return supCreditAuditStatusName;
	}

	public void setSupCreditAuditStatusName(String supCreditAuditStatusName) {
		this.supCreditAuditStatusName = supCreditAuditStatusName;
	}

	public String getSupplierStatusName() {
		return supplierStatusName;
	}

	public void setSupplierStatusName(String supplierStatusName) {
		this.supplierStatusName = supplierStatusName;
	}

	public String getSupCreditAuditTypeName() {
		return supCreditAuditTypeName;
	}

	public void setSupCreditAuditTypeName(String supCreditAuditTypeName) {
		this.supCreditAuditTypeName = supCreditAuditTypeName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public static String QUERY_AUDIT_SQL="SELECT t.sup_credit_audit_id supCreditAuditId\n" +
			"      ,t.supplier_id supplierId\n" +
			"      ,t.sup_credit_audit_type supCreditAuditType\n" +
			"      ,t.supplier_number supplierNumber\n" +
			"      ,T.SUP_CREDIT_AUDIT_CODE supCreditAuditCode\n" +
			"      ,t.sup_credit_audit_status supCreditAuditStatus\n" +
			"      ,pi.supplier_name supplierName\n" +
			"      ,t.qualification_id qualificationId\n" +
			"      ,t.qualification_code qualificationCode\n" +
			"      ,t.credit_audit_score creditAuditScore\n" +
			"      ,t.credit_audit_resule creditAuditResule\n" +
			"      ,decode(t.CREDIT_AUDIT_RESULE,\n" +
			"              'Y',\n" +
			"              '合格',\n" +
			"              'N',\n" +
			"              decode(t.SPECIAL_RESULTS,\n" +
			"                     NULL,\n" +
			"                     '不合格',\n" +
			"                     'Y',\n" +
			"                     '特批合格',\n" +
			"                     'N',\n" +
			"                     '特批不合格')) creditAuditResuleName\n" +
			"      ,T.supplier_status supplierStatus\n" +
			"      ,t.file_id fileId\n" +
			"      ,t.validity_period_date validityPeriodDate\n" +
			"      ,t.is_special isSpecial\n" +
			"      ,t.special_results specialResults\n" +
			"      ,t.special_file_id specialFileId\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,+t.reject_reason rejectReason\n" +
			"      ,t.file_name fileName\n" +
			"      ,t.file_path filePath\n" +
			"      ,t.SPECIAL_FILE_NAME specialFileName\n" +
			"      ,t.SPECIAL_FILE_PATH specialFilePath\n" +
			"      ,t.credit_Audit_Date credit_Audit_Date\n" +
			"      ,t.remark\n" +
			"FROM   Equ_Pos_Supplier_Credit_Audit t\n" +
			"      ,equ_pos_supplier_info         pi\n" +
			"WHERE  t.supplier_number = pi.supplier_number(+)";

	public static String QUERY_QUALIFICATION_LOV_SQL="select a.qualification_id qualificationId,a.scene_Type sceneType,a.qualification_status qualificationStatus,a.qualification_number qualificationNumber\n" +
			"   from equ_pos_qualification_info a where a.qualification_status = '50'";

	public static String QUERY_QUAL_LOV_SQL = "select a.qualification_id qualificationId,\n" +
			"       a.qualification_status qualificationStatus,\n" +
			"       a.qualification_number qualificationNumber,\n" +
			"       z.supplier_number supplierNumber,\n" +
			"       z.supplier_name supplierName,\n" +
			"       a.scene_Type sceneType,\n" +
			"       z.supplier_status supplierStatus,\n" +
			"       a.creation_date creationDate,\n" +
            "       a.created_by createdBy,\n" +
			"       a.supplier_id supplierId\n" +
			"  from equ_pos_qualification_info a, EQU_POS_ZZSC_SUPPLIER z\n" +
			" where a.qualification_status = '50'\n" +
			"   and a.supplier_id = z.supplier_id and a.qualification_id = z.qualification_id ";

}
