package com.sie.saaf.base.api.model.entities;

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
 * BaseApiCenterLEntity_HI Entity Object
 * Mon Dec 04 11:31:34 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_api_center_l")
public class BaseApiCenterLEntity_HI{
    private Integer apilId; //主键ID
    private String centerCode; //项目/中心编码
    private String modelName; //模块名称
    private String modelCode; //模块编码
    private Integer versionNum; //版本号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setApilId(Integer apilId) {
		this.apilId = apilId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_API_CENTER_l", sequenceName = "SEQ_BASE_API_CENTER_l", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_API_CENTER_l", strategy = GenerationType.SEQUENCE)
	@Column(name = "apil_id", nullable = false, length = 11)	
	public Integer getApilId() {
		return apilId;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	@Column(name = "center_code", nullable = true, length = 100)	
	public String getCenterCode() {
		return centerCode;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "model_name", nullable = true, length = 400)	
	public String getModelName() {
		return modelName;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	@Column(name = "model_code", nullable = true, length = 400)	
	public String getModelCode() {
		return modelCode;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
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

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
