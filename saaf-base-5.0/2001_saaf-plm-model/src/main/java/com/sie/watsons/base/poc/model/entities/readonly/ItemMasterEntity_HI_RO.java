package com.sie.watsons.base.poc.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * ItemMasterEntity_HI_RO Entity Object
 * Tue Aug 13 12:21:37 CST 2019  Auto Generate
 */

public class ItemMasterEntity_HI_RO {
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

	
	public String getItem() {
		return item;
	}

	public void setItemNumberType(String itemNumberType) {
		this.itemNumberType = itemNumberType;
	}

	
	public String getItemNumberType() {
		return itemNumberType;
	}

	public void setFormatId(String formatId) {
		this.formatId = formatId;
	}

	
	public String getFormatId() {
		return formatId;
	}

	public void setPrefix(java.math.BigDecimal prefix) {
		this.prefix = prefix;
	}

	
	public java.math.BigDecimal getPrefix() {
		return prefix;
	}

	public void setItemParent(String itemParent) {
		this.itemParent = itemParent;
	}

	
	public String getItemParent() {
		return itemParent;
	}

	public void setItemGrandparent(String itemGrandparent) {
		this.itemGrandparent = itemGrandparent;
	}

	
	public String getItemGrandparent() {
		return itemGrandparent;
	}

	public void setPackInd(String packInd) {
		this.packInd = packInd;
	}

	
	public String getPackInd() {
		return packInd;
	}

	public void setItemLevel(java.math.BigDecimal itemLevel) {
		this.itemLevel = itemLevel;
	}

	
	public java.math.BigDecimal getItemLevel() {
		return itemLevel;
	}

	public void setTranLevel(java.math.BigDecimal tranLevel) {
		this.tranLevel = tranLevel;
	}

	
	public java.math.BigDecimal getTranLevel() {
		return tranLevel;
	}

	public void setItemAggregateInd(String itemAggregateInd) {
		this.itemAggregateInd = itemAggregateInd;
	}

	
	public String getItemAggregateInd() {
		return itemAggregateInd;
	}

	public void setDiff1(String diff1) {
		this.diff1 = diff1;
	}

	
	public String getDiff1() {
		return diff1;
	}

	public void setDiff1AggregateInd(String diff1AggregateInd) {
		this.diff1AggregateInd = diff1AggregateInd;
	}

	
	public String getDiff1AggregateInd() {
		return diff1AggregateInd;
	}

	public void setDiff2(String diff2) {
		this.diff2 = diff2;
	}

	
	public String getDiff2() {
		return diff2;
	}

	public void setDiff2AggregateInd(String diff2AggregateInd) {
		this.diff2AggregateInd = diff2AggregateInd;
	}

	
	public String getDiff2AggregateInd() {
		return diff2AggregateInd;
	}

	public void setDiff3(String diff3) {
		this.diff3 = diff3;
	}

	
	public String getDiff3() {
		return diff3;
	}

	public void setDiff3AggregateInd(String diff3AggregateInd) {
		this.diff3AggregateInd = diff3AggregateInd;
	}

	
	public String getDiff3AggregateInd() {
		return diff3AggregateInd;
	}

	public void setDiff4(String diff4) {
		this.diff4 = diff4;
	}

	
	public String getDiff4() {
		return diff4;
	}

	public void setDiff4AggregateInd(String diff4AggregateInd) {
		this.diff4AggregateInd = diff4AggregateInd;
	}

	
	public String getDiff4AggregateInd() {
		return diff4AggregateInd;
	}

	public void setDept(java.math.BigDecimal dept) {
		this.dept = dept;
	}

	
	public java.math.BigDecimal getDept() {
		return dept;
	}

	public void setClass(java.math.BigDecimal midClass) {
		this.midClass = midClass;
	}

	
	public java.math.BigDecimal getMidClass() {
		return midClass;
	}

