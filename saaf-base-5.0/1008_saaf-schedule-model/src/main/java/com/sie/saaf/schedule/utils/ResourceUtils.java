package com.sie.saaf.schedule.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ResourceBundle;

/**
 * 获取全局参数信息工具类
 *
 * @author xiaoga
 */
public class ResourceUtils {

    private static ResourceBundle localResource = null;

    static {
        localResource = ResourceBundle.getBundle("com/sie/saaf/app/config/application-common");
    }

    /**
     * 功能描述： 获取fptHost
     * @author xiaoga
     * @date 2019/7/10
     * @param
     * @return
     */
    public static String getFtpHost() {
        return localResource.getString("ftpHost");
    }

    /**
     * 功能描述： 获取tfp密码
     * @author xiaoga
     * @date 2019/7/10
     * @param
     * @return
     */
    public static String getFtpPassword() {
        return localResource.getString("ftpPassword");
    }

    /**
     * 功能描述： 获取ftp用户名
     * @author xiaoga
     * @date 2019/7/10
     * @param
     * @return
     */
    public static String getFtpUserName() {
        return localResource.getString("ftpUserName");
    }

    /**
     * 功能描述： 获取ftp端口号
     * @author xiaoga
     * @date 2019/7/10
     * @param
     * @return
     */
    public static Integer getFtpPort() {
        return Integer.parseInt(localResource.getString("ftpPort"));
    }

    /**
     * 功能描述：交易日历，与交易日历相差天数
     */
    public static String getTradeDiffDate() {
        return localResource.getString("tradeDiffDate");
    }
    

    public static String getBasePath() {
        return localResource.getString("basePath");
    }

    
    public static String getSftpHost() {
        return localResource.getString("sftpHost");
    }

    public static String getSftpBasePath() {
        return localResource.getString("sftpBasePath");
    }

    public static Integer getSftpPort() {
        return Integer.parseInt(localResource.getString("sftpPort"));
    }


    public static String getSftpPassword() {
        return localResource.getString("sftpPassword");
    }
    
    public static String getSftpUserName() {
        return localResource.getString("sftpUserName");
    }
    
    
    public static String getSftpRemoteBasePath() {
        return localResource.getString("sftpRemoteBasePath");
    }

    /**
     * 获取FTP地址
     * @return
     */
    public static String getUftpHost() {
        return localResource.getString("UftpHost");
    }

    /**
     * 获取FTP密码
     * @return
     */
    public static String getUftpPassword() {
        return localResource.getString("UftpPassword");
    }

    /**
     * 获取FTP用户名
     * @return
     */
    public static String getUftpUserName() {
        return localResource.getString("UftpUserName");
    }

    /**
     * 获取FTP端口
     * @return
     */
    public static Integer getUftpPort() {
        return Integer.parseInt(localResource.getString("UftpPort"));
    }

    /**
     * 获取FTPbase路径
     * @return
     */
    public static String getUbasePath() {
        return localResource.getString("UbasePath");
    }

    /**
     * 获取邮件参数信息
     * @return
     */
    public static String getEmailParaConfig() {
        return localResource.getString("emailParaConfig");
    }

    /**
     * 获取发件人邮箱
     */
    public static String getEmailUser() {
        return localResource.getString("emailUser");
    }

    /**
     * 获取调度信息需要通知的人员
     */
    public static String getEmialReceiveCode() {
        return localResource.getString("emialReceiveCode");
    }

    /**
     * 是否需要发送邮件
     */
    public static boolean getIsSendMsg() {
        if ("true".equalsIgnoreCase(localResource.getString("isSendMsg"))){
             return true;
        }
         return false;
    }

    /**
     * 获取指定接收人的邮箱
     * @return
     */
    public static String getAssignEmailUser(){
        return localResource.getString("assignEmailUser");
    }

    public static String getBmpStartTime(){
        return localResource.getString("bmpStartTime");
    }


}
