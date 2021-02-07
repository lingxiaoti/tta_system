package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 * TtaTesteroiLineEntity_HI_MODEL Entity Object
 * Wed Oct 23 19:17:01 CST 2019  Auto Generate
 */

public class TtaTesteroiLineEntity_HI_MODEL {
    private Integer ttaTesteroiLineId;
    @ExcelProperty(value = "ITEM")
    private String itemCode;
    @ExcelProperty(value = "STORE")
    private String storeCode;
    @ExcelProperty(value = "ACCOUNT_DATE")
    private Date accountDate;
    @ExcelProperty(value = "TRAN_DATE")
    private Date tranDate;
    @ExcelProperty(value = "QTY")
    private Integer qty;
    @ExcelProperty(value = "AMT_EX")
    private Integer amtEx;
    @ExcelProperty(value = "AMT_IN")
    private Integer amtIn;
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;

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

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
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
}
