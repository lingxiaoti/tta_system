package com.sie.watsons.base.supplement.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSideAgrtHeaderEntity_HI Entity Object
 * Wed Jun 19 11:58:44 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SIDE_AGRT_HEADER")
public class TtaSideAgrtHeaderEntity_HI {
    private Integer sideAgrtHId;
    private String sideAgrtCode;
    private String billStatus;   
    private String sideAgrtVersion;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;//审批通过时间

	public void setSideAgrtHId(Integer sideAgrtHId) {
		this.sideAgrtHId = sideAgrtHId;
	}
	@Id
	@SequenceGenerator(name="SEQ_TTA_SIDE_AGRT_HEADER", sequenceName="SEQ_TTA_SIDE_AGRT_HEADER", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SIDE_AGRT_HEADER",strategy=GenerationType.SEQUENCE)
	@Column(name="side_agrt_h_id", nullable=false, length=22)	
	public Integer getSideAgrtHId() {
		return sideAgrtHId;
	}

	public void setSideAgrtCode(String sideAgrtCode) {
		this.sideAgrtCode = sideAgrtCode;
	}

	@Column(name="side_agrt_code", nullable=true, length=50)	
	public String getSideAgrtCode() {
		return sideAgrtCode;
	}
		
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	
	@Column(name="BILL_STATUS", nullable=true, length=50)	
	public String getBillStatus() {
		return billStatus;
	}

	public void setSideAgrtVersion(String sideAgrtVersion) {
		this.sideAgrtVersion = sideAgrtVersion;
	}
	
	@Column(name="side_agrt_version", nullable=true, length=50)	
	public String getSideAgrtVersion() {
		return sideAgrtVersion;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=true, length=7)	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=7)	
	public Date getEndDate() {
		return endDate;
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

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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

	@Column(name="approve_date", nullable=false, length=7)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
}
