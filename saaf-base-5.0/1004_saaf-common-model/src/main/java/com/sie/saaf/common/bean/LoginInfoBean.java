package com.sie.saaf.common.bean;

import java.util.Date;

/**
 * 用户登陆信息
 */
public class LoginInfoBean {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * agent信息
     */
    private String agent;

    /**
     * pc、app
     */
    private String  platform;;

    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 设备型号
     */
    private String deviceInfo;
    /**
     * 系统类型
     */
    private String system;
    /**
     * 系统版本
     */
    private String systemVersion;
    /**
     * 设配内存大小
     */
    private String totalMem;
    /**
     * app版本
     */
    private String appVersion;
    /**
     * 登录时间
     */
    private Date loginDate;
    /**
     * ip
     */
    private String ip;

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

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(String totalMem) {
        this.totalMem = totalMem;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
