package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseResponsibilityEntity_HI Entity Object
 * Tue Dec 12 19:17:00 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_responsibility")
public class BaseResponsibilityEntity_HI {
    private Integer responsibilityId; //职责标识
    private String responsibilityCode; //职责编号
    private String responsibilityName; //职责名称
    private String responsibilityDesc; //职责描述
	private String systemCode;//系统编码
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive; //生效时间
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive; //失效时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_RESPONSIBILITY", sequenceName = "SEQ_BASE_RESPONSIBILITY", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_RESPONSIBILITY", strategy = GenerationType.SEQUENCE)	
	@Column(name = "responsibility_id", nullable = false, length = 11)	
	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityCode(String responsibilityCode) {
		this.responsibilityCode = responsibilityCode;
	}

	@Column(name = "responsibility_code", nullable = true, length = 256)	
	public String getResponsibilityCode() {
		return responsibilityCode;
	}

	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}

	@Column(name = "responsibility_name", nullable = true, length = 256)	
	public String getResponsibilityName() {
		return responsibilityName;
	}

	public void setResponsibilityDesc(String responsibilityDesc) {
		this.responsibilityDesc = responsibilityDesc;
	}

	@Column(name = "responsibility_desc", nullable = true, length = 1000)	
	public String getResponsibilityDesc() {
		return responsibilityDesc;
	}

	@Column(name = "system_code", nullable = true, length = 30)
	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
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
