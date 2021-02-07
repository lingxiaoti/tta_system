package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BasePdaInvCfgEntity_HI Entity Object
 * Thu Mar 15 09:55:24 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_pda_inv_cfg")
public class BasePdaInvCfgEntity_HI {
    private Integer cfgId; //主键Id
    private String subInvCode;
    private Integer organizationId; //库存组织ID
    private String channelCode;
    private Integer roleId;
    private Integer userId; //用户ID
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setCfgId(Integer cfgId) {
		this.cfgId = cfgId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_PDA_INV_CFG", sequenceName = "SEQ_BASE_PDA_INV_CFG", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PDA_INV_CFG", strategy = GenerationType.SEQUENCE)	
	@Column(name = "cfg_id", nullable = false, length = 11)	
	public Integer getCfgId() {
		return cfgId;
	}

	public void setSubInvCode(String subInvCode) {
		this.subInvCode = subInvCode;
	}

	@Column(name = "sub_inv_code", nullable = true, length = 32)	
	public String getSubInvCode() {
		return subInvCode;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "organization_id", nullable = true, length = 11)	
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	@Column(name = "channel_code", nullable = true, length = 10)	
	public String getChannelCode() {
		return channelCode;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_id", nullable = true, length = 11)	
	public Integer getRoleId() {
		return roleId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_id", nullable = true, length = 11)	
	public Integer getUserId() {
		return userId;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "delete_flag", nullable = true, length = 11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
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
