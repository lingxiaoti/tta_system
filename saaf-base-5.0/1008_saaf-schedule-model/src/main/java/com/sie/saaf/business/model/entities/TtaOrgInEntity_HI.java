package com.sie.saaf.business.model.entities;

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
 * TtaOrgInEntity_HI Entity Object
 * Wed Jul 03 09:39:15 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_ORG_IN")
public class TtaOrgInEntity_HI {
   // private Integer orgId;
    private String orgCode;
    private String orgName;
    private String parentOrgCode;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	/*public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_ORG_IN", sequenceName = "SEQ_TTA_ORG_IN", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_ORG_IN", strategy = GenerationType.SEQUENCE)
	@Column(name="org_id", nullable=false, length=22)	
	public Integer getOrgId() {
		return orgId;
	}*/

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Id
	@Column(name="org_code", nullable=false, length=50)	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="org_name", nullable=false, length=50)	
	public String getOrgName() {
		return orgName;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	@Column(name="parent_org_code", nullable=true, length=50)	
	public String getParentOrgCode() {
		return parentOrgCode;
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

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((parentOrgCode == null) ? 0 : parentOrgCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TtaOrgInEntity_HI other = (TtaOrgInEntity_HI) obj;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (parentOrgCode == null) {
			if (other.parentOrgCode != null)
				return false;
		} else if (!parentOrgCode.equals(other.parentOrgCode))
			return false;
		return true;
	}


}
