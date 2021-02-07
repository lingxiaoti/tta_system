package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaDmFullLineEntity_HI Entity Object
 * Mon Nov 18 09:33:20 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_DM_FULL_LINE")
public class TtaDmFullLineEntity_HI {
    private Integer dmFullLineId;
	@ExcelProperty(value = "促销档期")
	private String durationPeriod;
/*
	@ExcelProperty(value = "促销开始日期")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;

	@ExcelProperty(value = "促销结束日期")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;

	@ExcelProperty(value = "图位")
	private String mapPosition;
*/
	@ExcelProperty(value = "DM页码")
	private String dmPage;
	@ExcelProperty(value = "位置")
	private String locationCode;
	@ExcelProperty(value = "生效区域")
	private String effectiveArea;
	@ExcelProperty(value = "生效区域数")
	private Integer effectiveAreaCnt;

	@ExcelProperty(value = "图位")//收取单位
	private String acceptUnit;
	@ExcelProperty(value = "产品编号")
	private String itemNbr;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String isCreate;
    private Integer operatorUserId;
	@ExcelProperty(value = "年月")
	private Integer yearMonth;


	public void setDmFullLineId(Integer dmFullLineId) {
		this.dmFullLineId = dmFullLineId;
	}


	@Id
	@SequenceGenerator(name = "SEQ_TTA_DM_FULL_LINE", sequenceName = "SEQ_TTA_DM_FULL_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_DM_FULL_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="dm_full_line_id", nullable=false, length=22)
	public Integer getDmFullLineId() {
		return dmFullLineId;
	}

	public void setDurationPeriod(String durationPeriod) {
		this.durationPeriod = durationPeriod;
	}

	@Column(name="duration_period", nullable=true, length=500)	
	public String getDurationPeriod() {
		return durationPeriod;
	}

	public void setDmPage(String dmPage) {
		this.dmPage = dmPage;
	}

	@Column(name="dm_page", nullable=true, length=22)	
	public String getDmPage() {
		return dmPage;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Column(name="location_code", nullable=true, length=500)	
	public String getLocationCode() {
		return locationCode;
	}

	public void setEffectiveArea(String effectiveArea) {
		this.effectiveArea = effectiveArea;
	}

	@Column(name="effective_area", nullable=true, length=500)	
	public String getEffectiveArea() {
		return effectiveArea;
	}

	public void setEffectiveAreaCnt(Integer effectiveAreaCnt) {
		this.effectiveAreaCnt = effectiveAreaCnt;
	}

	@Column(name="effective_area_cnt", nullable=true, length=22)	
	public Integer getEffectiveAreaCnt() {
		return effectiveAreaCnt;
	}
	
	public void setAcceptUnit(String acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	@Column(name="accept_unit", nullable=true, length=500)	
	public String getAcceptUnit() {
		return acceptUnit;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	@Column(name="item_nbr", nullable=true, length=100)	
	public String getItemNbr() {
		return itemNbr;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	@Column(name="is_create", nullable=true, length=2)	
	public String getIsCreate() {
		return isCreate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="year_month")
	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}
}
