package com.sie.watsons.base.ttaImport.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Wed Oct 23 19:17:01 CST 2019  Auto Generate
 */

public class TtaTesteroiLineEntity_HI_RO {

    public static final String  QUERY ="select *  from TTA_TESTEROI_LINE tbbl where 1=1 " ;

    private Integer ttaTesteroiLineId;
    private String itemCode;
    private String storeCode;
    @JSONField(format="yyyy-MM")
    private Date tranDate;
    @JSONField(format="yyyy-MM")
    private Date accountDate;
    private Integer qty;
    private Integer amtEx;
    private Integer amtIn;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setTtaTesteroiLineId(Integer ttaTesteroiLineId) {
		this.ttaTesteroiLineId = ttaTesteroiLineId;
	}

	
	public Integer getTtaTesteroiLineId() {
		return ttaTesteroiLineId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public String getStoreCode() {
		return storeCode;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	
	public Date getTranDate() {
		return tranDate;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	
	public Integer getQty() {
		return qty;
	}

	public void setAmtEx(Integer amtEx) {
		this.amtEx = amtEx;
	}

	
	public Integer getAmtEx() {
		return amtEx;
	}

	public void setAmtIn(Integer amtIn) {
		this.amtIn = amtIn;
	}

	
	public Integer getAmtIn() {
		return amtIn;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
