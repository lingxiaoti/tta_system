package com.sie.saaf.common.util;

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
     * 功能描述： 获取上传临时文件目录
     */
    public static String getUploadTempPath() {
        return localResource.getString("uploadTempPath");
    }


    /**
     * 上上签系统的地址
     */
    public static String getHost() {
        return localResource.getString("serverHost");
    }
    /**
     *  上上签BestSign分配的客户端编号
     */
    public static String getClientId() {
        return localResource.getString("clientId");
    }

    /**
     *  上上签客户端凭证
     */
    public static String getClientSecret() {
        return localResource.getString("clientSecret");
    }

    /**
     *  上上签私钥
     */
    public static String getPrivateKey() {
        return localResource.getString("privateKey");
    }

    /**
     *  屈臣氏主体登录账号
     */
    public static String getAcount() {
        return localResource.getString("acount");
    }

    /**
     * 获取邮件参数信息
     * @return
     */
    public static String getEmailParaConfig() {
        return localResource.getString("emailParaConfig");
    }


}
