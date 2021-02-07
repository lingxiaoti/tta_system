package com.sie.watsons.base.pon.itquotation.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuoContentItEntity_HI_RO Entity Object
 * Tue Dec 17 18:21:56 CST 2019  Auto Generate
 */

public class EquPonQuoContentItEntity_HI_RO {
    public static void main(String[] args) {
        System.out.println(EquPonQuoContentItEntity_HI_RO.QUERY_SQL);
    }

    //    内容、单位、规格、 品牌、单价(含税)(元)、数量、折扣%、金额(含税)(元)、税率%、备注，说明
    public static final String QUERY_SQL = "select ci.quotation_content_id quotationContentId,\n" +
            "       ci.relevance_Id         relevanceId,\n" +
            "       ci.quotation_id         quotationId,\n" +
            "       qc.content,\n" +
            "       qc.unit,\n" +
            "       qc.specs,\n" +
            "       qc.brand,\n" +
            "       to_char(ci.unit_price,'FM999999999990.00')           unitPrice,\n" +
            "       qc.amount,\n" +
            "       ci.discount,\n" +
            "       to_char(ci.amount_of_money,'FM999999999990.00')      amountOfMoney,\n" +
            "       ci.tax_rate             taxRate,\n" +
            "       ci.quotation_remark     quotationRemark,\n" +
            "       qc.remark,\n" +
            "       ci.version_num          versionNum,\n" +
            "       ci.creation_date        creationDate,\n" +
            "       ci.created_by           createdBy,\n" +
            "       to_char(ci.bargain_unit_price,'FM999999999990.00')      bargainUnitPrice,\n" +
            "       ci.bargain_discount        bargainDiscount,\n" +
            "       to_char(ci.bargain_amount_of_money,'FM999999999990.00') bargainAmountOfMoney,\n" +
            "       ci.bargain_tax_rate        bargainTaxRate\n" +
            "  from equ_pon_quo_content_it ci\n" +
            "  left join equ_pon_it_quotation_content qc\n" +
            "    on ci.relevance_Id = qc.content_id\n" +
            " where 1 = 1";


    private Integer quotationContentId;
    private Integer quotationId;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal amountOfMoney;
    private BigDecimal taxRate;
    private String quotationRemark;
    private String remark;
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
    private Integer relevanceId;
    private String content;
    private String brand;
    private BigDecimal amount;
    private String unit;
    private String specs;
    private BigDecimal bargainUnitPrice;
    private BigDecimal bargainDiscount;
    private BigDecimal bargainAmountOfMoney;
    private BigDecimal bargainTaxRate;

    public void setQuotationContentId(Integer quotationContentId) {
        this.quotationContentId = quotationContentId;
    }


    public Integer getQuotationContentId() {
        return quotationContentId;
    }

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(BigDecimal amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setQuotationRemark(String quotationRemark) {
        this.quotationRemark = quotationRemark;
    }


    public String getQuotationRemark() {
        return quotationRemark;
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

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public BigDecimal getBargainUnitPrice() {
        return bargainUnitPrice;
    }

    public void setBargainUnitPrice(BigDecimal bargainUnitPrice) {
        this.bargainUnitPrice = bargainUnitPrice;
    }

    public BigDecimal getBargainDiscount() {
        return bargainDiscount;
    }

    public void setBargainDiscount(BigDecimal bargainDiscount) {
        this.bargainDiscount = bargainDiscount;
    }

    public BigDecimal getBargainAmountOfMoney() {
        return bargainAmountOfMoney;
    }

    public void setBargainAmountOfMoney(BigDecimal bargainAmountOfMoney) {
        this.bargainAmountOfMoney = bargainAmountOfMoney;
    }

    public BigDecimal getBargainTaxRate() {
        return bargainTaxRate;
    }

    public void setBargainTaxRate(BigDecimal bargainTaxRate) {
        this.bargainTaxRate = bargainTaxRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
