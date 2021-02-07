package com.sie.saaf.base.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.util.HtmlUtils;

import java.util.Date;

/**
 * BaseMessageInstationEntity_HI_RO Entity Object
 * Tue Jul 10 16:14:54 CST 2018  Auto Generate
 */

public class BaseMessageInstationEntity_HI_RO {
    public static final String QUERY_MESSAGE_INFO_SQL = "SELECT bmi.ins_mess_id insMessId\n" +
            "\t\t\t,bmi.con_mess_id conMessId\n" +
            "\t\t\t,bmi.mess_title messTitle\n" +
            "\t\t\t,bmi.mess_sender messSender\n" +
            "\t\t\t,bmi.mess_from_system messFromSystem\n" +
            "\t\t\t,bmi.mess_from_module messFromModule\n" +
            "\t\t\t,bmc.con_mess_content messContent\n" +
            "\t\t\t,bmc.con_mess_content introduction\n" +
            "\t\t\t,bmi.creation_date creationDate\n" +
            "\t\t\t,bmi.mess_status messStatus\n" +
            "\t\t\t,if(bmi.mess_status = 0, '未读', '已读') messStatusName\n" +
            "\t\t\t,bmc.con_mess_type messType\n" +
            "\t\t\t,bmi.mess_receiver_id messReceiverId\n" +
            "\t\t\t,bmi.mess_receiver messReceiver\n" +
            "\t\t\t,person.person_name messSenderName\n" +
            "\t\t\t,bmi.source_id sourceId\n" +
            "FROM\n" +
            "\tbase_message_instation bmi\n" +
            "\tLEFT JOIN base_message_content bmc ON bmi.con_mess_id = bmc.con_mess_id\n" +
            "\tLEFT JOIN base_users users on bmi.mess_sender_id = users.user_id\n" +
            "\tLEFT JOIN base_person person on person.person_id = users.person_id\n" +
            " WHERE 1 = 1\n" +
            "\t AND bmi.delete_flag = 0\n" +
            "\t AND bmi.mess_receiver_id = :messReceiverId\n";

    public static final String QUERY_MESSAGE_DETAIL_SQL = "SELECT\n" +
            "\tbmi.ins_mess_id insMessId,\n" +
            "\tbmi.con_mess_id conMessId,\n" +
            "\tbmi.mess_title messTitle,\n" +
            "\tbmi.mess_sender messSender,\n" +
            "\tbmi.creation_date creationDate,\n" +
            "\tbmi.mess_status messStatus,\n" +
            "  if(bmi.mess_status = 0, '未读', '已读') messStatusName,\n" +
            "\tbmc.con_mess_type messType,\n" +
            "\tbmc.con_mess_content messContent,\n" +
            "\tbmi.mess_receiver_id messReceiverId,\n" +
            "\tbmi.mess_receiver messReceiver,\n" +
            "\tperson.person_name messSenderName,\n" +
            "\tbmi.source_id sourceId\n" +
            "FROM\n" +
            "\tbase_message_instation bmi\n" +
            "\tLEFT JOIN base_message_content bmc ON bmi.con_mess_id = bmc.con_mess_id\n" +
            "\tLEFT JOIN base_users users on bmi.mess_sender_id = users.user_id\n" +
            "\tLEFT JOIN base_person person on person.person_id = users.person_id\n" +
            "WHERE\n" +
            "\t1 = 1" +
            "\t AND bmi.delete_flag = 0\n" +
            "\t AND bmi.ins_mess_id = :insMessId";

    public static final String QUERY_USER_INFO_SQL = "select users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,users.user_full_name userFullName\n" +
            "  from base_users users\n" +
            " where 1 = 1\n";

    private Integer userId; //用户ID
    private String userName; //用户账号名称
    private String userFullName; //用户名称

    private Integer insMessId; //主键Id
    private Integer conMessId; //消息内容ID
    private String messTitle; //消息标题
    private String messContent; //消息内容
    private String messType;//消息类型
    private String messStatus; //消息状态 已读/未读
    private String messStatusName;
    private String messFromSystem; //消息的来源系统
    private String messFromModule; //消息的来源模块
    private Integer messReceiverId; //消息接收人ID
    private String messReceiver; //接收人
    private Integer messSenderId; //消息接收人员ID
    private String messSender; //发送人
    private String sourceId; //来源ID，当前存放图片ID
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;
    private String introduction;

    private String messSenderName;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessSenderName() {
        return messSenderName;
    }

    public void setMessSenderName(String messSenderName) {
        this.messSenderName = messSenderName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Integer getInsMessId() {
        return insMessId;
    }

    public void setInsMessId(Integer insMessId) {
        this.insMessId = insMessId;
    }

    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    public String getMessTitle() {
        return messTitle;
    }

    public void setMessTitle(String messTitle) {
        this.messTitle = messTitle;
    }

    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }

    public String getMessType() {
        return messType;
    }

    public void setMessType(String messType) {
        this.messType = messType;
    }

    public String getMessStatus() {
        return messStatus;
    }

    public void setMessStatus(String messStatus) {
        this.messStatus = messStatus;
    }

    public String getMessStatusName() {
        return messStatusName;
    }

    public void setMessStatusName(String messStatusName) {
        this.messStatusName = messStatusName;
    }

    public String getMessFromSystem() {
        return messFromSystem;
    }

    public void setMessFromSystem(String messFromSystem) {
        this.messFromSystem = messFromSystem;
    }

    public String getMessFromModule() {
        return messFromModule;
    }

    public void setMessFromModule(String messFromModule) {
        this.messFromModule = messFromModule;
    }

    public Integer getMessReceiverId() {
        return messReceiverId;
    }

    public void setMessReceiverId(Integer messReceiverId) {
        this.messReceiverId = messReceiverId;
    }

    public String getMessReceiver() {
        return messReceiver;
    }

    public void setMessReceiver(String messReceiver) {
        this.messReceiver = messReceiver;
    }

    public Integer getMessSenderId() {
        return messSenderId;
    }

    public void setMessSenderId(Integer messSenderId) {
        this.messSenderId = messSenderId;
    }

    public String getMessSender() {
        return messSender;
    }

    public void setMessSender(String messSender) {
        this.messSender = messSender;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    /**
     * 简介，就是内容去掉html标记
     * @return
     */
    public String getIntroduction() {
        return HtmlUtils.delHTMLTag(introduction);
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
