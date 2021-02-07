package com.sie.watsons.base.pon.standards.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonStandardsHEntity_HI_RO Entity Object
 * Sat Oct 05 16:25:56 CST 2019  Auto Generate
 */

public class EquPonStandardsHEntity_HI_RO {
    private Integer standardsId;
    private String standardsCode;
	private String parentStandardsCode;
    private String standardsStatus;
    private String approveDate;
	private Integer tableLv;
    private String standardsName;
    private String mark;
	private String createdByName;
	private String standardsStatusName;
    private String approvalTime;
    private String deptCode;
    private String copyStandardsCode;
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
	private String  isDisposable;
    private Integer operatorUserId;
    private String procInstId;
	private BigDecimal score;
	private BigDecimal fullCore;
	private Integer fileId;
	private String fileName;
	private String filePath;

	public static void main(String[] args) {
		System.out.println(QUERY_LIST_SQL);
	}
//	EquPonStandardsHEntity_HI_RO.QUERY_LIST_SQL

	public static String QUERY_LIST_SQL=
			"SELECT t.standards_id standardsId\n" +
					"      ,t.standards_code standardsCode\n" +
					"      ,t.standards_status standardsStatus\n" +
					"      ,t.standards_name\n" +
					"      ,t.mark\n" +
					"      ,t.score\n" +
					"      ,t.score fullScore\n" +
					"      ,t.version_num versionNum\n" +
					"      ,t.creation_date creationDate\n" +
					"      ,t.created_by createdBy\n" +
					"      ,t.last_updated_by lastUpdatedBy\n" +
					"      ,t.last_update_date lastUpdateDate\n" +
					"      ,t.approval_time approvalTime\n" +
					"      ,t.is_Disposable isDisposable\n" +
					"      ,t.dept_code deptCode\n" +
					"      ,t.copy_standards_code copyStandardsCode\n" +
					"      ,t.parent_standards_code parentStandardsCode\n" +
					"      ,t.file_id fileId\n" +
					"      ,t.file_name fileName\n" +
					"      ,t.file_path filePath\n" +
					"FROM   EQU_PON_STANDARDS_H t\n" +
					"WHERE  1 = 1";

	public void setStandardsId(Integer standardsId) {
		this.standardsId = standardsId;
	}

	
	public Integer getStandardsId() {
		return standardsId;
	}

	public void setStandardsCode(String standardsCode) {
		this.standardsCode = standardsCode;
	}

	
	public String getStandardsCode() {
		return standardsCode;
	}

	public void setStandardsStatus(String standardsStatus) {
		this.standardsStatus = standardsStatus;
	}

	
	public String getStandardsStatus() {
		return standardsStatus;
	}

	public void setStandardsName(String standardsName) {
		this.standardsName = standardsName;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getStandardsName() {
		return standardsName;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getTableLv() {
		return tableLv;
	}

	public void setTableLv(Integer tableLv) {
		this.tableLv = tableLv;
	}

	public String getMark() {
		return mark;
	}

	public String getIsDisposable() {
		return isDisposable;
	}

	public void setIsDisposable(String isDisposable) {
		this.isDisposable = isDisposable;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApprovalTime() {
		return approvalTime;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getParentStandardsCode() {
		return parentStandardsCode;
	}

	public void setParentStandardsCode(String parentStandardsCode) {
		this.parentStandardsCode = parentStandardsCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getStandardsStatusName() {
		return standardsStatusName;
	}

	public void setStandardsStatusName(String standardsStatusName) {
		this.standardsStatusName = standardsStatusName;
	}

	public String getCopyStandardsCode() {
        return copyStandardsCode;
    }

    public void setCopyStandardsCode(String copyStandardsCode) {
        this.copyStandardsCode = copyStandardsCode;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public BigDecimal getFullCore() {
		return fullCore;
	}

	public void setFullCore(BigDecimal fullCore) {
		this.fullCore = fullCore;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
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
}
