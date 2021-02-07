package com.sie.saaf.base.redisdata.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * BaseRedisDataEntity_HI Entity Object
 * Sat Feb 24 19:59:38 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_redis_data")
public class BaseRedisDataEntity_HI {
    private Integer redisDataId; //主键
    private String redisType; //类型
    private String redisKey; //redis键
    private String redisValue; //redis值
	private String redisDesc; //描述
    private String enabled; //是否启用（Y：启用；N：禁用）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setRedisDataId(Integer redisDataId) {
		this.redisDataId = redisDataId;
	}

	@Id	
	@GeneratedValue	
	@Column(name = "redis_data_id", nullable = false, length = 11)	
	public Integer getRedisDataId() {
		return redisDataId;
	}

	public void setRedisType(String redisType) {
		this.redisType = redisType;
	}

	@Column(name = "redis_type", nullable = true, length = 50)	
	public String getRedisType() {
		return redisType;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	@Column(name = "redis_key", nullable = true, length = 100)	
	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisValue(String redisValue) {
		this.redisValue = redisValue;
	}

	@Column(name = "redis_value", nullable = true, length = 4000)	
	public String getRedisValue() {
		return redisValue;
	}

	public void setRedisDesc(String redisDesc) {
		this.redisDesc = redisDesc;
	}

	@Column(name = "redis_desc", nullable = true, length = 500)
	public String getRedisDesc() {
		return redisDesc;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "enabled", nullable = true, length = 5)	
	public String getEnabled() {
		return enabled;
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
