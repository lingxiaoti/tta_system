package com.sie.watsons.base.ttaImport.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTradeCalendarEntity_HI_RO Entity Object
 * Tue Oct 15 09:37:32 CST 2019  Auto Generate
 */

public class TtaTradeCalendarEntity_HI_RO {
	public static final String  QUERY ="select *  from tta_trade_calendar ttc where 1=1 " ;
    private Integer trandeId;
    private Integer tradeYear;
    private Integer tradeMonth;
    private Integer weekStart;
    private Integer weekEnd;
    private Integer weekNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer tradeYearMonth;
    private Integer operatorUserId;

	public void setTrandeId(Integer trandeId) {
		this.trandeId = trandeId;
	}

	
	public Integer getTrandeId() {
		return trandeId;
	}

	public void setTradeYear(Integer tradeYear) {
		this.tradeYear = tradeYear;
	}

	
	public Integer getTradeYear() {
		return tradeYear;
	}

	public void setTradeMonth(Integer tradeMonth) {
		this.tradeMonth = tradeMonth;
	}

	
	public Integer getTradeMonth() {
		return tradeMonth;
	}

	public void setWeekStart(Integer weekStart) {
		this.weekStart = weekStart;
	}

	
	public Integer getWeekStart() {
		return weekStart;
	}

	public void setWeekEnd(Integer weekEnd) {
		this.weekEnd = weekEnd;
	}

	
	public Integer getWeekEnd() {
		return weekEnd;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	
	public Integer getWeekNum() {
		return weekNum;
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

	public void setTradeYearMonth(Integer tradeYearMonth) {
		this.tradeYearMonth = tradeYearMonth;
	}

	
	public Integer getTradeYearMonth() {
		return tradeYearMonth;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
