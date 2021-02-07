package com.sie.watsons.base.withdrawal.utils;

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
     * 功能描述： 获取上传临时文件目录
     */
    public static String getPageOfficecLicPath() {
        return localResource.getString("posyspath");
    }
    public static String getPageOfficecUrl() {
        return localResource.getString("url");
    }

}
