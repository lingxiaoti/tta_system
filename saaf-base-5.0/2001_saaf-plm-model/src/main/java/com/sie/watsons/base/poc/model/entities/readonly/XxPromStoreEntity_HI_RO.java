package com.sie.watsons.base.poc.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * XxPromStoreEntity_HI_RO Entity Object
 * Tue Aug 13 12:21:38 CST 2019  Auto Generate
 */

public class XxPromStoreEntity_HI_RO {
    private BigDecimal xxPromId;
    private BigDecimal store;
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

	public void setXxPromId(BigDecimal xxPromId) {
		this.xxPromId = xxPromId;
	}

	
	public BigDecimal getXxPromId() {
		return xxPromId;
	}

	public void setStore(BigDecimal store) {
		this.store = store;
	}

	
	public BigDecimal getStore() {
		return store;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	
	public String getCreateId() {
		return createId;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	
	public String getExtractStatus() {
		return extractStatus;
	}

	public void setExtractDatetime(Date extractDatetime) {
		this.extractDatetime = extractDatetime;
	}

	
	public Date getExtractDatetime() {
		return extractDatetime;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
