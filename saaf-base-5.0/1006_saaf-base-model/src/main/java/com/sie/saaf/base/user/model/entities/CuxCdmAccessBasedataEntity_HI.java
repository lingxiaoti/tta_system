package com.sie.saaf.base.user.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * CuxCdmAccessBasedataEntity_HI Entity Object
 * Mon Apr 23 21:28:23 CST 2018  Auto Generate
 */
@Entity
@Table(name = "cux_cdm_access_basedata")
public class CuxCdmAccessBasedataEntity_HI {
    private Integer orgId;
    private String accessType;
    private Integer userId;
    private Integer positionId;
    private Integer subordinatePersonId;
    private Integer subordinatePositionId;
    private Integer custAccountId;
    private String accountInt;
    private String territory2;
    private String territory3;
    private String secondaryInventoryName;
    private String channelType;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatetimeDatetime;
    private Integer lastUpdatetimedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDatetime;
    private Integer createdBy;
    private Integer lastUpdatetimeLogin;
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private Integer oaUserId;
    private Integer personId;
    private Integer operatorUserId;

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Id
	@Column(name = "org_id", nullable = true, length = 11)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	@Column(name = "access_type", nullable = true, length = 10)	
	public String getAccessType() {
		return accessType;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_id", nullable = true, length = 11)	
	public Integer getUserId() {
		return userId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	@Column(name = "position_id", nullable = true, length = 11)	
	public Integer getPositionId() {
		return positionId;
	}

	public void setSubordinatePersonId(Integer subordinatePersonId) {
		this.subordinatePersonId = subordinatePersonId;
	}

	@Column(name = "subordinate_person_id", nullable = true, length = 11)	
	public Integer getSubordinatePersonId() {
		return subordinatePersonId;
	}

	public void setSubordinatePositionId(Integer subordinatePositionId) {
		this.subordinatePositionId = subordinatePositionId;
	}

	@Column(name = "subordinate_position_id", nullable = true, length = 11)	
	public Integer getSubordinatePositionId() {
		return subordinatePositionId;
	}

	public void setCustAccountId(Integer custAccountId) {
		this.custAccountId = custAccountId;
	}

	@Column(name = "cust_account_id", nullable = true, length = 11)	
	public Integer getCustAccountId() {
		return custAccountId;
	}

	public void setAccountInt(String accountInt) {
		this.accountInt = accountInt;
	}

	@Column(name = "account_int", nullable = true, length = 30)	
	public String getAccountInt() {
		return accountInt;
	}

	public void setTerritory2(String territory2) {
		this.territory2 = territory2;
	}

	@Column(name = "territory2", nullable = true, length = 30)	
	public String getTerritory2() {
		return territory2;
	}

	public void setTerritory3(String territory3) {
		this.territory3 = territory3;
	}

	@Column(name = "territory3", nullable = true, length = 30)	
	public String getTerritory3() {
		return territory3;
	}

	public void setSecondaryInventoryName(String secondaryInventoryName) {
		this.secondaryInventoryName = secondaryInventoryName;
	}

	@Column(name = "secondary_inventory_name", nullable = true, length = 100)	
	public String getSecondaryInventoryName() {
		return secondaryInventoryName;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name = "channel_type", nullable = true, length = 30)	
	public String getChannelType() {
		return channelType;
	}

	public void setLastUpdatetimeDatetime(Date lastUpdatetimeDatetime) {
		this.lastUpdatetimeDatetime = lastUpdatetimeDatetime;
	}

	@Column(name = "last_updatetime_datetime", nullable = true, length = 0)	
	public Date getLastUpdatetimeDatetime() {
		return lastUpdatetimeDatetime;
	}

	public void setLastUpdatetimedBy(Integer lastUpdatetimedBy) {
		this.lastUpdatetimedBy = lastUpdatetimedBy;
	}

	@Column(name = "last_updatetimed_by", nullable = true, length = 11)	
	public Integer getLastUpdatetimedBy() {
		return lastUpdatetimedBy;
	}

	public void setCreationDatetime(Date creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	@Column(name = "creation_datetime", nullable = true, length = 0)	
	public Date getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatetimeLogin(Integer lastUpdatetimeLogin) {
		this.lastUpdatetimeLogin = lastUpdatetimeLogin;
	}

	@Column(name = "last_updatetime_login", nullable = true, length = 11)	
	public Integer getLastUpdatetimeLogin() {
		return lastUpdatetimeLogin;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name = "attribute_category", nullable = true, length = 30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name = "attribute1", nullable = true, length = 240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name = "attribute2", nullable = true, length = 240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name = "attribute3", nullable = true, length = 240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name = "attribute4", nullable = true, length = 240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name = "attribute5", nullable = true, length = 240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name = "attribute6", nullable = true, length = 240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name = "attribute7", nullable = true, length = 240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name = "attribute8", nullable = true, length = 240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name = "attribute9", nullable = true, length = 240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name = "attribute10", nullable = true, length = 240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	@Column(name = "attribute11", nullable = true, length = 240)	
	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	@Column(name = "attribute12", nullable = true, length = 240)	
	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	@Column(name = "attribute13", nullable = true, length = 240)	
	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	@Column(name = "attribute14", nullable = true, length = 240)	
	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	@Column(name = "attribute15", nullable = true, length = 240)	
	public String getAttribute15() {
		return attribute15;
	}

	public void setOaUserId(Integer oaUserId) {
		this.oaUserId = oaUserId;
	}

	@Column(name = "oa_user_id", nullable = true, length = 11)	
	public Integer getOaUserId() {
		return oaUserId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Column(name = "person_id", nullable = true, length = 11)	
	public Integer getPersonId() {
		return personId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
