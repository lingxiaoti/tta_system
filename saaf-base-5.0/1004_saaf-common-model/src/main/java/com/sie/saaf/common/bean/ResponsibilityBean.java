package com.sie.saaf.common.bean;

import java.util.ArrayList;
import java.util.List;

public class ResponsibilityBean implements Comparable<ResponsibilityBean> {

    private Integer responsibilityId;//职责Id
    private String responsibilityName;//职责名称
    private String responsibilityCode;//职责编码
    private String systemCode;//系统编码
    private OrgBean orgBean;
    private List<ProFileBean> proFileBeans=new ArrayList<>(); //职责对应的profile
    private PositionBean positionBean;//职位信息


    public Integer getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(Integer responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    public String getResponsibilityCode() {
        return responsibilityCode;
    }

    public void setResponsibilityCode(String responsibilityCode) {
        this.responsibilityCode = responsibilityCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public OrgBean getOrgBean() {
        return orgBean;
    }

    public void setOrgBean(OrgBean orgBean) {
        this.orgBean = orgBean;
    }

    public List<ProFileBean> getProFileBeans() {
        return proFileBeans;
    }

    public void setProFileBeans(List<ProFileBean> proFileBeans) {
        this.proFileBeans = proFileBeans;
    }

    public PositionBean getPositionBean() {
        return positionBean;
    }

    public void setPositionBean(PositionBean positionBean) {
        this.positionBean = positionBean;
    }

    @Override
    public int compareTo(ResponsibilityBean o) {
        if (o == null)
            return 1;
        else if (o.getResponsibilityId() == null && this.getResponsibilityId() == null)
            return 0;
        else if (this.getResponsibilityId()==null)
            return -1;
        else if (o.getResponsibilityId()==null)
            return 1;
        else
            return this.getResponsibilityId().compareTo(o.getResponsibilityId());
    }
}
