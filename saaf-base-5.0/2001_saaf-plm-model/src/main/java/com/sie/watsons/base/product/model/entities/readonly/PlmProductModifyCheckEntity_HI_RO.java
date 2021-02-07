package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProductModifyCheckEntity_HI_RO Entity Object
 * Tue Mar 31 17:24:42 CST 2020  Auto Generate
 */

public class PlmProductModifyCheckEntity_HI_RO {

	public static final String QUERY_UPDATE =" update PLM_PRODUCT_MODIFY_CHECK pmc set pmc.status='RMS_APPROVING' where 1=1 and pmc.check_id in (''";


	private Integer checkId;
    private String rmsCode;
    private Integer productHeadId;
    private String tableName;
    private Integer lineId;
    private String columnName;
    private String oldValue;
    private String newValue;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date  effectiveDate;
	private String detailInfo;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	
	public Integer getCheckId() {
		return checkId;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
	public String getTableName() {
		return tableName;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	
	public Integer getLineId() {
		return lineId;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	
	public String getColumnName() {
		return columnName;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	
	public String getOldValue() {
		return oldValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	
	public String getNewValue() {
		return newValue;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
