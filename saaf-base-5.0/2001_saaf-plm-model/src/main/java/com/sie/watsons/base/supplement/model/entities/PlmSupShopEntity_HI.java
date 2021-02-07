package com.sie.watsons.base.supplement.model.entities;

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
 * PlmSupShopEntity_HI Entity Object
 * Mon Sep 23 17:32:30 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_SUP_SHOP")
public class PlmSupShopEntity_HI {
    private Integer plmSupShopId;
    private String wc;
    private String region;
    private String meter;
    private String pogCode;
    private String shopCode;
    private String item;
    private String stopReason;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
	private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setPlmSupShopId(Integer plmSupShopId) {
		this.plmSupShopId = plmSupShopId;
	}

	@Id	
	@SequenceGenerator(name="SEQ_PLM_SUP_SHOP", sequenceName="SEQ_PLM_SUP_SHOP", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_PLM_SUP_SHOP",strategy=GenerationType.SEQUENCE)
	@Column(name="plm_sup_shop_id", nullable=false, length=11)	
	public Integer getPlmSupShopId() {
		return plmSupShopId;
	}

	public void setWc(String wc) {
		this.wc = wc;
	}

	@Column(name="wc", nullable=true, length=255)	
	public String getWc() {
		return wc;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name="region", nullable=true, length=255)	
	public String getRegion() {
		return region;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	@Column(name="meter", nullable=true, length=255)	
	public String getMeter() {
		return meter;
	}

	public void setPogCode(String pogCode) {
		this.pogCode = pogCode;
	}

	@Column(name="pog_code", nullable=true, length=255)	
	public String getPogCode() {
		return pogCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	@Column(name="shop_code", nullable=true, length=255)	
	public String getShopCode() {
		return shopCode;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
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

	@Column(name="created_by", nullable=true, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="last_update_login", nullable=true, length=11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="stop_reason", nullable=true, length=255)
	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	@Column(name="item", nullable=true, length=50)
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
