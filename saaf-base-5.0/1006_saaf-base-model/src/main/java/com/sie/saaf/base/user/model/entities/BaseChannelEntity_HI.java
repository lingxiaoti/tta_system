package com.sie.saaf.base.user.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * BaseChannelEntity_HI Entity Object
 * Thu Jan 11 19:05:56 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_channel")
public class BaseChannelEntity_HI {
    private Integer channelId; //渠道Id
    private String channelCode; //渠道编码
    private String channelName; //渠道名称
    private String channelDesc; //渠道描述
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_CHANNEL", sequenceName = "SEQ_BASE_CHANNEL", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_CHANNEL", strategy = GenerationType.SEQUENCE)		
	@Column(name = "channel_id", nullable = false, length = 11)	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	@Column(name = "channel_code", nullable = true, length = 150)	
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Column(name = "channel_name", nullable = true, length = 240)	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	@Column(name = "channel_desc", nullable = true, length = 240)	
	public String getChannelDesc() {
		return channelDesc;
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
