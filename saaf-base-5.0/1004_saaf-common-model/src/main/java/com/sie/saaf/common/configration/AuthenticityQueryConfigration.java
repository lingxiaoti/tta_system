package com.sie.saaf.common.configration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "authenticity", ignoreNestedProperties = true)
@Component
public class AuthenticityQueryConfigration {

    private String domain;
    private String codeRetrospectBaseInfoUrl;
    private String codeRetrospectRecordUrl;
    private String baseInfoUrl;
    private String retrospectRecordUrl;
    private String codeRecordUrl;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCodeRetrospectBaseInfoUrl() {
        return codeRetrospectBaseInfoUrl;
    }

    public void setCodeRetrospectBaseInfoUrl(String codeRetrospectBaseInfoUrl) {
        this.codeRetrospectBaseInfoUrl = codeRetrospectBaseInfoUrl;
    }

    public String getCodeRetrospectRecordUrl() {
        return codeRetrospectRecordUrl;
    }

    public void setCodeRetrospectRecordUrl(String codeRetrospectRecordUrl) {
        this.codeRetrospectRecordUrl = codeRetrospectRecordUrl;
    }

    public String getBaseInfoUrl() {
        return baseInfoUrl;
    }

    public void setBaseInfoUrl(String baseInfoUrl) {
        this.baseInfoUrl = baseInfoUrl;
    }

    public String getRetrospectRecordUrl() {
        return retrospectRecordUrl;
    }

    public void setRetrospectRecordUrl(String retrospectRecordUrl) {
        this.retrospectRecordUrl = retrospectRecordUrl;
    }

    public String getCodeRecordUrl() {
        return codeRecordUrl;
    }

    public void setCodeRecordUrl(String codeRecordUrl) {
        this.codeRecordUrl = codeRecordUrl;
    }
}
