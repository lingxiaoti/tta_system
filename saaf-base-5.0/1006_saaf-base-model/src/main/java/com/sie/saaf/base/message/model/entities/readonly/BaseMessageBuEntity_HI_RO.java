package com.sie.saaf.base.message.model.entities.readonly;

/**
 * BaseMessageBuEntity_HI_RO Entity Object
 * Tue Jul 10 16:14:45 CST 2018  Auto Generate
 */

public class BaseMessageBuEntity_HI_RO {
    public static final String QUERY_SQL = "SELECT messageBu.con_mess_id conMessId\n" +
            "\t\t\t,messageBu.bu_mess_id buMessId\n" +
            "\t\t\t,messageBu.bu_id buId\n" +
            "\t\t\t,blvBu.meaning buName\n" +
            "\t\t\t,messageBu.user_type userType\n" +
            "\t\t\t,blvUserType.meaning userTypeName\n" +
            "  FROM base_message_bu messageBu\n" +
            "\t\t\t,base_lookup_values blvBu\n" +
            "\t\t\t,base_lookup_values blvUserType\n" +
            " WHERE 1 = 1\n" +
            "\t AND messageBu.delete_flag = 0\n" +
            "\t AND messageBu.con_mess_id = :conMessId\n" +
            "\t AND messageBu.bu_id = blvBu.lookup_code\n" +
            "\t AND blvBu.lookup_type = 'BASE_OU'\n" +
            "\t AND blvBu.system_code = 'BASE'\n" +
            "\t AND messageBu.user_type = blvUserType.lookup_code\n" +
            "\t AND blvUserType.lookup_type = 'USER_TYPE'\n" +
            "\t AND blvUserType.system_code = 'BASE'\n" +
            " ORDER BY messageBu.bu_id\n" +
            "         ,messageBu.user_type";

    private Integer buMessId; //主键Id
    private Integer conMessId; //消息内容ID
    private Integer buId; //接收事业部ID
    private String buName;
    private String userType; //接收用户类型
    private String userTypeName;

    public Integer getBuMessId() {
        return buMessId;
    }

    public void setBuMessId(Integer buMessId) {
        this.buMessId = buMessId;
    }

    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    public Integer getBuId() {
        return buId;
    }

    public void setBuId(Integer buId) {
        this.buId = buId;
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }
}
