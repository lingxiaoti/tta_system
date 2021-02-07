package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmSalesAreaRowEntity_HI_RO Entity Object
 * Wed Nov 06 11:32:36 CST 2019  Auto Generate
 */

public class PlmSalesAreaRowEntity_HI_RO {
    private Integer plmSalesAreaRowId;
    private Integer plmSalesAreaId;
    private String areaCode;
    private String areaName;
    private String creator;
    private String groupCode;
    private String groupName;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
    private Integer operatorUserId;

    public static final String QUERY_SQL = "SELECT psar.PLM_SALES_AREA_ROW_ID as plmSalesAreaRowId,\n" +
			"       psar.PLM_SALES_AREA_ID as plmSalesAreaId,\n" +
			"       psar.AREA_CODE as areaCode,\n" +
			"       psar.AREA_NAME as areaName,\n" +
			"       psar.CREATOR as creator,\n" +
			"       psar.GROUP_CODE as groupCode,\n" +
			"       psar.GROUP_NAME as groupName,\n" +
			"       psar.CREATED_BY as createdBy,\n" +
			"       psar.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       psar.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       psar.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       psar.VERSION_NUM as versionNum,\n" +
			"       psar.CREATION_DATE as creationDate\n" +
			"FROM PLM_SALES_AREA_ROW psar\n" +
			"WHERE 1=1 ";

	public void setPlmSalesAreaRowId(Integer plmSalesAreaRowId) {
		this.plmSalesAreaRowId = plmSalesAreaRowId;
	}

	
	public Integer getPlmSalesAreaRowId() {
		return plmSalesAreaRowId;
	}

	public void setPlmSalesAreaId(Integer plmSalesAreaId) {
		this.plmSalesAreaId = plmSalesAreaId;
	}

	
	public Integer getPlmSalesAreaId() {
		return plmSalesAreaId;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	
	public String getAreaName() {
		return areaName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public String getCreator() {
		return creator;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
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
}
