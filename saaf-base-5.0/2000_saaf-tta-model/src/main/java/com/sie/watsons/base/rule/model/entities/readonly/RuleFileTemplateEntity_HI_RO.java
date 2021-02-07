package com.sie.watsons.base.rule.model.entities.readonly;

import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * RuleFileTemplateEntity_HI_RO Entity Object
 * Sun Jun 02 23:57:04 CST 2019  Auto Generate
 */

public class RuleFileTemplateEntity_HI_RO {

	public static final String QUERY_SQL = "select file_temp_id,\n" +
			"file_temp_name,\n" +
			"file_type,\n" +
			"file_content as file_content,\n" +
			"business_type,\n" +
			"created_by,\n" +
			"last_updated_by,\n" +
			"last_update_date,\n" +
			"creation_date,\n" +
			"last_update_login,\n" +
			"version_num \n" +
			" from tta_rule_file_template t" +
			" where 1 = 1 ";

    private Integer fileTempId;
    private String fileTempName;
    private String fileType;
    private Blob fileContent;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String businessType;

	public void setFileTempId(Integer fileTempId) {
		this.fileTempId = fileTempId;
	}

	
	public Integer getFileTempId() {
		return fileTempId;
	}

	public void setFileTempName(String fileTempName) {
		this.fileTempName = fileTempName;
	}

	
	public String getFileTempName() {
		return fileTempName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
	}

	public void setFileContent(Blob fileContent) {
		this.fileContent = fileContent;
	}

	
	public Blob getFileContent() {
		return fileContent;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessType() {
		return businessType;
	}
}
