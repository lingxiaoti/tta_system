package com.sie.watsons.base.api.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.yhg.hibernate.core.DataObjectDescriptor;

import java.util.Date;


public class UpdateItemCostPropertyEntity_HI_RO {

    private String request_id;
    private String change_desc;
    private String supplier;
    private String item;
    private String item_type;
    private String change_type;
    private String value;
    private String reason;
    private String effective_date;
    private String process_ind;
    private String create_datetime;
    private String process_datetime;
    private String message;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getChange_desc() {
        return change_desc;
    }

    public void setChange_desc(String change_desc) {
        this.change_desc = change_desc;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getChange_type() {
        return change_type;
    }

    public void setChange_type(String change_type) {
        this.change_type = change_type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }

    public String getProcess_ind() {
        return process_ind;
    }

    public void setProcess_ind(String process_ind) {
        this.process_ind = process_ind;
    }

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getProcess_datetime() {
        return process_datetime;
    }

    public void setProcess_datetime(String process_datetime) {
        this.process_datetime = process_datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
