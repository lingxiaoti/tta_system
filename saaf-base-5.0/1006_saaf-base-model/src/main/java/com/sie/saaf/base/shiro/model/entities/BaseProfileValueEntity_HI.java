package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseProfileValueEntity_HI Entity Object
 * Tue Dec 12 19:24:40 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_profile_value")
public class BaseProfileValueEntity_HI {
    private Integer profileValueId; //主键ID
    private Integer profileId; //profile表Id
    private Integer businessKey; //业务表对应的主键
    private String systemCode; //profile值关联的页面的Code
    private String keyTableName; //business_key来源的表的名字
    private String profileValue; //profile_value
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


	@Id
	@SequenceGenerator(name = "SEQ_BASE_PROFILE_VALUE", sequenceName = "SEQ_BASE_PROFILE_VALUE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PROFILE_VALUE", strategy = GenerationType.SEQUENCE)
	@Column(name = "profile_value_id", nullable = false, length = 11)
	public Integer getProfileValueId() {
		return profileValueId;
	}

	public void setProfileValueId(Integer profileValueId) {
		this.profileValueId = profileValueId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	@Column(name = "profile_id", nullable = true, length = 11)	
	public Integer getProfileId() {
		return profileId;
	}

	public void setBusinessKey(Integer businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name = "business_key", nullable = true, length = 11)	
	public Integer getBusinessKey() {
		return businessKey;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "system_code", nullable = true, length = 100)	
	public String getSystemCode() {
		return systemCode;
	}

	public void setKeyTableName(String keyTableName) {
		this.keyTableName = keyTableName;
	}

	@Column(name = "key_table_name", nullable = true, length = 100)	
	public String getKeyTableName() {
		return keyTableName;
	}

	public void setProfileValue(String profileValue) {
		this.profileValue = profileValue;
	}

	@Column(name = "profile_value", nullable = true, length = 200)	
	public String getProfileValue() {
		return profileValue;
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
