package com.sie.saaf.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class MsgCfgEntity_HI_RO {
    public static final String QUERY_SELECT_SQL ="SELECT\n" +
            "\tmc.msg_cfg_id AS msgCfgId,\n" +
            "\tmc.msg_cfg_name AS msgCfgName,\n" +
            "\tto_char(mc.org_id) AS orgId,\n" +
            "\tmc.channel_type AS channelType,\n" +
            "\tmc.msg_type_code AS msgTypeCode,\n" +
            "\tmc.msg_source_id AS msgSourceId,\n" +
            "\tmc.temple_id AS templeId,\n" +
            "\tmc.function_id AS functionId,\n" +
            "\tmc.is_timing_delivery AS isTimingDelivery,\n" +
            "\tmc.need_black_validate AS needBlackValidate,\n" +
            "\tmc.delivery_time AS deliveryTime,\n" +
            "\tmc.enabled_flag AS enabledFlag,\n" +
            "\tmc.blacklist_flag AS blacklistFlag,\n" +
            "\tmc.agent_id AS agentId,\n" +
            "\tmc.version_num AS versionNum,\n" +
            "\tmc.last_updated_by AS lastUpdatedBy,\n" +
            "\tmc.last_update_date AS lastUpdateDate,\n" +
            "\tmc.last_update_login AS lastUpdateLogin,\n" +
            "\tmc.creation_date AS creationDate,\n" +
            "\tmc.created_by AS createdBy,\n" +
            "\tmc.is_delete AS isDelete,\n" +
            "\tmc.compensate_flag AS compensateFlag,\n" +
          //  "\tmc.agent_id AS agentId,\n" +
            "\tbasechannel.CHANNEL_NAME as channelTypeName,\n" +
            "\tmtc.temple_name AS templeName,\n" +
            "\tmsc.source_name AS msgSourceName,\n" +
            "\tblv_org.meaning AS orgName,\n" +
            "\tblv_type.meaning AS msgTypeName,\n" +
            "\tblv_msg_flag.meaning AS enabledFlagName,\n" +
            "\tblv_black_flag.meaning AS blacklistFlagName,\n" +
            "\tblv_com_flag.meaning AS compensateFlagName\n" +
            "FROM\n" +
            "\tMSG_CFG  mc\n" +
            "LEFT JOIN base_channel  basechannel on mc.channel_type = basechannel.channel_code \n" +
            "LEFT JOIN msg_temple_cfg  mtc on mc.temple_id = mtc.temple_id\n" +
            "LEFT JOIN msg_source_cfg msc  on mc.msg_source_id = msc.msg_source_id \n" +
            "LEFT JOIN base_lookup_values blv_org on mc.org_id = blv_org.lookup_code and blv_org.lookup_type = 'BASE_OU' and blv_org.system_code = 'BASE' and blv_org.enabled_flag = 'Y' and blv_org.delete_flag ='0'\n" +
            "LEFT JOIN base_lookup_values blv_type on mc.msg_type_code = blv_type.lookup_code and blv_type.lookup_type = 'MESSAGE_TYPE' and blv_type.system_code = 'PUBLIC' and blv_type.enabled_flag = 'Y' and blv_type.delete_flag ='0'\n" +
            "LEFT JOIN base_lookup_values blv_msg_flag on mc.enabled_flag = blv_msg_flag.lookup_code and blv_msg_flag.lookup_type = 'MESSAGE_FLAG' and blv_msg_flag.system_code = 'BASE' and blv_msg_flag.enabled_flag = 'Y' and blv_msg_flag.delete_flag ='0'\n" +
            "LEFT JOIN base_lookup_values blv_black_flag on mc.blacklist_flag = blv_black_flag.lookup_code and blv_black_flag.lookup_type = 'MESSAGE_BLACKLIST_FLAG' and blv_black_flag.system_code = 'BASE' and blv_black_flag.enabled_flag = 'Y' and blv_black_flag.delete_flag ='0'\n" +
            "LEFT JOIN base_lookup_values blv_com_flag on mc.compensate_flag = blv_com_flag.lookup_code and blv_com_flag.lookup_type = 'MESSAGE_BLACKLIST_FLAG' and blv_com_flag.system_code = 'BASE' and blv_com_flag.enabled_flag = 'Y' and blv_com_flag.delete_flag ='0'\n" +
            "WHERE\n" +
            "\tmc.is_delete = '0'  ";

    private Integer msgCfgId; //消息配置ID
    private String msgCfgName; //消息配置名称
    private String orgId; //OU
    private String channelType; //渠道类型
    private String msgTypeCode; //消息类型CODE
    private Integer msgSourceId;
    private Integer templeId;
    private Integer functionId;
    private Integer isTimingDelivery; //定时发送频率,间隔多少分钟
    private String deliveryTime; //定时发送开始时间
    private String enabledFlag; //启用状态:0.已停用 1.启用
    private Integer versionNum;
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private String blacklistFlag; //是否过滤黑名单
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private String enabledFlagName;
    private String msgTypeName;
    private String agentId;//微信使用,agent_id
    private String compensateFlag;
    private String compensateFlagName;

    //渠道
    private String channelTypeName;
    //消息类型名
    private String templeName;

    private String blacklistFlagName; //是否过滤黑名单


    private String msgSourceName;

    private String needBlackValidate;
    private String orgName;

    public Integer getMsgCfgId() {
        return msgCfgId;
    }

    public void setMsgCfgId(Integer msgCfgId) {
        this.msgCfgId = msgCfgId;
    }

    public String getMsgCfgName() { return msgCfgName; }

    public void setMsgCfgName(String msgCfgName) { this.msgCfgName = msgCfgName; }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public Integer getMsgSourceId() {
        return msgSourceId;
    }

    public void setMsgSourceId(Integer msgSourceId) {
        this.msgSourceId = msgSourceId;
    }

    public Integer getTempleId() {
        return templeId;
    }

    public void setTempleId(Integer templeId) {
        this.templeId = templeId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getIsTimingDelivery() {
        return isTimingDelivery;
    }

    public void setIsTimingDelivery(Integer isTimingDelivery) {
        this.isTimingDelivery = isTimingDelivery;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
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

    public String getBlacklistFlag() {
        return blacklistFlag;
    }

    public void setBlacklistFlag(String blacklistFlag) {
        this.blacklistFlag = blacklistFlag;
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

    public String getEnabledFlagName() {
        return enabledFlagName;
    }

    public void setEnabledFlagName(String enabledFlagName) {
        this.enabledFlagName = enabledFlagName;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }

    public String getMsgSourceName() {
        return msgSourceName;
    }

    public void setMsgSourceName(String msgSourceName) {
        this.msgSourceName = msgSourceName;
    }

    public String getNeedBlackValidate() {
        return needBlackValidate;
    }

    public void setNeedBlackValidate(String needBlackValidate) {
        this.needBlackValidate = needBlackValidate;
    }

    public String getBlacklistFlagName() {
        return blacklistFlagName;
    }

    public void setBlacklistFlagName(String blacklistFlagName) {
        this.blacklistFlagName = blacklistFlagName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCompensateFlag() {
        return compensateFlag;
    }

    public void setCompensateFlag(String compensateFlag) {
        this.compensateFlag = compensateFlag;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCompensateFlagName() {
        return compensateFlagName;
    }

    public void setCompensateFlagName(String compensateFlagName) {
        this.compensateFlagName = compensateFlagName;
    }
}
