package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaOiTaxEntity_HI Entity Object
 * Tue Oct 15 09:37:41 CST 2019  Auto Generate
 */

public class TtaOiTaxEntity_HI_MODEL {

	@ExcelIgnore
	private Integer ttaOiTaxId;

	@ExcelProperty(value ="年月")
	private Date yearMonth;

	@ExcelProperty(value ="税率")
	private Integer tax;

	@ExcelProperty(value ="条款中文名称")
	private String terms;

	@ExcelProperty(value ="计生供应商（Y/N）")
	private String familyPlaningFlag;

	@ExcelProperty(value ="中药饮品(Y/N)")
	private String chineseHerbalDrink;

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

	public Integer getTtaOiTaxId() {
		return ttaOiTaxId;
	}

	public void setTtaOiTaxId(Integer ttaOiTaxId) {
		this.ttaOiTaxId = ttaOiTaxId;
	}

	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getTax() {
		return tax;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getFamilyPlaningFlag() {
		return familyPlaningFlag;
	}

	public void setFamilyPlaningFlag(String familyPlaningFlag) {
		this.familyPlaningFlag = familyPlaningFlag;
	}

	public String getChineseHerbalDrink() {
		return chineseHerbalDrink;
	}

	public void setChineseHerbalDrink(String chineseHerbalDrink) {
		this.chineseHerbalDrink = chineseHerbalDrink;
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
}
