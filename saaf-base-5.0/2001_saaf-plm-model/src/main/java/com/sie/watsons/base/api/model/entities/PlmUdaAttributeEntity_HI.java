package com.sie.watsons.base.api.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmUdaAttributeEntity_HI Entity Object
 * Wed Dec 18 15:18:54 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_UDA_ATTRIBUTE")
public class PlmUdaAttributeEntity_HI {
    private Integer headId;
    private Integer udaId;
    private String udaDesc;
    private String module;
    private String displayType;
    private String dataType;
    private Integer dataLength;
    private String singleValueInd;
    private String udaValueDesc;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private Integer udaValue;

	@Column(name="uda_value", nullable=true, length=1000)
	public Integer getUdaValue() {
		return udaValue;
	}

	public void setUdaValue(Integer udaValue) {
		this.udaValue = udaValue;
	}

	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	@Id
	@SequenceGenerator(name="PLM_UDA_ATTRIBUTE_SEQ", sequenceName="PLM_UDA_ATTRIBUTE_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="PLM_UDA_ATTRIBUTE_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="head_id", nullable=false, length=22)
	public Integer getHeadId() {
		return headId;
	}

	public void setUdaId(Integer udaId) {
		this.udaId = udaId;
	}

	@Column(name="uda_id", nullable=true, length=22)	
	public Integer getUdaId() {
		return udaId;
	}

	public void setUdaDesc(String udaDesc) {
		this.udaDesc = udaDesc;
	}

	@Column(name="uda_desc", nullable=true, length=1000)	
	public String getUdaDesc() {
		return udaDesc;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name="module", nullable=true, length=100)	
	public String getModule() {
		return module;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	@Column(name="display_type", nullable=true, length=100)	
	public String getDisplayType() {
		return displayType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name="data_type", nullable=true, length=100)	
	public String getDataType() {
		return dataType;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	@Column(name="data_length", nullable=true, length=22)	
	public Integer getDataLength() {
		return dataLength;
	}

	public void setSingleValueInd(String singleValueInd) {
		this.singleValueInd = singleValueInd;
	}

	@Column(name="single_value_ind", nullable=true, length=50)	
	public String getSingleValueInd() {
		return singleValueInd;
	}

	public void setUdaValueDesc(String udaValueDesc) {
		this.udaValueDesc = udaValueDesc;
	}

	@Column(name="uda_value_desc", nullable=true, length=800)	
	public String getUdaValueDesc() {
		return udaValueDesc;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
