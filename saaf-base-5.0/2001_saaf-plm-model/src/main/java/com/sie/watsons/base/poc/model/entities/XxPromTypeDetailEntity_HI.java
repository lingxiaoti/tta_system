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
 * XxPromTypeDetailEntity_HI Entity Object
 * Sat Aug 17 11:55:48 CST 2019  Auto Generate
 */
@Entity
@Table(name="xx_prom_type_detail")
public class XxPromTypeDetailEntity_HI {
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

	@Column(name="xx_prom_type", nullable=false, length=10)	
	public String getXxPromType() {
		return xxPromType;
	}

	public void setAllowOverlapType(String allowOverlapType) {
		this.allowOverlapType = allowOverlapType;
	}

	@Column(name="allow_overlap_type", nullable=true, length=1000)	
	public String getAllowOverlapType() {
		return allowOverlapType;
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

	public void setBuParentId(String buParentId) {
		this.buParentId = buParentId;
	}

	@Id
	@Column(name="bu_parent_id", nullable=true, length=6)	
	public String getBuParentId() {
		return buParentId;
	}

	public void setNumOverlapPromotions(java.math.BigDecimal numOverlapPromotions) {
		this.numOverlapPromotions = numOverlapPromotions;
	}

	@Column(name="num_overlap_promotions", nullable=false, length=22)	
	public java.math.BigDecimal getNumOverlapPromotions() {
		return numOverlapPromotions;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
