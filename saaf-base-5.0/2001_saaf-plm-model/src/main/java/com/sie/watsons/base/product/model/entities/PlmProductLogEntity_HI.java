package com.sie.watsons.base.product.model.entities;

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
 * PlmProductLogEntity_HI Entity Object Fri Oct 18 13:55:02 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_LOG")
public class PlmProductLogEntity_HI {
	private Integer id;
	private String nodeName;
	private String userId;
	private String dealName;
	private String dealResult;
	private String aprovResult;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateLogin;
	private String operation;
	private String businessId;
	private String tableName;
	private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_LOG", sequenceName = "SEQ_PLM_PRODUCT_LOG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_LOG", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 22)
	public Integer getId() {
		return id;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "node_name", nullable = true, length = 255)
	public String getNodeName() {
		return nodeName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_id", nullable = true, length = 255)
	public String getUserId() {
		return userId;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	@Column(name = "deal_name", nullable = true, length = 255)
	public String getDealName() {
		return dealName;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	@Column(name = "deal_result", nullable = true, length = 255)
	public String getDealResult() {
		return dealResult;
	}

	public void setAprovResult(String aprovResult) {
		this.aprovResult = aprovResult;
	}

	@Column(name = "aprov_result", nullable = true, length = 255)
	public String getAprovResult() {
		return aprovResult;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "start_date", nullable = true, length = 7)
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "end_date", nullable = true, length = 7)
	public Date getEndDate() {
		return endDate;
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

	public void setLastUpdateLogin(Date lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 7)
	public Date getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "operation", nullable = true, length = 255)
	public String getOperation() {
		return operation;
	}

	@Column(name = "business_id", nullable = true, length = 255)
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "table_name", nullable = true, length = 255)
	public String getTableName() {
		return tableName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
