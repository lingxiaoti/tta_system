package com.sie.saaf.common.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @auther: huqitao 2018/7/26
 */
public class MessageTransactionManualEntity_HI_RO {
    public static final String QUERY_BOTH_EXIST_TRANSACTION_ADN_MANUAL_SQL = "SELECT bms.manual_id manualId\n" +
            "\t\t\t,bctc.confirmId transConfirmId\n" +
            "\t\t\t,bms.message_index messageIndex\n" +
            "\tFROM base_manual_supplement bms\n" +
            "\t\t\t,base_common_txn_confirm bctc\n" +
            " WHERE bms.message_index = bctc.messageIndex";

    public static final String QUERY_EXIST_MANUAL_NOT_EXIST_TRANSACTION_SQL = "SELECT bms.manual_id manualId\n" +
            "\t\t\t,bms.message_index messageIndex\n" +
            "\t\t\t,bms.message_body messageBody\n" +
            "\t\t\t,bms.request_URL requestUrl\n" +
            "\t\t\t,bms.queue_name queueName\n" +
            "\t\t\t,bms.statusstatus\n" +
            "\t\t\t,bms.send_queue_times sendQueueTimes\n" +
            "\t\t\t,bms.message_content_bean messageContentBean\n" +
            "\tFROM base_manual_supplement bms\n" +
            " WHERE 1 = 1\n" +
            "\t AND NOT EXISTS (SELECT 1 FROM base_common_txn_confirm bctc where bctc.messageIndex = bms.message_index)";

    public static final String QUERY_BOTH_EXIST_MESS_CONFIRM_ADN_TRANSACTION_SQL = "SELECT bcmc.confirmId messageConfirmId\n" +
            "\t\t\t,bctc.confirmId transConfirmId\n" +
            "\t\t\t,bcmc.messageIndex messageIndex\n" +
            "\tFROM base_common_message_confirm bcmc\n" +
            "\t\t\t,base_common_txn_confirm bctc\n" +
            " WHERE bcmc.messageIndex = bctc.messageIndex\n";

    public static final String QUERY_EXIST_MESS_CONFIRM_NOT_EXIST_TRANSACTION_SQL = "SELECT bcmc.messageIndex messageIndex\n" +
            "\tFROM base_common_message_confirm bcmc\n" +
            " WHERE 1 = 1\n" +
            "\t AND NOT EXISTS (SELECT 1 FROM base_common_txn_confirm bctc where bctc.messageIndex = bctc.messageIndex)\n";

    public static final String QUERY_EXIST_TRANSACTION_SQL = "SELECT bctc.confirmId transConfirmId\n" +
            "\t\t\t,bctc.messageIndex messageIndex\n" +
            "\tFROM base_common_txn_confirm bctc\n" +
            " WHERE 1 = 1\n";

    private Integer messageConfirmId; //消息确认表唯一键
    private Integer transConfirmId;//消费确认表唯一键
    private Integer manualId;//补偿表确认表唯一键
    private Integer messageIndex; //消息体序号
    private byte[] messageBody; //消息体内容
    private String requestUrl; //请求地址
    private String queueName; //队列名
    private String status; //状态
    private Integer sendQueueTimes; //发送到mq 的次数
    private byte[] messageContentBean; //在mq中的完整的消息内容
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期

    public Integer getMessageConfirmId() {
        return messageConfirmId;
    }

    public void setMessageConfirmId(Integer messageConfirmId) {
        this.messageConfirmId = messageConfirmId;
    }

    public Integer getTransConfirmId() {
        return transConfirmId;
    }

    public void setTransConfirmId(Integer transConfirmId) {
        this.transConfirmId = transConfirmId;
    }

    public Integer getManualId() {
        return manualId;
    }

    public void setManualId(Integer manualId) {
        this.manualId = manualId;
    }

    public Integer getMessageIndex() {
        return messageIndex;
    }

    public void setMessageIndex(Integer messageIndex) {
        this.messageIndex = messageIndex;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSendQueueTimes() {
        return sendQueueTimes;
    }

    public void setSendQueueTimes(Integer sendQueueTimes) {
        this.sendQueueTimes = sendQueueTimes;
    }

    public byte[] getMessageContentBean() {
        return messageContentBean;
    }

    public void setMessageContentBean(byte[] messageContentBean) {
        this.messageContentBean = messageContentBean;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
