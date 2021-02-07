package com.sie.watsons.base.supplement.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmSupLogEntity_HI_RO Entity Object
 * Tue Oct 15 13:46:14 CST 2019  Auto Generate
 */

public class PlmSupShopLogEntity_HI_RO {

    public static final String QUERY ="select * from PLM_SUP_SHOP_LOG s where 1=1 ";
    private Integer plmSupLogId;
    private String logType;
    private String productCode;
    private String shopCode;
    private String updateType;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    private String supCode;

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public void setPlmSupLogId(Integer plmSupLogId) {
        this.plmSupLogId = plmSupLogId;
    }


    public Integer getPlmSupLogId() {
        return plmSupLogId;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }


    public String getLogType() {
        return logType;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }


    public String getShopCode() {
        return shopCode;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }


    public String getUpdateType() {
        return updateType;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }


    public Integer getVersionNum() {
        return versionNum;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }


    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}
