package com.sie.watsons.base.product.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductSaleshopEntity_HI_RO Entity Object
 * Fri Apr 17 16:04:07 CST 2020  Auto Generate
 */

public class PlmProductSaleshopEntity_HI_RO {
    private Integer id;
    private Integer productHeadId;
    private String plmCode;
    private String productName;
    private String shopLoc;
    private Integer salesId;
    private String isSales;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	
	public String getPlmCode() {
		return plmCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setShopLoc(String shopLoc) {
		this.shopLoc = shopLoc;
	}

	
	public String getShopLoc() {
		return shopLoc;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	
	public Integer getSalesId() {
		return salesId;
	}

	public void setIsSales(String isSales) {
		this.isSales = isSales;
	}

	
	public String getIsSales() {
		return isSales;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
