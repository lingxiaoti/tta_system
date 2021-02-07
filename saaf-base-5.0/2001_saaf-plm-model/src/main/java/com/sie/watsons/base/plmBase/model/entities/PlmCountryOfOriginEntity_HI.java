package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmCountryOfOriginEntity_HI Entity Object Sat Nov 09 11:47:05 CST 2019 Auto
 * Generate
 * 
 * @author psh
 */
@Entity
@Table(name = "PLM_COUNTRY_OF_ORIGIN")
public class PlmCountryOfOriginEntity_HI {
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer plmCountryOfOriginId;
	private String areaEn;
	private String areaCn;
	private String nameAbbreviation;
	private String billStatusName;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer operatorUserId;

	private String currencyCost;

	@Column(name = "currency_cost", nullable = true, length = 22)
	public String getCurrencyCost() {
		return currencyCost;
	}

	public void setCurrencyCost(String currencyCost) {
		this.currencyCost = currencyCost;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
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

	public void setPlmCountryOfOriginId(Integer plmCountryOfOriginId) {
		this.plmCountryOfOriginId = plmCountryOfOriginId;
	}

	@Id
	@SequenceGenerator(name = "plmCountryOfOriginEntity_HISeqGener", sequenceName = "SEQ_PLM_COUNTRY_OF_ORIGIN", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "plmCountryOfOriginEntity_HISeqGener", strategy = GenerationType.SEQUENCE)
	@Column(name = "plm_country_of_origin_id", nullable = false, length = 22)
	public Integer getPlmCountryOfOriginId() {
		return plmCountryOfOriginId;
	}

	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}

	@Column(name = "area_en", nullable = true, length = 50)
	public String getAreaEn() {
		return areaEn;
	}

	public void setAreaCn(String areaCn) {
		this.areaCn = areaCn;
	}

	@Column(name = "area_cn", nullable = true, length = 100)
	public String getAreaCn() {
		return areaCn;
	}

	public void setNameAbbreviation(String nameAbbreviation) {
		this.nameAbbreviation = nameAbbreviation;
	}

	@Column(name = "name_abbreviation", nullable = true, length = 20)
	public String getNameAbbreviation() {
		return nameAbbreviation;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	@Column(name = "bill_status_name", nullable = true, length = 50)
	public String getBillStatusName() {
		return billStatusName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
