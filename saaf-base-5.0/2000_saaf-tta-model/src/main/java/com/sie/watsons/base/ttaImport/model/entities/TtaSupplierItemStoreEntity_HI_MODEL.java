package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2019/10/24/024.
 */
public class TtaSupplierItemStoreEntity_HI_MODEL {
    private Integer mappinigId;
    @ExcelProperty(value = "TRAN_DATE")
    private Date tranDate;
    @ExcelProperty(value = "STORE")
    private String storeCode;
    @ExcelProperty(value = "ITEM")
    private String itemCode;
    @ExcelProperty(value = "SUPPLIER")
    private String vendorCode;
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;

    public void setMappinigId(Integer mappinigId) {
        this.mappinigId = mappinigId;
    }


    public Integer getMappinigId() {
        return mappinigId;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }


    public Date getTranDate() {
        return tranDate;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }


    public String getStoreCode() {
        return storeCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }


    public String getVendorCode() {
        return vendorCode;
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

