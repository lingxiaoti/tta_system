package com.sie.watsons.base.proposal.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/1/001.
 */
@Entity
@Table(name="tta_proposal_terms_line")
public class TtaProposalTermsLineEntity_HI {

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


    @Id
    @SequenceGenerator(name = "SEQ_TTA_PROPOSAL_TERMS_LINE", sequenceName = "SEQ_TTA_PROPOSAL_TERMS_LINE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_PROPOSAL_TERMS_LINE", strategy = GenerationType.SEQUENCE)
    @Column(name="terms_l_id", nullable=false, length=22)
    public Integer getTermsLId() {
        return termsLId;
    }

    public void setTermsLId(Integer termsLId) {
        this.termsLId = termsLId;
    }

    @Column(name="terms_h_id")
    public Integer getTermsHId() {
        return termsHId;
    }

    public void setTermsHId(Integer termsHId) {
        this.termsHId = termsHId;
    }

    @Column(name="income_type")
    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    @Column(name="y_terms")
    public String getyTerms() {
        return yTerms;
    }

    public void setyTerms(String yTerms) {
        this.yTerms = yTerms;
    }

    @Column(name="collect_type")
    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    @Column(name="reference_standard")
    public Integer getReferenceStandard() {
        return referenceStandard;
    }

    public void setReferenceStandard(Integer referenceStandard) {
        this.referenceStandard = referenceStandard;
    }

    @Column(name="y_year")
    public String getyYear() {
        return yYear;
    }

    public void setyYear(String yYear) {
        this.yYear = yYear;
    }

    @Column(name="qty")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name="unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name="terms_system")
    public String getTermsSystem() {
        return termsSystem;
    }

    public void setTermsSystem(String termsSystem) {
        this.termsSystem = termsSystem;
    }

    @Column(name="fee_notax")
    public Integer getFeeNotax() {
        return feeNotax;
    }

    public void setFeeNotax(Integer feeNotax) {
        this.feeNotax = feeNotax;
    }

    @Column(name="fee_intas")
    public Integer getFeeIntas() {
        return feeIntas;
    }

    public void setFeeIntas(Integer feeIntas) {
        this.feeIntas = feeIntas;
    }

    @Column(name="terms_code")
    public String getTermsCode() {
        return termsCode;
    }

    public void setTermsCode(String termsCode) {
        this.termsCode = termsCode;
    }

    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="creation_date")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name="created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="last_updated_by")
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name="last_update_date")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name="last_update_login")
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name="version_num")
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Column(name="proposal_id")
    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    @Column(name="clause_id")
    public Integer getClauseId() {
        return clauseId;
    }

    public void setClauseId(Integer clauseId) {
        this.clauseId = clauseId;
    }

    @Column(name="clause_tree_id")
    public Integer getClauseTreeId() {
        return clauseTreeId;
    }

    public void setClauseTreeId(Integer clauseTreeId) {
        this.clauseTreeId = clauseTreeId;
    }

    @Column(name="order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name="y_terms_en")
    public String getyTermsEn() {
        return yTermsEn;
    }

    public void setyTermsEn(String yTermsEn) {
        this.yTermsEn = yTermsEn;
    }

    @Column(name="old_clause_id")
    public Integer getOldClauseId() {
        return oldClauseId;
    }

    public void setOldClauseId(Integer oldClauseId) {
        this.oldClauseId = oldClauseId;
    }

    @Column(name="business_version")
    public String getBusinessVersion() {
        return businessVersion;
    }

    public void setBusinessVersion(String businessVersion) {
        this.businessVersion = businessVersion;
    }

    @Column(name="oi_type")
    public String getOiType() {
        return oiType;
    }

    public void setOiType(String oiType) {
        this.oiType = oiType;
    }
}
