package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmLocationListEntity_HI_RO Entity Object Tue Oct 22 09:33:19 CST 2019 Auto
 * Generate
 */

public class PlmLocationListEntity_HI_RO {
	private Integer plmLocationListId;
	private String descName;
	private String code;
	private String creatorName;
	private Integer createdBy;
	private String creatorCode;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private String warehouseCode; // 仓库编号

	public final static String QUERY_SQL = "SELECT pll.PLM_LOCATION_LIST_ID as plmLocationListId,\n"
			+ "       pll.DESC_NAME            as descName,\n"
			+ "       pll.CODE                 as code,\n"
			+ "       pll.CREATOR_NAME         as creatorName,\n"
			+ "		pll.CREATOR_CODE		 as creatorCode,\n"
			+ "       pll.CREATED_BY           as createdBy,\n"
			+ "       pll.LAST_UPDATED_BY      as lastUpdatedBy,\n"
			+ "       pll.LAST_UPDATE_DATE     as lastUpdateDate,\n"
			+ "       pll.LAST_UPDATE_LOGIN    as lastUpdateLogin,\n"
			+ "       pll.VERSION_NUM          as versionNum,\n"
			+ "       pll.CREATION_DATE        as creationDate,\n"
			+ "       pll.WAREHOUSE_CODE       as warehouseCode  "
			+ " FROM PLM_LOCATION_LIST pll \n" + "WHERE 1 = 1  ";

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public void setPlmLocationListId(Integer plmLocationListId) {
		this.plmLocationListId = plmLocationListId;
	}

	public Integer getPlmLocationListId() {
		return plmLocationListId;
	}

	public void setDescName(String descName) {
		this.descName = descName;
	}

	public String getDescName() {
		return descName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorName() {
		return creatorName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}
}
