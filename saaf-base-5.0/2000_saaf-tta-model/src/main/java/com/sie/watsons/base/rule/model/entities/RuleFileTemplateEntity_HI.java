package com.sie.watsons.base.rule.model.entities;

import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * RuleFileTemplateEntity_HI Entity Object
 * Sun Jun 02 23:57:04 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_rule_file_template")
public class RuleFileTemplateEntity_HI {
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

	@Id
	@SequenceGenerator(name="SEQ_TTA_RULE_FILE_TEMPLATE", sequenceName="SEQ_TTA_RULE_FILE_TEMPLATE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_RULE_FILE_TEMPLATE",strategy=GenerationType.SEQUENCE)
	@Column(name="file_temp_id", nullable=false, length=22)	
	public Integer getFileTempId() {
		return fileTempId;
	}

	public void setFileTempName(String fileTempName) {
		this.fileTempName = fileTempName;
	}

	@Column(name="file_temp_name", nullable=false, length=100)	
	public String getFileTempName() {
		return fileTempName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=false, length=50)	
	public String getFileType() {
		return fileType;
	}

	public void setFileContent(Blob fileContent) {
		this.fileContent = fileContent;
	}

	@Column(name="file_content", nullable=false)
	public Blob getFileContent() {
		return fileContent;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	@Column(name="business_type", nullable=false, length=50)
	public String getBusinessType() {
		return businessType;
	}
}
