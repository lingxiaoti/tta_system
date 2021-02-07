package com.sie.watsons.base.sync.model.entities;

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
 * PlmSyncItemSuppFromRmsEntity_HI Entity Object
 * Tue Aug 11 11:03:42 GMT+08:00 2020  Auto Generate
 */
@Entity
@Table(name="PLM_SYNC_ITEM_SUPP_FROM_RMS")
public class PlmSyncItemSuppFromRmsEntity_HI {
    private String roundToInnerPct;
    private String roundToCasePct;
    private String roundToLayerPct;
    private String roundToPalletPct;
    private String hi;
    private String ti;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String lastUpdateId;
    private String processStatus;
    private java.math.BigDecimal syncId;
    private String item;
    private String supplier;
    private String primarySuppInd;
    private String whConsignInd;
    private String originCountryId;
    private String unitCost;
    private String suppPackSize;
    private String innerPackSize;
    private Integer operatorUserId;

	public void setRoundToInnerPct(String roundToInnerPct) {
		this.roundToInnerPct = roundToInnerPct;
	}

	@Column(name="round_to_inner_pct", nullable=true, length=10)	
	public String getRoundToInnerPct() {
		return roundToInnerPct;
	}

	public void setRoundToCasePct(String roundToCasePct) {
		this.roundToCasePct = roundToCasePct;
	}

	@Column(name="round_to_case_pct", nullable=true, length=10)	
	public String getRoundToCasePct() {
		return roundToCasePct;
	}

	public void setRoundToLayerPct(String roundToLayerPct) {
		this.roundToLayerPct = roundToLayerPct;
	}

	@Column(name="round_to_layer_pct", nullable=true, length=10)	
	public String getRoundToLayerPct() {
		return roundToLayerPct;
	}

	public void setRoundToPalletPct(String roundToPalletPct) {
		this.roundToPalletPct = roundToPalletPct;
	}

	@Column(name="round_to_pallet_pct", nullable=true, length=10)	
	public String getRoundToPalletPct() {
		return roundToPalletPct;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}

	@Column(name="hi", nullable=true, length=10)	
	public String getHi() {
		return hi;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	@Column(name="ti", nullable=true, length=10)	
	public String getTi() {
		return ti;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Column(name="last_update_datetime", nullable=true, length=7)	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	@Column(name="last_update_id", nullable=true, length=20)	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name="process_status", nullable=true, length=10)	
	public String getProcessStatus() {
		return processStatus;
	}

	public void setSyncId(java.math.BigDecimal syncId) {
		this.syncId = syncId;
	}

    @Id
    @SequenceGenerator(name="plmSyncItemSuppFromRmsEntity_HISeqGener", sequenceName="SEQ_PLM_SYNC_ITEM_SUPP_FROM_RMS", allocationSize=1, initialValue=1)
    @GeneratedValue(generator="plmSyncItemSuppFromRmsEntity_HISeqGener",strategy=GenerationType.SEQUENCE)
	@Column(name="sync_id", nullable=true, length=22)	
	public java.math.BigDecimal getSyncId() {
		return syncId;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name="item", nullable=true, length=30)	
	public String getItem() {
		return item;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name="supplier", nullable=true, length=30)	
	public String getSupplier() {
		return supplier;
	}

	public void setPrimarySuppInd(String primarySuppInd) {
		this.primarySuppInd = primarySuppInd;
	}

	@Column(name="primary_supp_ind", nullable=true, length=10)	
	public String getPrimarySuppInd() {
		return primarySuppInd;
	}

	public void setWhConsignInd(String whConsignInd) {
		this.whConsignInd = whConsignInd;
	}

	@Column(name="wh_consign_ind", nullable=true, length=10)	
	public String getWhConsignInd() {
		return whConsignInd;
	}

	public void setOriginCountryId(String originCountryId) {
		this.originCountryId = originCountryId;
	}

	@Column(name="origin_country_id", nullable=true, length=30)	
	public String getOriginCountryId() {
		return originCountryId;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	@Column(name="unit_cost", nullable=true, length=30)	
	public String getUnitCost() {
		return unitCost;
	}

	public void setSuppPackSize(String suppPackSize) {
		this.suppPackSize = suppPackSize;
	}

	@Column(name="supp_pack_size", nullable=true, length=10)	
	public String getSuppPackSize() {
		return suppPackSize;
	}

	public void setInnerPackSize(String innerPackSize) {
		this.innerPackSize = innerPackSize;
	}

	@Column(name="inner_pack_size", nullable=true, length=10)	
	public String getInnerPackSize() {
		return innerPackSize;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
