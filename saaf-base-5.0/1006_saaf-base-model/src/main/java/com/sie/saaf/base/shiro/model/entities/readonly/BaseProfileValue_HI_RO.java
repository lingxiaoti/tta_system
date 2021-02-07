package com.sie.saaf.base.shiro.model.entities.readonly;

/**
 * @author ZhangJun
 * @createTime 2018-01-16 18:00
 * @description
 */
public class BaseProfileValue_HI_RO {
	public static final String QUERY_PROFILE_VALUE_QUERY = "select " +
			"    baseProfileValue.profile_value_id as profileValueId," +
			"    baseProfileValue.profile_id as profileId," +
			"    baseProfileValue.business_key as businessKey," +
			"    baseProfileValue.system_code as systemCode," +
			"    baseProfileValue.key_table_name as keyTableName," +
			"    baseProfileValue.profile_value as profileValue," +
			"    baseProfileValue.version_num as versionNum," +
			"    baseProfileValue.delete_flag as deleteFlag," +
			"    baseProfile.profile_code as profileCode," +
			"    baseProfile.profile_name as profileName," +
			"    baseProfile.profile_desc as profileDesc," +
			"    baseProfile.ds_name as dsName," +
			"    baseProfile.ds_id as dsId" +
			" from " +
			"    base_profile_value  baseProfileValue," +
			"    base_profile  baseProfile" +
			" where " +
			"    baseProfileValue.profile_id = baseProfile.profile_id" +
			"    and baseProfileValue.delete_flag=0 and baseProfile.delete_flag=0 ";

	private Integer profileValueId; //主键ID
	private Integer profileId; //profile表Id
	private Integer businessKey; //业务表对应的主键
	private String systemCode; //profile值关联的页面的Code
	private String keyTableName; //business_key来源的表的名字
	private String profileValue; //profile_value
	private Integer deleteFlag;//是否删除；0：否；1：删除
	private Integer versionNum;

	private String profileCode; //profile编码
	private Integer dsId; //数据源Id
	private String dsName; //数据源名字
	private String profileName; //profile名称
	private String profileDesc; //profile描述

	public Integer getProfileValueId() {
		return profileValueId;
	}

	public void setProfileValueId(Integer profileValueId) {
		this.profileValueId = profileValueId;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public Integer getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(Integer businessKey) {
		this.businessKey = businessKey;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getKeyTableName() {
		return keyTableName;
	}

	public void setKeyTableName(String keyTableName) {
		this.keyTableName = keyTableName;
	}

	public String getProfileValue() {
		return profileValue;
	}

	public void setProfileValue(String profileValue) {
		this.profileValue = profileValue;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getProfileCode() {
		return profileCode;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	public Integer getDsId() {
		return dsId;
	}

	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileDesc() {
		return profileDesc;
	}

	public void setProfileDesc(String profileDesc) {
		this.profileDesc = profileDesc;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
}
