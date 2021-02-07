package com.sie.saaf.base.sso.model.entities;

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
 * BaseFunctionCollectionUserEntity_HI Entity Object
 * Tue Jan 30 16:48:18 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_function_collection_user")
public class BaseFunctionCollectionUserEntity_HI {
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

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_FUNCTION_COLLECTION_USER", sequenceName = "SEQ_BASE_FUNCTION_COLLECTION_USER", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_FUNCTION_COLLECTION_USER", strategy = GenerationType.SEQUENCE)	
	@Column(name = "function_collection_user_id", nullable = false, length = 11)	
	public Integer getFunctionCollectionUserId() {
		return functionCollectionUserId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_id", nullable = true, length = 11)	
	public Integer getUserId() {
		return userId;
	}

	public void setFunctionCollectionId(Integer functionCollectionId) {
		this.functionCollectionId = functionCollectionId;
	}

	@Column(name = "function_collection_id", nullable = true, length = 11)	
	public Integer getFunctionCollectionId() {
		return functionCollectionId;
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
}
