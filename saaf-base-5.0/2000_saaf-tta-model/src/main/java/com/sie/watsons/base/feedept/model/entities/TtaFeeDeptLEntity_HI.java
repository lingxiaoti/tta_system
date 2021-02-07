package com.sie.watsons.base.feedept.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFeeDeptLEntity_HI Entity Object
 * Wed May 29 11:24:37 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_FEE_DEPT_LINE")
public class TtaFeeDeptLEntity_HI {
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
    private Integer feedeptId;

	private String itemDescCn;
	private String itemDescEn;
	private String unit;
	private BigDecimal standardValue ;
	private String dmFlyer;//宣传单类型


    private Integer operatorUserId;

	public void setFeedeptLineId(Integer feedeptLineId) {
		this.feedeptLineId = feedeptLineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_FEE_DEPT_LINE", sequenceName = "SEQ_TTA_FEE_DEPT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_FEE_DEPT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="feedept_line_id", nullable=false, length=22)	
	public Integer getFeedeptLineId() {
		return feedeptLineId;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	@Column(name="line_code", nullable=true, length=50)	
	public String getLineCode() {
		return lineCode;
	}

	public void setParentLineId(Integer parentLineId) {
		this.parentLineId = parentLineId;
	}

	@Column(name="parent_line_id", nullable=true, length=22)	
	public Integer getParentLineId() {
		return parentLineId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@Column(name="sort_id", nullable=true, length=22)	
	public Integer getSortId() {
		return sortId;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	@Column(name="item_nbr")
	public String getItemNbr() {
		return itemNbr;
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

	public void setIfEffect(String ifEffect) {
		this.ifEffect = ifEffect;
	}

	@Column(name="if_effect", nullable=true, length=1)	
	public String getIfEffect() {
		return ifEffect;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=1500)	
	public String getRemark() {
		return remark;
	}

	public void setFeedeptId(Integer feedeptId) {
		this.feedeptId = feedeptId;
	}

	@Column(name="feedept_id", nullable=true, length=22)	
	public Integer getFeedeptId() {
		return feedeptId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	@Column(name="item_desc_cn")
	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	@Column(name="item_desc_en")
	public String getItemDescEn() {
		return itemDescEn;
	}

	public void setItemDescEn(String itemDescEn) {
		this.itemDescEn = itemDescEn;
	}

	@Column(name="unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="standard_value")
	public BigDecimal getStandardValue() {
		return standardValue;
	}

	public void setStandardValue(BigDecimal standardValue) {
		this.standardValue = standardValue;
	}

	@Column(name="dm_flyer")
	public String getDmFlyer() {
		return dmFlyer;
	}

	public void setDmFlyer(String dmFlyer) {
		this.dmFlyer = dmFlyer;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
