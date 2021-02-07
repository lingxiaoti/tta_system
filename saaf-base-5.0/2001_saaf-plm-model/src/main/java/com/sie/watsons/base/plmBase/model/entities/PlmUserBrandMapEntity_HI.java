package com.sie.watsons.base.plmBase.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.stereotype.Component;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * 供应商与品牌之间的关系
 * Thu Nov 19 16:55:45 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_USER_BRAND_MAP")
public class PlmUserBrandMapEntity_HI {
    private Integer mapId;
    private Integer supUserId;
    private Integer brandMapId;
    private Integer brandInfoId;
    private Integer groupBrandId;
    private Integer motherCompanyId;
    private String brandCn;
    private Integer brandCnUdaId;
    private Integer brandCnUdaValue;
    private String brandEn;
    private Integer brandEnUdaId;
    private Integer brandEnUdaValue;
    private Integer createdBy;
    private Integer masterright = 0; //是否从Saaf平台同步的 1:是；0:否
    private Integer status = 0;  //是否生效，审批通过后
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer deleteFlag = 0;
    private Integer versionNum;
    private Integer operatorUserId;
    private String userName;
    private String userEmail;
    private String profileCode;
    private String motherCompanyName;
    private String groupBrandName;

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_USER_BRANDMAP", sequenceName = "SEQ_USER_BRANDMAP", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SEQ_USER_BRANDMAP", strategy = GenerationType.SEQUENCE)
	@Column(name="map_id", nullable=false, length=22)	
	public Integer getMapId() {
		return mapId;
	}

	public void setSupUserId(Integer supUserId) {
		this.supUserId = supUserId;
	}

	@Column(name="sup_user_id", nullable=false, length=22)
	public Integer getSupUserId() {
		return supUserId;
	}

	public void setBrandMapId(Integer brandMapId) {
		this.brandMapId = brandMapId;
	}

	@Column(name="brand_map_id", nullable=true, length=22)
	public Integer getBrandMapId() {
		return brandMapId;
	}

	public void setBrandInfoId(Integer brandInfoId) {
		this.brandInfoId = brandInfoId;
	}

	@Column(name="brand_info_id", nullable=true, length=22)
	public Integer getBrandInfoId() {
		return brandInfoId;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=255)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		this.brandCnUdaId = brandCnUdaId;
	}

	@Column(name="brand_cn_uda_id", nullable=true, length=22)	
	public Integer getBrandCnUdaId() {
		return brandCnUdaId;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		this.brandCnUdaValue = brandCnUdaValue;
	}

	@Column(name="brand_cn_uda_value", nullable=true, length=22)	
	public Integer getBrandCnUdaValue() {
		return brandCnUdaValue;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=255)
	public String getBrandEn() {
		return brandEn;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="USER_NAME", nullable=true, length=255)
	public String getUserName() {
		return userName;
	}

	@Column(name="USER_EMAIL", nullable=true, length=255)
	public String getUserEmail() {
		return userEmail;
	}

	@Column(name="PROFILE_CODE", nullable=true, length=255)
	public String getProfileCode() {
		return profileCode;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		this.brandEnUdaId = brandEnUdaId;
	}

	@Column(name="MOTHER_COMPANY_NAME", nullable=true, length=255)
	public String getMotherCompanyName() {
		return motherCompanyName;
	}

	public void setMotherCompanyName(String motherCompanyName) {
		this.motherCompanyName = motherCompanyName;
	}

	@Column(name="GROUP_BRAND_NAME", nullable=true, length=255)
	public String getGroupBrandName() {
		return groupBrandName;
	}

	public void setGroupBrandName(String groupBrandName) {
		this.groupBrandName = groupBrandName;
	}

	@Column(name="brand_en_uda_id", nullable=true, length=22)
	public Integer getBrandEnUdaId() {
		return brandEnUdaId;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		this.brandEnUdaValue = brandEnUdaValue;
	}

	@Column(name="brand_en_uda_value", nullable=true, length=22)	
	public Integer getBrandEnUdaValue() {
		return brandEnUdaValue;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setMasterright(Integer masterright) {
		this.masterright = masterright;
	}

	@Column(name="masterright", nullable=true, length=22)	
	public Integer getMasterright() {
		return masterright;
	}

	@Column(name="status", nullable=true, length=22)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Column(name="GROUP_BRAND_ID", nullable=true, length=22)
	public Integer getGroupBrandId() {
		return groupBrandId;
	}

	public void setGroupBrandId(Integer groupBrandId) {
		this.groupBrandId = groupBrandId;
	}

	@Column(name="MOTHER_COMPANY_ID", nullable=true, length=22)
	public Integer getMotherCompanyId() {
		return motherCompanyId;
	}

	public void setMotherCompanyId(Integer motherCompanyId) {
		this.motherCompanyId = motherCompanyId;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
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
