package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaOiReportHeaderEntity_HI Entity Object
 * Tue May 05 16:57:51 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_oi_report_header")
public class TtaOiReportHeaderEntity_HI {
    private Integer id;
    private String batchCode;
    private String status;
    private String reportType;
    private String backUp;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date passDate;
    private String isPublish;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date isPublishDate;
    private Integer isPublishBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String  confirmStatus; //Y:已确认,N:待确认

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_OI_REPORT_HEADER", sequenceName = "SEQ_TTA_OI_REPORT_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_OI_REPORT_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
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

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	@Column(name="is_publish", nullable=true, length=50)	
	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublishDate(Date isPublishDate) {
		this.isPublishDate = isPublishDate;
	}

	@Column(name="is_publish_date", nullable=true, length=7)	
	public Date getIsPublishDate() {
		return isPublishDate;
	}

	public void setIsPublishBy(Integer isPublishBy) {
		this.isPublishBy = isPublishBy;
	}

	@Column(name="is_publish_by", nullable=true, length=22)	
	public Integer getIsPublishBy() {
		return isPublishBy;
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


	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	@Column(name="confirm_status", nullable=true, length=10)
	public String getConfirmStatus() {
		return confirmStatus;
	}
}
