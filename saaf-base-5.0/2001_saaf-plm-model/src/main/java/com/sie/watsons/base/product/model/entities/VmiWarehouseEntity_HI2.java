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
 * VmiWarehouseEntity_HI Entity Object Mon Dec 02 20:44:35 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "vmi_warehouse")
public class VmiWarehouseEntity_HI2 {
	private Integer vhId;
	private String vhCode;
	private String vhName;
	private String addressDetail;
	private String contacts;
	private String phone;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer versionNum;
	private String vhPreCode;
	private Integer operatorUserId;

	public void setVhId(Integer vhId) {
		this.vhId = vhId;
	}

	@Id
	@SequenceGenerator(name = "VMI_WAREHOUSE_BILL_L_SEQ", sequenceName = "VMI_WAREHOUSE_BILL_L_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "VMI_WAREHOUSE_BILL_L_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "vh_id", nullable = false, length = 22)
	public Integer getVhId() {
		return vhId;
	}

	public void setVhCode(String vhCode) {
		this.vhCode = vhCode;
	}

	@Column(name = "vh_code", nullable = true, length = 50)
	public String getVhCode() {
		return vhCode;
	}

	public void setVhName(String vhName) {
		this.vhName = vhName;
	}

	@Column(name = "vh_name", nullable = true, length = 100)
	public String getVhName() {
		return vhName;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	@Column(name = "address_detail", nullable = true, length = 200)
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "contacts", nullable = true, length = 50)
	public String getContacts() {
		return contacts;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "phone", nullable = true, length = 50)
	public String getPhone() {
		return phone;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVhPreCode(String vhPreCode) {
		this.vhPreCode = vhPreCode;
	}

	@Column(name = "vh_pre_code", nullable = true, length = 50)
	public String getVhPreCode() {
		return vhPreCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
