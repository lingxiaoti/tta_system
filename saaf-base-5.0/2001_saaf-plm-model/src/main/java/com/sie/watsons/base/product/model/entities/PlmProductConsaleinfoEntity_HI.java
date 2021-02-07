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
 * PlmProductConsaleinfoEntity_HI Entity Object Fri Mar 27 12:26:24 CST 2020
 * Auto Generate
 */
@Entity
@Table(name = "plm_product_consaleinfo")
public class PlmProductConsaleinfoEntity_HI {
	private Integer id;
	private String requestId;
	private String whConsignDesc;
	private String currencyCode;
	private String exchangeRate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private String adjBasis;
	private String adjValue;
	private String supplier;
	private String locType;
	private String loc;
	private String consimentType;
	private String consignmentRate;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private Integer storeId;

	@Column(name = "store_id", nullable = true, length = 22)
	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_CONSALEINFO", sequenceName = "SEQ_PLM_PRODUCT_CONSALEINFO", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_CONSALEINFO", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 22)
	public Integer getId() {
		return id;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name = "request_id", nullable = true, length = 255)
	public String getRequestId() {
		return requestId;
	}

	public void setWhConsignDesc(String whConsignDesc) {
		this.whConsignDesc = whConsignDesc;
	}

	@Column(name = "wh_consign_desc", nullable = true, length = 255)
	public String getWhConsignDesc() {
		return whConsignDesc;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "currency_code", nullable = true, length = 50)
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "exchange_rate", nullable = true, length = 255)
	public String getExchangeRate() {
		return exchangeRate;
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

	public void setAdjBasis(String adjBasis) {
		this.adjBasis = adjBasis;
	}

	@Column(name = "adj_basis", nullable = true, length = 50)
	public String getAdjBasis() {
		return adjBasis;
	}

	public void setAdjValue(String adjValue) {
		this.adjValue = adjValue;
	}

	@Column(name = "adj_value", nullable = true, length = 50)
	public String getAdjValue() {
		return adjValue;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name = "supplier", nullable = true, length = 100)
	public String getSupplier() {
		return supplier;
	}

	public void setLocType(String locType) {
		this.locType = locType;
	}

	@Column(name = "loc_type", nullable = true, length = 50)
	public String getLocType() {
		return locType;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	@Column(name = "loc", nullable = true, length = 255)
	public String getLoc() {
		return loc;
	}

	public void setConsimentType(String consimentType) {
		this.consimentType = consimentType;
	}

	@Column(name = "consiment_type", nullable = true, length = 50)
	public String getConsimentType() {
		return consimentType;
	}

	public void setConsignmentRate(String consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	@Column(name = "consignment_rate", nullable = true, length = 100)
	public String getConsignmentRate() {
		return consignmentRate;
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

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
