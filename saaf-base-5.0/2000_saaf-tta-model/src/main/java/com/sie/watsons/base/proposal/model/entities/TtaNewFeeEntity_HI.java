package com.sie.watsons.base.proposal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaNewFeeEntity_HI Entity Object
 * Tue Jun 04 08:38:52 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_new_fee")
public class TtaNewFeeEntity_HI {
    private Integer newFeeId;
    private String newItem;
    private Integer companyFee;
    private String mtc;
    private Integer charges;
    private Integer newQty;
    private String outMtc;
    private Integer outCharges;
    private String outUnit;
    private String ynTerms;
    private String wnTerms;
    private Integer ynFeeNotax;
    private Integer ynFeeTax;
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
    private Integer operatorUserId;

	public void setNewFeeId(Integer newFeeId) {
		this.newFeeId = newFeeId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_NEW_FEE", sequenceName = "SEQ_TTA_NEW_FEE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_NEW_FEE", strategy = GenerationType.SEQUENCE)
	@Column(name="new_fee_id", nullable=false, length=22)	
	public Integer getNewFeeId() {
		return newFeeId;
	}

	public void setNewItem(String newItem) {
		this.newItem = newItem;
	}

	@Column(name="new_item", nullable=true, length=20)	
	public String getNewItem() {
		return newItem;
	}

	public void setCompanyFee(Integer companyFee) {
		this.companyFee = companyFee;
	}

	@Column(name="company_fee", nullable=true, length=22)	
	public Integer getCompanyFee() {
		return companyFee;
	}

	public void setMtc(String mtc) {
		this.mtc = mtc;
	}

	@Column(name="mtc", nullable=true, length=20)	
	public String getMtc() {
		return mtc;
	}

	public void setCharges(Integer charges) {
		this.charges = charges;
	}

	@Column(name="charges", nullable=true, length=22)	
	public Integer getCharges() {
		return charges;
	}

	public void setNewQty(Integer newQty) {
		this.newQty = newQty;
	}

	@Column(name="new_qty", nullable=true, length=22)	
	public Integer getNewQty() {
		return newQty;
	}

	public void setOutMtc(String outMtc) {
		this.outMtc = outMtc;
	}

	@Column(name="out_mtc", nullable=true, length=20)	
	public String getOutMtc() {
		return outMtc;
	}

	public void setOutCharges(Integer outCharges) {
		this.outCharges = outCharges;
	}

	@Column(name="out_charges", nullable=true, length=22)	
	public Integer getOutCharges() {
		return outCharges;
	}

	public void setOutUnit(String outUnit) {
		this.outUnit = outUnit;
	}

	@Column(name="out_unit", nullable=true, length=20)	
	public String getOutUnit() {
		return outUnit;
	}

	public void setYnTerms(String ynTerms) {
		this.ynTerms = ynTerms;
	}

	@Column(name="yn_terms", nullable=true, length=100)	
	public String getYnTerms() {
		return ynTerms;
	}

	public void setWnTerms(String wnTerms) {
		this.wnTerms = wnTerms;
	}

	@Column(name="wn_terms", nullable=true, length=100)	
	public String getWnTerms() {
		return wnTerms;
	}

	public void setYnFeeNotax(Integer ynFeeNotax) {
		this.ynFeeNotax = ynFeeNotax;
	}

	@Column(name="yn_fee_notax", nullable=true, length=22)	
	public Integer getYnFeeNotax() {
		return ynFeeNotax;
	}

	public void setYnFeeTax(Integer ynFeeTax) {
		this.ynFeeTax = ynFeeTax;
	}

	@Column(name="yn_fee_tax", nullable=true, length=22)	
	public Integer getYnFeeTax() {
		return ynFeeTax;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2030)	
	public String getRemark() {
		return remark;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
