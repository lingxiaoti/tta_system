package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmTaxTypeListEntity_HI_RO Entity Object
 * Wed Nov 06 11:32:35 CST 2019  Auto Generate
 */

public class PlmTaxTypeListEntity_HI_RO {
    private Integer plmTaxTypeListId;
    private String plmTaxTypeCode;
    private String plmTaxTypeName;
    private String billStatus;
    private String billStatusName;
    private String remarks;
    private String creator;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;
    private Integer createdBy;

    public static final String QUERY_SQL =
			"SELECT pttl.PLM_TAX_TYPE_LIST_ID as plmTaxTypeListId,\n" +
			"       pttl.PLM_TAX_TYPE_CODE    as plmTaxTypeCode,\n" +
			"       pttl.PLM_TAX_TYPE_NAME    as plmTaxTypeName,\n" +
			"       pttl.BILL_STATUS          as billStatus,\n" +
			"       pttl.BILL_STATUS_NAME     as billStatusName,\n" +
			"       pttl.REMARKS              as remarks,\n" +
			"       pttl.CREATOR              as creator,\n" +
			"       pttl.LAST_UPDATED_BY      as lastUpdatedBy,\n" +
			"       pttl.LAST_UPDATE_LOGIN    as lastUpdateLogin,\n" +
			"       pttl.LAST_UPDATE_DATE     as lastUpdateDate,\n" +
			"       pttl.VERSION_NUM          as versionNum,\n" +
			"		pttl.CREATED_BY		  	  as createdBy,\n" +
			"       pttl.CREATION_DATE        as creationDate\n" +
			"FROM PLM_TAX_TYPE_LIST pttl\n" +
			"WHERE 1 = 1 ";

	public void setPlmTaxTypeListId(Integer plmTaxTypeListId) {
		this.plmTaxTypeListId = plmTaxTypeListId;
	}

	
	public Integer getPlmTaxTypeListId() {
		return plmTaxTypeListId;
	}

	public void setPlmTaxTypeCode(String plmTaxTypeCode) {
		this.plmTaxTypeCode = plmTaxTypeCode;
	}

	
	public String getPlmTaxTypeCode() {
		return plmTaxTypeCode;
	}

	public void setPlmTaxTypeName(String plmTaxTypeName) {
		this.plmTaxTypeName = plmTaxTypeName;
	}

	
	public String getPlmTaxTypeName() {
		return plmTaxTypeName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	
	public String getBillStatusName() {
		return billStatusName;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public String getCreator() {
		return creator;
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

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
}
