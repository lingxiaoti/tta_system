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

import org.springframework.data.annotation.Version;

/**
 * PlmSupplementHeadEntity_HI Entity Object Mon Sep 09 15:57:23 GMT+08:00 2019
 * Auto Generate
 */
@Entity
@Table(name = "plm_supplement_head")
public class PlmSupplementHeadEntity_HI {
	private Integer plmSupplementHeadId;
	private String supCode;
	private String orderType;
	private String orderStatus;
	private String creator;
	private Integer createdBy;
	private Date creationDate;
	private Integer lastUpdatedBy;
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;
	private String userDept;
	private String userGroupCode;
	private String orgCode;
	private String processUser; // 流程发起人
	private String rmsReverBusinesskey; // 流程版本
	private String processReparam; // 流程驳回参数保存

	@Column(name = "process_user", nullable = true, length = 50)
	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	@Column(name = "rms_rever_businesskey", nullable = true, length = 255)
	public String getRmsReverBusinesskey() {
		return rmsReverBusinesskey;
	}

	public void setRmsReverBusinesskey(String rmsReverBusinesskey) {
		this.rmsReverBusinesskey = rmsReverBusinesskey;
	}

	@Column(name = "process_reparam", nullable = true, length = 255)
	public String getProcessReparam() {
		return processReparam;
	}

	public void setProcessReparam(String processReparam) {
		this.processReparam = processReparam;
	}

	public void setPlmSupplementHeadId(Integer plmSupplementHeadId) {
		this.plmSupplementHeadId = plmSupplementHeadId;
	}

	@Id
	@Column(name = "plm_supplement_head_id", nullable = true, length = 11)
	@SequenceGenerator(name = "SEQ_PLM_SUPPLEMENT_HEAD", sequenceName = "SEQ_PLM_SUPPLEMENT_HEAD", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_SUPPLEMENT_HEAD", strategy = GenerationType.SEQUENCE)
	public Integer getPlmSupplementHeadId() {
		return plmSupplementHeadId;
	}

	@Column(name = "user_dept", nullable = true, length = 150)
	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	@Column(name = "user_group_code", nullable = true, length = 150)
	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "order_type", nullable = true, length = 11)
	public String getOrderType() {
		return orderType;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "order_status", nullable = true, length = 11)
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "creator", nullable = true, length = 10)
	public String getCreator() {
		return creator;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 10)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 10)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 10)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 10)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 10)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 10)
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

	@Column(name = "sup_code", nullable = true, length = 50)
	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	@Column(name = "org_code", nullable = true, length = 150)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
