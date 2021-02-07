package com.sie.watsons.base.pon.quotation.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * EquPonQuotationPriceDocEntity_HI Entity Object
 * Sun Oct 06 15:41:49 CST 2019  Auto Generate
 */
@Entity
@Table(name = "equ_pon_quotation_price_doc")
public class EquPonQuotationPriceDocEntity_HI {
    private Integer quotationPriceId;
    private Integer quotationId;
    private Integer projectPriceId;
    private Integer priceFileId;
    private Integer updatePriceFileId;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;
    private String updatePriceFileName;
    private String priceFileName;
    private String priceFilePath;
    private String updatePriceFilePath;
    private String projectPriceName;
    private String projectPricePath;

    public void setQuotationPriceId(Integer quotationPriceId) {
        this.quotationPriceId = quotationPriceId;
    }

    @Id
    @SequenceGenerator(name = "EQU_PON_QUO_PRICE_DOC_SEQ", sequenceName = "EQU_PON_QUO_PRICE_DOC_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "EQU_PON_QUO_PRICE_DOC_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "quotation_price_id", nullable = false, length = 22)
    public Integer getQuotationPriceId() {
        return quotationPriceId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    @Column(name = "quotation_id", nullable = true, length = 22)
    public Integer getQuotationId() {
        return quotationId;
    }

    public void setProjectPriceId(Integer projectPriceId) {
        this.projectPriceId = projectPriceId;
    }

    @Column(name = "project_price_id", nullable = true, length = 22)
    public Integer getProjectPriceId() {
        return projectPriceId;
    }

    public void setPriceFileId(Integer priceFileId) {
        this.priceFileId = priceFileId;
    }

    @Column(name = "price_file_id", nullable = true, length = 22)
    public Integer getPriceFileId() {
        return priceFileId;
    }

    public void setUpdatePriceFileId(Integer updatePriceFileId) {
        this.updatePriceFileId = updatePriceFileId;
    }

    @Column(name = "update_price_file_id", nullable = true, length = 22)
    public Integer getUpdatePriceFileId() {
        return updatePriceFileId;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 22)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "creation_date", nullable = false, length = 7)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by", nullable = false, length = 22)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_updated_by", nullable = false, length = 22)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_date", nullable = false, length = 7)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_update_login", nullable = true, length = 22)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    @Column(name = "attribute1", nullable = true, length = 240)
    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    @Column(name = "attribute2", nullable = true, length = 240)
    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    @Column(name = "attribute3", nullable = true, length = 240)
    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    @Column(name = "attribute4", nullable = true, length = 240)
    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    @Column(name = "attribute5", nullable = true, length = 240)
    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    @Column(name = "attribute6", nullable = true, length = 240)
    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    @Column(name = "attribute7", nullable = true, length = 240)
    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    @Column(name = "attribute8", nullable = true, length = 240)
    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    @Column(name = "attribute9", nullable = true, length = 240)
    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

    @Column(name = "attribute10", nullable = true, length = 240)
    public String getAttribute10() {
        return attribute10;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name = "update_price_file_name", nullable = true, length = 100)
    public String getUpdatePriceFileName() {
        return updatePriceFileName;
    }

    public void setUpdatePriceFileName(String updatePriceFileName) {
        this.updatePriceFileName = updatePriceFileName;
    }
    @Column(name = "price_file_name", nullable = true, length = 100)
    public String getPriceFileName() {
        return priceFileName;
    }

    public void setPriceFileName(String priceFileName) {
        this.priceFileName = priceFileName;
    }
    @Column(name = "price_file_path", nullable = true, length = 240)
    public String getPriceFilePath() {
        return priceFilePath;
    }

    public void setPriceFilePath(String priceFilePath) {
        this.priceFilePath = priceFilePath;
    }
    @Column(name = "update_price_file_path", nullable = true, length = 240)
    public String getUpdatePriceFilePath() {
        return updatePriceFilePath;
    }

    public void setUpdatePriceFilePath(String updatePriceFilePath) {
        this.updatePriceFilePath = updatePriceFilePath;
    }
    @Column(name = "project_price_name", nullable = true, length = 100)
    public String getProjectPriceName() {
        return projectPriceName;
    }

    public void setProjectPriceName(String projectPriceName) {
        this.projectPriceName = projectPriceName;
    }
    @Column(name = "project_price_path", nullable = true, length = 240)
    public String getProjectPricePath() {
        return projectPricePath;
    }

    public void setProjectPricePath(String projectPricePath) {
        this.projectPricePath = projectPricePath;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}
