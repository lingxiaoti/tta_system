package com.sie.watsons.base.report.model.entities;

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
 * TtaReportAttGenRecordEntity_HI Entity Object
 * Thu Apr 09 17:24:57 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_report_att_gen_record")
public class TtaReportAttGenRecordEntity_HI {
    private Integer reportAttGenRecordId;
    private String msgCode;
    private String msgRemark;
    private String reportType;
    private String queryParams;
    private Integer fileId;
    private String fileName;
    private String fileUrl;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setReportAttGenRecordId(Integer reportAttGenRecordId) {
		this.reportAttGenRecordId = reportAttGenRecordId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_REPORT_ATT_GEN_RECORD", sequenceName = "SEQ_TTA_REPORT_ATT_GEN_RECORD", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REPORT_ATT_GEN_RECORD", strategy = GenerationType.SEQUENCE)
	@Column(name="report_att_gen_record_id", nullable=false, length=22)	
	public Integer getReportAttGenRecordId() {
		return reportAttGenRecordId;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	@Column(name="msg_code", nullable=true, length=2)	
	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgRemark(String msgRemark) {
		this.msgRemark = msgRemark;
	}

	@Column(name="msg_remark", nullable=true, length=4000)	
	public String getMsgRemark() {
		return msgRemark;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Column(name="report_type", nullable=true, length=5)	
	public String getReportType() {
		return reportType;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	@Column(name="query_params", nullable=true, length=2000)	
	public String getQueryParams() {
		return queryParams;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=true, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=200)	
	public String getFileName() {
		return fileName;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name="file_url", nullable=true, length=1000)	
	public String getFileUrl() {
		return fileUrl;
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

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
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
}
