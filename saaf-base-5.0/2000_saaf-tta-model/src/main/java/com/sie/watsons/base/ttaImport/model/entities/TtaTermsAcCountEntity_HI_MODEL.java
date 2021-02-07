package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaTermsAcCountEntity_HI Entity Object
 * Tue Oct 15 09:37:39 CST 2019  Auto Generate
 */

public class TtaTermsAcCountEntity_HI_MODEL {

	@ExcelIgnore
	private Integer ttaTermsAcCountId;

	@ExcelProperty(value ="年度")
	private Date year;

	@ExcelProperty(value ="TTA TERMS 条款 来源")
	private String yTermsSource;

	@ExcelProperty(value ="TTA TERMS 条款 目标")
	private String yTermsTarget;

	@ExcelProperty(value ="表名")
	private String tableName;

	@ExcelProperty(value ="创建日期")
	private Date creationDate;

	@ExcelProperty(value ="创建人")
	private Integer createdBy;

	@ExcelProperty(value ="更新人")
	private Integer lastUpdatedBy;

	@ExcelProperty(value ="更新日期")
	private Date lastUpdateDate;

	@ExcelIgnore
	private Integer lastUpdateLogin;

	@ExcelProperty(value ="版本号")
	private Integer versionNum;

	@ExcelIgnore
	private Integer deleteFlag;

	@ExcelProperty(value ="排序号")
	private Integer orderNo;

	@ExcelProperty(value ="数据类型，A为OI账单导入类型，B为ttaterms实际费用计算")
	private String dataType;

	@ExcelProperty(value ="合同条款中文名，analysis报表需要展示的条款")
	private String contractTermCn;

	@ExcelProperty(value ="analysis报表需要展示条款的顺序，升序")
	private Integer orderNum;

	@ExcelProperty(value ="是否展示，1展示，0不展示")
	private Integer showStatus;

	public Integer getTtaTermsAcCountId() {
		return ttaTermsAcCountId;
	}

	public void setTtaTermsAcCountId(Integer ttaTermsAcCountId) {
		this.ttaTermsAcCountId = ttaTermsAcCountId;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public String getyTermsSource() {
		return yTermsSource;
	}

	public void setyTermsSource(String yTermsSource) {
		this.yTermsSource = yTermsSource;
	}

	public String getyTermsTarget() {
		return yTermsTarget;
	}

	public void setyTermsTarget(String yTermsTarget) {
		this.yTermsTarget = yTermsTarget;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getContractTermCn() {
		return contractTermCn;
	}

	public void setContractTermCn(String contractTermCn) {
		this.contractTermCn = contractTermCn;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}
}
