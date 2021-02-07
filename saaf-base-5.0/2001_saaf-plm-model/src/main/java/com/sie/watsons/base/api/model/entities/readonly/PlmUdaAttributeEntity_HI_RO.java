package com.sie.watsons.base.api.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmUdaAttributeEntity_HI_RO Entity Object Wed Dec 18 15:18:54 CST 2019 Auto
 * Generate
 */

public class PlmUdaAttributeEntity_HI_RO {
	public static String query = "SELECT\r\n"
			+ "     attrs.head_id             AS headId,\r\n"
			+ "     attrs.uda_id              AS udaId,\r\n"
			+ "     attrs.uda_desc            AS udaDesc,\r\n"
			+ "     attrs.module              AS module,\r\n"
			+ "     attrs.display_type        AS displayType,\r\n"
			+ "     attrs.data_type           AS dataType,\r\n"
			+ "     attrs.data_length         AS dataLength,\r\n"
			+ "     attrs.single_value_ind    AS singleValueInd,\r\n"
			+ "     attrs.uda_value_desc      AS udaValueDesc,\r\n"
			+ "     attrs.version_num         AS versionNum,\r\n"
			+ "     attrs.creation_date       AS creationDate,\r\n"
			+ "     attrs.created_by          AS createdBy,\r\n"
			+ "     attrs.last_updated_by     AS lastUpdatedBy,\r\n"
			+ "     attrs.last_update_date    AS lastUpdateDate,\r\n"
			+ "     attrs.last_update_login   AS lastUpdateLogin,\r\n"
			+ "     attrs.uda_value           AS udaValue\r\n" + " FROM\r\n"
			+ "     plm_uda_attribute attrs where 1=1 ";
	private Integer headId;
	private Integer udaId;
	private String udaDesc;
	private String module;
	private String displayType;
	private String dataType;
	private Integer dataLength;
	private String singleValueInd;
	private String udaValueDesc;
	private java.math.BigDecimal versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private java.math.BigDecimal lastUpdateLogin;
	private Integer operatorUserId;
	private Integer udaValue;

	public Integer getUdaValue() {
		return udaValue;
	}

	public void setUdaValue(Integer udaValue) {
		this.udaValue = udaValue;
	}

	public Integer getHeadId() {
		return headId;
	}

	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	public Integer getUdaId() {
		return udaId;
	}

	public void setUdaId(Integer udaId) {
		this.udaId = udaId;
	}

	public String getUdaDesc() {
		return udaDesc;
	}

	public void setUdaDesc(String udaDesc) {
		this.udaDesc = udaDesc;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDataLength() {
		return dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public String getSingleValueInd() {
		return singleValueInd;
	}

	public void setSingleValueInd(String singleValueInd) {
		this.singleValueInd = singleValueInd;
	}

	public String getUdaValueDesc() {
		return udaValueDesc;
	}

	public void setUdaValueDesc(String udaValueDesc) {
		this.udaValueDesc = udaValueDesc;
	}

	public BigDecimal getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(BigDecimal versionNum) {
		this.versionNum = versionNum;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(BigDecimal lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
}
