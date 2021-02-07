package com.sie.watsons.base.ob.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmDevelopmentQaDetailEntity_HI Entity Object
 * Thu Aug 29 14:13:08 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_DEVELOPMENT_QA_DETAIL")
public class PlmDevelopmentQaDetailEntity_HI {
    private Integer plmDevelopmentQaDetailId;
    private Integer plmDevelopmentQaSummaryId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String fileName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date signatureTime;
    private String remarks;
    private String fileStatus;
    private String rejectReason;
    private String fileAddress;
    private java.math.BigDecimal fileSize;
    private String fileType;
    private String fileAction;
    private String obId;
    private String barcode;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;
    private Integer uploadId;
	private String isSpa;
	public void setPlmDevelopmentQaDetailId(Integer plmDevelopmentQaDetailId) {
		this.plmDevelopmentQaDetailId = plmDevelopmentQaDetailId;
	}

	@Id	
	@SequenceGenerator(name="plmDevelopmentQaDetailEntity_HISeqGener", sequenceName="SEQ_PLM_DEVELOPMENT_QA_DETAIL", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmDevelopmentQaDetailEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_development_qa_detail_id", nullable=false, length=22)	
	public Integer getPlmDevelopmentQaDetailId() {
		return plmDevelopmentQaDetailId;
	}

	public void setPlmDevelopmentQaSummaryId(Integer plmDevelopmentQaSummaryId) {		this.plmDevelopmentQaSummaryId = plmDevelopmentQaSummaryId;	}

	@Column(name="is_Spa", nullable=true, length=200)
	public String getIsSpa() {return isSpa;}
	public void setIsSpa(String isSpa) {this.isSpa = isSpa;}

	@Column(name="plm_development_qa_summary_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentQaSummaryId() {
		return plmDevelopmentQaSummaryId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="plm_development_info_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	@Column(name="plm_project_id", nullable=true, length=22)	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=200)
	public String getFileName() {
		return fileName;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name="upload_date", nullable=true, length=7)	
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setSignatureTime(Date signatureTime) {
		this.signatureTime = signatureTime;
	}

	@Column(name="signature_time", nullable=true, length=7)	
	public Date getSignatureTime() {
		return signatureTime;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=800)	
	public String getRemarks() {
		return remarks;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	@Column(name="file_status", nullable=true, length=20)	
	public String getFileStatus() {
		return fileStatus;
	}

	@Column(name="reject_reason", nullable=true, length=800)
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	@Column(name="file_address", nullable=true, length=500)
	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileSize(java.math.BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="file_size", nullable=true, length=22)	
	public java.math.BigDecimal getFileSize() {
		return fileSize;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=true, length=50)	
	public String getFileType() {
		return fileType;
	}

	public void setFileAction(String fileAction) {
		this.fileAction = fileAction;
	}

	@Column(name="file_action", nullable=true, length=20)	
	public String getFileAction() {
		return fileAction;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	@Column(name="ob_id", nullable=true, length=50)
	public String getObId() {
		return obId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name="barcode", nullable=true, length=50)	
	public String getBarcode() {
		return barcode;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="upload_id", nullable=true, length=22)
	public Integer getUploadId() {
		return uploadId;
	}

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}
}
