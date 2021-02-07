package com.sie.saaf.base.dict.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseLookupValuesEntity_HI Entity Object
 * Wed Dec 06 10:52:15 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_lookup_values")
public class BaseLookupValuesEntity_HI {
    private Integer lookupValuesId; //主键ID
    private String lookupType; //数据字典所属类型
    private String lookupCode; //数据字典编码
    private String meaning; //说明
    private String description; //描述
    private String parentLookupValuesId; //父节点Id
    private String enabledFlag; //是否启用
    private String buOrgId;//BU所属组织Id
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive; //生效日期
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive; //失效日期
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
	private Integer orderNo; //序号
	public void setLookupValuesId(Integer lookupValuesId) {
		this.lookupValuesId = lookupValuesId;
	}

	@Id
	//@GeneratedValue
	@SequenceGenerator(name = "SEQ_BASE_LOOKUP_VALUES", sequenceName = "SEQ_BASE_LOOKUP_VALUES", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_LOOKUP_VALUES", strategy = GenerationType.SEQUENCE)
	@Column(name = "lookup_values_id", nullable = false, length = 11)	
	public Integer getLookupValuesId() {
		return lookupValuesId;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	@Column(name = "lookup_type", nullable = true, length = 30)	
	public String getLookupType() {
		return lookupType;
	}

	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}

	@Column(name = "lookup_code", nullable = true, length = 30)	
	public String getLookupCode() {
		return lookupCode;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	@Column(name = "meaning", nullable = true, length = 50)	
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

	public void setParentLookupValuesId(String parentLookupValuesId) {
		this.parentLookupValuesId = parentLookupValuesId;
	}

	@Column(name = "parent_lookup_values_id", nullable = true, length = 30)	
	public String getParentLookupValuesId() {
		return parentLookupValuesId;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	@Column(name = "enabled_flag", nullable = true, length = 1)	
	public String getEnabledFlag() {
		return enabledFlag;
	}

	@Column(name="bu_org_id",length = 50)
	public String getBuOrgId() {
		return buOrgId;
	}

	public void setBuOrgId(String buOrgId) {
		this.buOrgId = buOrgId;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name = "start_date_active", nullable = true, length = 0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name = "end_date_active", nullable = true, length = 0)	
	public Date getEndDateActive() {
		return endDateActive;
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

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name = "order_no", nullable = true, length = 11)
	public Integer getOrderNo() {
		return orderNo;
	}
}
