package com.sie.watsons.base.supplement.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.product.model.entities.columnNames;

import javax.persistence.*;
import java.util.Date;

/**
 * PlmProductFileEntity_HI Entity Object Thu Aug 29 10:51:48 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_SUPPLEMENT_LINE_FILE")
public class PlmSupplementLineFileEntity_HI {
	private Integer fileId;
	@columnNames(name = "附件备注")
	private String fileRemarks;
	private String fileUrl;
	@columnNames(name = "附件名称")
	private String fileName;
	private String lineId;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String remarks;
	private String flags;

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_SUPPLEMENT_LINE_FILE", sequenceName = "SEQ_PLM_SUPPLEMENT_LINE_FILE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_SUPPLEMENT_LINE_FILE", strategy = GenerationType.SEQUENCE)
	@Column(name = "file_id", nullable = false, length = 22)
	public Integer getFileId() {
		return fileId;
	}

	@Column(name = "remarks", nullable = true, length = 100)
	public String getremarks() {
		return remarks;
	}

	public void setremarks(String remarks) {
		this.remarks = remarks;
	}

	public void setFileRemarks(String fileRemarks) {
		this.fileRemarks = fileRemarks;
	}

	@Column(name = "file_remarks", nullable = true, length = 255)
	public String getFileRemarks() {
		return fileRemarks;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "file_url", nullable = true, length = 255)
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_name", nullable = true, length = 255)
	public String getFileName() {
		return fileName;
	}

	public void setlineId(String lineId) {
		this.lineId = lineId;
	}

	@Column(name = "line_id", nullable = true, length = 22)
	public String getlineId() {
		return lineId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "flags", nullable = true, length = 255)
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}
}
