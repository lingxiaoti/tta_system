package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaDmFullLineEntity_HI_RO Entity Object
 * Mon Nov 18 09:33:20 CST 2019  Auto Generate
 */

public class TtaDmFullLineEntity_HI_RO {
    private Integer dmFullLineId;
    private String durationPeriod;

/*
 	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;

    private String mapPosition;
    */
    private String dmPage;
    private String locationCode;
    private String effectiveArea;
    private Integer effectiveAreaCnt;
    private String acceptUnit;
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
    private String createdByName;
	private Integer yearMonth;

	public static final String  CREATE_QUERY = " select * from\n" +
			"(select tpslv.dm_full_line_id,tpslv.duration_period,nvl(tpslv.is_create,'N') is_create, row_number() \n" +
			" over(partition by tpslv.duration_period order by nvl(tpslv.is_create,'N') desc ) rn\n" +
			" from tta_dm_full_line tpslv ) t1 where t1.rn=1 ";

	public static final String  QUERY_SQL ="select td.*,\n" +
			"       bu.user_full_name as created_by_name\n" +
			"  from tta_dm_full_line td\n" +
			"  left join base_users bu\n" +
			"    on td.created_by = bu.user_id\n" +
			" where 1 = 1 \n";

	public static final String GET_CHECK_SQL = "" +
			" select distinct\n" +
			" 	tdflc.dm_page,\n" +
			" 	tdflc.item_nbr,\n" +
			" 	tdflc.duration_period,\n" +
			" 	tdflc.location_code\n" +
			"  from tta_dm_full_line_check tdflc\n" +
			" where exists (" +
			"		select 1\n" +
			"          from tta_dm_full_line tdfl\n" +
			"            where tdflc.dm_page = tdfl.dm_page\n" +//dm页面
			"           and tdflc.item_nbr = tdfl.item_nbr\n" + //产品编号
			"           and tdflc.duration_period = tdfl.duration_period\n" + //促销档期
			"			and tdflc.location_code = tdfl.location_code \n" + //位置
			" )\n"; //产品编号+ DM页码+位置+促销档期

	public void setDmFullLineId(Integer dmFullLineId) {
		this.dmFullLineId = dmFullLineId;
	}

	
	public Integer getDmFullLineId() {
		return dmFullLineId;
	}

	public void setDurationPeriod(String durationPeriod) {
		this.durationPeriod = durationPeriod;
	}

	
	public String getDurationPeriod() {
		return durationPeriod;
	}


	public void setDmPage(String dmPage) {
		this.dmPage = dmPage;
	}

	
	public String getDmPage() {
		return dmPage;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	
	public String getLocationCode() {
		return locationCode;
	}

	public void setEffectiveArea(String effectiveArea) {
		this.effectiveArea = effectiveArea;
	}

	
	public String getEffectiveArea() {
		return effectiveArea;
	}

	public void setEffectiveAreaCnt(Integer effectiveAreaCnt) {
		this.effectiveAreaCnt = effectiveAreaCnt;
	}

	
	public Integer getEffectiveAreaCnt() {
		return effectiveAreaCnt;
	}

	public void setAcceptUnit(String acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	
	public String getAcceptUnit() {
		return acceptUnit;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	
	public String getItemNbr() {
		return itemNbr;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	
	public String getIsCreate() {
		return isCreate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

}
