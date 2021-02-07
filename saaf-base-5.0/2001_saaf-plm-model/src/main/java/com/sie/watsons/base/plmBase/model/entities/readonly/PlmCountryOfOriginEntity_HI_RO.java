package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmCountryOfOriginEntity_HI_RO Entity Object Sat Nov 09 11:47:05 CST 2019
 * Auto Generate
 */

public class PlmCountryOfOriginEntity_HI_RO {
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

	public static final String QUERY_SQL = "SELECT pcoo.PLM_COUNTRY_OF_ORIGIN_ID as plmCountryOfOriginId,\n"
			+ "       pcoo.AREA_EN as areaEn,\n"
			+ "       pcoo.AREA_CN as areaCn,\n"
			+ "       pcoo.NAME_ABBREVIATION as nameAbbreviation,\n"
			+ "       pcoo.BILL_STATUS_NAME as billStatusName,\n"
			+ "       pcoo.CREATED_BY as createdBy,\n"
			+ "       pcoo.LAST_UPDATED_BY as lastUpdatedBy,\n"
			+ "       pcoo.LAST_UPDATE_LOGIN as lastUpdateLogin,\n"
			+ "       pcoo.LAST_UPDATE_DATE as lastUpdateDate,\n"
			+ "       pcoo.VERSION_NUM as versionNum,\n"
			+ "       pcoo.CREATION_DATE as creationDate,\n"
			+ "       pcoo.CURRENCY_COST as currencyCost "
			+ " FROM PLM_COUNTRY_OF_ORIGIN pcoo \n" + "WHERE 1=1 ";

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getCurrencyCost() {
		return currencyCost;
	}

	public void setCurrencyCost(String currencyCost) {
		this.currencyCost = currencyCost;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setPlmCountryOfOriginId(Integer plmCountryOfOriginId) {
		this.plmCountryOfOriginId = plmCountryOfOriginId;
	}

	public Integer getPlmCountryOfOriginId() {
		return plmCountryOfOriginId;
	}

	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}

	public String getAreaEn() {
		return areaEn;
	}

	public void setAreaCn(String areaCn) {
		this.areaCn = areaCn;
	}

	public String getAreaCn() {
		return areaCn;
	}

	public void setNameAbbreviation(String nameAbbreviation) {
		this.nameAbbreviation = nameAbbreviation;
	}

	public String getNameAbbreviation() {
		return nameAbbreviation;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	public String getBillStatusName() {
		return billStatusName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
