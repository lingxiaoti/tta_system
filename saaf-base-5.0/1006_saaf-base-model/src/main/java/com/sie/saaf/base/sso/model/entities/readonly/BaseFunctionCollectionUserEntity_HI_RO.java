package com.sie.saaf.base.sso.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseFunctionCollectionUserEntity_HI_RO Entity Object
 * Tue Jan 30 16:51:30 CST 2018  Auto Generate
 */

public class BaseFunctionCollectionUserEntity_HI_RO {





    private Integer functionCollectionUserId;
    private Integer userId; //用户id
    private Integer functionCollectionId; //菜单收藏id
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setFunctionCollectionUserId(Integer functionCollectionUserId) {
		this.functionCollectionUserId = functionCollectionUserId;
	}

	
	public Integer getFunctionCollectionUserId() {
		return functionCollectionUserId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public Integer getUserId() {
		return userId;
	}

	public void setFunctionCollectionId(Integer functionCollectionId) {
		this.functionCollectionId = functionCollectionId;
	}

	
	public Integer getFunctionCollectionId() {
		return functionCollectionId;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
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
