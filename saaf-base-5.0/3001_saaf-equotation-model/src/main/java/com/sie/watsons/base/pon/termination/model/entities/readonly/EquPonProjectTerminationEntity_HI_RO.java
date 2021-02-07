package com.sie.watsons.base.pon.termination.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonProjectTerminationEntity_HI_RO Entity Object
 * Wed Feb 19 11:58:35 CST 2020  Auto Generate
 */

public class EquPonProjectTerminationEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	public  static  final String  QUERY_SQL = "select t.termination_id        terminationId\n" +
			"      ,t.termination_number terminationNumber\n" +
			"      ,t.project_id            projectId\n" +
			"      ,t.status\n" +
			"      ,t.file_id               fileId\n" +
			"      ,t.file_name             fileName\n" +
			"      ,t.file_path             filePath\n" +
			"      ,t.termination_reason    terminationReason\n" +
			"      ,t.dept_code             deptCode\n" +
			"      ,t.version_num           versionNum\n" +
			"      ,t.creation_date         creationDate\n" +
			"      ,t.created_by            createdBy\n" +
			"      ,t.last_updated_by       lastUpdatedBy\n" +
			"      ,t.last_update_date      lastUpdateDate\n" +
			"      ,t.last_update_login     lastUpdateLogin\n" +
			"      ,t.attribute1\n" +
			"      ,t.attribute2\n" +
			"      ,t.attribute3\n" +
			"      ,t.attribute4\n" +
			"      ,t.attribute5\n" +
			"      ,t.attribute6\n" +
			"      ,t.attribute7\n" +
			"      ,t.attribute8\n" +
			"      ,t.attribute9\n" +
			"      ,t.attribute10\n" +
			"      ,p.project_number        projectNumber\n" +
			"      ,p.project_name          projectName\n" +
			"      ,p.source_project_number sourceProjectNumber\n" +
			"from   equ_pon_project_termination t\n" +
			"      ,equ_pon_project_info        p\n" +
			"where  t.project_id = p.project_id\n";

	public  static  final String  QUERY_SQL_IT = "select t.termination_id        terminationId\n" +
			"      ,t.termination_number terminationNumber\n" +
			"      ,t.project_id            projectId\n" +
			"      ,t.status\n" +
			"      ,t.file_id               fileId\n" +
			"      ,t.file_name             fileName\n" +
			"      ,t.file_path             filePath\n" +
			"      ,t.termination_reason    terminationReason\n" +
			"      ,t.dept_code             deptCode\n" +
			"      ,t.version_num           versionNum\n" +
			"      ,t.creation_date         creationDate\n" +
			"      ,t.created_by            createdBy\n" +
			"      ,t.last_updated_by       lastUpdatedBy\n" +
			"      ,t.last_update_date      lastUpdateDate\n" +
			"      ,t.last_update_login     lastUpdateLogin\n" +
			"      ,t.attribute1\n" +
			"      ,t.attribute2\n" +
			"      ,t.attribute3\n" +
			"      ,t.attribute4\n" +
			"      ,t.attribute5\n" +
			"      ,t.attribute6\n" +
			"      ,t.attribute7\n" +
			"      ,t.attribute8\n" +
			"      ,t.attribute9\n" +
			"      ,t.attribute10\n" +
			"      ,p.project_number        projectNumber\n" +
			"      ,p.project_name          projectName\n" +
			"      ,p.source_project_number sourceProjectNumber\n" +
			"      ,t.union_deptment_flag unionDeptmentFlag\n" +
			"from   equ_pon_project_termination t\n" +
			"      ,equ_pon_it_project_info        p\n" +
			"where  t.project_id = p.project_id\n";
	private Integer terminationId;
    private String terminationNumber;
    private Integer projectId;
    private String status;
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String terminationReason;
    private String deptCode;
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
    private String projectNumber;
    private String projectName;
    private String sourceProjectNumber;
	private String unionDeptmentFlag;
    private Integer operatorUserId;

	public void setTerminationId(Integer terminationId) {
		this.terminationId = terminationId;
	}

	
	public Integer getTerminationId() {
		return terminationId;
	}

	public void setTerminationNumber(String terminationNumber) {
		this.terminationNumber = terminationNumber;
	}

	
	public String getTerminationNumber() {
		return terminationNumber;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getFilePath() {
		return filePath;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

	
	public String getTerminationReason() {
		return terminationReason;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
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

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSourceProjectNumber() {
		return sourceProjectNumber;
	}

	public void setSourceProjectNumber(String sourceProjectNumber) {
		this.sourceProjectNumber = sourceProjectNumber;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getUnionDeptmentFlag() {
		return unionDeptmentFlag;
	}

	public void setUnionDeptmentFlag(String unionDeptmentFlag) {
		this.unionDeptmentFlag = unionDeptmentFlag;
	}
}
