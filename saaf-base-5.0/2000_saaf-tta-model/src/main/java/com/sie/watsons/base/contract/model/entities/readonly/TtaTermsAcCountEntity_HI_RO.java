package com.sie.watsons.base.contract.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTermsAcCountEntity_HI_RO Entity Object
 * Sun Sep 29 11:23:17 CST 2019  Auto Generate
 */

public class TtaTermsAcCountEntity_HI_RO {
	public static final String  QUERY ="select *  from tta_terms_ac_count ttac where 1=1 " ;
    private Integer ttaTermsAcCountId;
    @JSONField(format="yyyy-MM-dd")
    private Date year;
    private String yTermsSource;
    private String yTermsTarget;
    private String tableName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer orderNo;
    private String dataType;
    private String contractTermCn;
    private Integer orderNum;
    private Integer showStatus;
    private Integer operatorUserId;

	public void setTtaTermsAcCountId(Integer ttaTermsAcCountId) {
		this.ttaTermsAcCountId = ttaTermsAcCountId;
	}

	
	public Integer getTtaTermsAcCountId() {
		return ttaTermsAcCountId;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	
	public Date getYear() {
		return year;
	}

	public void setYTermsSource(String yTermsSource) {
		this.yTermsSource = yTermsSource;
	}

	
	public String getYTermsSource() {
		return yTermsSource;
	}

	public void setYTermsTarget(String yTermsTarget) {
		this.yTermsTarget = yTermsTarget;
	}

	
	public String getYTermsTarget() {
		return yTermsTarget;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
	public String getTableName() {
		return tableName;
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

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	
	public String getDataType() {
		return dataType;
	}

	public void setContractTermCn(String contractTermCn) {
		this.contractTermCn = contractTermCn;
	}

	
	public String getContractTermCn() {
		return contractTermCn;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	
	public Integer getShowStatus() {
		return showStatus;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
