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
 * PlmProjectInfoEntity_HI Entity Object
 * Thu Aug 29 11:31:03 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_PROJECT_INFO")
public class PlmProjectInfoEntity_HI {
    private Integer plmProjectId;
    private String projectNumber;
    private String biddingCode;
    private String projectRange;
    private String projectName;
    private Integer supplierId;
    private String supplierType;
    private String supplierName;
    private String supplierCode;
    private String billStatus;
    private String creatorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private String remarks;
    private String multiProducer;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	@Id	
	@SequenceGenerator(name="plmProjectInfoEntity_HISeqGener", sequenceName="SEQ_PLM_PROJECT_INFO", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmProjectInfoEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_project_id", nullable=false, length=22)	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	@Column(name="project_number", nullable=true, length=20)	
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setBiddingCode(String biddingCode) {
		this.biddingCode = biddingCode;
	}

	@Column(name="bidding_code", nullable=true, length=20)	
	public String getBiddingCode() {
		return biddingCode;
	}

	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}

	@Column(name="project_range", nullable=true, length=400)	
	public String getProjectRange() {
		return projectRange;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name="project_name", nullable=true, length=200)	
	public String getProjectName() {
		return projectName;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	@Column(name="supplier_id", nullable=false, length=22)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_type", nullable=true, length=50)
	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=800)
	public String getSupplierName() {
		return supplierName;
	}

	@Column(name="supplier_code", nullable=true, length=100)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name="bill_status", nullable=true, length=20)	
	public String getBillStatus() {
		return billStatus;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name="creator_name", nullable=true, length=100)	
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="multi_producer", nullable=true, length=400)
	public String getMultiProducer() {
		return multiProducer;
	}

	public void setMultiProducer(String multiProducer) {
		this.multiProducer = multiProducer;
	}
}
