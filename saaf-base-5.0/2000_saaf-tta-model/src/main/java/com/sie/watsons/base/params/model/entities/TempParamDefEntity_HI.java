package com.sie.watsons.base.params.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import java.io.Serializable;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TempParamDefEntity_HI Entity Object
 * Tue May 28 19:49:34 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_temp_param_def")
public class TempParamDefEntity_HI implements Serializable {
    private Integer paramId;
    private String paramKey;
    private String paramContent;
    private String isSql;
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

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_TEMP_PARAM_DEF", sequenceName="SEQ_TTA_TEMP_PARAM_DEF", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_TEMP_PARAM_DEF",strategy=GenerationType.SEQUENCE)
	@Column(name="param_id", nullable=false, length=22)
	public Integer getParamId() {
		return paramId;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	@Column(name="param_key", nullable=false, length=200)	
	public String getParamKey() {
		return paramKey;
	}

	public void setParamContent(String paramContent) {
		this.paramContent = paramContent;
	}

	@Column(name="param_content", nullable=false, length=4000)
	public String getParamContent() {
		return paramContent;
	}

	public void setIsSql(String isSql) {
		this.isSql = isSql;
	}

	@Column(name="is_sql", nullable=false)
	public String getIsSql() {
		return isSql;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=false, length=100)	
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
