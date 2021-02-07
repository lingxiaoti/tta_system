package com.sie.watsons.base.feedept.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFeeDeptLEntity_HI_RO Entity Object
 * Wed May 29 11:24:37 CST 2019  Auto Generate
 */

public class TtaFeeDeptLEntity_HI_RO {
    private Integer feedeptLineId;
    private String lineCode;
    private Integer parentLineId;
    private Integer sortId;
    private String itemNbr;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String ifEffect;
    private String remark;
    private String itemDescCn;
	private String itemDescEn;
	private String unit;
	private BigDecimal standardValue ;
	private String parentLineCode;

	private String lineDesc;

    private Integer feedeptId;
    private Integer operatorUserId;

	private String dmFlyer;//宣传单类型

	public static final String TTA_ITEM_V = "select * from TTA_FEE_DEPT_L_V V where 1=1";

	public void setFeedeptLineId(Integer feedeptLineId) {
		this.feedeptLineId = feedeptLineId;
	}

	public Integer getFeedeptLineId() {
		return feedeptLineId;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	
	public String getLineCode() {
		return lineCode;
	}

	public void setParentLineId(Integer parentLineId) {
		this.parentLineId = parentLineId;
	}

	
	public Integer getParentLineId() {
		return parentLineId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	
	public Integer getSortId() {
		return sortId;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	
	public String getItemNbr() {
		return itemNbr;
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

	public void setIfEffect(String ifEffect) {
		this.ifEffect = ifEffect;
	}

	
	public String getIfEffect() {
		return ifEffect;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setFeedeptId(Integer feedeptId) {
		this.feedeptId = feedeptId;
	}

	
	public Integer getFeedeptId() {
		return feedeptId;
	}


	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	public String getItemDescEn() {
		return itemDescEn;
	}

	public void setItemDescEn(String itemDescEn) {
		this.itemDescEn = itemDescEn;
	}

	public String getParentLineCode() {
		return parentLineCode;
	}

	public void setParentLineCode(String parentLineCode) {
		this.parentLineCode = parentLineCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getStandardValue() {
		return standardValue;
	}

	public void setStandardValue(BigDecimal standardValue) {
		this.standardValue = standardValue;
	}

	public String getLineDesc() {
		return lineDesc;
	}

	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getDmFlyer() {
		return dmFlyer;
	}

	public void setDmFlyer(String dmFlyer) {
		this.dmFlyer = dmFlyer;
	}
}
