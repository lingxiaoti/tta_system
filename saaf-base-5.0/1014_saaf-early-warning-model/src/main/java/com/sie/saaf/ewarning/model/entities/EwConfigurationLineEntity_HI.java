package com.sie.saaf.ewarning.model.entities;

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
 * EwConfigurationLineEntity_HI Entity Object
 * Thu Apr 18 14:20:25 CST 2019  Auto Generate
 */
@Entity
@Table(name="ew_configuration_line")
public class EwConfigurationLineEntity_HI {
    private Integer ewcLineId; //表ID，主键，供其他表做外键
    private Integer ewcHeadId; //预警配置头表ID
    private String ewcLineMode; //推送方式
    private String ewcLineNeed; //待办页面
    private Integer ewcLineTemplate; //推送模板
    private String ewcLineType; //推送类型(个推)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDateActive; //起始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDateActive; //终止日期
    private String description; //说明、备注
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;

	public void setEwcLineId(Integer ewcLineId) {
		this.ewcLineId = ewcLineId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_EW_CONFIGURATION_LINE", sequenceName = "SEQ_EW_CONFIGURATION_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_EW_CONFIGURATION_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="ewc_line_id", nullable=false, length=11)	
	public Integer getEwcLineId() {
		return ewcLineId;
	}

	public void setEwcHeadId(Integer ewcHeadId) {
		this.ewcHeadId = ewcHeadId;
	}

	@Column(name="ewc_head_id", nullable=true, length=11)	
	public Integer getEwcHeadId() {
		return ewcHeadId;
	}

	public void setEwcLineMode(String ewcLineMode) {
		this.ewcLineMode = ewcLineMode;
	}

	@Column(name="ewc_line_mode", nullable=true, length=30)	
	public String getEwcLineMode() {
		return ewcLineMode;
	}

	public void setEwcLineNeed(String ewcLineNeed) {
		this.ewcLineNeed = ewcLineNeed;
	}

	@Column(name="ewc_line_need", nullable=true, length=30)	
	public String getEwcLineNeed() {
		return ewcLineNeed;
	}

	public void setEwcLineTemplate(Integer ewcLineTemplate) {
		this.ewcLineTemplate = ewcLineTemplate;
	}

	@Column(name="ewc_line_template", nullable=true, length=30)	
	public Integer getEwcLineTemplate() {
		return ewcLineTemplate;
	}

	public void setEwcLineType(String ewcLineType) {
		this.ewcLineType = ewcLineType;
	}

	@Column(name="ewc_line_type", nullable=true, length=30)	
	public String getEwcLineType() {
		return ewcLineType;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name="start_date_active", nullable=false, length=0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name="end_date_active", nullable=true, length=0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=500)	
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name="attribute_category", nullable=true, length=30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
