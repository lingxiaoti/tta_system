package com.sie.saaf.message.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Transient;

/**
 * MsgSourceCfgEntity_HI Entity Object
 * Thu Jun 07 09:39:30 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_source_cfg")
public class MsgSourceCfgEntity_HI {
    private Integer msgSourceId; //消息配置ID
    private Integer orgId; //OU
    private String msgTypeCode; //消息类型CODE
    private String msgTypeName; //消息类型CODE
    private String paramCfg; //接口配置参数
    private String sourceUser; //接口用户名
    private String sourcePwd; //接口密码,des加密存放
    private String sourceName; //消息接口名称
    private String sourceDesc; //接口描述
    private String enabledFlag; //启用状态:0.已停用 1.启用
    private Integer versionNum;
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
	private String enabledFlagName;
	private String orgName;

	public void setMsgSourceId(Integer msgSourceId) {
		this.msgSourceId = msgSourceId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_MSG_SOURCE_CFG", sequenceName = "SEQ_MSG_SOURCE_CFG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_SOURCE_CFG", strategy = GenerationType.SEQUENCE)	
	@Column(name = "msg_source_id", nullable = false, length = 15)	
	public Integer getMsgSourceId() {
		return msgSourceId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = false, length = 15)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setMsgTypeCode(String msgTypeCode) {
		this.msgTypeCode = msgTypeCode;
	}

	@Column(name = "msg_type_code", nullable = false, length = 20)	
	public String getMsgTypeCode() {
		return msgTypeCode;
	}

	public void setParamCfg(String paramCfg) {
		this.paramCfg = paramCfg;
	}

	@Column(name = "param_cfg", nullable = true, length = 4000)	
	public String getParamCfg() {
		return paramCfg;
	}

	public void setSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
	}

	@Column(name = "source_user", nullable = true, length = 255)	
	public String getSourceUser() {
		return sourceUser;
	}

	public void setSourcePwd(String sourcePwd) {
		this.sourcePwd = sourcePwd;
	}

	@Column(name = "source_pwd", nullable = true, length = 255)	
	public String getSourcePwd() {
		return sourcePwd;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Column(name = "source_name", nullable = false, length = 40)	
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
	}

	@Column(name = "source_desc", nullable = true, length = 100)	
	public String getSourceDesc() {
		return sourceDesc;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	@Column(name = "enabled_flag", nullable = false, length = 2)	
	public String getEnabledFlag() {
		return enabledFlag;
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

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = false, length = 20)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
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

	@Column(name = "created_by", nullable = false, length = 20)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "is_delete", nullable = false, length = 11)
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}


	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}

	@Transient
	public String getMsgTypeName() {
		return msgTypeName;
	}

	@Transient
	public String getEnabledFlagName() {
		return enabledFlagName;
	}

	public void setEnabledFlagName(String enabledFlagName) {
		this.enabledFlagName = enabledFlagName;
	}

	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
