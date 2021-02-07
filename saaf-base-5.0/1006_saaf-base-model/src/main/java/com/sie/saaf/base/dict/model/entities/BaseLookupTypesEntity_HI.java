package com.sie.saaf.base.dict.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * BaseLookupTypesEntity_HI Entity Object
 * Wed Dec 06 10:55:10 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_lookup_types")
public class BaseLookupTypesEntity_HI {
    private Integer lookupTypeId; //主键ID
    private Integer parentTypeId; //主键ID
    private String lookupType; //数据字典类型
    private String meaning; //说明
    private String description; //描述
	private Integer parentLookupTypeId; // 父节点id
    private String customizationLevel; //系统级别或是用户级别
    private String systemCode; //系统编码
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private Integer deleteFlag;//是否删除

	public void setLookupTypeId(Integer lookupTypeId) {
		this.lookupTypeId = lookupTypeId;
	}

	@Id		
	@SequenceGenerator(name = "SEQ_BASE_LOOKUP_TYPES", sequenceName = "SEQ_BASE_LOOKUP_TYPES", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_LOOKUP_TYPES", strategy = GenerationType.SEQUENCE)
	@Column(name = "lookup_type_id", nullable = false, length = 11)	
	public Integer getLookupTypeId() {
		return lookupTypeId;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	@Column(name = "parent_type_id",length = 11)	
	public Integer getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Integer parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	@Column(name = "lookup_type", nullable = true, length = 30)	
	public String getLookupType() {
		return lookupType;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	@Column(name = "meaning", nullable = true, length = 80)	
	public String getMeaning() {
		return meaning;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "description", nullable = true, length = 240)	
	public String getDescription() {
		return description;
	}

	public void setCustomizationLevel(String customizationLevel) {
		this.customizationLevel = customizationLevel;
	}

	@Column(name = "parent_lookup_type_id", nullable = true, length = 11)
	public Integer getParentLookupTypeId() {
		return parentLookupTypeId;
	}

	public void setParentLookupTypeId(Integer parentLookupTypeId) {
		this.parentLookupTypeId = parentLookupTypeId;
	}

	@Column(name = "customization_level", nullable = true, length = 1)
	public String getCustomizationLevel() {
		return customizationLevel;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	@Column(name = "system_code", nullable = true, length = 30)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
	
	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "delete_flag", nullable = true, length = 11)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
