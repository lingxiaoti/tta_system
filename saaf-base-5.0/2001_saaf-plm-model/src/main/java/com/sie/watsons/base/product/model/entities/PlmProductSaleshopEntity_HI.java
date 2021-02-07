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
 * PlmProductSaleshopEntity_HI Entity Object Fri Apr 17 16:04:07 CST 2020 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_SALESHOP")
public class PlmProductSaleshopEntity_HI {
	private Integer id;
	private Integer productHeadId;
	private String plmCode;
	private String productName;
	private String shopLoc;
	private Integer salesId;
	private String isSales;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "PLM_PRODUCT_SALESHOPSEQ", sequenceName = "PLM_PRODUCT_SALESHOPSEQ", allocationSize = 1)
	@GeneratedValue(generator = "PLM_PRODUCT_SALESHOPSEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 22)
	public Integer getId() {
		return id;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	@Column(name = "plm_code", nullable = true, length = 250)
	public String getPlmCode() {
		return plmCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_name", nullable = true, length = 20)
	public String getProductName() {
		return productName;
	}

	public void setShopLoc(String shopLoc) {
		this.shopLoc = shopLoc;
	}

	@Column(name = "shop_loc", nullable = true, length = 50)
	public String getShopLoc() {
		return shopLoc;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	@Column(name = "sales_id", nullable = true, length = 22)
	public Integer getSalesId() {
		return salesId;
	}

	public void setIsSales(String isSales) {
		this.isSales = isSales;
	}

	@Column(name = "is_sales", nullable = true, length = 20)
	public String getIsSales() {
		return isSales;
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
