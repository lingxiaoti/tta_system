package com.sie.saaf.deploy.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * BasePublishAppInfoEntity_HI_RO Entity Object
 * Fri Nov 09 10:41:44 GMT+08:00 2018  Auto Generate
 */

public class BasePublishAppInfoEntity_HI_RO {

	public static final String QUERY_LIST_SQL = "SELECT\n" +
			"\tpub.app_wap_id appWapId,\n" +
			"\tb1.meaning appWapName,\n" +
			"\tb2.meaning appSystem,\n" +
			"\tpub.app_publish_version appPublishVersion,\n" +
			"\tpub.app_update_tips appUpdateTips,\n" +
			"\tpub.app_download_url appDownloadUrl,\n" +
			"\tpub.created_by createdBy,\n" +
			"\tpub.creation_date creationDate,\n" +
			"\tpub.last_updated_by lastUpdatedBy,\n" +
			"\tpub.last_update_date lasUpdateDate\n" +
			"FROM\n" +
			"\tbase_publish_app_info pub\n" +
			"JOIN base_lookup_values b1 ON b1.lookup_code = pub.app_wap_name\n" +
			"AND b1.lookup_type = 'APP_NAME_DESC'\n" +
			"AND b1.system_code = 'PUBLIC'\n" +
			"AND b1.enabled_flag = 'Y'\n" +
			"AND b1.delete_flag = 0\n" +
			"JOIN base_lookup_values b2 ON b2.lookup_code = pub.app_system\n" +
			"AND b2.lookup_type = 'APP_SYSTEM'\n" +
			"AND b2.system_code = 'PUBLIC'\n" +
			"AND b2.enabled_flag = 'Y'\n" +
			"AND b2.delete_flag = 0";


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

	
	public Integer getAppWapId() {
		return appWapId;
	}

	public void setAppWapName(String appWapName) {
		this.appWapName = appWapName;
	}

	
	public String getAppWapName() {
		return appWapName;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	
	public String getAppSystem() {
		return appSystem;
	}

	public void setAppPublishVersion(String appPublishVersion) {
		this.appPublishVersion = appPublishVersion;
	}

	
	public String getAppPublishVersion() {
		return appPublishVersion;
	}

	public void setAppUpdateTips(String appUpdateTips) {
		this.appUpdateTips = appUpdateTips;
	}

	
	public String getAppUpdateTips() {
		return appUpdateTips;
	}

	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}

	
	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
