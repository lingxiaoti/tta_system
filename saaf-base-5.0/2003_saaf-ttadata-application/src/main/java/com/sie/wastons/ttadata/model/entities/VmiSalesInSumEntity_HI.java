package com.sie.wastons.ttadata.model.entities;

import java.math.BigInteger;
import java.util.Date;


/**
 * VmiSalesInSumEntity_HI Entity Object
 * Tue Oct 15 18:09:42 CST 2019  Auto Generate
 */
public class VmiSalesInSumEntity_HI {
    private BigInteger saleId;
    private String vendorNbr;
    private String store;
    private String item;
    private java.math.BigDecimal salesQtyFour;
    private java.math.BigDecimal salesQtyThirteen;
    private java.math.BigDecimal salesAmtFour;
    private java.math.BigDecimal salesAmtThirteen;
    private java.math.BigDecimal salesExcAmtFour;
    private java.math.BigDecimal salesExcAmtThirteen;
    private Integer versionNum;
    private Integer weekTime;
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;


	public Integer getWeekTime() {
		return weekTime;
	}

	public void setWeekTime(Integer weekTime) {
		this.weekTime = weekTime;
	}

	public void setSaleId(BigInteger saleId) {
		this.saleId = saleId;
	}

	public BigInteger getSaleId() {
		return saleId;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getStore() {
		return store;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setSalesQtyFour(java.math.BigDecimal salesQtyFour) {
		this.salesQtyFour = salesQtyFour;
	}

	public java.math.BigDecimal getSalesQtyFour() {
		return salesQtyFour;
	}

	public void setSalesQtyThirteen(java.math.BigDecimal salesQtyThirteen) {
		this.salesQtyThirteen = salesQtyThirteen;
	}

	public java.math.BigDecimal getSalesQtyThirteen() {
		return salesQtyThirteen;
	}

	public void setSalesAmtFour(java.math.BigDecimal salesAmtFour) {
		this.salesAmtFour = salesAmtFour;
	}

	public java.math.BigDecimal getSalesAmtFour() {
		return salesAmtFour;
	}

	public void setSalesAmtThirteen(java.math.BigDecimal salesAmtThirteen) {
		this.salesAmtThirteen = salesAmtThirteen;
	}

	public java.math.BigDecimal getSalesAmtThirteen() {
		return salesAmtThirteen;
	}

	public void setSalesExcAmtFour(java.math.BigDecimal salesExcAmtFour) {
		this.salesExcAmtFour = salesExcAmtFour;
	}

	public java.math.BigDecimal getSalesExcAmtFour() {
		return salesExcAmtFour;
	}

	public void setSalesExcAmtThirteen(java.math.BigDecimal salesExcAmtThirteen) {
		this.salesExcAmtThirteen = salesExcAmtThirteen;
	}

	public java.math.BigDecimal getSalesExcAmtThirteen() {
		return salesExcAmtThirteen;
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
