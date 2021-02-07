package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmSpecialProductTypeEntity_HI_RO Entity Object
 * Thu Oct 10 10:13:32 CST 2019  Auto Generate
 */

public class PlmSpecialProductTypeEntity_HI_RO {
    private Integer plmSpecialProductTypeId;
    private String specialProductType;
    private String specialProductTypeName;
    private String controlType;
    private String controlValue;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    public static final String QUERY_SQL = "SELECT pspt.PLM_SPECIAL_PRODUCT_TYPE_ID as plmSpecialProductTypeId,\n" +
			"       pspt.SPECIAL_PRODUCT_TYPE        as specialProductType,\n" +
			"       pspt.SPECIAL_PRODUCT_TYPE_NAME   as specialProductTypeName,\n" +
			"       pspt.CONTROL_TYPE                as controlType,\n" +
			"       pspt.CONTROL_VALUE               as controlValue,\n" +
			"       pspt.CREATED_BY                  as createdBy,\n" +
			"       pspt.LAST_UPDATED_BY             as lastUpdatedBy,\n" +
			"       pspt.LAST_UPDATE_DATE            as lastUpdateDate,\n" +
			"       pspt.LAST_UPDATE_LOGIN           as lastUpdateLogin,\n" +
			"       pspt.VERSION_NUM                 as versionNum,\n" +
			"       pspt.CREATION_DATE               as creationDate\n" +
			"FROM PLM_SPECIAL_PRODUCT_TYPE pspt\n" +
			"WHERE 1 = 1 ";

	public void setPlmSpecialProductTypeId(Integer plmSpecialProductTypeId) {
		this.plmSpecialProductTypeId = plmSpecialProductTypeId;
	}

	
	public Integer getPlmSpecialProductTypeId() {
		return plmSpecialProductTypeId;
	}

	public void setSpecialProductType(String specialProductType) {
		this.specialProductType = specialProductType;
	}

	
	public String getSpecialProductType() {
		return specialProductType;
	}

	public void setSpecialProductTypeName(String specialProductTypeName) {
		this.specialProductTypeName = specialProductTypeName;
	}

	
	public String getSpecialProductTypeName() {
		return specialProductTypeName;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	
	public String getControlType() {
		return controlType;
	}

	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

	
	public String getControlValue() {
		return controlValue;
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
