package com.sie.saaf.rule.model.beans;


import com.sie.saaf.rule.model.entities.SaafWebserviceParamInfoEntity_HI;

import java.util.List;

public class DimServiceBean {
    private String busTargetType;
    private String busTargetSource;
    private String webserviceUrl;
    private List<SaafWebserviceParamInfoEntity_HI> busRequestParams;
    private String busResultParams;

    public DimServiceBean() {
        super();
    }

    public DimServiceBean(String busTargetType, String busTargetSource, List<SaafWebserviceParamInfoEntity_HI> busParam, String ruleBusResultDim) {
        this.busTargetType = busTargetType;
        this.busTargetSource = busTargetSource;
        this.busRequestParams = busParam;
        this.busResultParams = ruleBusResultDim;
    }

    public DimServiceBean(String busTargetType, String busTargetSource, String webserviceUrl, List<SaafWebserviceParamInfoEntity_HI> busRequestParams, String busResultParams) {
        this.busTargetType = busTargetType;
        this.busTargetSource = busTargetSource;
        this.webserviceUrl = webserviceUrl;
        this.busRequestParams = busRequestParams;
        this.busResultParams = busResultParams;
    }

    public String getWebserviceUrl() {
        return webserviceUrl;
    }

    public void setWebserviceUrl(String webserviceUrl) {
        this.webserviceUrl = webserviceUrl;
    }

    public void setBusTargetType(String busTargetType) {
        this.busTargetType = busTargetType;
    }

    public String getBusTargetType() {
        return busTargetType;
    }

    public void setBusTargetSource(String busTargetSource) {
        this.busTargetSource = busTargetSource;
    }

    public String getBusTargetSource() {
        return busTargetSource;
    }

    public void setBusRequestParams(List<SaafWebserviceParamInfoEntity_HI> busRequestParams) {
        this.busRequestParams = busRequestParams;
    }

    public List<SaafWebserviceParamInfoEntity_HI> getBusRequestParams() {
        return busRequestParams;
    }

    public void setBusResultParams(String busResultParams) {
        this.busResultParams = busResultParams;
    }

    public String getBusResultParams() {
        return busResultParams;
    }
}
