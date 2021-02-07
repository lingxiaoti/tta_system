package com.sie.watsons.base.product.model.entities;

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
 * PlmProductBpmPersonEntity_HI Entity Object Thu Oct 24 15:52:35 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_BPM_PERSON")
public class PlmProductBpmPersonEntity_HI {
	private Integer id;
	private String name;
	private String useridList;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private String userType;
	private Integer operatorUserId;
	private String deptType;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_BPMPERSON", sequenceName = "SEQ_PLM_PRODUCT_BPMPERSON", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_BPMPERSON", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 22)
	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "name", nullable = true, length = 255)
	public String getName() {
		return name;
	}

	public void setUseridList(String useridList) {
		this.useridList = useridList;
	}

	@Column(name = "userid_list", nullable = true, length = 600)
	public String getUseridList() {
		return useridList;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "user_type", nullable = true, length = 255)
	public String getUserType() {
		return userType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name = "dept_type", nullable = true, length = 255)
	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
