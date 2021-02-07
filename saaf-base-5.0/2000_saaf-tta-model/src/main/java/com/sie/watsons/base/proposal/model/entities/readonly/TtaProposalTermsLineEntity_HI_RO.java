package com.sie.watsons.base.proposal.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2019/7/1/001.
 */
public class TtaProposalTermsLineEntity_HI_RO {


            private Integer termsLId;
            private Integer termsHId;
            private String incomeType;
            private String yTerms;
            private String collectType;
            private Integer referenceStandard;
            private String yYear;
            private Integer qty;
            private String unit;
            private String termsSystem;
            private Integer feeNotax;
            private Integer feeIntas;
            private String termsCode;
            private String remark;
            @JSONField(format="yyyy-MM-dd HH:mm:ss")
            private Date creationDate;
            private Integer createdBy;
            private Integer lastUpdatedBy;
            @JSONField(format="yyyy-MM-dd HH:mm:ss")
            private Date lastUpdateDate;
            private Integer lastUpdateLogin;
            private Integer versionNum;
            private Integer proposalId;
            private Integer clauseId;
            private Integer clauseTreeId;
            private String orderNo;
            private String yTermsEn;
            private Integer oldClauseId;
            private String businessVersion;
            private String oiType;

            private String orderNbr;

    public String getOrderNbr() {
        return orderNbr;
    }

    public void setOrderNbr(String orderNbr) {
        this.orderNbr = orderNbr;
    }

    public static final String TTA_ITEM_A= "select a.*,h.order_nbr from tta_proposal_terms_line a " +
            ",TTA_PROPOSAL_HEADER h where a.proposal_id = h.proposal_id " +
            "and h.version_status = 1  ";

    public Integer getTermsLId() {
        return termsLId;
    }

    public void setTermsLId(Integer termsLId) {
        this.termsLId = termsLId;
    }

    public Integer getTermsHId() {
        return termsHId;
    }

    public void setTermsHId(Integer termsHId) {
        this.termsHId = termsHId;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getyTerms() {
        return yTerms;
    }

    public void setyTerms(String yTerms) {
        this.yTerms = yTerms;
    }

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public Integer getReferenceStandard() {
        return referenceStandard;
    }

    public void setReferenceStandard(Integer referenceStandard) {
        this.referenceStandard = referenceStandard;
    }

    public String getyYear() {
        return yYear;
    }

    public void setyYear(String yYear) {
        this.yYear = yYear;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTermsSystem() {
        return termsSystem;
    }

    public void setTermsSystem(String termsSystem) {
        this.termsSystem = termsSystem;
    }

    public Integer getFeeNotax() {
        return feeNotax;
    }

    public void setFeeNotax(Integer feeNotax) {
        this.feeNotax = feeNotax;
    }

    public Integer getFeeIntas() {
        return feeIntas;
    }

    public void setFeeIntas(Integer feeIntas) {
        this.feeIntas = feeIntas;
    }

    public String getTermsCode() {
        return termsCode;
    }

    public void setTermsCode(String termsCode) {
        this.termsCode = termsCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    public Integer getClauseId() {
        return clauseId;
    }

    public void setClauseId(Integer clauseId) {
        this.clauseId = clauseId;
    }

    public Integer getClauseTreeId() {
        return clauseTreeId;
    }

    public void setClauseTreeId(Integer clauseTreeId) {
        this.clauseTreeId = clauseTreeId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getyTermsEn() {
        return yTermsEn;
    }

    public void setyTermsEn(String yTermsEn) {
        this.yTermsEn = yTermsEn;
    }

    public Integer getOldClauseId() {
        return oldClauseId;
    }

    public void setOldClauseId(Integer oldClauseId) {
        this.oldClauseId = oldClauseId;
    }

    public String getBusinessVersion() {
        return businessVersion;
    }

    public void setBusinessVersion(String businessVersion) {
        this.businessVersion = businessVersion;
    }

    public String getOiType() {
        return oiType;
    }

    public void setOiType(String oiType) {
        this.oiType = oiType;
    }
}
