package com.sie.watsons.base.supplement.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmSupLogEntity_HI Entity Object
 * Tue Oct 15 13:46:14 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_SUP_SHOP_LOG")
public class PlmSupShopLogEntity_HI {
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

    public void setPlmSupLogId(Integer plmSupLogId) {
        this.plmSupLogId = plmSupLogId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_PLM_SUP_SHOP_LOG", sequenceName = "SEQ_PLM_SUP_SHOP_LOG", allocationSize = 1)
    @Column(name="plm_sup_shop_log_id", nullable=false, length=11)
    @GeneratedValue(generator = "SEQ_PLM_SUP_SHOP_LOG", strategy = GenerationType.SEQUENCE)
    public Integer getPlmSupLogId() {
        return plmSupLogId;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Column(name="sup_code")
    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    @Column(name="log_type", nullable=true, length=100)
    public String getLogType() {
        return logType;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name="product_code", nullable=true, length=100)
    public String getProductCode() {
        return productCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name="shop_code", nullable=true, length=100)
    public String getShopCode() {
        return shopCode;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    @Column(name="update_type", nullable=true, length=22)
    public String getUpdateType() {
        return updateType;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name="version_num", nullable=true, length=22)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name="creation_date", nullable=true, length=7)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="created_by", nullable=true, length=22)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name="last_updated_by", nullable=true, length=22)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name="last_update_date", nullable=true, length=7)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name="last_update_login", nullable=true, length=22)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}
