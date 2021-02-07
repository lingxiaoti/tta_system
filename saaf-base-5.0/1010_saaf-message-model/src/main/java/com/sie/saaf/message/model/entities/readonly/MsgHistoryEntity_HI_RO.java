package com.sie.saaf.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class MsgHistoryEntity_HI_RO {

    public static final String QUERY_SELECT_SQL="SELECT\n" +
            "  msg_history.msg_history_id AS msgHistoryId,\n" +
            "  msg_history.org_id AS orgId,\n" +
            "  msg_history.job_id AS jobId,\n" +
            "  msg_history.channel_type AS channelType,\n" +
            "  msg_history.msg_type_code AS msgTypeCode,\n" +
            "  msg_history.msg_cfg_id AS msgCfgId,\n" +
            "  msg_history.receive_id AS receiveId,\n" +
            "  msg_history.receive_code AS receiveCode,\n" +
            "  msg_history.receive_name AS receiveName,\n" +
            "  msg_history.source_type AS sourceType,\n" +
            "  msg_history.biz_type AS bizType,\n" +
            "  msg_history.biz_id AS bizId,\n" +
            "  msg_history.msg_transaction_date AS msgTransactionDate,\n" +
            "  msg_history.msg_template_id AS msgTemplateId,\n" +
            "  msg_history.value_list AS valueList,\n" +
            "  msg_history.msg_subject AS msgSubject,\n" +
            "  msg_history.msg_content AS msgContent,\n" +
            "  msg_history.msg_priority AS msgPriority,\n" +
            "  msg_history.send_date AS sendDate,\n" +
            "  msg_history.send_status AS sendStatus,\n" +
            "  msg_history.failure_times AS failureTimes,\n" +
            "  msg_history.return_msg AS returnMsg,\n" +
            "  msg_history.synchro AS synchro,\n" +
            "  msg_history.version_num AS versionNum,\n" +
            "  msg_history.last_update_login AS lastUpdateLogin,\n" +
            "  msg_history.last_update_date AS lastUpdateDate,\n" +
            "  msg_history.last_updated_by AS lastUpdatedBy,\n" +
            "  msg_history.creation_date AS creationDate, \n" +
            "  msg_history.created_by AS createdBy,\n" +
            "  msg_history.request_id AS requestId,\n" +
            "  msg_history.msg_url AS msgUrl,\n" +
            "  msg_history.is_delete AS isDelete,\n" +
            "\tmsg_temple_cfg.temple_name AS msgTemplateName,\n" +
            "\tbase_channel.CHANNEL_NAME AS channelTypeName,\n" +
            "\tblv_org.meaning AS orgName,\n" +
            "\tblv_type.meaning AS msgTypeName,\n" +
            "\tblv_send_status.meaning AS sendStatusName,\n" +
            "\tCASE msg_history.synchro WHEN 'Y' THEN '同步' else '异步' end AS synchroName\n" +
            "FROM\n" +
            "\tmsg_history msg_history\n" +
            "LEFT JOIN msg_temple_cfg msg_temple_cfg ON msg_history.msg_template_id = msg_temple_cfg.temple_id\n" +
            "LEFT JOIN base_channel base_channel ON msg_history.channel_type = base_channel.CHANNEL_CODE\n" +
            "LEFT JOIN base_lookup_values blv_org on msg_history.org_id = blv_org.lookup_code and blv_org.lookup_type = 'BASE_OU' and blv_org.system_code ='BASE' and blv_org.enabled_flag = 'Y' and blv_org.delete_flag ='0' \n" +
            "LEFT JOIN base_lookup_values blv_type on msg_history.msg_type_code = blv_type.lookup_code and blv_type.lookup_type = 'MESSAGE_TYPE' and blv_type.system_code ='PUBLIC' and blv_type.enabled_flag = 'Y' and blv_type.delete_flag ='0'\n" +
            "LEFT JOIN base_lookup_values blv_send_status on msg_history.send_status = blv_send_status.lookup_code and blv_send_status.lookup_type = 'MESSAGE_SEND_STATUS' and blv_send_status.system_code ='BASE' and blv_send_status.enabled_flag = 'Y' and blv_send_status.delete_flag ='0'\n" +
            "where msg_history.is_delete = '0'  ";
    private Integer msgHistoryId; //消息记录ID
    private Integer orgId; //OU
    private String jobId;
    private String channelType; //渠道类型
    private String msgTypeCode; //消息类型CODE
    private Integer msgCfgId;
    private String receiveId;
    private String receiveCode; //接收对象
    private String receiveName;
    private String sourceType; //记录来源于哪个系统
    private String bizType; //请求源功能ID
    private String bizId; //请求源记录的主键ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date msgTransactionDate; //消息请求时间
    private String msgTemplateId;
    private String valueList;
    private String msgSubject; //消息标题
    private String msgContent; //消息内容
    private Integer msgPriority;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sendDate; //发送时间
    private String sendStatus; //发送状态：1.待发送，2.已发送成功   3.发送异常
    private Integer failureTimes;
    private String returnMsg; //返回消息
    private String synchro; //Y  同步 N  异步
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer lastUpdateLogin;
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private String requestId;
    private String msgUrl;//消息链接

    private String msgTemplateName;
    private String channelTypeName;
    private String msgTypeName;
    private String synchroName;
    private String orgName;
    private String sendStatusName;

    public String getMsgTemplateName() {
        return msgTemplateName;
    }

    public void setMsgTemplateName(String msgTemplateName) {
        this.msgTemplateName = msgTemplateName;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public String getSynchroName() {
        return synchroName;
    }

    public void setSynchroName(String synchroName) {
        this.synchroName = synchroName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSendStatusName() {
        return sendStatusName;
    }

    public void setSendStatusName(String sendStatusName) {
        this.sendStatusName = sendStatusName;
    }



    public Integer getMsgHistoryId() {
        return msgHistoryId;
    }

    public void setMsgHistoryId(Integer msgHistoryId) {
        this.msgHistoryId = msgHistoryId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getMsgTypeCode() {
        return msgTypeCode;
    }

    public void setMsgTypeCode(String msgTypeCode) {
        this.msgTypeCode = msgTypeCode;
    }

    public Integer getMsgCfgId() {
        return msgCfgId;
    }

    public void setMsgCfgId(Integer msgCfgId) {
        this.msgCfgId = msgCfgId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Date getMsgTransactionDate() {
        return msgTransactionDate;
    }

    public void setMsgTransactionDate(Date msgTransactionDate) {
        this.msgTransactionDate = msgTransactionDate;
    }

    public String getMsgTemplateId() {
        return msgTemplateId;
    }

    public void setMsgTemplateId(String msgTemplateId) {
        this.msgTemplateId = msgTemplateId;
    }

    public String getValueList() {
        return valueList;
    }

    public void setValueList(String valueList) {
        this.valueList = valueList;
    }

    public String getMsgSubject() {
        return msgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getMsgPriority() {
        return msgPriority;
    }

    public void setMsgPriority(Integer msgPriority) {
        this.msgPriority = msgPriority;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getFailureTimes() {
        return failureTimes;
    }

    public void setFailureTimes(Integer failureTimes) {
        this.failureTimes = failureTimes;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getSynchro() {
        return synchro;
    }

    public void setSynchro(String synchro) {
        this.synchro = synchro;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }
}
