package com.sie.watsons.base.ttaImport.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTradeCalendarEntity_HI Entity Object
 * Tue Oct 15 09:37:32 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_trade_calendar")
public class TtaTradeCalendarEntity_HI {
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
	@Id
	@SequenceGenerator(name="SEQ_TTA_TRADE_CALENDAR", sequenceName="SEQ_TTA_TRADE_CALENDAR", allocationSize=1)
	@GeneratedValue(generator="SEQ_TTA_TRADE_CALENDAR",strategy=GenerationType.SEQUENCE)
	@Column(name="trande_id", nullable=false, length=22)
	public Integer getTrandeId() {
		return trandeId;
	}

	public void setTradeYear(Integer tradeYear) {
		this.tradeYear = tradeYear;
	}

	@Column(name="trade_year", nullable=false, length=22)	
	public Integer getTradeYear() {
		return tradeYear;
	}

	public void setTradeMonth(Integer tradeMonth) {
		this.tradeMonth = tradeMonth;
	}

	@Column(name="trade_month", nullable=false, length=22)	
	public Integer getTradeMonth() {
		return tradeMonth;
	}

	public void setWeekStart(Integer weekStart) {
		this.weekStart = weekStart;
	}

	@Column(name="week_start", nullable=false, length=22)	
	public Integer getWeekStart() {
		return weekStart;
	}

	public void setWeekEnd(Integer weekEnd) {
		this.weekEnd = weekEnd;
	}

	@Column(name="week_end", nullable=false, length=22)	
	public Integer getWeekEnd() {
		return weekEnd;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	@Column(name="week_num", nullable=false, length=22)	
	public Integer getWeekNum() {
		return weekNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setTradeYearMonth(Integer tradeYearMonth) {
		this.tradeYearMonth = tradeYearMonth;
	}

	@Column(name="trade_year_month", nullable=true, length=22)	
	public Integer getTradeYearMonth() {
		return tradeYearMonth;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
