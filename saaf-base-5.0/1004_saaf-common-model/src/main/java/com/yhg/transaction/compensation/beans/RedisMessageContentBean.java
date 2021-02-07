package com.yhg.transaction.compensation.beans;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class RedisMessageContentBean {
    private Long messageIndex;
    private String messageBody;
    private String requestURL;
    private String queueName;
    /**
     * 0:消息预发送状态
     * 1: 消息已发送,自动补偿
     * 2: 消息已发送,不自动补偿
     * 3: 不自动补偿时，消费者消费失败
     */
    private int status;
    //状态中文说明
    private String statusName;
    private int sendQueueTimes;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    
    public RedisMessageContentBean(){
        super();    
    }
    
    public RedisMessageContentBean(String queueName) {
        this.queueName = queueName;
    }

    public RedisMessageContentBean(String messageBody, String queueName) {
        this.messageBody = messageBody;
        this.queueName = queueName;
    }

    public void setMessageIndex(Long messageIndex) {
        this.messageIndex = messageIndex;
    }

    public Long getMessageIndex() {
        return messageIndex;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getSendQueueTimes() {
        return sendQueueTimes;
    }

    public void setSendQueueTimes(int sendQueueTimes) {
        this.sendQueueTimes = sendQueueTimes;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
