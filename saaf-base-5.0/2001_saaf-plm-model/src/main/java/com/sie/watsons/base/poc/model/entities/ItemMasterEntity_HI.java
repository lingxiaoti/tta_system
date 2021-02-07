package com.sie.watsons.base.poc.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * ItemMasterEntity_HI Entity Object
 * Tue Aug 13 12:21:37 CST 2019  Auto Generate
 */
@Entity
@Table(name="item_master")
public class ItemMasterEntity_HI {
    private String item;
    private String itemNumberType;
    private String formatId;
    private java.math.BigDecimal prefix;
    private String itemParent;
    private String itemGrandparent;
    private String packInd;
    private java.math.BigDecimal itemLevel;
    private java.math.BigDecimal tranLevel;
    private String itemAggregateInd;
    private String diff1;
    private String diff1AggregateInd;
    private String diff2;
    private String diff2AggregateInd;
    private String diff3;
    private String diff3AggregateInd;
    private String diff4;
    private String diff4AggregateInd;
    private java.math.BigDecimal dept;
    private java.math.BigDecimal midClass;
    private java.math.BigDecimal subclass;
    private String status;
    private String itemDesc;
    private String shortDesc;
    private String descUp;
    private String primaryRefItemInd;
    private java.math.BigDecimal retailZoneGroupId;
    private java.math.BigDecimal costZoneGroupId;
    private String standardUom;
    private java.math.BigDecimal uomConvFactor;
    private java.math.BigDecimal packageSize;
    private String packageUom;
    private String merchandiseInd;
    private String storeOrdMult;
    private String forecastInd;
    private java.math.BigDecimal originalRetail;
    private java.math.BigDecimal mfgRecRetail;
    private String retailLabelType;
    private java.math.BigDecimal retailLabelValue;
    private String handlingTemp;
    private String handlingSensitivity;
    private String catchWeightInd;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date firstReceived;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastReceived;
    private java.math.BigDecimal qtyReceived;
    private String wasteType;
    private java.math.BigDecimal wastePct;
    private java.math.BigDecimal defaultWastePct;
    private String constDimenInd;
    private String simplePackInd;
    private String containsInnerInd;
    private String sellableInd;
    private String orderableInd;
    private String packType;
    private String orderAsType;
    private String comments;
    private String itemServiceLevel;
    private String giftWrapInd;
    private String shipAloneInd;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDatetime;
    private String lastUpdateId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String checkUdaInd;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date pnsEffectiveDateFrom;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date pnsEffectiveDateTo;
    private String pnsBarcodePluType;
    private String extSourceSystem;
    private String itemDescSecondary;
    private String bandedItemInd;
    private String primaryCaseSize;
    private String aipPackType;
    private Integer operatorUserId;

	public void setItem(String item) {
		this.item = item;
	}

	@Id
	@Column(name="item", nullable=false, length=25)	
	public String getItem() {
		return item;
	}

	public void setItemNumberType(String itemNumberType) {
		this.itemNumberType = itemNumberType;
	}

	@Column(name="item_number_type", nullable=false, length=6)	
	public String getItemNumberType() {
		return itemNumberType;
	}

	public void setFormatId(String formatId) {
		this.formatId = formatId;
	}

	@Column(name="format_id", nullable=true, length=1)	
	public String getFormatId() {
		return formatId;
	}

	public void setPrefix(java.math.BigDecimal prefix) {
		this.prefix = prefix;
	}

	@Column(name="prefix", nullable=true, length=22)	
	public java.math.BigDecimal getPrefix() {
		return prefix;
	}

	public void setItemParent(String itemParent) {
		this.itemParent = itemParent;
	}

	@Column(name="item_parent", nullable=true, length=25)	
	public String getItemParent() {
		return itemParent;
	}

	public void setItemGrandparent(String itemGrandparent) {
		this.itemGrandparent = itemGrandparent;
	}

	@Column(name="item_grandparent", nullable=true, length=25)	
	public String getItemGrandparent() {
		return itemGrandparent;
	}

	public void setPackInd(String packInd) {
		this.packInd = packInd;
	}

	@Column(name="pack_ind", nullable=false, length=1)	
	public String getPackInd() {
		return packInd;
	}

	public void setItemLevel(java.math.BigDecimal itemLevel) {
		this.itemLevel = itemLevel;
	}

	@Column(name="item_level", nullable=false, length=22)	
	public java.math.BigDecimal getItemLevel() {
		return itemLevel;
	}

	public void setTranLevel(java.math.BigDecimal tranLevel) {
		this.tranLevel = tranLevel;
	}

	@Column(name="tran_level", nullable=false, length=22)	
	public java.math.BigDecimal getTranLevel() {
		return tranLevel;
	}

	public void setItemAggregateInd(String itemAggregateInd) {
		this.itemAggregateInd = itemAggregateInd;
	}

