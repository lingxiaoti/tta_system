package com.sie.watsons.base.rule.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TempRuleDefEntity_HI Entity Object
 * Wed May 29 16:12:16 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_temp_rule_def")
public class TempRuleDefEntity_HI {
    private Integer rulId;
    private String ruleName;
    private String isEnable;
    private Integer soleResouceType;
    private Integer soleProductType;
    private String isIncludeEc;
    private String isIncludeSpecial;
    private String fileUrl;
    private String remark;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setRulId(Integer rulId) {
		this.rulId = rulId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_TEMP_RULE_DEF", sequenceName="SEQ_TTA_TEMP_RULE_DEF", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_TEMP_RULE_DEF",strategy=GenerationType.SEQUENCE)
	@Column(name="rul_id", nullable=false, length=22)	
	public Integer getRulId() {
		return rulId;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name="rule_name", nullable=false, length=100)	
	public String getRuleName() {
		return ruleName;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=false, length=2)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setSoleResouceType(Integer soleResouceType) {
		this.soleResouceType = soleResouceType;
	}

	@Column(name="sole_resouce_type", nullable=false, length=22)	
	public Integer getSoleResouceType() {
		return soleResouceType;
	}

	public void setSoleProductType(Integer soleProductType) {
		this.soleProductType = soleProductType;
	}

	@Column(name="sole_product_type", nullable=false, length=22)	
	public Integer getSoleProductType() {
		return soleProductType;
	}

	public void setIsIncludeEc(String isIncludeEc) {
		this.isIncludeEc = isIncludeEc;
	}

	@Column(name="is_include_ec", nullable=false, length=2)	
	public String getIsIncludeEc() {
		return isIncludeEc;
	}

	public void setIsIncludeSpecial(String isIncludeSpecial) {
		this.isIncludeSpecial = isIncludeSpecial;
	}

	@Column(name="is_include_special", nullable=false, length=2)	
	public String getIsIncludeSpecial() {
		return isIncludeSpecial;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name="file_url", nullable=false, length=500)	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=200)
	public String getRemark() {
		return remark;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
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
