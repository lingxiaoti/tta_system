package com.sie.saaf.deploy.model.entities;

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
 * BaseAppAuthContainEntity_HI Entity Object
 * Mon Aug 20 15:13:19 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_app_auth_contain")
public class BaseAppAuthContainEntity_HI {
    private Integer authAppWapId;
    private Integer appWapId;
    private String objectType;
    private Integer ouId;
    private String depCode;
    private Integer empId;
    private String province;
    private String city;
    private String area;
    private String store;
    private String dealer;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;

	public void setAuthAppWapId(Integer authAppWapId) {
		this.authAppWapId = authAppWapId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_APP_AUTH_CONTAIN", sequenceName = "SEQ_BASE_APP_AUTH_CONTAIN", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_APP_AUTH_CONTAIN", strategy = GenerationType.SEQUENCE)	
	@Column(name="auth_app_wap_id", nullable=false, length=11)	
	public Integer getAuthAppWapId() {
		return authAppWapId;
	}

	public void setAppWapId(Integer appWapId) {
		this.appWapId = appWapId;
	}

	@Column(name="app_wap_id", nullable=true, length=11)	
	public Integer getAppWapId() {
		return appWapId;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Column(name="object_type", nullable=true, length=16)	
	public String getObjectType() {
		return objectType;
	}

	
	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}
	
	@Column(name="ou_id", nullable=true, length=11)	
	public Integer getOuId() {
		return ouId;
	}	

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	@Column(name="dep_code", nullable=true, length=64)	
	public String getDepCode() {
		return depCode;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	@Column(name="emp_id", nullable=true, length=11)	
	public Integer getEmpId() {
		return empId;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name="province", nullable=true, length=64)	
	public String getProvince() {
		return province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="city", nullable=true, length=64)	
	public String getCity() {
		return city;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name="area", nullable=true, length=64)	
	public String getArea() {
		return area;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Column(name="store", nullable=true, length=64)	
	public String getStore() {
		return store;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	@Column(name="dealer", nullable=true, length=64)	
	public String getDealer() {
		return dealer;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
