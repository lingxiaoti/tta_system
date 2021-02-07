package com.sie.saaf.base.user.model.entities.readonly;

/**
 * Created by Greate Summer on 2020/11/20.
 */
public class BaseUsersEntity_HI_RO {
    public static final String QUERY_SQL = "select DISTINCT U.USER_ID userId,U.PHONE_NUMBER phoneNumber," +
            " U.NAME_PINGYIN namePingyin,U.USER_NAME userName,U.EMAIL_ADDRESS emailAddress,U.SOURCE_ID sourceId," +
            " (SELECT listagg(basePv.profile_value,',') within group(order by basePv.profile_value) FROM BASE_PROFILE_VALUE " +
            " basePv WHERE (basePv.KEY_TABLE_NAME='base_users' " +
            " AND basePv.DELETE_FLAG!=1 AND basePv.BUSINESS_KEY=U.USER_ID)) profileValues," +
            " U.SOURCE_CODE sourceCode ,U.PERSON_ID personId,U.USER_TYPE userType, U.USER_FULL_NAME userFullName" +
            "  FROM BASE_USERS U \n" +
            //", BP.PROFILE_CODE profileCode,BP.PROFILE_NAME profileName FROM BASE_USERS U \n" +
            " LEFT JOIN BASE_PROFILE_VALUE BPV ON (BPV.KEY_TABLE_NAME='base_users' AND BPV.DELETE_FLAG!=1 AND BPV.BUSINESS_KEY=U.USER_ID) \n" +
//            " LEFT JOIN BASE_PROFILE BP ON (BP.PROFILE_ID=BPV.PROFILE_ID AND BPV.DELETE_FLAG=0) \n" +
//            " WHERE BP.PROFILE_CODE IS NOT NULL ";
            " WHERE 1=1 ";

    private Integer userId; // 用户Id
    private String phoneNumber; // 电话号码
    private String namePingyin; // 用户姓名（拼音）
    private String userName; // 用户名/登录帐号
    private String userFullName; // 姓名
    private Integer personId; // 对应经销商、门店、员工的外围系统ID
    private String sourceId; // 关联人员ID、关联经销商ID、关联门店编码
    private String sourceCode; // 用户来源
    private String userType; // 用户类型：IN:内部员工，OUT：经销商、门店、导购
    private String emailAddress;
    private String profileValues;

    private String profileCode;
    private String profileName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNamePingyin() {
        return namePingyin;
    }

    public void setNamePingyin(String namePingyin) {
        this.namePingyin = namePingyin;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileValues() {
        return profileValues;
    }

    public void setProfileValues(String profileValues) {
        this.profileValues = profileValues;
    }
}
