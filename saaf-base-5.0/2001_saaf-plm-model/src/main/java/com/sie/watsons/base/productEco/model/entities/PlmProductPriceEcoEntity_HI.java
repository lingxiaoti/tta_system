package com.sie.watsons.base.productEco.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductPriceEcoEntity_HI Entity Object
 * Fri May 22 14:29:48 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_PRICE_ECO")
public class PlmProductPriceEcoEntity_HI {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
    private Integer priceId;
    private String priceGroup;
    private String priceArea;
    private String unitPrice;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String flags;
    private String updateSeson;
    private String seson;
    private String updateType;
    private Integer storeId;
    private String groupId;
    private String areaId;
    private String mainPrice;
    private String isUpdate;
    private String priceGroupCode;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatePriceDate;
    private Integer operatorUserId;

	public void setEcoId(Integer ecoId) {
		this.ecoId = ecoId;
	}

	@Column(name="eco_id", nullable=false, length=22)	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_LINE", sequenceName = "SEQ_PLM_PRODUCT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="line_id", nullable=false, length=22)	
	public Integer getLineId() {
		return lineId;
	}

	public void setAcdType(String acdType) {
		this.acdType = acdType;
	}

	@Column(name="acd_type", nullable=true, length=20)	
	public String getAcdType() {
		return acdType;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	@Column(name="price_id", nullable=false, length=22)	
	public Integer getPriceId() {
		return priceId;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

	@Column(name="price_group", nullable=true, length=255)	
	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceArea(String priceArea) {
		this.priceArea = priceArea;
	}

	@Column(name="price_area", nullable=true, length=255)	
	public String getPriceArea() {
		return priceArea;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name="unit_price", nullable=true, length=100)	
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name="product_head_id", nullable=true, length=22)	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
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

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@Column(name="flags", nullable=true, length=255)	
	public String getFlags() {
		return flags;
	}

	public void setUpdateSeson(String updateSeson) {
		this.updateSeson = updateSeson;
	}

	@Column(name="update_seson", nullable=true, length=255)	
	public String getUpdateSeson() {
		return updateSeson;
	}

	public void setSeson(String seson) {
		this.seson = seson;
	}

	@Column(name="seson", nullable=true, length=255)	
	public String getSeson() {
		return seson;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	@Column(name="update_type", nullable=true, length=255)	
	public String getUpdateType() {
		return updateType;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	@Column(name="store_id", nullable=true, length=22)	
	public Integer getStoreId() {
		return storeId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name="group_id", nullable=true, length=255)	
	public String getGroupId() {
		return groupId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name="area_id", nullable=true, length=255)	
	public String getAreaId() {
		return areaId;
	}

	public void setMainPrice(String mainPrice) {
		this.mainPrice = mainPrice;
	}

	@Column(name="main_price", nullable=true, length=255)	
	public String getMainPrice() {
		return mainPrice;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name="is_update", nullable=true, length=50)	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setPriceGroupCode(String priceGroupCode) {
		this.priceGroupCode = priceGroupCode;
	}

	@Column(name="price_group_code", nullable=true, length=255)	
	public String getPriceGroupCode() {
		return priceGroupCode;
	}

	public void setUpdatePriceDate(Date updatePriceDate) {
		this.updatePriceDate = updatePriceDate;
	}

	@Column(name="update_price_date", nullable=true, length=7)	
	public Date getUpdatePriceDate() {
		return updatePriceDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
