package com.sie.saaf.common.bean;

/**
 * @auther: huqitao 2018/10/30
 */
public class CustomerInfoBean {
    private Integer customerId; //客户ID
    private String customerName; //客户名称
    private String customerNumber; //客户编号
    private Integer custUserId; //经销商用户ID
    private Integer pdaUserId; //PDA用户ID

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Integer getCustUserId() {
        return custUserId;
    }

    public void setCustUserId(Integer custUserId) {
        this.custUserId = custUserId;
    }

    public Integer getPdaUserId() {
        return pdaUserId;
    }

    public void setPdaUserId(Integer pdaUserId) {
        this.pdaUserId = pdaUserId;
    }
}
