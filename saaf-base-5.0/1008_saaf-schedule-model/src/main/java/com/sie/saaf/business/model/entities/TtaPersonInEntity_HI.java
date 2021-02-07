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
 * TtaPersonInEntity_HI Entity Object
 * Mon Jul 01 18:28:47 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_PERSON_IN")
public class TtaPersonInEntity_HI {
	
	//private Integer personInId;
    private String code;
    private String name;
    private String nameEn;
    private String deptNo;
    private String postName;
    private Integer grand;
    private String mail;
    private String area;
    private String market;
    private String orgUnitName; //由orgCode更改成orgUnitName
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

/*    public void setPersonInId(Integer personInId) {
		this.personInId = personInId;
	}
    
	@Id
	@SequenceGenerator(name = "SEQ_TTA_PERSON_IN", sequenceName = "SEQ_TTA_PERSON_IN", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_PERSON_IN", strategy = GenerationType.SEQUENCE)
	@Column(name="person_in_id", nullable=true, length=22)	
    public Integer getPersonInId() {
		return personInId;
	}*/
    

	public void setCode(String code) {
		this.code = code;
	}

	@Id
	@Column(name="code", nullable=false, length=50)	
	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="name", nullable=true, length=50)	
	public String getName() {
		return name;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@Column(name="name_en", nullable=true, length=50)	
	public String getNameEn() {
		return nameEn;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name="dept_no", nullable=true, length=50)	
	public String getDeptNo() {
		return deptNo;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Column(name="post_name", nullable=true, length=100)
	public String getPostName() {
		return postName;
	}
	
	@Column(name="grand", nullable=true, length=22)
	public Integer getGrand() {
		return grand;
	}

	public void setGrand(Integer grand) {
		this.grand = grand;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name="mail", nullable=true, length=50)	
	public String getMail() {
		return mail;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name="area", nullable=true, length=50)	
	public String getArea() {
		return area;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	@Column(name="market", nullable=true, length=50)	
	public String getMarket() {
		return market;
	}

	public void setOrgUnitName(String orgUnitName) {
		this.orgUnitName = orgUnitName;
	}

	@Column(name="org_unit_name", nullable=true, length=10)
	public String getOrgUnitName() {
		return orgUnitName;
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
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((deptNo == null) ? 0 : deptNo.hashCode());
		result = prime * result + ((grand == null) ? 0 : grand.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nameEn == null) ? 0 : nameEn.hashCode());
		result = prime * result + ((orgUnitName == null) ? 0 : orgUnitName.hashCode());
		result = prime * result + ((postName == null) ? 0 : postName.hashCode());
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
		TtaPersonInEntity_HI other = (TtaPersonInEntity_HI) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (deptNo == null) {
			if (other.deptNo != null)
				return false;
		} else if (!deptNo.equals(other.deptNo))
			return false;
		if (grand == null) {
			if (other.grand != null)
				return false;
		} else if (!grand.equals(other.grand))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (market == null) {
			if (other.market != null)
				return false;
		} else if (!market.equals(other.market))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nameEn == null) {
			if (other.nameEn != null)
				return false;
		} else if (!nameEn.equals(other.nameEn))
			return false;
		if (orgUnitName == null) {
			if (other.orgUnitName != null)
				return false;
		} else if (!orgUnitName.equals(other.orgUnitName))
			return false;
		if (postName == null) {
			if (other.postName != null)
				return false;
		} else if (!postName.equals(other.postName))
			return false;
		return true;
	}
	
}
