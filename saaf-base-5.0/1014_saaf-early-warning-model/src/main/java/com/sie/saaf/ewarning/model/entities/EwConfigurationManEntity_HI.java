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
 * EwConfigurationManEntity_HI Entity Object
 * Thu Apr 18 14:20:37 CST 2019  Auto Generate
 */
@Entity
@Table(name="ew_configuration_man")
public class EwConfigurationManEntity_HI {
    private Integer ewcManId; //表ID，主键，供其他表做外键
    private Integer ewcLineId; //行表ID
    private Integer pushObject; //推送对象
    private String pushType; //推送类型(岗位,个人)
    private String position; //所在岗位
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
    private Integer operatorUserId;

	public void setEwcManId(Integer ewcManId) {
		this.ewcManId = ewcManId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_EW_CONFIGURATION_MAN", sequenceName = "SEQ_EW_CONFIGURATION_MAN", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_EW_CONFIGURATION_MAN", strategy = GenerationType.SEQUENCE)	
	@Column(name="ewc_man_id", nullable=false, length=11)	
	public Integer getEwcManId() {
		return ewcManId;
	}

	public void setEwcLineId(Integer ewcLineId) {
		this.ewcLineId = ewcLineId;
	}

	@Column(name="ewc_line_id", nullable=true, length=11)	
	public Integer getEwcLineId() {
		return ewcLineId;
	}

	public void setPushObject(Integer pushObject) {
		this.pushObject = pushObject;
	}

	@Column(name="push_object", nullable=true, length=11)	
	public Integer getPushObject() {
		return pushObject;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	@Column(name="push_type", nullable=true, length=10)	
	public String getPushType() {
		return pushType;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name="position", nullable=true, length=10)	
	public String getPosition() {
		return position;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
