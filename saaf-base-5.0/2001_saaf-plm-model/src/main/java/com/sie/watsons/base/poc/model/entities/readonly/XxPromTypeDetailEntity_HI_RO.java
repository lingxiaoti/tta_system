package com.sie.watsons.base.poc.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * XxPromTypeDetailEntity_HI_RO Entity Object
 * Sat Aug 17 11:55:48 CST 2019  Auto Generate
 */

public class XxPromTypeDetailEntity_HI_RO {
    private String xxPromType;
    private String allowOverlapType;
    private String lastUpdateId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String buParentId;
    private java.math.BigDecimal numOverlapPromotions;
    private Integer operatorUserId;

	public void setXxPromType(String xxPromType) {
		this.xxPromType = xxPromType;
	}

	
	public String getXxPromType() {
		return xxPromType;
	}

	public void setAllowOverlapType(String allowOverlapType) {
		this.allowOverlapType = allowOverlapType;
	}

	
	public String getAllowOverlapType() {
		return allowOverlapType;
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

	public void setBuParentId(String buParentId) {
		this.buParentId = buParentId;
	}

	
	public String getBuParentId() {
		return buParentId;
	}

	public void setNumOverlapPromotions(java.math.BigDecimal numOverlapPromotions) {
		this.numOverlapPromotions = numOverlapPromotions;
	}

	
	public java.math.BigDecimal getNumOverlapPromotions() {
		return numOverlapPromotions;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
