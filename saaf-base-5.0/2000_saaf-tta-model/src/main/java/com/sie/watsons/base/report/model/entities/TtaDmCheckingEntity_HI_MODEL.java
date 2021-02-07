package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldDesc;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaDmCheckingEntity_HI Entity Object
 * Mon Nov 18 20:28:25 CST 2019  Auto Generate
 */
public class TtaDmCheckingEntity_HI_MODEL {

	@ExcelProperty(value = "唯一标识")
	@FieldDesc(isUpdateWhereKey = true)
    private Integer dmId;

	@ExcelIgnore
    @FieldDesc(isUpdateWhereKey = false)
    private Integer createdBy;

	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = false)
    private Integer lastUpdatedBy;

	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = false)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = false)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = false)
    private Integer lastUpdateLogin;

	@ExcelProperty(value = "借记单编号")
	@FieldDesc(isUpdateWhereKey = false)
    private String debitOrderCode;

	@ExcelProperty(value = "实收金额")
    @FieldDesc(isUpdateWhereKey = false)
    private BigDecimal receipts;

	public Integer getDmId() {
		return dmId;
	}

	public void setDmId(Integer dmId) {
		this.dmId = dmId;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getDebitOrderCode() {
		return debitOrderCode;
	}

	public void setDebitOrderCode(String debitOrderCode) {
		this.debitOrderCode = debitOrderCode;
	}

	public BigDecimal getReceipts() {
		return receipts;
	}

	public void setReceipts(BigDecimal receipts) {
		this.receipts = receipts;
	}

}
