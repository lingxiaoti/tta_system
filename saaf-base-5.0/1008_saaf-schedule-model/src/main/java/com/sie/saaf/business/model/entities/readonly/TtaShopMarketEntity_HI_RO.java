package com.sie.saaf.business.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaShopMarketEntity_HI_RO Entity Object
 * Mon Jul 08 15:39:04 CST 2019  Auto Generate
 */

/**
 *
 * 店铺对应市场实体类
 *
 */
public class TtaShopMarketEntity_HI_RO {
    private Integer shopMarketId;//主键
    private String shopName;//店铺
    private String cityName;//城市
    private String provinceName;//省份
    private String marketName;//市场
    private String areaName;//区域
    private String channel;//渠道
    private String shopLevel;//店铺级别
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date openShop;//店铺开店时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date closeShop;//店铺关店时间
    private String warehouseCode;//仓库编号
    private String warehouseName;//仓库名称
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

	
	public Integer getShopMarketId() {
		return shopMarketId;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	
	public String getShopName() {
		return shopName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
	public String getCityName() {
		return cityName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	
	public String getProvinceName() {
		return provinceName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	
	public String getMarketName() {
		return marketName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	
	public String getAreaName() {
		return areaName;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	
	public String getChannel() {
		return channel;
	}

	public void setShopLevel(String shopLevel) {
		this.shopLevel = shopLevel;
	}

	
	public String getShopLevel() {
		return shopLevel;
	}

	public void setOpenShop(Date openShop) {
		this.openShop = openShop;
	}

	
	public Date getOpenShop() {
		return openShop;
	}

	public void setCloseShop(Date closeShop) {
		this.closeShop = closeShop;
	}

	
	public Date getCloseShop() {
		return closeShop;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	
	public String getWarehouseName() {
		return warehouseName;
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
