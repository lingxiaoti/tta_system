package com.sie.saaf.base.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * BaseMessageContentEntity_HI_RO Entity Object
 * Tue Jul 10 16:14:42 CST 2018  Auto Generate
 */

public class BaseMessageContentEntity_HI_RO {
    //站内消息列表
    public static final String QUERY_CON_MESS_LIST = "SELECT bmc.con_mess_id conMessId\n" +
            "\t\t\t,bmc.con_mess_title conMessTitle\n" +
            "\t\t\t,bmc.con_mess_content conMessContent\n" +
            "\t\t\t,bmc.con_mess_type conMessType\n" +
            "\t\t\t,blv1.meaning conMessTypeName\n" +
            "\t\t\t,bmc.con_mess_state conMessState\n" +
            "\t\t\t,blv2.meaning conMessStateName\n" +
            "\t\t\t,bmc.con_mess_system conMessSystem\n" +
            "\t\t\t,bmc.con_send_date conSendDate\n" +
            "\t\t\t,bmc.created_by createdBy\n" +
            "\t\t\t,bmc.creation_date creationDate\n" +
            "  FROM base_message_content bmc\n" +
            "\t\t\t,base_lookup_values blv1\n" +
            "\t\t\t,base_lookup_values blv2\n" +
            " WHERE 1 = 1\n" +
            "\t AND bmc.con_mess_type = blv1.lookup_code\n" +
            "\t AND blv1.lookup_type = 'CON_MESS_TYPE'\n" +
            "\t AND bmc.con_mess_state = blv2.lookup_code\n" +
            "\t AND blv2.lookup_type = 'CON_MESS_STATE'\n";

    public static final String QUERY_IS_OT_NOT_CONSULTED_SQL = "SELECT bmi.mess_status messStatus\n" +
            "\t\t\t,COUNT(bmi.mess_status) messStatusNum\n" +
            "  FROM base_message_instation bmi\n" +
            " WHERE bmi.con_mess_id = :conMessId\n" +
            "\t AND bmi.delete_flag = 0\n" +
            "GROUP BY bmi.mess_status";

    //获取待发送消息分组
    public static final String QUERY_WAITING_SEND_MESS_GROUP_SQL = "SELECT messagePerson.con_mess_id conMessId\n" +
            "\t\t\t,messagePerson.bu_mess_id buMessId\n" +
            "\t\t\t,messagePerson.dep_mess_id depMessId\n" +
            "\t\t\t,messagePerson.dept_id deptId\n" +
            "\tFROM base_message_person messagePerson\n" +
            " WHERE 1 = 1\n" +
            "\t AND messagePerson.con_mess_id = :conMessId\n" +
            "\t AND messagePerson.delete_flag = 0\n" +
            "GROUP BY messagePerson.con_mess_id\n" +
            "\t\t\t\t,messagePerson.bu_mess_id\n" +
            "\t\t\t\t,messagePerson.bu_mess_id\n" +
            "\t\t\t\t,messagePerson.dept_id\n" +
            "ORDER BY messagePerson.bu_mess_id\n" +
            "\t\t\t\t,messagePerson.dept_id";

    //获取待发送的消息和人（根据分组）
    public static final String QUERY_MESS_WAITING_TO_SEND = "SELECT messContent.con_mess_id conMessId\n" +
            "\t\t\t,messContent.con_mess_title conMessTitle\n" +
            "\t\t\t,messContent.con_mess_content conMessContent\n" +
            "\t\t\t,messContent.con_mess_system conMessSystem\n" +
            "\t\t\t,messPerson.user_id messReceiverId\n" +
            "\t\t\t,nvl(users.user_full_name,users.user_name) messReceiver\n" +
            "  FROM base_message_content messContent\n" +
            "\t\t\t,base_message_person messPerson\n" +
            "\t\t\t,base_users users\n" +
            " WHERE 1 = 1\n" +
            "\t AND messContent.con_mess_id = :conMessId\n" +
            "\t AND messContent.con_mess_id = messPerson.con_mess_id\n" +
            "\t AND messPerson.bu_mess_id = :buMessId\n" +
            "\t AND messPerson.dep_mess_id = :depMessId\n" +
            "\t AND messPerson.dept_id = :deptId\n" +
            "\t AND messPerson.user_id = users.user_id";

