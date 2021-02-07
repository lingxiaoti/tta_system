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
 * PlmDevelopmentBatchInfoEntity_HI Entity Object
 * Thu Aug 29 14:13:09 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_DEVELOPMENT_BATCH_INFO")
public class PlmDevelopmentBatchInfoEntity_HI {

    private Integer plmDevelopmentBatchInfoId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String fileAlterType;
    private String fileType;
    private String fileName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;
    private String productionBatch;
    private Integer productionCount;
    private String remarks;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;
	private String isSpa;
	
	private String fileAddress;

	@Column(name="file_address", nullable=true, length=500)
	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public void setPlmDevelopmentBatchInfoId(Integer plmDevelopmentBatchInfoId) {
		this.plmDevelopmentBatchInfoId = plmDevelopmentBatchInfoId;
	}

	@Id	
	@SequenceGenerator(name="plmDevelopmentBatchInfoEntity_HISeqGener", sequenceName="SEQ_PLM_DEVELOPMENT_BATCH_INFO", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmDevelopmentBatchInfoEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_development_batch_info_id", nullable=false, length=22)	
	public Integer getPlmDevelopmentBatchInfoId() {
		return plmDevelopmentBatchInfoId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="is_Spa", nullable=true, length=200)
	public String getIsSpa() {		return isSpa;	}
	public void setIsSpa(String isSpa) {		this.isSpa = isSpa;	}

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

	public void setFileAlterType(String fileAlterType) {
		this.fileAlterType = fileAlterType;
	}

	@Column(name="file_alter_type", nullable=true, length=200)	
	public String getFileAlterType() {
		return fileAlterType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=true, length=50)	
	public String getFileType() {
		return fileType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=50)	
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

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	@Column(name="production_batch", nullable=true, length=400)	
	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionCount(Integer productionCount) {
		this.productionCount = productionCount;
	}

	@Column(name="production_count", nullable=true, length=22)	
	public Integer getProductionCount() {
		return productionCount;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=800)	
	public String getRemarks() {
		return remarks;
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
}
