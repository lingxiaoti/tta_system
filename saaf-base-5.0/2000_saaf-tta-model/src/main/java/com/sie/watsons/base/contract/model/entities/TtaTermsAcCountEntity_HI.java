package com.sie.watsons.base.contract.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTermsAcCountEntity_HI Entity Object
 * Sun Sep 29 11:23:17 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_TERMS_AC_COUNT")
public class TtaTermsAcCountEntity_HI {
    private Integer ttaTermsAcCountId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
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
    @Id
    @SequenceGenerator(name = "SEQ_TTA_TERMS_AC_COUNT", sequenceName = "SEQ_TTA_TERMS_AC_COUNT", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_TERMS_AC_COUNT", strategy = GenerationType.SEQUENCE)
	@Column(name="tta_terms_ac_count_id", nullable=false, length=22)	
	public Integer getTtaTermsAcCountId() {
		return ttaTermsAcCountId;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	@Column(name="year", nullable=true, length=7)	
	public Date getYear() {
		return year;
	}

	public void setYTermsSource(String yTermsSource) {
		this.yTermsSource = yTermsSource;
	}

	@Column(name="y_terms_source", nullable=true, length=500)	
	public String getYTermsSource() {
		return yTermsSource;
	}

	public void setYTermsTarget(String yTermsTarget) {
		this.yTermsTarget = yTermsTarget;
	}

	@Column(name="y_terms_target", nullable=true, length=500)	
	public String getYTermsTarget() {
		return yTermsTarget;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name="table_name", nullable=true, length=500)	
	public String getTableName() {
		return tableName;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=22)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name="data_type", nullable=true, length=20)	
	public String getDataType() {
		return dataType;
	}

	public void setContractTermCn(String contractTermCn) {
		this.contractTermCn = contractTermCn;
	}

	@Column(name="contract_term_cn", nullable=true, length=255)	
	public String getContractTermCn() {
		return contractTermCn;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name="order_num", nullable=true, length=22)	
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	@Column(name="show_status", nullable=true, length=22)	
	public Integer getShowStatus() {
		return showStatus;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}


}
