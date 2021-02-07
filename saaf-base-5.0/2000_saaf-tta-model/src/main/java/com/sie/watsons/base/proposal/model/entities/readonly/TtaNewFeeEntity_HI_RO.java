package com.sie.watsons.base.proposal.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaNewFeeEntity_HI_RO Entity Object
 * Tue Jun 04 08:38:52 CST 2019  Auto Generate
 */

public class TtaNewFeeEntity_HI_RO {
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

	
	public Integer getNewFeeId() {
		return newFeeId;
	}

	public void setNewItem(String newItem) {
		this.newItem = newItem;
	}

	
	public String getNewItem() {
		return newItem;
	}

	public void setCompanyFee(Integer companyFee) {
		this.companyFee = companyFee;
	}

	
	public Integer getCompanyFee() {
		return companyFee;
	}

	public void setMtc(String mtc) {
		this.mtc = mtc;
	}

	
	public String getMtc() {
		return mtc;
	}

	public void setCharges(Integer charges) {
		this.charges = charges;
	}

	
	public Integer getCharges() {
		return charges;
	}

	public void setNewQty(Integer newQty) {
		this.newQty = newQty;
	}

	
	public Integer getNewQty() {
		return newQty;
	}

	public void setOutMtc(String outMtc) {
		this.outMtc = outMtc;
	}

	
	public String getOutMtc() {
		return outMtc;
	}

	public void setOutCharges(Integer outCharges) {
		this.outCharges = outCharges;
	}

	
	public Integer getOutCharges() {
		return outCharges;
	}

	public void setOutUnit(String outUnit) {
		this.outUnit = outUnit;
	}

	
	public String getOutUnit() {
		return outUnit;
	}

	public void setYnTerms(String ynTerms) {
		this.ynTerms = ynTerms;
	}

	
	public String getYnTerms() {
		return ynTerms;
	}

	public void setWnTerms(String wnTerms) {
		this.wnTerms = wnTerms;
	}

	
	public String getWnTerms() {
		return wnTerms;
	}

	public void setYnFeeNotax(Integer ynFeeNotax) {
		this.ynFeeNotax = ynFeeNotax;
	}

	
	public Integer getYnFeeNotax() {
		return ynFeeNotax;
	}

	public void setYnFeeTax(Integer ynFeeTax) {
		this.ynFeeTax = ynFeeTax;
	}

	
	public Integer getYnFeeTax() {
		return ynFeeTax;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
