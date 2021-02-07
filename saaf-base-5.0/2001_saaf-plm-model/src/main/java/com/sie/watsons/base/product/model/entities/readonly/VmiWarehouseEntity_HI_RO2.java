package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * VmiWarehouseEntity_HI_RO Entity Object Mon Dec 02 20:44:35 CST 2019 Auto
 * Generate
 */

public class VmiWarehouseEntity_HI_RO2 {
	public static String query = " select address_detail,vh_pre_code from \r\n"
			+ " (select address_detail,vh_pre_code from vmi_warehouse  \r\n"
			+ " group by vh_pre_code,address_detail) warehouse where 1=1 ";

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

	public Integer getVhId() {
		return vhId;
	}

	public void setVhCode(String vhCode) {
		this.vhCode = vhCode;
	}

	public String getVhCode() {
		return vhCode;
	}

	public void setVhName(String vhName) {
		this.vhName = vhName;
	}

	public String getVhName() {
		return vhName;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContacts() {
		return contacts;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVhPreCode(String vhPreCode) {
		this.vhPreCode = vhPreCode;
	}

	public String getVhPreCode() {
		return vhPreCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
