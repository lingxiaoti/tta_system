package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseProfileEntity_HI Entity Object
 * Tue Dec 12 19:24:40 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_profile")
public class BaseProfileEntity_HI {
    private Integer profileId; //主键ID
    private String profileCode; //profile编码
    private Integer dsId; //数据源Idb
    private String dsName; //数据源名字
    private String sourceSql; //source_sql
    private String profileName; //profile名称
    private String profileDesc; //profile描述
    private String systemCode; //系统编码：为哪个系统定义的profile，默认为All表示所有系统适用
	private Integer deleteFlag;//是否删除；0：否；1：删除
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_PROFILE", sequenceName = "SEQ_BASE_PROFILE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PROFILE", strategy = GenerationType.SEQUENCE)	
	@Column(name = "profile_id", nullable = false, length = 11)	
	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	@Column(name = "profile_code", nullable = true, length = 50)	
	public String getProfileCode() {
		return profileCode;
	}

	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	@Column(name = "ds_id", nullable = false, length = 11)	
	public Integer getDsId() {
		return dsId;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	@Column(name = "ds_name", nullable = true, length = 100)	
	public String getDsName() {
		return dsName;
	}

	public void setSourceSql(String sourceSql) {
		this.sourceSql = sourceSql;
	}

	@Column(name = "source_sql", nullable = true, length = 500)	
	public String getSourceSql() {
		return sourceSql;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	@Column(name = "profile_name", nullable = true, length = 50)	
	public String getProfileName() {
		return profileName;
	}

	public void setProfileDesc(String profileDesc) {
		this.profileDesc = profileDesc;
	}

	@Column(name = "profile_desc", nullable = true, length = 200)	
	public String getProfileDesc() {
		return profileDesc;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "system_code", nullable = true, length = 50)	
	public String getSystemCode() {
		return systemCode;
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

	@Column(name = "delete_flag", nullable = true, length = 11)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
