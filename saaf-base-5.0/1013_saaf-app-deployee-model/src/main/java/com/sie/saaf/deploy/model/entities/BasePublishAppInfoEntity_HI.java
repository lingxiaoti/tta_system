package com.sie.saaf.deploy.model.entities;

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
 * BasePublishAppInfoEntity_HI Entity Object
 * Fri Nov 09 10:41:44 GMT+08:00 2018  Auto Generate
 */
@Entity
@Table(name = "base_publish_app_info")
public class BasePublishAppInfoEntity_HI {
    private Integer appWapId; //app应用主键
    private String appWapName; //app应用名称
    private String appSystem; //app操作系统，ios：苹果；android：安卓
    private String appPublishVersion; //app发布版本号，例如：1.0.2
    private String appUpdateTips; //app更新提示
    private String appDownloadUrl; //app下载链接
    private Integer createdBy; //创建人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdateLogin; //最后登录id
    private Integer lastUpdatedBy; //最后更新人
    private Integer versionNum; //版本号
    private Integer deleteFlag; //是否删除0：未删除1：已删除
    private Integer operatorUserId;

	public void setAppWapId(Integer appWapId) {
		this.appWapId = appWapId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_PUBLISH_APP_INFO", sequenceName = "SEQ_BASE_PUBLISH_APP_INFO", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_PUBLISH_APP_INFO", strategy = GenerationType.SEQUENCE)	
	@Column(name = "app_wap_id", nullable = false, length = 11)	
	public Integer getAppWapId() {
		return appWapId;
	}

	public void setAppWapName(String appWapName) {
		this.appWapName = appWapName;
	}

	@Column(name = "app_wap_name", nullable = true, length = 64)	
	public String getAppWapName() {
		return appWapName;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	@Column(name = "app_system", nullable = true, length = 10)
	public String getAppSystem() {
		return appSystem;
	}

	public void setAppPublishVersion(String appPublishVersion) {
		this.appPublishVersion = appPublishVersion;
	}

	@Column(name = "app_publish_version", nullable = true, length = 10)	
	public String getAppPublishVersion() {
		return appPublishVersion;
	}

	public void setAppUpdateTips(String appUpdateTips) {
		this.appUpdateTips = appUpdateTips;
	}

	@Column(name = "app_update_tips", nullable = true, length = 2000)	
	public String getAppUpdateTips() {
		return appUpdateTips;
	}

	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}

	@Column(name = "app_download_url", nullable = true, length = 256)	
	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "delete_flag", nullable = true, length = 11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
