package com.sie.saaf.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.Date;

public class MsgTempleCfgEntity_HI_RO {
    public static final String QUERY_SELECT_SQL = " SELECT\n" +
            "\tmtc.temple_id AS templeId,\n" +
            "\tmtc.temple_name AS templeName,\n" +
            "\tmtc.temple_content AS templeContent,\n" +
            "\tmtc.is_html AS isHtml,\n" +
            "\tmtc.msg_type AS msgType,\n" +
            "\tto_char(mtc.org_id) AS orgId,\n" +
            "\tmtc.channel AS channel,\n" +
            "\tmtc.business AS business,\n" +
            "\tmtc.version_num AS versionNum,\n" +
            "\tmtc.last_updated_by AS lastUpdatedBy,\n" +
            "\tmtc.last_update_date AS lastUpdateDate,\n" +
            "\tmtc.last_update_login AS lastUpdateLogin,\n" +
            "\tmtc.creation_date AS creationDate,\n" +
            "\tmtc.msg_url AS msgUrl,\n" +
            "\tmtc.created_by AS createdBy,\n" +
            "\tmtc.temple_subject AS templeSubject,\n" +
            "\tmtc.is_delete AS isDelete,\n" +
            "\tbasechannel.CHANNEL_NAME as channelName,\n" +
            "\tblv_org.meaning AS orgName,\n" +
            "\tblv_type.meaning AS msgTypeName,\n" +
            "\tblv_html.meaning AS isHtmlName\n" +
            "FROM\n" +
            "\tMSG_TEMPLE_CFG  mtc\n" +
            "LEFT JOIN base_channel  basechannel ON mtc.channel = basechannel.channel_code \n" +
            "LEFT JOIN base_lookup_values blv_org on mtc.org_id = blv_org.lookup_code and blv_org.lookup_type = 'BASE_OU' and blv_org.system_code = 'BASE' and blv_org.enabled_flag = 'Y' and blv_org.delete_flag = '0'\n" +
            "LEFT JOIN base_lookup_values blv_type on mtc.msg_type = blv_type.lookup_code and blv_type.lookup_type = 'MESSAGE_TYPE' and blv_type.system_code = 'PUBLIC' and blv_type.enabled_flag = 'Y' and blv_type.delete_flag = '0'\n" +
            "LEFT JOIN base_lookup_values blv_html on mtc.is_html = blv_html.lookup_code and blv_html.lookup_type = 'IS_HTML' and blv_html.system_code = 'BASE' and blv_html.enabled_flag = 'Y' and blv_html.delete_flag = '0'\n" +
            "\n" +
            "WHERE\n" +
            "\tmtc.is_delete = '0' ";
    public static final String QUERY_TEMPLENAME_EXIST_SQL="SELECT mtc.temple_name templeName from MSG_TEMPLE_CFG mtc  where mtc.is_delete = '0' ";

    private Integer templeId; //消息配置ID
    private String templeName;
    private String templeSubject;//主题
    private String templeContent; //内容
    private Integer isHtml;
    private String msgType;
    private String orgId;//OU
    private String channel;
    private String business;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    private Integer lastUpdateLogin;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
    private String msgTypeName;
    private String msgUrl;//消息链接

    //渠道
    private String channelName;

    //OU
    private String orgName;
    private String isHtmlName;

    public Integer getTempleId() {
        return templeId;
    }

    public void setTempleId(Integer templeId) {
        this.templeId = templeId;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }

    public String getTempleSubject() {
        return templeSubject;
    }

    public void setTempleSubject(String templeSubject) {
        this.templeSubject = templeSubject;
    }

    public String getTempleContent() {


        return templeContent;
    }

    public void setTempleContent(Clob templeContent) throws Exception{
        StringBuffer buffer = new StringBuffer();
        if (templeContent != null) {
            BufferedReader br = null;
            try {
                Reader r = templeContent.getCharacterStream();  //将Clob转化成流
                br = new BufferedReader(r);
                String s = null;
                while ((s = br.readLine()) != null) {
                    buffer.append(s);
                }
            } catch (Exception e) {
                e.printStackTrace();	//打印异常信息
            } finally {
                if (br != null) {
                    br.close(); //关闭流
                }
            }
        }
        this.templeContent = buffer.toString();
    }

    public Integer getIsHtml() {
        return isHtml;
    }

    public void setIsHtml(Integer isHtml) {
        this.isHtml = isHtml;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }

    public String getIsHtmlName() { return isHtmlName; }

    public void setIsHtmlName(String isHtmlName) { this.isHtmlName = isHtmlName; }
}