	public void setSubclass(java.math.BigDecimal subclass) {
		this.subclass = subclass;
	}

	
	public java.math.BigDecimal getSubclass() {
		return subclass;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	
	public String getItemDesc() {
		return itemDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	
	public String getShortDesc() {
		return shortDesc;
	}

	public void setDescUp(String descUp) {
		this.descUp = descUp;
	}

	
	public String getDescUp() {
		return descUp;
	}

	public void setPrimaryRefItemInd(String primaryRefItemInd) {
		this.primaryRefItemInd = primaryRefItemInd;
	}

	
	public String getPrimaryRefItemInd() {
		return primaryRefItemInd;
	}

	public void setRetailZoneGroupId(java.math.BigDecimal retailZoneGroupId) {
		this.retailZoneGroupId = retailZoneGroupId;
	}

	
	public java.math.BigDecimal getRetailZoneGroupId() {
		return retailZoneGroupId;
	}

	public void setCostZoneGroupId(java.math.BigDecimal costZoneGroupId) {
		this.costZoneGroupId = costZoneGroupId;
	}

	
	public java.math.BigDecimal getCostZoneGroupId() {
		return costZoneGroupId;
	}

	public void setStandardUom(String standardUom) {
		this.standardUom = standardUom;
	}

	
	public String getStandardUom() {
		return standardUom;
	}

	public void setUomConvFactor(java.math.BigDecimal uomConvFactor) {
		this.uomConvFactor = uomConvFactor;
	}

	
	public java.math.BigDecimal getUomConvFactor() {
		return uomConvFactor;
	}

	public void setPackageSize(java.math.BigDecimal packageSize) {
		this.packageSize = packageSize;
	}

	
	public java.math.BigDecimal getPackageSize() {
		return packageSize;
	}

	public void setPackageUom(String packageUom) {
		this.packageUom = packageUom;
	}

	
	public String getPackageUom() {
		return packageUom;
	}

	public void setMerchandiseInd(String merchandiseInd) {
		this.merchandiseInd = merchandiseInd;
	}

	
	public String getMerchandiseInd() {
		return merchandiseInd;
	}

	public void setStoreOrdMult(String storeOrdMult) {
		this.storeOrdMult = storeOrdMult;
	}

	
	public String getStoreOrdMult() {
		return storeOrdMult;
	}

	public void setForecastInd(String forecastInd) {
		this.forecastInd = forecastInd;
	}

	
	public String getForecastInd() {
		return forecastInd;
	}

	public void setOriginalRetail(java.math.BigDecimal originalRetail) {
		this.originalRetail = originalRetail;
	}

	
	public java.math.BigDecimal getOriginalRetail() {
		return originalRetail;
	}

	public void setMfgRecRetail(java.math.BigDecimal mfgRecRetail) {
		this.mfgRecRetail = mfgRecRetail;
	}

	
	public java.math.BigDecimal getMfgRecRetail() {
		return mfgRecRetail;
	}

	public void setRetailLabelType(String retailLabelType) {
		this.retailLabelType = retailLabelType;
	}

	
	public String getRetailLabelType() {
		return retailLabelType;
	}

	public void setRetailLabelValue(java.math.BigDecimal retailLabelValue) {
		this.retailLabelValue = retailLabelValue;
	}

	
	public java.math.BigDecimal getRetailLabelValue() {
		return retailLabelValue;
	}

	public void setHandlingTemp(String handlingTemp) {
		this.handlingTemp = handlingTemp;
	}

	
	public String getHandlingTemp() {
		return handlingTemp;
	}

	public void setHandlingSensitivity(String handlingSensitivity) {
		this.handlingSensitivity = handlingSensitivity;
	}

	
	public String getHandlingSensitivity() {
		return handlingSensitivity;
	}

	public void setCatchWeightInd(String catchWeightInd) {
		this.catchWeightInd = catchWeightInd;
	}

	
	public String getCatchWeightInd() {
		return catchWeightInd;
	}

	public void setFirstReceived(Date firstReceived) {
		this.firstReceived = firstReceived;
	}

	
	public Date getFirstReceived() {
		return firstReceived;
	}

	public void setLastReceived(Date lastReceived) {
		this.lastReceived = lastReceived;
	}

	
	public Date getLastReceived() {
		return lastReceived;
	}

	public void setQtyReceived(java.math.BigDecimal qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	
	public java.math.BigDecimal getQtyReceived() {
		return qtyReceived;
	}

	public void setWasteType(String wasteType) {
		this.wasteType = wasteType;
	}

	
	public String getWasteType() {
		return wasteType;
	}

	public void setWastePct(java.math.BigDecimal wastePct) {
		this.wastePct = wastePct;
	}

	
	public java.math.BigDecimal getWastePct() {
		return wastePct;
	}

	public void setDefaultWastePct(java.math.BigDecimal defaultWastePct) {
		this.defaultWastePct = defaultWastePct;
	}

	
	public java.math.BigDecimal getDefaultWastePct() {
		return defaultWastePct;
	}

	public void setConstDimenInd(String constDimenInd) {
		this.constDimenInd = constDimenInd;
	}

	
	public String getConstDimenInd() {
		return constDimenInd;
	}

	public void setSimplePackInd(String simplePackInd) {
		this.simplePackInd = simplePackInd;
	}

	
	public String getSimplePackInd() {
		return simplePackInd;
	}

	public void setContainsInnerInd(String containsInnerInd) {
		this.containsInnerInd = containsInnerInd;
	}

	
	public String getContainsInnerInd() {
		return containsInnerInd;
	}

	public void setSellableInd(String sellableInd) {
		this.sellableInd = sellableInd;
	}

	
	public String getSellableInd() {
		return sellableInd;
	}

	public void setOrderableInd(String orderableInd) {
		this.orderableInd = orderableInd;
	}

	
	public String getOrderableInd() {
		return orderableInd;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	
	public String getPackType() {
		return packType;
	}

	public void setOrderAsType(String orderAsType) {
		this.orderAsType = orderAsType;
	}

	
	public String getOrderAsType() {
		return orderAsType;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public String getComments() {
		return comments;
	}

	public void setItemServiceLevel(String itemServiceLevel) {
		this.itemServiceLevel = itemServiceLevel;
	}

	
	public String getItemServiceLevel() {
		return itemServiceLevel;
	}

	public void setGiftWrapInd(String giftWrapInd) {
		this.giftWrapInd = giftWrapInd;
	}

	
	public String getGiftWrapInd() {
		return giftWrapInd;
	}

	public void setShipAloneInd(String shipAloneInd) {
		this.shipAloneInd = shipAloneInd;
	}

	
	public String getShipAloneInd() {
		return shipAloneInd;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setCheckUdaInd(String checkUdaInd) {
		this.checkUdaInd = checkUdaInd;
	}

	
	public String getCheckUdaInd() {
		return checkUdaInd;
	}

	public void setPnsEffectiveDateFrom(Date pnsEffectiveDateFrom) {
		this.pnsEffectiveDateFrom = pnsEffectiveDateFrom;
	}

	
	public Date getPnsEffectiveDateFrom() {
		return pnsEffectiveDateFrom;
	}

	public void setPnsEffectiveDateTo(Date pnsEffectiveDateTo) {
		this.pnsEffectiveDateTo = pnsEffectiveDateTo;
	}

	
	public Date getPnsEffectiveDateTo() {
		return pnsEffectiveDateTo;
	}

	public void setPnsBarcodePluType(String pnsBarcodePluType) {
		this.pnsBarcodePluType = pnsBarcodePluType;
	}

	
	public String getPnsBarcodePluType() {
		return pnsBarcodePluType;
	}

	public void setExtSourceSystem(String extSourceSystem) {
		this.extSourceSystem = extSourceSystem;
	}

	
	public String getExtSourceSystem() {
		return extSourceSystem;
	}

	public void setItemDescSecondary(String itemDescSecondary) {
		this.itemDescSecondary = itemDescSecondary;
	}

	
	public String getItemDescSecondary() {
		return itemDescSecondary;
	}

	public void setBandedItemInd(String bandedItemInd) {
		this.bandedItemInd = bandedItemInd;
	}

	
	public String getBandedItemInd() {
		return bandedItemInd;
	}

	public void setPrimaryCaseSize(String primaryCaseSize) {
		this.primaryCaseSize = primaryCaseSize;
	}

	
	public String getPrimaryCaseSize() {
		return primaryCaseSize;
	}

	public void setAipPackType(String aipPackType) {
		this.aipPackType = aipPackType;
	}

	
	public String getAipPackType() {
		return aipPackType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
