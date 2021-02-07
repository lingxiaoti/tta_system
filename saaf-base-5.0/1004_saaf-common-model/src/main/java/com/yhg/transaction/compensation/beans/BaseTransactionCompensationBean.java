package com.yhg.transaction.compensation.beans;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class BaseTransactionCompensationBean {
    private String queueName;
    private Long messageIdIndex;
    private String messageBody;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date messageCreateDate;
    private String messageType;
    private String serviceType;
    private String serviceUrl;
    private int status;

    public BaseTransactionCompensationBean() {
        super();
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setMessageIdIndex(Long messageIdIndex) {
        this.messageIdIndex = messageIdIndex;
    }

    public Long getMessageIdIndex() {
        return messageIdIndex;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageCreateDate(Date messageCreateDate) {
        this.messageCreateDate = messageCreateDate;
    }

    public Date getMessageCreateDate() {
        return messageCreateDate;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
