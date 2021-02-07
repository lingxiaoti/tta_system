package com.sie.saaf.common.configration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "edoc", ignoreNestedProperties = true)
@Component
public class EdocConfigration {

    private String accessKey;
    private String requestUrl;
    private String accessTokenApi;
    private String tokenApi;
    private String isLoginApi;
    private String ipAddress;
    private String uploadApi;
    private String uploadDomain;
    private String deleteFileApi;
    private String downloadUrl;
    private String userLoginApi;
    private String password;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getAccessTokenApi() {
        return accessTokenApi;
    }

    public void setAccessTokenApi(String accessTokenApi) {
        this.accessTokenApi = accessTokenApi;
    }

    public String getTokenApi() {
        return tokenApi;
    }

    public void setTokenApi(String tokenApi) {
        this.tokenApi = tokenApi;
    }

    public String getIsLoginApi() {
        return isLoginApi;
    }

    public void setIsLoginApi(String isLoginApi) {
        this.isLoginApi = isLoginApi;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUploadApi() {
        return uploadApi;
    }

    public void setUploadApi(String uploadApi) {
        this.uploadApi = uploadApi;
    }

    public String getDeleteFileApi() {
        return deleteFileApi;
    }

    public void setDeleteFileApi(String deleteFileApi) {
        this.deleteFileApi = deleteFileApi;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUploadDomain() {
        return uploadDomain;
    }

    public void setUploadDomain(String uploadDomain) {
        this.uploadDomain = uploadDomain;
    }

    public String getUserLoginApi() {
        return userLoginApi;
    }

    public void setUserLoginApi(String userLoginApi) {
        this.userLoginApi = userLoginApi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}