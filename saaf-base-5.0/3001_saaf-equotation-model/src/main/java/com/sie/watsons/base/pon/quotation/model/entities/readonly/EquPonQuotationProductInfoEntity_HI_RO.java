package com.sie.watsons.base.pon.quotation.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuotationProductInfoEntity_HI_RO Entity Object
 * Mon Oct 14 19:31:23 CST 2019  Auto Generate
 */

public class EquPonQuotationProductInfoEntity_HI_RO {
    public static final String SELECT_SEQ = "select t.quotation_product_id quotationProductId,\n" +
            "       t.quotation_id         quotationId,\n" +
            "       t.product_name         productName,\n" +
            "       t.bargain_first_price  bargainFirstPrice,\n" +
            "       t.bargain_two_price    bargainTwoPrice,\n" +
            "       t.first_product_price  firstProductPrice,\n" +
            "       t.supplier_id  supplierId,\n" +
            "       t.project_number  projectNumber,\n" +
            "       t.version_num  versionNum,\n" +
            "       t.specs_id  specsId,\n" +
            "       t.two_product_price    twoProductPrice\n" +
            "  from EQU_PON_QUOTATION_PRODUCT_INFO t\n" +
            " where 1 = 1\n";
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


    public Integer getQuotationProductId() {
        return quotationProductId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }


    public Integer getQuotationId() {
        return quotationId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductName() {
        return productName;
    }

    public void setFirstProductPrice(BigDecimal firstProductPrice) {
        this.firstProductPrice = firstProductPrice;
    }

    public BigDecimal getBargainFirstPrice() {
        return bargainFirstPrice;
    }

    public void setBargainFirstPrice(BigDecimal bargainFirstPrice) {
        this.bargainFirstPrice = bargainFirstPrice;
    }

    public BigDecimal getBargainTwoPrice() {
        return bargainTwoPrice;
    }

    public void setBargainTwoPrice(BigDecimal bargainTwoPrice) {
        this.bargainTwoPrice = bargainTwoPrice;
    }

    public BigDecimal getFirstProductPrice() {
        return firstProductPrice;
    }

    public void setTwoProductPrice(BigDecimal twoProductPrice) {
        this.twoProductPrice = twoProductPrice;
    }


    public BigDecimal getTwoProductPrice() {
        return twoProductPrice;
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

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }


    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }


    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }


    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }


    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }


    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }


    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }


    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }


    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }


    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }


    public String getAttribute10() {
        return attribute10;
    }

    public void setUpdateNopriceFileName(String updateNopriceFileName) {
        this.updateNopriceFileName = updateNopriceFileName;
    }


    public String getUpdateNopriceFileName() {
        return updateNopriceFileName;
    }


    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Integer getSpecsId() {
        return specsId;
    }

    public void setSpecsId(Integer specsId) {
        this.specsId = specsId;
    }
}
