package com.sie.wastons.ttadata.model.entities.readyonly;

import java.math.BigDecimal;

public class VmiSalesInSearchEntity_HI_RO {

    private String store;
    private String item;
    private BigDecimal sumSalesQty;
    private BigDecimal sumSalesAmt;
    private BigDecimal sumSalesExcludeAmt;


    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getSumSalesQty() {
        return sumSalesQty;
    }

    public void setSumSalesQty(BigDecimal sumSalesQty) {
        this.sumSalesQty = sumSalesQty;
    }

    public BigDecimal getSumSalesAmt() {
        return sumSalesAmt;
    }

    public void setSumSalesAmt(BigDecimal sumSalesAmt) {
        this.sumSalesAmt = sumSalesAmt;
    }

    public BigDecimal getSumSalesExcludeAmt() {
        return sumSalesExcludeAmt;
    }

    public void setSumSalesExcludeAmt(BigDecimal sumSalesExcludeAmt) {
        this.sumSalesExcludeAmt = sumSalesExcludeAmt;
    }
}
