package com.sie.saaf.common.bean;

public class BatchMaterialBalanceEntity {
    String batch_no;
    String line_type_desc;
    String item_code;
    String item_desc;
    String actual_qty;
    String dtl_um;

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

    public String getActual_qty() {
        return actual_qty;
    }

    public void setActual_qty(String actual_qty) {
        this.actual_qty = actual_qty;
    }

    public String getDtl_um() {
        return dtl_um;
    }

    public void setDtl_um(String dtl_um) {
        this.dtl_um = dtl_um;
    }
}
