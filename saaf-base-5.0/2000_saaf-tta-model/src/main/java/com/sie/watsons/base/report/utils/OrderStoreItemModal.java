package com.sie.watsons.base.report.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;

public class OrderStoreItemModal extends BaseRowModel {
    @ExcelProperty(value = "SHOP_CODE")
    private BigDecimal shopCode;
    @ExcelProperty(value = "ITEM_CODE")
    private String itemCode;
    @ExcelProperty(value = "ORDER_STORE")
    private BigDecimal orderStore;
    @ExcelProperty(value = "SUMMARY_QTY_202001_202010")
    private BigDecimal summaryQty;
    @ExcelProperty(value = "SUMMARY_AMOUNT_含税_202001_202010")
    private BigDecimal summaryAmount;
    @ExcelProperty(value = "SUMMARY_AMOUNT_不含税_202001_202010")
    private BigDecimal summaryExcludeAmount;

    public BigDecimal getShopCode() {
        return shopCode;
    }

    public void setShopCode(BigDecimal shopCode) {
        this.shopCode = shopCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public BigDecimal getOrderStore() {
        return orderStore;
    }

    public void setOrderStore(BigDecimal orderStore) {
        this.orderStore = orderStore;
    }

    public BigDecimal getSummaryQty() {
        return summaryQty;
    }

    public void setSummaryQty(BigDecimal summaryQty) {
        this.summaryQty = summaryQty;
    }

    public BigDecimal getSummaryAmount() {
        return summaryAmount;
    }

    public void setSummaryAmount(BigDecimal summaryAmount) {
        this.summaryAmount = summaryAmount;
    }

    public BigDecimal getSummaryExcludeAmount() {
        return summaryExcludeAmount;
    }

    public void setSummaryExcludeAmount(BigDecimal summaryExcludeAmount) {
        this.summaryExcludeAmount = summaryExcludeAmount;
    }
}
