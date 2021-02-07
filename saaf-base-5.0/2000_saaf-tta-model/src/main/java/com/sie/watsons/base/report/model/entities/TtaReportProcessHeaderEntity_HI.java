package com.sie.watsons.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaReportProcessHeaderEntity_HI Entity Object
 * Fri Nov 22 14:15:42 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_REPORT_PROCESS_HEADER")
public class TtaReportProcessHeaderEntity_HI {
    private Integer reportProcessHeaderId;
    private String batchCode;
    private String status;
    private String reportType;
    private String backUp;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date passDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String batchId;

	public void setReportProcessHeaderId(Integer reportProcessHeaderId) {
		this.reportProcessHeaderId = reportProcessHeaderId;
	}


	@Id
	@SequenceGenerator(name = "SEQ_TTA_REPORT_PROCESS_HEADER", sequenceName = "SEQ_TTA_REPORT_PROCESS_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REPORT_PROCESS_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="report_process_header_id", nullable=false, length=22)
	public Integer getReportProcessHeaderId() {
		return reportProcessHeaderId;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	@Column(name="batch_code", nullable=true, length=50)	
	public String getBatchCode() {
		return batchCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=50)	
	public String getStatus() {
		return status;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Column(name="report_type", nullable=true, length=50)	
	public String getReportType() {
		return reportType;
	}

	public void setBackUp(String backUp) {
		this.backUp = backUp;
	}

	@Column(name="back_up", nullable=true, length=50)	
	public String getBackUp() {
		return backUp;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	@Column(name="pass_date", nullable=true, length=7)	
	public Date getPassDate() {
		return passDate;
	}

	@Column(name="batch_id", nullable=true, length=80)
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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
