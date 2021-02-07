package com.sie.saaf.common.bean;

public class BatchMaterialGetRetEntity {
    String batch_no;
    String flex_values_desc;
    String line_type_desc;
    String item_code;
    String item_desc;
    String lot_number;
    String primary_quantity;
    String primary_uom_code;
    String wip_number;
    String supplier_lot_number;
    String subinventory_code;
    String transaction_date;

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getLine_type_desc() {
        return line_type_desc;
    }

    public void setLine_type_desc(String line_type_desc) {
        this.line_type_desc = line_type_desc;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getLot_number() {
        return lot_number;
    }

    public void setLot_number(String lot_number) {
        this.lot_number = lot_number;
    }

    public String getPrimary_quantity() {
        return primary_quantity;
    }

    public void setPrimary_quantity(String primary_quantity) {
        this.primary_quantity = primary_quantity;
    }

    public String getPrimary_uom_code() {
        return primary_uom_code;
    }

    public void setPrimary_uom_code(String primary_uom_code) {
        this.primary_uom_code = primary_uom_code;
    }

    public String getFlex_values_desc() {
        return flex_values_desc;
    }

    public void setFlex_values_desc(String flex_values_desc) {
        this.flex_values_desc = flex_values_desc;
    }

    public String getWip_number() {
        return wip_number;
    }

    public void setWip_number(String wip_number) {
        this.wip_number = wip_number;
    }

    public String getSupplier_lot_number() {
        return supplier_lot_number;
    }

    public void setSupplier_lot_number(String supplier_lot_number) {
        this.supplier_lot_number = supplier_lot_number;
    }

    public String getSubinventory_code() {
        return subinventory_code;
    }

    public void setSubinventory_code(String subinventory_code) {
        this.subinventory_code = subinventory_code;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }
}
