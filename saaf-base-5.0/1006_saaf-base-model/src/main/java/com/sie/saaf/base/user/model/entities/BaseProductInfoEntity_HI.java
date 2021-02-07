package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BaseProductInfoEntity_HI Entity Object
 * Wed Dec 27 18:00:58 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_product_info")
public class BaseProductInfoEntity_HI {
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
	private Integer shelfLifeMonth;//保质期
	private Integer lotControlCode;//
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;
    private String series;//物料系列:47-办公用品,66-礼品

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_PRODUCT_INFO", sequenceName = "SEQ_BASE_PRODUCT_INFO", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PRODUCT_INFO", strategy = GenerationType.SEQUENCE)	
	@Column(name = "product_id", nullable = false, length = 11)	
	public Integer getProductId() {
		return productId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "organization_id", nullable = true, length = 11)	
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "item_id", nullable = true, length = 11)	
	public Integer getItemId() {
		return itemId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 11)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	@Column(name = "channel_code", nullable = true, length = 100)	
	public String getChannelCode() {
		return channelCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "item_code", nullable = true, length = 50)	
	public String getItemCode() {
		return itemCode;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "is_valid", nullable = true, length = 1)	
	public String getIsValid() {
		return isValid;
	}

	public void setInnerItemCode(String innerItemCode) {
		this.innerItemCode = innerItemCode;
	}

	@Column(name = "inner_item_code", nullable = true, length = 10)	
	public String getInnerItemCode() {
		return innerItemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "item_name", nullable = true, length = 100)	
	public String getItemName() {
		return itemName;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Column(name = "item_desc", nullable = true, length = 280)	
	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Column(name = "item_type", nullable = true, length = 30)	
	public String getItemType() {
		return itemType;
	}

	public void setUnit_(String unit_) {
		this.unit_ = unit_;
	}

	@Column(name = "unit_", nullable = true, length = 10)	
	public String getUnit_() {
		return unit_;
	}

	public void setBoxUnit(String boxUnit) {
		this.boxUnit = boxUnit;
	}

	@Column(name = "box_unit", nullable = true, length = 10)	
	public String getBoxUnit() {
		return boxUnit;
	}

	public void setTrayUnit(String trayUnit) {
		this.trayUnit = trayUnit;
	}

	@Column(name = "tray_unit", nullable = true, length = 10)	
	public String getTrayUnit() {
		return trayUnit;
	}

	public void setTrackBarcode(String trackBarcode) {
		this.trackBarcode = trackBarcode;
	}

	@Column(name = "track_barcode", nullable = true, length = 1)	
	public String getTrackBarcode() {
		return trackBarcode;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	@Column(name = "net_weight", precision = 11, scale = 3)	
	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	@Column(name = "gross_weight", precision = 11, scale = 3)	
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setPkgVolume(BigDecimal pkgVolume) {
		this.pkgVolume = pkgVolume;
	}

	@Column(name = "pkg_volume", precision = 11, scale = 3)	
	public BigDecimal getPkgVolume() {
		return pkgVolume;
	}

	@Column(name = "LOT_CONTROL_CODE", nullable = true,length = 11)
	public Integer getLotControlCode() {
		return lotControlCode;
	}

	public void setLotControlCode(Integer lotControlCode) {
		this.lotControlCode = lotControlCode;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
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

	@Column(name = "shelf_life_month", nullable = true, length = 11)
	public Integer getShelfLifeMonth() {
		return shelfLifeMonth;
	}

	public void setShelfLifeMonth(Integer shelfLifeMonth) {
		this.shelfLifeMonth = shelfLifeMonth;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@Column(name = "series", nullable = true, length = 10)
	public String getSeries() {
		return series;
	}
}