	@Column(name="item_aggregate_ind", nullable=false, length=1)	
	public String getItemAggregateInd() {
		return itemAggregateInd;
	}

	public void setDiff1(String diff1) {
		this.diff1 = diff1;
	}

	@Column(name="diff_1", nullable=true, length=10)	
	public String getDiff1() {
		return diff1;
	}

	public void setDiff1AggregateInd(String diff1AggregateInd) {
		this.diff1AggregateInd = diff1AggregateInd;
	}

	@Column(name="diff_1_aggregate_ind", nullable=false, length=1)	
	public String getDiff1AggregateInd() {
		return diff1AggregateInd;
	}

	public void setDiff2(String diff2) {
		this.diff2 = diff2;
	}

	@Column(name="diff_2", nullable=true, length=10)	
	public String getDiff2() {
		return diff2;
	}

	public void setDiff2AggregateInd(String diff2AggregateInd) {
		this.diff2AggregateInd = diff2AggregateInd;
	}

	@Column(name="diff_2_aggregate_ind", nullable=false, length=1)	
	public String getDiff2AggregateInd() {
		return diff2AggregateInd;
	}

	public void setDiff3(String diff3) {
		this.diff3 = diff3;
	}

	@Column(name="diff_3", nullable=true, length=10)	
	public String getDiff3() {
		return diff3;
	}

	public void setDiff3AggregateInd(String diff3AggregateInd) {
		this.diff3AggregateInd = diff3AggregateInd;
	}

	@Column(name="diff_3_aggregate_ind", nullable=false, length=1)	
	public String getDiff3AggregateInd() {
		return diff3AggregateInd;
	}

	public void setDiff4(String diff4) {
		this.diff4 = diff4;
	}

	@Column(name="diff_4", nullable=true, length=10)	
	public String getDiff4() {
		return diff4;
	}

	public void setDiff4AggregateInd(String diff4AggregateInd) {
		this.diff4AggregateInd = diff4AggregateInd;
	}

	@Column(name="diff_4_aggregate_ind", nullable=false, length=1)	
	public String getDiff4AggregateInd() {
		return diff4AggregateInd;
	}

	public void setDept(java.math.BigDecimal dept) {
		this.dept = dept;
	}

	@Column(name="dept", nullable=false, length=22)	
	public java.math.BigDecimal getDept() {
		return dept;
	}

	public void setMidClass(java.math.BigDecimal midClass) {
		this.midClass = midClass;
	}

	@Column(name="class", nullable=false, length=22)	
	public java.math.BigDecimal getMidClass() {
		return midClass;
	}

	public void setSubclass(java.math.BigDecimal subclass) {
		this.subclass = subclass;
	}

