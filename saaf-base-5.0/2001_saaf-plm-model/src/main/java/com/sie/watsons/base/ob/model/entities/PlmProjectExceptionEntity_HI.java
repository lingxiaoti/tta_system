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
 * PlmProjectExceptionEntity_HI Entity Object
 * Thu Aug 29 14:13:06 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_PROJECT_EXCEPTION")
public class PlmProjectExceptionEntity_HI {
    private Integer plmProjectExceptionId;
    private Integer plmProjectId;
    private Integer plmDevelopmentInfoId;
	private Integer plmProjectProductDetailId;
    private String exceptionProductName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date exceptionOccurrenceTime;
    private String exceptionOccurrenceStage;
    private String exceptionDetails;
    private String results;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

	public void setPlmProjectExceptionId(Integer plmProjectExceptionId) {
		this.plmProjectExceptionId = plmProjectExceptionId;
	}

	@Id	
	@SequenceGenerator(name="plmProjectExceptionEntity_HISeqGener", sequenceName="SEQ_PLM_PROJECT_EXCEPTION", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmProjectExceptionEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_project_exception_id", nullable=false, length=22)	
	public Integer getPlmProjectExceptionId() {
		return plmProjectExceptionId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	@Column(name="plm_project_id", nullable=true, length=22)	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="plm_development_info_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	@Column(name="exception_product_name", nullable=true, length=400)
	public String getExceptionProductName() {
		return exceptionProductName;
	}

	public void setExceptionProductName(String exceptionProductName) {
		this.exceptionProductName = exceptionProductName;
	}

	public void setExceptionOccurrenceTime(Date exceptionOccurrenceTime) {
		this.exceptionOccurrenceTime = exceptionOccurrenceTime;
	}

	@Column(name="exception_occurrence_time", nullable=true, length=7)	
	public Date getExceptionOccurrenceTime() {
		return exceptionOccurrenceTime;
	}

	public void setExceptionOccurrenceStage(String exceptionOccurrenceStage) {
		this.exceptionOccurrenceStage = exceptionOccurrenceStage;
	}

	@Column(name="exception_occurrence_stage", nullable=true, length=20)	
	public String getExceptionOccurrenceStage() {
		return exceptionOccurrenceStage;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	@Column(name="exception_details", nullable=true, length=800)	
	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setResults(String results) {
		this.results = results;
	}

	@Column(name="results", nullable=true, length=800)	
	public String getResults() {
		return results;
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

	@Column(name="plm_project_product_detail_id", nullable=true, length=22)
	public Integer getPlmProjectProductDetailId() {
		return plmProjectProductDetailId;
	}

	public void setPlmProjectProductDetailId(Integer plmProjectProductDetailId) {
		this.plmProjectProductDetailId = plmProjectProductDetailId;
	}
}
