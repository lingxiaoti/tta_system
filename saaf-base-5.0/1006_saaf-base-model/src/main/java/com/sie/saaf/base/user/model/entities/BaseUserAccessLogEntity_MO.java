package com.sie.saaf.base.user.model.entities;

import org.springframework.data.annotation.Id;

import java.util.Date;
//@Document(collection = "base_user_access_log")
public class BaseUserAccessLogEntity_MO {
    @Id
    private String id;
    private String accessUserId;
    private String accessSystemCode;
    private String accessMenuTitle;
    private Date accessDate;
    private String accessSourceIp;
    private String accessDevice;
    public BaseUserAccessLogEntity_MO() {
        super();
    }

    public void setAccessUserId(String accessUserId) {
        this.accessUserId = accessUserId;
    }

    public String getAccessUserId() {
        return accessUserId;
    }

    public void setAccessSystemCode(String accessSystemCode) {
        this.accessSystemCode = accessSystemCode;
    }

    public String getAccessSystemCode() {
        return accessSystemCode;
    }

    public void setAccessMenuTitle(String accessMenuTitle) {
        this.accessMenuTitle = accessMenuTitle;
    }

    public String getAccessMenuTitle() {
        return accessMenuTitle;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessSourceIp(String accessSourceIp) {
        this.accessSourceIp = accessSourceIp;
    }

    public String getAccessSourceIp() {
        return accessSourceIp;
    }

    public void setAccessDevice(String accessDevice) {
        this.accessDevice = accessDevice;
    }

    public String getAccessDevice() {
        return accessDevice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