    private Integer conMessId; //主键Id
    private String conMessTitle; //消息标题
    private String conMessType; //消息类型(COMMON/URL)
    private String conMessTypeName;//消息类型中午名称
    private Integer conMessState; //消息状态 0:未发送给任何人；1:已发送；2:撤回
    private String conMessStateName;
    private String conMessSystem; //消息的接收系统
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date conSendDate; //消息的发送时间
    private String conMessContent; //消息内容
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

    private Integer messStatus;//私信状态
    private Integer messStatusNum;//已读未读数量
    private Integer notConsulted;//未读数量
    private Integer beenConsulted;//已读数量

    private Integer buMessId;
    private Integer depMessId;
    private Integer deptId;
    private Integer messReceiverId; //消息接收人ID
    private String messReceiver; //接收人

    private List<BaseMessageBuEntity_HI_RO> messageBuData;

    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    public String getConMessTitle() {
        return conMessTitle;
    }

    public void setConMessTitle(String conMessTitle) {
        this.conMessTitle = conMessTitle;
    }

    public String getConMessType() {
        return conMessType;
    }

    public void setConMessType(String conMessType) {
        this.conMessType = conMessType;
    }

    public String getConMessTypeName() {
        return conMessTypeName;
    }

    public void setConMessTypeName(String conMessTypeName) {
        this.conMessTypeName = conMessTypeName;
    }

    public String getConMessStateName() {
        return conMessStateName;
    }

    public void setConMessStateName(String conMessStateName) {
        this.conMessStateName = conMessStateName;
    }

    public Integer getConMessState() {
        return conMessState;
    }

    public void setConMessState(Integer conMessState) {
        this.conMessState = conMessState;
    }

    public String getConMessSystem() {
        return conMessSystem;
    }

    public void setConMessSystem(String conMessSystem) {
        this.conMessSystem = conMessSystem;
    }

    public Date getConSendDate() {
        return conSendDate;
    }

    public void setConSendDate(Date conSendDate) {
        this.conSendDate = conSendDate;
    }

    public String getConMessContent() {
        return conMessContent;
    }

    public void setConMessContent(String conMessContent) {
        this.conMessContent = conMessContent;
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

    public Integer getMessStatus() {
        return messStatus;
    }

    public void setMessStatus(Integer messStatus) {
        this.messStatus = messStatus;
    }

    public Integer getMessStatusNum() {
        return messStatusNum;
    }

    public void setMessStatusNum(Integer messStatusNum) {
        this.messStatusNum = messStatusNum;
    }

    public Integer getNotConsulted() {
        return notConsulted;
    }

    public void setNotConsulted(Integer notConsulted) {
        this.notConsulted = notConsulted;
    }

    public Integer getBeenConsulted() {
        return beenConsulted;
    }

    public void setBeenConsulted(Integer beenConsulted) {
        this.beenConsulted = beenConsulted;
    }

    public Integer getBuMessId() {
        return buMessId;
    }

    public void setBuMessId(Integer buMessId) {
        this.buMessId = buMessId;
    }

    public Integer getDepMessId() {
        return depMessId;
    }

    public void setDepMessId(Integer depMessId) {
        this.depMessId = depMessId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    public List<BaseMessageBuEntity_HI_RO> getMessageBuData() {
        return messageBuData;
    }

    public void setMessageBuData(List<BaseMessageBuEntity_HI_RO> messageBuData) {
        this.messageBuData = messageBuData;
    }
}
