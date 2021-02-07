package com.sie.watsons.base.pon.quotation.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuotationInfoEntity_HI Entity Object
 * Sun Oct 06 15:41:47 CST 2019  Auto Generate
 */
@Entity
@Table(name = "equ_pon_quotation_info")
public class EquPonQuotationInfoEntity_HI {
    private Integer quotationId;
    private String quotationNumber;
    private String projectNumber;
    private String docStatus;
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
    private BigDecimal firstPercent;
    private BigDecimal secondPercent;
    private Integer supplierId;
    private String orderType;
    private Integer sourceId;
    private Date commitTime;
    private Integer projectId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date secondQuotationDeadline;
    private String breakFlag;
    private String sourceProjectNumber;


    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    @Id
    @SequenceGenerator(name = "EQU_PON_QUOTATION_INFO_SEQ", sequenceName = "EQU_PON_QUOTATION_INFO_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "EQU_PON_QUOTATION_INFO_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "quotation_id", nullable = false, length = 22)
    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    @Column(name = "quotation_number", nullable = true, length = 30)
    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    @Column(name = "project_number", nullable = true, length = 30)
    public String getProjectNumber() {
        return projectNumber;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    @Column(name = "doc_status", nullable = true, length = 30)
    public String getDocStatus() {
        return docStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "remark", nullable = true, length = 240)
    public String getRemark() {
        return remark;
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

    @Column(name = "first_percent", nullable = true, length = 7)
    public BigDecimal getFirstPercent() {
        return firstPercent;
    }

    public void setFirstPercent(BigDecimal firstPercent) {
        this.firstPercent = firstPercent;
    }

    @Column(name = "second_percent", nullable = true, length = 7)
    public BigDecimal getSecondPercent() {
        return secondPercent;
    }

    public void setSecondPercent(BigDecimal secondPercent) {
        this.secondPercent = secondPercent;
    }

    @Column(name = "supplier_id", nullable = true, length = 22)
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Column(name = "order_type", nullable = true, length = 30)
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    @Column(name = "source_id", nullable = false, length = 22)
    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "commit_time", nullable = false, length = 7)
    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }
    @Column(name = "project_id", nullable = true, length = 22)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    @Column(name = "second_quotation_deadline", nullable = false, length = 7)
    public Date getSecondQuotationDeadline() {
        return secondQuotationDeadline;
    }

    public void setSecondQuotationDeadline(Date secondQuotationDeadline) {
        this.secondQuotationDeadline = secondQuotationDeadline;
    }
    @Column(name = "break_flag", nullable = true, length = 1)
    public String getBreakFlag() {
        return breakFlag;
    }

    public void setBreakFlag(String breakFlag) {
        this.breakFlag = breakFlag;
    }
    @Column(name = "source_project_number", nullable = true, length = 30)
    public String getSourceProjectNumber() {
        return sourceProjectNumber;
    }
    public void setSourceProjectNumber(String sourceProjectNumber) {
        this.sourceProjectNumber = sourceProjectNumber;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

}
