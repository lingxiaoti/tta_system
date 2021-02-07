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
 * PlmDevelopmentQaSummaryEntity_HI Entity Object
 * Thu Aug 29 14:13:07 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_DEVELOPMENT_QA_SUMMARY")
public class PlmDevelopmentQaSummaryEntity_HI {
    private Integer plmDevelopmentQaSummaryId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String fileAlterType;
    private String inapplicableSign;
    private Integer fileCount;
    private String remarks;
    private String productCategory;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;
    private String rejectReason;

	public void setPlmDevelopmentQaSummaryId(Integer plmDevelopmentQaSummaryId) {
		this.plmDevelopmentQaSummaryId = plmDevelopmentQaSummaryId;
	}

	@Id	
	@SequenceGenerator(name="plmDevelopmentQaSummaryEntity_HISeqGener", sequenceName="SEQ_PLM_DEVELOPMENT_QA_SUMMARY", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmDevelopmentQaSummaryEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_development_qa_summary_id", nullable=false, length=22)	
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

	public void setFileAlterType(String fileAlterType) {
		this.fileAlterType = fileAlterType;
	}

	@Column(name="file_alter_type", nullable=true, length=200)	
	public String getFileAlterType() {
		return fileAlterType;
	}

	public void setInapplicableSign(String inapplicableSign) {
		this.inapplicableSign = inapplicableSign;
	}

	@Column(name="inapplicable_sign", nullable=true, length=20)	
	public String getInapplicableSign() {
		return inapplicableSign;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	@Column(name="file_count", nullable=true, length=22)	
	public Integer getFileCount() {
		return fileCount;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=800)	
	public String getRemarks() {
		return remarks;
	}

	@Column(name="product_category", nullable=true, length=50)
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
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

	@Column(name="reject_reason", nullable=true, length=800)
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
}
