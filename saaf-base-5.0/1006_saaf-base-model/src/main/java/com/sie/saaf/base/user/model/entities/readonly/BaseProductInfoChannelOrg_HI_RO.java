package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhangJun
 * @createTime 2018-02-25 15:52
 * @description
 */
public class BaseProductInfoChannelOrg_HI_RO {

	public static final String QUERY_PRODUCT_SQL = "SELECT\n" +
			"    baseProductInfo.product_id AS productId,\n" +
			"    baseProductInfo.ORGANIZATION_ID AS organizationId,\n" +
			"    baseProductInfo.ITEM_ID AS itemId,\n" +
			"    baseProductInfo.ORG_ID AS orgId,\n" +
			"    baseProductInfo.CHANNEL_CODE AS channelCode,\n" +
			"    baseProductInfo.ITEM_CODE AS itemCode,\n" +
			"    baseProductInfo.IS_VALID AS isValid,\n" +
			"    baseProductInfo.INNER_ITEM_CODE AS innerItemCode,\n" +
			"    baseProductInfo.ITEM_NAME AS itemName,\n" +
			"    baseProductInfo.ITEM_DESC AS itemDesc,\n" +
			"    baseProductInfo.ITEM_TYPE AS itemType,\n" +
			"    baseProductInfo.UNIT_ AS unit_,\n" +
			"    baseProductInfo.BOX_UNIT AS boxUnit,\n" +
			"    baseProductInfo.TRAY_UNIT AS trayUnit,\n" +
			"    baseProductInfo.TRACK_BARCODE AS trackBarcode,\n" +
			"    baseProductInfo.NET_WEIGHT AS netWeight,\n" +
			"    baseProductInfo.GROSS_WEIGHT AS grossWeight,\n" +
			"    baseProductInfo.PKG_VOLUME AS pkgVolume,\n" +
			"    baseProductInfo.creation_date AS creationDate,\n" +
			"    baseProductInfo.created_by AS createdBy,\n" +
			"    baseProductInfo.last_updated_by AS lastUpdatedBy,\n" +
			"    baseProductInfo.last_update_date AS lastUpdateDate,\n" +
			"    baseProductInfo.version_num AS versionNum,\n" +
			"    baseProductInfo.last_update_login AS lastUpdateLogin,\n" +
			"    baseProductInfo.shelf_life_month AS shelfLifeMonth,\n" +
			"    baseProductInfo.series,\n" +
			"    baseChannel.CHANNEL_NAME AS channelName,\n" +
			"    baseChannel.CHANNEL_DESC AS channelDesc,\n" +
			"    baseOrganization.org_name AS organizationName,\n" +
			"	 priceList.operand AS price\n" +
			"FROM\n" +
			"    base_product_info AS baseProductInfo\n" +
			"LEFT JOIN \n" +
			"    base_organization  baseOrganization\n" +
			"ON \n" +
			"    baseProductInfo.ORGANIZATION_ID = baseOrganization.org_id\n" +
			"LEFT JOIN \n" +
			"    base_channel AS baseChannel\n" +
			"ON \n" +
			"    baseProductInfo.CHANNEL_CODE = baseChannel.CHANNEL_CODE\n" +
			"LEFT JOIN brc.cux_price_list priceList ON (\n" +
			"\tpriceList.inventory_item_id = baseProductInfo.item_id\n" +
			"\tAND priceList.org_id = baseProductInfo.org_id\n" +
			"\tAND trunc(sysdate) BETWEEN priceList.h_start_date_active\n" +
			"\tAND priceList.h_end_date_active\n" +
			"\tAND trunc(sysdate) BETWEEN priceList.l_start_date_active\n" +
			"\tAND priceList.l_end_date_active\n" +
			")\n" +
			"WHERE 1=1 ";

	public static final String QUERY_CHANNEL_SQL = "SELECT DISTINCT CP.ITEM_CODE itemCode\n" +
			"FROM BASE_CHANNEL_PRIVILEGE CP\n" +
			"WHERE 1=1\n" +
			" AND CP.ACCESS_TYPE = '6'\n" +
			" AND EXISTS (\n" +
			"  SELECT * FROM BASE_PRODUCT_INFO PRD\n" +
			"   WHERE 1=1\n" +
			"    AND PRD.ITEM_ID = CP.TRANSACTION_TYPE_ID\n" +
			"    AND PRD.ITEM_TYPE = 'FG'\n" +
			"    AND PRD.IS_VALID = 'Y'\n" +
			"    )";

	private Integer productId; //主键Id
	private Integer organizationId; //库存组织ID
	private Integer itemId; //物料ID
	private Integer orgId; //OU ID
	private String channelCode; //渠道编码
	private String itemCode; //产品编码
	private String isValid; //是否生效
	private String innerItemCode; //产品内部编码
	private String itemName; //产品名称
	private String itemDesc; //产品描述
	private String itemType; //产品类型
	private String unit_; //单位
	private String boxUnit; //单位数量/箱
	private String trayUnit; //单位数量/托盘
	private String trackBarcode; //是否跟踪条码
	private BigDecimal netWeight; //净重（NULL）
	private BigDecimal grossWeight; //毛重（NULL）
	private BigDecimal pkgVolume; //体积（NULL）
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建日期
	private Integer createdBy; //创建人
	private Integer lastUpdatedBy; //更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新日期
	private Integer versionNum; //版本号
	private Integer lastUpdateLogin; //last_update_login

	//渠道
	private String channelName;
	private String channelDesc;
	//OU
	private String ouOrgName;
	private String ouOrgCode;
	//库存组织
	private String organizationName;

	//保质期
	private Integer shelfLifeMonth;

	private BigDecimal price;// 单价

	private String series;//物料系列:47-办公用品,66-礼品

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getInnerItemCode() {
		return innerItemCode;
	}

	public void setInnerItemCode(String innerItemCode) {
		this.innerItemCode = innerItemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getUnit_() {
		return unit_;
	}

	public void setUnit_(String unit_) {
		this.unit_ = unit_;
	}

	public String getBoxUnit() {
		return boxUnit;
	}

	public void setBoxUnit(String boxUnit) {
		this.boxUnit = boxUnit;
	}

	public String getTrayUnit() {
		return trayUnit;
	}

	public void setTrayUnit(String trayUnit) {
		this.trayUnit = trayUnit;
	}

	public String getTrackBarcode() {
		return trackBarcode;
	}

	public void setTrackBarcode(String trackBarcode) {
		this.trackBarcode = trackBarcode;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getPkgVolume() {
		return pkgVolume;
	}

	public void setPkgVolume(BigDecimal pkgVolume) {
		this.pkgVolume = pkgVolume;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelDesc() {
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public String getOuOrgName() {
		return ouOrgName;
	}

	public void setOuOrgName(String ouOrgName) {
		this.ouOrgName = ouOrgName;
	}

	public String getOuOrgCode() {
		return ouOrgCode;
	}

	public void setOuOrgCode(String ouOrgCode) {
		this.ouOrgCode = ouOrgCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Integer getShelfLifeMonth() {
		return shelfLifeMonth;
	}

	public void setShelfLifeMonth(Integer shelfLifeMonth) {
		this.shelfLifeMonth = shelfLifeMonth;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}
}
