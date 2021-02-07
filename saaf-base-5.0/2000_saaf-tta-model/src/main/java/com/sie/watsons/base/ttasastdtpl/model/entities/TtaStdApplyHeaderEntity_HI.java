package com.sie.watsons.base.ttasastdtpl.model.entities;

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
 * TtaStdApplyHeaderEntity_HI Entity Object
 * Fri Apr 10 10:43:14 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_STD_APPLY_HEADER")
public class TtaStdApplyHeaderEntity_HI {
    private Integer stdApplyHeaderId;
    private String orderNo;
    private Integer versionCode;
    private Integer tplId;
    private String tplName;
    private String tplType;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveStartTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveEndTime;
    private String status;
    private Integer resourceId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String orgCode;
    private String orgCodeName;
    private String deptCode;
    private String deptCodeName;
	private String isAlert;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date approveDate;
    private Integer operatorUserId;

	public void setStdApplyHeaderId(Integer stdApplyHeaderId) {
		this.stdApplyHeaderId = stdApplyHeaderId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_STD_APPLY_HEADER", sequenceName = "SEQ_TTA_STD_APPLY_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_STD_APPLY_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="std_apply_header_id", nullable=false, length=22)	
	public Integer getStdApplyHeaderId() {
		return stdApplyHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=50)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	@Column(name="version_code", nullable=true, length=22)	
	public Integer getVersionCode() {
		return versionCode;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	@Column(name="tpl_id", nullable=true, length=22)	
	public Integer getTplId() {
		return tplId;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	@Column(name="tpl_name", nullable=true, length=100)	
	public String getTplName() {
		return tplName;
	}

	@Column(name="tpl_type", nullable=true, length=50)
	public String getTplType() {
		return tplType;
	}

	public void setTplType(String tplType) {
		this.tplType = tplType;
	}

	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}

	@Column(name="effective_start_time", nullable=true, length=7)	
	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	@Column(name="effective_end_time", nullable=true, length=7)	
	public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)	
	public String getStatus() {
		return status;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name="resource_id", nullable=true, length=22)
	public Integer getResourceId() {
		return resourceId;
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

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_code", nullable=true, length=100)	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCodeName(String orgCodeName) {
		this.orgCodeName = orgCodeName;
	}

	@Column(name="org_code_name", nullable=true, length=300)	
	public String getOrgCodeName() {
		return orgCodeName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=100)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCodeName(String deptCodeName) {
		this.deptCodeName = deptCodeName;
	}

	@Column(name="dept_code_name", nullable=true, length=300)	
	public String getDeptCodeName() {
		return deptCodeName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="is_alert", nullable=true, length=2)
	public String getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}

	@Column(name="approve_date", nullable=true, length=7)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
}
