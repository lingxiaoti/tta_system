package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaSystemCurrentLineEntity_HI Entity Object
 * Thu Jul 18 10:38:18 CST 2019  Auto Generate
 */
public class TtaSystemCurrentLineEntity_HI_MODEL {
	@ExcelIgnore
	private Integer systemCurrentId;

	@ExcelProperty(value ="12_Month")
	private String annualMonth;

	@ExcelProperty(value ="Month")
	private String month;

	@ExcelProperty(value ="Item")
	private String item;

	@ExcelProperty(value ="ITEM_DESC_CN")
	private String itemDescCn;

	@ExcelProperty(value ="BRAND_CN")
	private String brandCn;

	@ExcelProperty(value ="VENDOR_NBR")
	private String vendorNbr;

	@ExcelProperty(value ="VENDOR_NAME")
	private String vendorName;

	@ExcelProperty(value ="GROUP_DESC")
	private String groupDesc;

	@ExcelProperty(value ="DEPT_DESC")
	private String deptDesc;

	@ExcelProperty(value ="Activity")
	private String activity;

	@ExcelProperty(value ="UDA4")
	private String uda4;

	@ExcelProperty(value ="Store_Coun")
	private Integer storeCoun;

	@ExcelProperty(value ="CTW")
	private String ctw;

	@ExcelProperty(value ="EB")
	private String eb;

	@ExcelProperty(value ="rate card")
	private Integer rateCard;

	@ExcelProperty(value ="全年月份")
	private Integer annualMonthN;

	@ExcelProperty(value ="数据生成月")
	private Integer monthN;

	@ExcelIgnore
	private Integer createdBy;

	@ExcelIgnore
	private Integer lastUpdatedBy;

	@ExcelIgnore
	private Date lastUpdateDate;

	@ExcelIgnore
	private Date creationDate;

	@ExcelIgnore
	private Integer lastUpdateLogin;

	@ExcelIgnore
	private Integer versionNum;

	public Integer getSystemCurrentId() {
		return systemCurrentId;
	}

	public void setSystemCurrentId(Integer systemCurrentId) {
		this.systemCurrentId = systemCurrentId;
	}

	public String getAnnualMonth() {
		return annualMonth;
	}

	public void setAnnualMonth(String annualMonth) {
		this.annualMonth = annualMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getUda4() {
		return uda4;
	}

	public void setUda4(String uda4) {
		this.uda4 = uda4;
	}

	public Integer getStoreCoun() {
		return storeCoun;
	}

	public void setStoreCoun(Integer storeCoun) {
		this.storeCoun = storeCoun;
	}

	public String getCtw() {
		return ctw;
	}

	public void setCtw(String ctw) {
		this.ctw = ctw;
	}

	public String getEb() {
		return eb;
	}

	public void setEb(String eb) {
		this.eb = eb;
	}

	public Integer getRateCard() {
		return rateCard;
	}

	public void setRateCard(Integer rateCard) {
		this.rateCard = rateCard;
	}

	public Integer getAnnualMonthN() {
		return annualMonthN;
	}

	public void setAnnualMonthN(Integer annualMonthN) {
		this.annualMonthN = annualMonthN;
	}

	public Integer getMonthN() {
		return monthN;
	}

	public void setMonthN(Integer monthN) {
		this.monthN = monthN;
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

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
}
