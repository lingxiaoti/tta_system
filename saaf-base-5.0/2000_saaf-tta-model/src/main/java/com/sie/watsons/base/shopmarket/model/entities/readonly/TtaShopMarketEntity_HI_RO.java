package com.sie.watsons.base.shopmarket.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaShopMarketEntity_HI_RO Entity Object
 * Fri Aug 02 10:50:42 CST 2019  Auto Generate
 */

public class TtaShopMarketEntity_HI_RO {

	public static final String TTA_SHOP_MARKE_AREA ="      select tm.area area  \n" +
			"      from  tta_shop_market tm\n" +
			"      where 1=1 ";

	public static final String TTA_SHOP_MARKE ="select tm.warehouse_code warehouseCode,\n" +
			"tm.warehouse_name  warehouseName\n" +
			"from   tta_shop_market tm where 1=1";

	private String shopCode;
	private String shopName;
	private String cityName;
	private String provinceName;
	private String marketName;
	private String area;
	private String channel;
	private String shopLevel;
	private Integer openShop;
	private Integer closeShop;
	private Integer warehouseCode;
	private String warehouseName;
	private Integer updateDate;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}


	public String getShopCode() {
		return shopCode;
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

	public void setArea(String area) {
		this.area = area;
	}


	public String getArea() {
		return area;
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

	public void setOpenShop(Integer openShop) {
		this.openShop = openShop;
	}


	public Integer getOpenShop() {
		return openShop;
	}

	public void setCloseShop(Integer closeShop) {
		this.closeShop = closeShop;
	}


	public Integer getCloseShop() {
		return closeShop;
	}

	public void setWarehouseCode(Integer warehouseCode) {
		this.warehouseCode = warehouseCode;
	}


	public Integer getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}


	public String getWarehouseName() {
		return warehouseName;
	}

	public void setUpdateDate(Integer updateDate) {
		this.updateDate = updateDate;
	}


	public Integer getUpdateDate() {
		return updateDate;
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
