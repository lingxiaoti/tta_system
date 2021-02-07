package com.sie.watsons.base.questionnaire.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaQuestionNewMapDetailEntity_HI_RO Entity Object
 * Wed Sep 11 19:39:56 CST 2019  Auto Generate
 */

public class TtaQuestionNewMapDetailEntity_HI_RO {

    public static String querySlq = "select t.map_detail_id,\n" +
            "t.proposal_id,\n" +
            "t.sku_desc,\n" +
            "round(cost,2) as cost,\n" +
            "round(t.retail_price,2) as retail_price,\n" +
            "t.normal_gp,\n" +
            "round(t.promo_price, 2) as promo_price,\n" +
            "t.promo_gp,\n" +
            "t.version_num,\n" +
            "t.creation_date,\n" +
            "t.created_by,\n" +
            "t.last_updated_by,\n" +
            "t.last_update_date,\n" +
            "t.last_update_login,\n" +
            "t.remark\n" +
            " from tta_question_new_map_detail t where 1 = 1 ";

    private Integer mapDetailId;
    private Integer proposalId;
    private String skuDesc;
    private BigDecimal cost;
    private BigDecimal retailPrice;
    private String normalGp;
    private BigDecimal promoPrice;
    private String promoGp;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String remark;

    public void setMapDetailId(Integer mapDetailId) {
        this.mapDetailId = mapDetailId;
    }


    public Integer getMapDetailId() {
        return mapDetailId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }


    public Integer getProposalId() {
        return proposalId;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }


    public String getSkuDesc() {
        return skuDesc;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }


    public BigDecimal getCost() {
        return cost;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }


    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setNormalGp(String normalGp) {
        this.normalGp = normalGp;
    }


    public String getNormalGp() {
        return normalGp;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }


    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoGp(String promoGp) {
        this.promoGp = promoGp;
    }


    public String getPromoGp() {
        return promoGp;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
