package com.sie.saaf.ewarning.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * EwConfigurationManEntity_HI_RO Entity Object
 * Thu Apr 18 14:20:37 CST 2019  Auto Generate
 */

public class EwConfigurationManEntity_HI_RO {
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

	
	public Integer getEwcManId() {
		return ewcManId;
	}

	public void setEwcLineId(Integer ewcLineId) {
		this.ewcLineId = ewcLineId;
	}

	
	public Integer getEwcLineId() {
		return ewcLineId;
	}

	public void setPushObject(Integer pushObject) {
		this.pushObject = pushObject;
	}

	
	public Integer getPushObject() {
		return pushObject;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	
	public String getPushType() {
		return pushType;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
	public String getPosition() {
		return position;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
