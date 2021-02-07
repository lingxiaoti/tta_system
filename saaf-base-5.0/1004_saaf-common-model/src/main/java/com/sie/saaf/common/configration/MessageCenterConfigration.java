package com.sie.saaf.common.configration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 消息中心接口配置
 */
@ConfigurationProperties(prefix = "messageCenter", ignoreNestedProperties = true)
@Component
public class MessageCenterConfigration {

    private String userName;
    private String password;
    /**
     * 获取登录验证码模版id
     */
    private String loginVerificationCodeCfgId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginVerificationCodeCfgId() {
        return loginVerificationCodeCfgId;
    }

    public void setLoginVerificationCodeCfgId(String loginVerificationCodeCfgId) {
        this.loginVerificationCodeCfgId = loginVerificationCodeCfgId;
    }
}
