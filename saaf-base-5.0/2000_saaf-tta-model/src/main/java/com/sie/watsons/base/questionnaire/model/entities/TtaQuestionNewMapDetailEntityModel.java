package com.sie.watsons.base.questionnaire.model.entities;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;

/**
 * TtaOiBillLineEntity_HI_RO Entity Object
 * Sun Sep 01 19:33:36 CST 2019  Auto Generate
 */
public class TtaQuestionNewMapDetailEntityModel {

	@ExcelProperty(value = "map_detail_id")
	private Integer mapDetailId;

	@ExcelProperty(value = "sku_description")
	private String skuDesc;

	@ExcelProperty(value = "cost")
	private BigDecimal cost;

	@ExcelProperty(value = "retail_price")
	private BigDecimal retailPrice;

	@ExcelProperty(value = "remark")
	private String remark;

	@ExcelProperty(value = "promo_price")
	private BigDecimal promoPrice;

	private String normalGp;

	private String promoGp;

	public String getSkuDesc() {
		return skuDesc;
	}

	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		this.promoPrice = promoPrice;
	}

	public Integer getMapDetailId() {
		return mapDetailId;
	}

	public void setMapDetailId(Integer mapDetailId) {
		this.mapDetailId = mapDetailId;
	}

	public String getNormalGp() {
		return normalGp;
	}

	public void setNormalGp(String normalGp) {
		this.normalGp = normalGp;
	}

	public String getPromoGp() {
		return promoGp;
	}

	public void setPromoGp(String promoGp) {
		this.promoGp = promoGp;
	}
}
