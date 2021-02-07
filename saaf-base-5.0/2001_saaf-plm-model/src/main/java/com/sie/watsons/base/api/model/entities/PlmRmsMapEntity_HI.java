package com.sie.watsons.base.api.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmRmsMapEntity_HI Entity Object
 * Fri Feb 21 11:15:50 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_RMS_MAP")
public class PlmRmsMapEntity_HI {
    private Integer id;
    private String dataName;
    private String dataValue;
    private String status;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

    @Id
    @SequenceGenerator(name="PLM_RMS_MAP_SEQ", sequenceName="PLM_RMS_MAP_SEQ", allocationSize=1, initialValue=1)
    @GeneratedValue(generator="PLM_RMS_MAP_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)
	public Integer getId() {
		return id;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	@Column(name="data_name", nullable=true, length=255)
	public String getDataName() {
		return dataName;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	@Column(name="data_value", nullable=true, length=255)
	public String getDataValue() {
		return dataValue;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=255)
	public String getStatus() {
		return status;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
