package com.sie.saaf.common.bean;

import java.util.List;

public class ProductEntity {
    private String code; // 单品物流码
    private String boxCode; // 所属箱物流码
    private String itemCode; // 产品编码
    private String itemDesc; // 产品名称
    private String batchCode; // 产品批次
    private String productDate; // 产品效期
    private String masterInv; // IF商编码
    private String masterName; // IF商名称
    private String invCode; // 子库编码
    private String invName; // 子库名称
    private String saleStatus; // 积分状态

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getMasterInv() {
        return masterInv;
    }

    public void setMasterInv(String masterInv) {
        this.masterInv = masterInv;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }
}
