package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaTradeCalendarEntity_HI Entity Object
 * Tue Oct 15 09:37:32 CST 2019  Auto Generate
 */

public class TtaTradeCalendarEntity_HI_MODEL {

	@ExcelIgnore
	private Integer trandeId;

	@ExcelProperty(value ="年yyyy")
	private Integer tradeYear;

	@ExcelProperty(value ="月mm")
	private Integer tradeMonth;

	@ExcelProperty(value ="周开始 yyyymmdd")
	private Integer weekStart;

	@ExcelProperty(value ="周结束 yyyymmdd")
	private Integer weekEnd;

	@ExcelProperty(value ="月的第几周")
	private Integer weekNum;

	@ExcelIgnore
	private Date creationDate;

	@ExcelIgnore
	private Integer createdBy;

	@ExcelIgnore
	private Integer lastUpdatedBy;

	@ExcelIgnore
	private Date lastUpdateDate;

	@ExcelIgnore
	private Integer lastUpdateLogin;

	@ExcelIgnore
	private Integer versionNum;

	@ExcelProperty(value ="年月yyyymm")
	private Integer tradeYearMonth;

	public Integer getTrandeId() {
		return trandeId;
	}

	public void setTrandeId(Integer trandeId) {
		this.trandeId = trandeId;
	}

	public Integer getTradeYear() {
		return tradeYear;
	}

	public void setTradeYear(Integer tradeYear) {
		this.tradeYear = tradeYear;
	}

	public Integer getTradeMonth() {
		return tradeMonth;
	}

	public void setTradeMonth(Integer tradeMonth) {
		this.tradeMonth = tradeMonth;
	}

	public Integer getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(Integer weekStart) {
		this.weekStart = weekStart;
	}

	public Integer getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(Integer weekEnd) {
		this.weekEnd = weekEnd;
	}

	public Integer getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
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

	public Integer getTradeYearMonth() {
		return tradeYearMonth;
	}

	public void setTradeYearMonth(Integer tradeYearMonth) {
		this.tradeYearMonth = tradeYearMonth;
	}
}
