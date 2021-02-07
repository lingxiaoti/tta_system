package com.sie.watsons.base.poc.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * XxPromStoreEntity_HI Entity Object
 * Tue Aug 13 12:21:38 CST 2019  Auto Generate
 */
@Entity
@Table(name="xx_prom_store")
public class XxPromStoreEntity_HI {
    private String xxPromId;
    private String store;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String remarks;
    private String lastUpdateId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String createId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDatetime;
    private String extractStatus;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date extractDatetime;
    private Integer operatorUserId;

	public void setXxPromId(String xxPromId) {
		this.xxPromId = xxPromId;
	}

	@Id
	@Column(name="xx_prom_id", nullable=false, length=22)	
	public String getXxPromId() {
		return xxPromId;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Column(name="store", nullable=false, length=22)	
	public String getStore() {
		return store;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=false, length=7)	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=false, length=7)	
	public Date getEndDate() {
		return endDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=250)	
	public String getRemarks() {
		return remarks;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	@Column(name="last_update_id", nullable=false, length=40)	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Column(name="last_update_datetime", nullable=false, length=7)	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name="create_id", nullable=false, length=40)	
	public String getCreateId() {
		return createId;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name="create_datetime", nullable=false, length=7)	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	@Column(name="extract_status", nullable=false, length=1)	
	public String getExtractStatus() {
		return extractStatus;
	}

	public void setExtractDatetime(Date extractDatetime) {
		this.extractDatetime = extractDatetime;
	}

	@Column(name="extract_datetime", nullable=true, length=7)	
	public Date getExtractDatetime() {
		return extractDatetime;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
