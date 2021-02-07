package com.sie.watsons.base.pon.information.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * EquPonQuoSecondResultEntity_HI_RO Entity Object
 * Fri Oct 25 16:49:24 CST 2019  Auto Generate
 */

public class EquPonQuoSecondResultEntity_HI_RO {
    private Integer secResultId;
	private Integer approvalId;
    private String isSelect;
    private Integer supplierId;
    private String supplierNumber;
    private String supplierName;
    private Integer quotationId;
    private String quotationNumber;
    private Integer sQuotationId;
    private String sQuotationNum;
    private Integer noPriceScore;

    private Integer priceScore;
    private Integer totalScore;
    private String remark;
    private Integer createdBy;
    private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
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

	public void setSecResultId(Integer secResultId) {
		this.secResultId = secResultId;
	}

	
	public Integer getSecResultId() {
		return secResultId;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	
	public String getIsSelect() {
		return isSelect;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	
	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	
	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	
	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setSQuotationId(Integer sQuotationId) {
		this.sQuotationId = sQuotationId;
	}

	
	public Integer getSQuotationId() {
		return sQuotationId;
	}

	public void setSQuotationNum(String sQuotationNum) {
		this.sQuotationNum = sQuotationNum;
	}

	
	public String getSQuotationNum() {
		return sQuotationNum;
	}

	public void setNoPriceScore(Integer noPriceScore) {
		this.noPriceScore = noPriceScore;
	}

	
	public Integer getNoPriceScore() {
		return noPriceScore;
	}

	public void setPriceScore(Integer priceScore) {
		this.priceScore = priceScore;
	}

	
	public Integer getPriceScore() {
		return priceScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}

	public Integer getsQuotationId() {
		return sQuotationId;
	}

	public void setsQuotationId(Integer sQuotationId) {
		this.sQuotationId = sQuotationId;
	}

	public String getsQuotationNum() {
		return sQuotationNum;
	}

	public void setsQuotationNum(String sQuotationNum) {
		this.sQuotationNum = sQuotationNum;
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
}