	@Column(name="subclass", nullable=false, length=22)	
	public java.math.BigDecimal getSubclass() {
		return subclass;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=1)	
	public String getStatus() {
		return status;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Column(name="item_desc", nullable=false, length=100)	
	public String getItemDesc() {
		return itemDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@Column(name="short_desc", nullable=false, length=20)	
	public String getShortDesc() {
		return shortDesc;
	}

	public void setDescUp(String descUp) {
		this.descUp = descUp;
	}

	@Column(name="desc_up", nullable=false, length=100)	
	public String getDescUp() {
		return descUp;
	}

	public void setPrimaryRefItemInd(String primaryRefItemInd) {
		this.primaryRefItemInd = primaryRefItemInd;
	}

	@Column(name="primary_ref_item_ind", nullable=false, length=1)	
	public String getPrimaryRefItemInd() {
		return primaryRefItemInd;
	}

	public void setRetailZoneGroupId(java.math.BigDecimal retailZoneGroupId) {
		this.retailZoneGroupId = retailZoneGroupId;
	}

	@Column(name="retail_zone_group_id", nullable=true, length=22)	
	public java.math.BigDecimal getRetailZoneGroupId() {
		return retailZoneGroupId;
	}

	public void setCostZoneGroupId(java.math.BigDecimal costZoneGroupId) {
		this.costZoneGroupId = costZoneGroupId;
	}

	@Column(name="cost_zone_group_id", nullable=true, length=22)	
	public java.math.BigDecimal getCostZoneGroupId() {
		return costZoneGroupId;
	}

	public void setStandardUom(String standardUom) {
		this.standardUom = standardUom;
	}

	@Column(name="standard_uom", nullable=false, length=4)	
	public String getStandardUom() {
		return standardUom;
	}

	public void setUomConvFactor(java.math.BigDecimal uomConvFactor) {
		this.uomConvFactor = uomConvFactor;
	}

	@Column(name="uom_conv_factor", nullable=true, length=22)	
	public java.math.BigDecimal getUomConvFactor() {
		return uomConvFactor;
	}

	public void setPackageSize(java.math.BigDecimal packageSize) {
		this.packageSize = packageSize;
	}

	@Column(name="package_size", nullable=true, length=22)	
	public java.math.BigDecimal getPackageSize() {
		return packageSize;
	}

	public void setPackageUom(String packageUom) {
		this.packageUom = packageUom;
	}

	@Column(name="package_uom", nullable=true, length=4)	
	public String getPackageUom() {
		return packageUom;
	}

	public void setMerchandiseInd(String merchandiseInd) {
		this.merchandiseInd = merchandiseInd;
	}

	@Column(name="merchandise_ind", nullable=false, length=1)	
	public String getMerchandiseInd() {
		return merchandiseInd;
	}

	public void setStoreOrdMult(String storeOrdMult) {
		this.storeOrdMult = storeOrdMult;
	}

	@Column(name="store_ord_mult", nullable=false, length=1)	
	public String getStoreOrdMult() {
		return storeOrdMult;
	}

	public void setForecastInd(String forecastInd) {
		this.forecastInd = forecastInd;
	}

	@Column(name="forecast_ind", nullable=false, length=1)	
	public String getForecastInd() {
		return forecastInd;
	}

	public void setOriginalRetail(java.math.BigDecimal originalRetail) {
		this.originalRetail = originalRetail;
	}

	@Column(name="original_retail", nullable=true, length=22)	
	public java.math.BigDecimal getOriginalRetail() {
		return originalRetail;
	}

	public void setMfgRecRetail(java.math.BigDecimal mfgRecRetail) {
		this.mfgRecRetail = mfgRecRetail;
	}

	@Column(name="mfg_rec_retail", nullable=true, length=22)	
	public java.math.BigDecimal getMfgRecRetail() {
		return mfgRecRetail;
	}

	public void setRetailLabelType(String retailLabelType) {
		this.retailLabelType = retailLabelType;
	}

	@Column(name="retail_label_type", nullable=true, length=6)	
	public String getRetailLabelType() {
		return retailLabelType;
	}

	public void setRetailLabelValue(java.math.BigDecimal retailLabelValue) {
		this.retailLabelValue = retailLabelValue;
	}

	@Column(name="retail_label_value", nullable=true, length=22)	
	public java.math.BigDecimal getRetailLabelValue() {
		return retailLabelValue;
	}

	public void setHandlingTemp(String handlingTemp) {
		this.handlingTemp = handlingTemp;
	}

	@Column(name="handling_temp", nullable=true, length=6)	
	public String getHandlingTemp() {
		return handlingTemp;
	}

	public void setHandlingSensitivity(String handlingSensitivity) {
		this.handlingSensitivity = handlingSensitivity;
	}

	@Column(name="handling_sensitivity", nullable=true, length=6)	
	public String getHandlingSensitivity() {
		return handlingSensitivity;
	}

	public void setCatchWeightInd(String catchWeightInd) {
		this.catchWeightInd = catchWeightInd;
	}

	@Column(name="catch_weight_ind", nullable=false, length=1)	
	public String getCatchWeightInd() {
		return catchWeightInd;
	}

	public void setFirstReceived(Date firstReceived) {
		this.firstReceived = firstReceived;
	}

	@Column(name="first_received", nullable=true, length=7)	
	public Date getFirstReceived() {
		return firstReceived;
	}

	public void setLastReceived(Date lastReceived) {
		this.lastReceived = lastReceived;
	}

	@Column(name="last_received", nullable=true, length=7)	
	public Date getLastReceived() {
		return lastReceived;
	}

	public void setQtyReceived(java.math.BigDecimal qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	@Column(name="qty_received", nullable=true, length=22)	
	public java.math.BigDecimal getQtyReceived() {
		return qtyReceived;
	}

	public void setWasteType(String wasteType) {
		this.wasteType = wasteType;
	}

	@Column(name="waste_type", nullable=true, length=6)	
	public String getWasteType() {
		return wasteType;
	}

	public void setWastePct(java.math.BigDecimal wastePct) {
		this.wastePct = wastePct;
	}

	@Column(name="waste_pct", nullable=true, length=22)	
	public java.math.BigDecimal getWastePct() {
		return wastePct;
	}

	public void setDefaultWastePct(java.math.BigDecimal defaultWastePct) {
		this.defaultWastePct = defaultWastePct;
	}

	@Column(name="default_waste_pct", nullable=true, length=22)	
	public java.math.BigDecimal getDefaultWastePct() {
		return defaultWastePct;
	}

	public void setConstDimenInd(String constDimenInd) {
		this.constDimenInd = constDimenInd;
	}

	@Column(name="const_dimen_ind", nullable=false, length=1)	
	public String getConstDimenInd() {
		return constDimenInd;
	}

	public void setSimplePackInd(String simplePackInd) {
		this.simplePackInd = simplePackInd;
	}

	@Column(name="simple_pack_ind", nullable=false, length=1)	
	public String getSimplePackInd() {
		return simplePackInd;
	}

	public void setContainsInnerInd(String containsInnerInd) {
		this.containsInnerInd = containsInnerInd;
	}

	@Column(name="contains_inner_ind", nullable=false, length=1)	
	public String getContainsInnerInd() {
		return containsInnerInd;
	}

	public void setSellableInd(String sellableInd) {
		this.sellableInd = sellableInd;
	}

	@Column(name="sellable_ind", nullable=false, length=1)	
	public String getSellableInd() {
		return sellableInd;
	}

	public void setOrderableInd(String orderableInd) {
		this.orderableInd = orderableInd;
	}

	@Column(name="orderable_ind", nullable=false, length=1)	
	public String getOrderableInd() {
		return orderableInd;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	@Column(name="pack_type", nullable=true, length=1)	
	public String getPackType() {
		return packType;
	}

	public void setOrderAsType(String orderAsType) {
		this.orderAsType = orderAsType;
	}

	@Column(name="order_as_type", nullable=true, length=1)	
	public String getOrderAsType() {
		return orderAsType;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name="comments", nullable=true, length=250)	
	public String getComments() {
		return comments;
	}

	public void setItemServiceLevel(String itemServiceLevel) {
		this.itemServiceLevel = itemServiceLevel;
	}

	@Column(name="item_service_level", nullable=true, length=6)	
	public String getItemServiceLevel() {
		return itemServiceLevel;
	}

	public void setGiftWrapInd(String giftWrapInd) {
		this.giftWrapInd = giftWrapInd;
	}

	@Column(name="gift_wrap_ind", nullable=false, length=1)	
	public String getGiftWrapInd() {
		return giftWrapInd;
	}

	public void setShipAloneInd(String shipAloneInd) {
		this.shipAloneInd = shipAloneInd;
	}

	@Column(name="ship_alone_ind", nullable=false, length=1)	
	public String getShipAloneInd() {
		return shipAloneInd;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name="create_datetime", nullable=false, length=7)	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	@Column(name="last_update_id", nullable=false, length=30)	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Column(name="last_update_datetime", nullable=false, length=7)	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setCheckUdaInd(String checkUdaInd) {
		this.checkUdaInd = checkUdaInd;
	}

	@Column(name="check_uda_ind", nullable=true, length=1)	
	public String getCheckUdaInd() {
		return checkUdaInd;
	}

	public void setPnsEffectiveDateFrom(Date pnsEffectiveDateFrom) {
		this.pnsEffectiveDateFrom = pnsEffectiveDateFrom;
	}

	@Column(name="pns_effective_date_from", nullable=true, length=7)	
	public Date getPnsEffectiveDateFrom() {
		return pnsEffectiveDateFrom;
	}

	public void setPnsEffectiveDateTo(Date pnsEffectiveDateTo) {
		this.pnsEffectiveDateTo = pnsEffectiveDateTo;
	}

	@Column(name="pns_effective_date_to", nullable=true, length=7)	
	public Date getPnsEffectiveDateTo() {
		return pnsEffectiveDateTo;
	}

	public void setPnsBarcodePluType(String pnsBarcodePluType) {
		this.pnsBarcodePluType = pnsBarcodePluType;
	}

	@Column(name="pns_barcode_plu_type", nullable=true, length=1)	
	public String getPnsBarcodePluType() {
		return pnsBarcodePluType;
	}

	public void setExtSourceSystem(String extSourceSystem) {
		this.extSourceSystem = extSourceSystem;
	}

	@Column(name="ext_source_system", nullable=true, length=6)	
	public String getExtSourceSystem() {
		return extSourceSystem;
	}

	public void setItemDescSecondary(String itemDescSecondary) {
		this.itemDescSecondary = itemDescSecondary;
	}

	@Column(name="item_desc_secondary", nullable=true, length=255)	
	public String getItemDescSecondary() {
		return itemDescSecondary;
	}

	public void setBandedItemInd(String bandedItemInd) {
		this.bandedItemInd = bandedItemInd;
	}

	@Column(name="banded_item_ind", nullable=false, length=1)	
	public String getBandedItemInd() {
		return bandedItemInd;
	}

	public void setPrimaryCaseSize(String primaryCaseSize) {
		this.primaryCaseSize = primaryCaseSize;
	}

	@Column(name="primary_case_size", nullable=true, length=6)	
	public String getPrimaryCaseSize() {
		return primaryCaseSize;
	}

	public void setAipPackType(String aipPackType) {
		this.aipPackType = aipPackType;
	}

	@Column(name="aip_pack_type", nullable=true, length=6)	
	public String getAipPackType() {
		return aipPackType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
