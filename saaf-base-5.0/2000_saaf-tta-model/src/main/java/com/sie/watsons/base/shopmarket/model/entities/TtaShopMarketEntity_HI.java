package com.sie.watsons.base.shopmarket.model.entities;

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
 * TtaShopMarketEntity_HI Entity Object
 * Fri Jul 26 17:12:48 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_shop_market")
public class TtaShopMarketEntity_HI {
    private Integer shopMarketId;
    private String shopCode;
    private String shopName;
    private String cityName;
    private String provinceName;
    private String marketName;
    private String areaName;
    private String channel;
    private String shopLevel;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date openShop;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date closeShop;
    private String warehouseCode;
    private String warehouseName;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setShopMarketId(Integer shopMarketId) {
		this.shopMarketId = shopMarketId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SHOP_MARKET", sequenceName="SEQ_TTA_SHOP_MARKET", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SHOP_MARKET",strategy=GenerationType.SEQUENCE)
	@Column(name="shop_market_id", nullable=false, length=22)	
	public Integer getShopMarketId() {
		return shopMarketId;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	@Column(name="shop_code", nullable=false, length=100)	
	public String getShopCode() {
		return shopCode;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name="shop_name", nullable=false, length=100)	
	public String getShopName() {
		return shopName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name="city_name", nullable=false, length=100)	
	public String getCityName() {
		return cityName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Column(name="province_name", nullable=false, length=100)	
	public String getProvinceName() {
		return provinceName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	@Column(name="market_name", nullable=false, length=100)	
	public String getMarketName() {
		return marketName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name="area_name", nullable=false, length=100)	
	public String getAreaName() {
		return areaName;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name="channel", nullable=false, length=100)	
	public String getChannel() {
		return channel;
	}

	public void setShopLevel(String shopLevel) {
		this.shopLevel = shopLevel;
	}

	@Column(name="shop_level", nullable=false, length=2)	
	public String getShopLevel() {
		return shopLevel;
	}

	public void setOpenShop(Date openShop) {
		this.openShop = openShop;
	}

	@Column(name="open_shop", nullable=false, length=7)	
	public Date getOpenShop() {
		return openShop;
	}

	public void setCloseShop(Date closeShop) {
		this.closeShop = closeShop;
	}

	@Column(name="close_shop", nullable=true, length=7)	
	public Date getCloseShop() {
		return closeShop;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column(name="warehouse_code", nullable=false, length=50)	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Column(name="warehouse_name", nullable=false, length=100)	
	public String getWarehouseName() {
		return warehouseName;
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

	@Column(name="creation_date", nullable=false, length=7)	
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

	@Column(name="last_update_date", nullable=false, length=7)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
