package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * DomainWxEntity_HI Entity Object
 * Wed Feb 28 11:54:54 CST 2018  Auto Generate
 */
@Entity
@Table(name = "domain_wx")
public class DomainWxEntity_HI {
	private Integer configureId; //域名和微信配置主键ID
	private String domain; //域名
	private String corpId; //企业ID
	private String corpSecret; //应用的凭证密钥
	private Integer agentId; //授权方的网页应用ID
	private String secreth; //授权方的网页应用ID秘钥
	private String department; //事业部
	private String callbackScope; //回调域名
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum; //版本号
	private Integer operatorUserId;

	public void setConfigureId(Integer configureId) {
		this.configureId = configureId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_DOMAIN_WX", sequenceName = "SEQ_DOMAIN_WX", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_DOMAIN_WX", strategy = GenerationType.SEQUENCE)
	@Column(name = "configure_id", nullable = false, length = 20)
	public Integer getConfigureId() {
		return configureId;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Column(name = "domain", nullable = true, length = 150)
	public String getDomain() {
		return domain;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	@Column(name = "corp_id", nullable = true, length = 150)
	public String getCorpId() {
		return corpId;
	}

	public void setCorpSecret(String corpSecret) {
		this.corpSecret = corpSecret;
	}

	@Column(name = "corp_secret", nullable = true, length = 150)
	public String getCorpSecret() {
		return corpSecret;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	@Column(name = "agent_id", nullable = true, length = 20)
	public Integer getAgentId() {
		return agentId;
	}

	public void setSecreth(String secreth) {
		this.secreth = secreth;
	}

	@Column(name = "secreth", nullable = true, length = 150)
	public String getSecreth() {
		return secreth;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "department", nullable = true, length = 150)
	public String getDepartment() {
		return department;
	}

	public void setCallbackScope(String callbackScope) {
		this.callbackScope = callbackScope;
	}

	@Column(name = "callback_scope", nullable = true, length = 150)
	public String getCallbackScope() {
		return callbackScope;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = false, length = 0)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = false, length = 11)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = false, length = 11)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = false, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)
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
}
