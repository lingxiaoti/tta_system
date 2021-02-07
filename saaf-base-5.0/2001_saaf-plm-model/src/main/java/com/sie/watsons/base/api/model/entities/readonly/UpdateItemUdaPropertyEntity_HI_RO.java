package com.sie.watsons.base.api.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class UpdateItemUdaPropertyEntity_HI_RO {

    private String request_id;
    private String update_type;
    private String uda_type;
    private Integer uda_id;
    private String uda_value;
    private String item;
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

    public String getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(String update_type) {
        this.update_type = update_type;
    }

    public String getUda_type() {
        return uda_type;
    }

    public void setUda_type(String uda_type) {
        this.uda_type = uda_type;
    }

    public Integer getUda_id() {
        return uda_id;
    }

    public void setUda_id(Integer uda_id) {
        this.uda_id = uda_id;
    }

    public String getUda_value() {
        return uda_value;
    }

    public void setUda_value(String uda_value) {
        this.uda_value = uda_value;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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
