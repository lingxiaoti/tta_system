package com.sie.watsons.base.pon.quotation.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuotationProductInfoEntity_HI Entity Object
 * Mon Oct 14 19:31:23 CST 2019  Auto Generate
 */
@Entity
@Table(name = "equ_pon_quotation_product_info")
public class EquPonQuotationProductInfoEntity_HI {
    private Integer quotationProductId;
    private Integer quotationId;
    private String productName;
    private BigDecimal firstProductPrice;
    private BigDecimal twoProductPrice;
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
    private String updateNopriceFileName;
    private Integer operatorUserId;
    private BigDecimal bargainFirstPrice;
    private BigDecimal bargainTwoPrice;
    private Integer supplierId;
    private String projectNumber;
    private Integer specsId;

    public void setQuotationProductId(Integer quotationProductId) {
        this.quotationProductId = quotationProductId;
    }

    @Id
    @SequenceGenerator(name = "EQU_PON_QUO_PRODUCE_SEQ", sequenceName = "EQU_PON_QUO_PRODUCE_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "EQU_PON_QUO_PRODUCE_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "quotation_product_id", nullable = false, length = 22)
    public Integer getQuotationProductId() {
        return quotationProductId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    @Column(name = "quotation_id", nullable = true, length = 22)
    public Integer getQuotationId() {
        return quotationId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "product_name", nullable = true, length = 50)
    public String getProductName() {
        return productName;
    }

    public void setFirstProductPrice(BigDecimal firstProductPrice) {
        this.firstProductPrice = firstProductPrice;
    }

    @Column(name = "first_product_price", nullable = true, length = 22)
    public BigDecimal getFirstProductPrice() {
        return firstProductPrice;
    }

    public void setTwoProductPrice(BigDecimal twoProductPrice) {
        this.twoProductPrice = twoProductPrice;
    }

    @Column(name = "two_product_price", nullable = true, length = 22)
    public BigDecimal getTwoProductPrice() {
        return twoProductPrice;
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

    @Column(name = "last_update_date", nullable = true, length = 7)
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

    public void setUpdateNopriceFileName(String updateNopriceFileName) {
        this.updateNopriceFileName = updateNopriceFileName;
    }

    @Column(name = "update_noprice_file_name", nullable = true, length = 100)
    public String getUpdateNopriceFileName() {
        return updateNopriceFileName;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name = "bargain_first_price", nullable = true, length = 22)
    public BigDecimal getBargainFirstPrice() {
        return bargainFirstPrice;
    }

    public void setBargainFirstPrice(BigDecimal bargainFirstPrice) {
        this.bargainFirstPrice = bargainFirstPrice;
    }

    @Column(name = "bargain_two_price", nullable = true, length = 22)
    public BigDecimal getBargainTwoPrice() {
        return bargainTwoPrice;
    }

    public void setBargainTwoPrice(BigDecimal bargainTwoPrice) {
        this.bargainTwoPrice = bargainTwoPrice;
    }
    @Column(name = "supplier_id", nullable = false, length = 22)
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
    @Column(name = "project_number", nullable = false, length = 50)
    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }
    @Column(name="specs_id", nullable=true, length=22)
    public Integer getSpecsId() {
        return specsId;
    }

    public void setSpecsId(Integer specsId) {
        this.specsId = specsId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}
